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
                                                        <span>IVN Version Creation</span>    
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
                                                                <label for="vehicle">IVN version :</label>
                                                                <select ng-model="data.ivnversion" ng-change="LoadIVNPreviousVersion()">
                                                                    <s:iterator value="ivnversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="ivn_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                             
                                                            <a class="feature_add sig_add modal-trigger" href="#modal-signal-list">
                                                                <i class="icofont icofont-drag text-c-green"></i>
                                                                Signal List
                                                            </a>
                                                             <a class="feature_add ecu_add modal-trigger" href="#modal-ecu-list">
                                                                <i class="icofont icofont-drag1 text-c-green"></i>
                                                                Ecu List
                                                            </a>
                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()">Add</a>
                                                            
                                                        </div>   
                                                        <div class="col-lg-12">
                                                                
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
                                                                        <table st-table="rowCollection" class="table table-striped">
                                                                                <thead>
                                                                                <tr>

                                                                                    <th class="text-center">CAN</th>
                                                                                    <th class="text-center" ng-repeat="i in models">
                                                                                        {{i.mod}}
                                                                                    </th>

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
                                                                                                    <input class="border-checkbox checkbox_can_act" type="checkbox" id="checkbox_c_{{record.cid}}_{{i.id}}" ng-click="ivnnetwork_checkbox(record.cid,i.id,'can',nw_checkbox)" ng-model="nw_checkbox">
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
                                                                                                    <input class="border-checkbox checkbox_lin_act" type="checkbox" id="checkbox_l_{{record.lid}}_{{i.id}}" ng-click="ivnnetwork_checkbox(record.lid,i.id,'lin',nw_checkbox)" ng-model="nw_checkbox">
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
                                                                                                    <input class="border-checkbox checkbox_hardware_act" type="checkbox" id="checkbox_h_{{record.hid}}_{{i.id}}" ng-click="ivnnetwork_checkbox(record.hid,i.id,'hardware',nw_checkbox)" ng-model="nw_checkbox">
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
                                <select ng-model="data.network" ng-change="SelectNetwork()" class="col-lg-12">
                                    <option value="" selected>Select your Network</option>
                                    <option value="can">CAN</option>
                                    <option value="lin">LIN</option>
                                    <option value="hardware">H/W</option>
                                    <option value="signals">Signal</option>
                                    <option value="ecu">ECU</option>
                                </select>
                            </div>
                                <div class="" ng-if="data.network != 'signals'">
                                     <div ng-repeat="data in Demo.data">              
                                        <div class="form-group">
                                        <!--<label for="name">Feature</label>-->
                                        <input ng-model="data.name" type="text" class="validate col-lg-12" id="form-name" placeholder="Name"/>
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
                            <div class="signal_attr row" ng-if="data.network === 'signals'">              
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.name" type="text" class="validate" id="form-name" placeholder="Name"/>
                                </div>
                                 <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.alias" type="text" class="validate" id="form-name" placeholder="alias"/>
                                </div> 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.length" type="text" class="validate" id="form-name" placeholder="Length"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.byteorder" type="text" class="validate" id="form-name" placeholder="Byte Order"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.unit" type="text" class="validate" id="form-name" placeholder="Unit"/>
                                </div>
                                
                                <div class="form-group col-lg-6">
                                    <select ng-model="data.valuetype" ng-change="Selectvalue()">
                                        <option value="" selected>Select your Value</option>
                                        <option value="unsign">Unsigned</option>
                                        <option value="sign">Signed</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.initvalue" type="text" class="validate" id="form-name" placeholder="Init Value"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.factor" type="text" class="validate" id="form-name" placeholder="factor"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.offset" type="text" class="validate" id="form-name" placeholder="Offset"/>
                                </div>
                                 
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.minimum" type="text" class="validate" id="form-name" placeholder="Minimum"/>
                                </div> 
                                
                                <div class="form-group col-lg-6">
                                    <!--<label for="name">Feature</label>-->
                                    <input ng-model="data.maximum" type="text" class="validate" id="form-name" placeholder="Maximum"/>
                                </div>
                                 
                                 <div class="form-group col-lg-6">
                                    <select ng-model="data.valuetable" ng-change="">
                                        <option value="" selected>Select your Value Table</option>
                                        <option value="valuetable_1">valuetable_1</option>
                                        <option value="valuetable_2">valuetable_2</option>
                                    </select>
                                </div> 
                                
                                <div class="form-group col-lg-6">                                    
