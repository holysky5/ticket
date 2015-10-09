<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团购订单管理</title>
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
		<li><a href="${ctx}/book/tuanOrder/list/0">团购订单列表</a></li>
		<li class="active"><a href="${ctx}/book/tuanOrder/form?id=${tuanOrder.id}">团购订单<shiro:hasPermission name="book:tuanOrder:edit">${not empty tuanOrder.id?'处理':'添加'}</shiro:hasPermission><shiro:lacksPermission name="book:tuanOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tuanOrder" action="${ctx}/book/tuanOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	
		<div class="control-group">
			<label class="control-label">团购网站:</label>
			<div class="controls">
				${fns:getDictLabel(tuanOrder.tuanType, 'tuan_type', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团购产品:</label>
			<div class="controls">
				${tuanOrder.product.title}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用日期:</label>
			<div class="controls">
				${tuanOrder.useDate}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预订人信息:</label>
			<div class="controls">
				${tuanOrder.name} - ${tuanOrder.mobile} - ${tuanOrder.nameSfz}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预订备注:</label>
			<div class="controls">
				${tuanOrder.notice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团购券码:</label>
			<div class="controls">
				${tuanOrder.ticket}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交时间:</label>
			<div class="controls">
				<fmt:formatDate value="${tuanOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="book:tuanOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="处理完成"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
