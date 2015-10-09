package com.ourlife.dev.modules.biz.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.modules.biz.dao.ScenicDetailDao;
import com.ourlife.dev.modules.biz.entity.ScenicDetail;

/**
 * 景区详情Service
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Component
@Transactional(readOnly = true)
public class ScenicDetailService extends BaseService {

	@Autowired
	private ScenicDetailDao scenicDetailDao;

	public ScenicDetail get(Long id) {
		return scenicDetailDao.findOne(id);
	}

	public Page<ScenicDetail> find(Page<ScenicDetail> page,
			ScenicDetail scenicDetail) {
		DetachedCriteria dc = scenicDetailDao.createDetachedCriteria();

		if (scenicDetail.getScenic() != null) {
			dc.createAlias("scenic", "scenic");
			dc.add(Restrictions.eq("scenic.id", scenicDetail.getScenic()
					.getId()));
		}

		dc.add(Restrictions.eq(ScenicDetail.DEL_FLAG,
				ScenicDetail.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("type"));
		return scenicDetailDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(ScenicDetail scenicDetail) {
		scenicDetailDao.save(scenicDetail);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		scenicDetailDao.deleteById(id);
	}

}
