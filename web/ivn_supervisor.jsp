<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

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
                                    <h4>Vehicle Version</h4>
                                    <span>Vehicle and Models</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="page-header-breadcrumb">
                                <ul class="breadcrumb-title">
                                    <li class="breadcrumb-item">
                                        <a href="index.html"> <i class="icofont icofont-home"></i> </a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="#!">Vehicle</a> </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                               <div class="card-block marketing-card p-t-20 row">
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <a href="vehicle_version.jsp">    
                                                    <div class="card-block">
                                                        <span>Versions</span>
                                                        <i class="icofont icofont-settings-alt text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </a>    
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <a href="vehicle_listing.jsp">    
                                                    <div class="card-block">
                                                        <span>Vehicles</span>
                                                        <i class="icofont  icofont-wheel text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </a>    
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <a href="vehicle_model.jsp">    
                                                    <div class="card-block">
                                                        <span>Models</span>
                                                        <i class="icofont icofont-racings-wheel text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </a>    
                                        </div>
                                    </div>
                                   
                                   <div class="col-md-3 col-lg-3">
                                        <div class="card visitor-card">
                                            <a href="vehicle_add.jsp">    
                                                    <div class="card-block">
                                                        <span>Add Vehicles</span>
                                                        
                                                        <i class="icofont icofont-dashboard text-c-red"></i>  
                                                        <div class="clearfix"></div>
                                                    </div>
                                            </a>    
                                        </div>
                                    </div>
                                   
                                </div>
                            </div>
                        </div>
                            

<%@include file="footer.jsp" %>

     
</body>

</html>