<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户充值</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					amount: {amountset: true}
				},
				messages: {
					amount: {required:"金额必填"},
				},
				submitHandler: function(form){
					
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
			jQuery.validator.addMethod("amountset", function(value, element) {    
				if(value<-50000 || value >50000){
					return false;
				}
			    return true;       
			 }, "充值限额为正负50000内的数值");   
			
			
			 
		});
		
		
	

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${not empty distributor }">
		<li><a href="${ctx}/biz/${userType}/">分销商列表</a></li>
		</c:if>
		<c:if test="${not empty supplier }">
		<li><a href="${ctx}/biz/${userType}/">供应商列表</a></li>
		</c:if>
		<li><a href="${ctx}/biz/balanceHis/list/${userType}/${distributor.id}${supplier.id}">账户明细</a></li>
		<li class="active"><a href="${ctx}/biz/balanceHis/manage/${userType}/${distributor.id}${supplier.id}">余额管理</a></li>
	</ul><br>
	
	<form:form id="inputForm" modelAttribute="balanceHis" action="${ctx}/biz/balanceHis/save/${userType}/${distributor.id}${supplier.id}" method="post"  class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">
			<c:if test="${not empty distributor }">
			分销商名称:
			</c:if>
			<c:if test="${not empty supplier }">
			供应商名称:
			</c:if>
			</label>
			<div class="controls">
				<span class="uneditable-input">${distributor.name}${supplier.name}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前余额(元):</label>
			<div class="controls">
				<span class="uneditable-input"><fmt:formatNumber type="currency" pattern="#,##0.00#">${distributor.balance}${supplier.balance}</fmt:formatNumber></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>操作类型:</label>
			<div class="controls">
				<form:select path="type"  class="required ">
				<form:option value="">--请选择--</form:option>
				<form:option value="0">充值</form:option>
				<form:option value="3">结算</form:option>
				<form:option value="9">其它</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>操作金额(元):</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="200" class="required number"/>
				<span>金额大于0表示增加余额，小于0表示减少余额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>操作原因:</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作备注:</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" maxlength="200" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:balanceHis:manage"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
