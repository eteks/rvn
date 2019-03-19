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
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>System Owner</h4>
                                                        <span>System version</span>
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
                                                         <div class="row p-t-30">
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">vehicle version:</label>
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
                                                                <select ng-change="LoadACBVersion_for_System()" ng-if="vehicle_list.length > 0" ng-model="data.vehiclename">
                                                                        <option value="{{veh.vehicle_id}}" ng-repeat="veh in vehicle_list">{{veh.vehiclename}}</option>                                                                    
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">ACB version :</label>
                                                                <select ng-model="data.acbversion" ng-change="LoadACBDataForSystemVersion()">
                                                                    <option value=""></option>
                                                                    <option value="{{acb.id}}" ng-repeat="acb in acbversion">{{acb.acb_versionname}}</option> 
                                                                </select>
<!--                                                                <select ng-change="LoadACBPreviousVersion($event)" ng-focus="focusCallback($event)" ng-if="acbsubversion.length > 0" ng-model="data.acbsubversion" data="subversion">
                                                                    <option value="{{acb.id}}" ng-repeat="acb in acbsubversion">{{acb.acb_versionname}}</option>                                                                    
                                                                </select>-->
                                                            </div>
                                                             <div class="form-group col-md-3">
                                                                <label for="vehicle">ECU:</label>
                                                                <select ng-model="data.ecu" ng-change="assignstart(data.ecu)"
                                                                        ng-options="ecu_list.eid as ecu_list.listitem for ecu_list in ecu_list">
                                                                        <option>--</option>
                                                                </select>
                                                                <button class="text-c-green" style="font-weight:600" ng-click="exportSystemVersion()">Export</button>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">System version :</label>
                                                                <select ng-model="data.systemversion" ng-change="LoadSystemPreviousVersion()">
                                                                    <s:iterator value="systemversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="system_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                                <button class="text-c-green" style="font-weight:600" ng-click="exportReport()">Report</button>
                                                            </div>
                                                        </div>   
                                                        <div class="col-lg-12">
                                                            <div class="ng-table-scrollcontainer">
                                                            <table st-table="rowCollection" class="table table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th class="ng-table-fixedcolumn">Features</th>
                                                                            <th class="text-center" ng-repeat="x in (this_variant.variant_name | customSplitString) track by $index">
                                                                                {{x | uppercase}}
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        <td class="ng-table-fixedcolumn">
                                                                            <span class="compresslength" style="display:block">{{record.featurename}}</span>
                                                                        </td>
<!--                                                                    <td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                                                        <td class="text-center" ng-repeat="x in (this_variant.variant_name | customSplitString) track by $index">
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">                                                                                
                                                                                <!--<input type="radio" ng-click="radiovalue(record.fid,$index,'y')" name="f{{record.fid}}_{{$index}}" value="y" class="radio_button">-->
                                                                                <input type="radio" ng-click="radiovalue(record.fid,(this_variant.variant_id | customSplitString)[$index],'y')" name="f{{record.fid}}_{{(this_variant.variant_id | customSplitString)[$index]}}" value="y" class="radio_button">
                                                                                <span class="checkmark c_b_g">                                                                                    
                                                                                </span>
                                                                                <span class="tooltip-content2">yes</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,(this_variant.variant_id | customSplitString)[$index],'n')" name="f{{record.fid}}_{{(this_variant.variant_id | customSplitString)[$index]}}" value="n" class="radio_button">
                                                                                <span class="checkmark c_b_r"></span>
                                                                                <span class="tooltip-content2">no</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,(this_variant.variant_id | customSplitString)[$index],'o')" name="f{{record.fid}}_{{(this_variant.variant_id | customSplitString)[$index]}}" value="o" class="radio_button">    
                                                                                <span class="checkmark c_b_b"></span>
                                                                                <span class="tooltip-content2">optional</span>
                                                                              </label>
                                                                        </td>                                                                        
                                                                    </tr>
                                                                <tbody>
                                                                    <form ng-model="myform">    

                                                                    </form>
                                                                </tbody>
                                                            </table>
                                                            </div> 
                                                            <dir-pagination-controls
                                                                    max-size="20"
                                                                    direction-links="true"
                                                                    boundary-links="true">
                                                            </dir-pagination-controls>    
                                                        </div>                                               
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->            
            <div id="modal-product-form" class="modal signal_form acb_form">
                <div class="modal-content">
                   
