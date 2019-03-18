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
                                                                        {{s.listitem}} ({{s.salias}})
                                                                    </a>
<!--                                                                    <a class="modal-trigger removeSignalRow sig_edit" href="#modal-product-form" ng-click="showCreateForm()"><i class="icofont icofont-edit text-c-green"></i></a>    
                                                                    <a href="#" ng-click="removeSignalRow(s.sid)" class="removeSignalRow"><i class="icofont icofont-ui-close text-c-red"></i></a>    -->
                                                                </h3>
                                                                </div>
                                                                <div id="collapse{{s.sid}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading{{s.sid}}">
                                                                    
                                                                    <div class="accordion-content accordion-desc">
                                                                        <div ng-if="s.description">
                                                                            <label>Description :</label>
                                                                                {{s.description}}
                                                                        </div>
                                                                    
                                                                        <div ng-if="s.can">
                                                                            <label>CAN :</label>
                                                                                {{s.can}}
                                                                        </div>
                                                                   
                                                                        <div ng-if="s.lin">
                                                                            <label>LIN :</label>
                                                                                {{s.lin}}
                                                                        </div>
                                                                   
                                                                        <div ng-if="s.lin">
                                                                            <label>Hardware :</label>
                                                                                {{s.hardware}}
                                                                        </div>
                                                                        
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
                        <h5 class="text-c-red m-b-25">Add / Edit Signal
                            <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" >
                                <i class="icofont icofont-ui-close"></i>
                            </a>
                        </h5>
                        <div class="signal_attr row">              

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
            
            <!--<pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data=[];
            $scope.data = {};
            $scope.signal_list = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//            alert(JSON.stringify($scope.signal_list));
            
            network_list = JSON.parse("<s:property value="network_list_obj"/>".replace(/&quot;/g,'"'));
            $scope.cans = network_list.can_list;
            $scope.lin = network_list.lin_list;
            $scope.hw = network_list.hardware_list;

//            $scope.signal_list = 
//            [
//                { sid:'1',listitem:'signal 1',description:'description 1'},
//               { sid:'2',listitem:'signal 2',description:'description 2'},
//                { sid:'3',listitem:'signal 3',description:'description 3'},
//                { sid:'4',listitem:'signal 4',description:'description 4'}
//            ];

            $scope.create_ivn_required_attributes = function (event) 
            {        
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false; 
                var ivn_attribute_data = {};
                ivn_attribute_data['network'] = "signals";
                ivn_attribute_data['ivn_attribute_data'] = $scope.data;
                
                if($scope.data.name != undefined  && $scope.data.description != undefined && $scope.data.alias != undefined)
                {
                    $http({
                        url : 'create_ivn_required_attributes',
                        method : "POST",
                        data : ivn_attribute_data
                    })
                   .then(function (data, status, headers, config)
                    {
                        alert(data.data.maps.status);
                        angular.forEach(data.data.result_data, function(value, key) {
//                            alert(JSON.stringify(value));
                            $scope.signal_list.push(value);
                        });
                    });
                    $('#modal-product-form').closeModal();
                    can = [];
                    lin = [];
                    hardware = [];
                }
                else
                {
                    alert("Please fill the name and description and alias");
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
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            