package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourlife.dev.common.persistence.DataEntity;

/**
 * 景区详情Entity
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Entity
@Table(name = "biz_scenic_Detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScenicDetail extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 景区编号. */
	private Supplier scenic;

	/** 类型. */
	private String type;

	/** 内容. */
	private String content;

	@ManyToOne
	@JoinColumn(name = "scenic_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "所属景区不能为空")
	public Supplier getScenic() {
		return scenic;
	}

	public void setScenic(Supplier scenic) {
		this.scenic = scenic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ScenicDetail() {
		super();
	}

	public ScenicDetail(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_scenicDetail")
	// @SequenceGenerator(name = "seq_biz_scenicDetail", sequenceName =
	// "seq_biz_scenicDetail")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
