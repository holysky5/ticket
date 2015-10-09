package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.OrderLog;

/**
 * 订单日志DAO接口
 * @author ourlife
 * @version 2014-06-28
 */
public interface OrderLogDao extends OrderLogDaoCustom, CrudRepository<OrderLog, Long> {

	@Modifying
	@Query("update OrderLog set delFlag='" + OrderLog.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface OrderLogDaoCustom extends BaseDao<OrderLog> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class OrderLogDaoImpl extends BaseDaoImpl<OrderLog> implements OrderLogDaoCustom {

}
