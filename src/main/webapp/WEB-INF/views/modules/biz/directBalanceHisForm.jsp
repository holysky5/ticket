<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直通余额管理</title>
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
		<li ><a href="${ctx}/biz/direct/list/distributor">直通分销商列表</a></li>
		<li><a href="${ctx}/biz/directBalanceHis/list?direct.id=${directBalanceHis.direct.id}">账户明细</a></li>
		<shiro:hasPermission name="biz:direct:edit"><li  class="active"><a href="${ctx}/biz/directBalanceHis/form?direct.id=${directBalanceHis.direct.id}">余额管理</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="directBalanceHis" action="${ctx}/biz/directBalanceHis/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="direct.id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">
			分销商名称:
			</label>
			<div class="controls">
				<span class="uneditable-input">${directBalanceHis.direct.distributor.name}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前余额(元):</label>
			<div class="controls">
				<span class="uneditable-input"><fmt:formatNumber type="currency" pattern="#,##0.00#">${directBalanceHis.direct.balance}</fmt:formatNumber></span>
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
			<shiro:hasPermission name="biz:direct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
