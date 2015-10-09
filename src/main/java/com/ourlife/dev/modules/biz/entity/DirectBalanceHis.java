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
 * 直通余额Entity
 * 
 * @author ourlife
 * @version 2014-06-24
 */
@Entity
@Table(name = "biz_direct_Balance_His")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DirectBalanceHis extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 用户编号. */
	private Direct direct;

	/** 变动前. */
	private Double begin;

	/** 金额. */
	private Double amount;

	/** 类型. 0充值 1订单 2退票 9其它 */
	private String type;

	/** 变动后. */
	private Double end;

	/** 变动原因. */
	private String reason;

	/** 状态. */
	private String status;

	public DirectBalanceHis() {
		super();
	}

	public DirectBalanceHis(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_directBalanceHis")
	// @SequenceGenerator(name = "seq_biz_directBalanceHis", sequenceName =
	// "seq_biz_directBalanceHis")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "direct_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "直通账户不能为空")
	public Direct getDirect() {
		return direct;
	}

	public void setDirect(Direct direct) {
		this.direct = direct;
	}

	public Double getBegin() {
		return begin;
	}

	public void setBegin(Double begin) {
		this.begin = begin;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		this.end = end;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
