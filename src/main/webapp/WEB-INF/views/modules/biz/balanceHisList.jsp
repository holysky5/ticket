<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>余额历史管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function openRecharge(){
			var url = "${ctx}/biz/balanceHis/recharge";
			top.$.jBox.open("iframe:"+url, "在线充值", 600, 420, {iframeScrolling: 'no', buttons: {} });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/balanceHis/mylist">账户明细</a></li>
		<shiro:hasPermission name="biz:balanceHis:edit"><li><a href="javascript:openRecharge()">在线充值</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="balanceHis" action="${ctx}/biz/balanceHis/mylist" method="post" class="breadcrumb  form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<span class="label label-success">
		<c:if test="${userType == 4 }">
		当前易往平台应付景区余款：
		</c:if>
		<c:if test="${userType == 3 }">
		易往平台账户余额：
		</c:if>
		<fmt:formatNumber type="currency" pattern="#,##0.00#">${balance}</fmt:formatNumber>元</span>&nbsp;&nbsp;
		<label>类型 ：</label><form:select path="type" style="width:100px;">
			<form:option value="">--请选择--</form:option>
			<form:options items="${fns:getDictList('biz_recharge_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<label>状态 ：</label><form:select path="status" style="width:100px;">
			<form:option value="">--请选择--</form:option>
			<form:option value="0">成功</form:option>
			<form:option value="-1">失败</form:option>
		</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	
	
	
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>操作时间</th><th>操作类型</th><th>操作前余额</th><th>操作金额</th><th>操作后余额</th><th>操作原因</th><th>操作状态</th><th>备注</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="balanceHis" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td><fmt:formatDate value="${balanceHis.createDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${fns:getDictLabel(balanceHis.type, 'biz_recharge_type', '')}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${balanceHis.begin}</fmt:formatNumber></td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${balanceHis.amount}</fmt:formatNumber></td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${balanceHis.end}</fmt:formatNumber></td>
				<td>${balanceHis.reason}</td>
				<td>
				<c:if test="${balanceHis.status==0}">
						<img src="${ctxStatic}/images/true.png" title="成功">
					</c:if>
					<c:if test="${balanceHis.status==-1}">
						<img src="${ctxStatic}/images/false.png" title="失败">
					</c:if>
				</td>
				<td>${balanceHis.remarks}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
