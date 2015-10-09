package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Notice;

/**
 * 公告DAO接口
 * @author ourlife
 * @version 2014-05-25
 */
public interface NoticeDao extends NoticeDaoCustom, CrudRepository<Notice, Long> {

	@Modifying
	@Query("update Notice set delFlag='" + Notice.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface NoticeDaoCustom extends BaseDao<Notice> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDaoCustom {

}
