package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.ProductHistory;

/**
 * 余额历史DAO接口
 * @author ourlife
 * @version 2014-05-26
 */
public interface ProductHistoryDao extends ProductHistoryDaoCustom, CrudRepository<ProductHistory, Long> {

	@Modifying
	@Query("update ProductHistory set delFlag='" + ProductHistory.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface ProductHistoryDaoCustom extends BaseDao<ProductHistory> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class ProductHistoryDaoImpl extends BaseDaoImpl<ProductHistory> implements ProductHistoryDaoCustom {

}
