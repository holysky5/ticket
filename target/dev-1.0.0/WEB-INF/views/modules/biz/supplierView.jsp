<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>景区详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>

	<div style="padding:8px;" >
	<shiro:hasPermission name="biz:supplier:edit">
   <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>帐号</th><th>名称</th><th>姓名</th><th>手机</th><th>电话</th><th>余额</th><th>状态</th></tr></thead>
		<tbody>
			<tr>
				<td>${supplier.no}</td>
				<td>${supplier.name}</td>
				<td>${supplier.contactName}</td>
				<td>${supplier.contactMobile}</td>
				<td>${supplier.contactPhone}</td>
				<td>${supplier.balance}</td>
				<td>${fns:getDictLabel(supplier.status, 'biz_user_status', '')}</td>
			</tr>
		</tbody>
	</table>
	</shiro:hasPermission>
	<table class="table table-striped table-bordered table-condensed " >
 		<tr>
			<td style="width:70px;"><b>景区名称:</b></td>
			<td colspan="3"><div>${supplier.scenicDetailMap['0']}</div></td>
			
		<tr>
		<tr>
			<td style="width:70px;"><b>景区地址:</b></td>
			<td  style="width:350px;"><div>${supplier.scenicDetailMap['2']}</div></td>
			<td style="width:70px;"><b>开放时间:</b></td>
			<td><div>${supplier.scenicDetailMap['1']}</div></td>
		<tr>
		<tr>
			<td style="width:70px;"><b>取票地点:</b></td>
			<td><div>${supplier.scenicDetailMap['3']}</div></td>
			<td style="width:70px;"><b>入园凭证:</b></td>
			<td><div>${supplier.scenicDetailMap['4']}</div></td>
		<tr>
		<tr>
			<td style="width:70px;"><b>退改规则:</b></td>
			<td colspan="3"><div style="min-height:50px;">${supplier.scenicDetailMap['5']}</div></td>
		<tr>
		<tr>
			<td style="width:70px;"><b>温馨提示:</b></td>
			<td colspan="3"><div style="min-height:50px;">${supplier.scenicDetailMap['6']}</div></td>
		<tr>
	   	
  	</table>
  	</div>

</body>
</html>
