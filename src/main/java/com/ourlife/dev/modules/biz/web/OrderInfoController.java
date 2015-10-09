package com.ourlife.dev.modules.biz.web;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.DoubleUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.utils.excel.ExportExcel;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.*;
import com.ourlife.dev.modules.biz.service.*;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import com.ourlife.dev.terminal.TerminalFactory;
import com.ourlife.dev.terminal.TerminalService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单信息Controller
 *
 * @author ourlife
 * @version 2014-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/orderInfo")
public class OrderInfoController extends BaseController {

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductHistoryService productHistoryService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private OrderLogService orderLogService;

	@ModelAttribute
	public OrderInfo get(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String orderid) {
		if (id != null) {
			return orderInfoService.get(id);
		} else if (orderid != null && orderid.length() > 6) {
			return orderInfoService.getByOrderId(orderid);
		} else {
			return new OrderInfo();
		}
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = { "list/{type}" })
	public String list(@PathVariable String type, OrderInfo orderInfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (StringUtils.isNotBlank(request
				.getParameter("orderInfo.customerName"))) {
			orderInfo.setCustomerName(request
					.getParameter("orderInfo.customerName"));
		}
		if (StringUtils.isNotBlank(request
				.getParameter("orderInfo.customerMobile"))) {
			orderInfo.setCustomerMobile(request
					.getParameter("orderInfo.customerMobile"));
		}
		if (StringUtils.isNotBlank(request.getParameter("orderInfo.useDate"))) {
			orderInfo.setUseDate(request.getParameter("orderInfo.useDate"));
		}
		if (orderInfo.getType() == null) {
			orderInfo.setType(type);
		}

		if (type.equals("todo")) {
			orderInfo.setType("0");
			orderInfo.setStatus("-2");
		}

		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")) {
			if (orderInfo.getDistributor() == null) {
				orderInfo.setDistributor(new Distributor());
			}
			Distributor distributor = distributorService.getUserByUsername(user
					.getLoginName());

			orderInfo.getDistributor().setId(distributor.getId());
		} else if (user.getUserType().equals("4")) {
			if (orderInfo.getSupplier() == null) {
				orderInfo.setSupplier(new Supplier());
			}
			Supplier supplier = supplierService.getSupplierByNo(user
					.getLoginName());
			orderInfo.getSupplier().setId(supplier.getId());
		}

		Page<OrderInfo> page = orderInfoService.find(new Page<OrderInfo>(
				request, response), orderInfo);

