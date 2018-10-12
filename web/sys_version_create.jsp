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
                                                            </div>
                                                        </div>   
                                                        <div class="col-lg-12">
                                                            <table st-table="rowCollection" class="table table-striped">
                                                                    <thead>
                                                                        <tr>                                                                    

                                                                            <th class="">Features</th>
                                                                            <th class="text-center" ng-repeat="x in (this_variant.variant_name | customSplitString) track by $index">
                                                                                {{x | uppercase}}
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        
                                                                        <td class="">
                                                                                {{record.featurename}}
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
            <div id="modal-product-form" class="modal signal_form acb_form">
                <div class="modal-content">
                   
                  <form ng-submit="myFunc(my.fid,models,sigi.sid,sigo.sid)">
                    <div class="from_left">
                        <h5 class="text-c-red m-b-10">Feature</h5>
                            <!-- commented out because not getting the text value using ng-model. The code below under this commented code is working fine-->
         <!--                   <select ng-model="ecu_tin" ng-change="">
                                <option  ng-repeat="i in ecu_list" value="{{i.eid}}">{{i.listitem}}</option>                                                                            
                             </select>-->
                             <input type="text" ng-model="feature" placeholder="Add feature"/>
                             <textarea placeholder="description"></textarea>
                        <h5 class="text-c-red m-b-10">ECU</h5>
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
            <div class="col-lg-12 text-right">
                <label for="status" style="vertical-align:middle">Status:</label>
                <label class="switch m-r-50"  style="vertical-align:middle">
                    <input type="checkbox" ng-model="data.status">
                    <span class="slider round"></span>
                 </label>
                <a class="modal-trigger" href="#modal-product-form" style="text-decoration:underline;" ng-click="assignstart(record.fid)">
                                                                                Add feature
                                                                            </a>
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createsystemversion($event)" name="save">Save</button>
                <button type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createsystemversion($event)" name="submit">Submit</button>
                
            </div>
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data1=[];
            this.data2=[]; 
            var notification_to;
            $scope.list = [];
            $scope.$on('notifyValue', function (event, args) {
                notification_to = args;
                $scope.createsystemversion("submit");
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
//                alert(dfm_id);
//                alert(variant_id);
//                alert(status);
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
//            $scope.signal_list = 
//            [
//                { sid:'1',listitem:'signal 1',description:'description 1'},
//                { sid:'2',listitem:'signal 2',description:'description 2'},
//                { sid:'3',listitem:'signal 3',description:'description 3'},
//                { sid:'4',listitem:'signal 4',description:'description 4'}
//            ];
//            $scope.network = [
//                { id:'1',listitem:'CAN1',ntype:'can'},
//                { id:'2',listitem:'CAN2',ntype:'can'},
//                { id:'3',listitem:'CAN3',ntype:'can'},
//                { id:'4',listitem:'CAN4',ntype:'can'},
//                { id:'1',listitem:'LIN1',ntype:'lin'},
//                { id:'2',listitem:'LIN2',ntype:'lin'},
//                { id:'3',listitem:'LIN3',ntype:'lin'},
//                { id:'4',listitem:'LIN4',ntype:'lin'},
//                { id:'1',listitem:'H/W1',ntype:'hardware'},
//                { id:'2',listitem:'H/W2',ntype:'hardware'},
//               { id:'3',listitem:'H/W3',ntype:'hardware'},
//                { id:'4',listitem:'H/W4',ntype:'hardware'}
//              ];  
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
//                    alert(JSON.stringify(response.data.result_data));
//                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//                alert(result_data);
                var acbversion = response.data.result_data;
                $scope.acbversion = acbversion;
                if(acbversion.length == 0)
                    alert("Not yet created ACBVersion for this vehicle version");
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
            $scope.ecu_list = result_data.ecu_list;
            features_group = result_data.feature_list;
//            $scope.acbsubversion = response.data.acb_result_data.acbsubversion;
//                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
////                alert(result_data);
//                var acbversion = response.data.result_data;
//                $scope.acbversion = acbversion;
//                if(acbversion.length == 0)
//                    alert("Not yet created ACBVersion for this vehicle version");
            });
        };
        
        $scope.checkNotify = function (event){
            if($scope.data.status && event === "submit"){
                $(".notifyPopup").click();
            }else
                $scope.createsystemversion(event);
        }
        
        $scope.createsystemversion = function(event) 
        {
            if (!$scope.doSubmit) 
            {
                return;
            }
            $scope.doSubmit = false;
            if($scope.this_variant != undefined){
                var model_and_variant_length = $scope.features.length * $scope.this_variant['variant_id'].split(",").length;
            }
            if(model_and_variant_length != undefined){
                if($scope.list.length > 0 && $scope.list.length == model_and_variant_length){
//                    alert("proceed");
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
    //                              $window.open("pdb_listing.action","_self"); //                alert(data.maps);
    //            //                Materialize.toast(data['maps']["status"], 4000);
                    });
                }
                else{
                    alert("Please assign the status to all the Features and Variants");
                }
            }    
            else{
                alert("Please fill all the details");
            }
        };   
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
//
//               //Fill the variants
//               angular.element(document).ready(function(){
//                   $scope.variants = [];
//                   for(i=1;i<=$scope.models.length;i++)
//                       $scope.variants.push([]);
//                    var variants_data = document.getElementsByClassName('variants_data');
//                    angular.forEach(variants_data, function(value,key) {  
//                       var variable_name = value.getAttribute("name").split("_");
//                       var index_value = value.getAttribute("data-variants").split("_");
//                       var vmm_id = variable_name[1];
//                       var ecu_id = variable_name[2];
//                       var parent_index = index_value[1];
//                       var sub_index = index_value[2];
//                       result_data.modeldata_list.filter(function(v,i){
//                            if(v.vmm_id == vmm_id && v.ecu_id == ecu_id){
//                                $scope.variants[parent_index][sub_index] = v.variant_id;
//                            }
//                        });                           
//                    });
//               });   
//
            });
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