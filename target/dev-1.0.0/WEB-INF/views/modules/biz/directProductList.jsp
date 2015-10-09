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
		<li><a href="${ctx}/biz/direct/">直通景区列表</a></li>
		<li class="active"><a href="${ctx}/biz/direct/product?id=${direct.id}">${direct.supplier.name}</a></li>
	</ul>
	
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>门票类型</th><th>挂牌价</th><th>建议价</th><th>直通价</th><th>预定时间限制</th><shiro:hasPermission name="biz:product:edit"><th style="width:80px;">下单</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${directPriceList}" var="directPrice" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${directPrice.product.name}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.product.originalPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.product.recommendPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.price}</fmt:formatNumber> </td>
				<td>${directPrice.product.effectTimeDesc}</td>
				<shiro:hasPermission name="biz:product:edit"><td>
					<a href="${ctx}/biz/orderInfo/direct/prepareOrder?productNo=${directPrice.product.no}&directPriceId=${directPrice.id}"  class="btn btn-mini btn-primary"><i class="icon-shopping-cart icon-white"></i> 下单</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
