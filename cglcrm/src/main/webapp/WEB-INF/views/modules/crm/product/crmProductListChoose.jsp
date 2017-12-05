<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
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
	<form:form id="searchForm" modelAttribute="crmProduct" action="${ctx}/crm/product/crmProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>描述：</label>
				<form:input path="pdesc" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type='checkbox' onclick="checkAll(this);"></th>
				<th>产品名称</th>
				<th>描述</th>
				<th>备注</th>
				<shiro:hasPermission name="crm:product:crmProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crmProduct">
			<tr>
				<td><input type='checkbox' class="checkbox" id="${crmProduct.id}" value="${crmProduct.productName}"></td>
				<td><a href="${ctx}/crm/product/crmProduct/form?id=${crmProduct.id}">
					${crmProduct.productName}
				</a></td>
				<td>
					${crmProduct.pdesc}
				</td>
				<td>
					${crmProduct.remark}
				</td>
				<shiro:hasPermission name="crm:product:crmProduct:edit"><td>
    				<a href="${ctx}/crm/product/crmProduct/form?id=${crmProduct.id}">修改</a>
					<a href="${ctx}/crm/product/crmProduct/delete?id=${crmProduct.id}" onclick="return confirmx('确认要删除该产品管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>