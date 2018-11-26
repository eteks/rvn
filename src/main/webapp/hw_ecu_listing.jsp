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
                                                    <i class="icofont icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>IVN Engineer</h4>
                                                        <span>Network and ECU</span>    
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
                                                            <s:url action="ivn_engineer.action" var="aURL" />
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
                                                         
                                                        <div class="col-lg-12  p-t-30">
                                                                
                                                                <!-- Nav tabs -->
                                                                <ul class="nav nav-tabs  tabs" role="tablist">
                                                                    <li class="nav-item">
                                                                        <a class="nav-link" data-toggle="tab" href="#can" role="tab">CAN</a>
                                                                    </li>
                                                                    <li class="nav-item">
                                                                        <a class="nav-link" data-toggle="tab" href="#lin" role="tab">LIN</a>
                                                                    </li>
                                                                    <li class="nav-item">
                                                                        <a class="nav-link" data-toggle="tab" href="#hardware" role="tab">Hardware</a>
                                                                    </li>
                                                                    <li class="nav-item">
                                                                        <a class="nav-link" data-toggle="tab" href="#signals" role="tab">Signals</a>
                                                                    </li>
                                                                    <li class="nav-item">
                                                                        <a class="nav-link" data-toggle="tab" href="#ecu" role="tab">ECU</a>
                                                                    </li>
                                                                </ul>
                                                                <!-- Tab panes -->
                                                                <div class="tab-content tabs card-block">
                                                                    <div class="tab-pane" id="can" role="tabpanel">
                                                                        <form class="form-inline mt-3">
                                                                            <div class="form-group">
                                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                                            </div>
                                                                        </form>
                                                                        <table st-table="rowCollection" class="table table-striped">
                                                                                <thead>
                                                                                <tr>

                                                                                    <th class="text-center">CAN</th>                                                                                   

                                                                                </tr>
                                                                                </thead>

                                                                                <tbody>

                                                                                    <tr ng-repeat="record in cans">

                                                                                        <td class="text-center">
                                                                                            {{record.listitem}}
                                                                                        </td>

                                                                                        <td class="text-center" ng-repeat="i in models">                                                                                             
                                                                                            <div class="border-checkbox-section">                                                                                    
                                                                                                <div class="border-checkbox-group border-checkbox-group-success">
                                                                                                    <input class="border-checkbox" type="checkbox" id="checkbox_c_{{record.cid}}_{{i.id}}">
                                                                                                    <label class="border-checkbox-label" for="checkbox_c_{{record.cid}}_{{i.id}}"></label>
                                                                                                </div>
                                                                                            </div>
                                                                                        </td>
                                                                                        
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                    </div>
                                                                    
                                                                    <div class="tab-pane" id="lin" role="tabpanel">                                                                        
                                                                        <table st-table="rowCollection" class="table table-striped">
                                                                                <thead>
                                                                                <tr>

                                                                                    <th class="text-center">LIN</th>
                                                                                    <th class="text-center" ng-repeat="i in models">
                                                                                        {{i.mod}}
                                                                                    </th>

                                                                                </tr>
                                                                                </thead>

                                                                                <tbody>

                                                                                    <tr ng-repeat="record in lin">

                                                                                        <td class="text-center">
                                                                                            {{record.listitem}}
                                                                                        </td>

                                                                                        <td class="text-center" ng-repeat="i in models">                                                                                             
                                                                                            <div class="border-checkbox-section">                                                                                    
                                                                                                <div class="border-checkbox-group border-checkbox-group-success">
                                                                                                    <input class="border-checkbox" type="checkbox" id="checkbox_l_{{record.lid}}_{{i.id}}">
                                                                                                    <label class="border-checkbox-label" for="checkbox_l_{{record.lid}}_{{i.id}}"></label>
                                                                                                </div>
                                                                                            </div>
                                                                                        </td>
                                                                                        
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                    </div>
                                                                    <div class="tab-pane" id="hardware" role="tabpanel">
                                                                        <table st-table="rowCollection" class="table table-striped">
                                                                                <thead>
                                                                                <tr>
                                                                                    <th class="text-center">Hardware</th>
                                                                                    <th class="text-center" ng-repeat="i in models">
                                                                                        {{i.mod}}
                                                                                    </th>
                                                                                </tr>
                                                                                </thead>

                                                                                <tbody>

                                                                                    <tr ng-repeat="record in hw">

                                                                                        <td class="text-center">
                                                                                            {{record.listitem}}
                                                                                        </td>

                                                                                        <td class="text-center" ng-repeat="i in models">                                                                                             
                                                                                            <div class="border-checkbox-section">                                                                                    
                                                                                                <div class="border-checkbox-group border-checkbox-group-success">
                                                                                                    <input class="border-checkbox" type="checkbox" id="checkbox_h_{{record.hid}}_{{i.id}}">
                                                                                                    <label class="border-checkbox-label" for="checkbox_h_{{record.hid}}_{{i.id}}"></label>
                                                                                                </div>
                                                                                            </div>
                                                                                        </td>
                                                                                        
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                    </div>
                                                                    <div class="tab-pane" id="signals" role="tabpanel">
                                                                        <div class="col-lg-12">
                                                                                <div class="card">
                                                                                    <div class="card-block accordion-block">
                                                                                        <div id="accordion" role="tablist" aria-multiselectable="true">
                                                                                            
                                                                                            <div class="accordion-panel" ng-repeat="s in signal">
                                                                                                <div class="accordion-heading" role="tab" id="heading{{s.sid}}">
                                                                                                    <h3 class="card-title accordion-title">
                                                                                                    <a class="accordion-msg" data-toggle="collapse"
                                                                                                       data-parent="#accordion" href="#collapse{{s.sid}}"
                                                                                                       aria-expanded="true" aria-controls="collapse{{s.sid}}">
                                                                                                        {{s.listitem}}
                                                                                                    </a>
                                                                                                    <a href="#" ng-click="removeSignalRow(s.sid)" class="removeSignalRow"><i class="icofont icofont-ui-close text-c-red"></i></a>    
                                                                                                </h3>
                                                                                                </div>
                                                                                                <div id="collapse{{s.sid}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading{{s.sid}}">
                                                                                                    <div class="accordion-content accordion-desc">
                                                                                                        
                                                                                                        
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>                                                                                           
                                                                                            
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                    </div>
                                                                    <div class="tab-pane" id="ecu" role="tabpanel">
                                                                        
                                                                           
                                                                                <div ng-repeat="e in ecu">
                                                                                    <a href="#" ng-click="removeEcuRow(e.eid)" class="removeEcuRow"><i class="icofont icofont-ui-close text-c-red"></i></a>
                                                                                    <div class="border-checkbox-section check_pan">                                                                                    
                                                                                        <div class="border-checkbox-group border-checkbox-group-success">
                                                                                            <input class="border-checkbox" type="checkbox" id="checkbox_eu_{{e.eid}}">
                                                                                            <label class="border-checkbox-label" for="checkbox_eu_{{e.eid}}"></label>
                                                                                        </div>
                                                                                    </div>
                                                                                    <label>{{e.listitem}} ({{e.description}})</label>
                                                                                    
                                                                                </div>
                                                                        
                                                                    </div>
                                                                    
                                                                </div>
                                                            </div>                                               
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
            <!--<form class=""  name="myForm">-->
                <!-- modal for for creating new product -->
                <div id="modal-product-form" class="modal">
                    <div class="modal-content">
                        <h5 class="text-c-red m-b-25">Add Network / Signal / ECU
                            <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" >
                                <i class="icofont icofont-ui-close"></i>
                            </a>
                        </h5>
                       
                            <div class="form-group">
                                <!--<label for="name">Domain</label>-->
                                <select ng-model="network" ng-change="SelectNetwork()" class="col-lg-12">
                                    <option value="" disabled selected>Select your Network</option>
                                    <option value="can">CAN</option>
                                    <option value="lin">LIN</option>
                                    <option value="hardware">H/W</option>
                                    <option value="signals">Signal</option>
                                    <option value="ecu">ECU</option>
                                </select>
                            </div>
                                <div class="" ng-if="network != 'signals'">
                                     <div ng-repeat="data in Demo.data">              
                                        <div class="form-group">
                                        <!--<label for="name">Feature</label>-->
                                        <input ng-model="name" type="text" class="validate col-lg-12" id="form-name" placeholder="Name"/>
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
                            </div>
                            <div class="signal_attr row" ng-if="network === 'signals'">              
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="name" type="text" class="validate" id="form-name" placeholder="Name"/>
                                </div>
                                 <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="alias" type="text" class="validate" id="form-name" placeholder="alias"/>
                                </div> 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="length" type="text" class="validate" id="form-name" placeholder="Length"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="byte" type="text" class="validate" id="form-name" placeholder="Byte Order"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="unit" type="text" class="validate" id="form-name" placeholder="Unit"/>
                                </div>
                                
                                <div class="form-group col-lg-6">
                                    <select ng-model="selectvalue" ng-change="Selectvalue()">
                                        <option value="" disabled selected>Select your Value</option>
                                        <option value="unsign">Unsigned</option>
                                        <option value="sign">Signed</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="initvalue" type="text" class="validate" id="form-name" placeholder="Init Value"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="factor" type="text" class="validate" id="form-name" placeholder="factor"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="offset" type="text" class="validate" id="form-name" placeholder="Offset"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="minimum" type="text" class="validate" id="form-name" placeholder="Minimum"/>
                                </div> 
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="maximum" type="text" class="validate" id="form-name" placeholder="Maximum"/>
                                </div>
                                 
                                 <div class="form-group col-lg-6">
                                    <select ng-model="valuetable" ng-change="">
                                        <option value="" disabled selected>Select your Value Table</option>
                                        <option value="valuetable_1">valuetable_1</option>
                                        <option value="valuetable_2">valuetable_2</option>
                                    </select>
                                </div> 
                                
                                <div class="form-group col-lg-6">                                    
                                    <input ng-model="maximum" type="checkbox" class="validate"/>
                                    <label for="name">Automatic Min-Max Calculation</label>
                                </div>
                                 
                                <div class="form-group col-lg-12">
                                    <textarea ng-model="data.description" type="text" class="validate materialize-textarea  col-lg-12" placeholder="Description"></textarea>
                                    <label for="description">Description</label>
                                </div>
                                
                                <div class="form-group col-lg-12 pdb_sig_assign">
                                    <label>CAN </label> : 
                                    <div ng-repeat="c in cans">
                                        <label>{{c.listitem}}</label>
                                        <div class="border-checkbox-section check_pan">                                                                                    
                                            <div class="border-checkbox-group border-checkbox-group-success">
                                                <input class="border-checkbox" type="checkbox" id="checkbox_ca_{{s.sid}}_{{c.cid}}">
                                                <label class="border-checkbox-label" for="checkbox_ca_{{s.sid}}_{{c.cid}}"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group col-lg-12 pdb_sig_assign">
                                    <label>LIN </label> : 
                                    <div ng-repeat="l in lin">
                                        <label>{{l.listitem}}</label>
                                        <div class="border-checkbox-section check_pan">                                                                                    
                                            <div class="border-checkbox-group border-checkbox-group-success">
                                                <input class="border-checkbox" type="checkbox" id="checkbox_li_{{s.sid}}_{{l.lid}}">
                                                <label class="border-checkbox-label" for="checkbox_li_{{s.sid}}_{{l.lid}}"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group col-lg-12 pdb_sig_assign">
                                    <label>H/W </label> : 
                                    <div ng-repeat="h in hw">
                                        <label>{{h.listitem}}</label>
                                        <div class="border-checkbox-section check_pan">                                                                                    
                                            <div class="border-checkbox-group border-checkbox-group-success">
                                                <input class="border-checkbox" type="checkbox" id="checkbox_hw_{{s.sid}}_{{h.hid}}">
                                                <label class="border-checkbox-label" for="checkbox_hw_{{s.sid}}_{{h.hid}}"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            
                            <div class="input-field text-right">
                                <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature_and_domain()" ng-mousedown='doSubmit=true' name="add">Add</button>
                            </div>
                            
                    </div>
                </div>
                <!-- floating button for creating product -->
            <!--</form>-->
            
            <div id="modal-signal-list" class="modal modal-feature-list">
                <div class="modal-content">
                    <h5 class="text-c-red m-b-10">Signals <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    
                    <ul>
                        <li ng-repeat="fil in signal_list">
                            <a href="#" class="text-c-green" ng-click="add_signal_tab(fil.sid)">
                                <i class="icofont icofont-ui-add"></i>
                            </a>&nbsp;{{fil.listitem}}&nbsp;({{fil.description}})
                        </li>
                    </ul>
                    
                </div>
            </div>
           
             <div id="modal-ecu-list" class="modal modal-feature-list">
                <div class="modal-content">
                    
                    <h5 class="text-c-red m-b-10">ECU <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                    
                    <ul>
                        <li ng-repeat="fil in ecu_list">
                            <a href="#" class="text-c-green" ng-click="add_ecu_tab(fil.eid)">
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

            network_list = JSON.parse("<s:property value="network_list_obj"/>".replace(/&quot;/g,'"'));
            $scope.cans = network_list.can_list;
            $scope.lin = network_list.lin_list;
            $scope.hw = network_list.hardware_list;
              
            $scope.signal = [];
              
            $scope.ecu = [];

              ecu_list = JSON.parse("<s:property value="eculist_result_obj"/>".replace(/&quot;/g,'"'));
              $scope.ecu_list = ecu_list;

              signal_list = JSON.parse("<s:property value="signallist_result_obj"/>".replace(/&quot;/g,'"'));
              $scope.signal_list = signal_list;
            
