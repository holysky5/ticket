package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ourlife.dev.common.utils.DoubleUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.modules.sys.entity.User;

/**
 * 余额历史Entity
 *
 * @author ourlife
 * @version 2014-05-25
 */
@Entity
@Table(name = "biz_balanceHis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BalanceHis extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 用户编号. */
	private User user;

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

	public BalanceHis() {
		super();
	}

	public BalanceHis(Long id) {
		this();
		this.id = id;
	}

	/**
	 * 支付宝转账得金额变更记录
	 * @return
	 */
	public static BalanceHis withTransformAccount(Double begin,Double amount) {
		BalanceHis bh = new BalanceHis();
		bh.setStatus("-2");
		bh.setType("0");
		bh.setReason("在线充值");
		bh.setRemarks("支付宝转账");
		bh.setBegin(begin);
		bh.setAmount(amount);
		bh.setEnd(DoubleUtils.add(begin,amount));
		return bh;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_balanceHis")
	// @SequenceGenerator(name = "seq_biz_balanceHis", sequenceName =
	// "seq_biz_balanceHis")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
