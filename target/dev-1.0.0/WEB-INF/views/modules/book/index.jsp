<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html>
<head>
<title>易往票务团购自助预约服务平台</title>
<link href="${ctxStatic}/common/book_index.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/cityselect/js/jquery.cityselect.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			
			
			$("#cityGroup").hide();
			
			$("#inputForm").validate({
				submitHandler: function(form) {
					var pro = "";
					$('input[type=checkbox][name=selectProduct]').each(function(){
						 if($(this).prop("checked") == true){
							 pro = $(this).val(); 
						 }
					});
					if(pro == "" ){
						alert("请选择团购产品");
						return false;
					}
					form.submit(); 
				},
				rules: {
					username: {remote: "${ctx}/biz/distributor/register/checkUsername?oldUsername=" + encodeURIComponent('${distributor.username}')},
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名.",remote: "此帐号已被注册"},
					password: {required: "请填写密码."},
					confirmPassword: {equalTo: "输入与上面相同的密码"},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				}
			
			});
			
			
			
			
			$("#tuanType").change(function(){
				
				$("#showMessage").hide();
				//$("#showWarning").hide();
				$("#placeHolder").show();
				//$("#contentTable").show();
				groupProvince();
				$("#cityHide").val("");
				$("#cityProvince").val("");
				$("#cityGroup").hide();
				
				$("#provinceHide").val("");
				queryProduct();
				
				
			});
			
			
			
			
		});
		
		function groupProvince(){
			$.ajax({
	             type: "post",
	             url: "${ctx}/tuan/province/group",
	             dataType: "json",
	             data:'tuanType='+$("#tuanType").val(),
	             success: function(data){
	            	 $("#provinceSpan").empty();
	            	// $("#provinceSpan").append("<a href='#' style='font-weight: bold; margin-right: 10px;' value='' onclick='javascript:filterCityAll();'>全部</a>");

	            	 for(var i=0;i<data.length;i++){
	            		 $("#provinceSpan").append("<a href='#' style='font-weight: bold; margin-right: 10px;' value='"+data[i]+"' class='province_css' id='province_"+data[i]+"' onclick='javascript:filterCity(\""+data[i]+"\");' >"+data[i]+"</a>");
		             }
			     }
	         });
		}
		
		function filterCityAll(){
			$("#cityGroup").hide();
			$("#cityHide").val("");
		}
		
		function filterCity(a){
			$(".province_css").css({"background-color":"#fff","color":"#000"}); 
			$("#province_"+a).css({"background-color":"#53b9fb","color":"#fff"}); 
			
			$("#cityGroup").show();
			$("#provinceHide").val(a);
			$("#cityHide").val("");
			queryProduct();
			$.ajax({
	             type: "post",
	             url: "${ctx}/tuan/city/filter/"+a,
	             dataType: "json",
	             data:'tuanType='+$("#tuanType").val(),
	             success: function(data){
	            	 $("#citySpan").empty();
	            	// $("#citySpan").append("<a href='#' style='font-weight: bold; margin-right: 10px;' value='' onclick='javascript:filterProduct('');'>全部</a>");
	            	 for(var i=0;i<data.length;i++){
	            		 $("#citySpan").append("<a href='#' style='font-weight: bold; margin-right: 10px;' value='"+data[i]+"' class='city_css' id='city_"+data[i]+"' onclick='javascript:filterProduct(\""+data[i]+"\");'>"+data[i]+"</a>");
		             }
			     }
	         });
		}
		
		function filterProduct(city){
			$(".city_css").css({"background-color":"#fff","color":"#000"}); 
			$("#city_"+city).css({"background-color":"#53b9fb","color":"#fff"}); 
			
			$("#cityHide").val(city);
			queryProduct();
		}
		
		
		function queryProduct(){
			if($("#tuanType").val() == ''){
				$("#tuanProductTbody").empty();
				$("#tuanProductTbody").append("<tr><td style='width:750px;'>请选择团购网站名</td></tr>");
				return;
			}
			var data = 'tuanType='+$("#tuanType").val()+'&province='+$("#provinceHide").val()+'&city='+$("#cityHide").val();
			$.ajax({
	             type: "post",
	             url: "${ctx}/tuan/product/query",
	             dataType: "json",
	             data:data,
	             success: function(data){
	            	 $("#tuanProductTbody").empty();
	            	for(var i=0;i<data.length;i++){
	            		var tuanProduct = data[i];
	            		
	            		
	            		$("#tuanProductTbody").append("<tr style='cursor:pointer;' onClick=productClick('"+tuanProduct.id+"')><td style='width:30px;' ><input type='checkbox' name='selectProduct' class='selectProduct' id='selectProduct_"+tuanProduct.id+"' value='"+tuanProduct.id+"' /></td><td style='width:370px;' >"+tuanProduct.name+"</td><td style='width:300px;'>"+tuanProduct.effectTimeDesc+"</td></tr>");
	            		
	            	}
	            	if(data.length == 0){
	            		$("#tuanProductTbody").empty();
	            		$("#tuanProductTbody").append("<tr><td style='width:750px;'>暂时没有相关产品</td></tr>");
	            	}
			     }
	         });
			
			 if($("#tuanType").val() == '0'){
				 $("#noticeDiv").text("美团用户请填写：12位数字美团卷密码")
			 }else if($("#tuanType").val() == '1'){
				 $("#noticeDiv").text("拉手用户请填写：12位或8位数字拉手卷密码")
			 }else if($("#tuanType").val() == '2'){
				 $("#noticeDiv").text("糯米用户请填写：12位数字糯米卷密码")
			 }else if($("#tuanType").val() == '3'){
				 $("#noticeDiv").text("满座用户请填写：10位数字满座认证码")
			 }else{
				 $("#noticeDiv").text("请填写团购网站券号及密码或认证码")
			 }
		}
		
		function productClick(id){
			$('input[type=checkbox][name=selectProduct]').attr("checked", false);
			
			
			$("input[type=checkbox][name=selectProduct][value='"+id+"']").prop("checked",true); 
			
			$("#useDate").val("");
			$.ajax({
	             type: "post",
	             url: "${ctx}/tuan/product/date",
	             dataType: "json",
	             data:"id="+id,
	             success: function(data){
	            	$("#d4312").val(data[0]);
	            	$("#d4313").val(data[1]);
			     }
	         });
			
			
		}
		
		
		
		
		
		function AddRow() {
			var $test = $("input[name=txtTicketNo]"); //假设name为test
			//alert($test.length)
		    if ($test.length == 10) {
		        return
		    }
			
		    $("#addTicketNo" + $("#hidTicketNum").val()).hide();
		    $("#delTicketNo" + $("#hidTicketNum").val()).show();
		    $("#hidTicketNum").attr("value", parseInt($("#hidTicketNum").val()) + 1);
		    var div = "<div class=\"field-group\" id=\"divTicketNo" + $("#hidTicketNum").val() + "\">" +
		                                "<label class=\"required title\"><font color=red><strong>*</strong></font>券号及密码</label>" +
		                                "<span class=\"control-group\" id=\"SpanTicketNo" + $("#hidTicketNum").val() + "\">" +
		                                    "<div class=\"input_add_long_background\">" +
		                                        "<input class=\"register_input\" type=\"text\" id=\"txtTicketNo" + $("#hidTicketNum").val() + "\" name=\"txtTicketNo\" maxlength=\"20\" value='' onkeyup=\"value=value.replace(/[\\W]/g,'')\" onblur=\"checkTicket(" + $("#hidTicketNum").val() + ");\" />" +
		                                    "</div>" +
		                                     "<a id=\"delTicketNo" + $("#hidTicketNum").val() + "\"  class=\"del\"  href=\"javascript:void(0)\" onclick=\"DelRow('divTicketNo" + $("#hidTicketNum").val() + "');\">删除</a>" +
		                                     "<a id=\"addTicketNo" + $("#hidTicketNum").val() + "\" class=\"book_pgadd\" href=\"javascript:void(0)\" onclick=\"AddRow()\">添加</a>" +
		                                "</span>" +
		                                "<label class=\"tips\">" +
		                                "</label>" +
		                            "</div>";
		    $("#divTicketNo" + (parseInt($("#hidTicketNum").val()) - 1)).after(div);
		    $("#delTicketNo" + $("#hidTicketNum").val()).hide();
		}
		
		function DelRow(div) {
		    var $test = $("input[name=txtTicketNo]"); //假设name为test
		    if ($test.length == 1) {
		        return
		    }
		    $("#" + div).remove();
		}
		
		
		
		
	</script>
	<style type="text/css">


	</style>
