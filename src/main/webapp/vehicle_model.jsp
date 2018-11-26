<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="pcoded-content"ng-app="angularTable" ng-controller="RecordCtrl1">
                        <div class="pcoded-inner-content">
                            <div class="main-body">

                                <div class="page-wrapper">

                                    <div class="page-header card">
                                        <div class="row align-items-end">
                                            <div class="col-lg-8">
                                                <div class="page-header-title">
                                                    <i class="icofont icofont-car-alt-2 bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>Vehicle version</h4>
                                                        <span>Model listing</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="page-header-breadcrumb">
                                                    <ul class="breadcrumb-title">
                                        <li class="breadcrumb-item">
                                                <a href="index.html"> <i class="icofont icofont-home"></i></a>
                                        </li>
                                        <li class="breadcrumb-item">
                                            <s:url action="ivn_supervisor.action" var="aURL" />
                                            <s:a href="%{aURL}"> 
                                               Back
                                            </s:a>          
                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="page-body">
                                        <div class="row">

                                            <!-- Marketing Start -->
                                            <div class="col-md-12 model_listing">
                                                <div class="card">
                                                    <div class="card-block marketing-card p-t-0">
                                                        <form class="form-inline mt-3">
                                                            <div class="form-group">
                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                            </div>
                                                        </form>
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('vehicle')" class="text-center">Models</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                   

                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllVehicleModel()">
                                                                    
                                                                <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">

                                                                   <td class="text-center">

                                                                        <div class="mytooltip tooltip-effect-4">
                                                                                <div class="tooltip-item blink">{{record.modelname}}</div>
                                                                                <div class="tooltip-content clearfix">
                                                                                    <div class="tooltip-text">
                                                                                        <p>Version : {{record.versionname}}</p></br>
                                                                                        <p>Vehicle : {{record.vehiclename}}</p>
                                                                                    </div>
                                                                                </div>
                                                                        </div>


                                                                    </td>
                                                                   <td class="text-center"> 

                                                                <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === true">Active</button>

                                                                <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === false">Inactive</button>

                                                                    </td>


                                                                </tr>

                                                                </tbody>
                                                            </table>
                                                            <dir-pagination-controls
                                                                max-size="5"
                                                                direction-links="true"
                                                                boundary-links="true" >
                                                            </dir-pagination-controls>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
                                            
<%@include file="footer.jsp" %>

  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http)
        {
            

//            $scope.records = [
//                        { version: '1.0', vehicle: 'Scorpio', status: 'Active',mod: 'm1'},
//                        { version: '2.0', vehicle: 'Xuv', status: 'Inactive',mod: 'm2'},
//                        { version: '3.0', vehicle: 'Scorpio', status: 'Active',mod: 'm3'},
//                        { version: '1.0', vehicle: 'Scorpio', status: 'Active',mod: 'm4'},
//                        { version: '4.0', vehicle: 'Xuv', status: 'Inactive',mod: 'm5'},
//                        { version: '5.0', vehicle: 'Scorpio', status: 'Active',mod: 'm6'},
//                        { version: '6.0', vehicle: 'XUV', status: 'Active',mod: 'm7'}
//                    ];
                    
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;//set the sortKey to the param passed
                $scope.reverse = !$scope.reverse;//if true make it false and vice versa
            }
            // read all vehicle model
            $scope.getAllVehicleModel = function(){
//                alert("getall");
                $http.get("vehiclemodel_listing.action").then(function(response, data, status, headers, config){

                    var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
//                  alert(JSON.stringify(data));
                    $scope.records = data;
                });
            }
        });

    
    </script>   
</body>

</html>                                            