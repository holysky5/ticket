package com.ourlife.dev.terminal.sys;

import java.io.IOException;
import java.rmi.RemoteException;

import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.TerminalService;

/**
 * 贝竹接口
 * 
 * @author rocliao
 * 
 */
public class SYSTerminalService implements TerminalService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public SYSTerminalService() {

	}

	@Override
	public OrderInfo submitOrder(OrderInfo order)
			throws JsonProcessingException, IOException {
		order.setUuOrdernum(DateUtils.getDate("yyyyMMdd")
				+ order.getCustomerMobile());
		order.setStatus("0");
		return order;
	}

	@Override
	public boolean updateOrder(OrderInfo order) throws JsonProcessingException,
			IOException {

		return true;
	}

	@Override
	public boolean cancelOrder(OrderInfo order) throws JsonProcessingException,
			IOException {

		return true;

	}

	@Override
	public OrderInfo queryOrder(OrderInfo order)
			throws JsonProcessingException, IOException {

		return order;

	}

	@Override
	public String queryCodeOrder(OrderInfo order) throws RemoteException {
		return order.getUuCode();
	}

}