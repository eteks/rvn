<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="pcoded-content" ng-controller="MyCtrl as Demo" ng-init="getAllCount()">    
    <div class="pcoded-inner-content">
        <div class="main-body">

            <div class="page-wrapper">
                <div class="page-header card">
                    <div class="row align-items-end">
                        <div class="col-lg-8">
                            <div class="page-header-title">
                                <i class="icofont icofont-automation bg-c-red"></i>
                                <div class="d-inline">
                                    <h4>IVN Engineer</h4>
                                    <span>Vehicle, CAN, LIN, H/W  and IVN version</span>                                 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="page-header-breadcrumb">
                                <ul class="breadcrumb-title">
                                    <li class="breadcrumb-item">
                                        <a href="index.html"> <i class="icofont icofont-home"></i> </a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="#!">IVN</a> </li>
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
                                            <s:url action="ivn_version_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                    <div class="card-block">
                                                        <span>IVN Version</span></br>
                                                        <span class="count"><s:property value="dashboard_result.ivnversion_count"/></span>      
                                                        <i class="icofont  icofont-star-shape text-c-red"></i> 
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="hw_ecu_listing.action" var="aURL" />
                                            <s:a href="%{aURL}">   
                                                <div class="card-block">
                                                    <span>Network</span>
                                                    <span class="count"><s:property value="dashboard_result.network_count"/></span>  
                                                    <i class="icofont icofont-ui-pointer text-c-red"></i>
                                                    
                                                    <div class="clearfix"></div>
                                                </div>
                                            </s:a>                                             
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">  
                                            <s:url action="ivn_signals.action" var="aURL" />
                                            <s:a href="%{aURL}">     
                                                    <div class="card-block">
                                                        <span>Signals</span>
                                                        <span class="count"><s:property value="dashboard_result.signal_count"/></span>
                                                        <i class="icofont icofont-weed text-c-red"></i> 
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </s:a>   
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <s:url action="ivn_version_create.action" var="aURL" />
                                            <s:a href="%{aURL}">      
                                                <div class="card-block">
                                                    <span>IVN Version Create</span>
                                                    <span class="count"> + </span>
                                                    <i class="icofont icofont-ui-rate-add text-c-red"></i>
                                                   
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