<!--                  <form ng-submit="myFunc(my.fid,models,sigi.sid,sigo.sid)">-->
                    <form ng-submit="myFunc()">
                    <div class="from_left">
                        <h5 class="text-c-red m-b-10">Feature</h5>
                            <!-- commented out because not getting the text value using ng-model. The code below under this commented code is working fine-->
         <!--                   <select ng-model="ecu_tin" ng-change="">
                                <option  ng-repeat="i in ecu_list" value="{{i.eid}}">{{i.listitem}}</option>                                                                            
                             </select>-->
                             <input type="text" ng-model="pdbdata.domain" placeholder="Add Domain"/>
                             <input type="text" ng-model="pdbdata.feature" placeholder="Add feature"/>
                             <textarea placeholder="description" ng-model="pdbdata.description"></textarea>
<!--                        <h5 class="text-c-red m-b-10">ECU</h5>-->
                            <!-- commented out because not getting the text value using ng-model. The code below under this commented code is working fine-->
         <!--                   <select ng-model="ecu_tin" ng-change="">
                                <option  ng-repeat="i in ecu_list" value="{{i.eid}}">{{i.listitem}}</option>                                                                            
                             </select>-->
                             
                    </div>
                    <div class="from_right">
                        <h5 class="text-c-red m-b-10">Signals and Network<a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
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
                                    <td class="compresslength" style="max-width:75px;">
                                        <!--<select ng-model="valuetable" ng-change="">
                                            <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                                        </select>-->
                                        {{my.featurename}}
                                    </td>

                                    <!--<td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                    <td class="text-center" ng-repeat="i in models">
                                        <select ng-model="available_status" ng-change="createpdbdatalist(i.vmm_id,available_status)">
                                            <option value="y">Y</option>
                                            <option value="n">N</option>
                                            <option value="o">O</option>
                                        </select>
                                    </td>

                                </tr>
                                <tr>
                                    <td><h5>Input</h5></td>
                                </tr>
                                <tr ng-repeat="data in Demo.data1" class="inputcloned_data">
                                    <td>
                                        <a class="modal-trigger text-c-green" href="#modal-feature-list"  ng-click="op_signal(0,$index)">
                                            <i class="icofont icofont-ui-add"></i>
                                        </a>
                                        {{sigi[$index].listitem}}
                                    </td>
                                    <td ng-repeat="i in models">      
                                        <!--ip_{{$parent.$index}}_{{$index}}-->
    <!--                                    <select id="ip_{{i.vmm_id}}" ng-attr-name="ip{{$parent.$index}}{{$index}}" ng-model="ip_$parent.$index_$index" ng-change="" data-pdbgroupid="{{i.pdbgroup_id}}">-->
                                        <!--{{ip[$parent.$index][$index]}}-->
                                        <select id="ip_{{i.vmm_id}}" ng-attr-name="ip_{{$parent.$index}}_{{$index}}" ng-model="ip[$parent.$index][$index]" data-pdbgroupid="{{i.pdbgroup_id}}">
                                        <!--<select id="ip_{{i.vmm_id}}" ng-model="{{$index}}_{{$parent.$index}}" ng-change="" data-pdbgroupid="{{i.pdbgroup_id}}">-->                                
                                            <option ng-repeat="i in getnetwork(i.vmm_id) track by i.listitem" value="{{i.id}}" data-network="{{i.ntype}}">{{i.listitem}}</option>                                                                            
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
                                <tr ng-repeat="data in Demo.data2" class="outputcloned_data">
                                    <td>
                                        <a class="modal-trigger  text-c-green" href="#modal-feature-list" ng-click="op_signal(1,$index)">
                                            <i class="icofont icofont-ui-add"></i>
                                        </a>
                                        {{sigo[$index].listitem}}
                                    </td>
                                    <td ng-repeat="i in models">
    <!--                                    <select id="op_{{i.vmm_id}}" ng-model="op_$index" ng-change="">-->
                                        <select id="op_{{i.vmm_id}}" ng-attr-name="op{{$parent.$index}}{{$index}}" ng-model="op[$parent.$index][$index]" ng-change="" data-pdbgroupid="{{i.pdbgroup_id}}">
                                            <option ng-repeat="i in getnetwork(i.vmm_id)" value="{{i.id}}" data-network="{{i.ntype}}">{{i.listitem}}</option>                                                                            
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
                    </div>
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
                <a class="modal-trigger float-left text-c-green" style="font-weight:600" href="#modal-upload" style="text-decoration:underline;" ng-click="assignstart(record.fid)">
                    Import
                </a>
                <div id="modal-upload" class="modal">
                    <div class="modal-content">
                        <h5 class="text-c-red m-b-10"><a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                        <div ng-controller = "fileCtrl" class="float-left">
                            <input type = "file" name="userImport" file-model = "myFile" accept=".csv"/>
                            <button class="btn btn-primary" ng-click = "uploadFile()">Import</button>
                        </div>
                    </div>
                    <div class="loader-block" style="display:none;">
                        <div class="preloader6">
                            <hr>
                        </div>
                    </div>
                </div>
                <label for="status" style="vertical-align:middle">Status:</label>
                <label class="switch m-r-50"  style="vertical-align:middle">
                    <input type="checkbox" ng-model="data.status">
                    <span class="slider round"></span>
                 </label>
