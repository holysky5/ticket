package com.ourlife.dev.modules.book.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.utils.excel.ExportExcel;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.book.entity.TuanOrder;
import com.ourlife.dev.modules.book.service.TuanOrderService;

/**
 * 团购订单Controller
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/book/tuanOrder")
public class TuanOrderController extends BaseController {

	@Autowired
	private TuanOrderService tuanOrderService;

	@ModelAttribute
	public TuanOrder get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return tuanOrderService.get(id);
		} else {
			return new TuanOrder();
		}
	}

	@RequiresPermissions("book:tuanOrder:view")
	@RequestMapping(value = { "list/{type}" })
	public String list(@PathVariable String type, TuanOrder tuanOrder,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		if (StringUtils.isNotBlank(type)) {
			tuanOrder.setStatus(type);
		} else {
			tuanOrder.setStatus("0");
		}
		Page<TuanOrder> page = tuanOrderService.find(new Page<TuanOrder>(
				request, response), tuanOrder);
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		return "modules/book/tuanOrderList";
	}

	@RequiresPermissions("book:tuanOrder:view")
	@RequestMapping(value = "form")
	public String form(TuanOrder tuanOrder, Model model) {
		model.addAttribute("tuanOrder", tuanOrder);
		return "modules/book/tuanOrderForm";
	}

	@RequiresPermissions("book:tuanOrder:edit")
	@RequestMapping(value = "save")
	public String save(TuanOrder tuanOrder, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tuanOrder)) {
			return form(tuanOrder, model);
		}
		tuanOrder.setStatus("1");
		tuanOrderService.save(tuanOrder);
		addMessage(redirectAttributes, "处理成功");
		return "redirect:" + Global.getAdminPath()
				+ "/book/tuanOrder/list/0?repage";
	}

	@RequiresPermissions("book:tuanOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		tuanOrderService.delete(id);
		addMessage(redirectAttributes, "删除团购订单成功");
		return "redirect:" + Global.getAdminPath() + "/book/tuanOrder/?repage";
	}

	@RequiresPermissions("book:tuanOrder:view")
	@RequestMapping(value = "export/{type}", method = RequestMethod.POST)
	public String exportFile(@PathVariable String type, TuanOrder orderInfo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		if (orderInfo.getStatus() == null) {
			orderInfo.setStatus(type);
		}

		try {
			String fileName = "团购订单数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<TuanOrder> page = tuanOrderService.find(new Page<TuanOrder>(
					request, response, -1), orderInfo);
			new ExportExcel("团购订单数据", TuanOrder.class)
					.setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出团购订单数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/book/tuanOrder/list/"
				+ type;
	}

}
