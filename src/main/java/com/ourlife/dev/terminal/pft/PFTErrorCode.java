package com.ourlife.dev.terminal.pft;

import java.util.Map;

import com.google.common.collect.Maps;

public class PFTErrorCode {
	public static final Map<String, String> MAP = Maps.newHashMap();

	static {
		MAP.put("101", "ErrorCode:101;无授权，拒绝连接");
		MAP.put("102", "ErrorCode:102;传输数据类型出错");
		MAP.put("103", "ErrorCode:103;传输数据格式出错");
		MAP.put("104", "ErrorCode:104;数据无法传输");
		MAP.put("105", "ErrorCode:105;数据为空或重叠");
		MAP.put("106", "ErrorCode:106; 数据服务出错，无法提交订单");
		MAP.put("107", "ErrorCode:107; 返回数据非 XML 格式");
		MAP.put("108", "ErrorCode:108; 网络中断");
		MAP.put("109", "ErrorCode:109; 重复查询");
		MAP.put("110", "ErrorCode:110; 重复提交");
		MAP.put("111", "ErrorCode:111; 数据含有非法字符");
		MAP.put("112", "ErrorCode:112; 手机格式错误");
		MAP.put("113", "ErrorCode:113; 错误的联票方式");
		MAP.put("114", "ErrorCode:114; 编码出错，无法继续");
		MAP.put("115", "ErrorCode:115; 网站服务出错，请联系技术开发人员");
		MAP.put("116", "ErrorCode:116; 短信重发次数过多");
		MAP.put("117", "ErrorCode:117; 在线支付未支付成功无法发短信");
		MAP.put("118", "ErrorCode:118; 终端信息出错，请联系技术人员");
		MAP.put("119", "ErrorCode:119; 订单状态出错，请联系技术人员");
		MAP.put("120", "ErrorCode:120; 订单号已超出系统承载，无法生成");
		MAP.put("121", "ErrorCode:121; 状态参数出错");
		MAP.put("122", "ErrorCode:122; 资金账户余额不足");
		MAP.put("123", "ErrorCode:123; 未知的订单号");
		MAP.put("124", "ErrorCode:124; 权限不够");
		MAP.put("130", "ErrorCode:130; 邮件格式错误");
		MAP.put("131", "ErrorCode:131; 手机格式错误");
		MAP.put("132", "ErrorCode:132; 短信插入失败");
		MAP.put("133", "ErrorCode:133; 时间未到");
		MAP.put("134", "ErrorCode:134; 时间超过");
		MAP.put("141", "ErrorCode:141; 无修改返回");
		MAP.put("142", "ErrorCode:142; 存在时间交集");
		MAP.put("143", "ErrorCode:143; 未按照预期更新数据");
		MAP.put("144", "ErrorCode:144; 参加优惠计划的订单不支持修改订单");
		MAP.put("145", "ErrorCode:145; 修改订单内容不能全部为空");

		MAP.put("1061", "ErrorCode:1061; //无此日期的价格");
		MAP.put("1062", "ErrorCode:1062; //库存已售罄");
		MAP.put("1063", "ErrorCode:1063; //总库存已售罄");
		MAP.put("1064", "ErrorCode:1064; //错误的分销差价设置");
		MAP.put("1065", "ErrorCode:1065; //无此商品的分销价格");
		MAP.put("1066", "ErrorCode:1066; //商品票类数据出错");
		MAP.put("1067", "ErrorCode:1067; //商品数据出错");
		MAP.put("1068", "ErrorCode:1068; //商品需提前预定");
		MAP.put("1069", "ErrorCode:1069; //已超过当日购买的时间");
		MAP.put("1070", "ErrorCode:1070; //支付方式错误");
		MAP.put("1071", "ErrorCode:1071; //游玩时间必填");
		MAP.put("1072", "ErrorCode:1072; //取票人姓名、手机不能为空");
		MAP.put("1073", "ErrorCode:1073; //错误的供应链");
		MAP.put("1074", "ErrorCode:1074; //短信发送失败");
		MAP.put("1075", "ErrorCode:1075; //远端订单号不能重复");
		MAP.put("1076", "ErrorCode:1076; //过期的订单无法修改");
		MAP.put("1077", "ErrorCode:1077; //订单不是未使用状态，无法取消或修改");
		MAP.put("1078", "ErrorCode:1078; //订单修改数量只能减少，不能增加");
	};

}
