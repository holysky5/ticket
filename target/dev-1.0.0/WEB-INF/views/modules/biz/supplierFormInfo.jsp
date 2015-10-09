<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					no: {remote: "${ctx}/biz/supplier/checkNo?oldNo=" + encodeURIComponent('${supplier.no}')}
				},
				messages: {
					no: {remote: "帐号已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
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
			
			$("#checkTerminal").change(function(){
				if(this.value == '0'){
					$("#pft").show();
				}else{
					$("#pft").hide();
				}
			});
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/supplier/info">个人信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="supplier" action="${ctx}/biz/supplier/save/info" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>帐号:</label>
			<div class="controls">
				<c:if test="${empty supplier.no}">
					<form:input path="no" htmlEscape="false" minlength="3" maxlength="20" class="required"/>
				</c:if>
				<c:if test="${not empty supplier.no}">${supplier.no}</c:if>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>名称:</label>
			<div class="controls">
				${supplier.name}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>地区:</label>
			<div class="controls">
				<form:input path="area" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>姓名:</label>
			<div class="controls">
				<form:input path="contactName" htmlEscape="false" maxlength="100" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>手机:</label>
			<div class="controls">
				<form:input path="contactMobile" htmlEscape="false" maxlength="20" class="mobile required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="contactPhone" htmlEscape="false" maxlength="20" class="simplePhone"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="contactEmail" htmlEscape="false" maxlength="20" class="email"/>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
