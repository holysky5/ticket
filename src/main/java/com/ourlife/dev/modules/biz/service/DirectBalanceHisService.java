package com.ourlife.dev.modules.biz.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.modules.biz.dao.DirectBalanceHisDao;
import com.ourlife.dev.modules.biz.entity.DirectBalanceHis;

/**
 * 直通余额Service
 * 
 * @author ourlife
 * @version 2014-06-24
 */
@Component
@Transactional(readOnly = true)
public class DirectBalanceHisService extends BaseService {

	@Autowired
	private DirectBalanceHisDao directBalanceHisDao;

	public DirectBalanceHis get(Long id) {
		return directBalanceHisDao.findOne(id);
	}

	public Page<DirectBalanceHis> find(Page<DirectBalanceHis> page,
			DirectBalanceHis directBalanceHis) {
		DetachedCriteria dc = directBalanceHisDao.createDetachedCriteria();
		if (directBalanceHis.getDirect() != null) {
			dc.createAlias("direct", "direct");
			if (directBalanceHis.getDirect().getId() != null) {
				dc.add(Restrictions.eq("direct.id", directBalanceHis
						.getDirect().getId()));
			}
		}
		dc.add(Restrictions.eq(DirectBalanceHis.DEL_FLAG,
				DirectBalanceHis.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return directBalanceHisDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(DirectBalanceHis directBalanceHis) {
		directBalanceHisDao.save(directBalanceHis);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		directBalanceHisDao.deleteById(id);
	}

}
