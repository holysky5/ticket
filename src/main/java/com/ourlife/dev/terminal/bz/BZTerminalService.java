package com.ourlife.dev.terminal.bz;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.TerminalService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.rpc.ServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * 贝竹接口
 *
 * @author rocliao
 *
 */
public class BZTerminalService implements TerminalService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String AC = Global.getConfig("bz_ac");
	private static final String PW = Global.getConfig("bz_pw");

	private GetmesxmlBindingStub binding = null;

	public BZTerminalService() {
		Getmesxml service = new GetmesxmlLocator();
		try {
			binding = (GetmesxmlBindingStub) service.getgetmesxmlPort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String getParam(HashMap<String, String> hashmap) {
		TreeMap<String, String> treemap = new TreeMap<String, String>(hashmap);
		String signValue = "";
		String param = "";
		for (String key : treemap.keySet()) {
			signValue = signValue + key;
			signValue = signValue + treemap.get(key);
			param = param + key + "=" + treemap.get(key) + "&";

		}
		// System.out.println(signValue);
		signValue = PW + signValue + PW;
		// System.out.println(signValue);
		signValue = md5s(signValue);
		signValue = signValue.toUpperCase();

		param = param + "sign=" + signValue;

		return param;
	}

	@Override
	public OrderInfo submitOrder(OrderInfo order)
			throws JsonProcessingException, IOException {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("state", "16");
		hashmap.put("other_id", order.getOrderId());
		hashmap.put("uname", AC);
		hashmap.put("sfz", order.getCustomerId());
		hashmap.put("phone", order.getCustomerMobile());
		hashmap.put("gid", order.getProHistory().getUuPid());
		hashmap.put("num", order.getPurchaseAmount() + "");
		hashmap.put("plantime", order.getUseDate());
		hashmap.put("format", "json");
		hashmap.put("type", "1");

		String param = getParam(hashmap);
		logger.debug(param);

		String str = sendPost(Global.getConfig("bz_send"), param);

		logger.debug(str);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(str);
		String res = rootNode.get("res").toString();

		if (!res.equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(BZErrorCode.MAP.get(res));
		} else {
			// 处理订单
			logger.info(str);
			// 订单号
			order.setUuOrdernum(rootNode.get("order_id").toString());
			order.setStatus("0");
		}

		return order;
	}

	@Override
	public boolean updateOrder(OrderInfo order) throws JsonProcessingException,
			IOException {
		HashMap<String, String> hashmap = new HashMap<String, String>();

		hashmap.put("other_id", order.getOrderId());
		hashmap.put("uname", AC);
		hashmap.put("sfz", order.getCustomerId());
		hashmap.put("phone", order.getCustomerMobile());

		hashmap.put("num", order.getPurchaseAmount() + "");
		hashmap.put("plantime", order.getUseDate());
		hashmap.put("format", "json");
		hashmap.put("type", "1");

		String param = getParam(hashmap);
		logger.debug(param);

		String str = sendPost(Global.getConfig("bz_revise"), param);

		logger.debug(str);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(str);
		String res = rootNode.get("res").toString();

		if (!res.equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(BZErrorCode.MAP.get(res));
		} else {
			// 处理订单
			logger.info(str);
		}

		return res.equals("0");
	}

	@Override
	public boolean cancelOrder(OrderInfo order) throws JsonProcessingException,
			IOException {
		HashMap<String, String> hashmap = new HashMap<String, String>();

		hashmap.put("other_id", order.getOrderId());
		hashmap.put("uname", AC);
		hashmap.put("sfz", order.getCustomerId());

		hashmap.put("num", "0");
		hashmap.put("plantime", order.getUseDate());
		hashmap.put("format", "json");
		hashmap.put("type", "1");

		String param = getParam(hashmap);
		logger.debug(param);

		String str = sendPost(Global.getConfig("bz_revise"), param);

		logger.debug(str);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(str);
		String res = rootNode.get("res").toString();

		if (!res.equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(BZErrorCode.MAP.get(res));
		} else {
			// 处理订单
			logger.info(str);
		}

		return res.equals("0");

	}

	@Override
	public OrderInfo queryOrder(OrderInfo order)
			throws JsonProcessingException, IOException {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		// hashmap.put("order_id", "1602042");
		hashmap.put("other_id", order.getOrderId());
		hashmap.put("uname", AC);
		hashmap.put("format", "json");

		String param = getParam(hashmap);
		logger.debug(param);

		String str = sendPost(Global.getConfig("bz_select"), param);

		logger.debug(str);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(str);
		String res = rootNode.get("res").toString();
		if (!res.equals("0")) {
			logger.error(order.toString());
			logger.error(str);
			throw new RemoteException(BZErrorCode.MAP.get(res));
		} else {
			// 处理订单
			logger.info(str);
			String state = rootNode.get("state").getTextValue();
			if (state.equals("USED")) {
				state = OrderInfo.STATUS_USED;
			} else if (state.equals("CANCEL")) {
				state = OrderInfo.STATUS_CANCELD;
			} else if (state.equals("UNUSED")) {
				state = OrderInfo.STATUS_UNUSED;
			} else {
				// 未知状态
				logger.error(str);
				state = OrderInfo.STATUS_UNKOWN;
			}
			order.setStatus(state);
			if (!state.equals("1")) {
				order.setPurchaseAmount(rootNode.get("num").getValueAsInt());
			}
		}

		return order;

	}

	@Override
	public String queryCodeOrder(OrderInfo order) throws RemoteException {
		return order.getCustomerId();
	}

	public static void main(String[] args) {
		// System.err.println(md5s("1314520"));
		try {
			BZTerminalService service = new BZTerminalService();

			// service.submitOrder(new OrderInfo());

			// service.updateOrder(new OrderInfo());
			service.queryOrder(new OrderInfo());
			// BZService service = new BZService();
			// String string = service.binding.getmesxml(AC, PW);
			// System.err.println(string);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String md5s(String plainText) {
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
			// System.out.println("result: " + buf.toString());// 32位的加密
			// System.out.println("result: " + buf.toString().substring(8,
			// 24));// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return str;
	}
}
