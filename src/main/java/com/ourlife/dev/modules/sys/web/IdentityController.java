package com.ourlife.dev.modules.sys.web;

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
import com.ourlife.dev.modules.sys.entity.Identity;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysIdentityService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 流水号Controller
 * 
 * @author peng.liao
 * @version 2014-01-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/identity")
public class IdentityController extends BaseController {

	@Autowired
	private SysIdentityService identityService;

	@ModelAttribute
	public Identity get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return identityService.get(id);
		} else {
			return new Identity();
		}
	}

	@RequiresPermissions("sys:identity:view")
	@RequestMapping(value = { "list", "" })
	public String list(Identity identity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			identity.setCreateBy(user);
		}
		Page<Identity> page = identityService.find(new Page<Identity>(request,
				response), identity);
		model.addAttribute("page", page);
		return "modules/sys/identityList";
	}

	@RequiresPermissions("sys:identity:view")
	@RequestMapping(value = "form")
	public String form(Identity identity, Model model) {
		model.addAttribute("identity", identity);
		return "modules/sys/identityForm";
	}

	@RequiresPermissions("sys:identity:edit")
	@RequestMapping(value = "save")
	public String save(Identity identity, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, identity)) {
			return form(identity, model);
		}
		if (identity.getId() == null) {
			boolean rtn = this.identityService.isAliasExisted(identity
					.getAlias());
			if (rtn) {
				addMessage(redirectAttributes, "别名已经存在");
				return "redirect:" + Global.getAdminPath()
						+ "/sys/identity/?repage";
			}

		}
		identityService.save(identity);
		addMessage(redirectAttributes, "保存流水号'" + identity.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/sys/identity/?repage";
	}

	@RequiresPermissions("sys:identity:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		identityService.delete(id);
		addMessage(redirectAttributes, "删除流水号成功");
		return "redirect:" + Global.getAdminPath() + "/sys/identity/?repage";
	}

}
