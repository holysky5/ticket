<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预订结果</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
	
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() {
			top.jBox.close(true);
		});
		
		function openView(id){
			var url = "${ctx}/biz/orderInfo/view?orderid="+id;
			top.$.jBox.open("iframe:"+url, "订单详情", 950, 500, {top:60, buttons: { } });
		}
		
		
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/orderInfo/list/${orderInfo.type}">订单列表</a></li>
		<li class="active"><a href="#">订单详情</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/biz/orderInfo/createOrder" method="post"  class="form-horizontal">
	<tags:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed " >
	 		<tr>
				<td style="width:80px;"><b>景区名称:</b></td>
				<td><div>${supplier.scenicDetailMap['0']}</div></td>
				<td style="width:80px;"><b>门票类型:</b></td>
				<td><div>${product.name}</div></td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><b>旅游日期:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.useDate}</span></td>
				<td style="width:80px;"><b>结算单价:</b></td>
				<td><span class="uneditable-input" style="color:red;"> <fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.purchasePrice }</fmt:formatNumber>元</span></td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><b>预订票数:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.purchaseAmount}张</span></td>
				<td style="width:80px;"><b>订单总价:</b></td>
				<td><span id="count_price"  class="uneditable-input" style="color:red;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.totalpay}</fmt:formatNumber>元</span></td>
		   	</tr>
		   	<tr>
		   		<td style="width:80px;"><b>取票人姓名:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.customerName}</span></td>
				<td style="width:80px;"><b>取票人手机:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.customerMobile}</span></td>
			</tr>
			<tr>
		   		<td style="width:80px;"><b>订单状态:</b></td>
				<td><span class="uneditable-input" style="color:red;">${fns:getDictLabel(orderInfo.status, 'biz_order_status', '')}</span></td>
				<td style="width:80px;"><b>分销商:</b></td>
				<td><span class="uneditable-input">${orderInfo.distributor.name}</span></td>
				
			</tr>
			<c:if test="${orderInfo.status == '0'}">
			<tr>
		   		<c:if test="${product.scenic.checkTerminal == 0 }">
		   		<td style="width:80px;"><b>取票凭证号:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.uuCode}</span>&nbsp;
				<c:if test="${orderInfo.uuRremsg < 3}">
				<input id="btnSms" class="btn btn-mini btn-primary" type="button" value="信息重发" />
				</c:if>
				</td>
				</c:if>
				<c:if test="${product.scenic.checkTerminal == 1 }">
				<td style="width:80px;"><b>取票身份证:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.customerId}</span>&nbsp;
				</c:if>
				<td style="width:80px;"><b>远端订单号:</b></td>
				<td><span class="uneditable-input" style="color:red;">${orderInfo.uuOrdernum}</span></td>
			</tr>
			</c:if>
	  	</table>
	  	
	  	<table class="table table-striped table-bordered table-condensed " >
	  		<thead>
	  			<tr><th colspan="3">订单操作记录</th></tr>
	  			<tr><th>#</th><th>操作内容</th><th>操作时间</th></tr>
	  		</thead>
	  		<tbody>
	  		<c:forEach items="${orderInfo.orderLogList}" var="log" varStatus="status">
	  			<tr>
	  				<td>${status.index+1}</td>
	  				<td>${log.name}</td>
	  				<td><fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	  			</tr>
	  		</c:forEach>
	  		<c:if test="${empty orderInfo.orderLogList}">
	  			<tr><td colspan="3">没有操作记录</td></tr>
	  		</c:if>
	  		</tbody>
	  	</table>
	  </form:form>
</body>
</html>
