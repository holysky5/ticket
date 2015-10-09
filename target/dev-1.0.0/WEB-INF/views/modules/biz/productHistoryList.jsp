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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/productHistory/">门票历史列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productHistory" action="${ctx}/biz/productHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>门票名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>门票编号</th><th>门票名称</th><th>门票分类</th><th>数量</th><th>原价</th><th>采购价</th><th>指导价</th><th>发布时间</th><th>描述</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productHistory" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${productHistory.productId}</td>
				<td>${productHistory.name}</td>
				<td>${fns:getDictLabel(product.categoryName, 'biz_product_category', '景区门票')}</td>
				<td>${productHistory.amount}</td>
				<td>${productHistory.originalPrice}</td>
				<td>${productHistory.purchasePrice}</td>
				<td>${productHistory.recommendPrice}</td>
				<td>${productHistory.createDate}</td>
				<td>${productHistory.remarks}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
