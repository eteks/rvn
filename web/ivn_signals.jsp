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
                                                    <i class="icofont icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>IVN Engineer</h4>
                                                        <span>Signals</span>    
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
                                                        <li class="breadcrumb-item">
                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()">Add</a>
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
                                                         
                                                        <form class="form-inline mt-3">
                                                            <div class="form-group">
                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                            </div>
                                                        </form>
                                                        <div id="accordion" role="tablist" aria-multiselectable="true">
                                                                                            
                                                            <div class="accordion-panel" dir-paginate="s in signal_list|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                <div class="accordion-heading" role="tab" id="heading{{s.sid}}">
                                                                    <h3 class="card-title accordion-title">
                                                                    <a class="accordion-msg" data-toggle="collapse"
                                                                       data-parent="#accordion" href="#collapse{{s.sid}}"
                                                                       aria-expanded="true" aria-controls="collapse{{s.sid}}">
                                                                        {{s.listitem}}
                                                                    </a>
                                                                    <a class="modal-trigger removeSignalRow sig_edit" href="#modal-product-form" ng-click="showCreateForm()"><i class="icofont icofont-edit text-c-green"></i></a>    
                                                                    <a href="#" ng-click="removeSignalRow(s.sid)" class="removeSignalRow"><i class="icofont icofont-ui-close text-c-red"></i></a>    
                                                                </h3>
                                                                </div>
                                                                <div id="collapse{{s.sid}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading{{s.sid}}">
                                                                    <div class="accordion-content accordion-desc">


                                                                    </div>
                                                                </div>
                                                            </div>                                                                                           

                                                        </div>
                                                        
                                                        <dir-pagination-controls
                                                            max-size="5"
                                                            direction-links="true"
                                                            boundary-links="true" >
                                                        </dir-pagination-controls>

                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
                        
                <div id="modal-product-form" class="modal">
                    <div class="modal-content">
                        <h5 class="text-c-red m-b-25">Add Signal
                            <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" >
                                <i class="icofont icofont-ui-close"></i>
                            </a>
                        </h5>
                        <div class="signal_attr row">              

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.name" type="text" class="validate" id="form-name" placeholder="Name"/>
                            </div>
                             <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.alias" type="text" class="validate" id="form-name" placeholder="alias"/>
                            </div> 
                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.length" type="text" class="validate" id="form-name" placeholder="Length"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.byteorder" type="text" class="validate" id="form-name" placeholder="Byte Order"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.unit" type="text" class="validate" id="form-name" placeholder="Unit"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <select ng-model="ivndata.valuetype" ng-change="Selectvalue()">
                                    <option value="" selected>Select your Value</option>
                                    <option value="unsign">Unsigned</option>
                                    <option value="sign">Signed</option>
                                </select>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.initvalue" type="text" class="validate" id="form-name" placeholder="Init Value"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.factor" type="text" class="validate" id="form-name" placeholder="factor"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.offset" type="text" class="validate" id="form-name" placeholder="Offset"/>
                            </div>

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.minimum" type="text" class="validate" id="form-name" placeholder="Minimum"/>
                            </div> 

                            <div class="form-group col-lg-6">
                                <!--<label for="name">Feature</label>-->
                                <input ng-model="ivndata.maximum" type="text" class="validate" id="form-name" placeholder="Maximum"/>
                            </div>

                             <div class="form-group col-lg-6">
                                <select ng-model="ivndata.valuetable" ng-change="">
                                    <option value="" selected>Select your Value Table</option>
                                    <option value="valuetable_1">valuetable_1</option>
                                    <option value="valuetable_2">valuetable_2</option>
                                </select>
                            </div> 

                            <div class="form-group col-lg-6">                                    
<!--                                    <input ng-model="ivndata.maximum" type="checkbox" class="validate"/>-->
                                <input type="checkbox" class="validate"/>
                                <label for="name">Automatic Min-Max Calculation</label>
                            </div>

                            <div class="form-group col-lg-12">
                                <textarea ng-model="ivndata.description" type="text" class="validate materialize-textarea  col-lg-12" placeholder="Description"></textarea>
                                <label for="description">Description</label>
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
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl',function($scope, $http, $window)
        {
            this.data=[];
            var signal_list = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
            alert(JSON.stringify(signal_list));
            
            network_list = JSON.parse("<s:property value="network_list_obj"/>".replace(/&quot;/g,'"'));
            $scope.cans = network_list.can_list;
            $scope.lin = network_list.lin_list;
            $scope.hw = network_list.hardware_list;

            $scope.signal_list = 
            [
                { sid:'1',listitem:'signal 1',description:'description 1'},
                { sid:'2',listitem:'signal 2',description:'description 2'},
                { sid:'3',listitem:'signal 3',description:'description 3'},
                { sid:'4',listitem:'signal 4',description:'description 4'}
            ];
            $scope.create_ivn_required_attributes = function (event) 
            {        
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false;                
//                var ivn_attribute_data = {};
//                alert(JSON.stringify($scope.ivndata));
                var validate = true;
                if($scope.ivndata != undefined){
                    $scope.ivndata.network = "signals";
                    var count = Object.keys($scope.ivndata).length;
                    if(count >= 3){
                        alert(JSON.stringify($scope.ivndata));
                        $http({
                            url : 'create_ivn_required_attributes',
                            method : "POST",
                            data : $scope.ivndata
                        })
                       .then(function (data, status, headers, config)
                        {
                            alert(data.data.maps.status);
                        });
                        $('#modal-product-form').closeModal();
                        can = [];
                        lin = [];
                        hardware = [];
                    }
                    else
                        validate = false;
                }
                else{
                    validate = false;
                }
                if(validate == false)
                    alert("Please fill all the fields");
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
                $scope.ivndata.can = can.toString();
                $scope.ivndata.lin = lin.toString();
                $scope.ivndata.hardware = hardware.toString();                              
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
            
    </script>   
</body>

</html>                                            