<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${product.scenic.name} - ${product.name} - 下单</title>
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
					purchaseAmount: {min:"数量必须大于0" ,required:"数量必须大于0"},
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
				var value = ${directPrice.price }*this.value;
				//alert(value)
				if(isNaN(value)) {
					value = 0;
				}
				
				var orderOptFee = ${orderOptFee}*this.value;
				$("#total_opt_fee").text(orderOptFee.toFixed(2)+"元");
				
				$("#count_price").text(value.toFixed(2)+"元");
				value = value+orderOptFee;
				$("#total_price").text(value.toFixed(2)+"元");
				
				
				
			});
			
			$("#btnSubmit").click(function(){
				if(valid.form()){
					$("#pre_use_date").text($("#useDate").val());
					$("#pre_amount").text($("#purchaseAmount").val()+"张");
					$("#pre_count_price").text($("#total_price").text());
					$("#pre_total_opt_fee").text($("#total_opt_fee").text());
					$("#pre_name").text($("#customerName").val());
					$("#pre_mobile").text($("#customerMobile").val());
					<c:if test="${product.scenic.checkTerminal == 1 }">
				   	$("#pre_customerId").text($("#customerId").val());
				   	</c:if>
					top.jBox($("#orderPreview").html(), {id:'order_preview',title:"订单确认", top:10, width: 600,height: 530,buttons:{}});
				}
			});
			
			
		});
		
		jQuery.validator.addMethod("balance", function(value, element) {    
			var value = ${directPrice.price }*value;
			//alert(value)
			if(isNaN(value)) {
				value = 0;
			}
			if(${directPrice.direct.balance}<value){
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
			if(value>500 || value < 1){
				return false;
			}
		    return true;       
		 }, "订票数量不能大于500");
		
		
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
				<td><div class="uneditable-input" style="color:red;">${supplier.scenicDetailMap['0']} ${product.name}</div></td>
			</tr>
			</thead>
			<tbody>
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>旅游日期:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_use_date"></span></td>
			</tr>
		   	<tr>
				<td style="text-align:right;width:30%"><b>直通单价:</b></td>
				<td><span class="uneditable-input" style="color:red;"> <fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.price }</fmt:formatNumber>元</span></td>
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
				<td><span class="uneditable-input" style="color:red;" id="pre_name">${orderInfo.customerName }</span></td>
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
		<li><a href="${ctx}/biz/direct/">直通景区列表</a></li>
		<li><a href="${ctx}/biz/direct/product?id=${directPrice.direct.id}">${directPrice.direct.supplier.name}</a></li>
		<li class="active"><a href="${ctx}/biz/orderInfo/direct/prepareOrder?productNo=${product.no}&directPriceId=${directPrice.id}">${product.scenic.name} - ${product.name} - 下单</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/biz/orderInfo/direct/createOrder" method="post"  class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="directPriceId" value="${directPrice.id}" />
		<input type="hidden" id="proHistory.id" name="proHistory.id" value="${product.id}"/>
		<input type="hidden" id="purchasePrice" name="purchasePrice" value="${directPrice.price}"/>
		<tags:message content="${message}"/>
		
		<table class="table table-striped table-bordered table-condensed " >
	 		<tr>
				<td style="width:80px;"><b>景区名称:</b></td>
				<td><div>${supplier.scenicDetailMap['0']}</div></td>
				<td style="width:80px;"><b>门票类型:</b></td>
				<td><div>${product.name}</div></td>
			</tr>
			<tr>
				<td style="width:80px;"><b>景区地址:</b></td>
				<td><div>${supplier.scenicDetailMap['2']}</div></td>
				<td style="width:80px;"><b>开放时间:</b></td>
				<td><div>${supplier.scenicDetailMap['1']}</div></td>
			</tr>
			<tr>
				<td style="width:80px;"><b>取票地点:</b></td>
				<td><div>${supplier.scenicDetailMap['3']}</div></td>
				<td style="width:80px;"><b>入园凭证:</b></td>
				<td><div>${supplier.scenicDetailMap['4']}</div></td>
			</tr>
			<tr>
				<td style="width:80px;"><b>退改规则:</b></td>
				<td ><div style="">${supplier.scenicDetailMap['5']}</div></td>
			
				<td style="width:80px;"><b>温馨提示:</b></td>
				<td ><div style="">${supplier.scenicDetailMap['6']}</div></td>
			</tr>
			<tr>
				<td style="width:80px;"><b>购买须知:</b></td>
				<td><div class="asterisk">${product.notice}</div></td>
				<td style="width:80px;"><b>门票详情:</b></td>
				<td><div style="">${product.introduction}</div></td>
			</tr>
			
		   	<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>旅游日期:</b></td>
				<td><form:input path="useDate" autocomplete="off" htmlEscape="false" maxlength="20" readonly="readonly" class="Wdate required"  onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'${minday}',maxDate:'%y-%M-{%d+60}'});"/>
				<span class="label label-success">${product.effectTimeDesc}</span></td>
				<td style="width:80px;"><span class="asterisk">*</span><b>直通单价:</b></td>
				<td><span class="uneditable-input" style="color:red;"> <fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.price }</fmt:formatNumber>元</span><span class="label label-success">挂牌价：<fmt:formatNumber type="currency" pattern="#,##0.00#">${product.originalPrice }</fmt:formatNumber>元</span></td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>预订票数:</b></td>
				<td><form:input path="purchaseAmount" autocomplete="off" htmlEscape="false" maxlength="3"  min="1" class="required digits"/></td>
				<td style="width:80px;"><b>门票总价:</b></td>
				<td><span id="count_price"  class="uneditable-input" style="color:red;">0.00元</span><span class="label label-success">景区账户余额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${directPrice.direct.balance}</fmt:formatNumber>元</span></td>
		   	</tr>
		   		<tr>
		   		<td style="width:80px;"><b>平台手续费:</b></td>
				<td><span id="total_opt_fee" class="uneditable-input" style="color:red;"> <fmt:formatNumber type="currency" pattern="#,##0.00#">${orderOptFee }</fmt:formatNumber>元 /票 </span><span class="label label-success">系统账户余额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${distributor.balance}</fmt:formatNumber>元</span></td>
				<td style="width:80px;"><b>订单总价:</b></td>
				<td><span id="total_price"  class="uneditable-input" style="color:red;">0.00元</span><span class="label label-success">门票总价+平台手续费</span></td>
		   	</tr>
		   	<tr>
		   		<td style="width:80px;"><span class="asterisk">*</span><b>取票人姓名:</b></td>
				<td><form:input path="customerName" class="required" maxlength="20" /></td>
				<td style="width:80px;"><span class="asterisk">*</span><b>取票人手机:</b></td>
				<td><form:input path="customerMobile" class="required mobile"/></td>
				
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
			<shiro:hasPermission name="biz:orderInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="提交订单"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
