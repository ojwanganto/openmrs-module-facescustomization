<spring:htmlEscape defaultHtmlEscape="true" />
<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRFacesCustomizationService.js"/>
<openmrs:htmlInclude file="/moduleResources/facescustomization/css/amrsreports.css" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/facescustomization/manage.form"><spring:message
				code="facescustomization.manage" /></a>
	</li>
    <li
    <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
    <a
            href="${pageContext.request.contextPath}/module/facescustomization/syncStatus.list"><spring:message
            code="facescustomization.syncStatus" /></a>
    </li>

    <li
    <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
    <a
            href="${pageContext.request.contextPath}/module/facescustomization/queuedReport.list">Manage Faces Reports</a>
    </li>

	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="facescustomization.title" />
</h2>
