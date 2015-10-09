package com.ourlife.dev.terminal.pft;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.TerminalService;

public class PFTTerminalService implements TerminalService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String AC = Global.getConfig("pft_ac");
	private static final String PW = Global.getConfig("pft_pw");

	private PFTMXBindingStub binding = null;

	public PFTTerminalService() {
		PFTMX service = new PFTMXLocator();
		try {
			binding = (PFTMXBindingStub) service.getPFTMXPort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrderInfo submitOrder(OrderInfo order) throws RemoteException {
		String xml = binding.PFT_Order_Submit(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getSupplier().getUuId(), order
						.getProHistory().getUuPid(), order.getOrderId(), "0",
				order.getPurchaseAmount() + "", order.getUseDate(), order
						.getCustomerName(), order.getCustomerMobile(), order
						.getContactMobile(), "1", "0", "0", "", "", "0", "0",
				"");
		List<Map<String, String>> list = parserXml(xml);
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(order.toString());
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 处理订单
				logger.info(xml);
				// 票付通订单号
				order.setUuOrdernum(map.get("UUordernum"));
				order.setStatus("0");
			}
		}
		return order;

	}

	@Override
	public OrderInfo queryOrder(OrderInfo order) throws RemoteException {
		// 帐号 密码 商家编码 UUsalerid 票付通订单号175762
		String xml = binding.order_Globle_Search(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getSupplier().getUuSalerId(), "",
				"", "", "", "", "", "", "", "", "", "", order.getUuOrdernum(),
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"");
		List<Map<String, String>> list = parserXml(xml);
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 处理订单
				logger.info(xml);
				order.setStatus(map.get("UUstatus"));
				order.setUuRremsg(map.get("UUremsg"));
				order.setPurchaseAmount(Integer.valueOf(map.get("UUtnum")));
			}
		}
		return order;

	}

	@Override
	public boolean cancelOrder(OrderInfo order) throws RemoteException {
		// 参数 1： 票付通订单号175762
		// 参数 2：修改后数量 int（0 为取消订单 -1 不做修改）
		// 参数 3：修改取票人手机 String
		String xml = binding.order_Change_Pro(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getUuOrdernum(), "0", "", "");
		String code = "";
		List<Map<String, String>> list = parserXml(xml);
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 处理订单
				logger.info(xml);
				code = map.get("UUdone");
				logger.info(xml);
			}
		}
		return code.equals("100");

	}

	/**
	 * 
	 * @param order
	 * @param mobile
	 * @return
	 * @throws RemoteException
	 */

	@Override
	public boolean updateOrder(OrderInfo order) throws RemoteException {
		// 参数 1： 票付通订单号175762
		// 参数 2：修改后数量 int（0 为取消订单 -1 不做修改）
		// 参数 3：修改取票人手机 String
		String xml = binding.order_Change_Pro(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getUuOrdernum(),
				order.getPurchaseAmount() + "", order.getCustomerMobile(), "");
		String code = "";
		List<Map<String, String>> list = parserXml(xml);
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 处理订单
				logger.info(xml);
				code = map.get("UUdone");
				logger.info(xml);
			}
		}
		return code.equals("100");

	}

	public String queryCodeOrder(OrderInfo order) throws RemoteException {
		String xml = binding.terminal_Code_Verify(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getUuOrdernum(), "");
		List<Map<String, String>> list = parserXml(xml);
		String code = "";
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 获取验证码
				code = map.get("UUcode");
				logger.info(xml);
			}
		}
		return code;
	}

	public boolean sendSmsOrder(OrderInfo order) throws RemoteException {
		String xml = binding.reSend_SMS_Global_PL(PFTTerminalService.AC,
				PFTTerminalService.PW, order.getUuOrdernum(), "");
		List<Map<String, String>> list = parserXml(xml);
		String code = "";
		if (list.size() == 1) {
			Map<String, String> map = list.get(0);
			String errorCode = map.get("UUerrorcode");
			if (errorCode != null) {
				logger.error(xml);
				throw new RemoteException(PFTErrorCode.MAP.get(errorCode));
			} else {
				// 发送成功返回100
				code = map.get("UUdone");
				logger.info(xml);
			}
		}
		return code.equals("100");

	}

	private static List<Map<String, String>> parserXml(String xml) {
		List<Map<String, String>> list = Lists.newArrayList();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element datas = document.getRootElement();
			for (Iterator i = datas.elementIterator(); i.hasNext();) {
				Element data = (Element) i.next();
				Map<String, String> map = Maps.newHashMap();
				for (Iterator j = data.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					map.put(node.getName(), node.getText());
				}
				list.add(map);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {

		try {
			OrderInfo order = new OrderInfo();
			// order.setProHistoryId(1111L);
			order.setOrderId("8100153224");
			order.setPurchasePrice(100.00);
			order.setPurchaseAmount(1);
			order.setTotalpay(200.0);
			order.setUseDate("2014-06-09");
			order.setCustomerName("测试test");
			order.setCustomerMobile("13162796265");
			order.setContactMobile("13162796265");

			// order.setUuId("2039");
			// order.setUuSalerId("600473");
			// order.setUuPid("4041");

			PFTTerminalService pftService = new PFTTerminalService();

			// pftService.submitOrder(order);
			order.setUuOrdernum("182152");
			// pftService.queryOrder(order);
			// pftService.queryCodeOrder(order);
			String xml = pftService.binding.order_Globle_Search(
					PFTTerminalService.AC, PFTTerminalService.PW, order
							.getSupplier().getUuSalerId(), "", "", "", "", "",
					"", "", "", "", "", "", order.getUuOrdernum(), "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "");

			System.out.println(xml);

			// List<Map<String, String>> list = pftService.parserXml(xml);
			// for (Map<String, String> map : list) {
			// if (map.get("UUstatus").equals("0")) {
			// pftService.binding.order_Change_Pro(PFTService.AC,
			// PFTService.PW, map.get("UUordernum"), "0", "", "");
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
