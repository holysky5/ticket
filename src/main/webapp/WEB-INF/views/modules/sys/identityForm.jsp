<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流水号管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/identity/">流水号列表</a></li>
		<li class="active"><a href="${ctx}/sys/identity/form?id=${identity.id}">流水号<shiro:hasPermission name="sys:identity:edit">${not empty identity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:identity:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="identity" action="${ctx}/sys/identity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="11" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">别名:</label>
			<div class="controls">
				<form:input path="alias" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则:</label>
			<div class="controls">
				<form:input path="rule" htmlEscape="false" maxlength="50"  placeholder="" class="required"/>
				<span>{yyyy}{MM}{dd}{NO}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成:</label>
			<div class="controls">
				<form:select path="genEveryDay" >
					<form:options items="${fns:getDictList('sys_identity_create')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">长度:</label>
			<div class="controls">
				<form:input path="noLength" htmlEscape="false" maxlength="11" class="required"/>
				<span>{NO},如果长度为5，当前流水号为5，则在流水号前补4个0，表示为00005</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">初始值:</label>
			<div class="controls">
				<form:input path="initValue" htmlEscape="false" maxlength="11" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">步长:</label>
			<div class="controls">
				<form:input path="step" htmlEscape="false" maxlength="11" class="required"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:identity:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