<!--                                    <input ng-model="data.maximum" type="checkbox" class="validate"/>-->
                                    <input type="checkbox" class="validate"/>
                                    <label for="name">Automatic Min-Max Calculation</label>
                                </div>
                                 
                                <div class="form-group col-lg-12">
                                    <textarea ng-model="data.description" type="text" class="validate materialize-textarea  col-lg-12" placeholder="Description"></textarea>
                                    <!--<label for="description">Description</label>-->
                                </div>
                                
                                <div class="form-group col-lg-12 pdb_sig_assign">
                                    <label>CAN </label> : 
                                    <div ng-repeat="c in cans">
                                        <label>{{c.listitem}}</label>
                                        <div class="border-checkbox-section check_pan">                                                                                    
                                            <div class="border-checkbox-group border-checkbox-group-success">
                                                <input class="border-checkbox" type="checkbox" id="checkbox_ca_{{s.sid}}_{{c.cid}}" ng-click="checkboxvalue('can',c.cid)">
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
                                                <input class="border-checkbox" type="checkbox" id="checkbox_li_{{s.sid}}_{{l.lid}}" ng-click="checkboxvalue('lin',l.lid)">
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
                                                <input class="border-checkbox" type="checkbox" id="checkbox_hw_{{s.sid}}_{{h.hid}}" ng-click="checkboxvalue('hardware',h.hid)">
                                                <label class="border-checkbox-label" for="checkbox_hw_{{s.sid}}_{{h.hid}}"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            
                            <div class="input-field text-right">
                                <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="create_ivn_required_attributes()" ng-mousedown='doSubmit=true' name="add">Add</button>
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
                <a class="modal-trigger float-left text-c-green" style="font-weight:600" href="#modal-upload" style="text-decoration:underline;" ng-click="assignstart(record.fid)">
                    Import
                </a>
                <div id="modal-upload" class="modal">
                    <div class="modal-content">
                        <h5 class="text-c-red m-b-10"><a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
