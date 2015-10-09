package com.ourlife.dev.modules.biz.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.OrderLogDao;
import com.ourlife.dev.modules.biz.entity.OrderLog;

/**
 * 订单日志Service
 * 
 * @author ourlife
 * @version 2014-06-28
 */
@Component
@Transactional(readOnly = true)
public class OrderLogService extends BaseService {

	@Autowired
	private OrderLogDao orderLogDao;

	public OrderLog get(Long id) {
		return orderLogDao.findOne(id);
	}

	public Page<OrderLog> find(Page<OrderLog> page, OrderLog orderLog) {
		DetachedCriteria dc = orderLogDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(orderLog.getName())) {
			dc.add(Restrictions.like("name", "%" + orderLog.getName() + "%"));
		}

		if (orderLog.getOrderInfo() != null
				&& orderLog.getOrderInfo().getId() != null) {
			dc.createAlias("orderInfo", "orderInfo");
			dc.add(Restrictions.eq("orderInfo.id", orderLog.getOrderInfo()
					.getId()));
		}
		dc.add(Restrictions.eq(OrderLog.DEL_FLAG, OrderLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return orderLogDao.find(page, dc);
	}

	public List<OrderLog> find(OrderLog orderLog) {
		DetachedCriteria dc = orderLogDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(orderLog.getName())) {
			dc.add(Restrictions.like("name", "%" + orderLog.getName() + "%"));
		}

		if (orderLog.getOrderInfo() != null
				&& orderLog.getOrderInfo().getId() != null) {
			dc.createAlias("orderInfo", "orderInfo");
			dc.add(Restrictions.eq("orderInfo.id", orderLog.getOrderInfo()
					.getId()));
		}
		dc.add(Restrictions.eq(OrderLog.DEL_FLAG, OrderLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("id"));
		return orderLogDao.find(dc);
	}

	@Transactional(readOnly = false)
	public void save(OrderLog orderLog) {
		orderLogDao.save(orderLog);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		orderLogDao.deleteById(id);
	}

}
