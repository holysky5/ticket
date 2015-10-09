package com.ourlife.dev.modules.biz.entity;

import com.ourlife.dev.common.persistence.DataEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 转账充值时的确认.如果收到支付宝的充值短信后会将此记录得confirmFlag从0转成1.
 * 如果数据库中无此记录则代表没有充值记录.
 * Created by holysky on 15/6/25.
 */
@Entity
@Table(name = "biz_recharge_confirm")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RechargeConfirm extends DataEntity  {

    private Long id;
    /**  充值确认码 **/
    private String rechargeNo;
    /**  充值金额   **/
    private Double amount;

    /**  确认充值状态 (0 未确认,1 已确认)**/
    private String confirmFlag;



    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public String getRechargeNo() {
        return rechargeNo;
    }

    public Double getAmount() {
        return amount;
    }

    public String getConfirmFlag() {
        return confirmFlag;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setConfirmFlag(String confirmFlag) {
        this.confirmFlag = confirmFlag;
    }
}
