<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行转账</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		
	

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/biz/balanceHis/bank">银行转账</a></li>
	</ul><br/>
	<div class="alert alert-success">
		<p>用户您好，因支付宝充值需要收手续费，如果充值金额较大的时候，可以直接和我们沟通，通过银行转账或者直接支付宝转账，免收手续费。</p>
		<p>请提前和客服联系，以便我们查账并及时给您充值。</p>
	</div>
	<table  class="table table-striped table-bordered">
		<thead>
			<tr>
			<th>银行账户信息</th>
			</tr>
		</thead>
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
</body>
</html>
