<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户关系管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		function focusProductBtnFun(){
			top.$.jBox.open("iframe:${ctx}/crm/product/crmProduct/crmProductListChoose", "选择产品",$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},submit : function(v, h, f) { 
					debugger;
					var checked =$(h.find("iframe")[0].contentWindow.contentTable).find(".checkbox:checked");
					var focusPid = "";
					var focusPn = "";
					if(checked&&checked.length>0){
						checked.each(function () {
							if(focusPid!=null&&focusPid!=''){
								if(focusPid.indexOf($(this)[0].id)<0){
									focusPid += "," +$(this)[0].id;
								}
							}else{
								focusPid= $(this)[0].id;
							}
							if(focusPn!=null&&focusPn!=''){
								if(focusPn.indexOf($(this)[0].value)<0){
									focusPn += "," +$(this)[0].value;
								}
							}else{
								focusPn= $(this)[0].value;
							}
					    });
					}
					 $("#focusProducts").val(focusPid);
					 $("#focusProductsName").val(focusPn);
		        }
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/crm/custom/crmCustom/">客户关系管理列表</a></li>
		<li class="active"><a href="${ctx}/crm/custom/crmCustom/form?id=${crmCustom.id}">客户关系管理<shiro:hasPermission name="crm:custom:crmCustom:edit">${not empty crmCustom.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="crm:custom:crmCustom:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="crmCustom" action="${ctx}/crm/custom/crmCustom/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户来源：</label>
			<div class="controls">
				<form:input path="customSource" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">洲别：</label>
			<div class="controls">
				<form:select path="continent"  class="select-input input-xlarge ">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('continent')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国家：</label>
			<div class="controls">
				<form:select path="country"  class="select-input input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('country')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主营行业：</label>
			<div class="controls">
				<form:input path="mainIndustry" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营业态：</label>
			<div class="controls">
				<form:input path="managementFormat" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关注产品：</label>
			<div class="controls">
				<form:hidden path="focusProducts"/>
				<input id="focusProductsName" name="focusProductsName" type="text" value="${fns:getProductsName(crmCustom.focusProducts)}" class="input-xlarge "/>
				<input id="focusProductBtn" name="focusProductBut" type="button" value="选择产品" onclick="focusProductBtnFun()" class="btn btn-primary" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人：</label>
			<div class="controls">
				<sys:treeselect id="chargePerson" name="chargePerson.id" value="${crmCustom.chargePerson.id}" labelName="crmCustom.chargePerson.name" labelValue="${crmCustom.chargePerson.name}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成交阶段：</label>
			<div class="controls">
				<form:select path="dealStage"  class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('deal_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈情况：</label>
			<div class="controls">
				<form:select path="reback"  class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('reback')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后联系时间：</label>
			<div class="controls">
				<input name="lastContactTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${crmCustom.lastContactTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">称谓：</label>
			<div class="controls">
				<form:input path="cwname" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名字：</label>
			<div class="controls">
				<form:input path="firstName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中间名：</label>
			<div class="controls">
				<form:input path="middleName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓：</label>
			<div class="controls">
				<form:input path="lastName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名：</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网站：</label>
			<div class="controls">
				<form:input path="site" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统处理时间：</label>
			<div class="controls">
				<input name="sysDealTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${crmCustom.sysDealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="crm:custom:crmCustom:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>