<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<!--<link rel="stylesheet" type="text/css" href="css/query-builder.default.min.css">
<script type="text/javascript" src="js/query-builder.min.js"></script> 

<script type="text/javascript" src="js/select2.js"></script>
<link rel="stylesheet" type="text/css" href="css/select2.css">

<link rel="stylesheet" type="text/css" href="css/combineBuilder.css">
<script type="text/javascript" src="js/combineBuilder.js"></script>-->

<div class="pcoded-content" ng-controller="RecordCtrl1 as Demo">
    <div class="pcoded-inner-content">
        <div class="main-body">

            <div class="page-wrapper">

                <div class="page-header card">
                    <div class="row align-items-end">
                        <div class="col-lg-8">
                            <div class="page-header-title">
                                <i class="icofont  icofont-mining bg-c-red"></i>
                                <div class="d-inline">
                                    <h4>Combinations</h4>
                                    <span>Features Listing</span>
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
                                        <s:url action="legislation.action" var="aURL" />
                                        <s:a href="%{aURL}">
                                            Back
                                        </s:a>
                                    </li>
                                    <li class="breadcrumb-item">
                                        <a class="waves-effect waves-light modal-trigger" href="#modal-product-form" ng-click="showCreateForm()">Add Combination</a> 
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

                                                <th ng-click="sort('version')" class="">No</th>
                                                <th ng-click="sort('version')" class="">Legislation</th>
                                                <th ng-click="sort('vehicle')" class="">Features</th>
                                                <th ng-click="sort('action')" class="text-center">Action</th>

                                            </tr>
                                        </thead>
                                        <tbody ng-init="getAllDomain_and_Features()">

                                            <tr dir-paginate="record in legislation|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">

                                                <td class="">

                                                    {{$index + 1}}

                                                </td>
                                                <td class="">

                                                    {{record.leg}}

                                                </td>
                                                <td class="text-left">
                                                    <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                        Y
                                                        <span class="tooltip-content5">
                                                            <span class="tooltip-text3">
                                                                <span class="tooltip-inner2">
                                                                    <ul class="model-list">
                                                                        <li ng-repeat="mod in (record.yes| customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                    </ul>
                                                                </span>
                                                            </span>
                                                        </span>
                                                    </a>
                                                    <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                        N
                                                        <span class="tooltip-content5">
                                                            <span class="tooltip-text3">
                                                                <span class="tooltip-inner2">
                                                                    <ul class="model-list">
                                                                        <li ng-repeat="mod in (record.no| customSplitString)"><i class="icofont icofont-hand-right"></i>{{mod}}</li>
                                                                    </ul>
                                                                </span>
                                                            </span>
                                                        </span>
                                                    </a>
                                                    <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                        O
                                                        <span class="tooltip-content5">
                                                            <span class="tooltip-text3">
                                                                <span class="tooltip-inner2">
                                                                    <ul class="model-list">
                                                                        <li ng-repeat="mod in (record.opt| customSplitString)"><i class="icofont icofont-hand-right"></i>{{mod}}</li>
                                                                    </ul>
                                                                </span>
                                                            </span>
                                                        </span>
                                                    </a>


                                                </td>
                                                <td class="text-center">

                                                    <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a>

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
                                <h5 class="text-c-red m-b-25">Feature Combination <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                                <!--<a class="modal-trigger  text-c-green" href="#modal-feature-list" ng-click="combination()">
                                Combine
                                </a>
                                <div ng-repeat="com in combine">              
                        
                                <label class="custom_radio mytooltip tooltip-effect-8">                                                                                
                                <input type="radio" ng-click="radiovalue(com.fea,'y')" name="f{{com.fid}}" value="y" class="radio_button">
                                <span class="checkmark c_b_g">                                                                                    
                                </span>
                                <span class="tooltip-content2">yes</span>
                                </label>
                                <label class="custom_radio mytooltip tooltip-effect-8">
                                <input type="radio" ng-click="radiovalue(com.fea,'n')" name="f{{com.fid}}" value="n" class="radio_button">
                                <span class="checkmark c_b_r"></span>
                                <span class="tooltip-content2">no</span>
                                </label>
                                <label class="custom_radio mytooltip tooltip-effect-8">
                                <input type="radio" ng-click="radiovalue(com.fea,'o')" name="f{{com.fid}}" value="o" class="radio_button">    
                                <span class="checkmark c_b_b"></span>
                                <span class="tooltip-content2">optional</span>
                                </label>
                                <label>{{com.fea}}&nbsp;({{com.domain}})</label>
                                </div>
                                <div class="input-field text-right">
                                <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em btn-primary" ng-click="legislate()" ng-mousedown='doSubmit=true' name="add">Save</button>
                                </div>-->
                                <div class="col-md-12 col-lg-offset-1">
                                    <div id="builder-basic" style="display: block;"></div>
                                    <div class="btn-group">
                                        <!--                                        <button class="btn btn-warning reset" data-target="basic">Reset</button>
                                                                                <button class="btn btn-success set-json" data-target="basic">Set rules</button>-->
                                        <button class="btn btn-primary parse-json" data-target="basic" id="btn-get">Get rules</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="modal-feature-list" class="modal modal-feature-list">
                            <div class="modal-content">

                                <h5 class="text-c-red m-b-10">Features <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                                <form class="form-inline mt-3">
                                    <div class="form-group">
                                        <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                    </div>
                                </form>
                                <ul>
                                    <li ng-repeat="fil in features|orderBy:sortKey:reverse|filter:search">
                                        <a href="#" class="text-c-green" ng-click="add_feature(fil.fid)">
                                            <i class="icofont icofont-ui-add"></i></a>&nbsp;{{fil.fea}}&nbsp;({{fil.domain}})
                                    </li>
                                </ul>

                            </div>
                        </div>

                        <%@include file="footer.jsp" %>
                        <!--<script src="js/lib/materialize.min.js"></script>-->
                        <!--<script src="js/dirPagination.js"></script>-->
                        <script>
                            //        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

                            app.controller('RecordCtrl1', function($scope, $http)
                            {

                            this.data = [];
                            $scope.legislation = [{"leg":"Legislation1", "yes":"power window,RSC,f4", "no":"AEB", "opt":"f2"},
                            {"leg":"Legislation2", "yes":"f3", "no":"f4,f2", "opt":"f1"}];
                            $scope.getAllDomain_and_Features = function(){
                            //                alert("getall");
                            $http.get("features_listing.action").then(function(response, data, status, headers, config){
                            //                        alert("<s:property value="result_data_obj"/>");
                            var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g, '"'));
                            $scope.features = data;
                            //console.log(data);
                            //                        alert(JSON.stringify($scope.features));
                            });
                            }
                            $scope.combine = [];
                            $scope.combination = function()
                            {
                            //                alert(JSON.stringify($scope.sigi));
                            //                $scope.cen = {ip:ip,pri:ind};
                            //                alert('ind');

                            $('.modal-trigger').leanModal();
                            }
                            $scope.add_feature = function(fid)
                            {
                            //                alert(JSON.stringify($scope.sigi));
                            //                $scope.cen = {ip:ip,pri:ind};
                            var index = - 1;
                            var comArr = eval($scope.features);
                            for (var i = 0; i < comArr.length; i++)
                            {
                            if (comArr[i].fid === fid)
                            {
                            index = i;
                            break;
                            }
                            }
                            if (index === - 1)
                            {
                            alert("Something gone wrong");
                            }
                            $scope.features.splice(index, 1);
                            $scope.combine.push({fid:comArr[index].fid, fea:comArr[index].fea, domain:comArr[index].domain});
                            //                alert(id);

                            }
                            $scope.yes = []
                                    $scope.no = []
                                    $scope.opt = []
                                    var variable;
                            $scope.radiovalue = function(fid, stat)
                            {

                            if (stat === 'y')
                            {
                            variable = "yes";
                            }
                            else if (stat === 'n')
                            {
                            variable = "no";
                            }
                            else
                            {
                            variable = "opt";
                            }
                            $scope[variable].push(fid);
                            //                 $scope.legislation.push({fid:vmm_id,dfm_id:dfm_id,status:status});
                            //                if($scope.legislation.length === 0)
                            //                {
                            //                    
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
                            //                    }
                            //                    if(temp==0)
                            //                    {
                            //                        $scope.legislation.push({vmm_id:vmm_id,dfm_id:dfm_id,status:status});
                            //                    }
                            //                }
                            //                alert(stat);
                            };
                            $scope.legislate = function()
                            {
                            $scope.legislation.push({yes:$scope.yes, no:$scope.no, opt:$scope.opt});
                            alert(JSON.stringify($scope.legislation));
                            }
                            $scope.sort = function(keyname)
                            {
                            $scope.sortKey = keyname; //set the sortKey to the param passed
                            $scope.reverse = !$scope.reverse; //if true make it false and vice versa
                            }
                            $scope.removeRow = function(fid)
                            {
                            var index = - 1;
                            var comArr = eval($scope.features);
                            for (var i = 0; i < comArr.length; i++)
                            {
                            if (comArr[i].fid === fid)
                            {
                            index = i;
                            break;
                            }
                            }
                            if (index === - 1)
                            {
                            alert("Something gone wrong");
                            }
                            $scope.features.splice(index, 1);
                            };
                            // read all vehicle
                            $scope.getAllVehicle = function(){
                            //                alert("getall");
                            $http.get("vehicleversion_listing.action").then(function(response, data, status, headers, config){

                            var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g, '"'));
                            //                        alert(JSON.stringify(data));
                            $scope.records = data;
                            });
                            }
                            $scope.createfeature_and_domain = function (event)
                            {
                            if (!$scope.doSubmit)
                            {
                            return;
                            }
                            $scope.doSubmit = false;
                            var feature_and_domain_data = {};
                            feature_and_domain_data['domain_name'] = $scope.domain;
                            feature_and_domain_data['features_and_description'] = $scope.Demo.data;
                            if ($scope.Demo.data.length > 0)
                            {
                            //                        alert(JSON.stringify(feature_and_domain_data));
                            $http({
                            url : 'createfeature_and_domain',
                                    method : "POST",
                                    data : feature_and_domain_data
                            })
                                    .then(function (data, status, headers, config)
                                    {
                                    result_data = data.data.domainFeatures_result;
                                    //result_data =  result_data.slice(1, -1);
                                    for (var i = 0; i < result_data.length; i++)
                                    {
                                    $scope.features.push({fid:result_data[i].fid, fea:result_data[i].fea, domain:result_data[i].domain});
                                    }
                                    });
                            $('#modal-product-form').closeModal();
                            }
                            else
                            {
                            alert("Please create atleast one features");
                            }
                            }

                            });
                            $(document).ready(function(){
                            // initialize modal
                            $('.modal-trigger').leanModal();
                            });
                            app.filter('customSplitString', function()
                            {
                            return function(input)
                            {
                            if (input != undefined){
                            var arr = input.split(',');
                            return arr;
                            }
                            };
                            });
                        </script>   
                        </body>

                        </html>                                            