package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Direct;
import com.ourlife.dev.modules.biz.entity.DirectPrice;

/**
 * 直通景区DAO接口
 * 
 * @author ourlife
 * @version 2014-06-22
 */
public interface DirectPriceDao extends DirectPriceDaoCustom,
		CrudRepository<DirectPrice, Long> {

	@Modifying
	@Query("delete DirectPrice where id = ?1")
	public int deleteById(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author ourlife
 */
interface DirectPriceDaoCustom extends BaseDao<Direct> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ourlife
 */
@Component
class DirectPriceDaoImpl extends BaseDaoImpl<Direct> implements
		DirectPriceDaoCustom {

}
