<%--
  Created by IntelliJ IDEA.
  User: holysky
  Date: 15/6/25
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值金额确认</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

//		$(document).ready(function() {
//
//		});
//		function page(n,s){
//			$("#pageNo").val(n);
//			$("#pageSize").val(s);
//			$("#searchForm").submit();
//        	return false;
//        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<c:if test="${not empty distributor }">--%>
		<%--<li><a href="${ctx}/biz/${userType}/">分销商列表</a></li>--%>
		<%--</c:if>--%>
		<%--<c:if test="${not empty supplier }">--%>
		<%--<li><a href="${ctx}/biz/${userType}/">供应商列表</a></li>--%>
		<%--</c:if>--%>
		<%--<li class="active"><a href="${ctx}/biz/balanceHis/list/${userType}/${distributor.id}${supplier.id}">账户明细</a></li>--%>
		<%--<li><a href="${ctx}/biz/balanceHis/manage/${userType}/${distributor.id}${supplier.id}">余额管理</a></li>--%>
	<%--</ul>--%>
	<%--<form:form id="searchForm" modelAttribute="balanceHis" action="${ctx}/biz/balanceHis/list/${userType}/${userid}" method="post" class="breadcrumb  form-search">--%>
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
		<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<%--<span class="label label-success">--%>
		<%--<c:if test="${not empty  supplier}">--%>
		<%--当前易往平台应付${supplier.name}余款：--%>
		<%--</c:if>--%>
		<%--<c:if test="${not empty distributor}">--%>
		<%--${distributor.name}-易往平台账户余额：--%>
		<%--</c:if>--%>
		<%--<fmt:formatNumber type="currency" pattern="#,##0.00#">${distributor.balance}${supplier.balance}</fmt:formatNumber>元</span>&nbsp;&nbsp;--%>
		<%--<label>类型 ：</label><form:select path="type" style="width:100px;">--%>
			<%--<form:option value="">--请选择--</form:option>--%>
			<%--<form:options items="${fns:getDictList('biz_recharge_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
		<%--</form:select>--%>
		<%--<label>状态 ：</label><form:select path="status" style="width:100px;">--%>
			<%--<form:option value="">--请选择--</form:option>--%>
			<%--<form:option value="0">成功</form:option>--%>
			<%--<form:option value="-1">失败</form:option>--%>
		<%--</form:select>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	<%--</form:form>--%>
    <form id="confirmForm" class="breadcrumb form-search" action="recharge_confirm" method="post">
		<label>充值确认码 ：</label><input id="rechargeNo" name="rechargeNo" class="input-medium" type="text" value="" maxlength="50"/>
		<label>充值金额 ：</label><input id="amount" name="amount" class="input-medium" type="number" value=""/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="确认充值" onclick="loading('正在提交,请稍等...');"/>
	</form>
	<tags:message content="${message}"/>
</body>
</html>

