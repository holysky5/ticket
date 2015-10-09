<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告管理</title>
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
		<li><a href="${ctx}/biz/notice/">公告列表</a></li>
		<li class="active"><a href="${ctx}/biz/notice/form?id=${notice.id}">公告<shiro:hasPermission name="biz:notice:edit">${not empty notice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:notice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="notice" action="${ctx}/biz/notice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>对象:</label>
			<div class="controls">
				<form:select path="type">
					<form:option value="">--请选择--</form:option>
					<form:option value="0">所有用户</form:option>
					<form:option value="3">分销商</form:option>
					<form:option value="4">供应商</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>状态:</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('biz_user_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>置顶:</label>
			<div class="controls">
				<form:radiobuttons path="top" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>内容:</label>
			<div class="controls">
				<form:textarea  id="content" htmlEscape="true" path="content" class="required" rows="4" style="width: 83%;" maxlength="200"/>
				<tags:ckeditor replace="content" uploadPath="/notices" configPath="config_simple.js" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:notice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
