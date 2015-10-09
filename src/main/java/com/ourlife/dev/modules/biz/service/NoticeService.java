package com.ourlife.dev.modules.biz.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.common.utils.StringUtils;
import com.ourlife.dev.modules.biz.dao.NoticeDao;
import com.ourlife.dev.modules.biz.entity.Notice;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SysUploadInfoService;
import com.ourlife.dev.modules.sys.utils.AESCipherUtil;
import com.ourlife.dev.modules.sys.utils.UserUtils;

/**
 * 公告Service
 * 
 * @author ourlife
 * @version 2014-05-25
 */
@Component
@Transactional(readOnly = true)
public class NoticeService extends BaseService {

	@Autowired
	private NoticeDao noticeDao;

	public Notice get(Long id) {
		return noticeDao.findOne(id);
	}

	public Page<Notice> find(Page<Notice> page, Notice notice) {
		DetachedCriteria dc = noticeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(notice.getTitle())) {
			dc.add(Restrictions.like("title", "%" + notice.getTitle() + "%"));
		}
		User user = UserUtils.getUser();
		if (user.getUserType().equals("3")) {
			Object[] values = new String[] { "0", "3" };
			dc.add(Restrictions.in("type", values));
		} else if (user.getUserType().equals("4")) {
			Object[] values = new String[] { "0", "4" };
			dc.add(Restrictions.in("type", values));
		}
		dc.add(Restrictions.eq(Notice.DEL_FLAG, Notice.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("top"));
		dc.addOrder(Order.desc("id"));
		return noticeDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Notice notice) {
		noticeDao.save(notice);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		noticeDao.deleteById(id);
	}

	public Notice get(String idKey) {
		String fid = AESCipherUtil.decrypt(idKey, SysUploadInfoService.KEY);
		Long id = Long.parseLong(fid);
		return noticeDao.findOne(id);
	}

}
