
<body ng-app="myApp" ng-init="getModulename()" ng-controller="module_permisionctrl">
    <div class="container">

        <div class="col-md-12">
            <!-- Authentication card start -->
            <div class="login-card card-block auth-body mr-auto ml-auto">
                <form class="md-float-material" ng-submit="submit()">
                    <h3>Module Permision</h3>
                    <select ng-model="data.mod_permission">
                        <option ng-repeat="moduleName in names" value="{{moduleName.id}}">{{moduleName.moduleName}}</option>
                    </select>
                    <br><br>

                    <div ng-repeat="Moule_Activity in moduleActivity">
                        <input type="checkbox" checkbox-group />
                        <label>{{Moule_Activity.activity}}</label>
                    </div>
                    <input type="submit" id="submit" value="Add" class="btn bg-c-red btn-md btn-block waves-effect text-center m-b-20"/>
                    {{mod_activity}}


                    <br><br>


                </form>
            </div>
            <!-- Authentication card end -->
        </div>

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

    <script src="js/lib/angular.min.js"></script> 

    <script>

                                var app = angular.module('myApp', []);
                                app.controller("module_permisionctrl", function ($scope, $http)
                                {

                                    $scope.moduleActivity = [{"id": 1, "activity": "add"},
                                        {"id": 2, "activity": "edit"},
                                        {"id": 3, "activity": "view"},
                                        {"id": 4, "activity": "delete"}];
                                    $scope.mod_activity = [];
                                    $scope.getModulename = function ()
                                    {
                                        $http.get("getModuleName.action")
                                                .then(function (data, status, headers, config) {
                                                    $scope.names = data.data.moduleNameList;
                                                });
                                    }
                                    $scope.submit = function () {
                                        $scope.data.moduleAct = $scope.mod_activity;
                                        $http({
                                            url: 'CreateModulePermision',
                                            method: "POST",
                                            data: $scope.data
                                        })
                                                .then(function (response, status, headers, config) {
                                                    var stat = JSON.stringify(response.data.maps.status);
                                                    stat = stat.replace(/^"|"$/g, '');
                                                    alert(stat);
                                                    var suc = "success";
                                                    var suc1 = "exists";
                                                    var suc2 = "activity";
                                                    if (stat == suc)
                                                    {
                                                        alert("add succesS!");
                                                    } else if (stat == suc1)
                                                    {
                                                        alert("Already Permission Granded!");
                                                    } 
                                                    else if (stat == suc2)
                                                    {
                                                        alert("Choose atleast one Activity!");
                                                    }
                                                    else
                                                    {
                                                        alert("record not should be added!");
                                                    }
                                                });
                                    }
                                })
                                        .directive("checkboxGroup", function () {
                                            return {
                                                restrict: "A",
                                                link: function (scope, elem, attrs) {
                                                    if (scope.mod_activity.indexOf(scope.Moule_Activity.id) !== -1) {
                                                        elem[0].checked = true;
                                                    }
                                                    elem.bind('click', function () {
                                                        var index = scope.mod_activity.indexOf(scope.Moule_Activity.id);
                                                        if (elem[0].checked) {
                                                            if (index === -1)
                                                                scope.mod_activity.push(scope.Moule_Activity.id);
                                                        } else {
                                                            if (index !== -1)
                                                                scope.mod_activity.splice(index, 1);
                                                        }
                                                        scope.$apply(scope.mod_activity.sort(function (a, b) {
                                                            return a - b
                                                        }));
                                                    });
                                                }
                                            }
                                        });
    </script>
</body>