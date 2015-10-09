<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					username: {remote: "${ctx}/biz/distributor/checkUsername?oldUsername=" + encodeURIComponent('${distributor.username}')}
				},
				messages: {
					username: {remote: "帐号已存在"},
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
		});
		
		function showImage(){
			top.jBox($("#activityBox").html(), {title:"预览", width: 800,buttons:{"关闭":true}});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/distributor/info">个人信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="distributor" action="${ctx}/biz/distributor/save/info" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>帐号:</label>
			<div class="controls">
				<c:if test="${empty distributor.username}">
				<form:input path="username" htmlEscape="false" minlength="3" maxlength="20"  class="required"/>
				
				</c:if>
				<c:if test="${not empty  distributor.username}">${ distributor.username}</c:if>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>姓名:</label>
			<div class="controls">
				${ distributor.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="mobile required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="20" class="simplePhone"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="20" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ:</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="20" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="20" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务简介:</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
