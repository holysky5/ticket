<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验票-${orderInfo.orderId}</title>
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
				var value = ${product.platformPrice }*this.value;
				//alert(value)
				if(isNaN(value)) {
					value = 0;
				}
				$("#count_price").text(value.toFixed(2)+"元");
			});
			
			$("#btnSubmit").click(function(){
				if(valid.form()){
					$("#pre_amount").text($("#purchaseAmount").val()+"张");
					top.jBox($("#orderPreview").html(), {id:'order_preview',title:"订单验票确认",top:10, width: 600,height: 530,buttons:{}});
				}
			});
			
			
		});
		
		jQuery.validator.addMethod("balance", function(value, element) {    
			var value = ${product.platformPrice }*value;
			//alert(value)
			if(isNaN(value)) {
				value = 0;
			}
			if(${distributor.balance+orderInfo.totalpay}<value){
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
				<td style="text-align:right;width:30%"><b>门票类型:</b></td>
				<td><div class="uneditable-input" >${supplier.scenicDetailMap['0']} ${product.name}</div></td>
			</tr>
			</thead>
			<tbody>
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>旅游日期:</b></td>
				<td><span class="uneditable-input asterisk"  id="pre_use_date">${orderInfo.useDate }</span></td>
			</tr>
		   
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>验票数量:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_amount">${orderInfo.purchaseAmount }</span></td>
			</tr>
		   	
		   	<tr>
		   		<td style="text-align:right;width:30%"><b>取票人姓名:</b></td>
				<td><span class="uneditable-input"  id="pre_name">${orderInfo.customerName }</span></td>
			</tr>
		   	<tr>	
				<td style="text-align:right;width:30%"><b>取票人手机:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_mobile">${orderInfo.customerMobile }</span></td>
				
		   	</tr>
		   	<tr>	
				<td style="text-align:right;width:30%"><b>取票人身份证号码:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_customerId">${orderInfo.customerId }</span></td>
			</tr>
			<tr>	
				<td style="text-align:right;width:30%"><b>取票验证码:</b></td>
				<td><span class="uneditable-input" style="color:red;" id="pre_uuCode">${orderInfo.uuCode }</span></td>
			</tr>
		   	</tbody>
	  	</table>
	  	<div style="padding-left:30%;">
			<input id="btnConfirm"  class="btn btn-primary" type="button" value="确认提交"/>
			<input id="btnCancel" class="btn" type="button" value="返回修改" onclick="$.jBox.close(true)"/>
		</div>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/orderInfo/list/${orderInfo.type}">订单列表</a></li>
		<li class="active"><a href="${ctx}/biz/orderInfo/checkOrderView?orderid=${orderInfo.orderId}">订单验票</a></li>
	</ul> <br>
	<div class="alert alert-success" style="margin-left:20px;margin-right:20px;">
              <button type="button" class="close" data-dismiss="alert">×</button>
          	   提示：请根据景区验票规定仔细核对取票人旅游日期、手机/身份证号码/短信验证码等相关信息
    </div>
   
	<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/biz/orderInfo/checkOrder" method="post"  class="form-horizontal">
		<form:hidden path="orderId"/>
		<tags:message content="${message}"/>
		
		<table class="table table-striped table-bordered table-condensed " >
			<tr>
		   		<td style="width:80px;"><b>订单号:</b></td>
				<td style="width:40%;">${orderInfo.orderId }</td>
				<td style="width:80px;"><b>下单时间:</b></td>
				<td style="width:40%;"><fmt:formatDate value="${orderInfo.createDate }" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
			</tr>
	 		<tr>
				<td style="width:80px;"><b>景区名称:</b></td>
				<td><div>${supplier.scenicDetailMap['0']}</div></td>
				<td style="width:80px;"><b>门票类型:</b></td>
				<td><div>${product.name}</div></td>
			</tr>
		   	<tr>
		   		<td style="width:80px;"><b>旅游日期:</b></td>
				<td><span style="color:red;">${orderInfo.useDate }</span>
				<td style="width:80px;"><b>验票数量:</b></td>
				<td><form:input path="purchaseAmount" htmlEscape="false" maxlength="3"  min="1" class="required digits" style="width:50px;color:red;"/>
				<span class="help-inline">填写实际验票数量(只能减少，不能增加)</span>
				</td>
				
			</tr>
		   	<tr>
		   		<td style="width:80px;"><b>取票人姓名:</b></td>
				<td><span class="asterisk"> ${orderInfo.customerName }</span></td>
				
				<td style="width:80px;"><b>取票人手机:</b></td>
				
				<td><span class="asterisk"> ${orderInfo.customerMobile }</span></td>
		   	</tr>
		   	<tr>
		   		<td style="width:80px;"><b>身份证号码:</b></td>
				<td><span class="asterisk"> ${orderInfo.customerId }</span></td>
				
				<td style="width:80px;"><b>取票验证码:</b></td>
				
				<td><span class="asterisk"> ${orderInfo.uuCode }</span></td>
		   	</tr>
	  	</table>
		
		<div class="form-actions">
			<shiro:hasPermission name="biz:orderInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="验票完成"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
