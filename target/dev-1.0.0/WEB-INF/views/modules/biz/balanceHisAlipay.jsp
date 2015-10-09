<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行转账</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});


		function openRecharge(){
			var url = "${ctx}/biz/balanceHis/recharge";
			top.$.jBox.open("iframe:"+url, "在线充值", 600, 420, {iframeScrolling: 'no', buttons: {} });
		}

		function openTransformAccount(){
			var url = "${ctx}/biz/balanceHis/transformAccount";
			top.$.jBox.open("iframe:"+url, "在线充值", 600, 420, {iframeScrolling: 'no', buttons: {} });
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/biz/balanceHis/alipay">在线充值</a></li>
	</ul><br/>
	<div style="margin-bottom:20px;">
	<a href="javascript:openRecharge()" class="btn btn-info">支付宝在线充值</a>
		test
	<a href="javascript:openTransformAccount()" class="btn btn-info">支付宝在线转账</a>

	</div>

	<div class="alert alert-success">
		<p>用户您好，因支付宝充值需要收手续费，如果充值金额较大的时候，可以直接和我们沟通，通过银行转账或者直接支付宝转账，免收手续费。</p>
		<p>请提前和客服联系，以便我们查账并及时给您充值。</p>
	</div>

 		<div class="row">
        	<div class="span6 home-div" style="min-height:132px;">
        	<div class="header">
        	支付宝账户信息
        	</div>
        	<table  class="table table-striped table-bordered">

				<tbody>
				<tr>
					<td><b>支付宝用户名：</b>${fns:getConfig('allinpay_name')}</td>
				</tr>
				<tr>
					<td><b>支付宝帐号：</b>${fns:getConfig('allinpay_seller_email')}</td>
				</tr>

				</tbody>
			</table>
        	</div>

        	<div class="span6 home-div" >
        	<div class="header">
        	银行账户信息
        	</div>
        	<table  class="table table-striped table-bordered">
				<tbody>
				<tr>
					<td><b>银行户名：</b>${fns:getConfig('bank_user_name')}</td>
				</tr>
				<tr>
					<td><b>银行帐号：</b>${fns:getConfig('bank_user_no')}</td>
				</tr>
				<tr>
					<td><b>开户银行：</b>${fns:getConfig('bank_name')}</td>
				</tr>
				</tbody>
			</table>
        	</div>
        </div>

</body>
</html>
