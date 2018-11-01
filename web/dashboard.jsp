<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
 <div class="pcoded-content" ng-init="getAllCount()" ng-controller="RecordCtrl">
                        <div class="pcoded-inner-content">
                            <div class="main-body">
                                <div class="page-wrapper">

                                    <div class="page-body">
                                        <div class="row">
                                            <!-- card1 start -->
                                            <div class="col-md-6 col-xl-3">
                                                <div class="card widget-card-1">
                                                    <div class="card-block-small">
                                                        
                                                        <i class="icofont icofont-users bg-c-blue card1-icon"></i>
                                                        <span class="text-c-blue f-w-600">Users</span>
                                                        <h4><s:property value="count_result.vehiclecount"/></h4>
                                                        <div>
                                                            <span class="f-left m-t-10 text-muted">
                                                                <i class="text-c-blue f-16 icofont icofont-warning m-r-10"></i>Get more space
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- card1 end -->
                                            
                                            <!-- card1 start -->
                                            <div class="col-md-6 col-xl-3">
                                                <div class="card widget-card-1">
                                                    <div class="card-block-small">
                                                        <i class="icofont icofont-car-alt-1 bg-c-pink card1-icon"></i>
                                                        <span class="text-c-pink f-w-600">vehicles</span>
                                                        <h4><s:property value="count_result.vehiclecount"/></h4>
                                                        <div>
                                                            <span class="f-left m-t-10 text-muted">
                                                                <i class="text-c-pink f-16 icofont icofont-calendar m-r-10"></i>year 2018
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- card1 end -->
                                            
                                            <!-- card1 start -->
                                            <div class="col-md-6 col-xl-3">
                                                <div class="card widget-card-1">
                                                    <div class="card-block-small">
                                                    <i class="icofont icofont-chart-flow bg-c-yellow card1-icon"></i>
                                                        <span class="text-c-yellow f-w-600">Models</span>
                                                        <h4><s:property value="count_result.modelcount"/></h4>
                                                        <div>
                                                            <span class="f-left m-t-10 text-muted">
                                                                <i class="text-c-yellow f-16 icofont icofont-refresh m-r-10"></i>Just update
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- card1 end -->

                                            <!-- card1 start -->
                                            <div class="col-md-6 col-xl-3">
                                                <div class="card widget-card-1">
                                                    <div class="card-block-small">
                                                        <i class="icofont icofont-circuit bg-c-green card1-icon"></i>
                                                        <span class="text-c-green f-w-600">ECU</span>
                                                        <h4><s:property value="count_result.ecucount"/></h4>
                                                        <div>
                                                            <span class="f-left m-t-10 text-muted">
                                                                <i class="text-c-green f-16 icofont icofont-tag m-r-10"></i>Tracked from list
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- card1 end -->
                                            <!-- Statistics Start -->
                                            <div class="col-md-12 col-xl-8">
                                                <div class="card ">
                                                    <div class="card-header ">
                                                        <div class="card-header-left ">
                                                            <h5>Notifications</h5>
                                                        </div>
                                                        <div class="card-header-right">
                                                            <ul class="list-unstyled card-option">
                                                                <li><i class="icofont icofont-simple-left "></i></li>
                                                                <li><i class="icofont icofont-maximize full-card"></i></li>
                                                                <li><i class="icofont icofont-minus minimize-card"></i></li>
                                                                <li><i class="icofont icofont-refresh reload-card"></i></li>
                                                                <li><i class="icofont icofont-error close-card"></i></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="card-block p-0" ng-controller="notificationController" style="overflow-y: scroll;height: 292px;">
                                                        <div ng-if="addlist.length ===0">
                                                            <div class="card-comment ">
                                                                <div class="card-block-small">
                                                                    <div class="comment-desc" style="padding-left: 16em;">
                                                                        <h6>No Notification</h6>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div ng-repeat="x in addlist" ng-if="addlist.length > 0">
                                                            <div data-id="{{x.id}}" ng-click="view(x.id)" ng-if="x.status === false" style="cursor: pointer;background-color: #845f5f45;" class="card-comment ">
                                                                <div class="card-block-small">
                                                                    <img class="img-radius img-50" src="images/avatar-4.jpg" alt="user-1">
                                                                    <div class="comment-desc">
                                                                        <h6>{{x.firstname}}</h6>
                                                                        <p class="text-muted ">{{x.version_type_id}} {{x.version_id}} was created successfully</p>         
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div data-id="{{x.id}}" ng-click="view(x.id)" ng-if="x.status === true" style="cursor: pointer;" class="card-comment ">
                                                                <div class="card-block-small">
                                                                    <img class="img-radius img-50" src="images/avatar-4.jpg" alt="user-1">
                                                                    <div class="comment-desc">
                                                                        <h6>{{x.firstname}}</h6>
                                                                        <p class="text-muted ">{{x.version_type_id}} {{x.version_id}} was created successfully</p>         
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--<div class="card-comment ">
                                                            <div class="card-block-small">
                                                                <img class="img-radius img-50" src="images/avatar-4.jpg" alt="user-4">
                                                                <div class="comment-desc">
                                                                    <h6>User_1</h6>
                                                                    <p class="text-muted ">Vehicle version 2.0 was created successfully</p>         
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="card-comment ">
                                                            <div class="card-block-small">
                                                                <img class="img-radius img-50" src="images/avatar-4.jpg" alt="user-2">
                                                                <div class="comment-desc">
                                                                    <h6>User_1</h6>
                                                                    <p class="text-muted ">Vehicle version 1.0 was created successfully.</p>
                                                                </div>
                                                            </div>
                                                        </div>-->
                                                    </div>
                                                </div>
                                                
                                            </div>
                                            <div class="col-md-12 col-xl-4">
                                                <div class="card-header text-center">
                                                        <h5>Features</h5>             
                                                </div>
                                                <div class="card-block">
                                                        <div id="chart3"></div>
                                                </div>
                                            </div>

                                            <div class="col-md-12 col-xl-6">
                                                <div class="card">
                                                        <div class="card-header">
                                                            <h5 class="text-center">Vehicle Completion</h5>
                                                            <div class="card-header-left ">
                                                            </div>
                                                            <div class="card-header-right">
                                                                <ul class="list-unstyled card-option">
                                                                    <li><i class="icofont icofont-simple-left "></i></li>
                                                                    <li><i class="icofont icofont-maximize full-card"></i></li>
                                                                    <li><i class="icofont icofont-minus minimize-card"></i></li>
                                                                    <li><i class="icofont icofont-refresh reload-card"></i></li>
                                                                    <li><i class="icofont icofont-error close-card"></i></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="card-block">
                                                            <div id="statestics-chart" style="height:330px;"></div>
                                                        </div>
                                                </div>
                                            </div>
                                             <!-- CATEGORIES CHART start -->
                                            <div class="col-md-12 col-lg-6">
                                                <div class="card">
                                                    <div class="card-header">
                                                        <h5>User Categories chart</h5>
                                                    </div>
                                                    <div class="card-block">
                                                        <div id="placeholder" class="demo-placeholder" style="height:300px;"></div>
                                                    </div>
                                                </div>
                                            </div> 

                                        </div>
                                    </div>
                                </div>



<%@include file="footer.jsp" %>
    <!-- am chart -->
       <script src="js/amchart/amcharts.min.js"></script>
       <script src="js/amchart/serial.min.js"></script>

    <!-- c3 chart js -->
       <script src="js/chart/d3.min.js"></script>
       <script src="js/chart/c3.js"></script>
       <script src="js/chart/c3-custom-chart.js"></script>
       <!-- Custom js -->

       <!-- Float Chart js -->
       <script src="js/chart/jquery.flot.js"></script>
       <script src="js/chart/jquery.flot.categories.js"></script>
       <script type="text/javascript" src="js/chart/float-chart-custom.js"></script>
   </body>

</html>