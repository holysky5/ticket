<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团购产品管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/cityselect/js/jquery.cityselect.js" type="text/javascript"></script>
	
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
			
			
			var effectTimes = '${tuanProduct.effectTime}'.split(",");
			
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
			
			$("#province_city").citySelect({  
			    url:"${ctxStatic}/cityselect/js/city.min.js",  
			    prov:"${tuanProduct.province}", //省份 
			    city:"${tuanProduct.city}", //城市 
			    //dist:"岳麓区", //区县 
			    //nodata:"none", //当子集无数据时，隐藏select 
			    required:false
			}); 
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/book/tuanProduct/">团购产品列表</a></li>
		<li class="active"><a href="${ctx}/book/tuanProduct/form?id=${tuanProduct.id}">团购产品<shiro:hasPermission name="book:tuanProduct:edit">${not empty tuanProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="book:tuanProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tuanProduct" action="${ctx}/book/tuanProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 团购网站:</label>
			<div class="controls">
				<form:select path="tuanType" class="required" style="width:100px;">
					<form:option value="">--请选择--</form:option>
					<form:options items="${dicts}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 管理标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 前台名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 产品地区:</label>
			<div class="controls">
				<div id="province_city">
					<form:input path="province" htmlEscape="false" maxlength="200" class="required input-small"/><label></label>
					<form:input path="city" htmlEscape="false" maxlength="200" class="required input-small"/><label></label>
			  	</div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><span class="asterisk">*</span> 时间限制:</label>
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
			<label class="control-label">产品备注:</label>
			<div class="controls">
				<form:textarea path="notice" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="book:tuanProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
