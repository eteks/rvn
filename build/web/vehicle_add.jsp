<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<div class="pcoded-content"  ng-app="myApp" ng-controller="MyCtrl as Demo">    
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
                                    <li class="breadcrumb-item"><a href="ivn_supervisor.jsp">Back</a> </li>
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
                            <form class=""  name="myForm" ng-submit="vehicleversionFunc()">
                                <div class="form-group text-right">
                                    <label for="vehicle">Load vehicle version:</label>
                                    <select ng-model="data.vehicleversion">
                                        <option>v1.0</option>
                                        <option>v2.0</option>
                                        <option>v3.0</option>
                                        <option>v4.0</option>
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
                                        <button type="submit" class="btn btn-default">Submit</button>
                                    </div>
                             </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-block marketing-card p-t-20">
                                    {{Demo.data}} 
                                </div>
                            </div>
                        </div>    


<%@include file="footer.jsp" %>
<script>
    var app = angular.module('myApp', ['ngTagsInput']);
    app.controller('MyCtrl', function($scope, $http) 
    {
        this.data = [];
        $scope.vehicleversionFunc = function () 
        {
            var data = {};
            data['vehicle_and_model'] = $scope.Demo.data;
            data['vehicleversion'] = $scope.data;
    //            alert(JSON.stringify(data));
            $http({
                url : 'createvehicleversion',
                method : "POST",
                data : data
            })
            .then(function (data, status, headers, config) {
                  alert("New Vehicle version created Successfully ");
    //                alert(data.maps);
    //                Materialize.toast(data['maps']["status"], 4000);
            });
        }
    });
</script>
     
</body>

</html>