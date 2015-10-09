package com.ourlife.dev.modules.book.service;

import java.util.ArrayList;
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
import com.ourlife.dev.modules.book.dao.TuanProductDao;
import com.ourlife.dev.modules.book.entity.TuanProduct;

/**
 * 团购产品Service
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Component
@Transactional(readOnly = true)
public class TuanProductService extends BaseService {

	@Autowired
	private TuanProductDao tuanProductDao;

	public TuanProduct get(Long id) {
		return tuanProductDao.findOne(id);
	}

	public Page<TuanProduct> find(Page<TuanProduct> page,
			TuanProduct tuanProduct) {
		DetachedCriteria dc = tuanProductDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(tuanProduct.getTitle())) {
			dc.add(Restrictions.like("title", "%" + tuanProduct.getTitle()
					+ "%"));
		}
		if (StringUtils.isNotEmpty(tuanProduct.getTuanType())) {
			dc.add(Restrictions.eq("tuanType", tuanProduct.getTuanType()));
		}
		if (StringUtils.isNotEmpty(tuanProduct.getProvince())) {
			dc.add(Restrictions.eq("province", tuanProduct.getProvince()));
		}
		if (StringUtils.isNotEmpty(tuanProduct.getCity())) {
			dc.add(Restrictions.eq("city", tuanProduct.getCity()));
		}
		dc.add(Restrictions.eq(TuanProduct.DEL_FLAG,
				TuanProduct.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return tuanProductDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(TuanProduct tuanProduct) {
		tuanProductDao.save(tuanProduct);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		tuanProductDao.deleteById(id);
	}

	public List<String> groupProvince(String tuanType) {
		String sql = "select tuan_product.province from tuan_product  where tuan_Type = '"
				+ tuanType
				+ "' and del_Flag='0' GROUP BY(tuan_product.province) ";

		List<String> provinceList = new ArrayList<String>();
		List<Object> list = tuanProductDao.findBySql(sql);
		for (Object obj : list) {
			provinceList.add((String) obj);
		}
		return provinceList;
	}

	public List<String> filterCityByProvince(String tuanType, String province) {
		String sql = "select DISTINCT tuan_product.city from tuan_product where tuan_Type = '"
				+ tuanType
				+ "' and tuan_product.province='"
				+ province
				+ "' and del_Flag='0'";

		List<String> cityList = new ArrayList<String>();
		List<Object> list = tuanProductDao.findBySql(sql);
		for (Object obj : list) {
			cityList.add((String) obj);
		}
		return cityList;
	}

}
