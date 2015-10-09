package com.ourlife.dev.modules.book.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.book.entity.TuanProduct;

/**
 * 团购产品DAO接口
 * @author ourlife
 * @version 2014-10-13
 */
public interface TuanProductDao extends TuanProductDaoCustom, CrudRepository<TuanProduct, Long> {

	@Modifying
	@Query("update TuanProduct set delFlag='" + TuanProduct.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface TuanProductDaoCustom extends BaseDao<TuanProduct> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class TuanProductDaoImpl extends BaseDaoImpl<TuanProduct> implements TuanProductDaoCustom {

}