<!--                        <div class="float-left">
                            <input type="file" name="userImport" label="User File" />
                             <button  class="btn btn-primary">Import</button>
                        </div>-->
                        <div ng-controller = "fileCtrl" class="float-left">
                            <input type = "file" name="userImport" file-model = "myFile" accept=".csv"/>
                            <button class="btn btn-primary" ng-click = "uploadFile()">Import Signal</button>
                            <button class="btn btn-primary" ng-click = "uploadIVN()">Import IVN Version</button>
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
                
                <button ng-show="showSave == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="checkNotify('save')" name="save">Save</button>
                <button ng-show="showSubmit == true" type="submit" class="btn btn-primary" ng-mousedown='doSubmit=true' ng-click="checkNotify('submit')" name="submit">Submit</button>
                
            </div>  
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window, $location)
        {
            this.data=[];
            var notification_to;
            $scope.showSave =true;
            $scope.showSubmit =true;
            $scope.$on('notifyValue', function (event, args) {
                notification_to = args;
                $scope.createivnversion("submit");
            });
            $scope.data = {};
//            $scope.list = {
//                "signal":[1,2],
//                "can":[{"vmm_id":"1","network_id":1,"status":true,"network_type":"can"},{"vmm_id":"2","network_id":1,"status":true,"network_type":"can"}],
//                "lin":[{"vmm_id":"1","network_id":1,"status":true,"network_type":"lin"},{"vmm_id":"2","network_id":1,"status":true,"network_type":"lin"}],
//                "hardware":[{"vmm_id":"1","network_id":2,"status":true,"network_type":"hardware"},{"vmm_id":"2","network_id":2,"status":true,"network_type":"hardware"}],
//                "ecu":[1,3]
//            };
            $scope.list = {};
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
//                    alert(sid+" and "+comArr[i].sid);
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
                if($scope.list.signal == undefined){
                    $scope.list.signal = [];
                }
                $scope.list.signal.push(comArr[index].sid);
                $scope.signal_list.splice( index, 1 );
//                alert(JSON.stringify($scope.list));
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
                $scope.list.signal.splice(index,1);
//                alert(JSON.stringify($scope.list));
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
		if($scope.list.ecu == undefined){
                    $scope.list.ecu = [];
                }
                $scope.list.ecu.push(comArr[index].eid);
                $scope.ecu_list.splice( index, 1 );
//                alert(JSON.stringify($scope.list));
                
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
                $scope.list.ecu.splice(index,1);
//                alert(JSON.stringify($scope.list));
            };
            $scope.SelectNetwork = function()
            {
//                Object.keys($scope.data).forEach(function(itm){
//                    if(itm != "network") delete $scope.data[itm];
//                });
//                $scope.Demo.data = [];
                  Object.keys($scope.Demo.data).forEach(function(itm){
                      if(itm != "network") delete $scope.Demo.data[itm];
                  });
                  $scope.Demo.data = [];
                
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
//                alert("LoadSelectedVehicleVersionData");
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
                    
//                    for(var i=0; i<$scope.signal.length; i++)
//                        $scope.signal_list.push({sid:$scope.signal[i].sid,listitem:$scope.signal[i].listitem,description: $scope.signal[i].description})                   
//                    
//                    for(var i=0; i<$scope.ecu.length; i++)
//                        $scope.ecu_list.push({eid:$scope.ecu[i].eid,listitem:$scope.ecu[i].listitem,description: $scope.ecu[i].description})
//                    
//                    $scope.signal = [], $scope.list.signal = [];
//                    $scope.ecu = [], $scope.list.ecu = []; 
//                    
//                    $scope.list = [];
//                    
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
//                    alert(JSON.stringify($scope.list));
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
                $scope.list.can = []
                $scope.list.lin = [];
                $scope.list.hardware = [];
//                alert(JSON.stringify($scope.records));
//                var cls=angular.element( document.getElementsByClassName( 'tab-pane' ) );
//                cls.css('display','none');
//                var idr = '#can';
//                var myEl = angular.element( document.querySelector( idr ) );
//                myEl.css('display','block');
            }
            
            $scope.checkNotify = function (event){
            if($scope.data.status && event === "submit"){
                list_count = Object.keys($scope.list).length;
                if(list_count > 0 && $scope.list.can != undefined && $scope.list.lin != undefined && $scope.list.hardware != undefined
                         && $scope.list.signal != undefined && $scope.list.ecu != undefined){
                     if($scope.list.can.length > 0 && $scope.list.lin.length > 0 && $scope.list.hardware.length > 0
                             && $scope.list.signal.length > 0 && $scope.list.ecu.length > 0){
                            $(".notifyPopup").click();
                        }else{
                            alert("Please fill all the details to create IVN version");
                        }
                } else{
                    alert("Please fill all the details to create IVN version");
                }
            }else
                $scope.createivnversion(event);
            }
            
            $scope.createivnversion = function (event) 
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
//                alert(JSON.stringify($scope.data));
                data['ivnversion'] = $scope.data;
                data['ivndata_list'] = $scope.list;
                data['button_type'] = event;
                data['notification_to'] = notification_to+"";
//                alert(JSON.stringify(data));
                list_count = Object.keys($scope.list).length;
//                alert(JSON.stringify($scope.list));
                if(list_count > 0 && $scope.list.can != undefined && $scope.list.lin != undefined && $scope.list.hardware != undefined
                         && $scope.list.signal != undefined && $scope.list.ecu != undefined){
//                     alert(JSON.stringify($scope.list));
                    if($scope.list.can.length > 0 && $scope.list.lin.length > 0 && $scope.list.hardware.length > 0
                             && $scope.list.signal.length > 0 && $scope.list.ecu.length > 0){
//                         alert(JSON.stringify($scope.list));
                        $http({
                            url : 'createivnversion',
                            method : "POST",
                            data : data,
                        })
                        .then(function (data, status, headers, config){               
                                  alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                                  $window.open("ivn_version_listing.action","_self"); //                alert(data.maps);
    //            //                Materialize.toast(data['maps']["status"], 4000);
                        });
                    }  
                    else{
                        alert("Please fill all the details to create IVN version");
                    }
                }
                else{
                    alert("Please fill all the details to create IVN version");
                }    
