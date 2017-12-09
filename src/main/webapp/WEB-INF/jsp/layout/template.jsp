<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="m"%>
<%@ page language="java" import="java.util.ResourceBundle" %>
<% ResourceBundle resource = ResourceBundle.getBundle("settings");
    String timestamp=resource.getString("build.timestamp"); %>

<c:url var="defContextPath" value="/" />
<c:set var="basePath" scope="request">
    ${fn:substring(defContextPath, 0, fn:length(defContextPath) - 1)}
</c:set>
<c:url value="/img" var="imgPath" scope="request" />
<c:url value="/css" var="cssPath" scope="request" />
<c:url value="/js" var="jsPath" scope="request" />

<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${cssPath}/responsive-tables.css">
    <link rel="stylesheet" href="${cssPath}/bootstrap.min.css" />
    <link rel="stylesheet" href="${cssPath}/charisma-app.css" />
    <link rel="stylesheet" href="${cssPath}/multi-select.css" />
    <link rel="stylesheet" href="${cssPath}/chosen/chosen.css" />
    <link rel="stylesheet" href="${cssPath}/fix.css">
    <link rel="stylesheet" href="${cssPath}/custom.css?timestamp=<%=timestamp%>">
    <link rel="stylesheet" href="${cssPath}/bootstrapValidator.css" />
    <link rel="stylesheet" href="${cssPath}/toastr.scss" />
    <link rel="stylesheet" href="${cssPath}/angular-chart.css" />

    <link rel="stylesheet" href="${cssPath}/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="${cssPath}/alertify/alertify.core.css" />
    <link rel="stylesheet" href="${cssPath}/alertify/alertify.default.css" />
    <link rel="stylesheet" href="${cssPath}/ng-table.min.css" />
    <link rel="stylesheet" href="${cssPath}/select.min.css" />
    <link rel="stylesheet" href="${cssPath}/angular-ui-tree.min.css" />
    <link rel="stylesheet" href="${cssPath}/paginationx.css" />
    <link rel="stylesheet" href="${cssPath}/angular-busy.css" />

    <script src="${jsPath}/jquery-2.2.1.min.js"></script>

    <script src="${jsPath}/moment-with-locales.js"></script>


    <script src="${jsPath}/datatables.min.js"></script>
    <script src="${jsPath}/angular/angular.js"></script>
    <script src="${jsPath}/i18n/angular-locale_en.js"></script>
    <script src="${jsPath}/i18n/angular-locale_ru.js"></script>
    <script src="${jsPath}/angular/angular-resource.min.js"></script>
    <script src="${jsPath}/angular/angular-route.min.js"></script>
    <script src="${jsPath}/angular/angular-cookies.min.js"></script>
    <script src="${jsPath}/angular/angular-sanitize.min.js"></script>
    <script src="${jsPath}/angular/ui-bootstrap-tpls.js"></script>
    <script src="${jsPath}/angular/ng-file-upload.js"></script>
    <script src="${jsPath}/angular/lodash.js"></script>
    <script src="${jsPath}/angular/angularjs-dropdown-multiselect.js"></script>
    <script src="${jsPath}/angular/Chart.js"></script>
    <script src="${jsPath}/angular/angular-chart.js"></script>
    <script src="${jsPath}/angular/simplePagination.js"></script>
    <script src="${jsPath}/angular/angular-chosen.min.js"></script>
    <script src="${jsPath}/angular/datetime-picker.min.js"></script>

    <script src="${jsPath}/bootstrap.js"></script>
    <script src="${jsPath}/jquery-ui.js"></script>
    <script src="${jsPath}/jquery-utils.js"></script>
    <script src="${jsPath}/jquery.flot.js"></script>
    <script src="${jsPath}/jquery.maskedinput.min.js"></script>
    <script src="${jsPath}/jquery.filter_input.js"></script>
    <script src="${jsPath}/i18n/jquery.ui.datepicker-ru.js"></script>
    <script src="${jsPath}/i18n/jquery.ui.datepicker-en.js"></script>
    <script src="${jsPath}/jquery.flot.min.js"></script>
    <script src="${jsPath}/jquery.flot.navigationControl.js"></script>
    <script src="${jsPath}/jquery.flot.selection.js"></script>
    <script src="${jsPath}/jquery.flot.time.js"></script>
    <script src="${jsPath}/chosen.jquery.js"></script>
    <script src="${jsPath}/bootstrapValidator.js"></script>
    <script src="${jsPath}/toastr.js"></script>
    <script src="${jsPath}/alertify.min.js"></script>
    <script src="${jsPath}/angular-datatables.min.js"></script>
    <script src="${jsPath}/angular/ng-table.min.js"></script>
    <script src="${jsPath}/angular/select.min.js"></script>
    <script src="${jsPath}/angular/angular-ui-tree.min.js"></script>

    <!--  ******************************************************************  -->
    <script src="${jsPath}/jquery.cookie.js"></script>

    <script src="${jsPath}/fullcalendar.min.js"></script>
    <script src="${jsPath}/chosen.jquery.min.js"></script>
    <script src="${jsPath}/jquery.colorbox-min.js"></script>
    <script src="${jsPath}/jquery.noty.js"></script>
    <script src="${jsPath}/responsive-tables.js"></script>
    <script src="${jsPath}/bootstrap-tour.min.js"></script>
    <script src="${jsPath}/jquery.raty.min.js"></script>
    <script src="${jsPath}/jquery.iphone.toggle.js"></script>
    <script src="${jsPath}/jquery.autogrow-textarea.js"></script>
    <script src="${jsPath}/jquery.uploadify-3.1.min.js"></script>
    <script src="${jsPath}/jquery.tempgauge.js"></script>
    <script src="${jsPath}/charisma.js"></script>
    <script src="${jsPath}/angular-busy.js"></script>

    <script src="${jsPath}/dependencies/moment.min.js"></script>
    <script src="${jsPath}/dependencies/moment-with-locales.min.js"></script>
    <script src="${jsPath}/dependencies/bootstrap-datetimepicker.min.js"></script>

    <%--PAGINATION--%>
    <script src="js/paginationx/alasql.min.js"></script>
    <script src="js/paginationx/xlsx.core.min.js"></script>
    <script src="js/paginationx/jspdf.min.js"></script>
    <script src="js/paginationx/jspdf.plugin.autotable.js"></script>
    <script src="js/paginationx/paginationx.js"></script>
    <%--END OF PAGINATION--%>

    <script src="service.js?timestamp=<%=timestamp%>"></script>
    <script src="routes.js?timestamp=<%=timestamp%>"></script>
    <script src="translation.js?timestamp=<%=timestamp%>"></script>
    <script src="app.js?timestamp=<%=timestamp%>"></script>
    <script src="util.js?timestamp=<%=timestamp%>"></script>
    <script src="utils.js?timestamp=<%=timestamp%>"></script>
    <script src="securityUtils.js?timestamp=<%=timestamp%>"></script>
    <script src="dateUtils.js?timestamp=<%=timestamp%>"></script>


    <%--SERVICE--%>
    <script src="service/changePassword/changePasswordService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/modal/modalService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/pagination/paginationService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/fileUpload/fileUploadService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/session/sessionService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/paginationXFactory.js?timestamp=<%=timestamp%>"></script>
    <script src="service/users/usersService.js?timestamp=<%=timestamp%>"></script>
    <script src="service/settings/settingsService.js?timestamp=<%=timestamp%>"></script>
    <%--end of SERVICE--%>

    <%--MAIN--%>
    <script src="modal/main/footer/footer.js?timestamp=<%=timestamp%>"></script>
    <script src="modal/main/header/header.js?timestamp=<%=timestamp%>"></script>
    <script src="modal/main/main.js?timestamp=<%=timestamp%>"></script>
    <script src="modal/main/sidebar/sidebar.js?timestamp=<%=timestamp%>"></script>
    <%--end of MAIN--%>

    <%--users--%>
    <script src="models/users/users/users.js?timestamp=<%=timestamp%>"></script>
    <script src="models/users/users/edit/user.edit.js?timestamp=<%=timestamp%>"></script>
    <script src="models/users/users/add/user.add.js?timestamp=<%=timestamp%>"></script>
    <script src="models/users/users/view/user.view.js?timestamp=<%=timestamp%>"></script>
    <script src="models/users/resetPassword.js?timestamp=<%=timestamp%>"></script>
    <%--end of users--%>

    <%--settings--%>
    <script src="models/settings/changePassword.js?timestamp=<%=timestamp%>"></script>
    <script src="models/settings/commonSettings.js?timestamp=<%=timestamp%>"></script>
    <script src="models/settings/manageUsers/manageUsers.js?timestamp=<%=timestamp%>"></script>
    <%--end of settings--%>



    <script src="partials/ngInput.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputDate.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputDateTime.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputFile.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputPdfFile.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngValidateObject.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputSelect.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputSelectUser.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngTextArea.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngCheckbox.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngRadio.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputMultiselect.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/saveButton/saveButton.js?timestamp=<%=timestamp%>"></script>
    <script src="partials/ngInputDateManual.js?timestamp=<%=timestamp%>"></script>

    <script src="${jsPath}/angular/angular-ui-router.min.js"></script>
    <script src="${jsPath}/angular/angular-translate.min.js"></script>
    <script src="${jsPath}/angular/angular-translate-loader-static-files.min.js"></script>
    <script src="${jsPath}/angular/angular-translate-interpolation-messageformat.min.js"></script>

    <title><spring:message code="title" /></title>
    <style>
        textarea {
            resize: vertical;
        }

        html, body {
            height: 100%;
        }

        a {
            cursor: pointer;
        }

        #wrap {
            height: auto !important;
            margin: 0 auto -100px;
            min-height: 100%;
        }

        #push, #footer {
            height: 100px;
        }

        [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
            display: none !important;
        }
    </style>
</head>
<body ng-app="app" ng-cloak>
<script type="text/javascript">
    $(function() {
        var locale = window.navigator.userLanguage
            || window.navigator.language;
        moment.locale(locale);
        $.datepicker.setDefaults($.extend($.datepicker.regional["ru"]));

    });
</script>

<sec:authentication var="username" property="principal.username" />
<sec:authentication var="id" property="principal.id" />
<div cg-busy="{promise: ajaxLoader}">
    <div id="wrap">
        <div ui-view="header"></div>
        <div class="ch-container">
            <div class="row">
                <div class="col-sm-3 col-lg-3">
                    <div ui-view="sidebar"></div>
                </div>
                <div id="content" class="col-sm-9 col-lg-9" ng-init="setBaseUrl('${pageContext.request.contextPath}')">
                    <div ui-view="main"></div>
                    <div ng-show="oldPageState() && initialized">
                        <tiles:insertAttribute name="content" />
                    </div>
                </div>
            </div>
        </div>
        <div id="push"></div>
    </div>
    <div id="footer">
        <div ui-view="footer"></div>
    </div>
</div>
<script src="${jsPath}/jquery.multi-select.js"></script>
</body>
</html>
