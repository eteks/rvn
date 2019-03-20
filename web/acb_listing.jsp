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
                                                        <form class="form-inline mt-3">
                                                            <div class="form-group">
                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                            </div>
                                                        </form>
                                                        <a class="feature_add sig_add modal-trigger" href="#comparelist">
                                                            Compare
                                                        </a>
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('acb_versionname')" class="text-center">ACB</th>
                                                                    <th ng-click="sort('pdb_versionname')" class="text-center">PDB</th>
                                                                    <th ng-click="sort('ivn_versionname')" class="text-center">IVN</th> 
                                                                    <th ng-click="sort('touched_features')" class="text-center">Touched</th>
                                                                    <!--<th ng-click="sort('untouched_features')" class="text-center">Untouched</th>-->
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('flag')" class="text-center">Version Type</th>
                                                                    <th ng-click="sort('created_date')" class="text-center">Created Date</th>
                                                                    <th ng-click="sort('modified_date')" class="text-center">Modified Date</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                    
                                                                    <tr dir-paginate="record in records|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                                {{record.acb_versionname}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                                {{record.pdb_versionname}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                                {{record.ivn_versionname}}
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.touched_features"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Features:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.touched_features | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                            
                                                                        </td>
<!--                                                                        <td class="text-center">
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.untouched_features"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Features:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.untouched_features | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                            
                                                                        </td>-->
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === true">Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === false">Inactive
                                                                            </button>

                                                                        </td>
                                                                        <td class="text-center">                                                                             
                                                                            <span ng-if="record.flag === false">Temporary</span>
                                                                            <span ng-if="record.flag === true">Permanent</span>
                                                                        </td>
                                                                        <td class="text-center">{{record.created_date}}</td>
                                                                        <td class="text-center">{{record.modified_date}}</td>
                                                                        <td class="text-center"> 
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="edit" ng-if="record.status === false">Edit</button>
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-danger btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="view" ng-if="record.status === true">view</button>
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="delete($event)" name="delete" ng-if="record.delBut === 1">Delete</button>
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
<div id="comparelist" class="modal modal-feature-list compare_mod">
    <div class="modal-content">
        <h5 class="text-c-red m-b-10">Compare <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
        <div class="comp_search">
            <select class="js-example-basic-single col-sm-12 form-control form-control-primary"  ng-model="countSelected" ng-change="comp_1(countSelected)" ng-options="category as category.versionname for category in compare_records">
                <option value="">Select Version</option>
                <!--<optgroup label="Designer">
                    <option value="WY">Peter</option>
                    <option value="WY">Hanry Die</option>
                    <option value="WY">John Doe</option>
                </optgroup>-->
            </select>
            <p class="m-t-10 text-center">
                <strong>With</strong>
            </p>
            <select class="js-example-basic-single col-sm-12 form-control form-control-primary"  ng-model="countSelected" ng-change="comp_2(countSelected)" ng-options="category as category.versionname for category in compare_records">
                <option value="">Select Version</option>
                <!--<optgroup label="Designer">
                    <option value="WY">Peter</option>
                    <option value="WY">Hanry Die</option>
                    <option value="WY">John Doe</option>
                </optgroup>-->
            </select>
        </div>
        <div class="comp_slot_1" ng-repeat="record in compare_1">
            <div><label>Version</label>:{{record.versionname}}</div>
            <div ng-repeat="r in record.vehicledetails">
                <div><label>Vehicle Name</label>:{{r.vehiclename}}</div>
                <div><label>Vehicle Version</label>:{{r.vehicleversion}}</div>
                <div><label>Models Name</label>:{{r.modelname}}</div>
            </div>
        </div>
        <div class="comp_slot_2" ng-repeat="record in compare_2">       
            <div><label>Version</label>:{{record.versionname}}</div>
            <div ng-repeat="r in record.vehicledetails">
                <div><label>Vehicle Name</label>:{{r.vehiclename}}</div>
                <div><label>Vehicle Version</label>:{{r.vehicleversion}}</div>
                <div><label>Models Name</label>:{{r.modelname}}</div>
            </div>
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
                                <input ng-model="domain" type="text" class="validate col-lg-12" id="form-name" placeholder="ECU variants"/>
                            </div>
                            <div style="clear:both"></div>
                            <p class="text-right">
                                <a href="" ng-click="Demo.data[Demo.data.length] = {}" class="text-c-green">
                                    <strong>Clone</strong>
                                 </a>
                            </p>

                            <div class="input-field text-center">
                                <!--<a id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right" ng-click="createfeature()">Add</a>-->
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em btn-primary" ng-click="createfeature_and_domain()" ng-mousedown='doSubmit=true' name="add">Save</button>
                            </div>
                    </div>
                </div>
                                            
<%@include file="footer.jsp" %>
    
    <script>
        

        app.controller('RecordCtrl1',function($scope, $http, $window)
        {
            this.data=[];
//             $scope.records = [{"id":1,"acb_versionname":"1.0","pdb_versionname":"1.0","ivn_versionname":"1.0","touched_features":"f1 (d1),f2 (d2)","untouched_features":"f3 (d3)","status":"true"},
//                                {"id":2,"acb_versionname":"2.0","pdb_versionname":"1.0","ivn_versionname":"1.0","touched_features":"f3 (d3)","untouched_features":"f1 (d1),f2 (d2)","status":"false"}];
            $scope.records = JSON.parse("<s:property value="listing_result_data_obj"/>".replace(/&quot;/g,'"'));
//            alert(JSON.stringify($scope.records));
            
//            var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
////            alert(JSON.stringify(data));
//            $scope.records = data;
                    
                  
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
                $window.open("acb_version_create.action?id="+id+"&action="+name,"_self"); //  
            }
            
            $scope.delete = function(event){
//                alert("view_and_edit");
//                alert(event.target.id);
                var id = event.target.attributes['data-id'].value;
                var data = {id : id};
                $http({
                url : 'deleteacbversion',
                method : "POST",
                data : data
                })
                .then(function (data, status, headers, config){
                     if(data.data.dlStatus.status === 1){
                         alert("ACB Version Deleted Succesfully");
                         $window.location.reload();
                    }else{
                        alert("Error while deleting ACB Version");
                    }
            });
            }
        });
//        app.filter('customSplitString', function() 
//        {
//            return function(input) 
//            {
//                var arr = input.split(',');
//                return arr;
//            };     
//        });
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