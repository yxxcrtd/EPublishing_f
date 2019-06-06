<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec" %>
<%@ taglib uri="/WEB-INF/taglib/zhima-taglib.tld" prefix="zhima-tag"%>
<%@ taglib uri="/WEB-INF/taglib/daxtech-taglib.tld" prefix="daxtech-tag"%>
<%@ taglib uri="/WEB-INF/taglib/ingenta-taglib.tld" prefix="ingenta-tag"%>
<%@ taglib uri="/WEB-INF/taglib/ingenta-pagination-v3.tld" prefix="ingenta-tag-v3"%>
<%@ taglib uri="/WEB-INF/taglib/ingenta-pagination-v2.tld" prefix="ingenta-tag-v2"%>
<%@ taglib uri="/WEB-INF/taglib/ingenta-taglib.tld" prefix="ingenta-tag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="domain" value="http://${pageContext.request.serverName}"/>