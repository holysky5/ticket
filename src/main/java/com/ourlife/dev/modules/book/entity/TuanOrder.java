package com.ourlife.dev.modules.book.entity;

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
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.common.utils.excel.annotation.ExcelField;

/**
 * 团购订单Entity
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Entity
@Table(name = "tuan_Order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TuanOrder extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	private String no;
	private String tuanType;

	private TuanProduct product;
	private String title;

	private String useDate;

	private String name; // 名称
	private String mobile;
	private String nameSfz;

	private String notice;
	private String ticket;

	private String status;

	@ExcelField(title = "订单编号", type = 1, align = 1, sort = 1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@ExcelField(title = "团购网站", type = 1, align = 1, sort = 2)
	public String getTuanType() {
		return tuanType;
	}

	public void setTuanType(String tuanType) {
		this.tuanType = tuanType;
	}

	@ManyToOne
	@JoinColumn(name = "product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@ExcelField(value = "product.title", title = "产品标题", type = 1, align = 1, sort = 3)
	public TuanProduct getProduct() {
		return product;
	}

	public void setProduct(TuanProduct product) {
		this.product = product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ExcelField(title = "使用日期", type = 1, align = 1, sort = 4)
	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	@Length(min = 1, max = 200)
	@ExcelField(title = "姓名", type = 1, align = 1, sort = 5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title = "手机号码", type = 1, align = 1, sort = 6)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "身份证号码", type = 1, align = 1, sort = 7)
	public String getNameSfz() {
		return nameSfz;
	}

	public void setNameSfz(String nameSfz) {
		this.nameSfz = nameSfz;
	}

	@ExcelField(title = "备注", type = 1, align = 1, sort = 8)
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@ExcelField(title = "券号", type = 1, align = 1, sort = 9)
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@ExcelField(title = "状态", type = 1, align = 1, sort = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TuanOrder() {
		super();
	}

	public TuanOrder(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_book_tuanOrder")
	// @SequenceGenerator(name = "seq_book_tuanOrder", sequenceName =
	// "seq_book_tuanOrder")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
