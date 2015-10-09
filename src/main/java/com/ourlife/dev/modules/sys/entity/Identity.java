package com.ourlife.dev.modules.sys.entity;

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
 * 流水号Entity
 * 
 * @author peng.liao
 * @version 2014-01-21
 */
@Entity
@Table(name = "sys_identity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Identity extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String name; // 名称

	/** 别名. */
	private String alias;

	/** 生成规则. */
	private String rule;

	/** 每天生成. */
	private String genEveryDay = "0";

	/** 号码长度. */
	private Integer noLength = 5;

	/** 初始值. */
	private Integer initValue = 1;

	/** 当前值. */
	private Integer curValue;

	/** 当前日期. */
	private String curDate = "";

	/** 步长. */
	private Integer step = 1;

	public Identity() {
		super();
	}

	public Identity(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "seq_sys_identity")
	// @SequenceGenerator(name = "seq_sys_identity", sequenceName =
	// "seq_sys_identity")
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

	/**
	 * Set the 别名.
	 * 
	 * @param alias
	 *            别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Get the 别名.
	 * 
	 * @return 别名
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * Set the 生成规则.
	 * 
	 * @param rule
	 *            生成规则
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * Get the 生成规则.
	 * 
	 * @return 生成规则
	 */
	public String getRule() {
		return this.rule;
	}

	/**
	 * Set the 每天生成.
	 * 
	 * @param genEveryDay
	 *            每天生成
	 */
	public void setGenEveryDay(String genEveryDay) {
		this.genEveryDay = genEveryDay;
	}

	/**
	 * Get the 每天生成.
	 * 
	 * @return 每天生成
	 */
	public String getGenEveryDay() {
		return this.genEveryDay;
	}

	/**
	 * Set the 号码长度.
	 * 
	 * @param noLength
	 *            号码长度
	 */
	public void setNoLength(Integer noLength) {
		this.noLength = noLength;
	}

	/**
	 * Get the 号码长度.
	 * 
	 * @return 号码长度
	 */
	public Integer getNoLength() {
		return this.noLength;
	}

	/**
	 * Set the 初始值.
	 * 
	 * @param initValue
	 *            初始值
	 */
	public void setInitValue(Integer initValue) {
		this.initValue = initValue;
	}

	/**
	 * Get the 初始值.
	 * 
	 * @return 初始值
	 */
	public Integer getInitValue() {
		return this.initValue;
	}

	/**
	 * Set the 当前值.
	 * 
	 * @param curValue
	 *            当前值
	 */
	public void setCurValue(Integer curValue) {
		this.curValue = curValue;
	}

	/**
	 * Get the 当前值.
	 * 
	 * @return 当前值
	 */
	public Integer getCurValue() {
		return this.curValue;
	}

	/**
	 * Set the 当前日期.
	 * 
	 * @param curDate
	 *            当前日期
	 */
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	/**
	 * Get the 当前日期.
	 * 
	 * @return 当前日期
	 */
	public String getCurDate() {
		return this.curDate;
	}

	/**
	 * Set the 步长.
	 * 
	 * @param step
	 *            步长
	 */
	public void setStep(Integer step) {
		this.step = step;
	}

	/**
	 * Get the 步长.
	 * 
	 * @return 步长
	 */
	public Integer getStep() {
		return this.step;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Identity other = (Identity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
