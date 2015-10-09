package com.ourlife.dev.modules.biz.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.SupplierDao;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.sys.entity.Role;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysIdentityService;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 供应商信息Service
 * 
 * @author ourlife
 * @version 2014-05-21
 */
@Component
@Transactional(readOnly = true)
public class SupplierService extends BaseService {

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private SysIdentityService sysIdentityService;

	@Autowired
	private SystemService systemService;

	public Supplier get(Long id) {
		supplierDao.clear();
		return supplierDao.findOne(id);
	}

	public Page<Supplier> find(Page<Supplier> page, Supplier supplier) {
		DetachedCriteria dc = supplierDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(supplier.getName())) {
			dc.add(Restrictions.like("name", "%" + supplier.getName() + "%"));
		}
		if (StringUtils.isNotEmpty(supplier.getArea())) {
			dc.add(Restrictions.like("area", "%" + supplier.getArea() + "%"));
		}
		if (StringUtils.isNotEmpty(supplier.getStatus())) {
			dc.add(Restrictions.like("status", supplier.getStatus()));
		}
		dc.add(Restrictions.eq(Supplier.DEL_FLAG, Supplier.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return supplierDao.find(page, dc);
	}

	public Page<Supplier> findOrderList(Page<Supplier> page, Supplier supplier) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * from  biz_supplier s where s.del_flag = '0' ");
		sb.append(" and s.status= '0'");
		if (StringUtils.isNotEmpty(supplier.getName())) {
			sb.append(" and s.name like '%" + supplier.getName() + "%'");
		}
		if (StringUtils.isNotEmpty(supplier.getArea())) {
			sb.append(" and s.area like '%" + supplier.getArea() + "%'");
		}
		// 有效产品>1
		sb.append(" and (SELECT count(1) from biz_product  p where p.del_flag = '0'  ");
		sb.append("  and p.supplier_id=s.id");
		String nowDate = DateUtils.getDate();
		sb.append(" and p.audit_Flag='1'");
		sb.append(" and p.status='0'");
		sb.append(" and (p.start_Time='' OR p.start_Time is NULL or p.start_Time <='"
				+ nowDate + "')");
		sb.append(" and (p.stop_Time='' OR p.stop_Time is NULL or p.stop_Time >='"
				+ nowDate + "')");
		sb.append(" ) > 0");

		sb.append(" order by id desc");

		logger.debug(sb.toString());

		return supplierDao.findBySql(page, sb.toString(), Supplier.class);
	}

	public Page<Supplier> findOrderNotList(Page<Supplier> page,
			Supplier supplier) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * from  biz_supplier s where s.del_flag = '0' ");
		sb.append(" and s.status= '0'");
		if (StringUtils.isNotEmpty(supplier.getName())) {
			sb.append(" and s.name like '%" + supplier.getName() + "%'");
		}
		if (StringUtils.isNotEmpty(supplier.getArea())) {
			sb.append(" and s.area like '%" + supplier.getArea() + "%'");
		}
		// 有效产品>1
		sb.append(" and (SELECT count(1) from biz_product  p where p.del_flag = '0'  ");
		sb.append("  and p.supplier_id=s.id");
		String nowDate = DateUtils.getDate();
		sb.append(" and p.audit_Flag='0'");
		sb.append(" and p.status='0'");
		sb.append(" and (p.start_Time='' OR p.start_Time is NULL or p.start_Time <='"
				+ nowDate + "')");
		sb.append(" and (p.stop_Time='' OR p.stop_Time is NULL or p.stop_Time >='"
				+ nowDate + "')");
		sb.append(" ) > 0");

		sb.append(" order by id desc");

		logger.debug(sb.toString());

		return supplierDao.findBySql(page, sb.toString(), Supplier.class);
	}

	@Transactional(readOnly = false)
	public void save(Supplier supplier) {
		if (supplier.getId() == null || StringUtils.isBlank(supplier.getNo())) {
			String hashcode = supplier.getName().hashCode() + "";
			String no = sysIdentityService.nextId("supplier_no")
					+ hashcode.substring(hashcode.length() - 2);
			if (StringUtils.isBlank(supplier.getNo())) {
				supplier.setNo(no);
			}
			supplier.setBalance(0.0);
			supplier.setStatus("0");

			User user = new User();
			user.setCompany(UserUtils.getUser().getCompany());
			user.setOffice(UserUtils.getUser().getOffice());
			user.setMobile(supplier.getContactMobile());
			user.setPhone(supplier.getContactPhone());
			user.setEmail(supplier.getContactEmail());
			user.setRemarks(supplier.getNo());

			user.setLoginName(supplier.getNo());
			user.setName(supplier.getName());
			user.setNo(no);
			if (supplier.getPassword() == null) {
				user.setPassword(SystemService
						.entryptPassword(Supplier.DEFAULT_PWD));
			} else {
				user.setPassword(supplier.getPassword());
			}

			user.setUserType(Supplier.TYPE_VALUE);
			List<Role> roleList = Lists.newArrayList();
			roleList.add(systemService.getRole(Long
					.valueOf(Supplier.TYPE_VALUE)));
			user.setRoleList(roleList);

			systemService.saveUser(user);

		} else {
			User user = systemService.getUserByLoginName(supplier.getNo());
			user.setCompany(UserUtils.getUser().getCompany());
			user.setOffice(UserUtils.getUser().getOffice());
			user.setMobile(supplier.getContactMobile());
			user.setPhone(supplier.getContactPhone());
			user.setEmail(supplier.getContactEmail());
			user.setRemarks(supplier.getNo());

			user.setLoginName(supplier.getNo());
			user.setName(supplier.getName());
			user.setNo(supplier.getNo());
			user.setPassword(supplier.getPassword());

			systemService.saveUser(user);
		}
		supplierDao.save(supplier);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		Supplier supplier = get(id);
		User user = systemService.getUserByLoginName(supplier.getNo());
		systemService.deleteUser(user.getId());
		supplierDao.deleteById(id);
	}

	public Supplier getSupplierByNo(String no) {
		return supplierDao.findByNo(no);
	}

	public Double statTotalAmount() {
		String sql = "select sum(balance)  from biz_supplier";
		List<Object> list = supplierDao.findBySql(sql);
		for (Object o : list) {
			return (Double) o;
		}
		return 0.0;
	}

}
