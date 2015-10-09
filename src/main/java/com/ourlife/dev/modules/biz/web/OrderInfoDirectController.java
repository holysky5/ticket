package com.ourlife.dev.modules.biz.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.DoubleUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.DirectPrice;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.modules.biz.entity.OrderLog;
import com.ourlife.dev.modules.biz.entity.Product;
import com.ourlife.dev.modules.biz.entity.ProductHistory;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.DirectService;
import com.ourlife.dev.modules.biz.service.DistributorService;
import com.ourlife.dev.modules.biz.service.OrderInfoService;
import com.ourlife.dev.modules.biz.service.OrderLogService;
import com.ourlife.dev.modules.biz.service.ProductHistoryService;
import com.ourlife.dev.modules.biz.service.ProductService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import com.ourlife.dev.terminal.TerminalFactory;
import com.ourlife.dev.terminal.TerminalService;

/**
 * 订单信息Controller
 * 
 * @author ourlife
 * @version 2014-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/orderInfo/direct")
public class OrderInfoDirectController extends BaseController {

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

	@Autowired
	private DirectService directService;

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

	@ModelAttribute
	public void setPirect(HttpServletRequest request, Model model) {
		model.addAttribute("direct", "direct");
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
		return "modules/direct/orderInfoResult";
	}

	@RequiresPermissions("biz:orderInfo:view")
	@RequestMapping(value = "prepareOrder")
	public String prepareOrder(OrderInfo orderInfo, Model model,
			@RequestParam(required = true) String productNo,
			@RequestParam(required = true) long directPriceId) {
		DirectPrice directPrice = directService.getPriceById(directPriceId);
		model.addAttribute("directPrice", directPrice);
		model.addAttribute("orderOptFee", Global.getConfig("order_opt_fee"));

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

		model.addAttribute("minday", minday);

		return "modules/direct/orderInfoForm";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "createOrder")
	public String createOrder(OrderInfo orderInfo, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String directPriceId = request.getParameter("directPriceId");
		DirectPrice directPrice = directService.getPriceById(Long
				.valueOf(directPriceId));
		orderInfo.setDirect(directPrice.getDirect());

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
		if (directPrice.getDirect().getBalance() < orderInfo.getTotalpay()) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		Double orderOptFee = Double.valueOf(Global.getConfig("order_opt_fee"));
		if (distributor.getBalance() < orderOptFee) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		orderInfo.setTotalpay(orderInfo.getTotalpay()
				+ DoubleUtils.mul(orderOptFee, orderInfo.getPurchaseAmount()));

		orderInfo.setType("1");

		orderInfoService.save(orderInfo);

		addMessage(redirectAttributes, "订单提交成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/direct/result?id=" + orderInfo.getId();
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
		model.addAttribute("orderOptFee", Global.getConfig("order_opt_fee"));
		return "modules/direct/orderInfoUpdate";
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
		Double difPrice = DoubleUtils.mul(oldOrderInfo.getPurchasePrice(),
				(orderInfo.getPurchaseAmount() - oldOrderInfo
						.getPurchaseAmount()));
		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")
				&& user.getId() != oldOrderInfo.getCreateBy().getId()) {
			addMessage(redirectAttributes, "订单修改失败! 您没有权限修改该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("4")) {
			addMessage(redirectAttributes, "订单修改失败! 您没有权限修改该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
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
							orderInfo.getCustomerMobile())) {
				orderInfoService.updateOrder(oldOrderInfo, orderInfo);
				addMessage(redirectAttributes, "提交订单修改成功");

			} else {
				addMessage(redirectAttributes, "您提交的信息和原订单一样，订单修改失败");
			}
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
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
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("4")) {
			addMessage(redirectAttributes, "订单取消失败! 您没有权限取消该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
		}

		try {
			TerminalService service = TerminalFactory
					.createTerminalService(oldOrderInfo.getSupplier()
							.getCheckTerminal());
			service.queryOrder(oldOrderInfo);

			if (!oldOrderInfo.getStatus().equals("0")) {
				addMessage(redirectAttributes, "订单取消失败! 订单不是未使用状态，无法取消");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		orderInfoService.updateOrder(oldOrderInfo, orderInfo);
		addMessage(redirectAttributes, "提交取消订单成功");

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		// orderInfoService.delete(id);
		// addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/biz/orderInfo/?repage";
	}

	@RequiresPermissions("biz:orderInfo:edit")
	@RequestMapping(value = "checkOrder")
	public String checkOrder(OrderInfo oldOrderInfo, Model model,
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
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
		} else if (user.getUserType().equals("3")) {
			addMessage(redirectAttributes, "订单验证失败! 您没有权限验证该订单");
			return "redirect:" + Global.getAdminPath()
					+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
		}

		boolean ok = orderInfoService.checkOrder(oldOrderInfo);
		if (ok) {
			addMessage(redirectAttributes, "订单验证成功");
		} else {
			addMessage(redirectAttributes, "订单验证失败!");
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/orderInfo/direct/result?id=" + oldOrderInfo.getId();
	}

}
