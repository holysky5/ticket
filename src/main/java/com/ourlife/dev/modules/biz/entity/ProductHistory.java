package com.ourlife.dev.modules.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.common.utils.StringUtils;

/**
 * 门票历史Entity
 * 
 * @author ourlife
 * @version 2014-05-26
 */
@Entity
@Table(name = "biz_product_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductHistory extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // ID

	private Long productId;

	private String no; // 编号

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	private String name; // 名称

	/** 景区名称. */
	private Supplier scenic;

	/** 门票原价.挂牌价 */
	private Double originalPrice;

	/** 建议/指导价格.指导价 */
	private Double recommendPrice;

	/** 门票购买价格.购买价 */
	private Double purchasePrice;

	/** 易往价格. */
	private Double platformPrice;

	/** 门票状态. */
	private String status;

	/** 购买须知. */
	private String notice;

	/** 门票介绍. */
	private String introduction;

	/** 下完订单的生效时间. */
	private String effectTime;

	/** 销售日期. */
	private String beginTime;

	/** 销售日期. */
	private String endTime;

	/** 有效日期. */
	private String startTime;

	/** 有效日期. */
	private String stopTime;

	/** 已审核状态(1已经审核，0未审核). */
	private String auditFlag;

	/** 票付通门票号. */
	private String uuPid;

	/** 操作代码(增加、更新、删除). */
	private String actionCode;

	public ProductHistory() {
		super();
	}

	public ProductHistory(Long id) {
		this();
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "所属景区不能为空")
	public Supplier getScenic() {
		return scenic;
	}

	public void setScenic(Supplier scenic) {
		this.scenic = scenic;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_biz_product")
	// @SequenceGenerator(name = "seq_biz_product", sequenceName =
	// "seq_biz_product")
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

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getRecommendPrice() {
		return recommendPrice;
	}

	public void setRecommendPrice(Double recommendPrice) {
		this.recommendPrice = recommendPrice;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getPlatformPrice() {
		return platformPrice;
	}

	public void setPlatformPrice(Double platformPrice) {
		this.platformPrice = platformPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getUuPid() {
		return uuPid;
	}

	public void setUuPid(String uuPid) {
		this.uuPid = uuPid;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	@Transient
	public String getBeignAndEndTimeDesc() {
		if (StringUtils.isNotBlank(beginTime)
				&& StringUtils.isNotBlank(endTime)) {
			return beginTime + "至" + endTime;
		}
		return "全年销售";
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

}
