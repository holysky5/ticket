<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改订单-${orderInfo.orderId}</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
	
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			var valid = $("#inputForm").validate({
				rules: {
					purchaseAmount: {maxvalue:true,balance: true}
				},
				messages: {
					purchaseAmount: {min:"数量必须大于0",required:"数量必须大于0"},
				},
				submitHandler: function(form){
					top.$.jBox.close(true);
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#purchaseAmount").blur(function(){
				var value = ${orderInfo.purchasePrice }*this.value;
				//alert(value)
				if(isNaN(value)) {
					value = 0;
				}
				var orderOptFee = ${orderOptFee}*this.value;
				$("#total_opt_fee").text(orderOptFee.toFixed(2)+"元");
				value = value+orderOptFee;
				$("#count_price").text(value.toFixed(2)+"元");
			});
			
			$("#btnSubmit").click(function(){
				if(valid.form()){
					$("#pre_use_date").text($("#useDate").val());
					$("#pre_amount").text($("#purchaseAmount").val()+"张");
					$("#pre_count_price").text($("#count_price").text());
					$("#pre_total_opt_fee").text($("#total_opt_fee").text());
					$("#pre_name").text($("#customerName").val());
					$("#pre_mobile").text($("#customerMobile").val());
					<c:if test="${product.scenic.checkTerminal == 1 }">
				   	$("#pre_customerId").text($("#customerId").val());
				   	</c:if>
					top.jBox($("#orderPreview").html(), {id:'order_preview',title:"订单修改确认", top:10, width: 600,height: 530,buttons:{}});
				}
			});
			
			
		});
		
		jQuery.validator.addMethod("balance", function(value, element) {    
			var value = ${orderInfo.purchasePrice }*value;
			//alert(value)
			if(isNaN(value)) {
				value = 0;
			}
			if(${orderInfo.direct.balance}<value){
				return false;
			}
			if(${distributor.balance}<${orderOptFee}){
				return false;
			}
		    return true;       
		 }, "账户余额不足");
		
		jQuery.validator.addMethod("maxvalue", function(value, element) {  
			if(isNaN(value)) {
				value = 0;
			}
			if(value>${orderInfo.purchaseAmount } || value < 1){
				return false;
			}
		    return true;       
		 }, "数量只能减少");
		
		
		
	</script>
