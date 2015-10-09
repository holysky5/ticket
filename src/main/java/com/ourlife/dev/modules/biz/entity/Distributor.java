package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.ourlife.dev.common.persistence.DataEntity;

/**
 * 分销商Entity
 * 
 * @author ourlife
 * @version 2014-05-24
 */
@Entity
@Table(name = "biz_distributor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Distributor extends DataEntity {

	private static final long serialVersionUID = 1L;
	public static final String TYPE_VALUE = "3";

	private Long id; // 编号

	/** 用户名. */
	private String username;

	/** 密码. */
	private String password;

	/** 姓名. */
	private String name;

	/** 公司名称. */
	private String company;

	/** 电话. */
	private String phone;

	/** 手机. */
	private String mobile;

	/** 邮箱. */
	private String email;

	/** QQ. */
	private String qq;

	/** 营业执照. */
	private String businessLicense;

	/** 联系地址. */
	private String address;

	/** 公司简介. */
	private String introduce;

	/** 分组. */
	private String groupName;

	/** 排序. */
	private Integer sort = 10;

	/** 状态. */
	private String status;

	/** 余额. */
	private Double balance = 0.0;

	public Distributor() {
		super();
	}

	public Distributor(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_distributor")
	// @SequenceGenerator(name = "seq_biz_distributor", sequenceName =
	// "seq_biz_distributor")
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
