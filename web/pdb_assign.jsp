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
                                                    <i class="icofont icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>PDB Owner</h4>
                                                        <span>PDB Version Creation</span>
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
                                                        <form class=""  name="myForm" ng-submit="vehicleversionFunc()">                                 <div class="row p-t-30">
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">Vehicle version :</label>
                                                                <select ng-model="data.vehicleversion" ng-change="LoadSelectedVehicleVersionData()">
                                                                    <s:iterator value="vehicleversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">Vehicle:</label>
                                                                <select ng-hide="data.vehicleversion"></select>
                                                                <select ng-change="LoadVehicleModels(data.vehiclename)" ng-if="vehicle_list.length > 0" ng-model="data.vehiclename">
                                                                        <option value="{{veh.vehicle_id}}" ng-repeat="veh in vehicle_list">{{veh.vehiclename}}</option>                                                                    
                                                                </select>
                                                            </div>
                                                               <div class="form-group col-md-3">
                                                                <label for="vehicle">pdb version :</label>
                                                                <select ng-model="data.vehicleversion" ng-change="LoadPreviousVersion()">
                                                                    <s:iterator value="vehicleversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                                <a class="feature_add modal-trigger" href="#modal-feature-list">
                                                                    <i class="icofont icofont-ship-wheel text-c-red"></i>
                                                                    Feature List
                                                                </a>
                                                        </div>   
                                                            
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th class="text-center">Domain</th>
                                                                    <th class="text-center">Features</th>
                                                                    <th class="text-center" ng-repeat="i in records">
                                                                        {{i.mod}}
                                                                    </th>

                                                                </tr>
                                                                </thead>
                                                                
                                                                <tbody ng-init="getAllVehicle()">
                                                                    
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                           {{record.domain}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a> {{record.fea}}
                                                                        </td>
                                                                        <td class="text-center" ng-repeat="i in records"> 
                                                                            <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" name="f{{$index +1}}{{record.fid}}"  value="y" checked>
                                                                                <span class="checkmark c_b_g">
                                                                                    
                                                                                </span>
                                                                                <span class="tooltip-content2 c_b_g">yes</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" name="f{{$index +1}}{{record.fid}}" value="n">
                                                                                <span class="checkmark c_b_r"></span>
                                                                                <span class="tooltip-content2 c_b_r">no</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" name="f{{$index +1}}{{record.fid}}" value="o">    
                                                                                <span class="checkmark c_b_b"></span>
                                                                                <span class="tooltip-content2 c_b_b">optional</span>
                                                                              </label>
                                                                            
                                                                                
                                                                        </td>
                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </form>
                                                        <dir-pagination-controls
                                                                max-size="5"
                                                                direction-links="true"
                                                                boundary-links="true" >
                                                        </dir-pagination-controls>                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
                                             <!-- modal for for creating new product -->
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
            <!-- floating button for creating product -->
            
            
            <div id="modal-feature-list" class="modal">
                <div class="modal-content">
                    <h5 class="text-c-red m-b-10">Feature <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    
                    <ul>
                        <li ng-repeat="fil in features_list">
                            <a href="#" class="text-c-green" ng-click="add_feature_tab(fil.fid)">
                                <i class="icofont icofont-ui-add"></i></a>&nbsp;({{fil.domain}})&nbsp;{{fil.fea}}
                        </li>
                    </ul>
                    
                </div>
            </div>
            
            <div class="text-center">
               
                <a class="waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()">Add Feature</a>            
            
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="submit_vehicleversion($event)" name="save">Save</button>
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="submit_vehicleversion($event)" name="submit">Submit</button>
            </div>
                        
            
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http)
        {
                  
//       $scope.records = [
//                        { mod: 'm1'},
//                        { mod: 'm2'},
//                        { mod: 'm3'},
//                        { mod: 'm4'}
//                    ];
            this.data=[];
        $scope.features = [
              { fid:'1',domain:'d1',fea: 'feature1'},
              { fid:'2',domain:'d1',fea: 'feature2'},
              { fid:'3',domain:'d2',fea: 'feature3'},
              { fid:'4',domain:'d2',fea: 'feature4'}
          ]; 
        
        $scope.features_list = [
              { fid:'5',domain:'d1',fea: 'feature5'},
              { fid:'6',domain:'d1',fea: 'feature6'},
              { fid:'7',domain:'d2',fea: 'feature7'},
              { fid:'8',domain:'d2',fea: 'feature8'},
              { fid:'9',domain:'d1',fea: 'feature9'},
              { fid:'10',domain:'d1',fea: 'feature10'},
              { fid:'11',domain:'d2',fea: 'feature11'},
              { fid:'12',domain:'d2',fea: 'feature12'},
          ];                
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            // read all vehicle
            $scope.getAllVehicle = function()
            {
//                alert("getall");
                $http.get("vehicleversion_listing.action").then(function(response, data, status, headers, config){

                        var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
//                        alert(JSON.stringify(data));
                        $scope.records = data;
                });
            }
            $scope.add_feature_tab = function(fid)
            {				
		var index = -1;		
		var comArr = eval( $scope.features_list );
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
                $scope.features.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
		$scope.features_list.splice( index, 1 );
                
            };
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
                $scope.features_list.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
		$scope.features.splice( index, 1 );		
            };
            $scope.LoadSelectedVehicleVersionData = function() 
            {
                $http({
                    url : 'loadpreviousvehicleversion_data',
                    method : "POST",
                    data : {"vehicleversion_id":$scope.data.vehicleversion}
                })
                .then(function (response, status, headers, config){
                    result_data = JSON.stringify(response.data.vehmod_map_result);
//                    alert(result_data);
                    $scope.vehicle_list = []; 
                    $scope.model_list = [];
                    $scope.vehicle_list.push({"vehicle_id":"","vehiclename":"Select"});
                    for(var i = 0; i < response.data.vehmod_map_result.length; i++) 
                    {
                         var data= response.data.vehmod_map_result[i];
                         $scope.vehicle_list.push({
                             "vehicle_id":data.vehicle_id,
                             "vehiclename":data.vehiclename,
                         });          
                         $scope.model_list.push({
                             "vehicle_id":data.vehicle_id,
                             "mod":data.modelname.split(","),
                             "model_id":data.model_id.split(","),
                         }); 
//                         angular.forEach(data.modelname.split(","), function(value, key) {
//                            $scope.model_list.push({
//                             "vehicle_id":data.vehicle_id,
//                             "mod":value,
//                            }); 
//                         })
                    }
                });
            };

            $scope.LoadVehicleModels= function() 
            {
                alert("LoadVehicleModels");

            $scope.LoadVehicleModels= function(selected_vehicleid) {
                $scope.records = [];
                for(var i = 0; i < $scope.model_list.length; i++) 
                {
                   var data = $scope.model_list[i];
                   if(data.vehicle_id == selected_vehicleid){
                        angular.forEach(data.mod, function(value, key) {
                            $scope.records.push({
                             "mod":value,
                            }); 
                        })
                   }
                }
            }
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            