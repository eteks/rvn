
<body ng-app="myApp" ng-init="getAll()" ng-controller="gruop_registerCtrl">
    <div class="container">

        <div class="col-md-12">
            <!-- Authentication card start -->
            <div class="login-card card-block auth-body mr-auto ml-auto">
                <form class="md-float-material" ng-submit="submit()">

                    <div class="auth-box">
                        <div class="row m-b-20">
                            <div class="col-md-12">
                                <h3 class="text-left txt-primary">Add Group</h3>
                            </div>
                        </div>
                        <hr/>
                        <div class="input-group">
                            <input type="text" class="form-control"  name="usergroup" ng-model="usergroup" >
                            <span class="md-line"></span>
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
                    <th class="width-30-pct">Groupname</th>
                    <th class="width-30-pct">status</th>

                </tr>
            </thead>

            <tbody>
                <tr ng-repeat="group in names">
                    <td class="text-align-center">{{ group.id}}</td>
                    <td>{{ group.groupname}}</td>
                    <td>{{ group.status}}</td>
                </tr>
            </tbody>
        </table>
        <!-- end of col-sm-12 -->

        <!-- end of row -->
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
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
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script> 

    <script>
                            var app = angular.module('myApp', []);
                            app.controller("gruop_registerCtrl", function ($scope, $http) {
                                
                                $scope.getAll = function () {
                                    $http.get("getAllUserGroup.action")
                                            .then(function (data,status, headers, config) {
                                        $scope.names = data.data.userGroupList;
                                    });
                                }
                                $scope.submit = function () {
                                $http.post("groupCreation.action", {
                                        'usergroup': $scope.usergroup
                                    })
                                            .then(function (data, status, headers, config) {
                                                var stat = JSON.stringify(data.data.maps.status);
                                                stat = stat.replace(/^"|"$/g, '');
                                                var suc = "success";
                                                var suc1="error";
                                                var suc2="empty";
                                                if (suc == stat)
                                                {
                                                    alert("Success! Group Created");
                                                } else if(suc1 == stat)
                                                {
                                                    alert("Error!");
                                                }
                                                else if(suc2 == stat)
                                                {
                                                    alert("Enter the group name!");
                                                }
                                                else
                                                {
                                                    alert("group name already exists!");
                                                }
                                            });
                                }
                            });

    </script>
</body>