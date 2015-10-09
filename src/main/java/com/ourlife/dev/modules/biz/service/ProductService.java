package com.ourlife.dev.modules.biz.service;

import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.ProductDao;
import com.ourlife.dev.modules.biz.entity.Product;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysIdentityService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 门票详情Service
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Component
@Transactional(readOnly = true)
public class ProductService extends BaseService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private SysIdentityService sysIdentityService;

	public Product get(Long id) {
		productDao.clear();
		return productDao.findOne(id);
	}

	public Product getByNo(String no) {
		productDao.clear();
		return productDao.findByNo(no);
	}

	public Page<Product> find(Page<Product> page, Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();

		// if the user is the supplier, only get the own products

		if (null != product.getScenic()) {
			dc.add(Restrictions.eq("scenic.id", product.getScenic().getId()));
		} else {
			return null;
		}
		if (StringUtils.isNotEmpty(product.getName())) {
			dc.add(Restrictions.like("name", "%" + product.getName() + "%"));
		}
		if (StringUtils.isNotBlank(product.getAuditFlag())) {
			if (product.getAuditFlag().equals("0")) {
				dc.add(Restrictions.eq("auditFlag", "0"));
				dc.createAlias("updateBy", "updateBy");
				dc.add(Restrictions.eq("updateBy.userType", "4"));
			} else if (product.getAuditFlag().equals("1")) {
				dc.add(Restrictions.eq("auditFlag", "1"));
			} else if (product.getAuditFlag().equals("2")) {
				dc.createAlias("updateBy", "updateBy");
				dc.add(Restrictions.eq("updateBy.userType", "1"));
				dc.add(Restrictions.eq("auditFlag", "0"));
			}
		}
		if (StringUtils.isNotBlank(product.getStatus())) {
			dc.add(Restrictions.eq("status", product.getStatus()));
		}
		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}

	public Page<Product> findProductForAdmit(Page<Product> page, Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(product.getName())) {
			dc.add(Restrictions.like("name", "%" + product.getName() + "%"));
		}
		if (null != product.getScenic()
				&& StringUtils.isNotEmpty(product.getScenic().getName())) {
			dc.createAlias("scenic", "scenic");
			dc.add(Restrictions.like("scenic.name", "%"
					+ product.getScenic().getName() + "%"));
		}

		if (StringUtils.isNotBlank(product.getStopTime())
				&& product.getStopTime().equals("1")) {
			String nowDate = DateUtils.getDate();
			dc.add(Restrictions.or(Restrictions.isNull("startTime"),
					Restrictions.le("startTime", nowDate)));
			dc.add(Restrictions.or(Restrictions.isNull("stopTime"),
					Restrictions.ge("stopTime", nowDate)));
			dc.add(Restrictions.eq("auditFlag", "0"));
			dc.add(Restrictions.eq("status", "0"));
		} else {
			String nowDate = DateUtils.getDate();
			dc.add(Restrictions.or(Restrictions.isNull("startTime"),
					Restrictions.le("startTime", nowDate)));
			dc.add(Restrictions.or(Restrictions.isNull("stopTime"),
					Restrictions.ge("stopTime", nowDate)));
			// 已审核
			dc.add(Restrictions.eq("auditFlag", "1"));
			dc.add(Restrictions.eq("status", "0"));
		}

		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}

	public Page<Product> findProductForDistributor(Page<Product> page,
			Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(product.getName())) {
			dc.add(Restrictions.like("name", "%" + product.getName() + "%"));
		}
		if (null != product.getScenic() && product.getScenic().getId() != null) {
			dc.createAlias("scenic", "scenic");
			dc.add(Restrictions.eq("scenic.id", product.getScenic().getId()));
		}

		String nowDate = DateUtils.getDate();
		dc.add(Restrictions.or(Restrictions.isNull("startTime"),
				Restrictions.le("startTime", nowDate)));
		dc.add(Restrictions.or(Restrictions.isNull("stopTime"),
				Restrictions.ge("stopTime", nowDate)));

		dc.add(Restrictions.eq("auditFlag", "1"));
		dc.add(Restrictions.eq("status", "0"));
		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}

	public Page<Product> findByAuditFlag(Page<Product> page, Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(product.getName())) {
			dc.add(Restrictions.like("name", "%" + product.getName() + "%"));
		}

		String nowDate = DateUtils.getDate();
		dc.add(Restrictions.or(Restrictions.isNull("startTime"),
				Restrictions.le("startTime", nowDate)));
		dc.add(Restrictions.or(Restrictions.isNull("stopTime"),
				Restrictions.ge("stopTime", nowDate)));

		dc.add(Restrictions.eq("auditFlag", "0"));
		dc.add(Restrictions.eq("status", "0"));
		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));

		dc.createAlias("scenic", "scenic");
		dc.add(Restrictions.eq("scenic.delFlag", Supplier.DEL_FLAG_NORMAL));

		dc.addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Product product) {
		if (StringUtils.isBlank(product.getNo())) {
			String hashcode = product.getName().hashCode() + "";
			String no = sysIdentityService.nextId("product_no")
					+ hashcode.substring(hashcode.length() - 2);
			product.setNo(no);
		}
		productDao.save(product);
	}

	@Transactional(readOnly = false)
	public void delete(String no) {
		productDao.deleteByNo(no);
	}

	public boolean isHasOptPermission(Product product) {
		User user = UserUtils.getUser();
		if (product.getCreateBy() == null
				|| product.getCreateBy().getId() == user.getId()) {
			return true;
		}
		if (SecurityUtils.getSubject().isPermitted("biz:product:manage")) {
			return true;
		}
		return false;
	}
}
