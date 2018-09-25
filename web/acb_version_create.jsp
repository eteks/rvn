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
                                                                <select ng-change="LoadPDBandIVN_Version()" ng-if="vehicle_list.length > 0" ng-model="data.vehiclename">
                                                                        <option value="{{veh.vehicle_id}}" ng-repeat="veh in vehicle_list">{{veh.vehiclename}}</option>                                                                    
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">PDB version:</label>
                                                                <select ng-model="data.pdbversion" ng-change="LoadSelectedPDBData()">
                                                                    <option value=""></option>
                                                                    <option value="{{pdb.id}}" ng-repeat="pdb in pdbversion">{{pdb.pdb_versionname}}</option> 
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">IVN version:</label>
                                                                <select ng-model="data.ivnversion" ng-change="LoadSelectedIVNData()">
                                                                    <option value=""></option>
                                                                    <option value="{{ivn.id}}" ng-repeat="ivn in ivnversion">{{ivn.ivn_versionname}}</option> 
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
                                                            <!--<button type="" class="btn grn-btn" ng-click="Confirm()"">Confirm Admin</button>-->
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
                   <!-- commented out because not getting the text value using ng-model. The code below under this commented code is working fine-->
<!--                   <select ng-model="ecu_tin" ng-change="">
                       <option  ng-repeat="i in ecu_list" value="{{i.eid}}">{{i.listitem}}</option>                                                                            
                    </select>-->
                    <select ng-model="ecu_tin" ng-options="i as i.listitem for i in ecu_list track by i.eid">           
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
                                    {{ip[$parent.$index][$index]}}
                                    <select id="ip_{{i.vmm_id}}" ng-attr-name="ip_{{$parent.$index}}_{{$index}}" ng-model="ip[$parent.$index][$index]"  ng-init=" ip[$parent.$index][$index] = 'lin1'"  data-pdbgroupid="{{i.pdbgroup_id}}">
                                    <!--<select id="ip_{{i.vmm_id}}" ng-model="{{$index}}_{{$parent.$index}}" ng-change="" data-pdbgroupid="{{i.pdbgroup_id}}">-->
                                    <option ng-repeat="i in getnetwork(i.vmm_id)" value="{{i.id}}" data-network="{{i.ntype}}" ng-selected="i.listitem == ip[$parent.$index][$index]">{{i.listitem}}</option>                                                                            
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
                
                <button ng-show="showSave == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createacbversion($event)" name="save">Save</button>
                <button ng-show="showSubmit == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="createacbversion($event)" name="submit">Submit</button>
                
            </div>  
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['ui.bootstrap']);

        app.controller('RecordCtrl1',function($scope, $http, $window, $location)
        {
            this.data1=[];
            this.data2=[]; 
            $scope.showSave =true;
            $scope.showSubmit =true;
            $scope.ecu_list = [];
            $scope.signal_list = [];
            $scope.network = [];
            $scope.list = [];
            var features_group = [];
//            $scope.list.features_group = [];
            
//            $scope.Confirm = function() {
//            alertModalInstance = $uibModal.open({
//              animation: $scope.animationsEnabled,
//              templateUrl: '<div></div>',
//              scope: $scope
//            });}
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
//               { id:'3',listitem:'H/W3',ntype:'hardware'},
//                { id:'4',listitem:'H/W4',ntype:'hardware'}
//              ];  
            $scope.assignpopulate = [];
            $scope.alt = function(idd,rdd)
            {
//                alert(idd+','+rdd);
            }
            $scope.assignstart = function(fid)
            {
                $scope.ip = [[],[]];
                alert("assignstart");
                if($scope.data.ivnversion != undefined){
                    $('.modal-trigger').leanModal();
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
//                    alert(JSON.stringify($scope.features));

//                    var temp=0;
//                    for(var j=0; j<$scope.list.length; j++)
//                    {
//                        if($scope.list[j].fid === comArr[index].fid)
//                        {
//                            temp=1;
//                        }   
//                    }
//                    if(temp==0)
//                    {
//                        $scope.list.push({fid:comArr[index].fid});
//                    }
                    
                    var result_pdbgroup;
                    var ecu_name;
                    $scope.features.filter(function(v,i){
                        if(v.fid == comArr[index].fid){
                            result_pdbgroup = v.pdbgroup_id;
                            ecu_name = v.ecu;
                        }
                    });
                    
                    $scope.models.filter(function(m,i){
                        $scope.models[i].pdbgroup_id = result_pdbgroup[i];
                    });
//                    alert(JSON.stringify(features_group));
                    var fg_group = features_group.filter(function(fg,key){
                        if(fg.fid == fid)
                            return fg;
                    });
//                    alert(JSON.stringify(fg_group));
                    if(fg_group.length > 0){
                        $scope.Demo.data1 = [];
                        $scope.Demo.data2 = [];
                        $scope.ecu_tin = {"eid":fg_group[0].ecu,"listitem":ecu_name};
//                        fg_group[0].cloned_data = [{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"can","nt_id":"1","vmm_id":"3"},{"pdbgroup_id":"10","nt_type":"can","nt_id":"2","vmm_id":"1"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"2"}]},{"signal":"2","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"lin","nt_id":"1","vmm_id":"3"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"2"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"lin","vmm_id":3},{"pdbgroup_id":"10","nt_type":"hardware","vmm_id":1},{"pdbgroup_id":"11","nt_type":"can","vmm_id":2}]},{"signal":"2","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"can","vmm_id":3},{"pdbgroup_id":"10","nt_type":"can","vmm_id":1},{"pdbgroup_id":"11","nt_type":"can","vmm_id":2}]}];
//                        fg_group[0].cloned_data = [{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"8","vmm_id":"2"},{"pdbgroup_id":"7","vmm_id":"1"},{"pdbgroup_id":"9","vmm_id":"3"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"8","vmm_id":2},{"pdbgroup_id":"7","vmm_id":1},{"pdbgroup_id":"9","vmm_id":3}]}];
                        var ip_signal = fg_group[0].cloned_data.filter(function(f){
                            if(f.signal_type== "input")
                                return f;
                        });
//                        alert(ip_signal.length);
                        var op_signal = fg_group[0].cloned_data.filter(function(f){
                            if(f.signal_type== "output")
                                return f;
                        });
//                        $scope.Demo.data1[$scope.Demo.data1.length] = {};
//                        $scope.Demo.data2[$scope.Demo.data2.length] = {};
                        $scope.Demo.data1 = [{},{}];
                        $scope.Demo.data2 = [{},{}];
//                        alert(op_signal.length);
//                        for(i=1;i<=ip_signal.length;i++)
//                            $scope.Demo.data1.push({});
//                        
//                        for(j=1;j<=op_signal.length;j++)
//                            $scope.Demo.data2.push({});
                          alert(JSON.stringify(ip_signal));
                          var inputcloned_data=document.getElementsByClassName('inputcloned_data');
                          for(i=0;i<ip_signal.length;i++){
                              $scope.op_signal(0,i);
                              $scope.add_signal_tab($scope.cen.ip,$scope.cen.pri,ip_signal[i].signal);
                              angular.forEach(inputcloned_data, function(value,key) {                                          
                                var select_tag = value.getElementsByTagName("select");                                
                                angular.forEach(select_tag, function(s, j) {
                                    angular.forEach(s.options, function(item,k){
                                        if(item.getAttribute('data-network')==ip_signal[i].group_data[j].nt_type &&
                                               item.getAttribute('value')== ip_signal[i].group_data[j].nt_id){
                                           $scope.ip[i][j] = item.text;
//                                              alert("if");
//                                                alert(ip_signal[i].group_data[j].nt_type);
//                                              item.setAttribute('ng-selected',"true");
//                                              $scope.ip = [{"0":"1","1":"2","2":"1"},{"0":"1","1":"2","2":"1"}];                                                                                                                                     
                                        }                                           
                                    });                               
                                });
                              });
                          }
                          for(i=0;i<op_signal.length;i++){
                              $scope.op_signal(1,i);
                              $scope.add_signal_tab($scope.cen.ip,$scope.cen.pri,op_signal[i].signal);
                          }                                                
                    }
                    else
                        $scope.ecu_tin = {};
                }
                else{
                    alert("Please choose the IVNversion");
                }
            }
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
//                alert(JSON.stringify($scope.Demo));
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
                  $scope.assignpopulate.push({f_id:fid,ecu:$scope.ecu_tin.eid,ip_signal:ip_signal,op_signal:op_signal,ip_sig_mod:$scope.ip_sig_mod,op_sig_mod:$scope.op_sig_mod});
//                alert(JSON.stringify($scope.assignpopulate));
                  alert("ecu_name"+$scope.ecu_tin.listitem);
                  var index = 0;		
                  var comArr = eval( $scope.features );
                  for(var i = 0; i < comArr.length; i++) 
                  {
                       if( comArr[i].fid === fid ) 
                       {
                           comArr[i].touch = 'Yes'
                           $scope.features[i].ecu=$scope.ecu_tin.listitem;
                           index = 1;
                           break;
                       }
                  }
//                  alert(JSON.stringify($scope.features));
                  var inputcloned_data=document.getElementsByClassName('inputcloned_data');
                  var outputcloned_data = document.getElementsByClassName('outputcloned_data');
//                  alert(outputcloned_data.length);
                  var touched_group ={};
                  if($scope.sigi.length == 0 || $scope.sigo.length == 0){
                      alert("Please add the Input and Output signals");
                  }
                  else{
                      touched_group['fid'] = fid;
                      touched_group['ecu'] = $scope.ecu_tin.eid;
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
                            cloned_data.push({'signal': $scope.sigi[i].sid,'signal_type':'output',
                                            'group_data':group_data});
                      });
                      
                      touched_group['cloned_data'] = cloned_data;

                      var list_index = -1;
                      features_group.filter(function(l,li){ 
                          if(l.fid == fid)
                              list_index = li;                          
                      });
//                      alert(JSON.stringify(features_group));
                      if(list_index != -1)
                          features_group.splice(list_index,1);
                      features_group.push(touched_group);
                      alert("features_group"+JSON.stringify(features_group));
                      
//                      var list_index = -1;
//                      $scope.list.filter(function(l,li){ 
//                          if(l.fid == fid)
//                              list_index = li;                          
//                      });
//                      if(list_index != -1)
//                          $scope.list.splice(list_index,1);
//                      $scope.list.push(touched_group);
//                      alert("list after filter"+JSON.stringify($scope.list));
                  }                 
                 
                  
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
//                    alert(JSON.stringify(response.data.pdb_map_result,null,4));
                    var result_data = response.data.pdb_map_result;
                    var vehicledetail_list = result_data.vehicledetail_list;
                    var featuredetail_list = result_data.featuredetail_list;
                    $scope.models = vehicledetail_list;
                    $scope.features = featuredetail_list;
                    $scope.features.filter(function(v,i){
                        $scope.features[i].pdbgroup_id = $scope.features[i].pdbgroup_id.split(",");
                    });
                    delete $scope.data.acbversion;
