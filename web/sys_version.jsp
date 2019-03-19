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
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>System Owner</h4>
                                                        <span>Version</span>
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
                                                            <s:url action="sys_engg.action" var="aURL" />
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
                                                                    
                                                                    <th ng-click="sort('system_versionname')" class="text-center">System Version</th>
                                                                    <th ng-click="sort('vehicle_versionname')" class="text-center">Vehicle Version</th>
                                                                    <th ng-click="sort('vehiclename')" class="text-center">Vehicle</th>
                                                                    <th ng-click="sort('acb_versionname')" class="text-center">ACB</th>
                                                                    <th ng-click="sort('features')" class="text-center">Features</th>
                                                                    <th ng-click="sort('variants')" class="text-center">Variants</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('flag')" class="text-center">Version Type</th>
                                                                    <th ng-click="sort('created_date')" class="text-center">Created Date</th>
                                                                    <th ng-click="sort('modified_date')" class="text-center">Modified Date</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">                                                                           
                                                                                {{record.system_versionname}}                                                                                
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
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.features"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Features:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="fea in (record.features | customSplitString)"><i class="icofont icofont-hand-right"></i> {{fea}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>                                                                      
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.variants"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Variants:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="var in (record.variants | customSplitString)"><i class="icofont icofont-hand-right"></i> {{var}}</li>
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
                                                                        <td class="text-center">{{record.created_date}}</td>
                                                                        <td class="text-center">{{record.modified_date}}</td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="edit" ng-if="record.status === false">Edit</button>

                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-danger btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="view" ng-if="record.status === true">view</button>
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="delete($event)" name="delete" ng-if="record.delBut === 1">Delete</button>
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
//             $scope.records = 
//                     [{"id":4,"system_versionname":"4.0","vehicle_versionname":"1.0","vehiclename":"veh1","acb_versionname":"1.4","features":"f2","variants":"ecu1","flag":true,"status":true},
//                      {"id":3,"system_versionname":"3.0","vehicle_versionname":"1.0","vehiclename":"veh1","acb_versionname":"1.4","features":"f2","variants":"ecu1","flag":true,"status":true}
//                     ];
            $scope.records = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//            alert(JSON.stringify($scope.records));
            $scope.View_and_edit = function(event){
                var id = event.target.attributes['data-id'].value;
                var name = event.target.name;
                $window.open("sys_version_create.action?id="+id+"&action="+name,"_self"); //  
            }
            
            $scope.delete = function(event){
//                alert("view_and_edit");
//                alert(event.target.id);
                var id = event.target.attributes['data-id'].value;
                var data = {id : id};
                $http({
                url : 'deletesysversion',
                method : "POST",
                data : data
                })
                .then(function (data, status, headers, config){
                     if(data.data.dlStatus.status === 1){
                         alert("System Version Deleted Succesfully");
                         $window.location.reload();
                    }else{
                        alert("Error while deleting System Version");
                    }
            });
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