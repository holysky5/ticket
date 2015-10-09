<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${notice.title}</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
	</script>
</head>
<body>
	<div>
	<table  style="width:100%" >
		<thead>
			<tr style="font-family:黑体;height:50px;">
				<th style="vertical-align:middle; text-align:center;font-size:24px;" colspan="4" >${notice.title}</th>
			</tr>
		</thead>
		<tbody>
			<tr style="text-align:center;border-bottom: 1px dotted #ddd;height:1px;"><td colspan="4"><fmt:formatDate value="${notice.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td></tr>
			<tr>
				<td colspan="4">
					<div style="min-height:100px;margin:10px;">
					${notice.content}
					</div>
				</td>
			<tr>
			
		</tbody>
	</table>
	</div>
</body>
</html>