//                    alert(JSON.stringify($scope.features));
//                    angular.forEach(vehicledetail_list, function(value, key) {
//                        var variable = "network"+value.vmm_id;
//                        alert(variable);
//                        $scope[variable] = [];
////                        alert(variable);
////                       angular.for alert($scope[variable]);
//                    });
//                    $scope.network1 = [{"vmm_id":"2","id":"1","ntype":"can","listitem":"CAN1","status":true},{"vmm_id":"2","id":"2","ntype":"can","listitem":"CAN2","status":true},{"vmm_id":"2","id":"3","ntype":"can","listitem":"CAN3","status":true},{"vmm_id":"1","id":"3","ntype":"can","listitem":"CAN3","status":true}];
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
//                    alert(JSON.stringify(response.data.ivn_map_result,null,4));
                    var result_data = response.data.ivn_map_result;
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
                        $scope.ecu_list = result_data.ecu;
                        $scope.signal_list = result_data.signal;
//                        alert(JSON.stringify($scope.ecu_list));
                });
            };
            $scope.getnetwork = function(vmm_id) {
                var variable ="network"+vmm_id;
                return $scope[variable];
            };
            $scope.LoadPDBandIVN_Version = function() 
            {
//                alert($scope.data.vehicleversion);
//                alert($scope.data.vehiclename);
                $http({
                    url : 'loadpdb_and_ivnversion',
                    method : "POST",
                    data : {"vehicleversion_id":$scope.data.vehicleversion,"vehicle_id":$scope.data.vehiclename}
                })
                .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.result_data));
                    var pdbversion = response.data.result_data.pdbversion_list;
                    var ivnversion = response.data.result_data.ivnversion_list;
                    $scope.pdbversion = pdbversion;
                    $scope.ivnversion = ivnversion;
                    if(pdbversion.length == 0 && ivnversion.length == 0)
                        alert("Not yet created PDBVersion and IVNversion for this vehilce version");
                    else if(pdbversion.length == 0)
                        alert("Not yet created PBDVersion for this vehilce version");
                    else if(ivnversion.length == 0)
                        alert("Not yet created IVNVersion for this vehilce version");
                });
            }
            
            $scope.createacbversion = function (event) 
            {           
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false;  
                if($scope.data != undefined){
                    var data = {};
                    data['acbversion'] = $scope.data;
                    data['acbdata_list'] = features_group;
    //                data['acbdata_list'] = [{"fid":"3","ecu":"2","cloned_data":[{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"8","nt_type":"can","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"7","nt_type":"can","nt_id":"2","vmm_id":"1"},{"pdbgroup_id":"9","nt_type":"can","nt_id":"1","vmm_id":"3"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"8","nt_type":"lin","nt_id":"1","vmm_id":2},{"pdbgroup_id":"7","nt_type":"hardware","nt_id":"1","vmm_id":1},{"pdbgroup_id":"9","nt_type":"can","nt_id":"1","vmm_id":3}]}]},{"fid":"1","ecu":"1","cloned_data":[{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"can","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"3"}]},{"signal":"2","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"lin","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"3"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"can","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"3"}]},{"signal":"2","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"lin","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"3"}]}]}];
    //                data['acbdata_list'] = [{"fid":"1","ecu":"1","cloned_data":[{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"can","nt_id":"1","vmm_id":"3"},{"pdbgroup_id":"10","nt_type":"can","nt_id":"2","vmm_id":"1"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"2"}]},{"signal":"2","signal_type":"input","group_data":[{"pdbgroup_id":"12","nt_type":"lin","nt_id":"1","vmm_id":"3"},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":"1"},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":"2"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"lin","nt_id":"1","vmm_id":3},{"pdbgroup_id":"10","nt_type":"hardware","nt_id":"1","vmm_id":1},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":2}]},{"signal":"2","signal_type":"output","group_data":[{"pdbgroup_id":"12","nt_type":"can","nt_id":"1","vmm_id":3},{"pdbgroup_id":"10","nt_type":"can","nt_id":"2","vmm_id":1},{"pdbgroup_id":"11","nt_type":"can","nt_id":"1","vmm_id":2}]}]},{"fid":"3","ecu":"2","cloned_data":[{"signal":"1","signal_type":"input","group_data":[{"pdbgroup_id":"8","nt_type":"can","nt_id":"1","vmm_id":"2"},{"pdbgroup_id":"7","nt_type":"can","nt_id":"2","vmm_id":"1"},{"pdbgroup_id":"9","nt_type":"can","nt_id":"1","vmm_id":"3"}]},{"signal":"1","signal_type":"output","group_data":[{"pdbgroup_id":"8","nt_type":"lin","nt_id":"1","vmm_id":2},{"pdbgroup_id":"7","nt_type":"hardware","nt_id":"1","vmm_id":1},{"pdbgroup_id":"9","nt_type":"can","nt_id":"1","vmm_id":3}]}]}];
                    data['button_type'] = event.target.name;
                    alert(JSON.stringify(data));
                    list_count = Object.keys(features_group).length;
                    if($scope.data.ivnversion != undefined && $scope.data.pdbversion != undefined && 
                            $scope.data.vehicleversion != undefined && $scope.data.vehiclename != undefined){
                        if(list_count > 0){                 
                            $http({
                                url : 'createacbversion',
                                method : "POST",
                                data : data,
                            })
                            .then(function (data, status, headers, config){  
        //                              alert(JSON.stringify(data));
                                      alert(JSON.stringify(data.data.maps.status).slice(1, -1));
        //                              $window.open("ivn_version_listing.action","_self"); //                alert(data.maps);
        //            //                Materialize.toast(data['maps']["status"], 4000);
                            });
                        }
                        else{
                            alert("Please create aleast one touched features");
                        }   
                    }
                    else{
                        alert("Please fill above all the dependent version of ACB");
                    }
                }
                else{
                        alert("Please fill above all the dependent version of ACB");
                }
            }; 
            $scope.getUniqueValuesOfKey = function(array, key){
                return array.reduce(function(carry, item){
                    if(item[key] && !~carry.indexOf(item[key])) carry.push(item[key]);
                    return carry;
                }, []);
            };
            $scope.LoadACBPreviousVersion = function() 
            {
                $http({
                    url : 'loadacbpreviousvehicleversion_data',
                    method : "POST",
                    data : {"acbversion_id":$scope.data.acbversion}
                })
                .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.result_data));    
                    