<!--                <a class="modal-trigger" href="#modal-product-form" style="text-decoration:underline;" ng-click="assignstart(record.fid)">
                                                                                Add feature
                                                                            </a>-->
                <a class="modal-trigger" href="#modal-product-form" style="text-decoration:underline;">
                    Add feature
                </a>                                                           
                <button ng-show="showSave == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createsystemversion('save')" name="save">Save</button>
                <button ng-show="showSubmit == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createsystemversion('submit')" name="submit">Submit</button>
                
            </div>
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window, $location, $timeout)
        {
            this.data1=[];
            this.data2=[]; 
            var notification_to;
            $scope.list = [];
            $scope.models = [];
            $scope.vehicle_list = [];
            $scope.model_list = [];
            $scope.data = {};
            $scope.pdbdata ={};
            $scope.showSave =true;
            $scope.showSubmit =true;
            var pdbversion_id;
            var ivnversion_id;
            $scope.pdbdata_list = [];
            $scope.$on('notifyValue', function (event, args) {
                notification_to = args;
                $scope.createsystemversionAJAX("submit");
            });
             
//            $scope.features = [
//                        { fid:'1',featurename: 'feature1',status:"Y,O,Y,N",touch:'No'},
//                        { fid:'2',featurename: 'feature2',status:'O,N,Y,N',touch:'No'},
//                        { fid:'3',featurename: 'feature3',status:'Y,Y,O,N',touch:'No'},
//                        { fid:'4',featurename: 'feature4',status:'Y,Y,N,O',touch:'No'},
//                    ];    
//            $scope.ecu_list = [ 
//                { eid:'1',listitem:'ecu 1',description:'description 1',status:'true',variant_status:'true',variants:'high,mid,low'},
//                { eid:'2',listitem:'ecu 2',description:'description 2',status:'true',variant_status:'true',variants:'high,mid,low'},
//                { eid:'3',listitem:'ecu 3',description:'description 3',status:'true',variant_status:'true',variants:'high,mid,low'},
//                { eid:'4',listitem:'ecu 4',description:'description 4',status:'true',variant_status:'true',variants:'high,mid,low'}
//            ];
            var features_group;
            $scope.features = [];
            $scope.ecu_list = [];
            $scope.assignstart = function(eid)
            {
//                alert(fid);
                var index = -1;		
		var comArr = eval( $scope.ecu_list );
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].eid === eid ) 
                    {
                        index = i;
                        break;
                    }
		}
                
                $scope.this_variant = {eid:comArr[index].eid,listitem:comArr[index].listitem,variant_name:comArr[index].variant_name,variant_id:comArr[index].variant_id};
