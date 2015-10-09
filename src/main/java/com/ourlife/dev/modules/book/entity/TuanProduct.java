package com.ourlife.dev.modules.book.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.common.utils.StringUtils;

/**
 * 团购产品Entity
 * 
 * @author ourlife
 * @version 2014-10-13
 */
@Entity
@Table(name = "tuan_Product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TuanProduct extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号

	private String no;
	private String tuanType;
	private String name; // 名称
	private String title;
	private String province;
	private String city;

	/** 下完订单的生效时间. */
	private String effectTime;

	/** 销售日期. */
	private String beginTime;

	/** 销售日期. */
	private String endTime;

	private String status;

	private String notice;

	public TuanProduct() {
		super();
	}

	public TuanProduct(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_book_tuanProduct")
	// @SequenceGenerator(name = "seq_book_tuanProduct", sequenceName =
	// "seq_book_tuanProduct")
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

	public String getTuanType() {
		return tuanType;
	}

	public void setTuanType(String tuanType) {
		this.tuanType = tuanType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String geteffectTimeDesc() {
		String str = "";
		if (effectTime == null) {
			str = "";
		} else {
			String[] times = effectTime.split(",");
			if (times[0].equals("0")) {
				str = "无预订时间限制";
			} else if (times[0].equals("1")) {
				if (times[1].equals("0")) {
					str = "入园前当天" + times[2] + "时" + times[3] + "分之前预订";
				} else {
					str = "入园前" + times[1] + "天" + times[2] + "时" + times[3]
							+ "分之前预订";
				}

			} else if (times[0].equals("2")) {
				str = "预订后" + times[1] + "小时才能入园";
			}
		}

		return str;
	}

	@Transient
	public String getBeignAndEndTimeDesc() {
		if (StringUtils.isNotBlank(beginTime)
				&& StringUtils.isNotBlank(endTime)) {
			return beginTime + "至" + endTime;
		}
		return "全年销售";
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

}
