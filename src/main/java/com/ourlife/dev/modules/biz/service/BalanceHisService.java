package com.ourlife.dev.modules.biz.service;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.alipay.util.UtilDate;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.DoubleUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.BalanceHisDao;
import com.ourlife.dev.modules.biz.entity.BalanceHis;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.sys.dao.UserDao;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import org.dom4j.DocumentException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 余额历史Service
 *
 * @author ourlife
 * @version 2014-05-25
 */
@Component
@Transactional(readOnly = true)
public class BalanceHisService extends BaseService {

	@Autowired
	private BalanceHisDao balanceHisDao;

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private SupplierService supplierService;

	public BalanceHis get(Long id) {
		balanceHisDao.clear();
		return balanceHisDao.findOne(id);
	}

	public Page<BalanceHis> find(Page<BalanceHis> page, BalanceHis balanceHis) {
		DetachedCriteria dc = balanceHisDao.createDetachedCriteria();
		if (balanceHis.getUser() != null) {
			dc.createAlias("user", "user");
			dc.add(Restrictions.eq("user.id", balanceHis.getUser().getId()));
		}

		if (StringUtils.isNotBlank(balanceHis.getType())) {
			dc.add(Restrictions.eq("type", balanceHis.getType()));
		}
		if (StringUtils.isNotBlank(balanceHis.getStatus())) {
			dc.add(Restrictions.eq("status", balanceHis.getStatus()));
		}

		dc.add(Restrictions.eq(BalanceHis.DEL_FLAG, BalanceHis.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return balanceHisDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(BalanceHis balanceHis) {

		if (balanceHis.getStatus().equals("0")) {
			User user = UserUtils.getUser();
			Distributor distributor = distributorService.getUserByUsername(user
					.getLoginName());
			distributor.setBalance(DoubleUtils.add(distributor.getBalance(),
					balanceHis.getAmount()));
			distributorService.save(distributor);
		}
		balanceHisDao.save(balanceHis);

	}

	@Resource
	UserDao userDao;
	/**
	 * 给转账用户添加余额
	 * @param userId
	 * @param amount
	 */
	@Transactional(readOnly = false)
	public void addBalanceToDistributor(Long userId, Double amount) {
		User someOne = userDao.findOne(userId);
		Distributor distributor = distributorService.getUserByUsername(someOne.getLoginName());
		Double begin = distributor.getBalance();
		BalanceHis bh = BalanceHis.withTransformAccount(begin, amount);
		bh.setUser(someOne);
		distributor.setBalance(DoubleUtils.add(distributor.getBalance(), amount));
		balanceHisDao.save(bh);
		distributorService.save(distributor);

	}

	@Transactional(readOnly = false)
	public void save(BalanceHis balanceHis, Distributor distributor) {
		balanceHis.setBegin(distributor.getBalance());
		balanceHis.setEnd(DoubleUtils.add(distributor.getBalance(),
				balanceHis.getAmount()));
		balanceHis.setStatus("0");
		balanceHisDao.save(balanceHis);

		distributor.setBalance(distributor.getBalance()
				+ balanceHis.getAmount());
		distributorService.save(distributor);

	}

	@Transactional(readOnly = false)
	public void save(BalanceHis balanceHis, Supplier supplier) {
		balanceHis.setBegin(supplier.getBalance());
		balanceHis.setEnd(DoubleUtils.add(supplier.getBalance(),
				balanceHis.getAmount()));
		balanceHis.setStatus("0");
		balanceHisDao.save(balanceHis);

		supplier.setBalance(DoubleUtils.add(supplier.getBalance(),
				balanceHis.getAmount()));
		supplierService.save(supplier);

	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		balanceHisDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public String recharge(BalanceHis balanceHis) throws MalformedURLException,
			DocumentException, IOException {
		User user = UserUtils.getUser();
		Distributor distributor = distributorService.getUserByUsername(user
				.getLoginName());
		balanceHis.setUser(user);
		balanceHis.setType("0");
		balanceHis.setBegin(distributor.getBalance());
		balanceHis.setEnd(distributor.getBalance());
		balanceHis.setStatus("-1");
		balanceHis.setReason("在线充值(" + Global.getConfig("allinpay_fee") + "%)");
		balanceHis.setRemarks("易往门票分享系统充值—" + distributor.getName() + "-支付宝充值");
		balanceHisDao.save(balanceHis);

		return alipayRequest(distributor, balanceHis);
	}

	private String alipayRequest(Distributor distributor, BalanceHis balanceHis)
			throws MalformedURLException, DocumentException, IOException {
		// 支付类型
		String payment_type = "1";
		// 必填，不能修改
		// 服务器异步通知页面路径
		String notify_url = Global.getConfig("allinpay_notify_url");
		// 需http://格式的完整路径，不能加?id=123这类自定义参数

		// 页面跳转同步通知页面路径
		String return_url = Global.getConfig("allinpay_return_url");
		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		// 卖家支付宝帐户
		String seller_email = Global.getConfig("allinpay_seller_email");
		;
		// 必填

		// 商户订单号

		String out_trade_no = UtilDate.getOrderNum() + UtilDate.getThree()
				+ "-" + balanceHis.getId();
		// 商户网站订单系统中唯一订单号，必填

		// 订单名称
		String subject = balanceHis.getRemarks();
		// 必填

		// 付款金额
		double fee = DoubleUtils.add(
				balanceHis.getAmount(),
				balanceHis.getAmount()
						* Integer.valueOf(Global.getConfig("allinpay_fee"))
						/ 100);

		String total_fee = String.format("%.2f", fee) + "";

		// 必填

		// 订单描述

		String body = balanceHis.getRemarks();
		// 商品展示地址
		// String show_url = new String("");
		// 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

		// 防钓鱼时间戳
		String anti_phishing_key = AlipaySubmit.query_timestamp();
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		// String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		// sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		// sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		// sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}

}
