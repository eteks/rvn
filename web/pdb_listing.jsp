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
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>PDB Owner</h4>
                                                        <span>PDB Version </span>
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
                                                            <s:url action="pdb_owner.action" var="aURL" />
                                                            <s:a href="%{aURL}">
                                                                Back
                                                            </s:a>
                                                        </li>
                                                        <li class="breadcrumb-item">
                                                            <s:url action="pdb_assign.action" var="aURL" />
                                                            <s:a href="%{aURL}">
                                                                Create Action
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
                                                                    <th ng-click="sort('model')" class="text-center">Model</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllVehicle()">
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                           
                                                                                {{record.version}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{record.vehicle}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Models:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.model | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>     
                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === 'Active'">Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === 'Inactive'">Inactive
                                                                            </button>

                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round"  ng-click="Edit($index)" ng-if="record.status === 'Inactive'">Edit</button>

                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-danger btn-round"  ng-click="View($index)" ng-if="record.status === 'Active'">view</button>

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

  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http)
        {
            

             $scope.records = [
                        { version: '1.0', vehicle: 'Scorpio',model:'m1,m2,m3', status: 'Active'},
                        { version: '2.0', vehicle: 'Xuv',model:'m2,m5,m6', status: 'Inactive'},
                        { version: '3.0', vehicle: 'Scorpio',model:'m2,m1,m3', status: 'Active'},
                        { version: '1.0', vehicle: 'Scorpio',model:'m6,m4,m8', status: 'Active'},
                        { version: '4.0', vehicle: 'Xuv',model:'m5,m6,m9', status: 'Inactive'},
                        { version: '5.0', vehicle: 'Scorpio',model:'m6,m2,m4', status: 'Active'},
                        { version: '6.0', vehicle: 'XUV',model:'m4,m5,m7', status: 'Active'}
                    ];
                    
                  
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            // read all vehicle
            $scope.getAllVehicle = function(){
//                alert("getall");
                $http.get("vehicleversion_listing.action").then(function(response, data, status, headers, config){

                        var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
//                        alert(JSON.stringify(data));
                        $scope.records = data;
                });
            }
        });

    
    </script>   
</body>

</html>                                            