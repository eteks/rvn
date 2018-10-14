<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<!--<base href="/">-->
<div class="pcoded-content" ng-controller="MyCtrl as Demo">    
    <div class="pcoded-inner-content">
        <div class="main-body">

            <div class="page-wrapper">
                <div class="page-header card">
                    <div class="row align-items-end">
                        <div class="col-lg-8">
                            <div class="page-header-title">
                                <i class="icofont icofont-car-alt-2 bg-c-red"></i>
                                <div class="d-inline">
                                    <h4>Vehicle version</h4>
                                    <span>Add vehicle and Models</span>
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
                                        <s:url action="ivn_supervisor.action" var="aURL" />
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
                        <div class="col-md-6">
                            <div class="card">
                               <div class="card-block marketing-card p-t-20">
                            <form class=""  name="myForm">
                                <div class="form-group text-right">
                                    <label for="vehicle">Load vehicle version:</label>
                                    <select ng-model="data.vehicleversion" ng-change="LoadPreviousVersion()">
                                        <s:iterator value="vehicleversion_result" >
                                            <option value="<s:property value="id"/>"><s:property value="versionname"/></option>
                                        </s:iterator>
                                    </select>
                                </div>                
                                    <div>  
                                       
                                        <div ng-repeat="data in Demo.data">              
                                            <div class="form-group">
                                                <label for="vehicle">Vehicle:</label>
                                                <input type="text" class="form-control" placeholder="Enter vehicle" name="vehicle"  ng-model="data.vehiclename" required>
                                                <span ng-show="myForm.vehicle.$touched && myForm.vehicle.$invalid">The name is required.</span>
                                            </div>
                                            <div class="form-group">
                                                <label for="model">Model:</label>
                                                <tags-input ng-model="data.modelname"  use-strings="true"></tags-input>
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
                                    <div class="form-group">
                                      <label for="status">Status:</label>

                                      <label class="switch float-right">
                                          <input type="checkbox" ng-model="data.status">
                                          <span class="slider round"></span>
                                        </label>
                                    </div>
                                    <div class="text-center">
                                        <button ng-show="showSave == true" type="submit" class="btn btn-default" ng-mousedown='doSubmit=true' ng-click="checkNotify('save')" name="save">Save</button>
                                        <button ng-show="showSubmit == true" type="submit" class="btn btn-default" ng-mousedown='doSubmit=true' ng-click="checkNotify('submit')" name="submit">Submit</button>
                                    </div>
                             </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="row card-block marketing-card">
                                    <div class="col-md-12 mod_vec_animate" ng-repeat="x in Demo.data">
                                        <h5 class="m-t-20"><i class="icofont icofont-steering"></i> {{x.vehiclename}}</h5>
                                        <ul>
                                            <li ng-repeat="i in x.modelname">
                                                
                                                <i class="icofont icofont-whisle text-c-red"></i> {{i}} 
                                            </li>
                                        </ul>
                                        
                                    </div> 
                                </div>
                            </div>
                        </div>    
