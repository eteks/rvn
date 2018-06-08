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
                                    <li class="breadcrumb-item"><a href="#!">Vehicle</a> </li>
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
                            <form class=""  name="myForm">
                                <div class="form-group">
                                    <label for="vehicle">Load vehicle version:</label>
                                    <select ng-model="formInfo.vehicleversion">
                                        <option>v1.0</option>
                                        <option>v2.0</option>
                                        <option>v3.0</option>
                                        <option>v4.0</option>
                                    </select>
                                </div>                
                                    <div>  
                                        <a href="" ng-click="Demo.data[Demo.data.length] = {}">Add element</a>
                                        <div ng-repeat="data in Demo.data">              
                                            <div class="form-group">
                                                <label for="vehicle">Vehicle:</label>
                                                <input type="text" class="form-control" placeholder="Enter vehicle" name="vehicle"  ng-model="data.myName" required>
                                                <span ng-show="myForm.vehicle.$touched && myForm.vehicle.$invalid">The name is required.</span>
                                            </div>
                                            <div class="form-group">
                                                <label for="model">Model:</label>
                                                <tags-input ng-model="data.tags"  use-strings="true"></tags-input>
                                            </div>
                                            <a href="" ng-click="Demo.data.splice($index,1)">×</a>
                                        </div>
                                        
                                    </div>
                                    <div class="form-group">
                                      <label for="status">Status:</label>

                                      <label class="switch">
                                          <input type="checkbox">
                                          <span class="slider round"></span>
                                        </label>
                                    </div>
                                    <button type="submit" class="btn btn-default">Submit</button>
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
    app.controller('MyCtrl', function() 
    {
        this.data = [];

    });
</script>
     
</body>

</html>