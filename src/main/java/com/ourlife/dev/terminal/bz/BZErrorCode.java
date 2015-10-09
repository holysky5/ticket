package com.ourlife.dev.terminal.bz;

import java.util.Map;

import com.google.common.collect.Maps;

public class BZErrorCode {
	public static final Map<String, String> MAP = Maps.newHashMap();

	static {
		MAP.put("50007", "商品序号有问题");
		MAP.put("50012", "商品数量有问题");
		MAP.put("50015", "合作序号有问题");
		MAP.put("50020", "身份证有问题");
		MAP.put("50025", "手机不对");
		MAP.put("50035", "日期有问题");
		MAP.put("50040", "日期太早");
		MAP.put("50045", "合作序号不存在");
		MAP.put("50047", "合作权限不够");
		MAP.put("50050", "商品不存在");
		MAP.put("50055", "商品已售完");
		MAP.put("50061", "状态位出错了");
		MAP.put("50062", "余额钱不够");
		MAP.put("50063", "单号有问题");
		MAP.put("50064", "密码验证出错");
		MAP.put("50065", "用户名出错了");
		MAP.put("50066", "状态不符");
		MAP.put("50067", "订单不存在");
		MAP.put("50069", "您插入的订单号已经存在");
		MAP.put("50071", "密码输入错误");
		MAP.put("50072", "订单没付钱");
		MAP.put("50073", "Format 格式不对");
		MAP.put("50074", "Type不存在");
		MAP.put("50075", "Sign签名验证不对");
		MAP.put("30140", "商品已下架");
		MAP.put("30141", "没有到付票");
		MAP.put("30142", "没有在线支付");
		MAP.put("30143", "出游时间过早");
		MAP.put("30144", "出游时间超出有效期");
		MAP.put("30145", "此账号不具体购买团队票的权限");
		MAP.put("30150", "售票时间未到");
		MAP.put("30165", "这个票过期了");
		MAP.put("30170", "此票已售完");
		MAP.put("30171", "此票限定购买者");
		MAP.put("30172", "此票限定购买地区");
		MAP.put("30173", "此票限定购买地区");
		MAP.put("30174", "此票限定身份证购买张数");
		MAP.put("30177", "余额钱不够");
		MAP.put("30178", "此票限定起售数量");
		MAP.put("30179", "身份证个数有问题");
		MAP.put("31006", "没有任何修改");
		MAP.put("31009", "验证未通过");
		MAP.put("31002", "改票能改小不能改大");
		MAP.put("31003", "门票未支付");
		MAP.put("31004", "门票已使用");

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