</head>
<body>
	<div id="orderPreview" class="hide"  >
		<script type="text/javascript">
		$("#btnConfirm").click(function(){
			//mainFrame.$("#count_price").text('aaa');
			mainFrame.$('#inputForm').submit();
		});
		</script>
		<table class="table table-striped table-condensed " style="width:600px;margin-top:20px;" >
	 		<thead>
	 		<tr>
				<td style="text-align:right;width:30%"><b>您预订的:</b></td>
				<td><div class="uneditable-input" >${supplier.scenicDetailMap['0']} ${product.name}</div></td>
			</tr>
			</thead>
			<tbody>
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>旅游日期:</b></td>
				<td><span class="uneditable-input"  id="pre_use_date">${orderInfo.useDate }</span></td>
			</tr>
		   	<tr>
				<td style="text-align:right;width:30%"><b>结算单价:</b></td>
				<td><span class="uneditable-input" > <fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.purchasePrice }</fmt:formatNumber>元</span></td>
			</tr>
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>预订票数:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_amount">${orderInfo.purchaseAmount }</span></td>
			</tr>
		   <tr>
				<td style="text-align:right;width:30%"><b>订单总价:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_count_price">0.00元</span><span class="label label-success">其中平台手续费 <span id="pre_total_opt_fee"><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderOptFee}</fmt:formatNumber></span></span></td>
		   	</tr>
		   		<tr>
		   		<td style="text-align:right;width:30%"><b>取票人姓名:</b></td>
				<td><span class="uneditable-input"  id="pre_name">${orderInfo.customerName }</span></td>
			</tr>
		   	<tr>	
				<td style="text-align:right;width:30%"><b>取票人手机:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_mobile">${orderInfo.customerMobile }</span></td>
				
		   	</tr>
		   	<c:if test="${product.scenic.checkTerminal == 1 }">
		   	<tr>	
				<td style="text-align:right;width:30%"><b>取票人身份证号码:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_customerId">${orderInfo.customerId }</span></td>
			</tr>
		   	</c:if>
		   	</tbody>
	  	</table>
	  	<div style="padding-left:30%;">
			<input id="btnConfirm"  class="btn btn-primary" type="button" value="确认提交"/>
			<input id="btnCancel" class="btn" type="button" value="返回修改" onclick="$.jBox.close(true)"/>
		</div>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/orderInfo/list/${orderInfo.type}">订单列表</a></li>
		<li class="active"><a href="${ctx}/biz/orderInfo/direct/updateOrder?orderid=${orderInfo.orderId}">修改订单</a></li>
	</ul> <br>
	<div class="alert alert-success" style="margin-left:20px;margin-right:20px;">
              <button type="button" class="close" data-dismiss="alert">×</button>
                提示：只能减少订单订票数量、修改取票人
          	   <c:if test="${product.scenic.checkTerminal == 0 }">手机
				</c:if>
				<c:if test="${product.scenic.checkTerminal == 1 }">身份证号码
				</c:if>
			，其它操作请重新下单
    </div>
   
	<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/biz/orderInfo/direct/updateOrder" method="post"  class="form-horizontal">
		<form:hidden path="orderId"/>
		<tags:message content="${message}"/>
		
		<table class="table table-striped table-bordered table-condensed " >
			<tr>
		   		<td style="width:80px;"><b>订单号:</b></td>
				<td>${orderInfo.orderId }</td>
				<td style="width:80px;"><b>下单时间:</b></td>
				<td><fmt:formatDate value="${orderInfo.createDate }" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
			</tr>
	 		<tr>
				<td style="width:80px;"><b>景区名称:</b></td>
				<td><div>${supplier.scenicDetailMap['0']}</div></td>
				<td style="width:80px;"><b>门票类型:</b></td>
				<td><div>${product.name}</div></td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>旅游日期:</b></td>
				<td><span style="color:red;">${orderInfo.useDate }</span>
				<td style="width:80px;"><span class="asterisk">*</span><b>结算单价:</b></td>
				<td><span style="color:red;"> <fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.purchasePrice }</fmt:formatNumber>元</span> </td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>预订票数:</b></td>
				<td><form:input path="purchaseAmount" htmlEscape="false" maxlength="3"  min="1" class="required digits"/></td>
				<td style="width:80px;"><b>订单总价:</b></td>
				<td><span id="count_price"  class="uneditable-input" style="color:red;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderInfo.totalpay }</fmt:formatNumber>元</span><span class="label label-success">其中平台手续费 <span id="total_opt_fee" ><fmt:formatNumber type="currency" pattern="#,##0.00#">${orderOptFee * orderInfo.purchaseAmount}</fmt:formatNumber>元</span></span></td>
		   	</tr>
		   		<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>取票人姓名:</b></td>
				<td><span> ${orderInfo.customerName }</span></td>
				<td style="width:80px;"><span class="asterisk">*</span><b>取票人手机:</b></td>
				<td>
				<c:if test="${product.scenic.checkTerminal == 0 }">
				<form:input path="customerMobile" class="required mobile"/>
				</c:if>
				<c:if test="${product.scenic.checkTerminal == 1 }">
				<span> ${orderInfo.customerMobile }</span>
				<form:hidden path="customerMobile" class="required mobile"/>
				</c:if>
				</td>
		   	</tr>
		   	<c:if test="${product.scenic.checkTerminal == 1 }">
		   	 <tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>身份证号码:</b></td>
				<td colspan="3"><form:input path="customerId" class="required card" maxlength="18" />
				<span class="help-inline">该景区使用身份证刷卡检票,请务必正确填写取票人身份证号码</span>
				</td>
		   	</tr>
		   	</c:if>
	  	</table>
		
		<div class="form-actions">
			<shiro:hasPermission name="biz:orderInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="提交修改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
