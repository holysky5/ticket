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
		<li><a href="${ctx}/biz/distributor/">分销商列表</a></li>
		<li class="active"><a href="${ctx}/biz/distributor/form?id=${distributor.id}">分销商<shiro:hasPermission name="biz:distributor:edit">${not empty distributor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:distributor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="distributor" action="${ctx}/biz/distributor/save" method="post" class="form-horizontal" enctype="multipart/form-data">
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
			<label class="control-label"><span class="asterisk">*</span>密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty distributor.id?'required':''}"/>
				<c:if test="${not empty distributor.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="40" class="required"/>
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
		<div id="activityBox" class="hide" >
			<img id="activityNodeImg" alt="有效证件"  src="${ctx}/file/down/${distributor.businessLicense}">
		</div>
		<div class="control-group">
			<label class="control-label">有效证件:</label>
			<div class="controls">
				<c:if test="${empty distributor.businessLicense}">
					<input type="file" id="businessLicenseFile" name="businessLicenseFile"/>
				</c:if>
				<c:if test="${not empty distributor.businessLicense}">
					 <a href="#" onclick="showImage();">查看</a>
				</c:if>
			</div>
		</div>
		
				
		<div class="control-group">
			<label class="control-label">业务简介:</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 状态:</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('biz_user_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="3" class="digits required"/>
				<span>数字越大越靠前</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:distributor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
