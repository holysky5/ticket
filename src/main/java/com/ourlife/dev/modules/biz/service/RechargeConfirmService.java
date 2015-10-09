package com.ourlife.dev.modules.biz.service;

import com.ourlife.dev.common.persistence.BaseEntity;
import com.ourlife.dev.modules.biz.dao.RechargeConfirmDao;
import com.ourlife.dev.modules.biz.entity.RechargeConfirm;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.ourlife.dev.common.persistence.BaseEntity.YES;

/**
 * Created by holysky on 15/6/29.
 */
@Service
public class RechargeConfirmService {
    @Resource
    RechargeConfirmDao dao;
    @Resource
    BalanceHisService balanceHisService;
    @Resource
    DistributorService distributorService;

    public void saveNewRechargeConfirmRecord(String rechargeNo,Double amount) {
        RechargeConfirm record = new RechargeConfirm();
        record.setRechargeNo(rechargeNo);
        record.setAmount(amount);
        record.setRemarks("待验证充值记录");
        record.setConfirmFlag(BaseEntity.NO);
        dao.save(record);
    }

    public RechargeConfirm findByRechargeNo(String rechargeNo) {
        return dao.findByRechargeNo(rechargeNo);
    }

    /**
     * 确认充值记录.并提升对应账户得余额
     * @param userId
     * @param record
     */
    @Transactional
    public void confirmRecharge(Long userId, RechargeConfirm record) {
        record.setConfirmFlag(YES);
        record.setRemarks(UserUtils.getUser().getLoginName() + "-确认转账记录");
        dao.save(record);
        balanceHisService.addBalanceToDistributor(userId, record.getAmount());
    }
}
