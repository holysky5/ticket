package com.ourlife.dev.modules.biz.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ourlife.dev.common.persistence.DataEntity;

/**
 * 供应商信息Entity
 * 
 * @author ourlife
 * @version 2014-05-21
 */
@Entity
@Table(name = "biz_supplier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplier extends DataEntity {

	private static final long serialVersionUID = 1L;

	public static final String TYPE_VALUE = "4";
	public static final String DEFAULT_PWD = "123456";

	private Long id; // 编号

	/** 供应商帐号. */
	private String no;

	/** 密码. */
	private String password;

	/** 供应商名称. */
	private String name;

	/** 所在地区. */
	private String area;

	/** 详细地址. */
	private String address;

	/** 联系人姓名. */
	private String contactName;

	/** 联系人手机. */
	private String contactMobile;

	/** 联系人电话. */
	private String contactPhone;

	/** 联系人邮箱. */
	private String contactEmail;

	/** 排序. */
	private Integer sort = 10;

	/** 状态. */
	private String status;

	/** 账户余额. */
	private Double balance;

	/**
	 * 验票终端 0 票付通 1智游宝
	 */
	private String checkTerminal;

	// ----------------票付通 ---begin--------------

	/** 景区ID. */
	private String uuId;

	/** 商家编号. */
	private String uuSalerId;

	/** 终端编号. */
	private String uuTerminalId;

	// ----------------票付通 ---end----------------

	private List<ScenicDetail> scenicDetailList = Lists.newArrayList();

	private final Map<String, String> scenicDetailMap = Maps.newLinkedHashMap();

	public Supplier() {
		super();
	}

	public Supplier(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_supplier")
	// @SequenceGenerator(name = "seq_biz_supplier", sequenceName =
	// "seq_biz_supplier")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Length(min = 1, max = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	@OneToMany(mappedBy = "scenic")
	@OrderBy(value = "type")
	@JsonIgnore
	public List<ScenicDetail> getScenicDetailList() {
		return scenicDetailList;
	}

	public void setScenicDetailList(List<ScenicDetail> list) {
		this.scenicDetailList = list;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getUuSalerId() {
		return uuSalerId;
	}

	public void setUuSalerId(String uuSalerId) {
		this.uuSalerId = uuSalerId;
	}

	public String getUuTerminalId() {
		return uuTerminalId;
	}

	public void setUuTerminalId(String uuTerminalId) {
		this.uuTerminalId = uuTerminalId;
	}

	@Transient
	public Map<String, String> getScenicDetailMap() {
		if (this.scenicDetailList != null) {
			for (ScenicDetail sd : scenicDetailList) {
				scenicDetailMap.put(sd.getType(), sd.getContent());
			}
		}
		return scenicDetailMap;
	}

	public String getCheckTerminal() {
		return checkTerminal;
	}

	public void setCheckTerminal(String checkTerminal) {
		this.checkTerminal = checkTerminal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