</head>
<body id="login">
	
	<div id="mainbox">
		<div class="head">
			<div class="logo">
				<img src="${ctxStatic}/images/logo.png" height="50" />
			</div>
			<div class="des" style="padding-top:12px;">
				易往票务团购自助预约服务平台<br/>
			</div>
			<div class="text">
			
			是否预约成功以商家回复为准 &nbsp;&nbsp;&nbsp;服务热线：${fns:getConfig('comapny.phone')}
			</div>
		</div>
		
		<div class="content .index_box .box_main" style="min-height:950px;">
			<div style="padding-top:20px;padding-left:20px;padding-right:20px;color:red;line-height:20px;">
			欢迎使用易往票务团购自助预约服务平台，请按团购页面提示最迟的时间提前预约，具体可致电${fns:getConfig('comapny.phone')}咨询。<br/>
      		</div>
			<form:form id="inputForm" modelAttribute="tuanOrder" action="${ctx}/tuan/order/save" method="post" class="form-horizontal">
			<input type="hidden" id="provinceHide"/>
			<input type="hidden" id="cityHide"/>
			<div id="register" class="register">
                                <div id="form_submit" class="form_submit">
                                    <div class="fieldset">
                                        <div class="book-content">
                                            <div class="book-head">
                                                <strong>请选择预约产品</strong>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">
                                                    	团购网站名<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanGroupType">
                                                	<div class="input_add_long_background">
                                                    <form:select path="tuanType" class="required" style="width:270px;height:28px;">
														<form:option value="">--请选择团购网站名--</form:option>
														<form:options items="${fns:getDictList('tuan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
													</form:select>
													</div>
                                                </span>
                                                <label class="tips"></label>
                                            </div>
                                           
                                             <div class="field-group province_city" style="height:35px;">
                                                <label class="required title">选择团购产品</label>
                                                <span class="control-group" id="showMessage" style="margin-top:5px;"> <label class="tips">-请先选择团购网站名-</label></span>
                                                <span class="control-group" id="provinceSpan" style="margin-top:5px;width:650px;">
                                                </span>
                                            </div>
                                            <div class="field-group province_city" id="cityGroup" style="height:25px;" >
                                                <label class="required title">&nbsp;&nbsp;</label>
                                                <span class="control-group" id="citySpan" >
                                                </span>
                                            </div>
                                            <div class="field-group" style="height:189px;">
                                            <table  class="table" style="width:700px;margin-left:50px;margin-right:18px;">
												<thead><tr><th style="width:30px;">选择</th><th style="width:370px;">产品名称</th><th style="width:300px;">预订时间</th></tr></thead>
											</table>
											<div style="height:156px;overflow-x: no;overflow-y: auto;padding-top:1px;">
											<table id="contentTable" class="table" style="width:700px;margin-left:50px;">
											<tbody id="tuanProductTbody">
												<tr><td style="width:750px;">请选择团购网站名</td></tr>
											</tbody>
											</table>
											</div>
											</div>
                                        </div>
                                        <div class="book-content">
                                            <div class="book-head">
                                                <strong>输入个人信息</strong>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">
                                                    	使用日期<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanUseTime">
                                                    <div class="input_add_long_background">
                                                    <input id="useDate" name="useDate" class="register_input required" type="text" onFocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d4312\')||\'9999-10-01\'}',maxDate:'#F{$dp.$D(\'d4313\')||\'9999-10-01\'}'})"/> 
                                                    </div>
                                                </span>
                                                <label class="tips" style="color:red;">请按团购页面提示最迟的时间提前预约,否则无效</label>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">
                                                    	游客姓名<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanCustomerName">
                                                    <div class="input_add_long_background">
                                                        <form:input path="name" htmlEscape="false"  class="register_input required"   maxlength="20"  />
                                                    </div>
                                                </span>
                                                <label class="tips">请输入游客全名</label>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">
                                                    	手机号码<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanCellPhone">
                                                    <div class="input_add_long_background">
                                                        <form:input path="mobile" htmlEscape="false"  class="register_input required mobile"   maxlength="11"  />
                                                    </div>
                                                </span>
                                                <label class="tips">请输入您常用的手机号码</label>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">
                                                    	身份证号码<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanCellPhone">
                                                    <div class="input_add_long_background">
                                                        <form:input path="nameSfz" htmlEscape="false"  class="register_input required card"   maxlength="18"  />
                                                    </div>
                                                </span>
                                                <label class="tips">请输入游客身份证号码</label>
                                            </div>
                                            <div class="field-group">
                                                <label class="required title">备注</label>
                                                <span class="control-group" id="SpanRemark">
                                                    <div class="input_add_long_background">
                                                        <form:input path="notice" htmlEscape="false"  class="register_input "   maxlength="100"  />
                                                    </div>
                                                </span>
                                                <label class="tips">
                                                </label>
                                            </div>
                                        </div>
                                        <div class="book-content">
                                            <div class="book-head">
                                                <strong>验证消费码</strong>
                                            </div>
                                            <div id="noticeDiv" style="padding-left:170px;padding-right:20px;color:red;line-height:20px;">
												 请填写团购网站券号及密码或认证码
                                            </div>
                                            <div class="field-group" id="divTicketNo1">
                                                <label class="required title">
                                                    	券号及密码或认证码<font color="red"><strong>*</strong></font></label>
                                                <span class="control-group" id="SpanTicketNo1">
                                                    <div class="input_add_long_background">
                                                        <input class="register_input required" type="text"  name="txtTicketNo" maxlength="20" value="" onkeyup="value=value.replace(/[\W]/g,'')"  />
                                                     </div>
                                                    <a id="delTicketNo1" href="javascript:void(0)" onclick="DelRow('divTicketNo1');" style="display: none;" class="del">删除</a> <a id="addTicketNo1" class="book_pgadd" href="javascript:void(0)" onclick="AddRow()">添加</a> </span>
                                                <label class="tips">一次最多提交10张，超过请在备注注明需求
                                                </label>
                                            </div>
                                            
                                            <div class="field-group">
                                                <label class="required title">
                                                    	验证码<font color="red"><strong>*</strong></font></label>
                                               <span class="control-group" id="SpanTicketNo1">
                                                    <div class="input_add_long_background" style="width:400px;">
                                                    	<table style="margin:0;">
                                                    		<tr>
                                                    			<td>
                                                    				<input type="text" id="validateCode" name="validateCode" maxlength="4" style="width:100px;" class="required register_input" />
																</td>
                                                    			<td>
                                                    				<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('.validateCodeRefresh').click();" class="validateCode"/>
																	<a href="javascript:" onclick="$('.validateCode').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh" style="display:none">看不清</a>
													
                                                    			</td>
                                                    		</tr>
                                                    	</table>
													</div>
												</span>
												<label class="tips"> </label>
                                            </div>
                                        </div>
                                    </div>&nbsp;
                                </div>
                                
                                
                            </div>
                            
                            <div class="div_submit">
                                <input id="submit" class="btn_ok" type="submit" value="提交预约单"  >
                                <input id="hidTicketNum" type="hidden" value="1" />
                                <input id="d4312"  type="hidden" value="" />
                                <input id="d4313"  type="hidden" value="" />
                                
                            </div>
                            
                            </form:form>
			
		</div>
		<div class="copyright">
			${fns:getConfig('company.name')}  &copy; 2014-${fns:getConfig('copyrightYear')}  ${fns:getConfig('icp')}
		</div>
	</div>
	

</body>
</html>
