package com.ourlife.dev.modules.biz.service;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.DoubleUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.*;
import com.ourlife.dev.modules.biz.entity.*;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysIdentityService;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import com.ourlife.dev.terminal.TerminalFactory;
import com.ourlife.dev.terminal.TerminalService;
import com.ourlife.dev.terminal.sms.SmsService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * 订单信息Service
 *
 * @author ourlife
 * @version 2014-05-31
 */
@Component
@Transactional(readOnly = true)
public class OrderInfoService extends BaseService {

	@Autowired
	private OrderInfoDao orderInfoDao;

	@Autowired
	private DistributorDao distributorDao;

	@Autowired
	private SysIdentityService sysIdentityService;

	@Autowired
	private BalanceHisDao balanceHisDao;

	@Autowired
	private DirectBalanceHisDao directBalanceHisDao;

	@Autowired
	private ProductHistoryService productHistoryService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private OrderLogService orderLogService;

	public OrderInfo get(Long id) {
		orderInfoDao.clear();
		return orderInfoDao.findOne(id);
	}

	public OrderInfo getByOrderId(String orderId) {
		return orderInfoDao.findByOrderId(orderId);
	}

	public Page<OrderInfo> find(Page<OrderInfo> page, OrderInfo orderInfo) {
		DetachedCriteria dc = orderInfoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(orderInfo.getType())) {
			if (!orderInfo.getType().equals("all")) {
				dc.add(Restrictions.eq("type", orderInfo.getType()));
			}
		}
		if (StringUtils.isNotEmpty(orderInfo.getOrderId())) {
			dc.add(Restrictions.like("orderId", "%" + orderInfo.getOrderId()
					+ "%"));
		}
		if (StringUtils.isNotEmpty(orderInfo.getCustomerName())) {
			dc.add(Restrictions.like("customerName",
					"%" + orderInfo.getCustomerName() + "%"));
		}
		if (StringUtils.isNotEmpty(orderInfo.getCustomerMobile())) {
			dc.add(Restrictions.like("customerMobile",
					"%" + orderInfo.getCustomerMobile() + "%"));
		}
		if (StringUtils.isNotEmpty(orderInfo.getUseDate())) {
			// dc.add(Restrictions.eq("useDate", orderInfo.getUseDate()));
			dc.add(Restrictions.ge("useDate", orderInfo.getUseDate()));
		}
		if (StringUtils.isNotEmpty(orderInfo.getRemarks())) {
			dc.add(Restrictions.le("useDate", orderInfo.getRemarks()));
		} else {
			if (StringUtils.isNotEmpty(orderInfo.getUseDate())) {
				dc.add(Restrictions.le("useDate", orderInfo.getUseDate()));
			}

		}

		if (StringUtils.isNotEmpty(orderInfo.getStatus())) {
			dc.add(Restrictions.eq("status", orderInfo.getStatus()));
		}

