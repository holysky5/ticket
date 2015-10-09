package com.ourlife.dev.modules.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.ourlife.dev.common.persistence.DataEntity;
import com.ourlife.dev.common.utils.excel.annotation.ExcelField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 订单信息Entity
 *
 * @author ourlife
 * @version 2014-05-31
 */
@Entity
@Table(name = "biz_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderInfo extends DataEntity {


    public static final String STATUS_UNKOWN = "-5"; //不正确状态
    public static final String STATUS_PENDING = "-2"; //待处理
    public static final String STATUS_FAILD = "-1";
    public static final String STATUS_UNUSED = "0"; //未使用
    public static final String STATUS_USED = "1"; //已使用
    public static final String STATUS_OUTDATE = "2"; //已过期
    public static final String STATUS_CANCELD = "3"; //被取消
    public static final String STATUS_CANCE_ALREADY = "31"; //已取消

    private static final long serialVersionUID = 1L;

    /**
     * 编号.
     */
    private Long id;

    /**
     * 订单名称.
     */
    private String name;

    /**
     * 订单号.
     */
    private String orderId;

    private String type;

    private Supplier supplier;

    private Distributor distributor;

    private Direct direct;

    /**
     * 门票历史编号.
     */
    private ProductHistory proHistory;

    /**
     * 用户名字.
     */
    private String customerName = "";

    /**
     * 取票电话.
     */
    private String customerMobile = "";

    /**
     * 联系电话.
     */
    private String contactMobile = "";

    /**
     * 用户身份证.
     */
    private String customerId = "";

    /**
     * 购买价格.
     */
    private Double purchasePrice;

    /**
     * 购买数量.
     */
    private Integer purchaseAmount;

    /**
     * 总价.
     */
    private Double totalpay;

    /**
     * 游玩使用日期.
     */
    private String useDate;

    /**
     * 订单处理步骤 0 提交订单|1 支付成功|2远端确认|3 下单成功|4 发送信息.
     */
    private String step;

    /**
     * 订单状态0 未使用|1 已使用|2 已过期|3 被取消.
     */
    private String status;

    /**
     * 支付状态（1 已支付|0 未支付）.
     */
    private Integer payStatus;

    private Date orderDate;

    // ------------ 票付通信息 -------------------

    /**
     * UUordernum票付通订单号.
     */
    private String uuOrdernum;

    /**
     * UUcode 凭证号.
     */
    private String uuCode;

    /**
     * UUremsg 短信发送次数.
     */
    private String uuRremsg;

    private List<OrderLog> orderLogList = Lists.newArrayList();

    public OrderInfo() {
        super();
    }

    public OrderInfo(Long id) {
        this();
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "seq_biz_orderInfo")
    // @SequenceGenerator(name = "seq_biz_orderInfo", sequenceName =
    // "seq_biz_orderInfo")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ExcelField(title = "门票名称", type = 1, align = 1, sort = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "订单号", type = 1, align = 2, sort = 1)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @ManyToOne
    @JoinColumn(name = "pro_history_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public ProductHistory getProHistory() {
        return proHistory;
    }

    public void setProHistory(ProductHistory proHistory) {
        this.proHistory = proHistory;
    }

    @ExcelField(title = "取票人", type = 1, align = 1, sort = 6)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @ExcelField(title = "取票人手机", type = 1, align = 1, sort = 7)
    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @ExcelField(title = "单价", type = 1, align = 0, sort = 4)
    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @ExcelField(title = "票数", type = 1, align = 0, sort = 3)
    public Integer getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Integer purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    @ExcelField(title = "总价", type = 1, align = 0, sort = 5)
    public Double getTotalpay() {
        return totalpay;
    }

    public void setTotalpay(Double totalpay) {
        this.totalpay = totalpay;
    }

    @ExcelField(title = "旅游日期", type = 1, align = 2, sort = 8)
    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    @ExcelField(title = "订单状态", align = 2, sort = 10, dictType = "biz_order_status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getUuOrdernum() {
        return uuOrdernum;
    }

    public void setUuOrdernum(String uuOrdernum) {
        this.uuOrdernum = uuOrdernum;
    }

    public String getUuCode() {
        return uuCode;
    }

    public void setUuCode(String uuCode) {
        this.uuCode = uuCode;
    }

    public String getUuRremsg() {
        return uuRremsg;
    }

    public void setUuRremsg(String uuRremsg) {
        this.uuRremsg = uuRremsg;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "下单时间", type = 1, align = 2, sort = 9)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @ExcelField(title = "订单类型", align = 2, sort = 1, dictType = "biz_order_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ExcelField(title = "分销商", type = 1, align = 2, sort = 10, value = "distributor.name")
    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Transient
    public List<OrderLog> getOrderLogList() {
        return orderLogList;
    }

    public void setOrderLogList(List<OrderLog> orderLogList) {
        this.orderLogList = orderLogList;
    }

    @ManyToOne
    @JoinColumn(name = "direct_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

}
