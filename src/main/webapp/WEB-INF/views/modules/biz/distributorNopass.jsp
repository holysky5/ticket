<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销商审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					username: {remote: "${ctx}/biz/distributor/checkUsername?oldUsername=" + encodeURIComponent('${distributor.username}')}
				},
				messages: {
					username: {remote: "帐号已存在"}
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
		<li><a href="${ctx}/biz/distributor/audit">待审核分销商列表</a></li>
		<li class="active"><a href="${ctx}/biz/distributor/verify?id=${distributor.id}">分销商信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="distributor" action="${ctx}/biz/distributor/save/reaudit" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">帐号:</label>
			<div class="controls">
				<c:if test="${empty distributor.username}">
				<form:input path="username" htmlEscape="false" maxlength="40" class="required"/>
				<span class="asterisk">*</span> 
				</c:if>
				<c:if test="${not empty  distributor.username}">${ distributor.username}</c:if>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="40" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="mobile required" readonly="true"/>
				<span class="asterisk">*</span> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="20" class="simplePhone" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="20" class="email" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ:</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="20"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="20"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200"  readonly="true"/>
			</div>
		</div>
		<div id="activityBox" class="hide" >
			<img id="activityNodeImg" alt="有效证件"  src="${ctx}/file/down/${distributor.businessLicense}">
		</div>
		<div class="control-group">
			<label class="control-label">有效证件:</label>
			<div class="controls">
				<c:if test="${empty distributor.businessLicense}">
					<input type="file" id="businessLicenseFile" name="businessLicenseFile" />
				</c:if>
				<c:if test="${not empty distributor.businessLicense}">
					 <a href="#" onclick="showImage();">查看有效证件</a>
				</c:if>
			</div>
		</div>
		
				
		<div class="control-group">
			<label class="control-label">业务简介:</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审核备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		
		<input type="hidden" id="status" name="status" value="2">
		
		<div class="form-actions">
			<shiro:hasPermission name="biz:distributor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="转为重新审核"/>&nbsp;</shiro:hasPermission>
			<a class="btn btn-warning" href="${ctx}/biz/distributor/delete?id=${distributor.id}" onclick="return confirmx('确认要删除该分销商吗？', this.href)">删除</a>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
