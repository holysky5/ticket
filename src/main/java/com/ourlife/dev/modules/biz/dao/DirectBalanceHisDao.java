package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.DirectBalanceHis;

/**
 * 直通余额DAO接口
 * @author ourlife
 * @version 2014-06-24
 */
public interface DirectBalanceHisDao extends DirectBalanceHisDaoCustom, CrudRepository<DirectBalanceHis, Long> {

	@Modifying
	@Query("update DirectBalanceHis set delFlag='" + DirectBalanceHis.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface DirectBalanceHisDaoCustom extends BaseDao<DirectBalanceHis> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class DirectBalanceHisDaoImpl extends BaseDaoImpl<DirectBalanceHis> implements DirectBalanceHisDaoCustom {

}
