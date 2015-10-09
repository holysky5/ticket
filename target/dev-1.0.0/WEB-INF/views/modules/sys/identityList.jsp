<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流水号管理</title>
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
		<li class="active"><a href="${ctx}/sys/identity/">流水号列表</a></li>
		<shiro:hasPermission name="sys:identity:edit"><li><a href="${ctx}/sys/identity/form">流水号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="identity" action="${ctx}/sys/identity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>别名</th><th>规则</th><th>序号生成</th><th>长度</th><th>初始值</th><th>步长</th><th>当前值</th><shiro:hasPermission name="sys:identity:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="identity">
			<tr>
				<td><a href="${ctx}/sys/identity/form?id=${identity.id}">${identity.name}</a></td>
				<td>${identity.alias}</td>
				<td>${identity.rule}</td>
				<td>${fns:getDictLabel(identity.genEveryDay, 'sys_identity_create', '无')}</td>
				<td>${identity.noLength}</td>
				<td>${identity.initValue}</td>
				<td>${identity.step}</td>
				<td>${identity.curValue}</td>
				<shiro:hasPermission name="sys:identity:edit"><td>
    				<a href="${ctx}/sys/identity/form?id=${identity.id}">修改</a>
					<a href="${ctx}/sys/identity/delete?id=${identity.id}" onclick="return confirmx('确认要删除该流水号吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
