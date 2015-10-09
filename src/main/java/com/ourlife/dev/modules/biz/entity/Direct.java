package com.ourlife.dev.modules.biz.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ourlife.dev.common.persistence.DataEntity;

/**
 * 直通景区Entity
 * 
 * @author ourlife
 * @version 2014-06-22
 */
@Entity
@Table(name = "biz_direct")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Direct extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	/** 供应商编号. */
	private Supplier supplier;

	/** 分销商编号. */
	private Distributor distributor;

	/** 状态. */
	private String status;

	/** 账户余额. */
	private Double balance = 0.0;

	private List<DirectPrice> directPriceList = Lists.newArrayList();

	private Map<Long, Double> directPriceMap = Maps.newHashMap();

	public Direct() {
		super();
	}

	public Direct(Long id) {
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
	@JoinColumn(name = "supplier_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne
	@JoinColumn(name = "distributor_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "direct")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<DirectPrice> getDirectPriceList() {
		return directPriceList;
	}

	public void setDirectPriceList(List<DirectPrice> directPriceList) {
		this.directPriceList = directPriceList;
	}

	@Transient
	public Map<Long, Double> getDirectPriceMap() {
		for (DirectPrice price : directPriceList) {
			directPriceMap.put(price.getProduct().getId(), price.getPrice());
		}
		return directPriceMap;
	}

	public void setDirectPriceMap(Map<Long, Double> directPriceMap) {
		this.directPriceMap = directPriceMap;
	}

}
