package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Supplier;

/**
 * 供应商信息DAO接口
 * 
 * @author ourlife
 * @version 2014-05-21
 */
public interface SupplierDao extends SupplierDaoCustom,
		CrudRepository<Supplier, Long> {

	@Modifying
	@Query("update Supplier set delFlag='" + Supplier.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Supplier where no = ?1 and delFlag = '"
			+ Supplier.DEL_FLAG_NORMAL + "'")
	public Supplier findByNo(String no);

}

/**
 * DAO自定义接口
 * 
 * @author ourlife
 */
interface SupplierDaoCustom extends BaseDao<Supplier> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ourlife
 */
@Component
class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements
		SupplierDaoCustom {

}
