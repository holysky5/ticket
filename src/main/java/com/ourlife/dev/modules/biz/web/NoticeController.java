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
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Notice;
import com.ourlife.dev.modules.biz.service.NoticeService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 公告Controller
 * 
 * @author ourlife
 * @version 2014-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/notice")
public class NoticeController extends BaseController {

	@Autowired
	private NoticeService noticeService;

	@ModelAttribute
	public Notice get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return noticeService.get(id);
		} else {
			return new Notice();
		}
	}

	@RequiresPermissions("biz:notice:view")
	@RequestMapping(value = { "list", "" })
	public String list(Notice notice, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			notice.setCreateBy(user);
		}
		Page<Notice> page = noticeService.find(new Page<Notice>(request,
				response), notice);
		model.addAttribute("page", page);
		return "modules/biz/noticeList";
	}

	@RequiresPermissions("biz:notice:view")
	@RequestMapping(value = "form")
	public String form(Notice notice, Model model) {
		model.addAttribute("notice", notice);
		return "modules/biz/noticeForm";
	}

	@RequiresPermissions("biz:notice:edit")
	@RequestMapping(value = "save")
	public String save(Notice notice, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, notice)) {
			return form(notice, model);
		}
		notice.setContent(StringEscapeUtils.unescapeHtml4(notice.getContent()));
		noticeService.save(notice);
		addMessage(redirectAttributes, "保存公告成功");
		return "redirect:" + Global.getAdminPath() + "/biz/notice/?repage";
	}

	@RequiresPermissions("biz:notice:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		noticeService.delete(id);
		addMessage(redirectAttributes, "删除公告成功");
		return "redirect:" + Global.getAdminPath() + "/biz/notice/?repage";
	}

	@RequiresPermissions("biz:notice:view")
	@RequestMapping(value = "view/{idKey}")
	public String view(@PathVariable("idKey") String idKey, Model model) {
		Notice notice = noticeService.get(idKey);
		if (notice == null || !notice.getDelFlag().equals("0")) {
			return "redirect:" + Global.getAdminPath() + "/error/403";
		}

		model.addAttribute("notice", notice);

		return "modules/biz/noticeView";
	}

}
