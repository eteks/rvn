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
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>Legislation</h4>
                                                        <span>Legislation Listing</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="page-header-breadcrumb">
                                                    <ul class="breadcrumb-title">
                                                        <li class="breadcrumb-item">
                                                            <a href="index.html"> <i class="icofont icofont-home"></i></a>
                                                        </li>
                                                        <li class="breadcrumb-item">
                                                            <s:url action="legislation.action" var="aURL" />
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
                                                                <label for="vehicle">Legislation version :</label>
                                                                <select ng-model="data.pdbversion" ng-change="LoadPDBPreviousVersion()">
                                                                    <s:iterator value="pdbversion_result" >
                                                                        <option value="<s:property value="id"/>">
                                                                            <s:property value="pdb_versionname"/>
                                                                        </option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                            <a class="waves-effect waves-light modal-trigger" href="#modal-product-form" ng-click="showCreateForm()">Add Combination</a>
                                                        </div>   
                                                            
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th class="">Legislation</th>
                                                                    <th class="text-center" ng-repeat="i in records">
                                                                        {{i.modelname}}
                                                                    </th>

                                                                </tr>
                                                                </thead>
                                                                
                                                                <tbody>
                                                                <form ng-model="myform">    
                                                                    <tr dir-paginate="record in legislation|orderBy:sortKey:reverse|filter:search|itemsPerPage:20">
                                                                        
                                                                       
                                                                        <td class="">
                                                                            <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a> {{record.leg}}
                                                                        </td>
                                                                        <td class="text-center" ng-repeat="i in records">                                                                             
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">                                                                                
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'y')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="y" class="radio_button">
                                                                                <span class="checkmark c_b_g">                                                                                    
                                                                                </span>
                                                                                <span class="tooltip-content2">yes</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'n')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="n" class="radio_button">
                                                                                <span class="checkmark c_b_r"></span>
                                                                                <span class="tooltip-content2">no</span>
                                                                              </label>
                                                                              <label class="custom_radio mytooltip tooltip-effect-8">
                                                                                <input type="radio" ng-click="radiovalue(record.fid,i.vehicle_model_mapping_id,'o')" name="f{{record.fid}}_{{i.vehicle_model_mapping_id}}" value="o" class="radio_button">    
                                                                                <span class="checkmark c_b_b"></span>
                                                                                <span class="tooltip-content2">optional</span>
                                                                              </label>
                                                                        </td>
                                                                    </tr>
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
                <div class="col-lg-12 text-right">
                 
                    <div id="modal-upload" class="modal">
                        <div class="modal-content">
                            <h5 class="text-c-red m-b-10"><a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>
                            <form class="float-left">
                                <input type="file" ng-model="" name=""/>
                                 <button ng-show="showSubmit == true" type="submit" class="btn btn-primary">Upload</button>
                            </form>

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
                <div id="modal-product-form" class="modal">
                        <div class="modal-content">
                            <h5 class="text-c-red m-b-25">Feature Combination <a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>                                
                            <div class="col-md-12 col-lg-offset-1">
                                <input type="text" ng-model="" placeholder="Name" class="col-md-12"/>
                            </div>
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
                                            <!-- Marketing End -->
            <!--<form class=""  name="myForm">-->
                <!-- modal for for creating new product -->   
