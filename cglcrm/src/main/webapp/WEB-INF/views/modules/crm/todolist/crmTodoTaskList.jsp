<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/crm/todolist/crmTodoTask/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/crm/todolist/crmTodoTask/");
				$("#searchForm").submit();
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function updateTodoTaskFun(id,crmCustomId,dealState,dealType){
			var title = dealType=="ignore"?"忽略处理":dealType=="deal"?"处理操作":dealType=="waitDeal"?"修改为待处理":"任务处理相关";
			top.$.jBox.open("iframe:${ctx}/crm/todolist/crmTodoTask/todoTaskUpdate", title,350,250,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},submit : function(v, h, f) {   
					remark =$(h.find("iframe")[0].contentWindow.remark).val();
					todoTaskUpdateById(id,crmCustomId,remark,dealType)
		        }
			});
		}
		function todoTaskUpdateById(id,crmCustomId,remark,dealType){
			$.ajax({
				url: '${ctx}/crm/todolist/crmTodoTask/todoTaskUpdateById',
				type: 'POST',
				dataType: "json",
				data: {taskid:id,crmCustomId:crmCustomId,remark:remark,dealType:dealType},
				complete:function(data) {
					if(data.responseText){
						if(data.responseText.indexOf("ok")>=0){
							window.location.href = "${ctx}/crm/todolist/crmTodoTask/?dealState=${dealState}"
							$.jBox.tip("操作成功", 'success');
						}
					}
				}
			});
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
		function moreUpdateTodoTaskFun(dealType){
			var ids = "";
			var _ids="";
			$(".table input:checkbox:checked").each(function () {
				debugger;
				ids += $(this)[0].id + ",";
				_ids += $(this)[0].id + "|" + $(this)[0].value +",";
		    });
			if(ids){
				var title = dealType=="ignore"?"忽略处理":dealType=="deal"?"处理操作":dealType=="waitDeal"?"修改为待处理":"任务处理相关";
				top.$.jBox.open("iframe:${ctx}/crm/todolist/crmTodoTask/todoTaskUpdate", title,350,250,{
					buttons:{"确定":true}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},submit : function(v, h, f) {   
						remark =$(h.find("iframe")[0].contentWindow.remark).val();
						$.ajax({
							url: '${ctx}/crm/todolist/crmTodoTask/moreTodoTaskUpdateById',
							type: 'POST',
							dataType: "json",
							data: {ids:_ids,remark:remark,dealType:dealType},
							complete:function(data) {
								if(data.responseText){
									if(data.responseText.indexOf("ok")>=0){
										window.location.href = "${ctx}/crm/todolist/crmTodoTask/?dealState=${dealState}"
										$.jBox.tip("操作成功", 'success');
									}
								}
							}
						});
			        }
				});
			}else{
				$.jBox.tip("请选择相应的记录", 'error');
			}
		}
		function deleteFun(){
			var del_ids="";
			$(".table input:checkbox:checked").each(function () {
				del_ids += $(this)[0].id + ",";
		    });
			if(del_ids){
				top.$.jBox.confirm('确定要删除吗？','系统提示',function(v,h,f){
					if(v=='ok'){
						$.ajax({
							url: '${ctx}/crm/todolist/crmTodoTask/deleteFun',
							type: 'POST',
							dataType: "json",
							data: {del_ids:del_ids},
							complete:function(data) {
								if(data.responseText){
									if(data.responseText.indexOf("ok")>=0){
										window.location.href = "${ctx}/crm/todolist/crmTodoTask/?dealState=${dealState}"
										$.jBox.tip("操作成功", 'success');
									}
								}
							}
						});
					}
				},{buttonsFocus:1, closed:function(){
					if (typeof closed == 'function') {
						closed();
					}
				}});
			}else{
				$.jBox.tip("请选择相应的记录", 'error');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/crm/todolist/crmTodoTask/?dealState=${dealState}">待办任务列表</a></li>
		<li ><a href="#">客户查看</a></li>
		<%-- <shiro:hasPermission name="crm:todolist:crmTodoTask:edit"><li><a href="${ctx}/crm/todolist/crmTodoTask/form">待办任务添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="crmTodoTask" action="${ctx}/crm/todolist/crmTodoTask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="dealState" name="dealState" type="hidden" value="${dealState}"/>
		<ul class="ul-form">
			<li><label>客户名称：</label>
				<form:input path="crmCustom.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提醒时间：</label>
				<input name="beginUpdateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${crmTodoTask.beginUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUpdateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${crmTodoTask.endUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th><input type='checkbox' onclick="checkAll(this);"></th>
				<th>客户名称</th>
				<th>email</th>
				<th>关注产品</th>
				<th>负责人</th>
				<th>成交阶段</th>
				<th>反馈情况</th>
				<th>最后联系时间</th>
				<th>说明</th>
				<th>处理时间</th>
				<shiro:hasPermission name="crm:todolist:crmTodoTask:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crmTodoTask">
			<tr>
				<td><input type='checkbox' id="${crmTodoTask.id}" value="${crmTodoTask.crmCustom.id}"></td>
				<td><a href="${ctx}/crm/custom/crmCustom/formView?id=${crmTodoTask.crmCustom.id}&dealState=${crmTodoTask.dealState}">
					${crmTodoTask.crmCustom.name}
				</a></td>
				<td>
					${crmTodoTask.crmCustom.email}
				</td>
				<td>
					${fns:getProductsName(crmTodoTask.crmCustom.focusProducts)}
				</td>
				<td>
					${crmTodoTask.chargePerson.name}
				</td>
				<td>
					${fns:getDictLabel(crmTodoTask.crmCustom.dealStage, "deal_stage","")}
				</td>
				<td>
					${fns:getDictLabel(crmTodoTask.crmCustom.reback, "reback","")}
				</td>
				<td>
					<fmt:formatDate value="${crmTodoTask.crmCustom.lastContactTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${crmTodoTask.remark}
				</td>
				<td>
					<fmt:formatDate value="${crmTodoTask.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="crm:todolist:crmTodoTask:edit"><td>
					<c:choose>
						<c:when test="${dealState=='0'}">
							<a href="javascript:void(0)" onclick="updateTodoTaskFun('${crmTodoTask.id}','${crmTodoTask.crmCustom.id}','${crmTodoTask.dealState}','ignore')">忽略</a>|
							<a href="javascript:void(0)" onclick="updateTodoTaskFun('${crmTodoTask.id}','${crmTodoTask.crmCustom.id}','${crmTodoTask.dealState}','deal')">处理</a>
						</c:when>
						<c:when test="${dealState=='1'}">
							<a href="javascript:void(0)" onclick="updateTodoTaskFun('${crmTodoTask.id}','${crmTodoTask.crmCustom.id}','${crmTodoTask.dealState}','waitDeal')">待处理</a>|
							<a href="${ctx}/crm/todolist/crmTodoTask/delete?taskid=${crmTodoTask.id}&dealState=${crmTodoTask.dealState}" onclick="return confirmx('确认要删除该待办任务吗？', this.href)">删除</a>
						</c:when>
						<c:when test="${dealState=='2'}">
							<a href="javascript:void(0)" onclick="updateTodoTaskFun('${crmTodoTask.id}','${crmTodoTask.crmCustom.id}','${crmTodoTask.dealState}','waitDeal')">待处理</a>|
							<a href="${ctx}/crm/todolist/crmTodoTask/delete?taskid=${crmTodoTask.id}&dealState=${crmTodoTask.dealState}" onclick="return confirmx('确认要删除该待办任务吗？', this.href)">删除</a>
						</c:when>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="crm:todolist:crmTodoTask:edit">
		<div class="form-actions pagination-left" style=" margin-top:0px">
			<c:choose>
				<c:when test="${dealState=='0'}">
					<a class="btn btn-primary" href="javascript:void(0)" onclick="moreUpdateTodoTaskFun('ignore')">忽略</a>
					<a class="btn btn-primary" href="javascript:void(0)" onclick="moreUpdateTodoTaskFun('deal')">处理</a>
				</c:when>
				<c:when test="${dealState=='1'}">
					<a class="btn btn-primary" href="javascript:void(0)" onclick="moreUpdateTodoTaskFun('waitDeal')">待处理</a>
 					<a class="btn btn-primary" href="javascript:void(0)" onclick="deleteFun()">删除</a>
 				</c:when>
				<c:when test="${dealState=='2'}">
					<a class="btn btn-primary" href="javascript:void(0)" onclick="moreUpdateTodoTaskFun('waitDeal')">待处理</a>
					<a class="btn btn-primary" href="javascript:void(0)" onclick="deleteFun()">删除</a>
				</c:when>
			</c:choose>
		</div>
	</shiro:hasPermission>
</body>
</html>