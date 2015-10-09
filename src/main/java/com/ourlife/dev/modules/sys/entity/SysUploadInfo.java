package com.ourlife.dev.modules.sys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.modules.sys.service.SysUploadInfoService;
import com.ourlife.dev.modules.sys.utils.AESCipherUtil;

@Entity
@Table(name = "sys_upload_info")
public class SysUploadInfo extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String filePath;
	private String fileName;
	private String fileType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Transient
	public String getIdKey() {
		return AESCipherUtil.encrypt(this.id + "", SysUploadInfoService.KEY);
	}
}
