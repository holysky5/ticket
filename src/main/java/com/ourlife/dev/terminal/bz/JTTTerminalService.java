package com.ourlife.dev.terminal.bz;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.TerminalService;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Charsets.UTF_8;


/**
 * 计调通的处理代码
 * Created by holysky on 15/7/12.
 */
public class JTTTerminalService implements TerminalService {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    ContentType contentType=ContentType.APPLICATION_FORM_URLENCODED.withCharset(UTF_8);

    private static final Logger log = LoggerFactory.getLogger(JTTTerminalService.class);

    public static final String URL_CANCEL_ORDER = "http://www.op01.com/openapi/cancelorder";
    public static final String URL_SUBMIT_ORDER = "http://www.op01.com/openapi/addorderV20";
    public static final String URL_QUERY_ORDER = "http://www.op01.com/openapi/getorderview";


    private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    private static final String openId = Global.getConfig("jtt_openid");
    private static final String openKey = Global.getConfig("jtt_openkey");

    public static String getSign() {
        String str = openId + formater.format(new Date()) + openKey;
        return Hashing.md5().hashString(str, UTF_8).toString().toLowerCase();
    }

    public String argsForSumbmit(OrderInfo orderInfo) {
        return new StringBuilder()
                .append("openid=").append(openId).append("&")
                .append("sign=").append(getSign()).append("&")
                .append("sightpid=").append(orderInfo.getProHistory().getUuPid()).append("&") //传入产品编号
                .append("syrq=").append(orderInfo.getUseDate()).append("&")
                .append("leixin=").append(0).append("&") //散客 0 or 团队1
                .append("isxianfu=").append(0).append("&") //是否现付
                .append("lianxiren=").append(orderInfo.getCustomerName()).append("&") //取票人姓名
                .append("shuliang=").append(orderInfo.getPurchaseAmount()).append("&") //取票数量
                .append("mob=").append(orderInfo.getCustomerMobile()).append("&") //手机
                .append("sfz=").append(orderInfo.getCustomerId()).append("&") //身份证
                .append("issms=").append(1).append("&") //发送短信
                .append("dsfddh=").append(orderInfo.getOrderId()).append("&") //本地订单号
                .append("dsfsystem=").append("www.lvxing99.cn") //本地系统域名
                .toString();
    }



    public OrderInfo submitOrder(OrderInfo order) throws Exception {

        String args = argsForSumbmit(order);

        log.debug("[jtt][submit]:"+args);
        String content = Request.Post(URL_SUBMIT_ORDER)
                                .bodyString(args,contentType)
                                .execute().returnContent().asString();

        JTT_RS result = JTT_RS.fromBase64(content);
        RsOfSubmitOrder innerRs = result.dataAs(RsOfSubmitOrder.class);
        if(result.isNotValid()) {
            String errMsg = (innerRs != null) ? innerRs.alertinfo : result.getMessage();
            throw new RemoteException("计调通预定失败:"+errMsg);
        }else{
            order.setUuCode(innerRs.getFzcode());
            order.setUuOrdernum(innerRs.getDdh());
            order.setStatus("0");
        }

        return order;
    }

    public String argsForQuery(OrderInfo orderInfo) {
        return new StringBuilder()
                .append("openid=").append(openId).append("&")
                .append("sign=").append(getSign()).append("&")
                .append("fzcode=").append(orderInfo.getUuCode()).toString();
    }

    @Override
    public OrderInfo queryOrder(OrderInfo order) throws Exception {
        String args = argsForQuery(order);
        String content = Request.Post(URL_QUERY_ORDER).bodyString(args, contentType)
                .execute().returnContent().asString();
        log.debug("jtt query order content:{}",content);
        JTT_RS rs = JTT_RS.fromBase64(content);
        if (rs.isValid()) {
            RsOfQueryOrder result = rs.dataAs(RsOfQueryOrder.class);
            if (result.isUsed()) {
                log.info("计调通订单{}取票成功!",order.getOrderId());
                order.setStatus(OrderInfo.STATUS_USED);
            }else if (result.getStatus() == -1) {
                order.setStatus(OrderInfo.STATUS_CANCELD);
                order.setRemarks("采购商取消订单!");
            }else if (result.getStatus() == -2) {
                order.setStatus(OrderInfo.STATUS_CANCELD);
                order.setRemarks("供应商取消订单!");
            }
        }

        return order;
    }


