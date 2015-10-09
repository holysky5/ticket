<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团购产品管理</title>
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
		<li class="active"><a href="${ctx}/book/tuanProduct/">团购产品列表</a></li>
		<shiro:hasPermission name="book:tuanProduct:edit"><li><a href="${ctx}/book/tuanProduct/form">团购产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tuanProduct" action="${ctx}/book/tuanProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>管理标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>团购网 ：</label>
		<form:select path="tuanType" style="width:100px;">
			<form:option value="">--请选择--</form:option>
				<form:options items="${fns:getDictList('tuan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>#</th><th>团购网</th><th>管理标题</th><th>前台名称</th><th>产品地区</th><th>预订时间</th><shiro:hasPermission name="book:tuanProduct:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="tuanProduct" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${fns:getDictLabel(tuanProduct.tuanType, 'tuan_type', '')}</td>
				<td>${tuanProduct.title}</td>
				<td>${tuanProduct.name}</td>
				<td>${tuanProduct.province}${tuanProduct.city}</td>
				<td>${tuanProduct.effectTimeDesc}</td>
				<shiro:hasPermission name="book:tuanProduct:edit"><td>
    				<a href="${ctx}/book/tuanProduct/form?id=${tuanProduct.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
					<a href="${ctx}/book/tuanProduct/delete?id=${tuanProduct.id}" onclick="return confirmx('确认要删除该团购产品吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-trash icon-white"></i> 删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
