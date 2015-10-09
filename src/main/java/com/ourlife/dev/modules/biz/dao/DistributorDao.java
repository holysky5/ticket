package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Distributor;

/**
 * 分销商DAO接口
 * 
 * @author ourlife
 * @version 2014-05-24
 */
public interface DistributorDao extends DistributorDaoCustom,
		CrudRepository<Distributor, Long> {

	@Modifying
	@Query("update Distributor set delFlag='" + Distributor.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Distributor where username = ?1 and delFlag = '"
			+ Distributor.DEL_FLAG_NORMAL + "'")
	public Distributor findByUsername(String username);

}

/**
 * DAO自定义接口
 * 
 * @author ourlife
 */
interface DistributorDaoCustom extends BaseDao<Distributor> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ourlife
 */
@Component
class DistributorDaoImpl extends BaseDaoImpl<Distributor> implements
		DistributorDaoCustom {

}