    public String argsForCancelOrder(OrderInfo order) {
        return new StringBuilder()
                .append("openid=").append(openId).append("&")
                .append("sign=").append(getSign()).append("&")
                .append("fzcode=").append(order.getUuCode()).append("&")
                .toString();
    }

    @Override
    public boolean cancelOrder(OrderInfo order) throws Exception {


        String content = Request.Post(URL_CANCEL_ORDER)
                .bodyString(argsForCancelOrder(order).toString(),contentType)
                .execute().returnContent().asString();
        JTT_RS rs = JTT_RS.fromBase64(content);
        if (rs.isValid()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrder(OrderInfo order) throws Exception {
        if (order.getPurchaseAmount() == 0) {
			return cancelOrder(order);
		}
        log.error("计调通订单无法修改");
        return false;
    }

    @Override
    public String queryCodeOrder(OrderInfo order) throws Exception {
        return order.getUuCode();
    }

    static class JTT_RS {
        private String code;//返回码
        private String message;//返回消息
        private String data;

        public boolean isValid() {
            return "10000".equals(code);
        }

        public boolean isNotValid() {
            return !isValid();
        }

        public static JTT_RS fromBase64(String encodeJson) {
            return from(new String(BaseEncoding.base64().decode(encodeJson), UTF_8));
        }
        public static JTT_RS from(String json) {
            return JSON.parseObject(json, JTT_RS.class);
        }

        public <T> T dataAs(Class<T> clz) {
            if (Strings.isNullOrEmpty(data)) {
                return null;
            }
            return JSON.parseObject(data, clz);
        }
        public <T> T dataAs(Type type) {
            if (Strings.isNullOrEmpty(data)) {
                return null;
            }
            return JSON.parseObject(data, type);
        }

        public String dataAsStr() {
            return data;
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String msg) {
            this.message = msg;
        }


        public void setData(String data) {
            this.data = data;
        }
    };

    static class RsOfSubmitOrder{
        private Integer status; //下单结果 1表示成功
        private String  alertinfo;//下单结果信息
        private String fzcode;//取票吗
        private String ddh;  //计调通唯一订单号

        public boolean isSuccess() {
            return Integer.valueOf(1).equals(status);
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getAlertinfo() {
            return alertinfo;
        }

        public void setAlertinfo(String alertinfo) {
            this.alertinfo = alertinfo;
        }

        public String getFzcode() {
            return fzcode;
        }

        public void setFzcode(String fzcode) {
            this.fzcode = fzcode;
        }

        public String getDdh() {
            return ddh;
        }

        public void setDdh(String ddh) {
            this.ddh = ddh;
        }
    }

    static class RsOfQueryOrder {  //查询订单接口得返回值
        private String ddh;
        private Integer status;
        private String fzcode;
        private Integer isconsume;
        private Date consumetime;
        private Integer usednum;
        private Integer createnum;
        private Integer ispay;

        public boolean isUsed() {
            return isconsume==1 && usednum>=1;
        }

        public String getDdh() {
            return ddh;
        }

        public RsOfQueryOrder setDdh(String ddh) {
            this.ddh = ddh;
            return this;
        }

        public Integer getStatus() {
            return status;
        }

        public RsOfQueryOrder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public String getFzcode() {
            return fzcode;
        }

        public RsOfQueryOrder setFzcode(String fzcode) {
            this.fzcode = fzcode;
            return this;
        }

        public Integer getIsconsume() {
            return isconsume;
        }

        public RsOfQueryOrder setIsconsume(Integer isconsume) {
            this.isconsume = isconsume;
            return this;
        }

        public Date getConsumetime() {
            return consumetime;
        }

        public RsOfQueryOrder setConsumetime(Date consumetime) {
            this.consumetime = consumetime;
            return this;
        }

        public Integer getUsednum() {
            return usednum;
        }

        public RsOfQueryOrder setUsednum(Integer usednum) {
            this.usednum = usednum;
            return this;
        }

        public Integer getCreatenum() {
            return createnum;
        }

        public RsOfQueryOrder setCreatenum(Integer createnum) {
            this.createnum = createnum;
            return this;
        }

        public Integer getIspay() {
            return ispay;
        }

        public RsOfQueryOrder setIspay(Integer ispay) {
            this.ispay = ispay;
            return this;
        }
    }

}
