package com.ourlife.dev.modules.biz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Direct;
import com.ourlife.dev.modules.biz.entity.DirectPrice;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.Product;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.DirectService;
import com.ourlife.dev.modules.biz.service.DistributorService;
import com.ourlife.dev.modules.biz.service.ProductService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 直通景区Controller
 * 
 * @author ourlife
 * @version 2014-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/direct")
public class DirectController extends BaseController {

	@Autowired
	private DirectService directService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private DistributorService distributorService;

	@ModelAttribute
	public Direct get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return directService.get(id);
		} else {
			return new Direct();
		}
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = { "list", "" })
	public String listSupplier(Direct direct, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.getUserType().equals("3")) {
			return "error/403";
		}
		if (!user.isAdmin()) {
			direct.setCreateBy(user);
		}
		Distributor distributor = distributorService.getUserByUsername(user
				.getLoginName());
		direct.setDistributor(distributor);

		direct.setRemarks("特殊处理");
		Page<Direct> page = directService.find(new Page<Direct>(request,
				response, 15), direct);
		model.addAttribute("page", page);
		return "modules/biz/directList";
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = { "list/distributor" })
	public String listDistributor(Direct direct, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.getUserType().equals("4")) {
			return "error/403";
		}
		if (!user.isAdmin()) {
			direct.setCreateBy(user);
		}
		Supplier supplier = supplierService.getSupplierByNo(user.getLoginName()
				.trim());
		direct.setSupplier(supplier);
		Page<Direct> page = directService.find(new Page<Direct>(request,
				response), direct);
		model.addAttribute("page", page);
		return "modules/biz/directDistributorList";
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = "form")
	public String form(Direct direct, Model model) {
		model.addAttribute("direct", direct);
		return "modules/biz/directForm";
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = "supplier/form")
	public String formSupplier(Direct direct, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		if (direct.getStatus() == null) {
			direct.setStatus("0");
		}

		User user = UserUtils.getUser();
		Product product = new Product();
		Supplier supplier = supplierService.getSupplierByNo(user.getLoginName()
				.trim());
		product.setScenic(supplier);
		product.setStatus("0");
		Page<Product> page = productService.find(new Page<Product>(request,
				response, -1), product);
		List<Product> products = page.getList();

		List<DirectPrice> prices = Lists.newArrayList();
		if (direct.getId() == null) {
			for (int i = 0; i < products.size(); i++) {
				DirectPrice price = new DirectPrice();
				price.setDirect(direct);
				price.setProduct(products.get(i));
				prices.add(price);
			}
		} else {
			for (int i = 0; i < products.size(); i++) {
				DirectPrice price = directService.getPriceByDirectAndProduct(
						direct, products.get(i));
				if (price == null) {
					price = new DirectPrice();
					price.setDirect(direct);
					price.setProduct(products.get(i));
				}
				prices.add(price);
			}
		}

		model.addAttribute("prices", prices);

		model.addAttribute("supplier", supplier);

		model.addAttribute("direct", direct);

		return "modules/biz/directSupplierForm";
	}

	@RequiresPermissions("biz:direct:edit")
	@RequestMapping(value = "save")
	public String save(Direct direct, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, direct)) {
			return form(direct, model);
		}

		User user = UserUtils.getUser();
		Supplier supplier = supplierService.getSupplierByNo(user.getLoginName()
				.trim());
		direct.setSupplier(supplier);

		String distributorId = request.getParameter("distributorId");
		Distributor distributor = distributorService.get(Long
				.valueOf(distributorId));
		direct.setDistributor(distributor);

		String[] priceIds = request.getParameterValues("priceId");
		String[] productIds = request.getParameterValues("productId");
		String[] distributorPrices = request
				.getParameterValues("distributorPrice");
		List<DirectPrice> prices = Lists.newArrayList();
		for (int i = 0; i < distributorPrices.length; i++) {
			if (priceIds[i].equals("")) {
				if (StringUtils.isBlank(distributorPrices[i])) {
					continue;
				}
				DirectPrice price = new DirectPrice();
				price.setDirect(direct);
				price.setProduct(productService.get(Long.valueOf(productIds[i])));
				price.setPrice(Double.valueOf(distributorPrices[i]));
				prices.add(price);
			} else {
				DirectPrice price = directService
						.getPriceByDirectAndProduct(direct,
								productService.get(Long.valueOf(productIds[i])));
				if (StringUtils.isBlank(distributorPrices[i])) {
					directService.deletePrice(price);
					continue;
				}

				price.setPrice(Double.valueOf(distributorPrices[i]));

				prices.add(price);
			}

		}
		direct.setDirectPriceList(prices);
		directService.save(direct);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/direct/list/distributor?repage";
	}

	@RequiresPermissions("biz:direct:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		Direct direct = directService.get(id);
		if (direct.getSupplier().getNo()
				.equals(UserUtils.getUser().getLoginName())
				&& direct.getBalance() == 0.0 && direct.getStatus().equals("1")) {
			directService.delete(id);
			addMessage(redirectAttributes, "删除成功");
		} else {
			addMessage(redirectAttributes, "删除失败，您当前没有权限删除");
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/direct/list/distributor?repage";
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = "product")
	public String product(Direct direct, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DirectPrice> priceList = Lists.newArrayList();
		List<DirectPrice> directPriceList = direct.getDirectPriceList();
		for (DirectPrice price : directPriceList) {
			if (price.getProduct().getStatus().equals("0")) {
				priceList.add(price);
			}
		}

		model.addAttribute("directPriceList", priceList);
		model.addAttribute("direct", direct);
		return "modules/biz/directProductList";
	}
}
