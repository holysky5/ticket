package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Direct;

/**
 * 直通景区DAO接口
 * @author ourlife
 * @version 2014-06-22
 */
public interface DirectDao extends DirectDaoCustom, CrudRepository<Direct, Long> {

	@Modifying
	@Query("update Direct set delFlag='" + Direct.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface DirectDaoCustom extends BaseDao<Direct> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class DirectDaoImpl extends BaseDaoImpl<Direct> implements DirectDaoCustom {

}
