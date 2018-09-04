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
                                                    <i class="icofont icofont-automation bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>ACB</h4>
                                                        <span>ACB Version Create</span>                                 
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
                                                            <s:url action="acb.action" var="aURL" />
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
                                                                <label for="vehicle">PDB version:</label>
                                                                <select ng-model="data.pdbversion" ng-change="LoadSelectedPDBData()">
                                                                    <s:iterator value="pdbversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="pdb_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">IVN version:</label>
                                                                <select ng-model="data.ivnversion" ng-change="LoadSelectedIVNData()">
                                                                    <s:iterator value="ivnversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="ivn_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                               <div class="form-group col-md-3">
                                                                <label for="vehicle">ACB version :</label>
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
                                                                        <th class="">ECU</th>
                                                                        <th class="">Features</th>
                                                                        <th class="text-center" ng-repeat="i in models">
                                                                            {{i.modelname}}
                                                                        </th>
                                                                        <th class="text-center">Touched</th>
                                                                    </tr>
                                                                </thead>
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        <td class="">
                                                                            <span ng-if="record.ecu">{{record.ecu}}</span>
                                                                        </td>
                                                                        <td class="">
                                                                            <a class="modal-trigger" href="#modal-product-form" ng-click="assignstart(record.fid)">
                                                                                {{record.featurename}}
                                                                            </a>
                                                                        </td>
<!--                                                                    <td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                                                        <td class="text-center" ng-repeat="x in (record.status | customSplitString) track by $index">
                                                                            {{x | uppercase}}
                                                                        </td>
                                                                        <td class="text-center" ng-if='record.touch !== undefined'>
                                                                            {{record.touch}}
                                                                        </td>
                                                                        <td class="text-center" ng-if='record.touch == undefined'>
                                                                            No
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http, $window)
        {
            this.data1=[];
            this.data2=[]; 
//            $scope.models = [
//                        { vmm_id:'1',modelname: 'm1'},
//                        { vmm_id:'2',modelname: 'm2'},
//                        { vmm_id:'3',modelname: 'm3'},
//                        { vmm_id:'4',modelname: 'm4'}
//                    ];              
//            $scope.features = [
//                        { fid:'1',featurename: 'feature1',status:"Y,O,Y,N",touch:'No'},
//                        { fid:'2',featurename: 'feature2',status:'O,N,Y,N',touch:'No'},
//                        { fid:'3',featurename: 'feature3',status:'Y,Y,O,N',touch:'No'},
//                        { fid:'4',featurename: 'feature4',status:'Y,Y,N,O',touch:'No'},
//                    ];    
//            $scope.ecu_list = [ 
//                { eid:'1',listitem:'ecu 1',description:'description 1'},
//                { eid:'2',listitem:'ecu 2',description:'description 2'},
//                { eid:'3',listitem:'ecu 3',description:'description 3'},
//                { eid:'4',listitem:'ecu 4',description:'description 4'}
//            ];
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
//                { id:'3',listitem:'H/W3',ntype:'hardware'},
//                { id:'4',listitem:'H/W4',ntype:'hardware'}
//              ]; 
            $scope.assignpopulate = [];
            $scope.assignstart = function(fid)
            {
                $('.modal-trigger').leanModal();
//                alert(fid);
                var index = -1;		
		var comArr = eval($scope.features);
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
//                alert($scope.assignpopulate.length);
           
//                        $scope.assignpopulate.push({id:comArr[index].id,fea:comArr[index].fea,stat:comArr[index].stat,ecu:'',ip_signal:'',op_signal:'',});
                        $scope.my = {fid:comArr[index].fid,featurename:comArr[index].featurename,status:comArr[index].status};

//              $scope.features.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
                
//              $scope.assigner.push({id:'1',fea:'sd',stat:''});
//              alert(JSON.stringify($scope.assignpopulate));
            }
            $scope.op_signal = function(ip,ind)
            {  
                alert(JSON.stringify($scope.sigi));
                $scope.cen = {ip:ip,pri:ind};
//                alert(ind);
                $('.modal-trigger').leanModal();
            }
            $scope.sigi =[];
            $scope.sigo=[];
            $scope.add_signal_tab = function(sip,pri,sid)
            {				
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
//                     alert(JSON.stringify($scope.sigo));
                }
            };
            
            $scope.myFunc = function(fid,models,ip_signal,op_signal) 
            {
//                mod=JSON.stringify(models);
                    $scope.ip_sig_mod = [];
                    $scope.op_sig_mod = [];
                    for(i=0;i<models.length;i++)                
                    {
                        ip_mod='#ip_'+models[i].vmm_id;

                        $scope.ip_sig_mod.push({[models[i].modelname]:angular.element(ip_mod).val()});

                        op_mod='#op_'+models[i].vmm_id;
                        $scope.op_sig_mod.push({[models[i].modelname]:angular.element(op_mod).val()});
                    }
//                alert(JSON.stringify($scope.ip_sig_mod));
//                alert(JSON.stringify($scope.op_sig_mod));
                  $scope.assignpopulate.push({f_id:fid,ecu:$scope.ecu_tin,ip_signal:ip_signal,op_signal:op_signal,ip_sig_mod:$scope.ip_sig_mod,op_sig_mod:$scope.op_sig_mod});
//                alert(JSON.stringify($scope.assignpopulate));
                  var index = 0;		
                  var comArr = eval( $scope.features );
                  for(var i = 0; i < comArr.length; i++) 
                  {
                       if( comArr[i].fid === fid ) 
                       {
                           comArr[i].touch = 'Yes'
                           $scope.features[i].ecu=$scope.ecu_tin;
                           index = 1;
                           break;
                       }
                  }
                  alert(JSON.stringify($scope.features));
            };
            
