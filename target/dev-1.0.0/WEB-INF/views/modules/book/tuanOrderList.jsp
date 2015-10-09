<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团购订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出订单数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/book/tuanOrder/export/${type}");
						$("#searchForm").submit();
						$("#searchForm").attr("action","${ctx}/book/tuanOrder/list/${type}");
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/book/tuanOrder/list/${type}");
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/book/tuanOrder/list/${type}">团购订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tuanOrder" action="${ctx}/book/tuanOrder/list/${type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>管理标题：</label><form:input path="product.title" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>团购网 ：</label>
		<form:select path="tuanType" style="width:100px;">
			<form:option value="">--请选择--</form:option>
				<form:options items="${fns:getDictList('tuan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<label>旅游日期 ：</label>
		<form:input path="useDate" htmlEscape="false" maxlength="50" readonly="readonly" class="Wdate"  style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
		<form:input path="remarks" htmlEscape="false" maxlength="50" readonly="readonly" class="Wdate" style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<div style="margin-top:8px;">
		<label>游客姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>手机号码：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
		
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>#</th><th>团购网</th><th>产品管理标题</th><th>使用日期</th><th>游客姓名</th><th>手机号码</th><th>身份证号码</th><th>备注</th><th>券码</th><th>预订时间</th><c:if test="${tuanOrder.status == '0'}"><shiro:hasPermission name="book:tuanOrder:edit"><th>操作</th></shiro:hasPermission></c:if></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="tuanOrder" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${fns:getDictLabel(tuanOrder.tuanType, 'tuan_type', '')}</td>
				<td>${tuanOrder.product.title}</td>
				<td>${tuanOrder.useDate}</td>
				<td>${tuanOrder.name}</td>
				<td>${tuanOrder.mobile}</td>
				<td>${tuanOrder.nameSfz}</td>
				<td>${tuanOrder.notice}</td>
				<td>${tuanOrder.ticket}</td>
				<td title="<fmt:formatDate value="${tuanOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate value="${tuanOrder.createDate}" pattern="yyyy-MM-dd" /></td>
				<c:if test="${tuanOrder.status == '0'}">
				<shiro:hasPermission name="book:tuanOrder:edit"><td>
    				<a href="${ctx}/book/tuanOrder/form?id=${tuanOrder.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 处理</a>
					<!--
					<a href="${ctx}/book/tuanOrder/delete?id=${tuanProduct.id}" onclick="return confirmx('确认要删除该团购订单吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-trash icon-white"></i> 删除</a>
					  -->
				</td></shiro:hasPermission>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
