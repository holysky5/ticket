package com.ourlife.dev.modules.book.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.book.dao.TuanOrderDao;
import com.ourlife.dev.modules.book.entity.TuanOrder;
import com.ourlife.dev.modules.sys.dao.UserDao;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.utils.DictUtils;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import com.ourlife.dev.terminal.sms.SmsService;

/**
 * 团购订单Service
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Component
@Transactional(readOnly = true)
public class TuanOrderService extends BaseService {

	@Autowired
	private TuanOrderDao tuanOrderDao;

	@Autowired
	private UserDao userDao;

	public TuanOrder get(Long id) {
		return tuanOrderDao.findOne(id);
	}

	public Page<TuanOrder> find(Page<TuanOrder> page, TuanOrder tuanOrder) {
		DetachedCriteria dc = tuanOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(tuanOrder.getName())) {
			dc.add(Restrictions.like("name", "%" + tuanOrder.getName() + "%"));
		}

		if (StringUtils.isNotEmpty(tuanOrder.getStatus())) {
			dc.add(Restrictions.eq("status", tuanOrder.getStatus()));
		}

		if (StringUtils.isNotEmpty(tuanOrder.getTuanType())) {
			dc.add(Restrictions.eq("tuanType", tuanOrder.getTuanType()));
		}

		if (StringUtils.isNotEmpty(tuanOrder.getRemarks())) {
			dc.add(Restrictions.le("useDate", tuanOrder.getRemarks()));
		}
		if (StringUtils.isNotEmpty(tuanOrder.getUseDate())) {
			dc.add(Restrictions.ge("useDate", tuanOrder.getUseDate()));
		}

		String[] tuanTypes = UserUtils.getUser().getNo().replaceAll("t", "")
				.split(",");
		dc.add(Restrictions.in("tuanType", tuanTypes));

		dc.add(Restrictions.eq(TuanOrder.DEL_FLAG, TuanOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return tuanOrderDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(TuanOrder tuanOrder) {

		tuanOrderDao.save(tuanOrder);

		if (tuanOrder.getStatus() != null && tuanOrder.getStatus().equals("0")) {
			String sqlString = "select * from sys_user where no like '%t"
					+ tuanOrder.getTuanType() + "%'";
			List<User> userList = userDao.findBySql(sqlString, User.class);
			if (userList.size() > 0) {
				String mobile = userList.get(0).getMobile();
				if (StringUtils.isNotBlank(mobile) && mobile.length() == 11) {
					SmsService smsService = new SmsService();
					String content = "你好，"
							+ DictUtils.getDictLabel(tuanOrder.getTuanType(),
									"tuan_type", "团购网站") + "有新的订单，["
							+ tuanOrder.getName() + "]预约["
							+ tuanOrder.getUseDate() + "]的["
							+ tuanOrder.getProduct().getTitle() + "]，请处理。";
					// System.out.println(content);
					smsService.sendSms(mobile, content);
				}

			}
		}

	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		tuanOrderDao.deleteById(id);
	}

	/**
	 * 统计订单
	 */
	public List<Object> statOrder(String beginTime, String endTime) {
		String sqlString = "SELECT count(1) from tuan_order  where  create_date>='"
				+ beginTime + "' and create_date <'" + endTime + "'";
		List<Object> list = tuanOrderDao.findBySql(sqlString);
		List<Object> resultsList = Lists.newArrayList();
		for (Object o : list) {
			resultsList.add(o);
			return resultsList;
		}
		return resultsList;
	}

	public Map<String, Integer> statOrderGroupByType(String beginTime,
			String endTime) {
		String sqlString = "SELECT tuan_type,count(1) count from tuan_order where  create_date>='"
				+ beginTime
				+ "' and create_date <'"
				+ endTime
				+ "' group by tuan_type ORDER BY count desc";
		List<Object> list = tuanOrderDao.findBySql(sqlString);
		Map<String, Integer> map = Maps.newTreeMap();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			map.put(String.valueOf(objects[0]),
					((BigInteger) objects[1]).intValue());
		}
		return map;
	}
}
