<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门票管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.secondLabel{margin-left: 100px;text-align: right;width: 160px;}
	</style>
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
			
			var effectTimes = '${product.effectTime}'.split(",");
			
			if(effectTimes.length == 1){
				$("input[name=effectTime][value=0]").attr("checked",true); 
			}else if(effectTimes.length == 4){
				$("input[name=effectTime][value=1]").attr("checked",true); 
				$("#effectTime11").val(effectTimes[1]);
				$("#effectTime12").val(effectTimes[2]);
				$("#effectTime13").val(effectTimes[3]);
			}else if(effectTimes.length == 2){
				$("input[name=effectTime][value=2]").attr("checked",true); 
				$("#effectTime21").val(effectTimes[1]);
			}else{
				$("input[name=effectTime][value=0]").attr("checked",true); 
			}
			
		});
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/product/">门票列表</a></li>
		<li class="active"><a href="${ctx}/biz/product/form?id=${product.id}">门票<shiro:hasPermission name="biz:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:product:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="alert alert-success" style="margin-left:20px;margin-right:20px;">
              <button type="button" class="close" data-dismiss="alert">×</button>
             提示： 门票创建成功之后会自动提交到平台仓库给管理员审核，只有审核通过的门票才能上架销售！
    </div>
    <br>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/biz/product/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 门票名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required input-xlarge"/>
				<span class="help-inline">成人票、儿童票、老年票等</span>
			</div>
			
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 挂牌价(元)：</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<form:input path="originalPrice" htmlEscape="false" maxlength="50" class="required input-mini"/>
				</div>
				<div style="width: 70%; float: left;">
				<label class="secondLabel"><span class="asterisk">*</span> 指导价(元)：</label>
					<form:input path="recommendPrice" htmlEscape="false" maxlength="50" class="required input-mini"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 采购价(元)：</label>
			<div class="controls controls-row">
				<div style="width: 20%; float: left;">
					<form:input path="purchasePrice" htmlEscape="false" maxlength="50" class="required input-mini"/>
					
				</div>
				<div style="width: 50%; float: left;">
				<label class="secondLabel"><span class="asterisk">*</span> 门票状态：</label>
					<form:radiobuttons path="status" items="${fns:getDictList('biz_user_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 预订时间限制：</label>
			<div class="controls">
				<span>
				<input type="radio" id="effectTime0" name=effectTime value="0" class="required">无时间限制&nbsp;
				<input type="radio" id="effectTime1" name=effectTime value="1">入园前
				 <select id="effectTime11" name=effectTime1 style="width:65px">
				 	<option value="0">当天</option><option value="1">1天</option><option value="2">2天</option>
				 	<option value="3">3天</option><option value="7">7天</option>
				 	<option value="15">14天</option><option value="30">30天</option>
				 </select>
				 <select id="effectTime12" name=effectTime1 style="width:65px;">
				 	<option value="0">0点</option><option value="1">1点</option><option value="2">2点</option><option value="3">3点</option>
				 	<option value="4">4点</option><option value="5">5点</option><option value="6">6点</option><option value="7">7点</option>
				 	<option value="8">8点</option><option value="9">9点</option><option value="10">10点</option><option value="11">11点</option>
				 	<option value="12">12点</option><option value="13">13点</option><option value="14">14点</option><option value="15">15点</option>
				 	<option value="16">16点</option><option value="17">17点</option><option value="18">18点</option><option value="19">19点</option>
				 	<option value="20">20点</option><option value="21">21点</option><option value="22">22点</option><option value="23">23点</option>
				 </select>
				 <select id="effectTime13" name=effectTime1 style="width:65px">
				 	<option value="0">0分</option><option value="15">15分</option>
				 	<option value="30">30分</option><option value="45">45分</option>
				 </select>之前预订&nbsp;
				<input type="radio" id="effectTime1" name=effectTime value="2" >预订后 <input type="text" id="effectTime21" name=effectTime2  style="width:40px"  value="2">小时才能入园
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售卖日期限制：</label>
			<div class="controls">
				<form:input path="beginTime"  autocomplete="off" htmlEscape="false" maxlength="50"  class="input-mini"  onclick="WdatePicker({dateFmt:'MM-dd',isShowClear:false});"/>
				到
				<form:input path="endTime"  autocomplete="off" htmlEscape="false" maxlength="50" class="input-mini" onclick="WdatePicker({dateFmt:'MM-dd',isShowClear:false});"/>
				<span class="help-inline">选填，供特殊票填，淡季票：12-01到02-28，旺季票：03-01到11-31，黄金周：10-01到10-07</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门票有效日期：</label>
			<div class="controls">
				<form:input path="startTime"  autocomplete="off" htmlEscape="false" maxlength="50"  class="input-mini" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				到
				<form:input path="stopTime"  autocomplete="off" htmlEscape="false" maxlength="50" class="input-mini" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline">选填，不填表示长期有效，否则到期自动下架到仓库</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买须知：<br></label>
			<div class="controls">
				<form:textarea path="notice" htmlEscape="false"  value=""/>
				<tags:ckeditor replace="notice" uploadPath="/scenics" configPath="config_simple.js" height="100px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门票详情：</label>
			<div class="controls">
				<form:textarea path="introduction" htmlEscape="false"  />
				<tags:ckeditor replace="introduction" uploadPath="/scenics" configPath="config_simple.js" height="100px"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="biz:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