		dc.createAlias("distributor", "distributor");
		if (orderInfo.getDistributor() != null) {
			if (StringUtils.isNotBlank(orderInfo.getDistributor().getName())) {
				dc.add(Restrictions.or(
						Restrictions.like("distributor.name", "%"
								+ orderInfo.getDistributor().getName() + "%"),
						Restrictions.like("distributor.username", "%"
								+ orderInfo.getDistributor().getName() + "%"),
						Restrictions.like("distributor.company", "%"
								+ orderInfo.getDistributor().getName() + "%")));
			}
		}
		dc.createAlias("supplier", "supplier");
		if (orderInfo.getSupplier() != null) {
			if (StringUtils.isNotBlank(orderInfo.getSupplier().getName())) {
				dc.add(Restrictions.like("supplier.name", "%"
						+ orderInfo.getSupplier().getName() + "%"));
			}
		}
		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")) {
			dc.add(Restrictions.eq("distributor.id", orderInfo.getDistributor()
					.getId()));
		} else if (user.getUserType().equals("4")) {
			dc.add(Restrictions.eq("supplier.id", orderInfo.getSupplier()
					.getId()));
		}

		dc.add(Restrictions.eq(OrderInfo.DEL_FLAG, OrderInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return orderInfoDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public synchronized void save(OrderInfo orderInfo) {
		if (orderInfo.getId() == null) {
			orderInfo.setStep("-1");
			orderInfo.setStatus("-2");
		}
		logger.debug("0 提交订单");
		// 0 提交订单
		if (orderInfo.getStep().equals("-1")) {
			saveOrderInfoStep0(orderInfo);
		}
		logger.debug("1订单支付");
		// 1订单支付
		if (orderInfo.getStep().equals("0")) {
			saveOrderInfoStep1(orderInfo);
		}
		logger.debug("2远端确认");
		// 2远端确认
		if (orderInfo.getStep().equals("1")) {
			saveOrderInfoStep2(orderInfo);
		}
		logger.debug("3 信息发送");
		// 3 信息发送
		if (orderInfo.getStep().equals("2")) {
			saveOrderInfoStep3(orderInfo);
		}

	}

	/**
	 * 0 提交订单
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveOrderInfoStep0(OrderInfo orderInfo) {

		orderInfo.setOrderDate(new Date());
		String orderId = "";
		if (orderInfo.getType().equals("0")) {
			orderId = sysIdentityService.nextId("s_order_id");
		} else if (orderInfo.getType().equals("1")) {
			orderId = sysIdentityService.nextId("z_order_id");
		}
		String hashcode = (new Date()).hashCode() + "";
		orderInfo.setOrderId(orderId
				+ hashcode.substring(hashcode.length() - 1));
		orderInfo.setName(orderInfo.getSupplier().getName() + "-"
				+ orderInfo.getProHistory().getName());
		orderInfo.setContactMobile(orderInfo.getCustomerMobile());

		orderInfo.setPayStatus(0);

		orderInfo.setStep("0");
		orderInfoDao.save(orderInfo);

		this.addOrderLog("订单提交成功，订单号：" + orderInfo.getOrderId(), orderInfo);
	}

	/**
	 * 1 订单支付
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveOrderInfoStep1(OrderInfo orderInfo) {

		// 创建余额历史
		if (orderInfo.getType().endsWith("0")) {
			BalanceHis balanceHis = new BalanceHis();
			User user = systemService.getUserByLoginName(orderInfo
					.getDistributor().getUsername());
			balanceHis.setUser(user);
			balanceHis.setType("1");
			balanceHis.setBegin(orderInfo.getDistributor().getBalance());
			balanceHis.setAmount(-orderInfo.getTotalpay());
			orderInfo.getDistributor().setBalance(
					DoubleUtils.subtract(orderInfo.getDistributor()
							.getBalance(), orderInfo.getTotalpay()));
			balanceHis.setEnd(orderInfo.getDistributor().getBalance());
			balanceHis.setStatus("0");

			balanceHis.setReason("订单号：" + orderInfo.getOrderId());
			balanceHis.setRemarks(orderInfo.getPurchasePrice() + " * "
					+ orderInfo.getPurchaseAmount() + " = "
					+ orderInfo.getTotalpay());
			balanceHisDao.save(balanceHis);
		} else if (orderInfo.getType().endsWith("1")) {
			// 手续费处理
			Double orderOptFee = DoubleUtils.mul(
					Double.valueOf(Global.getConfig("order_opt_fee")),
					orderInfo.getPurchaseAmount());

			BalanceHis balanceHis = new BalanceHis();
			User user = systemService.getUserByLoginName(orderInfo
					.getDistributor().getUsername());
			balanceHis.setUser(user);
			balanceHis.setType("1");
			balanceHis.setBegin(orderInfo.getDistributor().getBalance());
			balanceHis.setAmount(-orderOptFee);
			orderInfo.getDistributor().setBalance(
					DoubleUtils.subtract(orderInfo.getDistributor()
							.getBalance(), orderOptFee));
			balanceHis.setEnd(orderInfo.getDistributor().getBalance());
			balanceHis.setStatus("0");

			balanceHis.setReason("订单号：" + orderInfo.getOrderId());
			balanceHis.setRemarks("直通手续费");
			balanceHisDao.save(balanceHis);

			// 票价处理
			DirectBalanceHis directBalanceHis = new DirectBalanceHis();
			directBalanceHis.setDirect(orderInfo.getDirect());
			directBalanceHis.setType("1");
			Double totlaPay = DoubleUtils.mul(orderInfo.getPurchaseAmount(),
					orderInfo.getPurchasePrice());
			directBalanceHis.setBegin(orderInfo.getDirect().getBalance());
			directBalanceHis.setAmount(-totlaPay);
			orderInfo.getDirect().setBalance(
					DoubleUtils.subtract(orderInfo.getDirect().getBalance(),
							totlaPay));
			directBalanceHis.setEnd(orderInfo.getDirect().getBalance());
			directBalanceHis.setStatus("0");

			directBalanceHis.setReason("订单号：" + orderInfo.getOrderId());
			directBalanceHis.setRemarks(orderInfo.getPurchasePrice() + " * "
					+ orderInfo.getPurchaseAmount() + " = " + totlaPay);
			directBalanceHisDao.save(directBalanceHis);
		}

		orderInfo.setPayStatus(1);

		orderInfo.setStep("1");
		orderInfoDao.save(orderInfo);

		if (orderInfo.getType().equals("0")) {
			this.addOrderLog("订单支付成功", orderInfo);
		} else if (orderInfo.getType().equals("1")) {
			Double totlaPay = DoubleUtils.mul(orderInfo.getPurchaseAmount(),
					orderInfo.getPurchasePrice());
			Double orderOptFee = DoubleUtils.mul(
					Double.valueOf(Global.getConfig("order_opt_fee")),
					orderInfo.getPurchaseAmount());
			this.addOrderLog("订单支付成功，支付金额：" + orderInfo.getTotalpay()
					+ "(门票总价：" + totlaPay + "，平台手续费：" + orderOptFee + ")",
					orderInfo);
		}
	}

	/**
	 * 2 远端确认
	 *
	 * @return
	 * @throws RemoteException
	 */
	@Transactional(readOnly = false)
	public void saveOrderInfoStep2(OrderInfo orderInfo) {
		String checkTerminal = orderInfo.getSupplier().getCheckTerminal();
		if (checkTerminal.equals("9")) {
			orderInfo.setStep("29");
			this.addOrderLog(
					"订单已提交待系统确认，订票数量：" + orderInfo.getPurchaseAmount(),
					orderInfo);
			SmsService smsService = new SmsService();
			String content = "订单" + orderInfo.getOrderId() + "已提交，请您处理确认！";
			smsService.sendSms(Global.getConfig("admin_mobile"), content);
			return;
		}

		try {
			TerminalService service = TerminalFactory
					.createTerminalService(orderInfo.getSupplier()
							.getCheckTerminal());
			service.submitOrder(orderInfo);
			orderInfo.setUuCode(service.queryCodeOrder(orderInfo));

			orderInfo.setStep("2");

			orderInfoDao.save(orderInfo);
			this.addOrderLog("远端确认成功，远端订单号：" + orderInfo.getUuOrdernum(),
					orderInfo);

		} catch (Exception e) {
			logger.error("远端订单确认失败", e);
			this.addOrderLog("远端确认失败，失败原因：" + e.getMessage(), orderInfo);
		}

	}

	/**
	 * 3 信息发送
	 *
	 * @return
	 * @throws RemoteException
	 */
	@Transactional(readOnly = false)
	public void saveOrderInfoStep3(OrderInfo orderInfo) {

		SmsService smsService = new SmsService();

		String returnCode = smsService.sendSms(orderInfo);
		if (returnCode.equals("1")) {
			if (orderInfo.getUuRremsg() == null) {
				orderInfo.setUuRremsg("1");
			} else {
				orderInfo
						.setUuRremsg((Integer.parseInt(orderInfo.getUuRremsg()) + 1)
								+ "");
			}

			orderInfo.setStep("3");
			orderInfoDao.save(orderInfo);

			String checkTerminal = orderInfo.getSupplier().getCheckTerminal();
			if (checkTerminal.equals("0") || checkTerminal.equals("2")) {
				this.addOrderLog("信息发送成功，取票凭证码：" + orderInfo.getUuCode(),
						orderInfo);
			} else {
				this.addOrderLog("信息发送成功", orderInfo);
			}
		} else {
			this.addOrderLog("信息发送失败，失败原因：" + returnCode, orderInfo);
		}

	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		orderInfoDao.deleteById(id);
	}

	public List<OrderInfo> getOrdersByStatus(String status) {
		return orderInfoDao.findByStatus(status);
	}

	@Transactional(readOnly = false)
	public synchronized void updateFromPft(OrderInfo orderInfo) {
		if (orderInfo.getStatus().equals("1")) {
			// 平台订单 验票后打款给景区
			if (orderInfo.getType().equals("0")) {
				Supplier supplier = orderInfo.getSupplier();
				BalanceHis balanceHis = new BalanceHis();
				User user = systemService.getUserByLoginName(supplier.getNo());
				balanceHis.setUser(user);
				balanceHis.setType("1");
				balanceHis.setBegin(supplier.getBalance());
				Double total = orderInfo.getProHistory().getPurchasePrice()
						* orderInfo.getPurchaseAmount();
				balanceHis.setAmount(total);
				supplier.setBalance(DoubleUtils.add(supplier.getBalance(),
						total));
				balanceHis.setEnd(supplier.getBalance());
				balanceHis.setReason("订单号：" + orderInfo.getOrderId());
				balanceHis.setStatus("0");
				balanceHisDao.save(balanceHis);

				supplierDao.save(supplier);
			}

			this.addOrderLog("订单完成，感谢使用！", orderInfo);

		}
		orderInfoDao.save(orderInfo);

	}

	@Transactional(readOnly = false)
	public String sendSms(String orderid) {
		OrderInfo orderInfo = getByOrderId(orderid);
		if (orderInfo == null) {
			return "false";
		}

		SmsService smsService = new SmsService();
		String returnCode = smsService.sendSms(orderInfo);
		boolean ok = returnCode.equals("1");
		if (ok) {
			int count = Integer.valueOf(orderInfo.getUuRremsg()) + 1;
			orderInfo.setUuRremsg(count + "");
			this.addOrderLog("重发信息成功，已发送信息" + count + "次", orderInfo);
			// orderInfoDao.save(orderInfo);
			return "true";
		} else {
			this.addOrderLog("信息发送失败，失败原因：" + returnCode, orderInfo);
		}
		return "false";
	}

	@Transactional(readOnly = false)
	public synchronized void updateOrder(OrderInfo orderInfo,
			OrderInfo newOrderInfo) {
		int oldPurchaseAmount = orderInfo.getPurchaseAmount();

		logger.debug("10 修改订单");
		// 0 修改订单
		if (orderInfo.getStep().equals("2") || orderInfo.getStep().equals("3")
				|| orderInfo.getStep().equals("12")) {
			updateOrderInfoStep0(orderInfo, newOrderInfo);
		}

		if (orderInfo.getStep().equals("29")) {
			updateOrderInfoStep0(orderInfo, newOrderInfo);
		}

		logger.debug("11远端确认");
		// 1远端确认
		if (orderInfo.getStep().equals("10")) {
			updateOrderInfoStep1(orderInfo);
		}

		logger.debug("12订票差额补扣");
		// 2订票差额补扣
		if (orderInfo.getStep().equals("11")
				&& orderInfo.getPurchaseAmount() != oldPurchaseAmount) {
			if (orderInfo.getType().equals("0")) {
				updateOrderInfoStep2(orderInfo);
			} else if (orderInfo.getType().equals("1")) {
				updateOrderInfoStep21(orderInfo, oldPurchaseAmount);
			}
		}

	}

	/**
	 * 0 提交订单修改
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateOrderInfoStep0(OrderInfo orderInfo, OrderInfo newOrderInfo) {
		String opt = "修改";
		if (newOrderInfo.getPurchaseAmount() == 0) {
			opt = "取消";
		}
		orderInfo.setRemarks(orderInfo.getPurchaseAmount() + "");
		String remark = "";
		if (newOrderInfo.getPurchaseAmount() > 0) {
			if (orderInfo.getPurchaseAmount() != newOrderInfo
					.getPurchaseAmount()) {
				remark += "，修改订票数量为：" + newOrderInfo.getPurchaseAmount();
			}
			if (!orderInfo.getCustomerMobile().equals(
					newOrderInfo.getCustomerMobile())) {
				remark += "，修改为取票人手机为：" + newOrderInfo.getCustomerMobile();
			}
			if (!orderInfo.getCustomerId().equals(newOrderInfo.getCustomerId())) {
				remark += "，修改为取票人身份证号码为：" + newOrderInfo.getCustomerId();
			}
		}

		orderInfo.setCustomerMobile(newOrderInfo.getCustomerMobile());
		// orderInfo.setCustomerName(newOrderInfo.getCustomerName());
		orderInfo.setContactMobile(newOrderInfo.getCustomerMobile());
		orderInfo.setPurchaseAmount(newOrderInfo.getPurchaseAmount());
		orderInfo.setCustomerId(newOrderInfo.getCustomerId());

		orderInfo.setStep("10");
		orderInfoDao.save(orderInfo);

		this.addOrderLog(opt + "订单提交成功" + remark, orderInfo);
	}

	/**
	 * 1远端确认
	 *
	 * @return
	 * @throws RemoteException
	 */
	@Transactional(readOnly = false)
	public void updateOrderInfoStep1(OrderInfo orderInfo) {

		if (orderInfo.getSupplier().getCheckTerminal().equals("9")) {
			orderInfo.setStatus("-2");
			orderInfoDao.save(orderInfo);
			this.addOrderLog("订单待系统确认，订票数量：" + orderInfo.getPurchaseAmount(),
					orderInfo);
			SmsService smsService = new SmsService();
			String content = "订单" + orderInfo.getOrderId() + "有改退，请您处理确认！";
			smsService.sendSms(Global.getConfig("sms_ac"), content);
			return;
		}
		try {
			boolean ok = false;
			TerminalService service = TerminalFactory
					.createTerminalService(orderInfo.getSupplier()
							.getCheckTerminal());
			ok = service.updateOrder(orderInfo);

			if (ok) {
				if (orderInfo.getPurchaseAmount() == 0) {
					orderInfo.setStatus("31");
				}
				orderInfo.setStep("11");
				// orderInfoDao.save(orderInfo);
			}
			this.addOrderLog("订单远端确认成功", orderInfo);
		} catch (Exception e) {
			this.addOrderLog("订单远端确认失败，失败原因：" + e.getMessage(), orderInfo);

		}

	}

	/**
	 * 2订票差额补扣
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateOrderInfoStep2(OrderInfo orderInfo) {

		// 订单差价
		Double cancelPrice = 0.0;
		if (orderInfo.getPurchaseAmount() == 0) {
			cancelPrice = Double.valueOf(Global.getConfig("order_cancel_fee"));
		}

		Double difPrice = DoubleUtils.add(
				DoubleUtils.subtract(
						DoubleUtils.mul(orderInfo.getPurchasePrice(),
								orderInfo.getPurchaseAmount()),
						orderInfo.getTotalpay()), cancelPrice);

		// 创建余额历史
		BalanceHis balanceHis = new BalanceHis();
		User user = systemService.getUserByLoginName(orderInfo.getDistributor()
				.getUsername());
		balanceHis.setUser(user);
		balanceHis.setType("2");
		balanceHis.setBegin(orderInfo.getDistributor().getBalance());
		balanceHis.setAmount(-difPrice);
		orderInfo.getDistributor().setBalance(
				DoubleUtils.subtract(orderInfo.getDistributor().getBalance(),
						difPrice));
		balanceHis.setEnd(orderInfo.getDistributor().getBalance());
		balanceHis.setStatus("0");
		balanceHis.setReason("订单号：" + orderInfo.getOrderId());
		if (orderInfo.getPurchaseAmount() == 0) {
			balanceHis.setRemarks("订单取消");
		} else {
			balanceHis.setRemarks("订单修改");
		}

		orderInfo.setTotalpay(DoubleUtils.mul(orderInfo.getPurchasePrice(),
				orderInfo.getPurchaseAmount()));

		balanceHisDao.save(balanceHis);

		// orderInfoDao.save(orderInfo);
		orderInfo.setStep("12");
		this.addOrderLog("订单改退差额补扣成功", orderInfo);
	}

	/**
	 * 2订票差额补扣 直通订票
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateOrderInfoStep21(OrderInfo orderInfo, int oldPurchaseAmount) {

		// 订单差价
		Double orderOptPrice = DoubleUtils.mul(
				Double.valueOf(Global.getConfig("order_opt_fee")),
				orderInfo.getPurchaseAmount());
		Double totalPay = DoubleUtils.add(
				DoubleUtils.mul(orderInfo.getPurchasePrice(),
						orderInfo.getPurchaseAmount()), orderOptPrice);

		Double difPrice = DoubleUtils.mul(orderInfo.getPurchasePrice(),
				DoubleUtils.subtract(orderInfo.getPurchaseAmount(),
						oldPurchaseAmount));

		DirectBalanceHis directBalanceHis = new DirectBalanceHis();
		directBalanceHis.setDirect(orderInfo.getDirect());
		directBalanceHis.setType("1");
		directBalanceHis.setBegin(orderInfo.getDirect().getBalance());
		directBalanceHis.setAmount(-difPrice);
		orderInfo.getDirect().setBalance(
				DoubleUtils.subtract(orderInfo.getDirect().getBalance(),
						difPrice));
		directBalanceHis.setEnd(orderInfo.getDirect().getBalance());
		directBalanceHis.setStatus("0");

		directBalanceHis.setReason("订单号：" + orderInfo.getOrderId());
		if (orderInfo.getPurchaseAmount() == 0) {
			directBalanceHis.setRemarks("订单取消");
		} else {
			directBalanceHis.setRemarks("订单修改");
		}
		orderInfo.setTotalpay(totalPay);

		directBalanceHisDao.save(directBalanceHis);

		Double orderOptFee = DoubleUtils.mul(Double.valueOf(Global
				.getConfig("order_opt_fee")), DoubleUtils.subtract(
				orderInfo.getPurchaseAmount(), oldPurchaseAmount));

		BalanceHis balanceHis = new BalanceHis();
		User user = systemService.getUserByLoginName(orderInfo.getDistributor()
				.getUsername());
		balanceHis.setUser(user);
		balanceHis.setType("2");
		balanceHis.setBegin(orderInfo.getDistributor().getBalance());
		balanceHis.setAmount(-orderOptFee);
		orderInfo.getDistributor().setBalance(
				orderInfo.getDistributor().getBalance() - orderOptFee);
		balanceHis.setEnd(orderInfo.getDistributor().getBalance());
		balanceHis.setStatus("0");

		balanceHis.setReason("订单号：" + orderInfo.getOrderId());
		balanceHis.setRemarks("直通退改手续费");
		balanceHisDao.save(balanceHis);

		// orderInfoDao.save(orderInfo);

		orderInfo.setStep("12");
		this.addOrderLog("订单改退差额补扣成功", orderInfo);
	}

	@Transactional(readOnly = false)
	public boolean checkOrder(OrderInfo orderInfo) {
		try {
			TerminalService service = TerminalFactory
					.createTerminalService(orderInfo.getSupplier()
							.getCheckTerminal());
			if (service.cancelOrder(orderInfo)) {
				orderInfo.setStatus("1");
				this.addOrderLog(UserUtils.getUser().getName() + "平台验票成功",
						orderInfo);
				updateFromPft(orderInfo);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Transactional(readOnly = false)
	public void addOrderLog(String log, OrderInfo order) {
		// order.getOrderLogList().add(new OrderLog(log, order));
		orderLogService.save(new OrderLog(log, order));
	}

	/**
	 * 统计订单
	 */
	public List<Object> statOrder(String type, String beginTime, String endTime) {
		String sqlString = "SELECT count(1),sum(totalPay) from biz_order  where type='"
				+ type
				+ "' and order_date>='"
				+ beginTime
				+ "' and order_date<'" + endTime + "'";
		List<Object> list = orderInfoDao.findBySql(sqlString);
		List<Object> resultsList = Lists.newArrayList();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			resultsList.add(objects[0]);
			if (objects[1] == null) {
				resultsList.add(0.0);
			} else {
				resultsList.add(objects[1]);
			}

			return resultsList;
		}
		return resultsList;
	}

	@Transactional(readOnly = false)
	public void dealOrder(OrderInfo orderInfo) {
		if (!orderInfo.getStatus().equals("-2")) {
			return;
		}

		if (orderInfo.getStep().equals("29")) {
			// 提交订单

			orderInfo.setStatus("0");
			orderInfo.setStep("2");

			this.addOrderLog("订单系统确认成功，确认数量：" + orderInfo.getPurchaseAmount(),
					orderInfo);

			// 发送信息 todo
			saveOrderInfoStep3(orderInfo);

		} else if (orderInfo.getStep().equals("10")) {
			// 修改取消订单

			if (orderInfo.getPurchaseAmount() > 0) {
				orderInfo.setStatus("0");

			} else {
				orderInfo.setStatus("31");
			}

			// 2订票差额补扣
			if (orderInfo.getType().equals("0")) {
				updateOrderInfoStep2(orderInfo);
			} else if (orderInfo.getType().equals("1")) {
				// 没有处理直通订单
				// updateOrderInfoStep21(orderInfo);
			}

			this.addOrderLog("订单系统确认成功，确认数量：" + orderInfo.getPurchaseAmount(),
					orderInfo);
		}

	}

}