//                    alert(JSON.stringify(response.data.result_data.acb_inputsignal)); 
//                    alert(JSON.stringify(response.data.result_data.acb_outputsignal)); 
                                       
                    // Fill the vehicle map result
                    var vehicle_result_data = response.data.result_data.vehmod_map_result;
                    $scope.vehicle_list = []; 
                    $scope.model_list = [];
                    $scope.vehicle_list.push({"vehicle_id":"","vehiclename":"Select"});
                    for(var i = 0; i < vehicle_result_data.length; i++) 
                    {
                         var data= vehicle_result_data[i];
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
                    
                    
                    
                    //Fill the PDB and IVN dropdown result
                    var pdbversion = response.data.result_data.pdb_ivn_result.pdbversion_list;
                    var ivnversion = response.data.result_data.pdb_ivn_result.ivnversion_list;
                    $scope.pdbversion = pdbversion;
                    $scope.ivnversion = ivnversion;
                    
                    // Fill the acb linked dropdown data result
                    var acbversion_result_data = response.data.result_data.acbversion[0];
                    $scope.data = {'vehicleversion': acbversion_result_data.vehicleversion,'vehiclename': acbversion_result_data.vehiclename,
                                   'pdbversion': acbversion_result_data.pdbversion, 'ivnversion': acbversion_result_data.ivnversion, 'acbversion':$scope.data.acbversion};
                    
                    // Fill the pdb map result
                    var pdb_result_data = response.data.result_data.pdb_map_result;
                    var vehicledetail_list = pdb_result_data.vehicledetail_list;
                    var featuredetail_list = pdb_result_data.featuredetail_list;
                    $scope.models = vehicledetail_list;
                    $scope.features = featuredetail_list;
                    $scope.features.filter(function(v,i){
                        $scope.features[i].pdbgroup_id = $scope.features[i].pdbgroup_id.split(",");
                    });
                    
                    // Fill the feature touched status
                    var acbgroup_result = response.data.result_data.acbgroup;
                    angular.forEach(acbgroup_result, function(value, key) {
//                        alert(JSON.stringify($scope.features));
                        $scope.features.filter(function(f,i){
                            if(value.fid == f.fid){
                                $scope.features[i].touch = 'Yes'
                                $scope.features[i].ecu=value.ecu_name;
                                $scope.features[i].ecu_id=value.ecu;
                            }
                        });
                        
                    });
                    
                    //Fill the ivn map result
                    var ivn_result_data = response.data.result_data.ivn_map_result;
                    var vehicledetail = ivn_result_data.vehicledetail_list;
                    angular.forEach(vehicledetail, function(value, key) {
                            var variable = "network"+value.vmm_id;
                            $scope[variable] = [];
                            ivn_result_data.can.filter(function(h){    
                                if(h.vmm_id == value.vmm_id)
                                    $scope[variable].push(h);
                            });
                            ivn_result_data.lin.filter(function(h){    
                                if(h.vmm_id == value.vmm_id)
                                    $scope[variable].push(h);
                            });
                            ivn_result_data.hardware.filter(function(h){    
                                if(h.vmm_id == value.vmm_id)
                                    $scope[variable].push(h);
                            });                           
                         })
                        $scope.ecu_list = ivn_result_data.ecu;
                        $scope.signal_list = ivn_result_data.signal;
//                        alert(JSON.stringify($scope.ecu_list));

                    //Fill the input and output signal for features group
                    var acb_ipsignal_result = response.data.result_data.acb_inputsignal;
                    var acb_opsignal_result = response.data.result_data.acb_outputsignal;
//                    alert(JSON.stringify(acb_ipsignal_result));
//                    alert(JSON.stringify(acb_opsignal_result));
                    
                    
//                    alert(JSON.stringify($scope.getUniqueValuesOfKey(acb_ipsignal_result, 'input_signal_id')));
                    var temp_input = $scope.getUniqueValuesOfKey(acb_ipsignal_result, 'input_signal_id');
                    var temp_output = $scope.getUniqueValuesOfKey(acb_opsignal_result, 'output_signal_id');                    
//                    alert(JSON.stringify($scope.features));
                    features_group = [];
                    $scope.features.filter(function(f,i){
                        var touched_group ={};
//                        var ip_result = [];
                        if(f.touch != undefined){
                            touched_group['fid'] = f.fid;
                            touched_group['ecu'] = f.ecu_id;
                            var cloned_data = [];
                            var signal_data = [];
                            angular.forEach(temp_input,function(t,key){                                                              
                                var ip_result = acb_ipsignal_result.filter(function(ip,key){
                                    if(ip.fid == f.fid && ip.input_signal_id == t)
                                       return ip;                                    
                                });
//                                alert(JSON.stringify(ip_result));
//                                alert(key);  
//                                alert(ip_result.length);
                                if(ip_result.length > 0){                                    
                                    var group_data = [];
                                    angular.forEach(ip_result,function(ip){
                                        group_data.push({'pdbgroup_id':ip.pdbversion_group_id,'nt_type':ip.network_type,
                                                        'nt_id':ip.input_network_id,'vmm_id':ip.vmm_id});
                                    });
                                    signal_data.push({'signal':t,'signal_type':'input','group_data':group_data}); 
                                }                          
                            });   
                            angular.forEach(temp_output,function(t,key){                                                              
                                var op_result = acb_opsignal_result.filter(function(op,key){
                                    if(op.fid == f.fid && op.output_signal_id == t)
                                       return op;                                    
                                });
//                                alert(key);  
//                                alert(ip_result.length);
                                if(op_result.length > 0){                                    
                                    var group_data = [];
                                    angular.forEach(op_result,function(ip){
                                        group_data.push({'pdbgroup_id':ip.pdbversion_group_id,'nt_type':ip.network_type,
                                                        'nt_id':ip.output_network_id,'vmm_id':ip.vmm_id});
                                    });
                                    signal_data.push({'signal':t,'signal_type':'output','group_data':group_data}); 
                                }                          
                            }); 
                            touched_group['cloned_data'] = signal_data;
                            features_group.push(touched_group);
                        }
                    });
                    alert("features_group"+JSON.stringify(features_group));                    
                });
            }
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
                                
                // Fill the vehicle map result
                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//                alert(JSON.stringify(result_data));
                var vehicle_result_data = result_data.vehmod_map_result;
                $scope.vehicle_list = []; 
                $scope.model_list = [];
                $scope.vehicle_list.push({"vehicle_id":"","vehiclename":"Select"});
                for(var i = 0; i < vehicle_result_data.length; i++) 
                {
                     var data= vehicle_result_data[i];
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



                //Fill the PDB and IVN dropdown result
                var pdbversion = result_data.pdb_ivn_result.pdbversion_list;
                var ivnversion = result_data.pdb_ivn_result.ivnversion_list;
                $scope.pdbversion = pdbversion;
                $scope.ivnversion = ivnversion;

                // Fill the acb linked dropdown data result
                var acbversion_result_data = result_data.acbversion[0];
                $scope.data = {'vehicleversion': acbversion_result_data.vehicleversion,'vehiclename': acbversion_result_data.vehiclename,
                               'pdbversion': acbversion_result_data.pdbversion, 'ivnversion': acbversion_result_data.ivnversion, 'acbversion':$scope.acbversion};

                // Fill the pdb map result
                var pdb_result_data = result_data.pdb_map_result;
                var vehicledetail_list = pdb_result_data.vehicledetail_list;
                var featuredetail_list = pdb_result_data.featuredetail_list;
                $scope.models = vehicledetail_list;
                $scope.features = featuredetail_list;
                $scope.features.filter(function(v,i){
                    $scope.features[i].pdbgroup_id = $scope.features[i].pdbgroup_id.split(",");
                });

                // Fill the feature touched status
                var acbgroup_result = result_data.acbgroup;
                angular.forEach(acbgroup_result, function(value, key) {
//                        alert(JSON.stringify($scope.features));
                    $scope.features.filter(function(f,i){
                        if(value.fid == f.fid){
                            $scope.features[i].touch = 'Yes'
                            $scope.features[i].ecu=value.ecu_name;
                            $scope.features[i].ecu_id=value.ecu;
                        }
                    });

                });

                //Fill the ivn map result
                var ivn_result_data = result_data.ivn_map_result;
                var vehicledetail = ivn_result_data.vehicledetail_list;
                angular.forEach(vehicledetail, function(value, key) {
                        var variable = "network"+value.vmm_id;
                        $scope[variable] = [];
                        ivn_result_data.can.filter(function(h){    
                            if(h.vmm_id == value.vmm_id)
                                $scope[variable].push(h);
                        });
                        ivn_result_data.lin.filter(function(h){    
                            if(h.vmm_id == value.vmm_id)
                                $scope[variable].push(h);
                        });
                        ivn_result_data.hardware.filter(function(h){    
                            if(h.vmm_id == value.vmm_id)
                                $scope[variable].push(h);
                        });                           
                     })
                    $scope.ecu_list = ivn_result_data.ecu;
                    $scope.signal_list = ivn_result_data.signal;
//                        alert(JSON.stringify($scope.ecu_list));

                //Fill the input and output signal for features group
                var acb_ipsignal_result = result_data.acb_inputsignal;
                var acb_opsignal_result = result_data.acb_outputsignal;
//                    alert(JSON.stringify(acb_ipsignal_result));
//                    alert(JSON.stringify(acb_opsignal_result));


//                    alert(JSON.stringify($scope.getUniqueValuesOfKey(acb_ipsignal_result, 'input_signal_id')));
                var temp_input = $scope.getUniqueValuesOfKey(acb_ipsignal_result, 'input_signal_id');
                var temp_output = $scope.getUniqueValuesOfKey(acb_opsignal_result, 'output_signal_id');                    
//                    alert(JSON.stringify($scope.features));
                features_group = [];
                $scope.features.filter(function(f,i){
                    var touched_group ={};
//                        var ip_result = [];
                    if(f.touch != undefined){
                        touched_group['fid'] = f.fid;
                        touched_group['ecu'] = f.ecu_id;
                        var cloned_data = [];
                        var signal_data = [];
                        angular.forEach(temp_input,function(t,key){                                                              
                            var ip_result = acb_ipsignal_result.filter(function(ip,key){
                                if(ip.fid == f.fid && ip.input_signal_id == t)
                                   return ip;                                    
                            });
//                                alert(JSON.stringify(ip_result));
//                                alert(key);  
//                                alert(ip_result.length);
                            if(ip_result.length > 0){                                    
                                var group_data = [];
                                angular.forEach(ip_result,function(ip){
                                    group_data.push({'pdbgroup_id':ip.pdbversion_group_id,'nt_type':ip.network_type,
                                                    'nt_id':ip.input_network_id,'vmm_id':ip.vmm_id});
                                });
                                signal_data.push({'signal':t,'signal_type':'input','group_data':group_data}); 
                            }                          
                        });   
                        angular.forEach(temp_output,function(t,key){                                                              
                            var op_result = acb_opsignal_result.filter(function(op,key){
                                if(op.fid == f.fid && op.output_signal_id == t)
                                   return op;                                    
                            });
//                                alert(key);  
//                                alert(ip_result.length);
                            if(op_result.length > 0){                                    
                                var group_data = [];
                                angular.forEach(op_result,function(ip){
                                    group_data.push({'pdbgroup_id':ip.pdbversion_group_id,'nt_type':ip.network_type,
                                                    'nt_id':ip.output_network_id,'vmm_id':ip.vmm_id});
                                });
                                signal_data.push({'signal':t,'signal_type':'output','group_data':group_data}); 
                            }                          
                        }); 
                        touched_group['cloned_data'] = signal_data;
                        features_group.push(touched_group);
                    }
                });
//                alert("features_group"+JSON.stringify(features_group));   
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