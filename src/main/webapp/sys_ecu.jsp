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
                                                        <span>ECU Variants</span>
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
                                                        <form class="form-inline mt-3">
                                                            <div class="form-group">
                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                            </div>
                                                        </form>
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('pdb_version')" class="text-center">ECU</th>
                                                                    <th ng-click="sort('veh_version')" class="text-center">Description</th>
                                                                    <th ng-click="sort('vehicle')" class="text-center">Variants Status</th>                                                                    
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                                {{record.listitem}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                                {{record.description}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.variants != undefined"> 
                                                                                Present
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Variants:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.variants | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                            <span ng-if="record.variants === undefined">Not Present</span>
                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === true">Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === false">Inactive
                                                                            </button>

                                                                        </td>
                                                                        
                                                                        
                                                                        <td class="text-center"> 
                                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm(record.eid)" ng-if="record.variants != undefined">Edit</a>
                                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm(record.eid)" ng-if="record.variants === undefined">Create</a>
                                                                        </td>
                                                                    </tr>

                                                                </tbody>
                                                            </table>
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
                        <h5 class="text-c-red m-b-25">Variants <a class="modal-action modal-close waves-effect waves-light float-right m-t-5"> <strong><em>Close</em></strong></a></h5>


                            <div class="" ng-repeat="data in Demo.data">         
                                <p class="text-right">
                                    <a href="" ng-click="Demo.data.splice($index,1)">
                                        <i class="icofont icofont-ui-close text-c-red "></i>
                                    </a>
                                 </p>
                                <input ng-model="data.variants" type="text" class="validate col-lg-12" id="form-name" placeholder="ECU variants"/>
                            </div>
                            <div style="clear:both"></div>
                            <p class="text-right">
                                <a href="" ng-click="Demo.data[Demo.data.length] = {}" class="text-c-green">
                                    <strong>Clone</strong>
                                 </a>
                            </p>

                            <div class="input-field text-center">
                                <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em btn-primary" ng-click="createvariants()" ng-mousedown='doSubmit=true' name="add">Save</button>
                            </div>
                    </div>
                </div>
                                            
<%@include file="footer.jsp" %>
    <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data=[];
//             $scope.records = [ 
//                            { eid:'1',listitem:'ecu 1',description:'description 1',status:'true',variant_status:'true',variants:'high,mid,low'},
//                            { eid:'2',listitem:'ecu 2',description:'description 2',status:'true',variant_status:'true',variants:'high,mid,low'},
//                            { eid:'3',listitem:'ecu 3',description:'description 3',status:'false',variant_status:'false',variants:''},
//                            { eid:'4',listitem:'ecu 4',description:'description 4',status:'true',variant_status:'true',variants:'high,mid,low'}
//                        ];

            var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
//            alert(JSON.stringify(data));
            $scope.records = data;
            var selected_ecuid;   
            $scope.data = {};
            var operation_type;     
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            
            $scope.View_and_edit = function(event){
//                alert("view_and_edit");
//                alert(event.target.id);
                var id = event.target.attributes['data-id'].value;
                var name = event.target.name;
//                alert(id);
//                alert(name);
//                $http.get("vehicle_add.action", {
//                    params: { "id": id }
//                });
                $window.open("pdb_assign.action?id="+id+"&action="+name,"_self"); //  
            }
            $scope.showCreateForm = function(eid)
            {
                $scope.Demo.data = [];
                var variants_list;
                selected_ecuid = eid;
                $scope.records.filter(function(e,i){
                    if(e.eid == eid && e.variants != undefined)
                        variants_list = e.variants.split(",");
                });
                if(variants_list != undefined){
                    operation_type = "update";
                    for(i=0;i<variants_list.length;i++)
                        $scope.Demo.data.push({"variants":variants_list[i]});
                }    
                else{
                    operation_type = "create";
                    $scope.Demo.data.push({});
                }    
            }
            $scope.createvariants = function (event) 
            { 
                if (!$scope.doSubmit) 
                {
                    return;
                }
                $scope.doSubmit = false; 
//                alert(JSON.stringify($scope.Demo.data));
//                alert($scope.Demo.data[0].variants);
                if($scope.Demo.data[0].variants != undefined){                   
//                    alert(selected_ecuid);
                    var ecu_variants_data = {};
                    ecu_variants_data['variants'] = $scope.Demo.data;
                    ecu_variants_data['ecu_id'] = selected_ecuid;
                    ecu_variants_data['operation_type'] = operation_type;
                    $http({
                        url : 'createvariants',
                        method : "POST",
                        data : ecu_variants_data
                    })
                    .then(function (data, status, headers, config)
                    {
                        alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                        $('#modal-product-form').closeModal();
                        $window.open("sys_ecu.action","_self"); 
                    });
                }
                else
                    alert("Please fill the variants");
                
            }
        });
        app.filter('customSplitString', function() 
        {
            return function(input) 
            {
                if(input !=undefined){
                    var arr = input.split(',');
                    return arr;
                }                
            };     
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            