		model.addAttribute("orderCancelFee",
				Global.getConfig("order_cancel_fee"));
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("query", request.getParameter("query"));
		return "modules/biz/orderInfoList";
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = "export/{type}", method = RequestMethod.POST)
	public String exportFile(@PathVariable String type, OrderInfo orderInfo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		if (orderInfo.getType() == null) {
			orderInfo.setType(type);
		}
		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")) {
			if (orderInfo.getDistributor() == null) {
				orderInfo.setDistributor(new Distributor());
			}
			Distributor distributor = distributorService.getUserByUsername(user
					.getLoginName());

			orderInfo.getDistributor().setId(distributor.getId());
		} else if (user.getUserType().equals("4")) {
			if (orderInfo.getSupplier() == null) {
				orderInfo.setSupplier(new Supplier());
			}
			Supplier supplier = supplierService.getSupplierByNo(user
					.getLoginName());
			orderInfo.getSupplier().setId(supplier.getId());
		}
		try {
			String fileName = "订单数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<OrderInfo> page = orderInfoService.find(new Page<OrderInfo>(
					request, response, -1), orderInfo);
			new ExportExcel("订单数据", OrderInfo.class)
					.setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订单数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/biz/orderInfo/list/"
				+ type;
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = "result")
	public String result(OrderInfo orderInfo, Model model) {
		ProductHistory productHis = orderInfo.getProHistory();

		model.addAttribute("product", productHis);
		model.addAttribute("supplier", productHis.getScenic());

		OrderLog orderLog = new OrderLog();
		orderLog.setOrderInfo(orderInfo);
		orderInfo.setOrderLogList(orderLogService.find(orderLog));
		model.addAttribute("orderInfo", orderInfo);
		return "modules/biz/orderInfoResult";
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = "prepareOrder")
	public String prepareOrder(OrderInfo orderInfo, Model model,
			@RequestParam(required = true) String productNo) {

		// get product info
		Product product = productService.getByNo(productNo);
		ProductHistory productHis = productHistoryService.getLastValid(product
				.getId());
		Supplier supplier = product.getScenic();

		User user = UserUtils.getUser();
		Distributor distributor = distributorService.getUserByUsername(user
				.getLoginName());

		model.addAttribute("product", productHis);
		model.addAttribute("supplier", supplier);
		model.addAttribute("distributor", distributor);
		model.addAttribute("orderInfo", orderInfo);

		String minday = "";
		try {
			String[] times = productHis.getEffectTime().split(",");
			if (times[0].equals("0")) {
				minday = DateUtils.getDate();
			} else if (times[0].equals("1")) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[2]));
				calendar.set(Calendar.MINUTE, Integer.valueOf(times[3]));
				if (new Date().after(calendar.getTime())) {
					calendar.add(Calendar.DATE, Integer.valueOf(times[1]) + 1);
				} else {
					calendar.add(Calendar.DATE, Integer.valueOf(times[1]));
				}
				minday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
			} else if (times[0].equals("2")) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR, Integer.valueOf(times[1]));
				minday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
			}
		} catch (Exception e) {
			minday = DateUtils.getDate();
		}
		String maxday = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 60);
		maxday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");

		if (StringUtils.isNotBlank(productHis.getBeginTime())
				&& StringUtils.isNotBlank(productHis.getEndTime())) {
			String beginDate = DateUtils.getYear() + "-"
					+ productHis.getBeginTime();
			String endDate = DateUtils.getYear() + "-"
					+ productHis.getEndTime();
			if (productHis.getBeginTime().compareTo(productHis.getEndTime()) > 0) {
				endDate = (Integer.valueOf(DateUtils.getYear()) + 1) + "-"
						+ productHis.getEndTime();
			}
			if (DateUtils.parseDate(beginDate).after(
					DateUtils.parseDate(minday))) {
				minday = beginDate;
			}
			if (DateUtils.parseDate(endDate)
					.before(DateUtils.parseDate(maxday))) {
				maxday = endDate;
			}
		}

		if (DateUtils.parseDate(maxday).before(DateUtils.parseDate(minday))) {
			minday = maxday;
			model.addAttribute("notbegin", true);
		}
		model.addAttribute("minday", minday);
		model.addAttribute("maxday", maxday);

		return "modules/biz/orderInfoForm";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "createOrder")
	public String createOrder(OrderInfo orderInfo, Model model,
			RedirectAttributes redirectAttributes) {

		ProductHistory productHis = productHistoryService.get(orderInfo
				.getProHistory().getId());
		orderInfo.setProHistory(productHis);
		orderInfo.setSupplier(productHis.getScenic());

		User user = UserUtils.getUser();
		Distributor distributor = distributorService.getUserByUsername(user
				.getLoginName());
		orderInfo.setDistributor(distributor);

		orderInfo.setTotalpay(DoubleUtils.mul(orderInfo.getPurchasePrice(),
				orderInfo.getPurchaseAmount()));
		if (distributor.getBalance() < orderInfo.getTotalpay()) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		orderInfo.setType("0");

		orderInfoService.save(orderInfo);
		addMessage(redirectAttributes, "订单提交成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/result?id=" + orderInfo.getId();
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "updateOrder", method = RequestMethod.GET)
	public String updateOrder(OrderInfo orderInfo, Model model) {
		if (orderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		ProductHistory productHis = orderInfo.getProHistory();
		Distributor distributor = orderInfo.getDistributor();
		model.addAttribute("distributor", distributor);
		model.addAttribute("product", productHis);
		model.addAttribute("supplier", orderInfo.getSupplier());
		model.addAttribute("orderInfo", orderInfo);
		return "modules/biz/orderInfoUpdate";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "updateOrder", method = RequestMethod.POST)
	public String updateOrderPost(OrderInfo orderInfo, Model model,
			RedirectAttributes redirectAttributes) {
		if (orderInfo.getOrderId() == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		OrderInfo oldOrderInfo = orderInfoService.getByOrderId(orderInfo
				.getOrderId());
		Double difPrice = DoubleUtils.mul(
				oldOrderInfo.getPurchasePrice(),
				orderInfo.getPurchaseAmount()
						- oldOrderInfo.getPurchaseAmount());
		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")
				&& user.getId() != oldOrderInfo.getCreateBy().getId()) {
			addMessage(redirectAttributes, "订单修改失败! 您没有权限修改该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("4")) {
			addMessage(redirectAttributes, "订单修改失败! 您没有权限修改该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		}

		try {

			TerminalService service = TerminalFactory
					.createTerminalService(oldOrderInfo.getSupplier()
							.getCheckTerminal());
			service.queryOrder(oldOrderInfo);

			if (!oldOrderInfo.getStatus().equals("0")) {
				addMessage(redirectAttributes, "订单修改失败! 订单不是未使用状态，无法修改");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (difPrice > 0) {
			addMessage(redirectAttributes, "订单修改数量只能减少，不能增加，订单修改失败");
		} else {
			if (oldOrderInfo.getPurchaseAmount() != orderInfo
					.getPurchaseAmount()
					|| !oldOrderInfo.getCustomerMobile().equals(
							orderInfo.getCustomerMobile())
					|| !oldOrderInfo.getCustomerId().equals(
							orderInfo.getCustomerId())) {
				orderInfoService.updateOrder(oldOrderInfo, orderInfo);
				addMessage(redirectAttributes, "提交订单修改成功");

			} else {
				addMessage(redirectAttributes, "您提交的信息和原订单一样，订单修改失败");
			}
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "cancelOrder")
	public String cancelOrder(OrderInfo oldOrderInfo, Model model,
			RedirectAttributes redirectAttributes) {
		if (oldOrderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setPurchaseAmount(0);
		orderInfo.setCustomerMobile(oldOrderInfo.getCustomerMobile());

		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")
				&& user.getId() != oldOrderInfo.getCreateBy().getId()) {
			addMessage(redirectAttributes, "订单取消失败! 您没有权限取消该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("4")) {
			addMessage(redirectAttributes, "订单取消失败! 您没有权限取消该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		}

		try {

			TerminalService service = TerminalFactory
					.createTerminalService(oldOrderInfo.getSupplier()
							.getCheckTerminal());
			service.queryOrder(oldOrderInfo);

			if (!oldOrderInfo.getStatus().equals(OrderInfo.STATUS_UNUSED)) {
				addMessage(redirectAttributes, "订单取消失败! 订单不是未使用状态，无法取消");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		orderInfoService.updateOrder(oldOrderInfo, orderInfo);
		addMessage(redirectAttributes, "提交取消订单成功");

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "dealOrder")
	public String dealOrder(OrderInfo orderInfo, Model model,
			RedirectAttributes redirectAttributes) {
		if (orderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		User user = UserUtils.getUser();
		if (!user.getUserType().equals("1")
				|| !orderInfo.getSupplier().getCheckTerminal().equals("9")) {
			addMessage(redirectAttributes, "订单处理失败! 您没有权限处理该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + orderInfo.getId();
		}

		orderInfoService.dealOrder(orderInfo);
		addMessage(redirectAttributes, "订单处理成功");

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/result?id=" + orderInfo.getId();
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		// orderInfoService.delete(id);
		// addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/biz/orderInfo/?repage";
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = "view")
	public String view(OrderInfo orderInfo, Model model) {

		if (orderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		ProductHistory productHis = orderInfo.getProHistory();

		model.addAttribute("product", productHis);
		model.addAttribute("supplier", productHis.getScenic());

		OrderLog orderLog = new OrderLog();
		orderLog.setOrderInfo(orderInfo);
		orderInfo.setOrderLogList(orderLogService.find(orderLog));
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("user", UserUtils.getUser());

		return "modules/biz/orderInfoView";
	}

	@ResponseBody
	@RequestMapping(value = "sendSms")
	public String sendSms(String orderid) {
		return orderInfoService.sendSms(orderid);
	}

	/**
	 *
	 * 票付通回调 验证
	 *
	 * @return success
	 */
	@ResponseBody
	@RequestMapping(value = "pft/return")
	public String pftReturnGet() {
		return "success";
	}

	/**
	 *
	 * 票付通回调
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pft/return", method = RequestMethod.POST)
	public String pftReturnPost(HttpServletRequest request) {
		String poststr = "";
		Map map = request.getParameterMap();
		for (Object key : map.keySet()) {
			poststr = key.toString();
			if (poststr.contains("VerifyCode")) {
				break;
			}
		}
		logger.debug(poststr);
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode data = mapper.readTree(poststr);
			String key = Global.getConfig("pft_ac")
					+ Global.getConfig("pft_pw");
			key = DigestUtils.md5Hex(key);

			String verifyCode = data.path("VerifyCode").getValueAsText();
			String order16U = data.path("Order16U").getValueAsText();
			String actionTime = data.path("ActionTime").getValueAsText();
			String orderCall = data.path("OrderCall").getValueAsText();
			String tnumber = data.path("Tnumber").getValueAsText();
			int orderState = data.path("OrderState").getValueAsInt();

			if (key.equals(verifyCode)) {
				OrderInfo orderInfo = orderInfoService.getByOrderId(orderCall);
				if (!orderInfo.getStatus().equals("0")) {
					return "orderInfo.getStatus()=" + orderInfo.getStatus();
				}
				// 1 已使用 2 已取消 4 已过期 5被终端修改 6 被终端撤销 7 部分使用

				// 0 未使用|1 已使用|2 已过期|3 被取消 31已取消
				// orderInfo.setUuDtime(actionTime);
				if (orderState == 1) {
					orderInfo.setStatus("1");
					orderInfoService.addOrderLog("用户终端取票成功", orderInfo);
				} else if (orderState == 2) {
					orderInfo.setStatus("31");
					orderInfoService.addOrderLog("用户终端取消订单", orderInfo);
				} else if (orderState == 4) {
					orderInfo.setStatus("2");
				} else if (orderState == 5) {
					orderInfo.setStatus("0");
					orderInfo.setPurchaseAmount(Integer.valueOf(tnumber));
				} else if (orderState == 6) {
					orderInfo.setStatus("3");
					orderInfo.setPurchaseAmount(0);
				} else if (orderState == 7) {
					orderInfo.setStatus("1");
					// orderInfo.setPurchaseAmount(Integer.valueOf(tnumber));
				}

				orderInfoService.updateFromPft(orderInfo);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "success";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "checkOrderView")
	public String checkOrderView(OrderInfo oldOrderInfo, Model model,
			RedirectAttributes redirectAttributes) {
		if (oldOrderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		User user = UserUtils.getUser();
		Supplier supplier = supplierService
				.getSupplierByNo(user.getLoginName());
		if (user.getUserType().equals("4")
				&& oldOrderInfo.getSupplier().getId() != supplier.getId()) {
			addMessage(redirectAttributes, "订单验证失败! 您没有权限验证该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("3")) {
			addMessage(redirectAttributes, "订单验证失败! 您没有权限验证该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + oldOrderInfo.getId();
		}

		ProductHistory productHis = oldOrderInfo.getProHistory();
		Distributor distributor = oldOrderInfo.getDistributor();
		model.addAttribute("distributor", distributor);
		model.addAttribute("product", productHis);
		model.addAttribute("supplier", oldOrderInfo.getSupplier());

		return "modules/biz/orderInfoCheck";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "checkOrder")
	public String checkOrder(OrderInfo newOrderInfo, Model model,
			RedirectAttributes redirectAttributes) {
		if (newOrderInfo == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		User user = UserUtils.getUser();
		OrderInfo orderInfo = orderInfoService.getByOrderId(newOrderInfo
				.getOrderId());
		Supplier supplier = supplierService
				.getSupplierByNo(user.getLoginName());
		if (user.getUserType().equals("4")
				&& orderInfo.getSupplier().getId() != supplier.getId()) {
			addMessage(redirectAttributes, "订单验证失败! 您没有权限验证该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + orderInfo.getId();
		} else if (user.getUserType().equals("3")) {
			addMessage(redirectAttributes, "订单验证失败! 您没有权限验证该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/result?id=" + orderInfo.getId();
		}

		if (orderInfo.getPurchaseAmount() != newOrderInfo.getPurchaseAmount()) {
			orderInfoService.addOrderLog("景区验票修改数量", orderInfo);
			newOrderInfo.setCustomerName(orderInfo.getCustomerName());
			newOrderInfo.setCustomerMobile(orderInfo.getContactMobile());
			newOrderInfo.setCustomerId(orderInfo.getCustomerId());
			orderInfoService.updateOrder(orderInfo, newOrderInfo);

		}

		boolean ok = orderInfoService.checkOrder(orderInfo);

		if (ok) {
			addMessage(redirectAttributes, "订单验证成功");
		} else {
			addMessage(redirectAttributes, "订单验证失败!");
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/result?id=" + orderInfo.getId();
	}

	@ResponseBody
	@RequestMapping(value = "queryTodoOrders")
	public List<Integer> queryTodoOrders(HttpServletRequest request,
			HttpServletResponse response) {

		List<Integer> list = Lists.newArrayList();

		List<OrderInfo> todoList = orderInfoService.getOrdersByStatus(OrderInfo.STATUS_OUTDATE);
		if (todoList == null) {
			list.add(0);
		} else {
			list.add(todoList.size());
		}

		Product product = new Product();
		Page<Product> page = productService.findByAuditFlag(new Page<Product>(
				request, response, 1000), product);
		list.add(Long.valueOf(page.getCount()).intValue());
		return list;
	}
}
