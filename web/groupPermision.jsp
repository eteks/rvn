
<body ng-app="myApp" ng-init="getModule_Group()" ng-controller="group_permisionctrl">
    <div class="container">

        <div class="col-md-12">
            <!-- Authentication card start -->
            <div class="login-card card-block auth-body mr-auto ml-auto">
                <form class="md-float-material" ng-submit="submit()">
                    <h3>Group Name</h3>
                    <select ng-model="data.groupName"
                            required>
                        <option ng-repeat="groupName in grouplist" ng-model="groupId" value={{groupName.id}}>{{groupName.groupname}}</option>
                    </select>

                    <h3>Module Name</h3>
                    <select ng-model="data.mudulename" ng-change="getSelectedModulePermission()" >
                        <option ng-repeat="moduleName in moduleList" value={{moduleName.id}}>{{moduleName.moduleName}}</option>
                    </select>
                    <h3>Permission </h3> 
                    <select  ng-model="data.selectmultiple" ng-change="selectMultiModels(data.selectmultiple)" data-ng-options="record as record.name for record in filterRecords">
                        <option value="">Select module</option>
                    </select>
                    <input type="submit" id="submit" value="Add" class="btn bg-c-red btn-md btn-block waves-effect text-center m-b-20"/>
                    {{multiModelId}} {{multiModelName}}

                </form>
            </div>
            <!-- Authentication card end -->
        </div>

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

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script> 

    <script>

                                var app = angular.module('myApp', []);
                                app.controller("group_permisionctrl", function ($scope, $http)
                                {
                                    $scope.multiModelName = [];
                                    $scope.multiModelId = [];
                                    $scope.records = [{id: 1, name: "add"}, {id: 2, name: "edit"},
                                        {id: 3, name: "view"}, {id: 4, name: "delete"}];
                                    $scope.filterRecords = [];
                                    $scope.selectMultiModels = function (item)
                                    {
                                        $scope.multiModelId.push(item.id);
                                        $scope.multiModelName.push(item.name);
                                    }
                                    $scope.getModule_Group = function ()
                                    {
                                        $http.get("getModule_Group.action")
                                                .then(function (data, status, headers, config) {
                                                    $scope.grouplist = data.data.groupList;
                                                    $scope.moduleList = data.data.moduleList;
                                                });
                                    }
                                    $scope.getSelectedModulePermission = function ()
                                    {

                                        $http.post("getSelectedModulePermission.action", {
                                            'module_id': $scope.data.mudulename
                                        })
                                                .then(function (data, status, headers, config) {
                                                    $scope.names = data.data.modulePermision;

                                                    angular.forEach($scope.names, function (item)
                                                    {
                                                        angular.forEach($scope.records, function (item1)
                                                        {
                                                            if (item.operationid === item1.id)
                                                            {
                                                               // alert(item1.id);
                                                                $scope.filterRecords.push({id: item1.id, name: item1.name});
                                                            }
                                                        })
                                                    })

                                                    var operationId = JSON.stringify(data.data.modulePermision);
                                                    alert(operationId);
                                                });
                                    }
                                    $scope.submit = function ()
                                    {
 //                                      var groupname=JSON.stringify($scope.data.groupName);
 //                                     var modulename=JSON.stringify($scope.data.groupName);
                                        alert(JSON.stringify($scope.data.groupName));
                                        // alert(groupname);
                                        if (JSON.stringify($scope.data.groupName) !== undefined)
                                        {
//                                            alert("inside group--");
//                                            if (JSON.stringify($scope.data.modulename) !== undefined)
//                                            {
                                                $scope.data.selectmultiple = $scope.multiModelId;
                                                $http({
                                                    url: 'createGroupPermission',
                                                    method: "POST",
                                                    data: $scope.data
                                                })
                                                        .then(function (data, status, headers, config) {
                                                            alert();
                                                            var stat = JSON.stringify(data.data.maps.status);
                                                            stat = stat.replace(/^"|"$/g, '');
                                                            var suc = "success";
                                                            var suc1 = "activity";
                                                            if (stat == suc)
                                                            {
                                                                alert("Success! group created");
                                                            }
                                                        });
//                                            } else {
//                                                alert("choose the module name");
//                                            }
                                        } else
                                        {
                                            alert("choose the group name");
                                        }
                                    }
                                });
    </script>
</body>