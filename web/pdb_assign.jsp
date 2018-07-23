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
                                                         <div class="row p-t-30">
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
                                                                <select ng-model="data.pdbversion" ng-change="LoadPDBPreviousVersion()">
                                                                    <s:iterator value="pdbversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="pdb_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                             
                                                            <a class="feature_add modal-trigger" href="#modal-feature-list">
                                                                <i class="icofont icofont-ship-wheel text-c-red"></i>
                                                                Feature List
                                                            </a>
                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()">Add Feature</a>
                                                            
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
                                                                
                                                                <tbody>
                                                                <form ng-model="myform">    
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                           {{record.domain}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a> {{record.fea}}
                                                                        </td>
                                                                        <td class="text-center" ng-repeat="i in records"> 
                                                                            
                                                                            <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'y')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="y" >
                                                                                <span class="checkmark c_b_g">                                                                                    
                                                                                </span>
                                                                                <span class="tooltip-content2 c_b_g">yes</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'n')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="n">
                                                                                <span class="checkmark c_b_r"></span>
                                                                                <span class="tooltip-content2 c_b_r">no</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'o')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="o">    
                                                                                <span class="checkmark c_b_b"></span>
                                                                                <span class="tooltip-content2 c_b_b">optional</span>
                                                                              </label>
                                                                                
                                                                        </td>
                                                                    </tr>
                                                                </form>
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
            <!--<form class=""  name="myForm">-->
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
                                <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature_and_domain()" ng-mousedown='doSubmit=true' name="add">Add</button>
                            </div>
                    </div>
                </div>
                <!-- floating button for creating product -->
            <!--</form>-->
            
            <div id="modal-feature-list" class="modal modal-feature-list">
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
               
                            
                             
                <label for="status">Status:</label>

                <label class="switch float-right">
                    <input type="checkbox" ng-model="data.status">
                    <span class="slider round"></span>
                 </label>
                
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createpdbversion($event)" name="save">Save</button>
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createpdbversion($event)" name="submit">Submit</button>
                
            </div>  
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http, $window)
        {
                  
//       $scope.records = [
//                        { mod: 'm1'},
//                        { mod: 'm2'},
//                        { mod: 'm3'},
//                        { mod: 'm4'}
//                    ];
            this.data=[];
            $scope.features = [];
            $scope.list = [];
            
            var features_list = JSON.parse("<s:property value="featureslist_result_obj"/>".replace(/&quot;/g,'"'));
            $scope.features_list = features_list;
                     
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
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
//                    var vm_id =[];
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
                             "vehicle_mapping_id":data.vehicle_mapping_id.split(","),
                         });                         
//                         vm_id.push({"vehicle_mapping_id": data.vehicle_mapping_id.split(",")});
//                         angular.forEach(data.modelname.split(","), function(value, key) {
//                            $scope.model_list.push({
//                             "vehicle_id":data.vehicle_id,
//                             "mod":value,
//                            }); 
//                         })
                    }
//                    alert(JSON.stringify($scope.model_list));
                });
            };
            $scope.LoadVehicleModels= function(selected_vehicleid)
            {
                $scope.records = [];
                for(var i = 0; i < $scope.model_list.length; i++) 
                {
                   var data = $scope.model_list[i];
                   if(data.vehicle_id == selected_vehicleid){
//                       alert(data.vehicle_mapping_id);
                        angular.forEach(data.mod, function(value, key) {
                            $scope.records.push({
                             "mod":value,
                             "vehicle_model_mapping_id":data.vehicle_mapping_id[key],
                            }); 
                        })
                   }
                }
//                alert(JSON.stringify($scope.records));
            }
            $scope.createfeature_and_domain = function (event) 
            {        
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false; 
                var feature_and_domain_data = {};
                feature_and_domain_data['domain_name'] = $scope.domain;
                feature_and_domain_data['features_and_description'] = $scope.Demo.data;
                if($scope.Demo.data.length > 0)
                {
                //                        alert(JSON.stringify(feature_and_domain_data));
                       $http({
                       url : 'createfeature_and_domain',
                       method : "POST",
                       data : feature_and_domain_data
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
            
            $scope.createpdbversion = function (event) 
            {           
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false;         
//                alert(event);
//                $scope.list.push(this.text);
//                alert(JSON.stringify($scope.list));
                var data = {};
                data['pdbversion'] = $scope.data;
                data['pdbdata_list'] = $scope.list;
                data['button_type'] = event.target.name;
                if($scope.list.length > 0){
                    $http({
                        url : 'createpdbversion',
                        method : "POST",
                        data : data,
                        })
                        .then(function (data, status, headers, config){               
                              alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                              $window.open("pdb_assign.action","_self"); //                alert(data.maps);
    //            //                Materialize.toast(data['maps']["status"], 4000);
                    });
                }
                else{
                    alert("Please fill the domain and feature status to create PDB version");
                }
            };
            $scope.radiovalue = function(vmm_id,dfm_id,status)
            {		
                //alert();
                if($scope.list.length === 0)
                {
                    $scope.list.push({vmm_id:vmm_id,dfm_id:dfm_id,status:status});
                }
                else
                {
                    var temp=0;
                    for(var i=0; i<$scope.list.length; i++)
                    {
                        if(($scope.list[i].vmm_id === vmm_id) && ($scope.list[i].dfm_id === dfm_id))
                        {
                            $scope.list[i].status=status;
                            temp=1;
                        }
                        
                    }
                    if(temp==0)
                    {
                        $scope.list.push({vmm_id:vmm_id,dfm_id:dfm_id,status:status});
                    }
                }
                
            };
            $scope.LoadPDBPreviousVersion = function() 
            {
//                alert("LoadPDBPreviousVersion");
//                alert($scope.data.pdbversion);
                $http({
                    url : 'loadpdbpreviousvehicleversion_data',
                    method : "POST",
                    data : {"pdbversion_id":$scope.data.pdbversion}
                })
                .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.pdb_map_result));
                    var result_data = response.data.pdb_map_result;
                    var vehicledetail=result_data.vehicledetail_list[0].modelname;
                    var pdbdetail=result_data.featuredetail_list;
//                        vehicledetail=vehicledetail.slice(1, -1); 
                        for(var i=0; i<pdbdetail.length; i++)
                        {
                            $scope.features.push({fid:pdbdetail[i].dfm_id,fea:pdbdetail[i].featurename,domain:pdbdetail[i].domainname});
                            $scope.list.push({vmm_id:pdbdetail[i].vmm_id,dfm_id:pdbdetail[i].dfm_id,status:pdbdetail[i].status});
                        }
                        
//                   $scope.Demo.data = [{"vehiclename":"sasdsa","modelname":["dfsd","jhkjk","hkkjhk","kljk"],"versionname":"4.0","status":false}];
                });
            };
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            