<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="pcoded-content"ng-app="angularTable" ng-controller="RecordCtrl">
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
                                                        <span>Version Listing</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="page-header-breadcrumb">
                                                    <ul class="breadcrumb-title">
                                                        <li class="breadcrumb-item">
                                                            <a href="index.html"> <i class="icofont icofont-home"></i> </a>
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
                                            <div class="col-md-12">
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
                                                                    
                                                                    <th ng-click="sort('version')" class="text-center">Version</th>
                                                                    <th ng-click="sort('vehicle')" class="text-center">Vehicle</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>

                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllVehicleVersion()">
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                        <td class="text-center">{{record.versionname}}</td>
                                                                        <td class="text-center">
                                                                        <span class="mytooltip tooltip-effect-5">
                                                                            <span class="tooltip-item">{{record.vehiclename}}</span>
                                                                            <span class="tooltip-content clearfix">                                                                                 <!-- LOOP.-->
                                                                                
                                                                             <span class="tooltip-text bg-success">
                                                                                <h3>Models:</h3>
                                                                                <ul ng-repeat="model in (record.modelname | customSplitString)">
                                                                                    <li>{{model}}</li>
                                                                                </ul>
<!--                                                                            <ul ng-repeat="model in models">
                                                                                    <li>{{model.mod}}</li>
                                                                                </ul>-->
                                                                             </span>
                                                                            </span>
                                                                        </span>
                                                                        
                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === true">             Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === false">             Inactive
                                                                            </button>

                                                                        </td>
                                                                        <td class="text-center">
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round"  ng-click="Edit($index)" ng-if="record.status === false">Edit</button>

                                                                             <button class="btn btn-default btn-bg-c-blue btn-outline-danger btn-round"  ng-click="View($index)" ng-if="record.status === true">view</button>    

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

                                            
<%@include file="footer.jsp" %>

  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http)
        {            
//             $scope.records = [
//                        { version: '1.0', vehicle: 'Scorpio', status: 'Active'},
//                        { version: '2.0', vehicle: 'Xuv', status: 'Inactive'},
//                        { version: '3.0', vehicle: 'Scorpio', status: 'Active'},
//                        { version: '1.0', vehicle: 'Scorpio', status: 'Active'},
//                        { version: '4.0', vehicle: 'Xuv', status: 'Inactive'},
//                        { version: '5.0', vehicle: 'Scorpio', status: 'Active'},
//                        { version: '6.0', vehicle: 'XUV', status: 'Active'}
//                    ];
//                    
//            $scope.models = [
//                        { mod: 'm1'},
//                        { mod: 'm2'},
//                        { mod: 'm3'},
//                        { mod: 'm1'},
//                        { mod: 'm4'},
//                        { mod: 'm5'},
//                        { mod: 'm6'}
//                    ];
                    
            $scope.sort = function(keyname)
            {
//                alert("sort");
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            // read all vehicle version
            $scope.getAllVehicleVersion = function(){
//                alert("getall");
                $http.get("vehicleversion_listing.action").then(function(response, data, status, headers, config){
                        var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
//                        alert(JSON.stringify(data));
                        $scope.records = data;
                });
            };         
        });
        app.filter('customSplitString', function() {
            return function(input) {
                var arr = input.split(',');
                return arr;
            };     
        });

    
    </script>   
</body>

</html>                                            