//                if(Object.keys($scope.list).length)
//                    alert("yes");
//                if($scope.list.length > 0)
//                {
//                    $http({
//                        url : 'createpdbversion',
//                        method : "POST",
//                        data : data,
//                        })
//                        .then(function (data, status, headers, config){               
//                              alert(JSON.stringify(data.data.maps.status).slice(1, -1));
//                              $window.open("pdb_assign.action","_self"); //                alert(data.maps);
//    //            //                Materialize.toast(data['maps']["status"], 4000);
//                    });
//                }
//                else
//                {
//                    alert("Please fill the domain and feature status to create PDB version");
//                }
            };
            
            $scope.LoadIVNPreviousVersion_NetworkValue =  function(v,nt){  
                var result_name = v.getAttribute("id").split("_");
                var nid = result_name[2];
                var vmm_id = result_name[3];  
                if(nt == "can")
                    arr_var = $scope.list.can;
                else if(nt == "lin")
                    arr_var = $scope.list.lin;
                else
                    arr_var = $scope.list.hardware;
                angular.forEach(arr_var, function(item) {
                    if(item.network_id == nid && item.vmm_id == vmm_id)
                         v.checked = true;
                });                     
            };
                    
            $scope.LoadIVNPreviousVersion = function() 
            {
                $http({
                    url : 'loadivnpreviousvehicleversion_data',
                    method : "POST",
                    data : {"ivnversion_id":$scope.data.ivnversion}
                })
                .then(function (response, status, headers, config){
//                    alert(JSON.stringify(response.data.ivn_map_result));
                    $scope.models = [];
                    var result_data = response.data.ivn_map_result;
                    var vehicledetail = result_data.vehicledetail_list;
//                    var signal_result = result_data.signal.slice(1, -1).split(",");
//                    var ecu_result = result_data.ecu.slice(1, -1).split(",");
                    var signal_result = result_data.signal;
                    var ecu_result = result_data.ecu;
                    var can_list = $scope.list.can = result_data.can;
                    var lin_list = $scope.list.lin = result_data.lin;
                    var hw_list = $scope.list.hardware = result_data.hardware;  
                    
                    $scope.data.status = result_data.ivnversion_status[0].status;
                     
                    for(var i=0; i<$scope.signal.length; i++)
                        $scope.signal_list.push({sid:$scope.signal[i].sid,listitem:$scope.signal[i].listitem,description: $scope.signal[i].description})                   
                    
                    for(var i=0; i<$scope.ecu.length; i++)
                        $scope.ecu_list.push({eid:$scope.ecu[i].eid,listitem:$scope.ecu[i].listitem,description: $scope.ecu[i].description})
                    
                    $scope.signal = [], $scope.list.signal = [];
                    $scope.ecu = [], $scope.list.ecu = [];                  

                    for(var i=0; i<signal_result.length; i++)
//                       $scope.add_signal_tab(parseInt(signal_result[i]));
                        $scope.add_signal_tab(signal_result[i]);
                  
                    for(var i=0; i<ecu_result.length; i++)
//                       $scope.add_ecu_tab(parseInt(ecu_result[i]));
                        $scope.add_ecu_tab(ecu_result[i]);
                    
                    for(var i=0; i<vehicledetail.length; i++)
                    {
                        $scope.models.push({
                                "mod":vehicledetail[i].modelname,
                                "id":vehicledetail[i].vmm_id,
                        }); 
                    }

                    $scope.data.vehicleversion = vehicledetail[0].vehver_id.toString();
                    $scope.LoadSelectedVehicleVersionData();
                    $scope.data.vehiclename = vehicledetail[0].vehicle_id.toString();
                    
                    var network_type = ["can","lin","hardware"];
                    angular.element(function () {
                        angular.forEach(network_type, function(value, key) {
                            network_type = value;
                            var result = document.getElementsByClassName("checkbox_"+network_type+"_act");    
                            angular.forEach(result, function(value) {
                                $scope.LoadIVNPreviousVersion_NetworkValue(value,network_type);
                            });
                        });
                    });
//                    alert(JSON.stringify($scope.list));
                });
            };
            
            
            
            $scope.create_ivn_required_attributes = function (event) 
            {        
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false; 
                var ivn_attribute_data = {};
                ivn_attribute_data['network'] = $scope.data.network;
                if($scope.data.network == "signals")
                    ivn_attribute_data['ivn_attribute_data'] = $scope.data;
                else
                    ivn_attribute_data['ivn_attribute_data'] = $scope.Demo.data;
                
//                alert($scope.data.network);
//                alert(JSON.stringify($scope.data));
//                alert(Object.keys($scope.data).length);
                              
//                alert(JSON.stringify($scope.Demo));
//                alert($scope.Demo.data.length);
//                alert(JSON.stringify($scope.Demo.data[0]));

//                if(($scope.Demo.data.length > 0 && $scope.Demo.data[0].name != undefined  && $scope.Demo.data[0].description != undefined) || 
//                        ($scope.data.network == "signals" && Object.keys($scope.data).length >= 17 && $scope.data.can !="" && $scope.data.lin !="" && $scope.data.hardware !=""))
                if(($scope.data.network != "signals" && $scope.Demo.data.length > 0 && $scope.Demo.data[0].name != undefined  && $scope.Demo.data[0].description != undefined)||
                        ($scope.data.network == "signals" && $scope.data.name != undefined  && $scope.data.description != undefined))
                {
                    $http({
                        url : 'create_ivn_required_attributes',
                        method : "POST",
                        data : ivn_attribute_data
                    })
                   .then(function (data, status, headers, config)
                    {
                        alert(data.data.maps.status);
//                        alert(JSON.stringify(data.data.result_data));
                        angular.forEach(data.data.result_data, function(value, key) {
                            if($scope.data.network == "can")
                                $scope.cans.push(value);
                            else if($scope.data.network == "lin")
                                $scope.lin.push(value);
                            else if($scope.data.network == "hardware")
                                $scope.hw.push(value);
                            else if($scope.data.network == "ecu"){
                                if($scope.list.ecu == undefined){
                                    $scope.list.ecu = [];
                                }
                                $scope.list.ecu.push(value.eid);
                                $scope.ecu.push(value);
                            }
                            else if($scope.data.network == "signals"){
                                if($scope.list.signal == undefined){
                                    $scope.list.signal = [];
                                }
                                $scope.list.signal.push(value.sid);
                                $scope.signal.push(value);
                            }
                        });
                    });
                    $('#modal-product-form').closeModal();
                    can = [];
                    lin = [];
                    hardware = [];
                }
                else{
                    if($scope.data.network == "signals")
                        alert("Please fill the name and description");
                    else
                        alert("Please fill all the fields");
                }                              
            };
            var can = [];
            var lin = [];
            var hardware = [];
            $scope.checkboxvalue = function(network_type,network_id)
            {	
                network_type = eval(network_type);
                if(network_type.indexOf(network_id) !== -1)
                    network_type.splice(can.indexOf(network_id), 1);     
                else
                    network_type.push(network_id);
                $scope.data.can = can.toString();
                $scope.data.lin = lin.toString();
                $scope.data.hardware = hardware.toString();                              
            };
