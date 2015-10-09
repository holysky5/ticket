<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销商审核</title>
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
		<li class="active"><a href="${ctx}/biz/distributor/audit">待审核分销商列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="distributor" action="${ctx}/biz/distributor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>帐号</th><th>名称</th><th>公司</th><th>手机</th><th>电话</th><th>QQ</th><th>审核备注</th><shiro:hasPermission name="biz:distributor:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="distributor" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${distributor.username}</td>
				<td>${distributor.name}</td>
				<td>${distributor.company}</td>
				<td>${distributor.mobile}</td>
				<td>${distributor.phone}</td>
				<td>${distributor.qq}</td>
				<td>${distributor.remarks}</td>
				<shiro:hasPermission name="biz:distributor:edit"><td>
    				<a href="${ctx}/biz/distributor/view/nopass?id=${distributor.id}" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