//            var features_list = JSON.parse("<s:property value="featureslist_result_obj"/>".replace(/&quot;/g,'"'));
//            $scope.features_list = features_list;
                     
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            
            $scope.add_signal_tab = function(sid)
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
//                angular.element( document.querySelector( '.tab-pane' ) ).css('display','none');
//                var idr = '#signals';
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');
                $scope.signal.push({sid:comArr[index].sid,listitem:comArr[index].listitem,description: comArr[index].description})
		$scope.signal_list.splice( index, 1 );
            };
            
            $scope.removeSignalRow = function(sid)
            {				
		var index = -1;		
		var comArr = eval( $scope.signal );
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
                $scope.signal_list.push({sid:comArr[index].sid,listitem:comArr[index].listitem,description: comArr[index].description})
		$scope.signal.splice( index, 1 );		
            };
            
            $scope.add_ecu_tab = function(eid)
            {				
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
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
//                angular.element( document.querySelector( '.tab-pane' ) ).css('display','none');
//                var idr = '#ecu';
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');

                $scope.ecu.push({eid:comArr[index].eid,listitem:comArr[index].listitem,description: comArr[index].description})
		$scope.ecu_list.splice( index, 1 );
                
            };     
            $scope.removeEcuRow = function(eid)
            {				
		var index = -1;		
		var comArr = eval( $scope.ecu);
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].eid === eid ) 
                    {
                        index = i;
                        break;
                    }
		}
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
                $scope.ecu_list.push({eid:comArr[index].eid,listitem:comArr[index].listitem,description: comArr[index].description})
		$scope.ecu.splice( index, 1 );		
            };
            $scope.SelectNetwork = function()
            {
                
//                if($scope.data.network === 'can')
//                {
//                    
//                }
//                else if($scope.data.network === 'lin')
//                {
////                   var myEl = angular.element( document.querySelector( '#lin' ) );
////                    myEl.css('display','block');
//                }
//                else if($scope.data.network === 'hardware')
//                {
//                    
//                }
//                else if($scope.data.network === 'signals')
//                {
////                    $scope.signal.push({sid:comArr[index].sid,listitem:comArr[index].listitem,desc: comArr[index].desc})
//                }
//                else if($scope.data.network === 'ecu')
//                {
////                    $scope.ecu.push({eid:comArr[index].eid,listitem:comArr[index].listitem,desc: comArr[index].desc})
//                }
//                else
//                {   
//                   
////                    $scope.ecu.push({eid:comArr[index].sid,listitem:comArr[index].listitem,desc: comArr[index].desc})
//                }
//                var cls=angular.element( document.getElementsByClassName('tab-pane'));
//                cls.css('display','none');
//                var idr = '#' + $scope.data.network;
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');
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
                $scope.models = [];
                for(var i = 0; i < $scope.model_list.length; i++) 
                {
                   var data = $scope.model_list[i];
                   if(data.vehicle_id == selected_vehicleid)
                   {
//                       alert(data.vehicle_mapping_id);
                        angular.forEach(data.mod, function(value, key) {
                            $scope.models.push({
                             "mod":value,
                             "id":data.vehicle_mapping_id[key],
                            }); 
                        })
                   }
                }
//                alert(JSON.stringify($scope.records));
//                var cls=angular.element( document.getElementsByClassName( 'tab-pane' ) );
//                cls.css('display','none');
//                var idr = '#can';
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');
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

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
//            $scope.models = [
//                        { id:'1',mod: 'm1'},
//                        { id:'2',mod: 'm2'},
//                        { id:'3',mod: 'm3'},
//                        { id:'4',mod: 'm4'},
//                    ];              
//            $scope.cans = [
//                { cid:'1',listitem:'CAN1'},
//                { cid:'2',listitem:'CAN2'},
//                { cid:'3',listitem:'CAN3'},
//                { cid:'4',listitem:'CAN4'}
//              ];       
//            $scope.lin = [
//                { lid:'1',listitem:'LIN1'},
//                { lid:'2',listitem:'LIN2'},
//                { lid:'3',listitem:'LIN3'},
//                { lid:'4',listitem:'LIN4'}
//              ];           
//            $scope.hw = [
//                { hid:'1',listitem:'H/W1'},
//                { hid:'2',listitem:'H/W2'},
//                { hid:'3',listitem:'H/W3'},
//                { hid:'4',listitem:'H/W4'}
//              ]; 
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
    </script>   
</body>

</html>                                            