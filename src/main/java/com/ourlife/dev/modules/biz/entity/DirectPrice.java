package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "biz_product_direct_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DirectPrice {
	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 直通编号. */
	private Direct direct;

	/** 产品ID. */
	private Product product;

	/** 产品价格. */
	private Double price;

	public DirectPrice() {
		super();
	}

	public DirectPrice(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_direct")
	// @SequenceGenerator(name = "seq_biz_direct", sequenceName =
	// "seq_biz_direct")
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
	public Direct getDirect() {
		return direct;
	}

	public void setDirect(Direct direct) {
		this.direct = direct;
	}

	@ManyToOne
	@JoinColumn(name = "product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