//            network_list = JSON.parse("<s:property value="network_list_obj"/>".replace(/&quot;/g,'"'));
//            $scope.cans = network_list.can_list;
//            $scope.lin = network_list.lin_list;
//            $scope.hw = network_list.hardware_list;
//              
//            $scope.signal = [];
//              
//            $scope.ecu = [];
            

//
//
//              signal_list = JSON.parse("<s:property value="signallist_result_obj"/>".replace(/&quot;/g,'"'));
//              $scope.signal_list = signal_list;
//            
//                     
//            $scope.sort = function(keyname)
//            {
//                $scope.sortKey = keyname;   //set the sortKey to the param passed
//                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
//            }
            
//            $scope.add_ecu_tab = function(eid)
//            {				
//		var index = -1;		
//		var comArr = eval( $scope.ecu_list );
//		for( var i = 0; i < comArr.length; i++ ) 
//                {
//                    if( comArr[i].eid === eid ) 
//                    {
//                        index = i;
//                        break;
//                    }
//		}
//		if( index === -1 ) 
//                {
//			alert( "Something gone wrong" );
//		}
////                angular.element( document.querySelector( '.tab-pane' ) ).css('display','none');
////                var idr = '#ecu';
////                var myEl = angular.element( document.querySelector( idr ) );
////                myEl.css('display','block');
//
//                $scope.ecu.push({eid:comArr[index].eid,listitem:comArr[index].listitem,description: comArr[index].description})
//		$scope.ecu_list.splice( index, 1 );
//                
//            };     
//            $scope.removeEcuRow = function(eid)
//            {				
//		var index = -1;		
//		var comArr = eval( $scope.ecu);
//		for( var i = 0; i < comArr.length; i++ ) 
//                {
//                    if( comArr[i].eid === eid ) 
//                    {
//                        index = i;
//                        break;
//                    }
//		}
//		if( index === -1 ) 
//                {
//			alert( "Something gone wrong" );
//		}
//                $scope.ecu_list.push({eid:comArr[index].eid,listitem:comArr[index].listitem,description: comArr[index].description})
//		$scope.ecu.splice( index, 1 );		
//            };
             
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
                if($scope.list.length > 0)
                {
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
                else
                {
                    alert("Please fill the domain and feature status to create PDB version");
                }
            };
            
            $scope.radiovalue = function(vmm_id,dfm_id,status)
            {	
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
            $scope.LoadSelectedPDBData = function() 
            {                
                $http({
//                    url : 'loadpdbpreviousvehicleversion_data',
                    url : 'loadselectedpdbdata_for_acbversion',
                    method : "POST",
                    data : {"pdbversion_id":$scope.data.pdbversion}
                })
                .then(function (response, status, headers, config){
                    alert(JSON.stringify(response.data.pdb_map_result));
                    var result_data = response.data.pdb_map_result;
                    var vehicledetail_list = result_data.vehicledetail_list;
                    var featuredetail_list = result_data.featuredetail_list;
                    $scope.models = vehicledetail_list;
                    $scope.features = featuredetail_list;
//                    alert(JSON.stringify($scope.models));
//                    alert(JSON.stringify($scope.features));
                });
            };
            $scope.LoadSelectedIVNData = function() 
            {
                $http({
//                    url : 'loadivnpreviousvehicleversion_data',
                    url : 'loadselectedivndata_for_acbversion',
                    method : "POST",
                    data : {"ivnversion_id":$scope.data.ivnversion}
                })
                .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.ivn_map_result));
                    var result_data = response.data.ivn_map_result;
                    $scope.ecu_list = result_data.ecu;
                    $scope.signal_list = result_data.signal;
                    alert(JSON.stringify(result_data.can));
//                    $scope.network = result_data.can;
//                    result_data.lin.filter(function(l){
//                        $scope.network.push(l);
//                    });
//                    result_data.hardware.filter(function(h){
//                        $scope.network.push(h);
//                    });
//                    alert(JSON.stringify($scope.network));
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