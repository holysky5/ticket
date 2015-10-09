<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告管理</title>
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
		
		function openNotice(title,url){
			top.$.jBox.open("iframe:"+url, "公告通知", 800, 400, { buttons: { } });
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/notice/">公告列表</a></li>
		<shiro:hasPermission name="biz:notice:edit"><li><a href="${ctx}/biz/notice/form">公告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="notice" action="${ctx}/biz/notice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th width="20px;">#</th><th>标题</th><th width="100px;">状态</th><th width="150px;">时间</th><shiro:hasPermission name="biz:notice:edit"><th width="150px;">操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="notice" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td><a  href="javascript:openNotice('${notice.title}','${ctx}/biz/notice/view/${notice.idKey}')">${notice.title}</a>
					
				</td>
				<td>
					<c:if test="${notice.status==0}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${notice.status==1}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
					<c:if test="${notice.top==1}">
						<i class="icon-star-empty"></i>
					</c:if>
				</td>
				
				<td><fmt:formatDate value="${notice.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<shiro:hasPermission name="biz:notice:edit"><td>
    				<a href="${ctx}/biz/notice/form?id=${notice.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
					<a href="${ctx}/biz/notice/delete?id=${notice.id}" class="btn btn-mini btn-danger" onclick="return confirmx('确认要删除该公告吗？', this.href)"><i class="icon-trash icon-white"></i> 删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<c:if test="${empty page.list}">
			<tr><td colspan="100">没有相关信息</td></tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
