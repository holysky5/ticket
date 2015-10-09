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
import com.ourlife.dev.modules.biz.dao.DirectDao;
import com.ourlife.dev.modules.biz.dao.DirectPriceDao;
import com.ourlife.dev.modules.biz.entity.Direct;
import com.ourlife.dev.modules.biz.entity.DirectPrice;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.Product;
import com.ourlife.dev.modules.biz.entity.Supplier;

/**
 * 直通景区Service
 * 
 * @author ourlife
 * @version 2014-06-22
 */
@Component
@Transactional(readOnly = true)
public class DirectService extends BaseService {

	@Autowired
	private DirectDao directDao;

	@Autowired
	private DirectPriceDao directPriceDao;

	public Direct get(Long id) {
		return directDao.findOne(id);
	}

	public Page<Direct> find(Page<Direct> page, Direct direct) {
		DetachedCriteria dc = directDao.createDetachedCriteria();
		dc.createAlias("supplier", "supplier");
		dc.add(Restrictions.eq("supplier.delFlag", Supplier.DEL_FLAG_NORMAL));
		if (direct.getSupplier() != null) {

			if (StringUtils.isNotBlank(direct.getSupplier().getName())) {
				dc.add(Restrictions.like("supplier.name", "%"
						+ direct.getSupplier().getName() + "%"));
			}
			if (direct.getSupplier().getId() != null) {
				dc.add(Restrictions.eq("supplier.id", direct.getSupplier()
						.getId()));
			}
		}
		dc.createAlias("distributor", "distributor");
		dc.add(Restrictions.eq("distributor.delFlag",
				Distributor.DEL_FLAG_NORMAL));
		if (direct.getDistributor() != null) {

			if (StringUtils.isNotBlank(direct.getDistributor().getName())) {
				dc.add(Restrictions.or(
						Restrictions.like("distributor.name", "%"
								+ direct.getDistributor().getName() + "%"),
						Restrictions.like("distributor.username", "%"
								+ direct.getDistributor().getName() + "%"),
						Restrictions.like("distributor.company", "%"
								+ direct.getDistributor().getName() + "%")));
			}
			if (direct.getDistributor().getId() != null) {
				dc.add(Restrictions.eq("distributor.id", direct
						.getDistributor().getId()));
			}
		}
		if (StringUtils.isNotBlank(direct.getRemarks())) {

			dc.add(Restrictions.or(Restrictions.gt("balance", 0.0),
					Restrictions.eq("status", "0")));
		}
		dc.add(Restrictions.eq(Direct.DEL_FLAG, Direct.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return directDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Direct direct) {
		directDao.save(direct);
		// directPriceDao.save(direct.getDirectPriceList());
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		directDao.deleteById(id);
	}

	public DirectPrice getPriceByDirectAndProduct(Direct direct, Product product) {
		String sqlString = "SELECT * from biz_product_direct_price where direct_id = "
				+ direct.getId() + " and product_id=" + product.getId();
		// System.err.println(sqlString);
		List<DirectPrice> list = directPriceDao.findBySql(sqlString,
				DirectPrice.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void deletePrice(DirectPrice price) {
		directPriceDao.delete(price);
	}

	public DirectPrice getPriceById(Long id) {
		return directPriceDao.findOne(id);
	}

}
