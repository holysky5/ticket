package com.ourlife.dev.modules.biz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Product;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.ProductService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 门票详情Controller
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;

	@ModelAttribute
	public Product get(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String no) {
		if (id != null) {
			return productService.get(id);
		} else if (StringUtils.isNotEmpty(no) && no.length() >= 8) {
			return productService.getByNo(no);
		} else {
			return new Product();
		}
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = { "list", "" })
	public String list(Product product, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			product.setCreateBy(user);
		}

		// get supplier info
		Supplier supplier = null;
		try {
			Long supplierid = Long.valueOf(request.getParameter("supplierid"));
			supplier = supplierService.get(supplierid);
			model.addAttribute("supplierid", supplierid);
		} catch (Exception e) {
			supplier = supplierService.getSupplierByNo(user.getLoginName()
					.trim());
		}

		product.setScenic(supplier);
		Page<Product> page = productService.find(new Page<Product>(request,
				response), product);
		model.addAttribute("page", page);
		return "modules/biz/productList";
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = { "listProduct4Admin/{supplierId}" })
	public String listProductForAdmin(@PathVariable Long supplierId,
			Product product, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Supplier supplier = supplierService.get(supplierId);
		if (supplier == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		product.setScenic(supplier);
		Page<Product> page = productService.findProductForAdmit(
				new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		model.addAttribute("supplier", supplier);
		return "modules/biz/adminProductList";
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = { "listProduct4Distributor/{supplierId}" })
	public String listProduct4Distributor(Product product,
			@PathVariable Long supplierId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Supplier supplier = supplierService.get(supplierId);
		if (supplier == null) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		product.setScenic(supplier);
		Page<Product> page = productService.findProductForDistributor(
				new Page<Product>(request, response, -1), product);
		model.addAttribute("page", page);
		model.addAttribute("supplier", supplier);
		return "modules/biz/proList4Distributor";
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = { "listWait4AuditProduct" })
	public String listWaitForAuditProduct(Product product,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			product.setCreateBy(user);
		}
		// get supplier info
		Supplier supplier = supplierService.getSupplierByNo(user.getLoginName()
				.trim());
		product.setScenic(supplier);
		Page<Product> page = productService.findByAuditFlag(new Page<Product>(
				request, response), product);
		model.addAttribute("page", page);
		return "modules/biz/adminAuditProductList";
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		if (product.getStatus() == null) {
			product.setStatus("0");
		}
		if (!productService.isHasOptPermission(product)) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		model.addAttribute("product", product);
		return "modules/biz/productForm";
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = "auditForm")
	public String auditForm(Product product, Model model) {
		model.addAttribute("product", product);
		return "modules/biz/productAutitForm";
	}

	@RequiresPermissions("biz:product:edit")
	@RequestMapping(value = "save")
	public String save(Product product, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		Supplier supplier = null;
		try {
			Long supplierid = Long.valueOf(request.getParameter("supplierid"));
			supplier = supplierService.get(supplierid);
			model.addAttribute("supplierid", supplierid);
		} catch (Exception e) {

			supplier = supplierService.getSupplierByNo(user.getLoginName()
					.trim());
		}
		product.setScenic(supplier);
		if (!beanValidator(model, product)) {
			return form(product, model);
		}
		// 预订时间处理
		if (product.getEffectTime().equals("1")) {
			product.setEffectTime(product.getEffectTime()
					+ ","
					+ StringUtils.join(
							request.getParameterValues("effectTime1"), ','));

		} else if (product.getEffectTime().equals("2")) {
			product.setEffectTime(product.getEffectTime()
					+ ","
					+ StringUtils.join(
							request.getParameterValues("effectTime2"), ','));
		}
		if (null != product.getId()) {
			Product originalPro = productService.get(product.getId());
			if (!product.getOriginalPrice().equals(
					originalPro.getOriginalPrice())
					|| !product.getPurchasePrice().equals(
							originalPro.getPurchasePrice())
					|| !product.getRecommendPrice().equals(
							originalPro.getRecommendPrice())) {
				// once the price changed, it will be audit by admin
				product.setAuditFlag("0");
				product.setRemarks(user.getLoginName() + "更新门票基本信息和相关价格成功！");
			} else {
				product.setAuditFlag(originalPro.getAuditFlag());
				product.setPlatformPrice(originalPro.getPlatformPrice());
				product.setRemarks(user.getLoginName() + "更新门票基本信息成功！");
			}
		} else {
			// 新门票默认未审核
			product.setAuditFlag("0");
			product.setRemarks("新门票被" + user.getLoginName() + "创建成功！");
		}
		product.setPlatformPrice(product.getRecommendPrice());
		product.setNotice(StringEscapeUtils.unescapeHtml4(product.getNotice()));
		product.setIntroduction(StringEscapeUtils.unescapeHtml4(product
				.getIntroduction()));
		productService.save(product);
		addMessage(redirectAttributes, "保存门票详情成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/product/?supplierid=" + supplier.getId();
	}

	@RequiresPermissions("biz:product:edit")
	@RequestMapping(value = "audit")
	public String audit(Product product, Model model,
			RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		Product pro = productService.get(product.getId());
		pro.setAuditFlag(product.getAuditFlag());
		pro.setPlatformPrice(product.getPlatformPrice());
		pro.setUuPid(product.getUuPid());
		if (null != user) {
			pro.setRemarks("管理员" + user.getLoginName() + "审核通过！");
		} else {
			pro.setRemarks("管理员审核通过！");
		}
		productService.save(pro);
		addMessage(redirectAttributes, "门票审核成功");
		String stopTime = "0";
		if (product.getAuditFlag() != null
				&& product.getAuditFlag().equals("0")) {
			stopTime = "1";
		}
		return "redirect:" + Global.getAdminPath()
				+ "/biz/product/listProduct4Admin/"
				+ product.getScenic().getId() + "?stopTime=" + stopTime;
	}

	@RequiresPermissions("biz:product:edit")
	@RequestMapping(value = "delete")
	public String delete(String no, RedirectAttributes redirectAttributes) {
		if (!productService.isHasOptPermission(productService.getByNo(no))) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}
		productService.delete(no);
		addMessage(redirectAttributes, "删除门票详情成功");
		return "redirect:" + Global.getAdminPath() + "/biz/product/?repage";
	}

}
