<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户关系管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
				$("#btnExport").click(function(){
					top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
						if(v=="ok"){
							$("#searchForm").attr("action","${ctx}/crm/custom/crmCustom/export");
							$("#searchForm").submit();
						}
					},{buttonsFocus:1});
					top.$('.jbox-body .jbox-icon').css('top','55px');
				});
				$("#btnImport").click(function(){
					$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
				});
				$("#btnSubmit").click(function(){
					$("#searchForm").attr("action","${ctx}/crm/custom/crmCustom/");
					$("#searchForm").submit();
				});
				
				$("#btnReset").click(function() {
					clearForm();
				});
				
		});
		
		function clearForm() {
			$("#searchForm :input[type != 'button']").val("");
			$("#searchForm .select2-chosen").each(function(){
				this.innerHTML = "请选择";
			});
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function zhouqiButtonFun(customId,value){
			top.$.jBox.open("iframe:${ctx}/crm/custom/crmCustom/crmCustomZhouqi?id=" + customId + "&emailCycle=" + value, "添加相关",400,320,{
				buttons:{"确定":true}, loaded:function(h){
					console.info('customId:' + customId + ";value: " + value);
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},submit : function(v, h, f) {   
					value =$(h.find("iframe")[0].contentWindow.emailCycle).val();
					console.info('customId:' + customId + ";emailCycle: " + value);
					updateById(customId,value);
		        }
			});
		}
		function updateById(customId,emailCycle){
			$.ajax({
				url: '${ctx}/crm/custom/crmCustom/updateCustomZhouqi',
				type: 'POST',
				dataType: "json",
				data: {customId:customId,emailCycle:emailCycle},
				complete:function(data) {
					if(data.responseText){
						if(data.responseText.indexOf("ok")>=0){
							var obj = eval('(' + data.responseText + ')');
							$("#"+customId+"Td").html(obj.emailCycleStr);
						}
					}
				}
			});
		}
		function updateAllEmailCycle(){
			var emailCycle = $("#email_cycle").val();
			var ids = "";
			var _ids="";
			$(".table input:checkbox:checked").each(function () {
				ids += "'" + $(this).val() + "',";
				_ids += $(this).val() + ",";
		    });
			if(ids){
				top.$.jBox.confirm("确认要修改吗？","系统提示",function(v,h,f){
					if(v=="ok"){
							ids = ids.substr(0,ids.length-1);
							_ids =  _ids.substr(0,_ids.length-1);
							$.ajax({
								url: '${ctx}/crm/custom/crmCustom/updateAllEmailCycle',
								type: 'POST',
								dataType: "json",
								data: {ids:ids,emailCycle:emailCycle},
								complete:function(data) {
									if(data.responseText){
										if(data.responseText.indexOf("ok")>=0){
											$.jBox.tip("操作成功", 'success');
											var obj = eval('(' + data.responseText + ')');
											var emailCycleStr = obj.emailCycleStr;
											var idsss = _ids.split(",");
											console.info(idsss);
											for(var i=0;i<idsss.length;i++){
												$("#"+idsss[i]+"Td").html(emailCycleStr);
											}
											$(".table input:checkbox").each(function () {
									    		 $(this).prop("checked", "")
										    });
										}
									}
								}
							});
					}
				},{buttonsFocus:1});
			}else{
				$.jBox.tip("请选择相应的记录", 'error');
			}
		}
		function checkAll(obj){
			 if ($(obj).attr("checked") == "checked") {
				 $(".table input:checkbox").each(function () {
					 $(this).prop("checked", "checked")
			    });
		     }else{
		    	 $(".table input:checkbox").each(function () {
		    		 $(this).prop("checked", "")
			    });
		     }
		}
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/crm/custom/crmCustom/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctxStatic}/model.xlsx">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/crm/custom/crmCustom/">客户关系管理列表</a></li>
		<shiro:hasPermission name="crm:custom:crmCustom:edit"><li><a href="${ctx}/crm/custom/crmCustom/form">客户关系管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="crmCustom" action="${ctx}/crm/custom/crmCustom/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户来源：</label>
				<form:select path="customSource"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getCustomSourceList()}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>国家：</label>
				<form:select path="country"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('country')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>洲别：</label>
				<form:select path="continent"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('continent')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>主营行业：</label>
				<form:select path="mainIndustry"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getMainIndustryList()}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>关注产品：</label>
				<form:select path="focusProducts"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getProductsList()}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>负责人：</label>
				<sys:treeselect id="chargePerson" name="chargePerson.id" value="${crmCustom.chargePerson.id}" labelName="chargePerson.name" labelValue="${crmCustom.chargePerson.name}"
					title="用户" url="/sys/office/treeData?type=3" cssStyle="    width: 118px;" allowClear="true" notAllowSelectParent="true" />
			
			</li>
			<li><label>成交阶段：</label>
				<form:select path="dealStage"  class="select-input input-medium" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('deal_stage')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>反馈情况：</label>
				<form:select path="reback"  class="select-input input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('reback')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-medium"/>
				</form:select>
			</li>
			<li><label>名字：</label>
				<form:input path="firstName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>中间名：</label>
				<form:input path="middleName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>姓：</label>
				<form:input path="lastName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>公司名：</label>
				<form:input path="company" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label style="    width: 120px;">电话、邮箱、网址：</label>
				<form:input path="telEmailSite" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label style="width: 100px;">最后联系时间：</label>
				<input name="beginLastContactTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${crmCustom.beginLastContactTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endLastContactTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${crmCustom.endLastContactTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
				<input id="btnReset" class="btn btn-primary" type="button" value="重置"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div style="width:100%;overflow-x:scroll;">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th><input type='checkbox' onclick="checkAll(this);"></th>
				<th>姓名</th>
				<th>客户来源</th>
				<th>洲别</th>
				<th>国家</th>
				<th>主营行业</th>
				<th>关注产品</th>
				<th>负责人</th>
				<th>成交阶段</th>
				<th>反馈情况</th>
				<th>最后联系时间</th>
				<th>公司名</th>
				<th>手机</th>
				<th>电话</th>
				<th>系统处理时间</th>
				<th>邮件周期</th>
				<shiro:hasPermission name="crm:custom:crmCustom:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crmCustom">
			<tr>
				<td><input type='checkbox' value="${crmCustom.id}"></td>
				<td><a href="${ctx}/crm/custom/crmCustom/form?id=${crmCustom.id}">
					${crmCustom.firstName} ${crmCustom.middleName} ${crmCustom.lastName}
				</a></td>
				<td>
					${crmCustom.customSource}
				</td>
				<td>
					${fns:getDictLabel(crmCustom.continent,"continent","")}
				</td>
				<td>
					${fns:getDictLabel(crmCustom.country,"country","")}
				</td>
				<td>
					${crmCustom.mainIndustry}
				</td>
				<td>
					${fns:getProductsName(crmCustom.focusProducts)}
				</td>
				<td>
					${crmCustom.chargePerson.name}
				</td>
				<td>
					${fns:getDictLabel(crmCustom.dealStage, "deal_stage","")}
				</td>
				<td>
					${fns:getDictLabel(crmCustom.reback, "reback","")}
				</td>
				<td>
					<fmt:formatDate value="${crmCustom.lastContactTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${crmCustom.company}
				</td>
				<td>
					${crmCustom.phone}
				</td>
				<td>
					${crmCustom.telephone}
				</td>
				<td>
					<fmt:formatDate value="${crmCustom.sysDealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td id="${crmCustom.id}Td">
					${fns:getDictLabel(crmCustom.emailCycle, "email_cycle", "未设置")}
				</td>
				<shiro:hasPermission name="crm:custom:crmCustom:edit"><td>
    				<a href="${ctx}/crm/custom/crmCustom/form?id=${crmCustom.id}">修改</a>
    				<a href="javascript:void(0)" onclick="zhouqiButtonFun('${crmCustom.id}','${crmCustom.emailCycle}')">周期</a>
					<a href="${ctx}/crm/custom/crmCustom/delete?id=${crmCustom.id}" onclick="return confirmx('确认要删除该客户关系管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
	<shiro:hasPermission name="crm:custom:crmCustom:edit">
		<div class="form-actions pagination-left" style=" margin-top:0px">
			<select id="email_cycle" name="email_cycle" class="select2-container input-medium">  
				<c:forEach items="${fns:getDictList('email_cycle')}" var="cycle">
					<option value="${cycle.value}">${cycle.label }</option>  
				</c:forEach>
			</select>  
			<input id="updateAllSubmit" class="btn btn-primary" type="button" value ="保持邮件周期" onclick="updateAllEmailCycle();"/>
		</div>
	</shiro:hasPermission>
</body>
</html>