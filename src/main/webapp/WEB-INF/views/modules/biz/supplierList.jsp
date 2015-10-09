<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function openView(name,id){
			var url = "${ctx}/biz/supplier/view?id="+id;
			top.$.jBox.open("iframe:"+url, name+"景区详情", 950, 500, {top:60, buttons: { } });
		}
		
		function openDetail(name,id){
			var url = "${ctx}/biz/scenicDetail/update/"+id;
			top.$.jBox.open("iframe:"+url, name+"景区详情修改", 950, 500, {top:60, buttons: { } });
		}
		
		function openProduct(name,id){
			var url = "${ctx}/biz/product/list?supplierid="+id;
			top.$.jBox.open("iframe:"+url, name+"景区门票", 950, 500, {top:60, buttons: { } });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/supplier/">供应商列表</a></li>
		<shiro:hasPermission name="biz:supplier:edit"><li><a href="${ctx}/biz/supplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/biz/supplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>帐号</th><th>名称</th><th>姓名</th><th>手机</th><th>电话</th><th>余额</th><th>状态</th><th>验票终端</th><shiro:hasPermission name="biz:supplier:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplier" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td><a href="javascript:openView('${supplier.name}','${supplier.id}')">${supplier.no}</a></td>
				<td>${supplier.name}</td>
				<td>${supplier.contactName}</td>
				<td>${supplier.contactMobile}</td>
				<td>${supplier.contactPhone}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${supplier.balance}</fmt:formatNumber></td>
				<td>
					<c:if test="${supplier.status==0}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${supplier.status==1}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
				</td>
				<td>${fns:getDictLabel(supplier.checkTerminal, 'biz_check_terminal', '无')}</td>
				
				<shiro:hasPermission name="biz:supplier:edit"><td>
    				<a href="${ctx}/biz/supplier/form?id=${supplier.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
					<a href="${ctx}/biz/balanceHis/list/supplier/${supplier.id}" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 余额</a>
					<a href="javascript:openDetail('${supplier.name}','${supplier.id}')" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 详情</a>
					<c:if test="${supplier.balance == 0.0}">
					<a href="${ctx}/biz/supplier/delete?id=${supplier.id}" onclick="return confirmx('确认要删除该供应商信息吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-trash icon-white"></i> 删除</a>
					</c:if>
					<c:if test="${supplier.balance != 0.0}">
					<a href="#"  class="btn btn-mini btn-warning disabled"  title="与该供应商结算完成才能删除"><i class="icon-trash icon-white"></i> 删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
