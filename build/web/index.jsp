<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Global IVN</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="description" content="#">
        <meta name="keywords" content="Admin , Responsive, Landing, Bootstrap, App, Template, Mobile, iOS, Android, apple, creative app">
        <meta name="author" content="#">
        <!-- Favicon icon -->
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <!-- Google font-->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet">
        <!-- Required Fremwork -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

        <!-- ico font -->
        <link rel="stylesheet" type="text/css" href="css/icofont.css">

        <!-- Style.css -->
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/custom.css">


    </head>

    <body class="fix-menu"  ng-app="myApp">
        <!-- Pre-loader start -->
        <div class="theme-loader">
            <div class="ball-scale">
                <div class='contain'>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                    <div class="ring"><div class="frame"></div></div>
                </div>
            </div>
        </div>
        <!-- Pre-loader end -->

        <section class="login p-fixed d-flex text-center common-img-bg" ng-controller="LoginController">
            <!-- Container-fluid starts -->
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <!-- Authentication card start -->
                        <div class="login-card card-block auth-body mr-auto ml-auto">
                            <form class="md-float-material" ng-submit="submit()">
                                <div class="text-center">
                                    <img src="images/logo.png" alt="logo.png">
                                </div>
                                <div class="auth-box">
                                    <div class="row m-b-20">
                                        <div class="col-md-12">
                                            <h3 class="text-left txt-primary">Sign In</h3>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Username" name="username" ng-model="username" >
                                        <span class="md-line"></span>
                                    </div>
                                    <div class="input-group">
                                        <input type="password" class="form-control" placeholder="Password"  ng-model="password" name="password">
                                        <span class="md-line"></span>
                                    </div>
                                    <div class="row m-t-25 text-left">
                                        <div class="col-12">

                                            <div class="forgot-phone text-right f-right">
                                                <a href="#" class="text-right f-w-600 text-inverse"> Forgot Password?</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row m-t-30">
                                        <div class="col-md-12">
                                            <input type="submit" id="submit" value="Submit" class="btn bg-c-red btn-md btn-block waves-effect text-center m-b-20"/>

                                        </div>
                                    </div>
                                    <hr/>
                                </div>
                            </form>
                            <!-- end of form -->
                        </div>
                        <!-- Authentication card end -->
                    </div>
                    <!-- end of col-sm-12 -->
                </div>
                <!-- end of row -->
            </div>
            <!-- end of container-fluid -->
        </section>

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

        <script>
                                            var app = angular.module('myApp', []);
                                            app.controller("LoginController", function ($scope, $http, $window) {
                                                $scope.submit = function () {
                                                    if($scope.username === "" || typeof $scope.username === 'undefined' && $scope.password === "" || typeof $scope.password === 'undefined')
                                                    {
                                                        $window.alert("fill the username and password");
                                                    }
                                                        else
                                                        {
                                                            $http.post("UserLoginProcess.action", {
                                                        'username': $scope.username,
                                                        'password': $scope.password
                                                    })
                                                            .then(function (data, status, headers, config) {
                                                                var stat = JSON.stringify(data.data.maps.status);
                                                                stat = stat.replace(/^"|"$/g, '');
                                                                var suc = "success";
                                                                if (suc == stat)
                                                                {
                                                                    $window.open("dashboard.action", "_self");
                                                                } else
                                                                {
                                                                    alert("invalid username and password");
                                                                }
                                                            });
                                                        }
                                                }
                                            });

        </script>
    </body>

</html>
