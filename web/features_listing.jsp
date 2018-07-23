<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content"ng-app="angularTable" ng-controller="RecordCtrl as Demo">
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
                                                        <span>Features Listing</span>
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
                                                            <a class="waves-effect waves-light modal-trigger" href="#modal-product-form" ng-click="showCreateForm()">Add Feature</a> 
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
                                                                    
                                                                    <th ng-click="sort('version')" class="text-center">Domain</th>
                                                                    <th ng-click="sort('vehicle')" class="text-center">Features</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllDomain_and_Features()">
                                                                    
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                           
                                                                                {{record.domain}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{record.fea}}
                                                                                
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
                                                    <h5 class="text-c-red m-b-25">Add Feature <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>

                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="domain" type="text" class="validate col-lg-12" id="form-name" placeholder="Domain"/>
                                                        </div>
                                                         <div ng-repeat="data in Demo.data">              
                                                            <div class="form-group">
                                                            <!--<label for="name">Feature</label>-->
                                                            <input ng-model="data.feature" type="text" class="validate  col-lg-12" id="form-name" placeholder="Feature"/>
                                                            </div>
                                                            <div class="form-group">
                                                            <textarea ng-model="data.description" type="text" class="validate materialize-textarea  col-lg-12" placeholder="Description"></textarea>
                                                            <!--<label for="description">Description</label>-->
                                                            </div>
                                                             <p class="text-right">
                                                             <a href="" ng-click="Demo.data.splice($index,1)">
                                                                 <i class="icofont icofont-ui-close text-c-red "></i>
                                                             </a>
                                                             </p>
                                                        </div>

                                                        <p class="text-right">
                                                            <a href="" ng-click="Demo.data[Demo.data.length] = {}">
                                                                 <i class="icofont icofont-ui-add text-c-green"></i>
                                                             </a>
                                                        </p>
                                                        <div class="input-field text-right">
                                                            <a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>
                                                        </div>
                                                </div>
                                            </div>
                                            
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http)
        {
            
               this.data = [];
//              $scope.features = [
//                { fid:'1',domain:'d1',fea: 'feature1'},
//                { fid:'2',domain:'d1',fea: 'feature2'},
//                { fid:'3',domain:'d2',fea: 'feature3'},
//                { fid:'4',domain:'d2',fea: 'feature4'}
//              ]; 
          
            $scope.getAllDomain_and_Features = function(){
//                alert("getall");
                $http.get("features_listing.action").then(function(response, data, status, headers, config){
//                        alert("<s:property value="result_data_obj"/>");
                        var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
                        $scope.features = data;
                });
            }
                    
                    
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