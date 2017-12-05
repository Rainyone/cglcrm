<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>周期设置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="crmCustom" action="${ctx}/crm/custom/crmCustom/" method="post" class="breadcrumb form-search">
			<input id="id" name="id" type="hidden" value="${crmCustom.id}"/>
			<li><label>成交阶段：</label>
				<form:select path="emailCycle"  class="input-medium" id="emailCycle">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('email_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
	</form:form>
</body>
</html>