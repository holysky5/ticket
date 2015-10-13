/*
 *  Copyright 2012, ALLINFINANCE Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF ALLINFINANCE CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF ALLINFINANCE CO., LTD
 *
 *  Module Name:SMSJob.java,v 1.0 2012-7-18 Created by 廖鹏
 *
 *  Edit History:
 *
 *  2012-7-18 Created by 廖鹏
 */
package com.ourlife.dev.modules.biz.service;

import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.TerminalFactory;
import com.ourlife.dev.terminal.TerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 每日定时检查待办任务发送统一邮件
 *
 * @author 廖鹏
 *
 */

public class OrderDealJob {

	private static Logger logger = LoggerFactory.getLogger(OrderDealJob.class);

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	JpaTransactionManager tm;

	@Transactional()
	public void execute() {
		logger.info("同步远程订单开始...");
		TerminalService pftService = TerminalFactory.createTerminalService("0");
		TerminalService bzService = TerminalFactory.createTerminalService("1");
		TerminalService zybService = TerminalFactory.createTerminalService("2");
		TerminalService jttService = TerminalFactory.createTerminalService("3");

		// 把平台未使用的订单从票付通同步回来
		List<OrderInfo> list = orderInfoService.getOrdersByStatus("0");
		for (OrderInfo order : list) {
			try {
				if (order.getSupplier().getCheckTerminal().equals("0")) {
					pftService.queryOrder(order);
				} else if (order.getSupplier().getCheckTerminal().equals("1")) {
					bzService.queryOrder(order);
				} else if (order.getSupplier().getCheckTerminal().equals("2")) {
					zybService.queryOrder(order);
				}else if (order.getSupplier().getCheckTerminal().equals("3")) {
					jttService.queryOrder(order);
				} else if (order.getSupplier().getCheckTerminal().equals("8")) {
					// 到期自动显示为已使用
					Date date = DateUtils.parseDate(order.getUseDate()
							+ " 17:30:00");
					if (date.before(new Date())) {
						order.setStatus("1");
					}
				}

				if (order.getStatus().equals("1")) {
					orderInfoService.addOrderLog("用户取票成功", order);
				}
				if (order.getType().equals("0")) {
					orderInfoService.updateFromPft(order);
				} else if (order.getType().equals("1")) {
					orderInfoService.updateFromPft(order);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("同步远程订单结束!");

		// logger.info("查询待处理订单开始...");
		//
		// List<OrderInfo> todoList = orderInfoService.getOrdersByStatus("-2");
		//
		// logger.info("查询待处理订单结束!");

	}
}
