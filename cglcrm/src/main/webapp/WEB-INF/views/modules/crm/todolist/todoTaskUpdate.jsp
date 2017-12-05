<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处理任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="updateForm" modelAttribute="crmTodoTask" action="" method="post" class="breadcrumb form-search">
			<input id="id" name="id" type="hidden" value="${crmTodoTask.id}"/>
			<li><label>说明：</label>
				<form:textarea path="remark" class="required" rows="5" maxlength="20"/>
			</li>
	</form:form>
</body>
</html>