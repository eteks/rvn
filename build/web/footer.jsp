                                        </div><!-- row -->
                                    </div><!-- page body -->
                                </div><!-- page wrapper -->
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

     <!-- Required Jquery -->
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
     <!--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/angular.js"></script>--> 

    <script src="js/ng-tags-input.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
    
<!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/angularjs/1.6.9/angular-animate.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angularjs/1.6.9/angular-sanitize.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap.js"></script>    -->
    <script src="js/dirPagination.js"></script>
    <script>
        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination','ngTagsInput']);
//        var app = angular.module('angularTable', ['ui.bootstrap']);
        app.controller('RecordCtrl',function($scope, $http)
        {  
            $http.get("dashboardata.action").then(function(data, status, headers, config)
            {
//                alert(JSON.stringify(data.data));
                $scope.groups = data.data.group_result;
                $scope.vehiclecount =data.data.count_result.vehiclecount;
                $scope.modelcount = data.data.count_result.modelcount;
                $scope.vehicleversion_count = data.data.count_result.vehicleversion_count;
                $scope.pdbversion_count = data.data.count_result.pdbversion_count;
                $scope.pdbfeatures_count = data.data.count_result.pdbfeatures_count;
                $scope.count ={"ivnversion_count":data.data.count_result.ivnversion_count,
                                "ecucount":data.data.count_result.ecucount
                               } ;
            });
        });
    </script>
    