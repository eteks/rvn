<body ng-app="myApp" ng-init="getAll()" ng-controller="module_registerCtrl">
    <div class="container">

        <div class="col-md-12">
            <!-- Authentication card start -->
            <div class="login-card card-block auth-body mr-auto ml-auto">
                <form class="md-float-material" ng-submit="submit()">

                    <div class="auth-box">
                        <div class="row m-b-20">
                            <div class="col-md-12">
                                <h3 class="text-left txt-primary">Add Module</h3>
                            </div>
                        </div>
                        <hr/>
                        <div class="input-group">
                            module name: <input type="text" class="form-control"  name="modulename" ng-model="moduleName" >
                            Icon code: <input type="text" class="form-control"  name="iconcode" ng-model="iconCode" >
                            Route Pages: <input type="text" class="form-control"  name="routepages" ng-model="routePage" >
                            <input type="submit" id="submit" value="Add" class="btn bg-c-red btn-md btn-block waves-effect text-center m-b-20"/>
                        </div>
                        <hr/>
                    </div>
                </form>
                <!-- end of form -->
            </div>
            <!-- Authentication card end -->
        </div>
        <table class="hoverable bordered">
            <thead>
                <tr>
                    <th class="text-align-center">ID</th>
                    <th class="width-30-pct">Module Name</th>
                    <th class="width-30-pct">Icon Code</th>
                    <th class="width-30-pct">Route Pages</th>
                    <th class="width-30-pct">status</th>

                </tr>
            </thead>

            <tbody>
                <tr ng-repeat="module in names">
                    <td class="text-align-center">{{ module.id}}</td>
                    <td>{{ module.moduleName}}</td>
                    <td>{{ module.iconCode}}</td>
                    <td>{{ module.routePage}}</td>
                    <td>{{ module.status}}</td>
                </tr>
            </tbody>
        </table>
      
    </div>
    <script src="js/lib/jquery.min.js"></script>
    <!-- <script type="text/javascript" src="../bower_components/jquery-ui/js/jquery-ui.min.js"></script> -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>    

    <!-- modernizr js -->
    <script type="text/javascript" src="js/modernizr.js"></script>

    <!-- Custom js -->
    <script type="text/javascript" src="js/custom-dashboard.min.js"></script>
    <script src="js/pcoded.min.js"></script>
    <script src="js/demo-12.js"></script>
    <script type="text/javascript" src="js/script.min.js"></script>
    <!-- Custom js -->

    <!-- include jquery -->

    <!-- material design js -->
    <!--<script src="js/materialize.min.js"></script>-->
    <script src="js/lib/angular.min.js"></script> 

    <script>
                                var app = angular.module('myApp', []);
                                app.controller("module_registerCtrl", function ($scope, $http) {

                                    $scope.getAll = function () {
                                        //alert("inside get function");
                                        $http.get("getAllUserModule.action")
                                                .then(function (data, status, headers, config) {
                                                    //alert("success");
                                                    $scope.names = data.data.userModuleList;
                                                    //alert($scope.names);
                                                });
                                    }
                                    $scope.submit = function () {
//                    alert("inside function");
//                    alert($scope.usergroup);
                                        $http.post("moduleCreation.action", {

                                            'moduleName': $scope.moduleName,
                                            'iconCode': $scope.iconCode,
                                            'routePage': $scope.routePage
                                        })
                                                .then(function (data, status, headers, config) {
                                                    var stat = JSON.stringify(data.data.maps.status);
                                                    stat = stat.replace(/^"|"$/g, '');
                                                    alert(stat);
                                                    var suc ="success";
                                                    var suc1="empty";
                                                    if (suc == stat)
                                                    {
                                                        alert("successfully module created");
                                                    } 
                                                    else if(suc1==stat)
                                                    {
                                                        alert("Enter the module name!");
                                                    }
                                                    else
                                                    {
                                                        alert("some error occured");
                                                    }
                                                });
                                    }
                                });

    </script>
</body>