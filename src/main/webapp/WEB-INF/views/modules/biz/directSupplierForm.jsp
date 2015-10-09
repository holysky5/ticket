<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销商权限管理</title>
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
			
			$("#selectDistributor").click(function(){
				top.$.jBox.open("iframe:${ctx}/biz/distributor/selectList", "分销商查询",600,400,{
					buttons:{"确定":true}
				});
			});
		});
		
		function articleSelectAddOrDel(id,title){
			$("#distributorId").val(id);
			$("#distributorName").val(title);
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/biz/direct/list/distributor">直通分销商列表</a></li>
		<li class="active"><a href="${ctx}/biz/direct/supplier/form?id=${direct.id}">分销商权限<shiro:hasPermission name="biz:direct:edit">${not empty direct.id?'修改':'开通'}</shiro:hasPermission><shiro:lacksPermission name="biz:direct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<c:if test="${supplier.checkTerminal == 9}">
		<div class="alert alert-success" style="margin-left:20px;margin-right:20px;">
              	 提示： 景区订单为人工处理，不支持直通订票！如有问题，请联系我们
   		</div>
	</c:if>
	<c:if test="${supplier.checkTerminal != 9}">
	<form:form id="inputForm" modelAttribute="direct" action="${ctx}/biz/direct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 分销商：</label>
			<div class="controls controls-row">
				<div style="width: 50%; float: left;">
					<c:if test="${empty direct.id}">
					<div class="input-append">
						<input type="hidden" id="supplierId" name="supplierId" value="${supplier.id}">
						<input type="hidden" id="distributorId" name="distributorId" >
				 		<input  id="distributorName" name="distributorName" type="text" htmlEscape="false" maxlength="200" class="required" readOnly/>
				 		 <button class="btn btn-primary" type="button"  id="selectDistributor">选择</button>
					</div>
					</c:if>
					<c:if test="${not empty direct.id}">
					<input type="hidden" id="distributorId" name="distributorId" value="${direct.distributor.id}">
					<span class="uneditable-input">${direct.distributor.name}</span>
					</c:if>
				</div>
				<div style="width: 50%; float: left;">
				<label class="secondLabel"><span class="asterisk">*</span> 开通状态：</label>
					<form:radiobuttons path="status" items="${fns:getDictList('biz_user_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 门票类型：</label>
			<div class="controls">
				<table class="table table-striped table-bordered table-condensed" style="width:80%;">
					<tr>
						<th style="width:30px;">#</th><th style="width:100px;">名称</th><th style="width:80px;">挂牌价</th><th style="width:80px;">指导价</th><th>直通价<span class="asterisk">（开通请填写直通价，否则不填设为空）</span></th>
					</tr>
					<c:forEach items="${prices}" var="price" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${price.product.name}</td>
						<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${price.product.originalPrice}</fmt:formatNumber> </td>
						<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${price.product.recommendPrice}</fmt:formatNumber> </td>
			
						<td>
						<input type="hidden" name="priceId" value="${price.id}" />
						<input type="hidden" name="productId" value="${price.product.id}"/>
						<input name="distributorPrice"  type="text"  maxlength="20"  style="width:50px;" value="${price.price}"/>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:direct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</c:if>
</body>
</html>