<!--            <pre>list={{list}}</pre>-->
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http, $window, $location, $element)
        {
                  
//       $scope.records = [
//                        { mod: 'm1'},
//                        { mod: 'm2'},
//                        { mod: 'm3'},
//                        { mod: 'm4'}
//                    ];
            this.data=[];
            var notification_to;
            $scope.features = [];
            $scope.list = [];
            
            var features_list = JSON.parse("<s:property value="featureslist_result_obj"/>".replace(/&quot;/g,'"'));
            $scope.features_list = features_list;
            
            $scope.showSave =true;
            $scope.showSubmit =true;
            $scope.$on('notifyValue', function (event, args) {
                notification_to = args;
                $scope.createpdbversion("submit");
            });
            $scope.data = {};
                             
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            $scope.add_feature_tab = function(fid)
            {				
		var index = -1;		
		var comArr = eval( $scope.features_list );
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
                $scope.features.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
		$scope.features_list.splice( index, 1 );
                
            };
            $scope.removeRow = function(fid)
            {				
		var index = -1;		
		var comArr = eval( $scope.features );
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
                $scope.features_list.push({fid:comArr[index].fid,domain:comArr[index].domain,fea: comArr[index].fea})
		$scope.features.splice( index, 1 );
                
                var list_data =[];
                $scope.list.filter(function(l,j){
                    if(l.dfm_id != fid)
                        list_data.push(l);
                });
                $scope.list = list_data;
//                alert(JSON.stringify($scope.list));
                
//                var list_index = -1;
//                $scope.list.filter(function(l,j){
//                    if(l.dfm_id == fid)
//                        list_index = j;
//                });
//                if(list_index != 1)
//                    $scope.list.splice( list_index, 1 );
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
                $scope.records = [];
                $scope.list = [];
                for(var i = 0; i < $scope.model_list.length; i++) 
                {
                   var data = $scope.model_list[i];
                   if(data.vehicle_id == selected_vehicleid){
//                       alert(data.vehicle_mapping_id);
                        angular.forEach(data.mod, function(value, key) {
                            $scope.records.push({
                             "modelname":value,
                             "vehicle_model_mapping_id":data.vehicle_mapping_id[key],
                            }); 
                        })
                   }
                }
//                alert(JSON.stringify($scope.records));
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
                if($scope.Demo.data.length > 0)
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
                            for(var i = 0; i < result_data.length; i++) 
                            {
                                $scope.features.push({fid:result_data[i].fid,fea:result_data[i].fea,domain:result_data[i].domain});
                            }
                       });
                       $('#modal-product-form').closeModal();
                }
                else
                {
                    alert("Please create atleast one features");
                }
            }
            $scope.checkNotify = function (event){
            if($scope.data.status && event === "submit"){
                $(".notifyPopup").click();
            }else
                $scope.createpdbversion(event);
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
                data['button_type'] = event;
                data['notification_to'] = notification_to+"";
                //console.log(data);
                if($scope.list.length > 0){
                    $http({
                        url : 'createpdbversion',
                        method : "POST",
                        data : data,
                        })
                        .then(function (data, status, headers, config){               
                              alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                              $window.open("pdb_listing.action","_self"); //                alert(data.maps);
    //            //                Materialize.toast(data['maps']["status"], 4000);
                    });
                }
                else{
                    alert("Please fill the domain and feature status to create PDB version");
                }
            }
            $scope.radiovalue = function(dfm_id,vmm_id,status)
            {		
//                alert("enter");
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
//                alert(JSON.stringify($scope.list))
            };
            $scope.LoadPDBPreviousVersion = function() 
            {                
                $scope.legislation = [{"leg":"Legislation1","yes":"power window,RSC,f4","no":"AEB","opt":"f2"},
                                    {"leg":"Legislation2","yes":"f3","no":"f4,f2","opt":"f1"}]; 
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
                $scope.data.pdbversion = params_array[0].id;
                var action = params_array[1].action;
                
                var result_data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
                var vehicledetail_list = result_data.vehicledetail_list;
                $scope.data.status = result_data.pdbversion_status[0].status;
                
                $scope.data.vehicleversion = vehicledetail_list[0].vehver_id.toString();
                $scope.LoadSelectedVehicleVersionData();
                $scope.data.vehiclename = vehicledetail_list[0].vehicle_id.toString();
                $scope.records = vehicledetail_list;
//                    alert(JSON.stringify($scope.records));                    
                var featuredetail_list = result_data.featuredetail_list;
                for(var i=0; i<featuredetail_list.length; i++)
                {
                    if($scope.features.length === 0)
                    {
                        $scope.add_feature_tab(featuredetail_list[i].fid);
//                            $scope.features.push({fid:featuredetail_list[i].fid,fea:featuredetail_list[i].featurename,domain:featuredetail_list[i].domainname,status:featuredetail_list[i].status});
                    }
                    else
                    {
                        var temp=0;
                        for(var j=0; j<$scope.features.length; j++)
                        {
                            if($scope.features[j].fid === featuredetail_list[i].fid)
                            {
                                temp=1;
                            }   
                        }
                        if(temp==0)
                        {
                            $scope.add_feature_tab(featuredetail_list[i].fid);
                        }
                    }

                    $scope.radiovalue(featuredetail_list[i].fid,featuredetail_list[i].vmm_id,featuredetail_list[i].status);
//                        alert(JSON.stringify($scope.list));  
                }
                angular.element(function () {
                    var result = document.getElementsByClassName("radio_button");
                    angular.forEach(result, function(value) {
                        var result_name = value.getAttribute("name").substring(1).split("_");
                        var fid = result_name[0];
                        var vmm_id = result_name[1];
                        var status = value.getAttribute("value");  
                        angular.forEach($scope.list, function(item) {
                            if(item.dfm_id == fid && item.vmm_id == vmm_id && item.status == status)
                                value.setAttribute("checked","checked");
                        });    
                    });
                });
                if(action == "view"){
                    $scope.showSave =false;
                    $scope.showSubmit =false;
                }
            }    
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>          
