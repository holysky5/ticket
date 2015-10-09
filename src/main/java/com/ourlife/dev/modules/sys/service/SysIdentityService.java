package com.ourlife.dev.modules.sys.service;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.modules.sys.dao.IdentityDao;
import com.ourlife.dev.modules.sys.entity.Identity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 流水号Service
 *
 * @author peng.liao
 * @version 2014-01-21
 */
@Component
@Transactional(readOnly = true)
public class SysIdentityService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(SysIdentityService.class);

	@Autowired
	private IdentityDao identityDao;

	public Identity get(Long id) {
		return identityDao.findOne(id);
	}

	public Page<Identity> find(Page<Identity> page, Identity identity) {
		DetachedCriteria dc = identityDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(identity.getName())) {
			dc.add(Restrictions.like("name", "%" + identity.getName() + "%"));
		}
		dc.add(Restrictions.eq(Identity.DEL_FLAG, Identity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return identityDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Identity identity) {
		identityDao.save(identity);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		identityDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public synchronized String nextId(String alias) {
		Identity identity = identityDao.findByAlias(alias);
		String rule = identity.getRule();
		int step = identity.getStep().shortValue();
		int genEveryDay = Integer.valueOf(identity.getGenEveryDay());
		Integer curValue = identity.getCurValue();
		if (curValue == null) {
			curValue = identity.getInitValue();
		}
		// genEveryDay 0唯一 1每天 2每月 3每年
		if (genEveryDay == 1) {
			String curDate = getCurDate();
			String oldDate = identity.getCurDate();
			if (!curDate.equals(oldDate)) {
				identity.setCurDate(curDate);
				curValue = identity.getInitValue();
			} else {
				curValue = Integer.valueOf(curValue.intValue() + step);
			}
		} else if (genEveryDay == 2) {
			String curDate = getCurDate().substring(0, 7);
			String oldDate = identity.getCurDate();
			if (!curDate.equals(oldDate)) {
				identity.setCurDate(curDate);
				curValue = identity.getInitValue();
			} else {
				curValue = Integer.valueOf(curValue.intValue() + step);
			}
		} else if (genEveryDay == 3) {
			String curDate = getCurDate().substring(0, 4);
			String oldDate = identity.getCurDate();
			if (!curDate.equals(oldDate)) {
				identity.setCurDate(curDate);
				curValue = identity.getInitValue();
			} else {
				curValue = Integer.valueOf(curValue.intValue() + step);
			}
		} else {
			curValue = Integer.valueOf(curValue.intValue() + step);
		}
		identity.setCurValue(curValue);
		identityDao.save(identity);

		String rtn = getByRule(rule, identity.getNoLength().intValue(),
				curValue.intValue()).toUpperCase();

		return rtn;
	}

	public static String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	private String getByRule(String rule, int length, int curValue) {
		Calendar calendar = Calendar.getInstance();

		String year = calendar.get(Calendar.YEAR) + "";
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		String longMonth = String.format("%1$02d", month);

		String seqNo = getSeqNo(rule, curValue, length);

		String longDay = String.format("%1$02d", day);

		return rule.replace("{YYYY}", year).replace("{yyyy}", year)
				.replace("{YY}", year.substring(2))
				.replace("{yy}", year.substring(2)).replace("{MM}", longMonth)
				.replace("{mm}", longMonth).replace("{DD}", longDay)
				.replace("{dd}", longDay).replace("{NO}", seqNo)
				.replace("{no}", seqNo);

	}

	private static String getSeqNo(String rule, int curValue, int length) {
		return String.format("%1$0" + length + "d", curValue);
	}

	/**
	 * @param alias
	 * @return
	 */
	public boolean isAliasExisted(String alias) {
		if (identityDao.findByAlias(alias) != null) {
			return true;
		}
		return false;
	}

}