//            var can = [];
//            var lin = [];
//            var hardware = [];
//            $scope.list.can = [];
//            $scope.list.lin = [];
//            var network = [];
            $scope.ivnnetwork_checkbox = function(network_id, vmm_id, network_type, $event)
            {	
                var available_status = $event;
//                alert(available_status);
                if(network_type == "can"){
                    if($scope.list.can == undefined){
                        $scope.list.can = [];
                    }
                    var index = -1;
                    $scope.list.can.filter(function(v,i){
                        if(v.vmm_id === vmm_id && v.network_id === network_id)
                            index = i;
//                            return index;
                    });
                    if(index == -1)
                        $scope.list.can.push({vmm_id:vmm_id,network_id:network_id,status:available_status,network_type:network_type});
                    else
                        $scope.list.can.splice(index,1);
                }
                else if(network_type == "lin"){
                    if($scope.list.lin == undefined){
                        $scope.list.lin = [];
                    }
                    var index = -1;
                    var result = $scope.list.lin.filter(function(v,i){
                        if(v.vmm_id === vmm_id && v.network_id === network_id)
                            index = i;
//                            return index;
                    });
                    if(index == -1)
                        $scope.list.lin.push({vmm_id:vmm_id,network_id:network_id,status:available_status,network_type:network_type});
                    else
                        $scope.list.lin.splice(index,1);
                }
                else{
                    if($scope.list.hardware == undefined){
                        $scope.list.hardware = [];
                    }
                    var index = -1;
                    var result = $scope.list.hardware.filter(function(v,i){
                        if(v.vmm_id === vmm_id && v.network_id === network_id)
                            index = i;
//                            return index;
                    });
                    if(index == -1)
                        $scope.list.hardware.push({vmm_id:vmm_id,network_id:network_id,status:available_status,network_type:network_type});
                    else
                        $scope.list.hardware.splice(index,1);
                }
//                alert(JSON.stringify($scope.list));

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
                $scope.data.ivnversion = params_array[0].id;
                var action = params_array[1].action;
                                
                $scope.models = [];
                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//                alert(JSON.stringify(result_data));
                var vehicledetail = result_data.vehicledetail_list;
//                var signal_result = result_data.signal.slice(1, -1).split(",");
//                var ecu_result = result_data.ecu.slice(1, -1).split(",");
                var signal_result = result_data.signal;
                var ecu_result = result_data.ecu;
                var can_list = $scope.list.can = result_data.can;
                var lin_list = $scope.list.lin = result_data.lin;
                var hw_list = $scope.list.hardware = result_data.hardware;  

                $scope.data.status = result_data.ivnversion_status[0].status;

                for(var i=0; i<$scope.signal.length; i++)
                    $scope.signal_list.push({sid:$scope.signal[i].sid,listitem:$scope.signal[i].listitem,description: $scope.signal[i].description})                   

                for(var i=0; i<$scope.ecu.length; i++)
                    $scope.ecu_list.push({eid:$scope.ecu[i].eid,listitem:$scope.ecu[i].listitem,description: $scope.ecu[i].description})

                $scope.signal = [], $scope.list.signal = [];
                $scope.ecu = [], $scope.list.ecu = [];                  

                for(var i=0; i<signal_result.length; i++)
//                   $scope.add_signal_tab(parseInt(signal_result[i]));
                    $scope.add_signal_tab(signal_result[i]);

                for(var i=0; i<ecu_result.length; i++)
//                   $scope.add_ecu_tab(parseInt(ecu_result[i]));
                    $scope.add_ecu_tab(ecu_result[i]);

                for(var i=0; i<vehicledetail.length; i++)
                {
                    $scope.models.push({
                            "mod":vehicledetail[i].modelname,
                            "id":vehicledetail[i].vmm_id,
                    }); 
                }

                $scope.data.vehicleversion = vehicledetail[0].vehver_id.toString();
                $scope.LoadSelectedVehicleVersionData();
                $scope.data.vehiclename = vehicledetail[0].vehicle_id.toString();

                var network_type = ["can","lin","hardware"];
                angular.element(function () {
                    angular.forEach(network_type, function(value, key) {
                        network_type = value;
                        var result = document.getElementsByClassName("checkbox_"+network_type+"_act");    
                        angular.forEach(result, function(value) {
                            $scope.LoadIVNPreviousVersion_NetworkValue(value,network_type);
                        });
                    });
                });
                if(action == "view"){
                    $scope.showSave =false;
                    $scope.showSubmit =false;
                }
            } 
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
      
         app.service('fileUpload', ['$http', function ($http) {
            this.uploadFileToUrl = function(file, uploadUrl){
               var fd = new FormData();
               fd.append('file', file);
            
               $http.post(uploadUrl, fd, {
                  transformRequest: angular.identity,
                  headers: {'Content-Type': undefined}
               }).then(function success(response) {
                       // $(".loader-block").hide();
                        alert("Success");
                        
                    }, function error(response) {
                        //$(".loader-block").hide();
                        alert("Error");
                    })
            }
         }]);
      
         app.controller('fileCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
            $scope.uploadFile = function(){
                //$(".loader-block").show();
//                alert('hi');
               var file = $scope.myFile;
               
               //console.log('file is ' );
               //console.dir(file);
               
               var uploadUrl = "signalImport";
               fileUpload.uploadFileToUrl(file, uploadUrl);
            };
            $scope.uploadIVN = function(){
                //$(".loader-block").show();
//                alert('hi');
               var file = $scope.myFile;
               
               //console.log('file is ' );
               //console.dir(file);
               
               var uploadUrl = "ivnImport";
               fileUpload.uploadFileToUrl(file, uploadUrl);
            };
         }]);

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