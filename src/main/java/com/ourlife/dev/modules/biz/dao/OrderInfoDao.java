package com.ourlife.dev.modules.biz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.OrderInfo;

/**
 * 订单信息DAO接口
 * 
 * @author ourlife
 * @version 2014-05-31
 */
public interface OrderInfoDao extends OrderInfoDaoCustom,
		CrudRepository<OrderInfo, Long> {

	@Modifying
	@Query("update OrderInfo set delFlag='" + OrderInfo.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);

	@Query("from OrderInfo where status = ?1 and delFlag = '"
			+ OrderInfo.DEL_FLAG_NORMAL + "'")
	public List<OrderInfo> findByStatus(String status);

	@Query("from OrderInfo where orderId = ?1 and delFlag = '"
			+ OrderInfo.DEL_FLAG_NORMAL + "'")
	public OrderInfo findByOrderId(String orderId);

}

/**
 * DAO自定义接口
 * 
 * @author ourlife
 */
interface OrderInfoDaoCustom extends BaseDao<OrderInfo> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ourlife
 */
@Component
class OrderInfoDaoImpl extends BaseDaoImpl<OrderInfo> implements
		OrderInfoDaoCustom {

}
