<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户充值</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#pay").click(function () {
				var param={
					rechargeNo:$("input[name=title]").val(),
					amount:$("input[name=payAmount]").val()
				};
				$.post('${ctx}/biz/balanceHis/addConfirmRecharge.json', param)
						.done(function (data) {
//							$("#inputForm").submit();
						});
			});

			<%--$("#name").focus();--%>
			<%--$("#inputForm").validate({--%>
				<%--rules: {--%>
					<%--amount: {amountset: true}--%>
				<%--},--%>
				<%--messages: {--%>
					<%--amount: {amountset: "充值限额为1~5000的整数值",required:"充值金额必须大于1元"}--%>
				<%--},--%>
				<%--submitHandler: function(form){--%>

					<%--form.submit();--%>
					<%--var submit = function (v, h, f) {--%>
					    <%--if (v == true){--%>
					    	<%--window.top.mainFrame.location.reload();--%>
					    	<%--top.$.jBox.close(true);--%>
					    <%--}--%>

					    <%--return true;--%>
					<%--};--%>
					<%--// 自定义按钮--%>
					<%--top.$.jBox.confirm("是否支付成功？", "是否支付成功", submit, {  top: '30%',buttons: { '支付成功': true, '重新支付': false} });--%>


				<%--},--%>
				<%--errorContainer: "#messageBox",--%>
				<%--errorPlacement: function(error, element) {--%>
					<%--$("#messageBox").text("输入有误，请先更正。");--%>
					<%--if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){--%>
						<%--error.appendTo(element.parent().parent());--%>
					<%--} else {--%>
						<%--error.insertAfter(element);--%>
					<%--}--%>
				<%--}--%>
			<%--});--%>
			<%--jQuery.validator.addMethod("amountset", function(value, element) {--%>
				<%--if(isNaN(value)) {--%>
					<%--value = 0;--%>
				<%--}--%>
				<%--if(value<1 || value >5000){--%>
					<%--return false;--%>
				<%--}--%>
			    <%--return true;--%>
			 <%--}, "充值限额为1~5000的整数值");--%>

			<%--$("#amount").blur(function(){--%>
				<%--var value = $("#amount").val()*${fee}*100/10000;--%>
				<%--$("#fee").text(value.toFixed(2));--%>
			<%--});--%>

		});




	</script>
</head>
<body>
	<div id="head" style="border-bottom: solid 2px #CFD2D7;padding: 10px;margin-bottom:20px;">
		<div style="float:left;"><img src="${ctxStatic}/images/alipay_logo.png"></div>
		<dl style="text-align: right;font-size: 12px;">
		<a target="_blank" href="http://www.alipay.com">支付宝首页</a>|
        <a target="_blank" href="http://help.alipay.com">帮助中心</a>
        </dl>
    </div>


	<%--支付宝转账form--%>
	<form id="inputForm" action="https://shenghuo.alipay.com/send/payment/fill.htm" class="form-horizontal" method="POST" target="_blank" accept-charset="GBK">
		<input name="optEmail" type="hidden" value="${fns:getConfig('allinpay_seller_email')}"/>
		<div class="control-group">
			<label class="control-label">当前余额(元):</label>
			<div class="controls">
				<span class="uneditable-input">${balanceHis.begin}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>充值金额(元):</label>
			<div class="controls">
				<input name="payAmount" type="number" step="any" placeholder="请输入您要充值得金额" autofocus autocomplete="off"/>
			</div>
		</div>
		<input name="title" type="hidden" value="${rechargeNo}"/>
		<input name="memo" type="hidden" value="请勿更改转账备注,系统根据备注信息确认充值.若未及时收到充值成功信息,请致电易网客服"/>
		<div class="control-group">
			<div class="controls">
				<button id="pay" ><img src="https://img.alipay.com/sys/personalprod/style/mc/btn-index.png" alt="充值"></button>
			</div>
		</div>
	</form>


	<%--<form:form id="inputForm" modelAttribute="balanceHis" action="${ctx}/biz/balanceHis/recharge" method="post" target="_blank"  class="form-horizontal">--%>
		<%--<form:hidden path="id"/>--%>
		<%--<tags:message content="${message}"/>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">当前余额(元):</label>--%>
			<%--<div class="controls">--%>
				<%--<span class="uneditable-input">${balanceHis.begin}</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label"><span class="asterisk">*</span>充值金额(元):</label>--%>
			<%--<div class="controls" >--%>
				<%--<div>--%>
				<%--<form:input path="amount" htmlEscape="false" maxlength="200" class="required digits"/>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group" style="display:none;">--%>
			<%--<label class="control-label">支付宝汇费(元):</label>--%>
			<%--<div class="controls">--%>
				<%--<span class="uneditable-input" id="fee">0.00</span>--%>
				<%--<span>${fee}%</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div style="color:red;font-size:10px;">提示：充值5000元以上等大额时，建议直接转帐我公司公帐帐号或我公司支付宝以免客服及时处理。</div>--%>
		<%--<div class="form-actions">--%>
			<%--<shiro:hasPermission name="biz:balanceHis:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="确认付款"/>&nbsp;</shiro:hasPermission>--%>
		<%--</div>--%>
	<%--</form:form>--%>
</body>
</html>