//                alert(JSON.stringify($scope.this_variant));
//                alert(JSON.stringify(features_group));
                $scope.features = features_group.filter(function(fg,key){
                    if(fg.eid == eid)
                        return fg;
                });
            };
            $scope.radiovalue = function(dfm_id,variant_id,status)
            {		
                if($scope.list.length === 0)
                {
                    $scope.list.push({"dfm_id":dfm_id,"variant_id":variant_id,"status":status});
                }
                else
                {
                    var temp=0;
                    for(var i=0; i<$scope.list.length; i++)
                    {
                        if(($scope.list[i].dfm_id === dfm_id) && ($scope.list[i].variant_id === variant_id))
                        {
                            $scope.list[i].status=status;
                            temp=1;
                        }
                        
                    }
                    if(temp==0)
                    {
                        $scope.list.push({"dfm_id":dfm_id,"variant_id":variant_id,"status":status});
                    }
                }
//                alert(JSON.stringify($scope.list));
            };
            $scope.LoadSelectedVehicleVersionData = function() 
            {
                $scope.vehicle_list = [];
                $scope.model_list = [];
                $http({
                    url : 'loadpreviousvehicleversion_data',
                    method : "POST",
                    data : {"vehicleversion_id":$scope.data.vehicleversion}
                })
                .then(function (response, status, headers, config){
                    result_data = JSON.stringify(response.data.vehmod_map_result);
    //                    alert(result_data);
//                    $scope.vehicle_list = []; 
//                    $scope.model_list = [];
    //                    var vm_id =[];
//                    $scope.vehicle_list.push({"vehicle_id":"","vehiclename":"Select"});
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
                    }
    //                    alert(JSON.stringify($scope.model_list));
                });
            };
            $scope.LoadACBVersion_for_System = function() 
            {
    //                alert($scope.data.vehicleversion);
    //                alert($scope.data.vehiclename);
                $http({
                    url : 'loadacbversion_for_system',
                    method : "POST",
                    data : {"vehicleversion_id":$scope.data.vehicleversion,"vehicle_id":$scope.data.vehiclename}
                })
                .then(function (response, status, headers, config){
//                        alert(JSON.stringify(response.data.result_data));
    //                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
    //                alert(result_data);
                    var acbversion = response.data.result_data;
                    $scope.acbversion = acbversion;
                    $scope.models = [];
                    for(var i = 0; i < $scope.model_list.length; i++) 
                    {
                       var data = $scope.model_list[i];
                       if(data.vehicle_id == $scope.data.vehiclename){
    //                       alert(data.vehicle_mapping_id);
                            angular.forEach(data.mod, function(value, key) {
                                $scope.models.push({
                                 "modelname":value,
                                 "vmm_id":data.vehicle_mapping_id[key],
                                }); 
                            })
                       }
                    }
                    if(acbversion.length == 0)
//                        alert("Not yet created ACBVersion for this vehicle or Features may not be fully Touched");
                          alert("Not yet created ACBVersion for this vehicle");
                });
            }
            $scope.LoadACBDataForSystemVersion = function() 
            {
    //                alert($scope.data.vehicleversion);
    //                alert($scope.data.vehiclename);
                $http({
                    url : 'loadselectedacbdata_for_systemversion',
                    method : "POST",
                    data : {"acbversion_id":$scope.data.acbversion,"vehicleversion_id":$scope.data.vehicleversion,"vehicle_id":$scope.data.vehiclename}
                })
                .then(function (response, status, headers, config){
    //                    alert(JSON.stringify(response.data.acb_result_data.ecu_list));
                        var result_data = response.data.acb_result_data;
//                        alert(JSON.stringify(result_data.ecu_list));
                        pdbversion_id = result_data.acbversion_group[0].pdbversion;
                        ivnversion_id = result_data.acbversion_group[0].ivnversion;
                        
                        if(result_data.ecu_list.length > 0){
                            $scope.ecu_list = result_data.ecu_list;
                            features_group = result_data.feature_list;
        //                    alert(JSON.stringify(result_data));
                            //Load IVN data
                            var vehicledetail = result_data.vehicledetail_list;
                            angular.forEach(vehicledetail, function(value, key) {
                                var variable = "network"+value.vmm_id;
                                $scope[variable] = [];
                                result_data.can.filter(function(h){    
                                    if(h.vmm_id == value.vmm_id)
                                        $scope[variable].push(h);
                                });
                                result_data.lin.filter(function(h){    
                                    if(h.vmm_id == value.vmm_id)
                                        $scope[variable].push(h);
                                });
                                result_data.hardware.filter(function(h){    
                                    if(h.vmm_id == value.vmm_id)
                                        $scope[variable].push(h);
                                });                           
                             })
        //                     alert(JSON.stringify(result_data.signal))
                            $scope.signal_list = result_data.signal;      
                        }
                        else{
                            alert("Variants are empty. Please assign the Variants to ECU");
                        }
                });
            };
            $scope.getnetwork = function(vmm_id) {
//                alert(vmm_id);
                var variable ="network"+vmm_id;
                return $scope[variable];
            };

            $scope.op_signal = function(ip,ind)
            {  
    //                alert(JSON.stringify($scope.sigi));
                $scope.cen = {ip:ip,pri:ind};
    //                alert(ind);
                $('.modal-trigger').leanModal();
            }
            $scope.sigi =[];
            $scope.sigo=[];
            $scope.add_signal_tab = function(sip,pri,sid)
            {		
//                alert(sid);
//                alert(pri);
//                alert(sid);
                var index = -1;		
                var comArr = eval( $scope.signal_list );
                for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].sid === sid ) 
                    {
                        index = i;
                        break;
                    }
                }
                if( index === -1 ) 
                {
                        alert( "Something gone wrong" );
                }
                if(sip == 0)
                {
                    if($scope.sigi.length === 0 )
                    {
//                        alert(comArr[index]);
                        $scope.sigi.push({sid:comArr[index].sid,pri:pri,listitem:comArr[index].listitem,description:comArr[index].description});
                    }
                    else
                    {
                      var perc = -1;
                      $scope.sigi.filter(function(s,i)
                      {

                            if(s.pri === pri)
                            {

                                perc= i;
                            }           

                         });
                         if(perc == -1)
                            {
                                    $scope.sigi.push({sid:comArr[index].sid,pri:pri,listitem:comArr[index].listitem,description:comArr[index].description});
    //                                    alert(JSON.stringify($scope.sigi));
                            }
                            else
                            {
                                $scope.sigi[perc].sid=comArr[index].sid;
                                $scope.sigi[perc].listitem=comArr[index].listitem;
                                $scope.sigi[perc].description=comArr[index].description;
                            }
                    }
    //                    alert(JSON.stringify($scope.sigi));                    
                }
                else
                {
                    if($scope.sigo.length === 0 )
                    {
                        $scope.sigo.push({sid:comArr[index].sid,pri:pri,listitem:comArr[index].listitem,description:comArr[index].description});
                    }
                    else
                    {
                      var perc = -1;
                      $scope.sigo.filter(function(s,i)
                      {

                            if(s.pri === pri)
                            {

                                perc= i;
                            }           

                         });
                         if(perc == -1)
                         {
                                $scope.sigo.push({sid:comArr[index].sid,pri:pri,listitem:comArr[index].listitem,description:comArr[index].description});
    //                                    alert(JSON.stringify($scope.sigi));
                         }
                         else
                         {
                                $scope.sigo[perc].sid=comArr[index].sid;
                                $scope.sigo[perc].listitem=comArr[index].listitem;
                                $scope.sigo[perc].description=comArr[index].description;
                         }
                    }
//                         alert(JSON.stringify($scope.sigi));
//                         alert(JSON.stringify($scope.sigo));
                }
            };
        
            /*$scope.checkNotify = function (event){
                if($scope.data.status && event === "submit"){
                    if($scope.this_variant != undefined){
                        var model_and_variant_length = $scope.features.length * $scope.this_variant['variant_id'].split(",").length;
                    }
                    if(model_and_variant_length != undefined){
                        if($scope.list.length > 0 && $scope.list.length == model_and_variant_length){
                            $(".notifyPopup").click();
                        }else{
                            alert("Please assign the status to all the Features and Variants");
                        }
                    }else{
                        alert("Please fill all the details");
                    }
                }else
                    $scope.createsystemversion(event);
            }*/
            
            $scope.createsystemversionAJAX = function (event){
                var status = $scope.data.status;
                if(status == undefined || status == false)
                    notification_to = undefined;
                
                var data = {};
                        data['systemversion'] = $scope.data;
                        data['systemdata_list'] = $scope.list;
                        data['button_type'] = event;
                        data['notification_to'] = notification_to+"";
    //                        alert(JSON.stringify(data));
                        $http({
                            url : 'createsystemversion',
                            method : "POST",
                            data : data,
                            })
                            .then(function (data, status, headers, config){               
                                  alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                                      $window.open("sys_version.action","_self"); //                alert(data.maps);
        //            //                Materialize.toast(data['maps']["status"], 4000);
                        });
            }
            
            $scope.createsystemversion = function(event) 
            {
                var status = $scope.data.status;
                if(status == undefined )
                    status = false;
                
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false;
                if($scope.this_variant != undefined){
                    var model_and_variant_length = $scope.features.length * $scope.this_variant['variant_id'].split(",").length;
                }
                if(model_and_variant_length != undefined && $scope.data.vehicleversion != undefined 
                        && $scope.data.vehiclename != undefined && $scope.data.acbversion != undefined 
                        && $scope.data.ecu != undefined){
                    if($scope.list.length > 0 && $scope.list.length == model_and_variant_length){
    //                    alert("proceed");
                        if(status && event === "submit"){
                            $(".notifyPopup").click();
                        }else
                            $scope.createsystemversionAJAX(event);
                    }
                    else{
                        alert("Please assign the status to all the Features and Variants");
                    }
                }    
                else{
                    alert("Please fill all the details");
                }
            };  
            $scope.exportSystemVersion = function(){
               var data = {
                   "vehicle_version": $scope.data.vehicleversion,
                   "vehicle": $scope.data.vehiclename,
                   "acb_version": $scope.data.acbversion,
                   "ecu": $scope.data.ecu
               }
               //console.log(data);
               $http({
//                    url : 'loadpdbpreviousvehicleversion_data',
                    url : 'system_export',
                    method : "POST",
                    data : data
                })
                .then(function (response, status, headers, config){
                   if (window.navigator.msSaveOrOpenBlob) {
                    var blob = new Blob([decodeURIComponent(encodeURI(response.data))], {
                      type: "text/csv;charset=utf-8;"
                    });
                    navigator.msSaveBlob(blob, 'System Export.csv');
                  } else {
                    var a = document.createElement('a');
                    a.href = 'data:attachment/csv;charset=utf-8,' + encodeURI(response.data);
                    a.target = '_blank';
                    a.download = 'System Export.csv';
                    document.body.appendChild(a);
                    a.click();
                }
                });
            }
            
            $scope.exportReport = function(){
               var data = {
                   "systemversion_id":$scope.data.systemversion,
                   "ecu": $scope.data.ecu
               }
               //console.log(data);
               $http({
                    url : 'system_report',
                    method : "POST",
                    data : data,
                    responseType: "arraybuffer"
                })
                .then(function (response, status, headers, config){
                   if (window.navigator.msSaveOrOpenBlob) {
                    var blob = new Blob([decodeURIComponent(encodeURI(response.data))], {
                      type: "application/vnd.ms-excel;base64;"
                    });
                    navigator.msSaveBlob(blob, 'System Report.xls');
                  } else {
                    var blob = new Blob([response.data],{ type: response.headers('Content-Type')});
                    var a = document.createElement('a');
                    //a.href = 'data:application/vnd.ms-excel;base64' + encodeURI(response.data);
                    a.href = window.URL.createObjectURL(blob);
                    a.target = '_blank';
                    a.download = 'System Report.xlsx';
                    document.body.appendChild(a);
                    a.click();
                    window.URL.revokeObjectURL(a.href);
                }
                });
            }
            
        $scope.LoadSystemPreviousVersion = function () 
        { 
            $http({
                url : 'loadsystempreviousversion_data',
                method : "POST",
                data : {"systemversion_id":$scope.data.systemversion}
            })
            .then(function (response, status, headers, config){
               var result_data = response.data.system_result_data;
//                   alert(JSON.stringify(result_data));
               $scope.data.vehicleversion = result_data.systemversion[0].vehicleversion;
               $scope.LoadSelectedVehicleVersionData();
               $scope.data.vehiclename = result_data.systemversion[0].vehiclename;
               $scope.LoadACBVersion_for_System();
               $scope.data.acbversion = result_data.systemversion[0].acbversion;
               $scope.LoadACBDataForSystemVersion();
               $scope.data.ecu = result_data.systemversion[0].ecu;
               $scope.list = result_data.systemdata_list; 
               $scope.features = result_data.feature_list;
               $scope.this_variant = result_data.ecu_variant_list[0]; 
               $scope.data.status = result_data.systemversion_status[0].status;
               
               //Fill the variants
               angular.element(function () {
                    var result = document.getElementsByClassName("radio_button");
                    angular.forEach(result, function(value) {
                        var result_name = value.getAttribute("name").substring(1).split("_");
                        var fid = result_name[0];
                        var variant_id = result_name[1];
                        var status = value.getAttribute("value");  
                        angular.forEach($scope.list, function(item) {
                            if(item.dfm_id == fid && item.variant_id == variant_id && item.status == status)
                                value.setAttribute("checked","checked");
                        });    
                    });
                });
            });
        };
        
        $scope.myFunc = function() 
        {
            var models = $scope.models;
            var pdbdata ={};
            pdbdata['pdbversion'] = pdbversion_id;
            pdbdata['pdbdata'] = $scope.pdbdata;
            pdbdata['pdbdata_list'] = $scope.pdbdata_list;
            var temppdb_result;
            var feature_and_domain_data ={};
            var temp_pdbdata = [];
            temp_pdbdata.push($scope.pdbdata)
            feature_and_domain_data['pdbversion'] = pdbversion_id;
            feature_and_domain_data['domain_name'] = $scope.pdbdata.domain;
            feature_and_domain_data['features_and_description'] = temp_pdbdata;
                        
            if($scope.pdbdata.domain != undefined && $scope.pdbdata.feature != undefined && $scope.pdbdata.description != undefined){
                if($scope.pdbdata_list.length == $scope.models.length){
                    if($scope.sigi.length != 0 && $scope.sigo.length != 0){
                        $http({
                            url : 'createfeature_and_domain',
                            method : "POST",
                            data : feature_and_domain_data,
                        })
                        .then(function (data, status, headers, config){                                                                        
                                result_data = data.data.domainFeatures_result;
                                pdbdata['dfm_id'] = result_data[0].fid;
                                $http({
                                    url : 'createpdbdata_from_system',
                                    method : "POST",
                                    data : pdbdata,
                                })
                                .then(function (data, status, headers, config){               
//                                       console.log("success");
                                       var pdbresult = data.data.result_data;
                                       pdbresult.filter(function(p,i){
                                        $scope.models.filter(function(m,i){
                                            if(m.vmm_id == p.vmm_id)
                                                $scope.models[i].pdbgroup_id = p.pdbgroup_id;
                                        });
                                       });
                                       $timeout(function(){
                                           $scope.features.push({fid:result_data[0].fid,featurename:result_data[0].fea,domainname:result_data[0].domain});
                                            var inputcloned_data=document.getElementsByClassName('inputcloned_data');
                                            var outputcloned_data = document.getElementsByClassName('outputcloned_data');
                                            var touched_group ={};
                                            touched_group['acbversion'] = $scope.data.acbversion;
                                            touched_group['ivnversion'] = ivnversion_id;
                                            touched_group['pdbversion'] = pdbversion_id;
                                            touched_group['vehicleversion'] = $scope.data.vehicleversion;
                                            touched_group['vehicle'] = $scope.data.vehiclename;
                                            touched_group['fid'] = result_data[0].fid;
                                            touched_group['ecu'] = $scope.data.ecu;
                                            var cloned_data = [];
                                            angular.forEach(inputcloned_data, function(value,i) {
                                              var select_tag = value.getElementsByTagName("select");
                                              var group_data = [];
                                              angular.forEach(select_tag, function(s, j) {
                                                  group_data.push({
                                                      'pdbgroup_id':s.getAttribute('data-pdbgroupid'),
                                                      'nt_type':s.options[s.selectedIndex].getAttribute('data-network'),
                                                      'nt_id':s.options[s.selectedIndex].value,
                                                      'vmm_id':s.getAttribute('id').split("_")[1]
                                                  });                                  
                                              });
                                              cloned_data.push({'signal': $scope.sigi[i].sid,'signal_type':'input',
                                                                    'group_data':group_data});
                                            });
                                              angular.forEach(outputcloned_data, function(value,i) {
                                                    var select_tag = value.getElementsByTagName("select");
                                                    var group_data = [];
                                                    angular.forEach(select_tag, function(s, j) {
                                                        group_data.push({
                                                            'pdbgroup_id':s.getAttribute('data-pdbgroupid'),
                                                            'nt_type':s.options[s.selectedIndex].getAttribute('data-network'),
                                                            'nt_id':s.options[s.selectedIndex].value,
                                                            'vmm_id':s.getAttribute('id').split("_")[1]
                                                        });                                  
                                                    });
                                                    cloned_data.push({'signal': $scope.sigo[i].sid,'signal_type':'output',
                                                                    'group_data':group_data});
                                              });
                                            touched_group['cloned_data'] = cloned_data;
//                                            alert(JSON.stringify(touched_group));
                                            $http({
                                                url : 'createacbdata_from_system',
                                                method : "POST",
                                                data : touched_group,
                                            })
                                            .then(function (data, status, headers, config){ 
                                                alert("PDB features created successfully and stored as touched");
                                                $('#modal-product-form').closeModal();     
                                            });
                                       });                                  
                                           
                                });
                        });                                                
                    }
                    else{
                        alert("Please add the Input and Output signals");
                    }
                }
                else{
                    alert("Please fill all the status of models for features");
                }
            }
            else{
                alert("Please fill the domain, features, and description");
            }                    
        };

        $scope.createpdbdatalist = function(vmm_id,status)
        {		
//            alert("createpdbdatalist");
            if($scope.pdbdata_list.length === 0)
            {
                $scope.pdbdata_list.push({vmm_id:vmm_id,status:status});
            }
            else
            {
                var temp=0;
                for(var i=0; i<$scope.pdbdata_list.length; i++)
                {
                    if($scope.pdbdata_list[i].vmm_id === vmm_id)
                    {
                        $scope.pdbdata_list[i].status=status;
                        temp=1;
                    }

                }
                if(temp==0)
                {
                    $scope.pdbdata_list.push({vmm_id:vmm_id,status:status});
                }
            }
//                alert(JSON.stringify($scope.pdbdata_list));
        };
        
        if($location.absUrl().includes("?")){
                var params_array = [];
                var absUrl = $location.absUrl().split("?")[1].split("&");
                for(i=0;i<absUrl.length;i++){
                    var key_test = absUrl[i].split("=")[0];
                    var value = absUrl[i].split("=")[1];
//                    alert(key_test);
//                    alert(value);
                    params_array.push({[key_test]:value});
                }
//                alert(JSON.stringify(params_array));
                $scope.acbversion = params_array[0].id;
                var action = params_array[1].action;
                
               var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//                   alert(JSON.stringify(result_data));
               $scope.data.vehicleversion = result_data.systemversion[0].vehicleversion;
               $scope.LoadSelectedVehicleVersionData();
               $scope.data.vehiclename = result_data.systemversion[0].vehiclename;
               $scope.LoadACBVersion_for_System();
               $scope.data.acbversion = result_data.systemversion[0].acbversion;
               $scope.LoadACBDataForSystemVersion();
               $scope.data.ecu = result_data.systemversion[0].ecu;
               $scope.list = result_data.systemdata_list; 
               $scope.features = result_data.feature_list;
               $scope.this_variant = result_data.ecu_variant_list[0]; 
               $scope.data.status = result_data.systemversion_status[0].status;
               $scope.data.systemversion = result_data.systemversion[0].systemversion;
               
               //Fill the variants
               angular.element(function () {
                    var result = document.getElementsByClassName("radio_button");
                    angular.forEach(result, function(value) {
                        var result_name = value.getAttribute("name").substring(1).split("_");
                        var fid = result_name[0];
                        var variant_id = result_name[1];
                        var status = value.getAttribute("value");  
                        angular.forEach($scope.list, function(item) {
                            if(item.dfm_id == fid && item.variant_id == variant_id && item.status == status)
                                value.setAttribute("checked","checked");
                        });    
                    });
                });
                if(action == "view"){
                    $scope.showSave =false;
                    $scope.showSubmit =false;
                }
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
    
        app.directive('fileModel', ['$parse', function ($parse) {
            return {
               restrict: 'A',
               link: function(scope, element, attrs) {
                  var model = $parse(attrs.fileModel);
                  var modelSetter = model.assign;
                  
                  element.bind('change', function(){
                     scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                     });
                  });
               }
            };
         }]);
      
         app.service('fileUpload', ['$http','$window', function ($http,$window) {
            this.uploadFileToUrl = function(file, uploadUrl){
               var fd = new FormData();
               fd.append('file', file);
            
               $http.post(uploadUrl, fd, {
                  transformRequest: angular.identity,
                  headers: {'Content-Type': undefined}
               }).then(function success(response) {
                        $(".loader-block").hide();
                        alert("Successfully Imported");
                        $window.open("sys_version.action","_self");
                    }, function error(response) {
                        $(".loader-block").hide();
                        alert("Error");
                    })
            }
         }]);
      
         app.controller('fileCtrl', ['$scope', 'fileUpload','$window', function($scope, fileUpload, $window){
            $scope.uploadFile = function(){
                var file = $scope.myFile;
                if(file != undefined){
                    $(".loader-block").show();
    //                alert('hi');             

                   //console.log('file is ' );
                   //console.dir(file);

                   var uploadUrl = "systemImport";
                   fileUpload.uploadFileToUrl(file, uploadUrl); 
                   //$window.open("sys_version.action","_self");
                }
                else{
                   alert("Please upload CSV file for import");
                }
            };
         }]);
         
    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
            
    </script>   
</body>

</html>                                            