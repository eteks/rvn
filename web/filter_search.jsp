<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content" ng-app="angularTable" ng-controller="RecordCtrl1">
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
                                                        <span>Vehicle listing</span>
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

                                            <!-- Marketing Start -->
                                            <div class="col-md-12">
                                                <div class="card">
                                                    <div class="card-block marketing-card p-t-0">
                                                        <input type="checkbox" ng-click="includeColour('1,2')"/> Red</br/>
                                                        <input type="checkbox" ng-click="includeColour('1')"/> Orange</br/>
                                                        <input type="checkbox" ng-click="includeColour('2,3')"/> Yellow</br/>

                                                        <ul>
                                                            <li ng-repeat="f in signal_list | filter:tagFilter">
                                                                {{f.tag_id}}
                                                            </li>
                                                        </ul>                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Marketing End -->
                                            
<%@include file="footer.jsp" %>

  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http)
        {
                 $scope.signal_list = [{"tagname":"tag1","tag_id":1,"signal_id":"1,2"},{"tagname":"tag2","tag_id":2,"signal_id":"1"},
                     {"tagname":"tag3","tag_id":3,"signal_id":"1"},{"tagname":"tag5","tag_id":5,"signal_id":"2"}];

            $scope.colourIncludes = [];
            var unique = function(origArr) 
            {
                var newArr = [],
                    origLen = origArr.length,
                    found, x, y;

                for (x = 0; x < origLen; x++) {
                    found = undefined;
                    for (y = 0; y < newArr.length; y++) {
                        if (origArr[x] === newArr[y]) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        newArr.push(origArr[x]);
                    }
                }
                return newArr;
            }
            $scope.includeColour = function(signal_id) 
            {
//                alert(signal_id);
                var arr_sig = signal_id.split(",");
//                var sigmat=JSON.stringify(arr_sig);
                var i = $.inArray(arr_sig, $scope.colourIncludes);
                if (i > -1) 
                {
                    $scope.colourIncludes.splice(i, 1);
                } 
                else 
                {
                    $scope.colourIncludes.push(arr_sig);
//                    var arr_sig = ;
                    alert($scope.colourIncludes);
                    
                }
            }

            $scope.tagFilter = function(signal_list) 
            {
                if ($scope.colourIncludes.length > 0) 
                {
                    if ($.inArray(signal_list.colour, $scope.colourIncludes) < 0)
                        return;
                }

                return signal_list;
            }
        });

    
    </script>   
</body>

</html>                                            