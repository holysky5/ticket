package com.ourlife.dev.modules.book.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.servlet.ValidateCodeServlet;
import com.ourlife.dev.common.utils.Collections3;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.book.entity.TuanOrder;
import com.ourlife.dev.modules.book.entity.TuanProduct;
import com.ourlife.dev.modules.book.service.TuanOrderService;
import com.ourlife.dev.modules.book.service.TuanProductService;
import com.ourlife.dev.modules.sys.security.CaptchaException;

/**
 * 首页Controller
 * 
 * @author ourlife
 * @version 2014-05-25
 */
@Controller
@RequestMapping(value = { "${adminPath}/tuan", "${adminPath}/tuan/tuan" })
public class TuanBookController extends BaseController {
	@Autowired
	private TuanOrderService tuanOrderService;

	@Autowired
	private TuanProductService tuanProductService;

	@RequestMapping(value = { "" })
	public String index(TuanOrder tuanOrder, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		return "modules/book/index";
	}

	@RequestMapping(value = "order/save")
	public String orderSave(TuanOrder tuanOrder, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		Session session = SecurityUtils.getSubject().getSession();
		String code = (String) session
				.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		String captcha = request.getParameter("validateCode");
		if (captcha == null || !captcha.toUpperCase().equals(code)) {
			throw new CaptchaException("验证码错误.");
		}

		String productId = request.getParameter("selectProduct");
		tuanOrder.setProduct(tuanProductService.get(Long.valueOf(productId)));

		String[] tickets = request.getParameterValues("txtTicketNo");

		tuanOrder.setTicket(Collections3.convertToString(
				Arrays.asList(tickets), ","));
		tuanOrder.setStatus("0");
		tuanOrderService.save(tuanOrder);
		addMessage(redirectAttributes, "您的团购预约单提交成功");
		return "redirect:" + Global.getAdminPath() + "/tuan/result";
	}

	@RequestMapping(value = { "result" })
	public String result(TuanOrder tuanOrder, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/book/result";
	}

	@ResponseBody
	@RequestMapping(value = "product/query")
	public List<TuanProduct> queryProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String tuanType = request.getParameter("tuanType");
		String province = request.getParameter("province");
		if (province == null || province.equals("null")) {
			province = "";
		}
		String city = request.getParameter("city");
		if (city == null || city.equals("null")) {
			city = "";
		}

		TuanProduct tuanProduct = new TuanProduct();
		tuanProduct.setTuanType(tuanType);
		tuanProduct.setProvince(province);
		tuanProduct.setCity(city);
		Page<TuanProduct> page = tuanProductService.find(new Page<TuanProduct>(
				request, response, -1), tuanProduct);
		return page.getList();
	}

	@ResponseBody
	@RequestMapping(value = "product/date")
	public List<String> queryProductDate(HttpServletRequest request,
			HttpServletResponse response) {
		String idString = request.getParameter("id");
		if (idString == null) {
			return null;
		}
		TuanProduct productHis = tuanProductService.get(Long.valueOf(idString));

		List<String> list = Lists.newArrayList();
		String minday = "";
		try {
			String[] times = productHis.getEffectTime().split(",");
			if (times[0].equals("0")) {
				minday = DateUtils.getDate();
			} else if (times[0].equals("1")) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[2]));
				calendar.set(Calendar.MINUTE, Integer.valueOf(times[3]));
				if (new Date().after(calendar.getTime())) {
					calendar.add(Calendar.DATE, Integer.valueOf(times[1]) + 1);
				} else {
					calendar.add(Calendar.DATE, Integer.valueOf(times[1]));
				}
				minday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
			} else if (times[0].equals("2")) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR, Integer.valueOf(times[1]));
				minday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
			}
		} catch (Exception e) {
			minday = DateUtils.getDate();
		}
		String maxday = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 60);
		maxday = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");

		if (StringUtils.isNotBlank(productHis.getBeginTime())
				&& StringUtils.isNotBlank(productHis.getEndTime())) {
			String beginDate = DateUtils.getYear() + "-"
					+ productHis.getBeginTime();
			String endDate = DateUtils.getYear() + "-"
					+ productHis.getEndTime();
			if (productHis.getBeginTime().compareTo(productHis.getEndTime()) > 0) {
				endDate = (Integer.valueOf(DateUtils.getYear()) + 1) + "-"
						+ productHis.getEndTime();
			}
			if (DateUtils.parseDate(beginDate).after(
					DateUtils.parseDate(minday))) {
				minday = beginDate;
			}
			if (DateUtils.parseDate(endDate)
					.before(DateUtils.parseDate(maxday))) {
				maxday = endDate;
			}
		}

		if (DateUtils.parseDate(maxday).before(DateUtils.parseDate(minday))) {
			minday = maxday;
		}
		list.add(minday);
		list.add(maxday);

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "province/group")
	public List<String> groupProvince(HttpServletRequest request,
			HttpServletResponse response) {
		String tuanType = request.getParameter("tuanType");
		List<String> provinceList = tuanProductService.groupProvince(tuanType);
		return provinceList;
	}

	@ResponseBody
	@RequestMapping(value = { "city/filter/{province}" })
	public List<String> filterCityByProvince(@PathVariable String province,
			HttpServletRequest request, HttpServletResponse response) {
		String tuanType = request.getParameter("tuanType");
		List<String> cityList = tuanProductService.filterCityByProvince(
				tuanType, province);
		return cityList;
	}
}
