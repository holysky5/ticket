<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门票详情管理</title>
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
		<li><a href="${ctx}/biz/supplier/list/product?stopTime=${product.stopTime}">景区列表</a></li>
		<li class="active"><a href="${ctx}/biz/product/listProduct4Admin/${supplier.id}?stopTime=${product.stopTime}">${product.stopTime == '1'?'仓库':'在售'}门票列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/biz/product/listProduct4Admin/${supplier.id}?stopTime=${product.stopTime}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>门票名称(门票类型) ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;&nbsp;&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>供应商</th><th>门票名称</th><th>挂牌价</th><th>指导价</th><th>采购价</th><th>易往价</th><th>状态</th><th>审核</th><th>发布时间</th><th>更新时间</th><shiro:hasPermission name="biz:product:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${product.scenic.name}</td>
				<td>${product.name}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.originalPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.recommendPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.purchasePrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.platformPrice}</fmt:formatNumber> </td>
			
				<td>
					<c:if test="${product.status==0}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${product.status==1}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
				</td>
				<td>
					<c:if test="${product.auditFlag==1}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${product.auditFlag==0}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
				</td>
				<td><fmt:formatDate value="${product.createDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${product.updateDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<shiro:hasPermission name="biz:product:edit"><td>
    				<c:if test="${product.auditFlag==1}">
						<a href="${ctx}/biz/product/auditForm?id=${product.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
					</c:if>
					<c:if test="${product.auditFlag==0}">
						<a href="${ctx}/biz/product/auditForm?id=${product.id}" class="btn btn-mini btn-primary"><i class="icon-flag icon-white"></i> 上架</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
