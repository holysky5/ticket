package com.ourlife.dev.modules.biz.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.ScenicDetail;

/**
 * 景区详情DAO接口
 * @author ourlife
 * @version 2014-05-24
 */
public interface ScenicDetailDao extends ScenicDetailDaoCustom, CrudRepository<ScenicDetail, Long> {

	@Modifying
	@Query("update ScenicDetail set delFlag='" + ScenicDetail.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ourlife
 */
interface ScenicDetailDaoCustom extends BaseDao<ScenicDetail> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class ScenicDetailDaoImpl extends BaseDaoImpl<ScenicDetail> implements ScenicDetailDaoCustom {

}
