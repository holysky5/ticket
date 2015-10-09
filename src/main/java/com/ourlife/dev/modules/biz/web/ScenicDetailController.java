package com.ourlife.dev.modules.biz.web;

import java.util.List;

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
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.ScenicDetail;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.ScenicDetailService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.Dict;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.DictUtils;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 景区详情Controller
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/scenicDetail")
public class ScenicDetailController extends BaseController {

	@Autowired
	private ScenicDetailService scenicDetailService;

	@Autowired
	private SupplierService supplierService;

	@ModelAttribute
	public ScenicDetail get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return scenicDetailService.get(id);
		} else {
			return new ScenicDetail();
		}
	}

	@RequiresPermissions("biz:scenicDetail:view")
	@RequestMapping(value = { "list", "" })
	public String list(ScenicDetail scenicDetail, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (user.getUserType() == null || !user.getUserType().equals("4")) {
			return "redirect:" + Global.getAdminPath() + "/error/404";
		}

		if (!user.isAdmin()) {
			scenicDetail.setCreateBy(user);
		}
		Supplier supplier = supplierService
				.getSupplierByNo(user.getLoginName());
		scenicDetail.setScenic(supplier);
		List<Dict> dicts = DictUtils.getDictList("scenic_info_type");
		if (supplier.getScenicDetailList() == null
				|| supplier.getScenicDetailList().size() != dicts.size()) {
			for (Dict dict : dicts) {
				if (supplier.getScenicDetailMap().get(dict.getValue()) == null) {
					ScenicDetail newDetail = new ScenicDetail();
					newDetail.setType(dict.getValue());
					newDetail.setContent("");
					newDetail.setScenic(supplier);
					scenicDetailService.save(newDetail);
				}
			}
		}

		Page<ScenicDetail> page = scenicDetailService.find(
				new Page<ScenicDetail>(request, response, -1), scenicDetail);
		model.addAttribute("page", page);
		return "modules/biz/scenicDetailList";
	}

	@RequiresPermissions("biz:scenicDetail:view")
	@RequestMapping(value = "form")
	public String form(ScenicDetail scenicDetail, Model model) {
		model.addAttribute("scenicDetail", scenicDetail);
		return "modules/biz/scenicDetailForm";
	}

	@RequiresPermissions("biz:scenicDetail:edit")
	@RequestMapping(value = "save")
	public String save(ScenicDetail scenicDetail, Model model,
			RedirectAttributes redirectAttributes) {
		if (scenicDetail.getScenic() == null) {
			scenicDetail.setScenic(supplierService.getSupplierByNo(UserUtils
					.getUser().getLoginName()));
		}

		if (!beanValidator(model, scenicDetail)) {
			return form(scenicDetail, model);
		}

		scenicDetail.setContent(StringEscapeUtils.unescapeHtml4(scenicDetail
				.getContent()));
		scenicDetailService.save(scenicDetail);
		addMessage(redirectAttributes, "保存景区详情成功");
		User user = UserUtils.getUser();
		if (user.getUserType().equals("1")) {
			return "redirect:" + Global.getAdminPath()
					+ "/biz/scenicDetail/update/"
					+ scenicDetail.getScenic().getId();
		}
		return "redirect:" + Global.getAdminPath()
				+ "/biz/scenicDetail/?repage";
	}

	@RequiresPermissions("biz:scenicDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		scenicDetailService.delete(id);
		addMessage(redirectAttributes, "删除景区详情成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/scenicDetail/?repage";
	}

	/**
	 * 管理员修改景区详情
	 * 
	 * @param scenicDetail
	 * @param supplierId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("biz:supplier:edit")
	@RequestMapping(value = { "update/{supplierId}" })
	public String adminUpdate(ScenicDetail scenicDetail,
			@PathVariable Long supplierId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (user.getUserType() == null || !user.getUserType().equals("1")) {
			return "redirect:" + Global.getAdminPath() + "/error/404";
		}
		if (!user.isAdmin()) {
			scenicDetail.setCreateBy(user);
		}
		Supplier supplier = supplierService.get(supplierId);
		scenicDetail.setScenic(supplier);
		List<Dict> dicts = DictUtils.getDictList("scenic_info_type");
		if (supplier.getScenicDetailList() == null
				|| supplier.getScenicDetailList().size() != dicts.size()) {
			for (Dict dict : dicts) {
				if (supplier.getScenicDetailMap().get(dict.getValue()) == null) {
					ScenicDetail newDetail = new ScenicDetail();
					newDetail.setType(dict.getValue());
					newDetail.setContent("");
					newDetail.setScenic(supplier);
					scenicDetailService.save(newDetail);
				}
			}
		}

		Page<ScenicDetail> page = scenicDetailService.find(
				new Page<ScenicDetail>(request, response, -1), scenicDetail);
		model.addAttribute("page", page);
		return "modules/biz/scenicDetailList";
	}

}
