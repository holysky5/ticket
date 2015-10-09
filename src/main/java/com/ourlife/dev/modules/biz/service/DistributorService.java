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
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.DistributorDao;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.sys.entity.Role;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysIdentityService;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 分销商Service
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Component
@Transactional(readOnly = true)
public class DistributorService extends BaseService {

	@Autowired
	private DistributorDao distributorDao;

	@Autowired
	private SystemService systemService;

	@Autowired
	private SysIdentityService sysIdentityService;

	public Distributor get(Long id) {
		return distributorDao.findOne(id);
	}

	public Page<Distributor> find(Page<Distributor> page,
			Distributor distributor) {
		DetachedCriteria dc = distributorDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(distributor.getName())) {
			dc.add(Restrictions.or(Restrictions.like("name",
					"%" + distributor.getName() + "%"), Restrictions.like(
					"username", "%" + distributor.getName() + "%"),
					Restrictions.like("company", "%" + distributor.getName()
							+ "%")));
		}
		Object[] values = new String[] { "0", "1" };
		dc.add(Restrictions.in("status", values));

		dc.add(Restrictions.eq(Distributor.DEL_FLAG,
				Distributor.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return distributorDao.find(page, dc);
	}

	public Page<Distributor> findAudit(Page<Distributor> page,
			Distributor distributor) {
		DetachedCriteria dc = distributorDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(distributor.getName())) {
			dc.add(Restrictions.or(Restrictions.like("name",
					"%" + distributor.getName() + "%"), Restrictions.like(
					"username", "%" + distributor.getName() + "%"),
					Restrictions.like("company", "%" + distributor.getName()
							+ "%")));
		}

		if (StringUtils.isNotEmpty(distributor.getStatus())) {
			dc.add(Restrictions.eq("status", distributor.getStatus()));
		}

		dc.add(Restrictions.eq(Distributor.DEL_FLAG,
				Distributor.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return distributorDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Distributor distributor) {
		if (distributor.getId() == null && distributor.getStatus().equals("0")) {
			String hashcode = distributor.getName().hashCode() + "";
			String no = sysIdentityService.nextId("distributor_no")
					+ hashcode.substring(hashcode.length() - 2);

			User user = new User();
			user.setCompany(UserUtils.getUser().getCompany());
			user.setOffice(UserUtils.getUser().getOffice());
			user.setMobile(distributor.getMobile());
			user.setPhone(distributor.getPhone());
			user.setEmail(distributor.getEmail());
			user.setRemarks(distributor.getCompany());

			user.setLoginName(distributor.getUsername());
			user.setName(distributor.getName());
			user.setNo(no);
			user.setPassword(distributor.getPassword());
			user.setUserType(Distributor.TYPE_VALUE);
			List<Role> roleList = Lists.newArrayList();
			roleList.add(systemService.getRole(Long
					.valueOf(Distributor.TYPE_VALUE)));
			user.setRoleList(roleList);

			systemService.saveUser(user);
		} else {
			if (distributor.getId() != null
					&& distributor.getStatus().equals("0")) {
				User user = systemService.getUserByLoginName(distributor
						.getUsername());
				user.setMobile(distributor.getMobile());
				user.setPhone(distributor.getPhone());
				user.setEmail(distributor.getEmail());
				user.setRemarks(distributor.getCompany());

				user.setLoginName(distributor.getUsername());
				user.setName(distributor.getName());
				user.setNo(distributor.getUsername());
				user.setPassword(distributor.getPassword());
				systemService.saveUser(user);
			}

		}
		distributorDao.save(distributor);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		Distributor distributor = get(id);
		User user = systemService.getUserByLoginName(distributor.getUsername());
		systemService.deleteUser(user.getId());
		distributorDao.deleteById(id);
	}

	public Distributor getUserByUsername(String username) {
		return distributorDao.findByUsername(username);
	}

	@Transactional(readOnly = false)
	public void auditSave(Distributor distributor) {
		if (distributor.getStatus().equals("0")) {
			String hashcode = distributor.getName().hashCode() + "";
			String no = sysIdentityService.nextId("distributor_no")
					+ hashcode.substring(hashcode.length() - 2);

			User user = new User();
			user.setCompany(UserUtils.getUser().getCompany());
			user.setOffice(UserUtils.getUser().getOffice());
			user.setMobile(distributor.getMobile());
			user.setPhone(distributor.getPhone());
			user.setEmail(distributor.getEmail());

			user.setLoginName(distributor.getUsername());
			user.setName(distributor.getName());
			user.setNo(no);
			user.setPassword(distributor.getPassword());
			user.setUserType(Distributor.TYPE_VALUE);
			List<Role> roleList = Lists.newArrayList();
			roleList.add(systemService.getRole(Long
					.valueOf(Distributor.TYPE_VALUE)));
			user.setRoleList(roleList);

			systemService.saveUser(user);
		}
		distributorDao.save(distributor);

	}

	public Double statTotalAmount() {
		String sql = "select sum(balance)  from biz_distributor";
		List<Object> list = distributorDao.findBySql(sql);
		for (Object o : list) {
			return (Double) o;
		}
		return 0.0;
	}

}
