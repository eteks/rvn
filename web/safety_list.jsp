<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>

                    <div class="pcoded-content" ng-controller="RecordCtrl1 as Demo">
                        <div class="pcoded-inner-content">
                            <div class="main-body">

                                <div class="page-wrapper">

                                    <div class="page-header card">
                                        <div class="row align-items-end">
                                            <div class="col-lg-8">
                                                <div class="page-header-title">
                                                    <i class="icofont  icofont-mining bg-c-red"></i>
                                                    <div class="d-inline">
                                                        <h4>Safety</h4>
                                                        <span>Safety Listing</span>
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
                                                            <s:url action="safety.action" var="aURL" />
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
                                                        <form class="form-inline mt-3">
                                                            <div class="form-group">
                                                                <input type="text" ng-model="search" class="form-control" placeholder="Search">
                                                            </div>
                                                        </form>
                                                        <table st-table="rowCollection" class="table table-striped">
                                                                <thead>
                                                                <tr>
                                                                    
                                                                    <th ng-click="sort('version')" class="">No</th>
                                                                    <th ng-click="sort('version')" class="">Safety Version</th>
                                                                    <th ng-click="sort('vehicle')" class="">Vehicle Details</th>
                                                                    <th ng-click="sort('action')" class="text-center">Action</th>
                                                                    
                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getAllDomain_and_Features()">
                                                                    
                                                                    <tr dir-paginate="record in safety|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="">
                                                                           
                                                                                {{$index+1}}
                                                                                
                                                                        </td>
                                                                        <td class="">
                                                                           
                                                                                {{record.saf}}
                                                                                
                                                                        </td>
                                                                        <td class="text-left">
                                                                           <a class="mytooltip p-l-10 p-r-10 blink" href="javascript:void(0)"> 
                                                                                {{record.vehicle}}
                                                                                <span class="tooltip-content5">
                                                                                    <span class="tooltip-text3">
                                                                                        <span class="tooltip-inner2">
                                                                                            <h4>{{record.version}}</h4>
                                                                                            <ul class="model-list">
                                                                                                <li ng-repeat="mod in (record.model | customSplitString)"><i class="icofont icofont-hand-right"></i> {{mod}}</li>
                                                                                            </ul>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </a>
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                <a href="#" ng-click="removeRow(record.fid)"><i class="icofont icofont-ui-close text-c-red"></i></a>
                                                                                
                                                                        </td>
                                                                        
                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                            <dir-pagination-controls
                                                                max-size="5"
                                                                direction-links="true"
                                                                boundary-links="true" >
                                                            </dir-pagination-controls>
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            
                 
                                           
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope, $http)
        {
            
               this.data = [];
              $scope.safety = [{"saf":"1.0","model":"v1,v2","version":"1.0","vehicle":"Xuv"},
                                    {"saf":"2.0","model":"v1,v2","version":"2.0","vehicle":"Scorpio"}]; 
          
        });
        app.filter('customSplitString', function() 
        {
            return function(input) 
            {
                if(input !=undefined){
                    var arr = input.split(',');
                    return arr;
                }                
            };     
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    
    </script>   
</body>

</html>                                            