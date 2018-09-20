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
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('acb_versionname')" class="text-center">ACB</th>
                                                                    <th ng-click="sort('pdb_versionname')" class="text-center">PDB</th>
                                                                    <th ng-click="sort('ivn_versionname')" class="text-center">IVN</th> 
                                                                    <th ng-click="sort('touched_features')" class="text-center">Touched</th>
                                                                    <th ng-click="sort('untouched_features')" class="text-center">Untouched</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
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
                                                                                            <h3>Variants:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.touched_features | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                            
                                                                        </td>
                                                                        <td class="text-center">
                                                                            <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)" ng-if="record.untouched_features"> 
                                                                                <i class="icofont icofont-hand-drawn-up"></i>
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h3>Variants:</h3>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.untouched_features | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                            
                                                                        </td>
                                                                        <td class="text-center"> 
                                                                            
                                                                            <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="record.status === 'true'">Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="record.status === 'false'">Inactive
                                                                            </button>

                                                                        </td>
                                                                        
                                                                        
                                                                        <td class="text-center"> 
                                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()" ng-if="record.status === 'true'">Edit</a>
                                                                            <a class="feature_add_tip waves-effect waves-light btn modal-trigger btn-floating btn-large red" href="#modal-product-form" ng-click="showCreateForm()" ng-if="record.status === 'false'">View</a>
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
             $scope.records = [{"id":1,"acb_versionname":"1.0","pdb_versionname":"1.0","ivn_versionname":"1.0","touched_features":"f1 (d1),f2 (d2)","untouched_features":"f3 (d3)","status":"true"},
                                {"id":2,"acb_versionname":"2.0","pdb_versionname":"1.0","ivn_versionname":"1.0","touched_features":"f3 (d3)","untouched_features":"f1 (d1),f2 (d2)","status":"false"}];

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
                $window.open("pdb_assign.action?id="+id+"&action="+name,"_self"); //  
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