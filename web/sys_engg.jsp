<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="pcoded-content" ng-controller="MyCtrl as Demo" ng-init="getAllCount()" ng-app="angularTable">    
    <div class="pcoded-inner-content">
        <div class="main-body">

            <div class="page-wrapper">
                <div class="page-header card">
                    <div class="row align-items-end">
                        <div class="col-lg-8">
                            <div class="page-header-title">
                                <i class="icofont icofont-automation bg-c-red"></i>
                                <div class="d-inline">
                                    <h4>System Owner</h4>
                                    <span>System version/ECU Variants</span>                                 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="page-header-breadcrumb">
                                <ul class="breadcrumb-title">
                                    <li class="breadcrumb-item">
                                        <a href="index.html"> <i class="icofont icofont-home"></i> </a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="#!">System Owner</a> </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                               <div class="card-block marketing-card p-t-20 row">
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">  
                                            <s:url action="acb_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">     
                                                    <div class="card-block">
                                                        <span>ACB Version</span>
                                                        <span class="count"><s:property value="dashboard_result.acbversion_count"/></span>
                                                        <i class="icofont icofont-unity-hand text-c-red"></i>
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="sys_version.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>System Version</span></br>
                                                        <span class="count"><s:property value="dashboard_result.systemversion_count"/></span> 
                                                        <i class="icofont icofont-help-robot text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="sys_ecu.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>ECU Variants</span>
                                                        <span class="count"><s:property value="dashboard_result.variants_count"/></span>
                                                        <i class="icofont  icofont-package text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>                                            
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="sys_version_create.action" var="aURL" />
                                            <s:a href="%{aURL}">      
                                                <div class="card-block">
                                                    <span>System Owner Activity</span>
                                                    <span class="count"> + </span>
                                                    <i class="icofont icofont-plus-square text-c-red"></i> 
                                                    <div class="clearfix"></div>
                                                </div>
                                            </s:a>    
                                        </div>
                                    </div>
                                   
                                </div>
                            </div>
                        </div>
                                            
<%@include file="footer.jsp" %>
<script>
//        var app = angular.module('angularTable', []);
        app.controller('MyCtrl',function($scope, $http)
        {      
//            var data = JSON.parse("<s:property value="count"/>".replace(/&quot;/g,'"'));
////            alert(JSON.stringify(data));
//            $scope.count ={"ivnversion_count":data['ivnversion_count'],
//                           "ecucount":data['ecucount']
//                          } ;
        });
</script> 
     
</body>

</html>