package com.ourlife.dev.modules.biz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 供应商信息Controller
 * 
 * @author ourlife
 * @version 2014-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public Supplier get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return supplierService.get(id);
		} else {
			return new Supplier();
		}
	}

	@RequiresPermissions("biz:supplier:view")
	@RequestMapping(value = { "list", "" })
	public String list(Supplier supplier, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			supplier.setCreateBy(user);
		}
		Page<Supplier> page = supplierService.find(new Page<Supplier>(request,
				response), supplier);
		model.addAttribute("page", page);
		return "modules/biz/supplierList";
	}

	@RequiresPermissions("biz:supplier:view")
	@RequestMapping(value = "form")
	public String form(Supplier supplier, Model model) {
		if (StringUtils.isBlank(supplier.getStatus())) {
			supplier.setStatus("0");
		}
		model.addAttribute("supplier", supplier);
		return "modules/biz/supplierForm";
	}

	@RequestMapping(value = "info")
	public String info(Model model) {
		Supplier supplier = supplierService.getSupplierByNo(UserUtils.getUser()
				.getLoginName());
		model.addAttribute("supplier", supplier);
		return "modules/biz/supplierFormInfo";
	}

	@RequiresPermissions("biz:supplier:view")
	@RequestMapping(value = "view")
	public String view(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		return "modules/biz/supplierView";
	}

	@RequiresPermissions("biz:supplier:edit")
	@RequestMapping(value = "save")
	public String save(Supplier supplier, String newPassword, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)) {
			return form(supplier, model);
		}
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			supplier.setPassword(SystemService.entryptPassword(newPassword));
		}

		supplierService.save(supplier);
		addMessage(redirectAttributes, "保存供应商信息'" + supplier.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/biz/supplier/?repage";
	}

	@RequestMapping(value = "save/info")
	public String saveInfo(Supplier supplier, String newPassword, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)) {
			return form(supplier, model);
		}
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			supplier.setPassword(SystemService.entryptPassword(newPassword));
		}

		supplierService.save(supplier);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/supplier/info/?repage";
	}

	@RequiresPermissions("biz:supplier:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		Supplier supplier = supplierService.get(id);
		if (supplier.getBalance() == 0) {
			supplierService.delete(id);
			addMessage(redirectAttributes, "删除供应商信息成功");
		} else {
			addMessage(redirectAttributes, "删除供应商信息失败，未与该供应商结算完成");
		}

		return "redirect:" + Global.getAdminPath() + "/biz/supplier/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkNo")
	public String checkNo(String oldNo, String no) {
		if (no != null && no.equals(oldNo)) {
			return "true";
		} else if (no != null && systemService.getUserByLoginName(no) == null
				&& supplierService.getSupplierByNo(no) == null) {
			return "true";
		}
		return "false";
	}

	@RequiresPermissions("biz:supplier:view")
	@RequestMapping(value = { "list/valid" })
	public String listValid(Supplier supplier, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		supplier.setStatus("0");
		Page<Supplier> page = supplierService.findOrderList(new Page<Supplier>(
				request, response, 15), supplier);
		model.addAttribute("page", page);
		return "modules/biz/supplierValidList";
	}

	@RequiresPermissions("biz:supplier:view")
	@RequestMapping(value = { "list/product" })
	public String listProduct(Supplier supplier, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		supplier.setStatus("0");
		String stopTime = request.getParameter("stopTime");
		Page<Supplier> page = null;
		if (StringUtils.isBlank(stopTime)) {
			page = supplierService.findOrderList(new Page<Supplier>(request,
					response), supplier);
		} else {
			page = supplierService.findOrderNotList(new Page<Supplier>(request,
					response), supplier);
		}

		model.addAttribute("stopTime", request.getParameter("stopTime"));
		model.addAttribute("page", page);
		return "modules/biz/supplierProductList";
	}
}
