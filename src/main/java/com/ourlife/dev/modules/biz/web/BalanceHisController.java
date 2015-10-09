package com.ourlife.dev.modules.biz.web;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.persistence.BaseEntity;
import com.ourlife.dev.common.persistence.Page;
import com.ourlife.dev.common.utils.DoubleUtils;
import com.ourlife.dev.common.web.BaseController;
import com.ourlife.dev.modules.biz.entity.BalanceHis;
import com.ourlife.dev.modules.biz.entity.Distributor;
import com.ourlife.dev.modules.biz.entity.RechargeConfirm;
import com.ourlife.dev.modules.biz.entity.Supplier;
import com.ourlife.dev.modules.biz.service.BalanceHisService;
import com.ourlife.dev.modules.biz.service.DistributorService;
import com.ourlife.dev.modules.biz.service.RechargeConfirmService;
import com.ourlife.dev.modules.biz.service.SupplierService;
import com.ourlife.dev.modules.sys.entity.User;
import com.ourlife.dev.modules.sys.service.SystemService;
import com.ourlife.dev.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 余额历史Controller
 *
 * @author ourlife
 * @version 2014-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/balanceHis")
public class BalanceHisController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BalanceHisService balanceHisService;

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SystemService systemService;

    @Resource
    private RechargeConfirmService rcService;


    @ModelAttribute
    public BalanceHis get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return balanceHisService.get(id);
        } else {
            return new BalanceHis();
        }
    }

    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = {"mylist"})
    public String myList(BalanceHis balanceHis, HttpServletRequest request,
                         HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        balanceHis.setUser(user);

        Page<BalanceHis> page = balanceHisService.find(new Page<BalanceHis>(
                request, response), balanceHis);
        model.addAttribute("page", page);
        Double balance = 0.0;
        if (user.getUserType().equals("3")) {
            Distributor distributor = distributorService.getUserByUsername(user
                    .getLoginName());
            balance = distributor.getBalance();
        } else if (user.getUserType().equals("4")) {
            Supplier supplier = supplierService.getSupplierByNo(user
                    .getLoginName());
            balance = supplier.getBalance();
        } else {
            return "redirect:" + Global.getAdminPath() + "/error/403";
        }
        model.addAttribute("userType", user.getUserType());

        model.addAttribute("balance", balance);
        return "modules/biz/balanceHisList";
    }

    @RequiresPermissions("biz:balanceHis:manage")
    @RequestMapping(value = {"list/{userType}/{userid}"})
    public String list(@PathVariable String userType,
                       @PathVariable Long userid, BalanceHis balanceHis,
                       HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        if (userType == null || userid == null) {
            return "redirect:" + Global.getAdminPath() + "/error/403";
        }
        if (userType.equals("distributor")) {
            Distributor distributor = distributorService.get(userid);
            User user = systemService.getUserByLoginName(distributor
                    .getUsername());
            balanceHis.setUser(user);
            model.addAttribute("distributor", distributor);
        } else if (userType.equals("supplier")) {
            Supplier supplier = supplierService.get(userid);
            User user = systemService.getUserByLoginName(supplier.getNo());
            balanceHis.setUser(user);
            model.addAttribute("supplier", supplier);
        }
        Page<BalanceHis> page = balanceHisService.find(new Page<BalanceHis>(
                request, response), balanceHis);
        model.addAttribute("page", page);
        model.addAttribute("userType", userType);
        model.addAttribute("userid", userid);

        return "modules/biz/balanceHisManage";
    }

    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = "form")
    public String form(BalanceHis balanceHis, Model model) {
        model.addAttribute("balanceHis", balanceHis);
        return "modules/biz/balanceHisForm";
    }

    @RequiresPermissions("biz:balanceHis:manage")
    @RequestMapping(value = "manage/{userType}/{userid}")
    public String formManage(@PathVariable String userType,
                             @PathVariable Long userid, BalanceHis balanceHis, Model model) {
        if (userType == null || userid == null) {
            return "redirect:" + Global.getAdminPath() + "/error/403";
        }
        if (userType.equals("distributor")) {
            Distributor distributor = distributorService.get(userid);
            model.addAttribute("distributor", distributor);
        } else if (userType.equals("supplier")) {
            Supplier supplier = supplierService.get(userid);
            model.addAttribute("supplier", supplier);
        }

        model.addAttribute("balanceHis", balanceHis);
        return "modules/biz/balanceHisFormManage";
    }

    @RequiresPermissions("biz:balanceHis:manage")
    @RequestMapping(value = "save/{userType}/{userid}")
    public String save(@PathVariable String userType,
                       @PathVariable Long userid, BalanceHis balanceHis, Model model,
                       RedirectAttributes redirectAttributes) {
        if (userType.equals("distributor")) {
            Distributor distributor = distributorService.get(userid);
            balanceHis.setUser(systemService.getUserByLoginName(distributor
                    .getUsername()));
            balanceHisService.save(balanceHis, distributor);
        } else if (userType.equals("supplier")) {
            Supplier supplier = supplierService.get(userid);
            balanceHis.setUser(systemService.getUserByLoginName(supplier
                    .getNo()));
            balanceHisService.save(balanceHis, supplier);
        }

        addMessage(redirectAttributes, "保存账户信息成功");
        return "redirect:" + Global.getAdminPath() + "/biz/balanceHis/list/"
                + userType + "/" + userid;
    }

    @RequiresPermissions("biz:balanceHis:edit")
    @RequestMapping(value = "delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        balanceHisService.delete(id);
        addMessage(redirectAttributes, "删除余额历史成功");
        return "redirect:" + Global.getAdminPath() + "/biz/balanceHis/?repage";
    }

    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = "recharge")
    public String rechargeGet(BalanceHis balanceHis, Model model) {
        Distributor distributor = distributorService
                .getUserByUsername(UserUtils.getUser().getLoginName());
        balanceHis.setBegin(distributor.getBalance());
        model.addAttribute("balanceHis", balanceHis);
        model.addAttribute("fee",
                Integer.valueOf(Global.getConfig("allinpay_fee")));
        return "modules/biz/balanceHisForm";
    }

    /**
     * 转账
     *
     * @param balanceHis
     * @param model
     * @return
     */
    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = "transformAccount")
    public String transformAccount(BalanceHis balanceHis, Model model) {
//		model.addAttribute("user", UserUtils.getUser());
        User curUser = UserUtils.getUser();
        Distributor distributor = distributorService.getUserByUsername(curUser.getLoginName());
        balanceHis.setBegin(distributor.getBalance());
        model.addAttribute("balanceHis", balanceHis);
//		model.addAttribute("fee",
//				Integer.valueOf(Global.getConfig("allinpay_fee")));
        //生成转账流水
        model.addAttribute("rechargeNo", curUser.getId() + "-" + new Date().getTime());
        return "modules/biz/balanceHisTransformAccountForm";
    }

    @RequiresPermissions("biz:balanceHis:edit")
    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    public void rechargePost(BalanceHis balanceHis,
                             HttpServletResponse response, Model model) {
        String sHtmlText = "";
        try {
            sHtmlText = balanceHisService.recharge(balanceHis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(sHtmlText);
        // System.err.println(sHtmlText);
        OutputStream os;
        try {
            os = response.getOutputStream();
            response.setContentType("text/html;charset="
                    + AlipayConfig.input_charset);
            os.write(sHtmlText.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "allinpay/return")
    public String rechargeReturn(HttpServletRequest request, Model model) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // try {
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            // logger.debug(valueStr);
            // } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            // }
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号

        String out_trade_no = request.getParameter("out_trade_no");

        // 支付宝交易号

        String trade_no = request.getParameter("trade_no");

        // 交易状态
        String trade_status = request.getParameter("trade_status");

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        // 计算得出通知验证结果
        logger.info(params.toString());
        boolean verify_result = AlipayNotify.verify(params);
        logger.debug(verify_result + "");
        if (verify_result) {// 验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码

            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if (trade_status.equals("TRADE_FINISHED")
                    || trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                BalanceHis balanceHis = balanceHisService.get(Long
                        .valueOf(out_trade_no.split("-")[1]));
                balanceHis.setStatus("0");
                balanceHis.setEnd(DoubleUtils.add(balanceHis.getEnd(),
                        balanceHis.getAmount()));
                balanceHis.setRemarks("支付宝交易号：" + trade_no);
                balanceHisService.save(balanceHis);

            }

            return "redirect:" + Global.getAdminPath() + "/";
        } else {
            return "redirect:" + Global.getAdminPath() + "/";
        }

    }

    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = "bank")
    public String bank(BalanceHis balanceHis, Model model) {
        return "modules/biz/balanceHisBank";
    }

    @RequiresPermissions("biz:balanceHis:view")
    @RequestMapping(value = "alipay")
    public String alipay(BalanceHis balanceHis, Model model) {
        return "modules/biz/balanceHisAlipay";
    }

    @RequestMapping("addConfirmRecharge.json")
    @ResponseBody
    public void addRechargeConfirm(String rechargeNo, Double amount) {
        rcService.saveNewRechargeConfirmRecord(rechargeNo, amount);
    }

    @RequestMapping(value = "confirmRecharge", method = RequestMethod.GET)
    public String confirmRechargePage() {
        return "modules/biz/rechargeConfirmForm";
    }

    @RequestMapping(value = "recharge_confirm", method = RequestMethod.POST)
    public String confirmRecharge(String rechargeNo, Double amount, Model rs) {
        String msg = "";
        RechargeConfirm record = null;
        if (Strings.isNullOrEmpty(rechargeNo) || amount == null) {
            msg = "操作失败,参数错误";
        } else {
            record = rcService.findByRechargeNo(rechargeNo);
            if (record == null) {
                msg = "操作失败,转账记录不存在";
            }
            if (!record.getAmount().equals(amount)) {
                msg = "操作失败,转账金额不符合预期";
            }
            if (BaseEntity.YES.equals(record.getConfirmFlag())) {
                msg = "操作失败,该笔转账记录已被确认";
            }

        }
        if (!Strings.isNullOrEmpty(msg)) {
            addMessage(rs,msg);
            return "modules/biz/rechargeConfirmForm";
        }
        String userId = Splitter.on("-").split(rechargeNo).iterator().next();

        rcService.confirmRecharge(Long.valueOf(userId), record);
        addMessage(rs,"操作成功");
        //添加账户金额
        return "modules/biz/rechargeConfirmForm";
    }

}
