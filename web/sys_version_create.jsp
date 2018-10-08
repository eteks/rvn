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
                                                                <select ng-model="data.acbversion" ng-change="LoadSelectedACBData()">
                                                                    <option value=""></option>
                                                                    <option value="{{acb.id}}" ng-repeat="acb in acbversion">{{acb.acb_versionname}}</option> 
                                                                </select>
                                                                <select ng-change="LoadACBPreviousVersion($event)" ng-focus="focusCallback($event)" ng-if="acbsubversion.length > 0" ng-model="data.acbsubversion" data="subversion">
                                                                    <option value="{{acb.id}}" ng-repeat="acb in acbsubversion">{{acb.acb_versionname}}</option>                                                                    
                                                                </select>
                                                            </div>
                                                             <div class="form-group col-md-3">
                                                                <label for="vehicle">ECU:</label>
                                                                <select  ng-model="myOption" ng-change="assignstart(myOption)"
                                                                        ng-options="ecu_list.eid as ecu_list.listitem for ecu_list in ecu_list">
                                                                        <option>--</option>
                                                                </select>
                                                            </div>
                                                           
                                                        </div>   
                                                        <div class="col-lg-12">
                                                            <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                    <tr>                                                                    
                                                                        
                                                                        <th class="">Features</th>
                                                                        <th class="text-center" ng-repeat="x in (this_variant.variants | customSplitString) track by $index">
                                                                            {{x | uppercase}}
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                    <tr dir-paginate="record in features|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        
                                                                        <td class="">
                                                                                {{record.featurename}}
                                                                        </td>
<!--                                                                    <td class="text-center" ng-repeat="x in (record.stat | customSplitString)">-->
                                                                        <td class="text-center" ng-repeat="x in (this_variant.variants | customSplitString) track by $index">
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">                                                                                
                                                                                <input type="radio" ng-click="radiovalue(record.fid,$index,'y')" name="f{{record.fid}}_{{$index}}" value="y" class="radio_button">
                                                                                <span class="checkmark c_b_g">                                                                                    
                                                                                </span>
                                                                                <span class="tooltip-content2">yes</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,$index,'n')" name="f{{record.fid}}_{{$index}}" value="n" class="radio_button">
                                                                                <span class="checkmark c_b_r"></span>
                                                                                <span class="tooltip-content2">no</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,$index,'o')" name="f{{record.fid}}_{{$index}}" value="o" class="radio_button">    
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
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data=[];
             
            $scope.features = [
                        { fid:'1',featurename: 'feature1',status:"Y,O,Y,N",touch:'No'},
                        { fid:'2',featurename: 'feature2',status:'O,N,Y,N',touch:'No'},
                        { fid:'3',featurename: 'feature3',status:'Y,Y,O,N',touch:'No'},
                        { fid:'4',featurename: 'feature4',status:'Y,Y,N,O',touch:'No'},
                    ];    
            $scope.ecu_list = [ 
                { eid:'1',listitem:'ecu 1',description:'description 1',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'2',listitem:'ecu 2',description:'description 2',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'3',listitem:'ecu 3',description:'description 3',status:'true',variant_status:'true',variants:'high,mid,low'},
                { eid:'4',listitem:'ecu 4',description:'description 4',status:'true',variant_status:'true',variants:'high,mid,low'}
            ];
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
                
                $scope.this_variant = {eid:comArr[index].eid,listitem:comArr[index].listitem,variants:comArr[index].variants};
//                alert(JSON.stringify($scope.this_variant));
            };
            $scope.radiovalue = function(dfm_id,vmm_id,status)
            {		
                alert(vmm_id);
//                if($scope.list.length === 0)
//                {
//                    $scope.list.push({vmm_id:vmm_id,dfm_id:dfm_id,status:status});
//                }
//                else
//                {
//                    var temp=0;
//                    for(var i=0; i<$scope.list.length; i++)
//                    {
//                        if(($scope.list[i].vmm_id === vmm_id) && ($scope.list[i].dfm_id === dfm_id))
//                        {
//                            $scope.list[i].status=status;
//                            temp=1;
//                        }
//                        
//                    }
//                    if(temp==0)
//                    {
//                        $scope.list.push({vmm_id:vmm_id,dfm_id:dfm_id,status:status});
//                    }
//                }
//                alert(JSON.stringify($scope.list))
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
        $scope.LoadSelectedACBData = function() 
        {
//                alert($scope.data.vehicleversion);
//                alert($scope.data.vehiclename);
            $http({
                url : 'loadselectedacbdata_for_systemversion',
                method : "POST",
                data : {"acbversion_id":$scope.data.acbversion,"vehicleversion_id":$scope.data.vehicleversion,"vehicle_id":$scope.data.vehiclename}
            })
            .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.acb_result_data));
            $scope.acbsubversion = response.data.acb_result_data.acbsubversion;
//                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
////                alert(result_data);
//                var acbversion = response.data.result_data;
//                $scope.acbversion = acbversion;
//                if(acbversion.length == 0)
//                    alert("Not yet created ACBVersion for this vehicle version");
            });
        }
        
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