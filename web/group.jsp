<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content" ng-controller="RecordCtrl1 as Demo">
                        <div class="pcoded-inner-content">
                            <div class="main-body">

                                <div class="page-wrapper">

                                    <div class="page-header card">
                                        <div class="row align-items-end">
                                            <div class="col-lg-8">
                                                <div class="page-header-title">
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>Admin</h4>
                                                        <span>Group Listing</span>
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
                                                            <s:url action="admin.action" var="aURL" />
                                                            <s:a href="%{aURL}">
                                                                Back
                                                            </s:a>
                                                        </li>
                                                        <li class="breadcrumb-item">
                                                            <a class="waves-effect waves-light modal-trigger" href="#modal-product-form" ng-click="showCreateForm()">Add Group</a> 
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
                                                        <table st-table="rowCollection" class="table table-striped" ng-init="getAll()">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('id')" class="">No</th>
                                                                    <th ng-click="sort('groupname')" class="">Group</th>
                                                                    <th ng-click="sort('route')" class="">Route</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllDomain_and_Features()">
                                                                    
                                                                    <tr dir-paginate="record in names|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="">
                                                                           
                                                                                {{record.id}}
                                                                                
                                                                        </td>
                                                                        <td class="">
                                                                           
                                                                                {{record.group_name}}
                                                                                
                                                                        </td>
                                                                        <td class="">
                                                                           
                                                                                {{record.route_pages}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{record.status}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a>
                                                                                
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
                                            <div id="modal-product-form" class="modal">
                                                <div class="modal-content">
                                                    <h5 class="text-c-red m-b-25">Add Group<a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>

                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="data.group_name" type="text" class="validate col-lg-12" id="form-name" placeholder="Group Name"/>
                                                        </div>                                                                      
                                                        <div class="form-group">
                                                            <!--<label for="name">Feature</label>-->
                                                            <input ng-model="data.pages" type="text" class="validate  col-lg-12" id="form-name" placeholder="Route Pages"/>
                                                        </div>
                                                        <div class="form-group">
                                                            
                                                            <label for="status" style="vertical-align:middle">Status:</label>
                                                            <label class="switch m-r-50"  style="vertical-align:middle">
                                                                <input type="checkbox" ng-model="data.status">
                                                                <span class="slider round"></span>
                                                             </label>
                                                            
                                                            <label for="name">Is Admin</label>
                                                            <input ng-model="data.admin" style="width:auto"type="checkbox" class="validate  col-lg-12" id="form-name" placeholder="Route Pages"/>
                                                        </div>
                                                        <div class="input-field text-right">
                                                            <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em btn-primary" ng-click="creategroup()" ng-mousedown='doSubmit=true' name="add">Save</button>
                                                            <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                                        </div>
                                                </div>
                                            </div>
                                            
<%@include file="footer.jsp" %>
<!--<script src="js/lib/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http)
        {
            
               this.data = [];
//              $scope.features = [
//                { fid:'1',domain:'d1',fea: 'feature1'},
//                { fid:'2',domain:'d1',fea: 'feature2'},
//                { fid:'3',domain:'d2',fea: 'feature3'},
//                { fid:'4',domain:'d2',fea: 'feature4'}
//              ]; 
                        $scope.getAll = function () {
//                                    $http.get("getAllUserGroup.action")
//                                            .then(function (data,status, headers, config) {
//                                        $scope.names = data.data.userGroupList;
                            $http.get("getAllUserGroup.action").then(function(response, data, status, headers, config){
//                                            console.log(JSON.stringify(response.data.result_data));
//alert(JSON.stringify();
//                                            var data = response.data.result_data;
//                                        alert(JSON.stringify(response));
//                                        alert("<s:property value="result_data_obj"/>");
                            var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
                            $scope.names = data;
                            });
//                                        alert(JSON.stringify($scope.names));
                    }
            $scope.creategroup = function (event) 
            {
                 if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false; 
//                $http.post("groupCreation.action", {
//                        'usergroup': $scope.usergroup
//                    })
//                        .then(function (data, status, headers, config) {
//                            var stat = JSON.stringify(data.data.maps.status);
//                            stat = stat.replace(/^"|"$/g, '');
//                            var suc = "success";
//                            var suc1="error";
//                            var suc2="empty";
//                            if (suc == stat)
//                            {
//                                alert("Success! Group Created");
//                            } else if(suc1 == stat)
//                            {
//                                alert("Error!");
//                            }
//                            else if(suc2 == stat)
//                            {
//                                alert("Enter the group name!");
//                            }
//                            else
//                            {
//                                alert("group name already exists!");
//                            }
//                        });
//                    alert(JSON.stringify($scope.data));
//                    var feature_and_domain_data = {};
                    if($scope.Demo.data.length > 0)
                    {
                    //                        alert(JSON.stringify(feature_and_domain_data));
                           $http({
                           url : 'groupCreation',
                           method : "POST",
                           data : $scope.data
                           })
                           .then(function (data, status, headers, config)
                           {
                                result_data = data.data.domainFeatures_result;
                                //result_data =  result_data.slice(1, -1);
                                for(var i = 0; i < result_data.length; i++) 
                                {
                                    $scope.features.push({fid:result_data[i].fid,fea:result_data[i].fea,domain:result_data[i].domain});
                                }
                           });
                           $('#modal-product-form').closeModal();
                    }
                    else
                    {
                        alert("Please create atleast one features");
                    }
            }
//            $scope.getAllDomain_and_Features = function(){
////                alert("getall");
//                $http.get("features_listing.action").then(function(response, data, status, headers, config){
////                        alert("<s:property value="result_data_obj"/>");
//                        var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//                        $scope.features = data;
//                });
//            }
                    
                    
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            $scope.removeRow = function(fid)
            {				
		var index = -1;		
		var comArr = eval( $scope.features );
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].fid === fid ) 
                    {
                        index = i;
                        break;
                    }
		}
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
                
		$scope.features.splice( index, 1 );		
            };
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

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            