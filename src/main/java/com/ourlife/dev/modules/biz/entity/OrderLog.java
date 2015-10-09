package com.ourlife.dev.modules.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourlife.dev.common.persistence.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * 订单日志Entity
 *
 * @author ourlife
 * @version 2014-06-28
 */
@Entity
@Table(name = "biz_order_Log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderLog extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 订单编号. */
	private OrderInfo orderInfo;

	private String name; // 名称

	public OrderLog() {
		super();
	}

	public OrderLog(String log, OrderInfo orderInfo) {
		this.name = log;
		this.orderInfo = orderInfo;
	}

	public OrderLog(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_orderLog")
	// @SequenceGenerator(name = "seq_biz_orderLog", sequenceName =
	// "seq_biz_orderLog")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Length(min = 1, max = 1024)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "order_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

}
