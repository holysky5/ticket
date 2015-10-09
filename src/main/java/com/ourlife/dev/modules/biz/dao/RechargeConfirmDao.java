package com.ourlife.dev.modules.biz.dao;

import com.ourlife.dev.common.persistence.BaseDao;
import com.ourlife.dev.common.persistence.BaseDaoImpl;
import com.ourlife.dev.modules.biz.entity.RechargeConfirm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by holysky on 15/6/29.
 */
public interface RechargeConfirmDao extends RechargeConfirmCustom, CrudRepository<RechargeConfirm, Long> {
    RechargeConfirm findByRechargeNo(String rechargeNo);

    @Modifying
    @Query("update RechargeConfirm  r set r.confirmFlag='1' where r.id=?1")
    void updateForConfirm(Long id);
}
/**
 * DAO自定义接口
 * @author ourlife
 */
interface RechargeConfirmCustom extends BaseDao<RechargeConfirm> {

}

/**
 * DAO自定义接口实现
 * @author ourlife
 */
@Component
class RechargeConfirmDaoImpl extends BaseDaoImpl<RechargeConfirm> implements RechargeConfirmCustom {

}
