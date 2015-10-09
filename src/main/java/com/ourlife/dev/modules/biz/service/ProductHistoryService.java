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
import com.ourlife.dev.modules.biz.dao.ProductHistoryDao;
import com.ourlife.dev.modules.biz.entity.ProductHistory;

/**
 * 余额历史Service
 * 
 * @author ourlife
 * @version 2014-05-26
 */
@Component
@Transactional(readOnly = true)
public class ProductHistoryService extends BaseService {

	@Autowired
	private ProductHistoryDao productHistoryDao;

	public ProductHistory get(Long id) {
		return productHistoryDao.findOne(id);
	}

	public Page<ProductHistory> find(Page<ProductHistory> page,
			ProductHistory productHistory) {
		DetachedCriteria dc = productHistoryDao.createDetachedCriteria();
		if (productHistory.getProductId() != null) {
			dc.add(Restrictions.eq("productId", productHistory.getProductId()));
		}
		dc.add(Restrictions.eq(ProductHistory.DEL_FLAG,
				ProductHistory.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productHistoryDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(ProductHistory productHistory) {
		productHistoryDao.save(productHistory);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		productHistoryDao.deleteById(id);
	}

	public ProductHistory getLastValid(Long id) {
		String sql = "SELECT * from biz_product_history where product_id = "
				+ id
				+ " and  status = '0' and audit_flag = '1' ORDER BY id desc  LIMIT 1";
		List<ProductHistory> list = productHistoryDao.findBySql(sql,
				ProductHistory.class);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
