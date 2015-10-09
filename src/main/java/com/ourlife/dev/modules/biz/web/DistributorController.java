package com.ourlife.dev.modules.biz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.service.DistributorService;
import com.ourlife.dev.modules.sys.entity.SysUploadInfo;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysUploadInfoService;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 分销商Controller
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/distributor")
public class DistributorController extends BaseController {

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private SysUploadInfoService sysUploadInfoService;

	@ModelAttribute
	public Distributor get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return distributorService.get(id);
		} else {
			return new Distributor();
		}
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = { "list", "" })
	public String list(Distributor distributor, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			distributor.setCreateBy(user);
		}
		Page<Distributor> page = distributorService.find(new Page<Distributor>(
				request, response), distributor);
		model.addAttribute("page", page);
		return "modules/biz/distributorList";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = { "audit" })
	public String audit(Distributor distributor, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			distributor.setCreateBy(user);
		}
		distributor.setStatus("2");
		Page<Distributor> page = distributorService.findAudit(
				new Page<Distributor>(request, response), distributor);
		model.addAttribute("page", page);
		return "modules/biz/distributorAuditList";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = "form")
	public String form(Distributor distributor, Model model) {
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorForm";
	}

	@RequestMapping(value = "info")
	public String info(Model model) {
		Distributor distributor = distributorService
				.getUserByUsername(UserUtils.getUser().getLoginName());
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorFormInfo";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = "form/balance")
	public String formBalance(Distributor distributor, Model model) {
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorFormBalance";
	}

	@RequiresPermissions("biz:distributor:edit")
	@RequestMapping(value = "save")
	public String save(Distributor distributor, String newPassword,
			Model model, DefaultMultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, distributor)) {
			return form(distributor, model);
		}

		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			distributor.setPassword(SystemService.entryptPassword(newPassword));
		}

		try {
			if (request.getParameter("businessLicenseFile") != null) {

				List<SysUploadInfo> fileContentList = sysUploadInfoService
						.upload(request, "businessLicenseFile");
				if (fileContentList != null && fileContentList.size() == 1) {
					distributor.setBusinessLicense(fileContentList.get(0)
							.getIdKey());
				}
			}

			distributorService.save(distributor);
			addMessage(redirectAttributes, "保存分销商'" + distributor.getName()
					+ "'成功");
		} catch (IOException e) {
			addMessage(redirectAttributes, "保存分销商'" + distributor.getName()
					+ "'失败 " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:" + Global.getAdminPath() + "/biz/distributor/?repage";
	}

	@RequestMapping(value = "save/info")
	public String saveInfo(Distributor distributor, String newPassword,
			Model model, DefaultMultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, distributor)) {
			return form(distributor, model);
		}

		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			distributor.setPassword(SystemService.entryptPassword(newPassword));
		}
		distributorService.save(distributor);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:" + Global.getAdminPath()
				+ "/biz/distributor/info?repage";
	}

	@RequiresPermissions("biz:distributor:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		Distributor distributor = distributorService.get(id);
		if (distributor.getBalance() == 0) {
			distributorService.delete(id);
			addMessage(redirectAttributes, "删除分销商成功");
		} else {
			addMessage(redirectAttributes, "删除分销商失败，未与该分销商结算完成");
		}

		return "redirect:" + Global.getAdminPath() + "/biz/distributor/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkUsername")
	public String checkUsername(String oldUsername, String username) {
		if (username != null && username.equals(oldUsername)) {

			return "true";
		} else if (username != null
				&& systemService.getUserByLoginName(username) == null
				&& distributorService.getUserByUsername(username) == null) {
			return "true";
		}
		return "false";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = "verify")
	public String verify(Distributor distributor, Model model) {
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorVerify";
	}

	@RequiresPermissions("biz:distributor:edit")
	@RequestMapping(value = "save/audit")
	public String saveAudit(Distributor distributor, Model model,
			DefaultMultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, distributor)) {
			return form(distributor, model);
		}

		distributorService.auditSave(distributor);
		addMessage(redirectAttributes, "分销商'" + distributor.getName() + "'审核完成");

		return "redirect:" + Global.getAdminPath()
				+ "/biz/distributor/audit?repage";
	}

	@RequiresPermissions("biz:distributor:edit")
	@RequestMapping(value = "save/reaudit")
	public String saveReAudit(Distributor distributor, Model model,
			DefaultMultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, distributor)) {
			return form(distributor, model);
		}

		distributorService.save(distributor);
		addMessage(redirectAttributes, "分销商'" + distributor.getName()
				+ "'状态变更为重新审核");

		return "redirect:" + Global.getAdminPath()
				+ "/biz/distributor/audit/nopass?repage";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = { "audit/nopass" })
	public String auditNopass(Distributor distributor,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			distributor.setCreateBy(user);
		}
		distributor.setStatus("3");
		Page<Distributor> page = distributorService.findAudit(
				new Page<Distributor>(request, response), distributor);
		model.addAttribute("page", page);
		return "modules/biz/distributorAuditNopassList";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = "view/nopass")
	public String viewNopass(Distributor distributor, Model model) {
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorNopass";
	}

	// -------------------------分销商注册----------------------

	@RequestMapping(value = "register")
	public String register(Distributor distributor, Model model) {
		model.addAttribute("distributor", distributor);
		return "modules/biz/distributorRegister";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(Distributor distributor,
			DefaultMultipartHttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		if (!beanValidator(model, distributor)) {
			return form(distributor, model);
		}
		try {
			try {
				List<SysUploadInfo> fileContentList = sysUploadInfoService
						.upload(request, "businessLicenseFile");
				if (fileContentList != null && fileContentList.size() == 1) {
					distributor.setBusinessLicense(fileContentList.get(0)
							.getIdKey());
				}
			} catch (IOException e) {

			}

			distributor.setStatus("2");
			distributor.setSort(10);
			distributor.setPassword(SystemService.entryptPassword(distributor
					.getPassword()));

			distributorService.save(distributor);
			addMessage(redirectAttributes, "注册成功，等待管理员审核");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "注册失败 " + e.getMessage());
		}

		return "redirect:" + Global.getAdminPath()
				+ "/biz/distributor/register";
	}

	@ResponseBody
	@RequestMapping(value = "register/checkUsername")
	public String checkRegisterUsername(String oldUsername, String username) {
		if (username != null && username.equals(oldUsername)) {
			return "true";
		} else if (username != null
				&& systemService.getUserByLoginName(username) == null
				&& distributorService.getUserByUsername(username) == null) {
			return "true";
		}
		return "false";
	}

	@RequiresPermissions("biz:distributor:view")
	@RequestMapping(value = { "selectList" })
	public String selectList(Distributor distributor,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			distributor.setCreateBy(user);
		}
		distributor.setStatus("0");
		if (distributor.getName() == null
				|| distributor.getName().trim().equals("")) {
			distributor.setName("&&&&");
		}
		Page<Distributor> page = distributorService.findAudit(
				new Page<Distributor>(request, response), distributor);
		if (distributor.getName().trim().equals("&&&&")) {
			distributor.setName("");
		}
		model.addAttribute("page", page);
		return "modules/biz/distributorSelectList";
	}
}
