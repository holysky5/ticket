package com.ourlife.dev.modules.sys.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.sys.entity.SysUploadInfo;

/**
 * 文件上传信息DAO接口
 * 
 * @author allen
 * @version 2014-02-26
 */
public interface SysUploadInfoDao extends SysUploadInfoDaoCustom,
		CrudRepository<SysUploadInfo, Long> {

	@Modifying
	@Query("update SysUploadInfo set delFlag='" + SysUploadInfo.DEL_FLAG_DELETE
			+ "' where id = ?1")
	public int deleteById(Long id);
}

/**
 * DAO自定义接口
 * 
 * @author ThinkGem
 */
interface SysUploadInfoDaoCustom extends BaseDao<SysUploadInfo> {

}

/**
 * DAO自定义接口实现
 * 
 * @author ThinkGem
 */
@Repository
class SysUploadInfoDaoImpl extends BaseDaoImpl<SysUploadInfo> implements
		SysUploadInfoDaoCustom {

}
