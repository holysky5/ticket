<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>景区详情管理</title>
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
		<li><a href="#">景区详情</a></li>
		<li class="active"><a href="${ctx}/biz/scenicDetail/form?id=${scenicDetail.id}">详情<shiro:hasPermission name="biz:scenicDetail:edit">${not empty scenicDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:scenicDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scenicDetail" action="${ctx}/biz/scenicDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<span class="input-xlarge uneditable-input">${fns:getDictLabel(scenicDetail.type, 'scenic_info_type', '')}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">
				<c:if test="${scenicDetail.type == '0' || scenicDetail.type == '1' || scenicDetail.type == '2' || scenicDetail.type == '3' ||scenicDetail.type == '4' }">
				<form:textarea path="content" htmlEscape="false"  maxlength="200" class="required" style="width:270px;height:100px;"/>
				</c:if>
				<c:if test="${scenicDetail.type == '5' || scenicDetail.type == '6'}">
				<form:textarea path="content" htmlEscape="true" class="required" />
				<tags:ckeditor replace="content" uploadPath="/scenics" configPath="config_simple.js" height="200px;" />
				</c:if>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:scenicDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
