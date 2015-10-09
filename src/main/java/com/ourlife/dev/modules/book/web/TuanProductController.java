package com.ourlife.dev.modules.book.web;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.book.entity.TuanProduct;
import com.ourlife.dev.modules.book.service.TuanProductService;
import com.ourlife.dev.modules.sys.entity.Dict;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.DictUtils;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 团购产品Controller
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/book/tuanProduct")
public class TuanProductController extends BaseController {

	@Autowired
	private TuanProductService tuanProductService;

	@ModelAttribute
	public TuanProduct get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return tuanProductService.get(id);
		} else {
			return new TuanProduct();
		}
	}

	@RequiresPermissions("book:tuanProduct:view")
	@RequestMapping(value = { "list", "" })
	public String list(TuanProduct tuanProduct, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			tuanProduct.setCreateBy(user);
		}
		Page<TuanProduct> page = tuanProductService.find(new Page<TuanProduct>(
				request, response), tuanProduct);
		model.addAttribute("page", page);
		return "modules/book/tuanProductList";
	}

	@RequiresPermissions("book:tuanProduct:view")
	@RequestMapping(value = "form")
	public String form(TuanProduct tuanProduct, Model model) {
		model.addAttribute("tuanProduct", tuanProduct);

		String[] tuanTypes = UserUtils.getUser().getNo().replaceAll("t", "")
				.split(",");
		List<Dict> dicts = Lists.newArrayList();
		for (String tuanType : tuanTypes) {
			String name = DictUtils.getDictLabel(tuanType, "tuan_type", "");
			Dict dict = new Dict();
			dict.setValue(tuanType);
			dict.setLabel(name);
			dicts.add(dict);
		}
		model.addAttribute("dicts", dicts);
		return "modules/book/tuanProductForm";
	}

	@RequiresPermissions("book:tuanProduct:edit")
	@RequestMapping(value = "save")
	public String save(TuanProduct tuanProduct, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tuanProduct)) {
			return form(tuanProduct, model);
		}

		// 预订时间处理
		if (tuanProduct.getEffectTime().equals("1")) {
			tuanProduct.setEffectTime(tuanProduct.getEffectTime()
					+ ","
					+ StringUtils.join(
							request.getParameterValues("effectTime1"), ','));

		} else if (tuanProduct.getEffectTime().equals("2")) {
			tuanProduct.setEffectTime(tuanProduct.getEffectTime()
					+ ","
					+ StringUtils.join(
							request.getParameterValues("effectTime2"), ','));
		}
		tuanProductService.save(tuanProduct);
		addMessage(redirectAttributes, "保存团购产品'" + tuanProduct.getName()
				+ "'成功");
		return "redirect:" + Global.getAdminPath()
				+ "/book/tuanProduct/?repage";
	}

	@RequiresPermissions("book:tuanProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		tuanProductService.delete(id);
		addMessage(redirectAttributes, "删除团购产品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/book/tuanProduct/?repage";
	}

}
