<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>景区列表</title>
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
		
		function openView(name,id){
			var url = "${ctx}/biz/supplier/view?id="+id;
			top.$.jBox.open("iframe:"+url, name+"景区详情", 950, 500, {top:60, buttons: { } });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/supplier/list/valid">景区列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/biz/supplier/list/valid" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>景区名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>所在地区：</label><form:input path="area" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>景区名称</th><th>所在地区</th><th style="width:150px;">操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplier" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td><a href="javascript:openView('${supplier.name}','${supplier.id}')" >${supplier.name}</a></td>
				<td>${supplier.area}</td>
				<td>
    				<a href="javascript:openView('${supplier.name}','${supplier.id}')" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 详情</a>
					<a href="${ctx}/biz/product/listProduct4Distributor/${supplier.id}" class="btn btn-mini btn-primary"><i class="icon-circle-arrow-down icon-white"></i> 预订</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
