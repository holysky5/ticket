package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.modules.sys.service.SysUploadInfoService;
import com.ourlife.dev.modules.sys.utils.AESCipherUtil;

/**
 * 公告Entity
 * 
 * @author ourlife
 * @version 2014-05-25
 */
@Entity
@Table(name = "biz_notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 标题. */
	private String title;

	/** 内容. */
	private String content;

	/** 查看对象. */
	private String type;

	/** 状态. */
	private String status;

	/** 状态. */
	private String top;

	public Notice() {
		super();
	}

	public Notice(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_notice")
	// @SequenceGenerator(name = "seq_biz_notice", sequenceName =
	// "seq_biz_notice")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getIdKey() {
		return AESCipherUtil.encrypt(this.id + "", SysUploadInfoService.KEY);
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

}
