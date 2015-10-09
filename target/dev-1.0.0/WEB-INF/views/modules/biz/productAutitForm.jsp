<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门票管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.secondLabel{margin-left: 100px;text-align: right;width: 160px;}
		.radio_disabled{disabled:disabled;}
		input{disabled:disabled;}
	</style>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!--  <li><a href="${ctx}/biz/product/listWait4AuditProduct">待审核门票列表</a></li>-->
		<li class="active"><a href="${ctx}/biz/product/auditForm?no=${product.no}">门票<shiro:hasPermission name="biz:product:edit">${not empty product.id?'上下架/修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:product:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="alert alert-info" style="margin-left:20px;margin-right:20px;">
		<button type="button" class="close" data-dismiss="alert">×</button>
 		 注意：管理员上架审核过程请注意修改易往价格.
	</div>
	<br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/biz/product/audit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label">门票名称:</label>
			<div class="controls">
				<div style="width: 20%; float: left;">
					<span class="input-xlarge uneditable-input">${product.name }</span>
				</div>
				<div style="width: 50%; float: left;">
					<label class="secondLabel"><span class="asterisk">*</span> 上下架:</label>
					<input type="radio" name="auditFlag" id="auditFlag1" value="1" ${product.auditFlag == '1' ? 'checked':'' }>上架
					<input type="radio" name="auditFlag" id="auditFlag2" value="0" ${product.auditFlag == '0' ? 'checked':'' }>下架
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门票编号:</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<span class="uneditable-input" style="width:100px;">${product.no }</span>
				</div>
				<div style="width: 50%; float: left;">
					
					<label class="secondLabel"><span class="asterisk">*</span> 门票类型编号:</label>
					<c:if test="${product.scenic.checkTerminal != 9 }">
					<form:input path="uuPid"  htmlEscape="false" maxlength="20" class="required input-samll"  style="width:100px;"/>
					</c:if>
					<c:if test="${product.scenic.checkTerminal == 9 }">
					<span class="help-inline">不需要</span>
					</c:if>
					<c:if test="${product.scenic.checkTerminal == 0 }">
					<span><a href="${ctxStatic}/images/pft_supplier.png" target="_blank">票付通四位编码</a></span>
					</c:if>
					<c:if test="${product.scenic.checkTerminal == 1 }">
					<span><a href="${ctxStatic}/images/beizhu_gid.png" target="_blank">贝竹四位编码</a></span>
					</c:if>
					
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">挂牌价:</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<span class="uneditable-input" style="width:100px;">${product.originalPrice }元</span>
				</div>
				<div style="width: 50%; float: left;">
					<label class="secondLabel">指导价:</label>
					<span class="uneditable-input" style="width:100px;">${product.recommendPrice }元</span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购价:</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<span class="uneditable-input" style="width:100px;">${product.purchasePrice }元</span>
				</div>
				<div style="width: 50%; float: left;">
					<label class="secondLabel"><span class="asterisk">*</span> 易往价:</label>
						<form:input path="platformPrice"  htmlEscape="false" maxlength="200" class="required input-samll"  style="width:100px;"/>
				 		<span>元</span>
						
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预定时间限制:</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<span class="input-xlarge uneditable-input">${product.effectTimeDesc }，${product.beignAndEndTimeDesc }</span>
				</div>
				<div style="width: 50%; float: left;">
					<label class="secondLabel">门票有效时间:</label>
					<span class="uneditable-input" style="width:150px;">${product.startAndStopTimeDesc }</span>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">购买须知:</label>
			<div class="controls">
				<span class="uneditable-input" style="width:60%;min-height:100px;">${product.notice }</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门票详情:</label>
			<div class="controls">
				<span class="uneditable-input" style="width:60%;min-height:100px;">${product.introduction }</span>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="biz:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
