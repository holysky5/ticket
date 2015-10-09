package com.ourlife.dev.modules.sys.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.sys.entity.Identity;

/**
 * 流水号DAO接口
 * 
 * @author peng.liao
 * @version 2014-01-21
 */
public interface IdentityDao extends IdentityDaoCustom,
		CrudRepository<Identity, Long> {

	@Modifying
	@Query("update Identity set delFlag='" + Identity.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);

	@Query("select a from Identity a where a.alias = ?1")
	public Identity findByAlias(String alias);

}

/**
 * DAO自定义接口
 * 
 * @author peng.liao
 */
interface IdentityDaoCustom extends BaseDao<Identity> {

}

/**
 * DAO自定义接口实现
 * 
 * @author peng.liao
 */
@Repository
class IdentityDaoImpl extends BaseDaoImpl<Identity> implements
		IdentityDaoCustom {

}
