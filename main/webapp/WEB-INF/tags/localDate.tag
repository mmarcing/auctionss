<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ attribute name="date" required="true" type="java.time.LocalDateTime" %>

<c:set var = "replaceDate" value = "${fn:replace(date, 'T', ' ')}" />
<fmt:parseDate value="${replaceDate}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" />
<fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/>