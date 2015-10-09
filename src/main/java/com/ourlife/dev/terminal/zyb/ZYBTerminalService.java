package com.ourlife.dev.terminal.zyb;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.modules.biz.entity.ProductHistory;
import com.ourlife.dev.terminal.TerminalService;

/**
 * 贝竹接口
 * 
 * @author rocliao
 * 
 */
public class ZYBTerminalService implements TerminalService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String AC = Global.getConfig("zyb_ac");
	private static final String PW = Global.getConfig("zyb_pw");

	public ZYBTerminalService() {

	}

	private static Map<String, String> getParam(String xmlMsg) {
		String key = Global.getConfig("zyb_key");
		String sign = MD5.md5("xmlMsg=" + xmlMsg + key);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("xmlMsg", xmlMsg);
		paramMap.put("sign", sign);

		return paramMap;
	}

	@Override
	public OrderInfo submitOrder(OrderInfo order)
			throws JsonProcessingException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<PWBRequest>");
		sb.append("<transactionName>SEND_CODE_REQ</transactionName>");
		sb.append("<header>");
		sb.append("<application>SendCode</application>");
		sb.append("<requestTime>" + DateUtils.getDate("yyyy-MM-dd")
				+ "</requestTime>");
		sb.append("</header>");
		sb.append("<identityInfo>");
		sb.append("<corpCode>" + Global.getConfig("zyb_key") + "</corpCode>");
		sb.append("<userName>" + AC + "</userName>");
		sb.append("</identityInfo>");
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<certificateNo>" + order.getCustomerId()
				+ "</certificateNo>");
		sb.append("<linkName>" + order.getCustomerName() + "</linkName>");
		sb.append("<linkMobile>" + order.getCustomerMobile() + "</linkMobile>");
		sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");
		sb.append("<orderPrice>" + order.getTotalpay() + "</orderPrice>");
		sb.append("<groupNo></groupNo>");
		sb.append("<payMethod>third_vm</payMethod>");

		if (order.getProHistory().getUuPid().toUpperCase().startsWith("T")) {
			// 套票
			sb.append("<familyOrders>");
			sb.append("<familyOrder>");
			sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");
			sb.append("<price>" + order.getPurchasePrice() + "</price>");
			sb.append("<quantity>" + order.getPurchaseAmount() + "</quantity>");
			sb.append("<totalPrice>" + order.getTotalpay() + "</totalPrice>");
			sb.append("<occDate>" + order.getUseDate() + "</occDate>");
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<goodsCode>"
					+ order.getProHistory().getUuPid().substring(1)
					+ "</goodsCode>");
			sb.append("<goodsName>" + order.getProHistory().getName()
					+ "</goodsName>");
			sb.append("</familyOrder>");
			sb.append("</familyOrders>");
		} else {
			// 一般门票
			sb.append("<scenicOrders>");
			sb.append("<scenicOrder>");
			sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");
			sb.append("<price>" + order.getPurchasePrice() + "</price>");
			sb.append("<quantity>" + order.getPurchaseAmount() + "</quantity>");
			sb.append("<totalPrice>" + order.getTotalpay() + "</totalPrice>");
			sb.append("<occDate>" + order.getUseDate() + "</occDate>");
			sb.append("<goodsCode>"
					+ order.getProHistory().getUuPid().substring(1)
					+ "</goodsCode>");
			sb.append("<goodsName>" + order.getProHistory().getName()
					+ "</goodsName>");
			sb.append("</scenicOrder>");
			sb.append("</scenicOrders>");
		}

		sb.append("</order>");
		sb.append("</orderRequest>");
		sb.append("</PWBRequest>");

		String str = HttpUtil.postRequest(Global.getConfig("zyb_send"),
				getParam(sb.toString()), "utf-8");

		logger.debug(str);

		Map<String, String> map = parserXml(str);
		logger.debug(map.toString());

		if (!map.get("code").equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(map.get("code") + "="
					+ map.get("description"));
		} else {
			// 处理订单
			logger.info(str);
			// 订单号
			order.setUuOrdernum(map.get("orderCode"));
			order.setUuCode(map.get("assistCheckNo"));
			order.setStatus("0");
		}

		return order;
	}

	@Override
	public boolean updateOrder(OrderInfo order) throws JsonProcessingException,
			IOException {

		if (order.getPurchaseAmount() == 0) {
			return cancelOrder(order);
		}
		logger.debug("ZYB Update start: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		StringBuffer sb = new StringBuffer();
		sb.append("<PWBRequest>");
		sb.append("<transactionName>RETURN_TICKET_NUM_REQ</transactionName>");
		sb.append("<header>");
		sb.append("<application>SendCode</application>");
		sb.append("<requestTime>" + DateUtils.getDate("yyyy-MM-dd")
				+ "</requestTime>");
		sb.append("</header>");
		sb.append("<identityInfo>");
		sb.append("<corpCode>" + Global.getConfig("zyb_key") + "</corpCode>");
		sb.append("<userName>" + AC + "</userName>");
		sb.append("</identityInfo>");
		sb.append("<orderRequest>");

		sb.append("<returnTicket>");
		sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");
		if (order.getProHistory().getUuPid().toUpperCase().startsWith("T")) {
			sb.append("<orderType>family</orderType>");
		} else {
			sb.append("<orderType>scenic</orderType>");
		}
		sb.append("<returnNum>"
				+ (Integer.valueOf(order.getRemarks()) - order
						.getPurchaseAmount()) + "</returnNum>");
		sb.append("</returnTicket>");

		sb.append("</orderRequest>");
		sb.append("</PWBRequest>");

		String str = HttpUtil.postRequest(Global.getConfig("zyb_send"),
				getParam(sb.toString()), "utf-8");

		logger.debug(str);

		Map<String, String> map = parserXml(str);

		logger.debug(map.toString());

		if (!map.get("code").equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(map.get("code") + "="
					+ map.get("description"));
		} else {
			// 处理订单
			logger.info(str);
		}
		logger.debug("ZYB Update End: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

		return map.get("code").equals("0");
	}

	@Override
	public boolean cancelOrder(OrderInfo order) throws JsonProcessingException,
			IOException {
		logger.debug("ZYB Cancel start: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

		StringBuffer sb = new StringBuffer();
		sb.append("<PWBRequest>");
		sb.append("<transactionName>SEND_CODE_CANCEL_REQ</transactionName>");
		sb.append("<header>");
		sb.append("<application>SendCode</application>");
		sb.append("<requestTime>" + DateUtils.getDate("yyyy-MM-dd")
				+ "</requestTime>");
		sb.append("</header>");
		sb.append("<identityInfo>");
		sb.append("<corpCode>" + Global.getConfig("zyb_key") + "</corpCode>");
		sb.append("<userName>" + AC + "</userName>");
		sb.append("</identityInfo>");
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");

		sb.append("</order>");
		sb.append("</orderRequest>");
		sb.append("</PWBRequest>");

		String str = HttpUtil.postRequest(Global.getConfig("zyb_send"),
				getParam(sb.toString()), "utf-8");

		logger.debug(str);

		Map<String, String> map = parserXml(str);

		logger.debug(map.toString());

		if (!map.get("code").equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(map.get("code") + "="
					+ map.get("description"));
		} else {
			// 处理订单
			logger.info(str);
		}
		logger.debug("ZYB Cancel End: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

		return map.get("code").equals("0");

	}

	@Override
	public OrderInfo queryOrder(OrderInfo order)
			throws JsonProcessingException, IOException {
		logger.debug("ZYB Query Start: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		StringBuffer sb = new StringBuffer();
		sb.append("<PWBRequest>");
		sb.append("<transactionName>CHECK_STATUS_QUERY_REQ</transactionName>");
		sb.append("<header>");
		sb.append("<application>SendCode</application>");
		sb.append("<requestTime>" + DateUtils.getDate("yyyy-MM-dd")
				+ "</requestTime>");
		sb.append("</header>");
		sb.append("<identityInfo>");
		sb.append("<corpCode>" + Global.getConfig("zyb_key") + "</corpCode>");
		sb.append("<userName>" + AC + "</userName>");
		sb.append("</identityInfo>");
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<orderCode>" + order.getOrderId() + "</orderCode>");

		sb.append("</order>");
		sb.append("</orderRequest>");
		sb.append("</PWBRequest>");

		String str = HttpUtil.postRequest(Global.getConfig("zyb_send"),
				getParam(sb.toString()), "utf-8");

		logger.debug(str);

		Map<String, String> map = parserXml(str);

		logger.debug(map.toString());

		if (!map.get("code").equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(map.get("code") + "="
					+ map.get("description"));
		} else {
			// 处理订单
			logger.info(str);

			int needCheckNum = Integer.parseInt(map.get("needCheckNum"));
			// int alreadyCheckNum =
			// Integer.parseInt(map.get("alreadyCheckNum"));
			int alreadyCheckNum = 0;
			int returnNum = Integer.parseInt(map.get("returnNum"));
			int num = needCheckNum - returnNum - alreadyCheckNum;

			String state = map.get("checkStatus");
			if (state.equals("checked")) {
				state = "1";
			} else if (num == 0) {
				state = "3";
			} else if (state.equals("un_check")) {
				state = "0";
			} else {
				// 未知状态
				logger.error(str);
				state = "-5";
			}
			order.setStatus(state);
			order.setPurchaseAmount(num);
		}

		logger.debug("ZYB Query End: "
				+ DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

		return order;

	}

	@Override
	public String queryCodeOrder(OrderInfo order) throws RemoteException {
		return order.getUuCode();
	}

	public static void main(String[] args) {
		// System.err.println(md5s("1314520"));
		try {
			ZYBTerminalService service = new ZYBTerminalService();

			OrderInfo order = new OrderInfo();
			order.setOrderId("8201409211000239060");
			order.setCustomerId("432524198801113011");
			order.setCustomerName("廖鹏");
			order.setCustomerMobile("13162796265");
			order.setPurchasePrice(2.0);
			order.setPurchaseAmount(3);
			order.setTotalpay(2.0);
			order.setUseDate("2014-09-25");
			ProductHistory productHistory = new ProductHistory();
			productHistory.setUuPid("T20140224011129");
			productHistory.setName("成人票");
			order.setProHistory(productHistory);
			service.submitOrder(order);

			// service.cancelOrder(order);
			// order.setPurchaseAmount(2);
			// service.updateOrder(order);
			// service.queryOrder(order);
			// BZService service = new BZService();
			// String string = service.binding.getmesxml(AC, PW);
			// System.err.println(string);

			// String xml =
			// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PWBResponse>  <transactionName>SEND_CODE_RES</transactionName>  <code>0</code>  <description>成功</description>  <orderResponse>    <order>      <certificateNo>432524198801113011</certificateNo>      <linkName>廖鹏</linkName>      <linkMobile>13162796265</linkMobile>      <orderCode>201409210008399099</orderCode>      <orderPrice>2.0</orderPrice>      <payMethod>vm</payMethod>      <groupNo></groupNo>      <assistCheckNo>10580448</assistCheckNo>      <payStatus>payed</payStatus>      <src>interface</src>      <familyOrders>        <familyOrder>          <orderCode>2014092100111</orderCode>          <totalPrice>2.0</totalPrice>          <price>2.0</price>          <quantity>3</quantity>          <occDate>2014-09-25 00:00:00</occDate>          <goodsCode>20140224011129</goodsCode>          <goodsName>关麓成人票</goodsName>        </familyOrder>      </familyOrders>    </order>  </orderResponse></PWBResponse>";

			// System.out.println(service.parserXml(xml));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, String> parserXml(String xml) {
		Map<String, String> map = Maps.newLinkedHashMap();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element datas = document.getRootElement();
			for (Iterator i = datas.elementIterator(); i.hasNext();) {
				Element data = (Element) i.next();

				if (data.getName().equalsIgnoreCase("orderResponse")
						|| data.getName().equalsIgnoreCase("subOrders")) {
					Element orderElement = (Element) data.elementIterator()
							.next();
					for (Iterator j = orderElement.elementIterator(); j
							.hasNext();) {
						Element node = (Element) j.next();
						map.put(node.getName(), node.getText());
					}
				} else {
					map.put(data.getName(), data.getText());
				}

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

}