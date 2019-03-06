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
                                <i class="icofont icofont-car-alt-2 bg-c-red"></i>
                                <div class="d-inline">
                                    <h4>IVN Supervisor</h4>
                                    <span>Vehicle and Models</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="page-header-breadcrumb">
                                <ul class="breadcrumb-title">
                                    <li class="breadcrumb-item">
                                        <a href="index.html"> <i class="icofont icofont-home"></i> </a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="#!">Vehicle</a></li>
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
                                            <s:url action="vehicleversion_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>Vehicle Versions</span></br>
                                                        <span class="count"><s:property value="dashboard_result.vehicleversion_count"/></span>
                                                        <i class="icofont  icofont-car-alt-1 text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="vehicle_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>Vehicles</span>
                                                        <span class="count"><s:property value="dashboard_result.vehiclecount"/></span>
                                                        <i class="icofont  icofont-car-alt-2 text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>
                                            </a>    
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">  
                                            <s:url action="vehiclemodel_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">     
                                                    <div class="card-block">
                                                        <span>Models</span>
                                                        <span class="count"><s:property value="dashboard_result.modelcount"/></span>
                                                        <i class="icofont icofont-racings-wheel text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="vehicle_add.action" var="aURL" />
                                            <s:a href="%{aURL}">      
                                                <div class="card-block">
                                                    <span> Create versions </span>
                                                    <span class="count"> + </span>
                                                    <i class="icofont icofont-plus-square text-c-red"></i>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </s:a>    
                                        </div>
                                    </div>
                                   
                                    <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="model_version.action" var="aURL" />
                                            <s:a href="%{aURL}">      
                                                <div class="card-block">
                                                    <span>Model Config </span>
                                                    <span class="count"> + </span>
                                                    <i class="icofont icofont-pixels text-c-red"></i>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </s:a>    
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="model_version_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>Model Config Listing</span></br>
                                                        <span class="count"><s:property value="dashboard_result.modelversion_count"/></span> 
                                                        <i class="icofont  icofont-numbered text-c-red"></i>  
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
//            alert("MyCtrl");
//            $scope.getAllCount = function(){
//                $http.get("dashboarddata.action").then(function(data, status, headers, config){
//                    var data = JSON.parse("<s:property value="count"/>".replace(/&quot;/g,'"'));
//                    $scope.vehicleversion_count = data['vehicleversion_count'];
//                    $scope.vehiclecount = data['vehiclecount'];
//                    $scope.modelcount = data['modelcount'];
//                });
//            }
        });
 </script> 
     
</body>

</html>