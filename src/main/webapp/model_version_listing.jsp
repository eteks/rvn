<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content" ng-controller="RecordCtrl1">
                        <div class="pcoded-inner-content">
                            <div class="main-body">

                                <div class="page-wrapper">

                                    <div class="page-header card">
                                        <div class="row align-items-end">
                                            <div class="col-lg-8">
                                                <div class="page-header-title">
                                                    <i class="icofont icofont-car-alt-2 bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>Model version</h4>
                                                        <span>Model Listing</span>
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
                                                                    
                                                                    <th ng-click="sort('model_versionname')" class="text-center">Model Version</th>
                                                                    <th ng-click="sort('vehicle_versionname')" class="text-center">Vehicle Version</th>
                                                                    <th ng-click="sort('vehiclename')" class="text-center">Vehicle</th>
                                                                    <th ng-click="sort('acb_versionname')" class="text-center">ACB</th>
                                                                    <th ng-click="sort('models')" class="text-center">Models</th>
                                                                    <th ng-click="sort('ecu')" class="text-center">ECU</th>                                                   
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('flag')" class="text-center">Version Type</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">                                                                           
                                                                                {{record.model_versionname}}                                                                                
                                                                        </td>
                                                                        <td class="text-center">                                                                           
                                                                                {{record.vehicle_versionname}}                                                                                
                                                                        </td>
                                                                        <td class="text-center">                                                                           
                                                                                {{record.vehiclename}}                                                                                
                                                                        </td>
                                                                        <td class="text-center">                                                                           
                                                                                {{record.acb_versionname}}                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Models:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.models | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>     
                                                                        </td>
                                                                        <td class="text-center">
                                                                           <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>ECU:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="ecu in (record.ecu | customSplitString)"><i class="icofont icofont-hand-right"></i> {{ecu}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>     
                                                                        </td>                                                                        
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === true">Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === false">Inactive
                                                                            </button>

                                                                        </td>
                                                                        <td class="text-center">                                                                             
                                                                            <span ng-if="record.flag === false">Temporary</span>
                                                                            <span ng-if="record.flag === true">Permanent</span>
                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="edit" ng-if="record.status === false">Edit</button>

                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-danger btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="view" ng-if="record.status === true">view</button>

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

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
//            $scope.records = 
//                [{"id":2,"model_versionname":"2.0","vehicle_versionname":"1.0","vehiclename":"veh1","acb_versionname":"1.4","models":"v12,v13,v11","ecu":"ecu2,ecu1","flag":false,"status":false},
//                   {"id":1,"model_versionname":"1.0","vehicle_versionname":"1.0","vehiclename":"veh1","acb_versionname":"1.4","models":"v12,v11,v13","ecu":"ecu2,ecu1","flag":true,"status":false}
//            ];
              $scope.records = JSON.parse("<s:property value="listing_result_data_obj"/>".replace(/&quot;/g,'"'));
//              alert(JSON.stringify($scope.records));
              
              $scope.sort = function(keyname)
              {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
              }

              $scope.View_and_edit = function(event){
                var id = event.target.attributes['data-id'].value;
                var name = event.target.name;
                $window.open("model_version.action?id="+id+"&action="+name,"_self"); //  
               }
        });
        app.filter('customSplitString', function() 
        {
            return function(input) 
            {
                if(input !=undefined){
                    var arr = input.split(',');
                    return arr;
                }                
            };     
        });
       </script>   
</body>

</html>                                            