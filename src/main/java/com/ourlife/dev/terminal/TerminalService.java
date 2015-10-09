package com.ourlife.dev.terminal;

import com.ourlife.dev.modules.biz.entity.OrderInfo;

public interface TerminalService {
	OrderInfo submitOrder(OrderInfo order) throws Exception;

	OrderInfo queryOrder(OrderInfo order) throws Exception;

	boolean cancelOrder(OrderInfo order) throws Exception;

	boolean updateOrder(OrderInfo order) throws Exception;

	String queryCodeOrder(OrderInfo order) throws Exception;
}
