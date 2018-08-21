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
                                                                <select ng-model="data.vehicleversion" ng-change="LoadSelectedVehicleVersionData()">
                                                                    <s:iterator value="vehicleversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">IVN version:</label>
                                                                <select ng-hide="data.vehicleversion"></select>
                                                                <select ng-change="LoadVehicleModels(data.vehiclename)" ng-if="vehicle_list.length > 0" ng-model="data.vehiclename">
                                                                        <option value="{{veh.vehicle_id}}" ng-repeat="veh in vehicle_list">{{veh.vehiclename}}</option>                                                                    
                                                                </select>
                                                            </div>
                                                               <div class="form-group col-md-3">
                                                                <label for="vehicle">ACB version :</label>
                                                                <select ng-model="data.pdbversion" ng-change="LoadPDBPreviousVersion()">
                                                                    <s:iterator value="ivnversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="ivn_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>                                
                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()">Add</a>
                                                            
                                                        </div>   
                                                        <div class="col-lg-12">
                                                            <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                    <tr>                                                                    
                                                                        <th class="">ECU</th>
                                                                        <th class="">Features</th>
                                                                        <th class="text-center" ng-repeat="i in models">
                                                                            {{i.mod}}
                                                                        </th>
                                                                        <th class="text-center">Touched</th>
                                                                    </tr>
                                                                </thead>
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        <td class="">
                                                                            <!--<select ng-model="valuetable" ng-change="">
                                                                                <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                                                                            </select>-->
                                                                        </td>
                                                                        <td class="">
                                                                            <a class="modal-trigger" href="#modal-product-form" ng-click="assignstart(record.id)">
                                                                                {{record.fea}}
                                                                            </a>
                                                                        </td>
<!--                                                                    <td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                                                        <td class="text-center" ng-repeat="x in (record.stat | customSplitString) track by $index">
                                                                            {{x}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                            {{record.touch}}
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
                    <h5 class="text-c-red m-b-10">Signals Version Create<a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    <select ng-model="valuetable" ng-change="">
                        <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                    </select>
                    <table st-table="rowCollection" class="table table-striped">
                        <thead>
                            <tr>                                                                    
                                <th class="">Type</th>
                                <th class="text-center" ng-repeat="i in models">
                                    {{i.mod}}
                                </th>
                            </tr>
                        </thead>
                            <tr>
                                <td class="">
                                    <!--<select ng-model="valuetable" ng-change="">
                                        <option  ng-repeat="i in ecu_list">{{i.listitem}}</option>                                                                            
                                    </select>-->
                                    {{my.fea}}
                                </td>
                                
                                <!--<td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                <td class="text-center" ng-repeat="x in (my.stat | customSplitString) track by $index">
                                    {{x}}
                                </td>
                                
                            </tr>
                            <tr>
                                <td><h5>Input</h5></td>
                            </tr>
                            <tr>
                                <td>
                                    <a class="modal-trigger" href="#modal-feature-list">
                                        <i class="icofont icofont-ship-wheel text-c-red"></i>
                                        Signal List
                                    </a>
                                </td>
                                <td ng-repeat="x in (my.stat | customSplitString) track by $index">
                                    <select ng-model="network" ng-change="">
                                        <option  ng-repeat="i in network">{{i.listitem}}</option>                                                                            
                                    </select>
                                </td>
                                
                            </tr>
                            <tr>
                                <td><h5>Output</h5></td>
                            </tr>
                            <tr>
                                <td>
                                    <a class="modal-trigger" href="#modal-feature-list">
                                        <i class="icofont icofont-ship-wheel text-c-red"></i>
                                        Signal List
                                    </a>
                                </td>
                                <td ng-repeat="x in (my.stat | customSplitString) track by $index">
                                    <select ng-model="network" ng-change="">
                                        <option  ng-repeat="i in network">{{i.listitem}}</option>                                                                            
                                    </select>
                                </td>
                                
                            </tr>
                        <tbody>
                        <form ng-model="myform">    

                        </form>
                        </tbody>
                    </table>
                    
                </div>
            </div>
           
             <div id="modal-feature-list" class="modal modal-feature-list">
                <div class="modal-content">
                    <h5 class="text-c-red m-b-10">Signals <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    
                    <ul>
                        <li ng-repeat="fil in signal_list">
                            <a href="#" class="text-c-green" ng-click="add_signal_tab(fil.id)">
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
            this.data=[];
             
            $scope.models = [
                        { id:'1',mod: 'm1'},
                        { id:'2',mod: 'm2'},
                        { id:'3',mod: 'm3'},
                        { id:'4',mod: 'm4'}
                    ];              
            $scope.features = [
                        { id:'1',fea: 'feature1',stat:"Y,O,Y,N",touch:'No'},
                        { id:'2',fea: 'feature2',stat:'O,N,Y,N',touch:'No'},
                        { id:'3',fea: 'feature3',stat:'Y,Y,O,N',touch:'No'},
                        { id:'4',fea: 'feature4',stat:'Y,Y,N,O',touch:'No'},
                    ];    
            $scope.ecu_list = [ 
                { eid:'1',listitem:'ecu 1',description:'description 1'},
                { eid:'2',listitem:'ecu 2',description:'description 2'},
                { eid:'3',listitem:'ecu 3',description:'description 3'},
                { eid:'4',listitem:'ecu 4',description:'description 4'}
            ];
            $scope.signal_list = 
            [
                { sid:'1',listitem:'signal 1',description:'description 1'},
                { sid:'2',listitem:'signal 2',description:'description 2'},
                { sid:'3',listitem:'signal 3',description:'description 3'},
                { sid:'4',listitem:'signal 4',description:'description 4'}
            ];
            $scope.network = [
                { id:'1',listitem:'CAN1'},
                { id:'2',listitem:'CAN2'},
                { id:'3',listitem:'CAN3'},
                { id:'4',listitem:'CAN4'},
                { id:'1',listitem:'LIN1'},
                { id:'2',listitem:'LIN2'},
                { id:'3',listitem:'LIN3'},
                { id:'4',listitem:'LIN4'},
                { id:'1',listitem:'H/W1'},
                { id:'2',listitem:'H/W2'},
                { id:'3',listitem:'H/W3'},
                { id:'4',listitem:'H/W4'}
              ]; 
            $scope.assignpopulate = [];
            $scope.assignstart = function(id)
            {
                var index = -1;		
		var comArr = eval( $scope.features);
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].id === id ) 
                    {
                        index = i;
                        break;
                    }
		}
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
                
                //$scope.features.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
                $scope.assignpopulate.push({id:comArr[index].id,fea:comArr[index].fea,stat:comArr[index].stat});
                $scope.my = {id:comArr[index].id,fea:comArr[index].fea,stat:comArr[index].stat};
//                  $scope.assigner.push({id:'1',fea:'sd',stat:''});

//               alert($scope.assignpopulate);
            }
            $scope.add_signal_tab = function(sid)
            {				
		var index = -1;		
		var comArr = eval( $scope.signal_list );
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].id === sid ) 
                    {
                        index = i;
                        break;
                    }
		}
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
//                angular.element( document.querySelector( '.tab-pane' ) ).css('display','none');
//                var idr = '#signals';
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');
                $scope.sig = {id:comArr[index].id,listitem:comArr[index].listitem,description:comArr[index].description};
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