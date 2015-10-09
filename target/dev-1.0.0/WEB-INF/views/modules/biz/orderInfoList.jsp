<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出订单数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/biz/orderInfo/export/${type}");
						$("#searchForm").submit();
						$("#searchForm").attr("action","${ctx}/biz/orderInfo/list/${type}");
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/biz/orderInfo/list/${type}");
			});
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function openView(id){
			var url = "${ctx}/biz/orderInfo/view?orderid="+id;
			top.$.jBox.open("iframe:"+url, "订单详情", 950, 500, {top:60, buttons: { } });
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/orderInfo/list/${type}">订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orderInfo" action="${ctx}/biz/orderInfo/list/${type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<c:if test="${type == 'all'}" >
		<input type="hidden" name="query" value="${type}" >
		<table class="table-condensed">
			<tr>
				<td>
				<label>取票人 ：</label><form:input path="customerName" htmlEscape="false" maxlength="50" class="input-medium" style="width:100px;"/>
				<label>取票手机 ：</label><form:input path="customerMobile" htmlEscape="false" maxlength="50" class="input-medium" style="width:100px;"/>
				<label>旅游日期 ：</label>
				<form:input path="useDate" htmlEscape="false" maxlength="50" readonly="readonly" class="Wdate"  style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/> 
					
				&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				
				</td>
			</tr>
			
		</table>
		</c:if>
		<c:if test="${type != 'all'}" >
		<table class="table-condensed">
			<tr>
				<td>
				<label>订单号 ：</label><form:input path="orderId" htmlEscape="false" maxlength="50" class="input-medium"/>
				<label>取票人 ：</label><form:input path="customerName" htmlEscape="false" maxlength="50" class="input-medium" style="width:100px;"/>
				<label>取票手机 ：</label><form:input path="customerMobile" htmlEscape="false" maxlength="50" class="input-medium" style="width:100px;"/>
				<label>订单状态 ：</label>
				<form:select path="status" style="width:100px;">
					<form:option value="">--请选择--</form:option>
						<form:options items="${fns:getDictList('biz_order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</td>
			</tr>
			<tr>
				<td>
				<c:if test="${type == 'all'}" >
				
				<label>订单类型 ：</label>
				<form:select path="type" style="width:100px;">
					<form:option value="all">--请选择--</form:option>
					<form:options items="${fns:getDictList('biz_order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</c:if>
				<label>旅游日期 ：</label>
				<form:input path="useDate" htmlEscape="false" maxlength="50" readonly="readonly" class="Wdate"  style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<form:input path="remarks" htmlEscape="false" maxlength="50" readonly="readonly" class="Wdate" style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<c:if test="${(empty orderInfo.distributor.id && type == '1')||user.userType =='1'}" >
				<label>分销商：</label><form:input path="distributor.name" htmlEscape="false" maxlength="50" class="input-medium"  style="width:100px;"/>
				</c:if>
				<c:if test="${empty orderInfo.supplier.id }" >
				<label>景区：</label><form:input path="supplier.name" htmlEscape="false" maxlength="50" class="input-medium" style="width:100px;"/>
				</c:if>
				
				&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				</td>
			</tr>
		</table>
		</c:if>
		</form:form>
	<tags:message content="${message}"/>
	<c:if test="${(not empty query && type == 'all')||type != 'all'}" >
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>#</th><th>订单号</th><c:if test="${type == 'all'}" ><th>类型</th></c:if><th>门票名称</th><th>票数</th>
		<c:if test="${(type == '1' &&  user.userType =='4') || user.userType !='4' }" >
		<th>单价</th><th>总价</th>
		</c:if>
		<th>取票人</th><th>取票人手机</th><th>旅游日期</th><th>下单时间</th>
		<c:if test="${(type == '1' &&  user.userType =='4') || user.userType !='4' }" >
		<th>分销商</th>
		</c:if>
		<th>订单状态</th><shiro:hasPermission name="biz:orderInfo:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderInfo" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${orderInfo.orderId}</td>
				<c:if test="${type == 'all'}" >
				<td>
				${fns:getDictLabel(orderInfo.type, 'biz_order_type', '')}
				</td>
				</c:if>
				<td>${orderInfo.name}</td>
				<td>${orderInfo.purchaseAmount}</td>
				<c:if test="${(type == '1' &&  user.userType =='4') || user.userType !='4' }" >
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.purchasePrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.totalpay}</fmt:formatNumber> </td>
				</c:if>
				<td>${orderInfo.customerName}</td>
				<td>${orderInfo.customerMobile}</td>
				<td>${orderInfo.useDate}</td>
				<td><fmt:formatDate value="${orderInfo.orderDate}" pattern="MM-dd HH:mm" /></td>
				<c:if test="${(type == '1' &&  user.userType =='4') || user.userType !='4' }" >
		
				<td>${orderInfo.distributor.name}</td>
				</c:if>
				<td>${fns:getDictLabel(orderInfo.status, 'biz_order_status', '')}</td>
				<td>
					<a href="javascript:openView('${orderInfo.orderId}')" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 详情</a>
    				
    				<c:if test="${user.userType == '1' || user.userType == '3'}">
    				<c:if test="${orderInfo.status == '0' || orderInfo.status == '-2'}">
    				<c:if test="${orderInfo.type == '0'}">
    				<c:if test="${user.userType == '1'  && orderInfo.status == '-2'}">
    				<a href="${ctx}/biz/orderInfo/dealOrder?orderid=${orderInfo.orderId}" onclick="return confirmx('确认该订单已经线下处理完成了吗', this.href)" class="btn btn-mini btn-success"><i class="icon-ok icon-white"></i> 处理完成</a><br>
    				</c:if>
    				<c:if test="${orderInfo.purchaseAmount > 0 && orderInfo.status == '0'}">
    				<a href="${ctx}/biz/orderInfo/updateOrder?orderid=${orderInfo.orderId}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
    				<a href="${ctx}/biz/orderInfo/cancelOrder?orderid=${orderInfo.orderId}" onclick="return confirmx('确认要取消订单吗？取消订单将收取手续费<span style=\'color:red;\'> ${orderCancelFee}元/单</span>', this.href)" class="btn btn-mini btn-warning"><i class="icon-share-alt icon-white"></i> 取消</a>
    				</c:if>
    				</c:if>
    				<c:if test="${orderInfo.type == '1'  && orderInfo.purchaseAmount > 0}">
    				<a href="${ctx}/biz/orderInfo/direct/updateOrder?orderid=${orderInfo.orderId}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
    				<a href="${ctx}/biz/orderInfo/direct/cancelOrder?orderid=${orderInfo.orderId}" onclick="return confirmx('确认要取消订单吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-share-alt icon-white"></i> 取消</a>
    				</c:if>
    				</c:if>
    				</c:if>
    				<c:if test="${ user.userType == '4'}">
    				<c:if test="${orderInfo.status == '0'}">
    				<a href="${ctx}/biz/orderInfo/checkOrderView?orderid=${orderInfo.orderId}"  class="btn btn-mini btn-warning"><i class="icon-ok-circle icon-white"></i> 验票</a>
    				</c:if>
    				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</c:if>
</body>
</html>
