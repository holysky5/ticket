package com.ourlife.dev.modules.biz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.ProductHistory;
import com.ourlife.dev.modules.biz.service.ProductHistoryService;

/**
 * 余额历史Controller
 * 
 * @author ourlife
 * @version 2014-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/productHistory")
public class ProductHistoryController extends BaseController {

	@Autowired
	private ProductHistoryService productHistoryService;

	@ModelAttribute
	public ProductHistory get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return productHistoryService.get(id);
		} else {
			return new ProductHistory();
		}
	}

	@RequiresPermissions("biz:product:view")
	@RequestMapping(value = { "list/{productId}" })
	public String list(@PathVariable Long productId,
			ProductHistory productHistory, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		productHistory.setProductId(productId);
		Page<ProductHistory> page = productHistoryService.find(
				new Page<ProductHistory>(request, response), productHistory);
		model.addAttribute("page", page);
		return "modules/biz/productHistoryList";
	}
}
