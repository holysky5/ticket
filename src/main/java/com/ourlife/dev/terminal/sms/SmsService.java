package com.ourlife.dev.terminal.sms;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.utils.DateUtils;
import com.ourlife.dev.modules.biz.entity.OrderInfo;
import com.ourlife.dev.terminal.zyb.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SmsService {
	public Logger logger = LoggerFactory.getLogger(getClass());

	private static final String AC = Global.getConfig("sms_ac");
	private static final String PW = Global.getConfig("sms_pw");
	private static final boolean IS_OPEN = Boolean.valueOf(Global
			.getConfig("sms_open"));
	private static final String KEY = "【易往】";

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
		return str.toUpperCase();
	}

	private String getSmsContent(OrderInfo orderInfo) {
		String content = "";
		if (orderInfo.getSupplier().getCheckTerminal().equals("0")) {
			content = orderInfo.getCustomerName() + "，您好，您预定的（"
					+ orderInfo.getUseDate() + "）"
					+ orderInfo.getSupplier().getName() + "-"
					+ orderInfo.getProHistory().getName()
					+ orderInfo.getPurchaseAmount() + "张，已经预定成功，取票码："
					+ orderInfo.getUuCode() + "，"
					+ replaceAllHtml(orderInfo.getProHistory().getNotice())
					+ "，若有疑义请联系小二4009949520转2。";
		} else {
			content = orderInfo.getCustomerName() + "，您好，您预定的（"
					+ orderInfo.getUseDate() + "）"
					+ orderInfo.getSupplier().getName() + "-"
					+ orderInfo.getProHistory().getName()
					+ orderInfo.getPurchaseAmount() + "张，已经预定成功，"
					+ replaceAllHtml(orderInfo.getProHistory().getNotice())
					+ "，若有疑义请联系小二4009949520转2。";
		}

		return content;
	}

	private static String replaceAllHtml(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	public String sendSms(OrderInfo orderInfo) {
		if (!IS_OPEN) {
			return "1";
		}
		String reply = "";

		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("accName", AC);
		hashmap.put("accPwd", md5s(PW));
		hashmap.put("aimcodes", orderInfo.getCustomerMobile());
		hashmap.put("content", getSmsContent(orderInfo) + KEY);
		hashmap.put("schTime", "");
		hashmap.put("bizId", DateUtils.getDate("yyyyMMddHHmmss"));
		hashmap.put("dataType", "string");

		String str = HttpUtil.postRequest(Global.getConfig("sms_send"),
				hashmap, "utf-8");

		logger.debug(str);
		String[] replys = str.split(";");
		if (replys[0].equals("1")) {
			reply = "1";
		} else {
			reply = replys[1];
		}
		return reply;
	}

	public String sendSms(String mobiles, String content) {
		if (!IS_OPEN) {
			return "1";
		}

		String reply = "";

		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("accName", AC);
		hashmap.put("accPwd", md5s(PW));
		hashmap.put("aimcodes", mobiles);
		hashmap.put("content", content + KEY);
		hashmap.put("schTime", "");
		hashmap.put("bizId", DateUtils.getDate("yyyyMMddHHmmss"));
		hashmap.put("dataType", "string");

		String str = HttpUtil.postRequest(Global.getConfig("sms_send"),
				hashmap, "utf-8");

		logger.debug(str);
		String[] replys = str.split(";");
		if (replys[0].equals("1")) {
			reply = "1";
		} else {
			reply = replys[1];
		}
		return reply;
	}

	public static void main(String[] args) {
		// 需要网站审核
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("accName", "15055990218");
		hashmap.put("accPwd", md5s("hs15055990218"));
		hashmap.put("aimcodes", "13166059778");
		hashmap.put("content", "廖鹏，您好【易往】");
		hashmap.put("schTime", "");
		hashmap.put("bizId", DateUtils.getDate("yyyyMMddHHmmss"));
		hashmap.put("dataType", "string");

		// String str = HttpUtil.postRequest(Global.getConfig("sms_send"),
		// hashmap, "utf-8");
		String str = "<p><span style=\"color: rgb(85, 85, 85); font-family: Helvetica, Georgia, Arial, sans-serif, 宋体; font-size: 13px; line-height: 20px; white-space: normal; background-color: rgb(249, 249, 249); \">入园时凭预订时的身份证到景区售票处刷身份证换票入园。</span></p>";
		// str = HtmlUtils.htmlUnescape(str);
		str = replaceAllHtml(str);
		System.out.println(str);

	}
}
