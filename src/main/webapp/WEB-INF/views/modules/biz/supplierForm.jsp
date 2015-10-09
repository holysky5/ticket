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
		<li><a href="${ctx}/biz/supplier/">供应商列表</a></li>
		<li class="active"><a href="${ctx}/biz/supplier/form?id=${supplier.id}">供应商<shiro:hasPermission name="biz:supplier:edit">${not empty supplier.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:supplier:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="supplier" action="${ctx}/biz/supplier/save" method="post" class="form-horizontal">
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
			<label class="control-label"><span class="asterisk">*</span>密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty supplier.id?'required':''}"/>
				<c:if test="${not empty supplier.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="required"/>
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
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>状态:</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('biz_user_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="3" class="digits required"/>
				<span>数字越大越靠前</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>验票终端:</label>
			<div class="controls">
				<form:select path="checkTerminal" class="required">
					<form:option value="">--请选择--</form:option>
					<form:options items="${fns:getDictList('biz_check_terminal')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div id="pft" style="${supplier.checkTerminal == '0' ? '' :'display:none;'}">
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>景区编号:</label>
			<div class="controls">
				<form:input path="uuId" htmlEscape="false" maxlength="4" class="required"/>
				<span><a href="${ctxStatic}/images/pft_supplier.png" target="_blank">票付通四位编码</a></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>商家编号:</label>
			<div class="controls">
				<form:input path="uuSalerId" htmlEscape="false" maxlength="6" class="required"/>
				<span><a href="${ctxStatic}/images/pft_supplier.png" target="_blank">票付通六位编码</a></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span>终端编号:</label>
			<div class="controls">
				<form:input path="uuTerminalId" htmlEscape="false" maxlength="4" class="required"/>
				<span><a href="${ctxStatic}/images/pft_supplier.png" target="_blank">票付通四位编码</a>,景区小票终端需同时设置</span>
			</div>
		</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:supplier:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
