package com.ourlife.dev.modules.biz.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.Notice;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.DistributorService;
import com.ourlife.dev.modules.biz.service.NoticeService;
import com.ourlife.dev.modules.biz.service.OrderInfoService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.book.service.TuanOrderService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 首页Controller
 * 
 * @author ourlife
 * @version 2014-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/home")
public class HomeController extends BaseController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private TuanOrderService tuanOrderService;

	@ModelAttribute
	public Notice get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return noticeService.get(id);
		} else {
			return new Notice();
		}
	}

	@RequestMapping(value = { "" })
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Double balance = 0.0;
		if (user.getUserType().equals(Supplier.TYPE_VALUE)) {
			Supplier supplier = supplierService.getSupplierByNo(user
					.getLoginName());
			balance = supplier.getBalance();
		} else if (user.getUserType().equals(Distributor.TYPE_VALUE)) {
			Distributor distributor = distributorService.getUserByUsername(user
					.getLoginName());
			balance = distributor.getBalance();
		} else if (user.getUserType().equals("1")) {
			balance = 0.0;
		}
		model.addAttribute("user", user);
		model.addAttribute("balance", balance);

		Notice notice = new Notice();
		Page<Notice> page = noticeService.find(new Page<Notice>(request,
				response, 5), notice);
		model.addAttribute("notices", page.getList());

		if (user.getUserType().equals("1")) {
			String[] days = DateUtils.getDayBeginTimeAndEndTime(DateUtils
					.getDate());
			List<Object> dayList = orderInfoService.statOrder("0", days[0],
					days[1]);
			model.addAttribute("dayList", dayList);
			String[] months = DateUtils.getMonthBeginTimeAndEndTime(DateUtils
					.getDate());
			List<Object> monthList = orderInfoService.statOrder("0", months[0],
					months[1]);
			model.addAttribute("monthList", monthList);

			Double notUseTotalAmount = distributorService.statTotalAmount();
			model.addAttribute("notUseTotalAmount", notUseTotalAmount);

			Double toSuppliereTotalAmount = supplierService.statTotalAmount();
			model.addAttribute("toSuppliereTotalAmount", toSuppliereTotalAmount);
		} else if (user.getUserType().equals("6")) {
			List<Object> dataList = Lists.newArrayList();
			String[] days = DateUtils.getDayBeginTimeAndEndTime(DateUtils
					.getDate());
			List<Object> dayList = tuanOrderService.statOrder(days[0], days[1]);
			dataList.add(dayList.get(0));
			String[] days1 = DateUtils
					.getDayBeginTimeAndEndTime(DateUtils.formatDate(
							DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"));
			List<Object> day1List = tuanOrderService.statOrder(days1[0],
					days1[1]);
			dataList.add(day1List.get(0));

			String[] months = DateUtils.getMonthBeginTimeAndEndTime(DateUtils
					.getDate());
			List<Object> monthList = tuanOrderService.statOrder(months[0],
					months[1]);
			dataList.add(monthList.get(0));
			model.addAttribute("dataList", dataList);

			Map<String, Integer> monthMap = tuanOrderService
					.statOrderGroupByType(months[0], months[1]);

			model.addAttribute("dataMap", monthMap);
		}

		return "modules/biz/home";
	}

}
