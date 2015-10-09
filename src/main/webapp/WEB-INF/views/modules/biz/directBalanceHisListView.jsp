<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直通余额管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/direct/">直通景区列表</a></li>
		<li class="active"><a href="${ctx}/biz/directBalanceHis/list/view?direct.id=${directBalanceHis.direct.id}">账户明细</a></li>
		<shiro:hasPermission name="biz:direct:edit"><li><a href="${ctx}/biz/directBalanceHis/form?direct.id=${directBalanceHis.direct.id}">余额管理</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="directBalanceHis" action="${ctx}/biz/directBalanceHis/list/view?direct.id=${directBalanceHis.direct.id}" method="post" class="breadcrumb  form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<span class="label label-success">${direct.supplier.name}-景区预存款可使用余额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${direct.balance}</fmt:formatNumber>元</span>&nbsp;&nbsp;
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
		<c:forEach items="${page.list}" var="directBalanceHis" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td><fmt:formatDate value="${directBalanceHis.createDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${fns:getDictLabel(directBalanceHis.type, 'biz_recharge_type', '')}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directBalanceHis.begin}</fmt:formatNumber></td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directBalanceHis.amount}</fmt:formatNumber></td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directBalanceHis.end}</fmt:formatNumber></td>
				<td>${directBalanceHis.reason}</td>
				<td>
				<c:if test="${directBalanceHis.status==0}">
						<img src="${ctxStatic}/images/true.png" title="成功">
					</c:if>
					<c:if test="${directBalanceHis.status==-1}">
						<img src="${ctxStatic}/images/false.png" title="失败">
					</c:if>
				</td>
				<td>${directBalanceHis.remarks}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
