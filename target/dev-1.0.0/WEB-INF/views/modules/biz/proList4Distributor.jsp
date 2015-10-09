<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门票列表</title>
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
		
		function openPrepareOrder(productId){
			var url = "${ctx}/biz/orderInfo/prepareOrder?productId="+productId;
			top.$.jBox.open("iframe:"+url, "订单", 950, 500, { top:60,buttons: { '关闭': true} });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/supplier/list/valid">景区列表</a></li>
		<li class="active"><a href="${ctx}/biz/product/listProduct4Distributor/${supplier.id}">${product.scenic.name}</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/biz/product/listProduct4Distributor/${supplier.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>门票类型 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>门票类型</th><th>挂牌价</th><th>建议价</th><th>易往价</th><th>预定时间限制</th><shiro:hasPermission name="biz:product:edit"><th style="width:80px;">下单</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${product.name}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.originalPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.recommendPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.platformPrice}</fmt:formatNumber> </td>
				<td>${product.effectTimeDesc}</td>
				<shiro:hasPermission name="biz:product:edit"><td>
					<a href="${ctx}/biz/orderInfo/prepareOrder?productNo=${product.no}"  class="btn btn-mini btn-primary"><i class="icon-shopping-cart icon-white"></i> 下单</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
