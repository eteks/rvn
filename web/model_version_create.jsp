<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content"ng-app="angularTable" ng-controller="RecordCtrl1 as Demo">
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
                                                                <label for="vehicle">Model version :</label>
                                                                <select ng-model="data.acbversion" ng-change="LoadACBPreviousVersion()">
                                                                    <s:iterator value="acbversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="acb_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>                                
                                                           
                                                        </div>   
                                                        <div class="col-lg-12">
                                                            <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                    <tr>                            
                                                                        <th class="">Models</th>
                                                                        <th class="text-center" ng-repeat="i in ecu_list">
                                                                            {{i.listitem}}
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                    <tr dir-paginate="record in models|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        <td class="">
                                                                            <a class="modal-trigger" href="#modal-product-form" ng-click="assignstart(record.fid)">
                                                                                {{record.modelname}}
                                                                            </a>
                                                                        </td>
<!--                                                                    <td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                                                        <td class="text-center" ng-repeat="i in ecu_list">
                                                                            <select  ng-model="myOption" ng-change="">
                                                                                <option ng-repeat="x in (i.variants | customSplitString)">
                                                                                    {{x}}
                                                                                </option>
                                                                            </select>
                                                                        </td>
                                                                        
                                                                    </tr>
                                                                <tbody>
                                                                <form ng-model="myform">    
                                                                    
                                                                </form>
                                                                </tbody>
                                                            </table>
                                                            <dir-pagination-controls
                                                                    max-size="20"
                                                                    direction-links="true"
                                                                    boundary-links="true" >
                                                            </dir-pagination-controls>    
                                                        </div>                                               
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
            
            
            <div id="modal-product-form" class="modal signal_form">
                <div class="modal-content">
                   <h5 class="text-c-red m-b-10">Signal Assign<a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                  <form ng-submit="myFunc(my.fid,models,sigi.sid,sigo.sid)">
                   <label>ECU :</label> 
                   <select ng-model="ecu_tin" ng-change="">
                        <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                    </select>
                    <table st-table="rowCollection" class="table table-striped">
                        <thead>
                            <tr>                                                                    
                                <th class="">Type</th>
                                <th class="text-center" ng-repeat="i in models">
                                    {{i.modelname}}
                                </th>
                            </tr>
                        </thead>
                            <tr>
                                <td class="">
                                    <!--<select ng-model="valuetable" ng-change="">
                                        <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                                    </select>-->
                                    {{my.featurename}}
                                </td>
                                
                                <!--<td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                <td class="text-center" ng-repeat="x in (my.status | customSplitString) track by $index">
                                    {{x | uppercase}}
                                </td>
                                
                            </tr>
                            <tr>
                                <td><h5>Input</h5></td>
                            </tr>
                            <tr ng-repeat="data in Demo.data1">
                                <td>
                                    <a class="modal-trigger text-c-green" href="#modal-feature-list"  ng-click="op_signal(0,$index)">
                                        <i class="icofont icofont-ui-add"></i>
                                    </a>
                                    
                                    {{sigi[$index].listitem}}
                                </td>
                                <td ng-repeat="i in models">
                                    
                                    <select id="ip_{{i.vmm_id}}" ng-model="ip_$index" ng-change="">
                                        <option  ng-repeat="i in network" value="{{i.id}}" data-newtwork="{{i.ntype}}">{{i.listitem}}</option>                                                                            
                                    </select>
                                </td>
                                <td class="float-right">
                                    <a href="" ng-click="Demo.data1.splice($index,1)">
                                        <i class="icofont icofont-ui-close text-c-red "></i>
                                    </a>
                                 </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="" ng-click="Demo.data1[Demo.data1.length] = {}" class="text-c-green">
                                        <strong>Clone</strong>
                                     </a>
                                </td>
                            </tr>
                            <tr>
                                <td><h5>Output</h5></td>
                            </tr>
                            <tr ng-repeat="data in Demo.data2" >
                                <td>
                                    <a class="modal-trigger  text-c-green" href="#modal-feature-list" ng-click="op_signal(1,$index)">
                                        <i class="icofont icofont-ui-add"></i>
                                    </a>
                                    {{sigo[$index].listitem}}
                                </td>
                                <td ng-repeat="i in models">
                                    <select id="op_{{i.vmm_id}}" ng-model="op_$index" ng-change="">
                                        <option ng-repeat="i in network">{{i.listitem}}</option>                                                                            
                                    </select>
                                </td>
                                <td class="float-right">
                                    <a href="" ng-click="Demo.data2.splice($index,1)">
                                        <i class="icofont icofont-ui-close text-c-red "></i>
                                    </a>
                                 </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="" ng-click="Demo.data2[Demo.data2.length] = {}" class="text-c-green">
                                        <strong>Clone</strong>
                                     </a>
                                </td>
                            </tr>
                    </table>
                   <div style="clear:both"></div>
                            
                     <input class="btn btn-primary float-right" type="submit" value="submit">
                    <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                </form>
 
                </div>
            </div>
           
             <div id="modal-feature-list" class="modal modal-feature-list">
                <div class="modal-content">
                    
                    <h5 class="text-c-red m-b-10">Signals <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    <form class="form-inline mt-3">
                        <div class="form-group">
                            <input type="text" ng-model="search" class="form-control" placeholder="Search">
                        </div>
                    </form>
                    <ul>
                        <li ng-repeat="fil in signal_list|orderBy:sortKey:reverse|filter:search">
                            <a href="#" class="text-c-green" ng-click="add_signal_tab(cen.ip,cen.pri,fil.sid)">
                            <i class="icofont icofont-ui-add"></i></a>&nbsp;{{fil.listitem}}&nbsp;({{fil.description}})
                        </li>
                    </ul>
                    
                </div>
            </div>
            
            <div class="col-lg-12 text-right">
                <label for="status" style="vertical-align:middle">Status:</label>
                <label class="switch m-r-50"  style="vertical-align:middle">
                    <input type="checkbox" ng-model="data.status">
                    <span class="slider round"></span>
                 </label>
                
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createpdbversion($event)" name="save">Save</button>
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createpdbversion($event)" name="submit">Submit</button>
                
            </div>  
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data=[];
            $scope.models = [
                        { vmm_id:'1',modelname: 'm1'},
                        { vmm_id:'2',modelname: 'm2'},
                        { vmm_id:'3',modelname: 'm3'},
                        { vmm_id:'4',modelname: 'm4'}
                    ];              
