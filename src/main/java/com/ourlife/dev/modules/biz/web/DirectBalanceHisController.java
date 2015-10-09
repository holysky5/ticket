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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Direct;
import com.ourlife.dev.modules.biz.entity.DirectBalanceHis;
import com.ourlife.dev.modules.biz.service.DirectBalanceHisService;
import com.ourlife.dev.modules.biz.service.DirectService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 直通余额Controller
 * 
 * @author ourlife
 * @version 2014-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/directBalanceHis")
public class DirectBalanceHisController extends BaseController {

	@Autowired
	private DirectBalanceHisService directBalanceHisService;

	@Autowired
	private DirectService directService;

	@ModelAttribute
	public DirectBalanceHis get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return directBalanceHisService.get(id);
		} else {
			return new DirectBalanceHis();
		}
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = { "list/view" })
	public String listView(DirectBalanceHis directBalanceHis,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			directBalanceHis.setCreateBy(user);
		}
		Page<DirectBalanceHis> page = directBalanceHisService
				.find(new Page<DirectBalanceHis>(request, response),
						directBalanceHis);
		if (directBalanceHis.getDirect() != null
				&& directBalanceHis.getDirect().getId() != null) {
			Direct direct = directService.get(directBalanceHis.getDirect()
					.getId());
			model.addAttribute("direct", direct);
		}
		model.addAttribute("page", page);
		return "modules/biz/directBalanceHisListView";
	}

	@RequiresPermissions("biz:direct:edit")
	@RequestMapping(value = { "list", "" })
	public String list(DirectBalanceHis directBalanceHis,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			directBalanceHis.setCreateBy(user);
		}
		Page<DirectBalanceHis> page = directBalanceHisService
				.find(new Page<DirectBalanceHis>(request, response),
						directBalanceHis);
		if (directBalanceHis.getDirect() != null
				&& directBalanceHis.getDirect().getId() != null) {
			Direct direct = directService.get(directBalanceHis.getDirect()
					.getId());
			model.addAttribute("direct", direct);
		}
		model.addAttribute("page", page);
		return "modules/biz/directBalanceHisList";
	}

	@RequiresPermissions("biz:direct:view")
	@RequestMapping(value = "form")
	public String form(DirectBalanceHis directBalanceHis, Model model) {
		if (directBalanceHis.getDirect() != null
				&& directBalanceHis.getDirect().getId() != null) {
			Direct direct = directService.get(directBalanceHis.getDirect()
					.getId());
			directBalanceHis.setDirect(direct);
		}
		model.addAttribute("directBalanceHis", directBalanceHis);
		return "modules/biz/directBalanceHisForm";
	}

	@RequiresPermissions("biz:direct:edit")
	@RequestMapping(value = "save")
	public String save(DirectBalanceHis directBalanceHis, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, directBalanceHis)) {
			return form(directBalanceHis, model);
		}
		if (directBalanceHis.getDirect() != null
				&& directBalanceHis.getDirect().getId() != null) {
			Direct direct = directService.get(directBalanceHis.getDirect()
					.getId());
			directBalanceHis.setDirect(direct);
			directBalanceHis.setBegin(direct.getBalance());
			direct.setBalance(direct.getBalance()
					+ directBalanceHis.getAmount());
			directBalanceHis.setEnd(direct.getBalance());
		}
		directBalanceHis.setStatus("0");
		directBalanceHisService.save(directBalanceHis);
		addMessage(redirectAttributes, "保存余额成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/directBalanceHis/list?direct.id="
				+ directBalanceHis.getDirect().getId();
	}

	@RequiresPermissions("biz:direct:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		directBalanceHisService.delete(id);
		addMessage(redirectAttributes, "删除直通余额成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/directBalanceHis/?repage";
	}

}
