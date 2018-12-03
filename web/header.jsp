<%-- 
    Document   : header
    Created on : Jun 8, 2018, 10:26:28 AM
    Author     : ETS-4
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Global IVN</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="description" content="#">
        <meta name="keywords" content="Admin , Responsive, Landing, Bootstrap, App, Template, Mobile, iOS, Android, apple, creative app">
        <meta name="author" content="#">
        <!-- Favicon icon -->
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <!-- Google font-->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet">
        <!-- Required Fremwork -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

        <!-- ico font -->
        <link rel="stylesheet" type="text/css" href="css/icofont.css">

        <!-- Style.css -->
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/custom.css">
        <link rel="stylesheet" type="text/css" href="css/ng-tags-input.min.css">

        <style>
            ul.pcoded-item
            {

            }

            ul.pcoded-item li
            {

            }

            .pcoded .pcoded-navbar[navbar-theme="themelight1"] .pcoded-item > li > a
            {
                color:#e5e5e5 !important;
            }

            .pcoded .pcoded-navbar[navbar-theme="themelight1"] .pcoded-item > li.active > a
            {
                color:#000 !important;
            }
            .pcoded .pcoded-navbar[navbar-theme="themelight1"] .pcoded-item > li.pcoded-trigger > a
            {
                color:#000 !important;
            }
            ul.pcoded-item li i
            {
                background: #c20016;
                padding: 2px;
                font-size: 20px;
                color: #fff;
                margin-right: 10px;
            }

        </style>
    </head>
    <body ng-app="angularTable">
        <!-- Pre-loader start -->
        <div class="theme-loader">
            <div class="ball-scale">
                <div class='contain'>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                    <div class="ring">
                        <div class="frame"></div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Pre-loader end -->
        <div id="pcoded" class="pcoded">        <div class="pcoded-overlay-box"></div>
            <div class="pcoded-container navbar-wrapper">

                <nav class="navbar header-navbar pcoded-header">
                    <div class="navbar-wrapper">

                        <div class="navbar-logo">
    <!--                        <h3>session login user:${sessionScope.user}</h3>
                            <h3>session login user:${sessionScope.userid}</h3>
                            <h3>session login user:${sessionScope.user_groupid}</h3>-->
                            <a class="mobile-menu" id="mobile-collapse" href="#!">
                                <i class="icofont icofont-listine-dots"></i>
                            </a>
                            <a class="mobile-search morphsearch-search" href="#">
                                <i class="ti-search"></i>
                            </a>
                            <s:url action="dashboard.action" var="aURL" />
                            <s:a href="%{aURL}">      
                                <img class="img-fluid" src="images/logo.png" alt="Theme-Logo" />
                            </s:a>
                            <a class="mobile-options">
                                <i class="ti-more"></i>
                            </a>
                        </div>

                        <div class="navbar-container container-fluid">

                            <ul class="nav-right">

                                <li class="header-notification" ng-app="" ng-controller="notificationController">
                                    <a href="#!">
                                        <i class="icofont icofont-alarm"></i>
                                        <span class="badge bg-c-green" ng-if="count !== 0">{{count}}</span>
                                    </a>
                                    <ul class="show-notification" ng-if="addlist.length === 0">
                                        <li>
                                            <h6>No Notifications</h6>
                                        </li>
                                    </ul>
                                    <ul class="show-notification" ng-if="addlist.length > 0" style="overflow-y: scroll;height: 292px;">
                                        <li>
                                            <h6>Notifications</h6>
                                            <label class="label label-danger">New</label>
                                        </li>
                                        <ul>
                                            <div  ng-repeat="x in addlist">
                                                <li data-id="{{x.id}}" ng-click="view(x.id)" ng-if="x.status === false" style="cursor: pointer;background-color: #845f5f45;">
                                                    <div class="media">
                                                        <img class="d-flex align-self-center img-radius" src="images/avatar-4.jpg" alt="Generic placeholder image">
                                                        <div class="media-body">
                                                            <h5 class="notification-user">{{x.firstname}}</h5>
                                                            <p class="notification-msg">{{x.version_type_id}} {{x.version_id}} was created successfully</p>
                                                            <span class="notification-time">{{x.created_date}}</span>
                                                        </div>
                                                    </div>
                                                </li>
                                                <li data-id="{{x.id}}" ng-click="view(x.id)" ng-if="x.status === true" style="cursor: pointer;">
                                                    <div class="media">
                                                        <img class="d-flex align-self-center img-radius" src="images/avatar-4.jpg" alt="Generic placeholder image">
                                                        <div class="media-body">
                                                            <h5 class="notification-user">{{x.firstname}}</h5>
                                                            <p class="notification-msg">{{x.version_type_id}} {{x.version_id}} was created successfully</p>
                                                            <span class="notification-time">{{x.created_date}}</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </div>
                                        </ul>
                                    </ul>
                                </li>
                                <li class="user-profile header-notification">
                                    <a href="#!">
                                        <img src="images/avatar-4.jpg" class="img-radius" alt="User-Profile-Image">
                                        <span><s:property value="#session.userId" /></span>
                                        <i class="ti-angle-down"></i>
                                    </a>
                                    <ul class="show-notification profile-notification">
<!--                                        <li>
                                            <a href="#">
                                                <i class="ti-settings"></i> Settings
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="ti-email"></i> My Messages
                                            </a>
                                        </li>-->
                                        <li>
                                            <a href="logout">
                                                <i class="ti-layout-sidebar-left"></i> Logout
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>

                        </div>
                    </div>
                </nav>