//            $scope.features = [
//                        { fid:'1',featurename: 'feature1',status:"Y,O,Y,N",touch:'No'},
//                        { fid:'2',featurename: 'feature2',status:'O,N,Y,N',touch:'No'},
//                        { fid:'3',featurename: 'feature3',status:'Y,Y,O,N',touch:'No'},
//                        { fid:'4',featurename: 'feature4',status:'Y,Y,N,O',touch:'No'},
//                    ];    
            $scope.ecu_list = [ 
                { eid:'1',listitem:'ecu 1',description:'description 1',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'2',listitem:'ecu 2',description:'description 2',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'3',listitem:'ecu 3',description:'description 3',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'4',listitem:'ecu 4',description:'description 4',status:'true',variant_status:'true',variants:'high,mid,low'}
            ];
//            
            
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
                $scope.list = [];
                for(var i = 0; i < $scope.model_list.length; i++) 
                {
                   var data = $scope.model_list[i];
                   if(data.vehicle_id == selected_vehicleid){
//                       alert(data.vehicle_mapping_id);
                        angular.forEach(data.mod, function(value, key) {
                            $scope.records.push({
                             "modelname":value,
                             "vehicle_model_mapping_id":data.vehicle_mapping_id[key],
                            }); 
                        })
                   }
                }
//                alert(JSON.stringify($scope.records));
            };
        });
    app.filter('customSplitString', function() 
        {
            
            return function(input) 
            {
                
                var arr = input.split(',');
                
                return arr;
                
            };     
        });    
    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
            
    </script>   
</body>

</html>                                            