<!--<base href="/">-->
<%@include file="footer.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular-animate.js"></script>
<script>
//    var app = angular.module('myApp', ['ngTagsInput']);
//    app.config( [ '$locationProvider', function( $locationProvider ) {
//        // In order to get the query string from the
//        // $location object, it must be in HTML5 mode.
//        $locationProvider.html5Mode( true );
//     }]);
    app.controller('MyCtrl',function($scope, $http ,$window, $location)
    {       
        this.data = [];
        var notification_to;
            $scope.showSave =true;
            $scope.showSubmit =true;
            $scope.$on('notifyValue', function (event, args) {
                notification_to = args;
                $scope.submit_vehicleversion("submit");
            });
          
          if($location.absUrl().includes("?")){
                $scope.data = {};
                var params_array = [];
                var absUrl = $location.absUrl().split("?")[1].split("&");
//                alert(absUrl);
                for(i=0;i<absUrl.length;i++){
                    var key_test = absUrl[i].split("=")[0];
                    var value = absUrl[i].split("=")[1];
//                    alert(key_test);
//                    alert(value);
                    params_array.push({[key_test]:value});
                }
              $scope.data.vehicleversion = params_array[0].id;
//               alert(JSON.stringify(params_array));
              var action = params_array[1].action;
              var result_data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
    //        result_data = [{"vehicle_mapping_id":"1,2","vehiclename":"vehicle1","modelname":"v11,v12","model_id":"1,2","versionname":"1.0","vehicle_id":1,"status":true},{"vehicle_mapping_id":"3,4,5","vehiclename":"vehicle2","modelname":"v21,v22,v23","model_id":"3,4,5","versionname":"1.0","vehicle_id":2,"status":true}];
            var array_result = [];
            var status_value = "";
            for(var i = 0; i < result_data.length; i++) 
            {
                 var data= result_data[i];
                 array_result.push({
                     "vehiclename":data.vehiclename,
                     "modelname":data.modelname.split(","),
                     "versionname":data.versionname,
                     "status":data.status
                 });
                 status_value = data.status;  
             }
             $scope.Demo.data = array_result;
             $scope.data.status = status_value;
            
             if(action == "view"){
                 $scope.showSave =false;
                $scope.showSubmit =false;
             }
         }
        $scope.checkNotify = function (event){
            if($scope.data.status && event === "submit"){
                if($scope.Demo.data.length > 0){
                    $(".notifyPopup").click();
                }else{
                    alert("Please fill all the fields");
                }
            }else
                $scope.submit_vehicleversion(event);
        }
        
        $scope.submit_vehicleversion = function (event) 
        {         
            if (!$scope.doSubmit) {
                return;
            }
            $scope.doSubmit = false;         
            var data = {};
            data['vehicle_and_model'] = $scope.Demo.data;
            data['vehicleversion'] = $scope.data;
            data['button_type'] = event;
            
            data['notification_to'] = notification_to+"";
            //console.log(data); 
            if(data['vehicle_and_model'].length > 0){
                $http({
                url : 'createvehicleversion',
                method : "POST",
                data : data
                })
                .then(function (data, status, headers, config){
                      alert(JSON.stringify(data.data.maps.status).slice(1, -1));
                      $window.open("vehicleversion_listing.action","_self"); //                alert(data.maps);
        //                Materialize.toast(data['maps']["status"], 4000);
                });
            }
            else{
                alert("Please fill all the fields");
            }
//            
////            for (var key in $scope.Demo.data) 
////            {
////                for (var i = 0; i < $scope.Demo.data[key].length; i++) 
////                {
////                    var title = $scope.Demo.data[key][i].vehiclename;
////                    var desc = $scope.Demo.data[key][i].modelname;
////                    var badge = document.createElement('div');
////                    badge.className = 'badge';
////                    badge.innerHTML =
////                    '<h1>' + title + '</h1>' +
////                    '<h2>' + desc + '</h1>' +
////                    '<div class="options-only-phone">' +
////                    '<a class="service-provider-call" href="#" target="_blank"> Buy for $' + price + '</a>';
////                    document.getElementById('basketball'').appendChild(badge);
////                }
////            }
        }
        $scope.LoadPreviousVersion = function() 
        {
//            alert("loadpreviousversion");
//            alert($scope.data.vehicleversion);
            $http({
                url : 'loadpreviousvehicleversion_data',
                method : "POST",
                data : {"vehicleversion_id":$scope.data.vehicleversion}
            })
            .then(function (response, status, headers, config){
//                result_data = JSON.stringify(response.data.vehmod_map_result);
               var array_result = [];
               var status_value = "";
               for(var i = 0; i < response.data.vehmod_map_result.length; i++) 
               {
                    var data= response.data.vehmod_map_result[i];
                    array_result.push({
                        "vehiclename":data.vehiclename,
                        "modelname":data.modelname.split(","),
                        "versionname":data.versionname,
                        "status":data.status
                    });
                    status_value = data.status;  
                }
                $scope.Demo.data = array_result;
                $scope.data.status = status_value;
//                $scope.Demo.data = [{"vehiclename":"sasdsa","modelname":["dfsd","jhkjk","hkkjhk","kljk"],"versionname":"4.0","status":false}];
            });
        };
        
    });
//     var m = angular.module('App',['ngAnimate']);
</script>
     
</body>

</html>