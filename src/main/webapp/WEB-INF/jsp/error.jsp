<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date"/>
    <table cellspacing="0" cellpadding="0" border="0">
        <tbody>
            <tr valign="top">
                <td id="content">
                <h1><fmt:message key="error"/></h1>
                    <c:choose>
                            <c:when test="${status eq '404'}">
                                <div class="error"><fmt:message key="errors.404" /></div>
                            </c:when>
                            <c:when test="${status eq '403'}">
                                <div class="error"><fmt:message key="errors.403" /></div>
                            </c:when>
                            <c:otherwise>
                                <div class="error"><fmt:message key="errors.500" /></div>
                                [<fmt:formatDate value="${now}" pattern="dd-MM-yyyy hh:mm:ss"/>]&nbsp;
                                <c:out value="${exceptionMessage}"/><br />
                            <c:if test="${not empty stackTrace}">
                                <ul class="ns_hierarchy">
                                <li><strong><fmt:message key="errors.500.detail"/></strong>
                                <ul><li>
                                <c:out value="${stackTrace}" escapeXml="false"/>
                                </li></ul>
                                </li>
                                </ul>
                            </c:if>
                            </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </tbody>
    </table>