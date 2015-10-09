package com.ourlife.dev.modules.book.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.book.entity.TuanOrder;

/**
 * 团购订单DAO接口
 * @author ourlife
 * @version 2014-10-13
 */
public interface TuanOrderDao extends TuanOrderDaoCustom, CrudRepository<TuanOrder, Long> {

	@Modifying
	@Query("update TuanOrder set delFlag='" + TuanOrder.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface TuanOrderDaoCustom extends BaseDao<TuanOrder> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class TuanOrderDaoImpl extends BaseDaoImpl<TuanOrder> implements TuanOrderDaoCustom {

}
