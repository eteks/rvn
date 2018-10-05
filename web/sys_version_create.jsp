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
                                                                <select ng-model="data.pdbversion" ng-change="LoadSelectedPDBData()">
                                                                    <s:iterator value="pdbversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="pdb_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-3">
                                                                <label for="vehicle">Vehicle:</label>
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
                             <select class="form-control form-control-primary" ng-model="ecu_tin" ng-options="i as i.listitem for i in ecu_list track by i.eid">           
                             </select>
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
            this.data1=[];
            this.data2=[]; 
             
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