package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.Product;

/**
 * 门票详情DAO接口
 * 
 * @author ourlife
 * @version 2014-05-24
 */
public interface ProductDao extends ProductDaoCustom,
		CrudRepository<Product, Long> {

	@Modifying
	@Query("update Product set delFlag='" + Product.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);

	@Modifying
	@Query("update Product set delFlag='" + Product.DEL_FLAG_DELETE
			+ "' where no = ?1")
	public int deleteByNo(String no);

	@Query("from Product where delFlag='" + Product.DEL_FLAG_NORMAL
			+ "' and no = ?1")
	public Product findByNo(String no);

}

/**
 * DAO自定义接口
 * 
 * @author ourlife
 */
interface ProductDaoCustom extends BaseDao<Product> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ourlife
 */
@Component
class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDaoCustom {

}
