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
                                                        <h4>Admin</h4>
                                                        <span>User Listing</span>
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
                                                            <s:url action="admin.action" var="aURL" />
                                                            <s:a href="%{aURL}">
                                                                Back
                                                            </s:a>
                                                        </li>
                                                        <li class="breadcrumb-item">
                                                            <a class="waves-effect waves-light modal-trigger" href="#modal-product-form" ng-click="showCreateForm()">Add User</a> 
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
                                                        <table st-table="rowCollection" class="table table-striped" ng-init="getAll()">
                                                                <thead>
                                                                <tr>
                                                                    <th ng-click="sort('groupname')" class="text-center">Employee ID</th>
                                                                    <th ng-click="sort('route')" class="text-center">First Name</th>
                                                                    <th ng-click="sort('action')" class="text-center">Email</th>
                                                                    <th ng-click="sort('action')" class="text-center">Mobile Number</th>
                                                                    <th ng-click="sort('action')" class="text-center">Group</th>
                                                                    <th ng-click="sort('action')" class="text-center">Approved</th>
                                                                    <th ng-click="sort('status')" class="text-center">Status</th>
                                                                    <th ng-click="sort('status')" class="text-center">Action</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody ng-init="getUsers()">
                                                                    
                                                                    <tr dir-paginate="user in users|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
                                                                        
                                                                       <td class="text-center">
                                                                           
                                                                                {{user.employee_id}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{user.firstname}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{user.email}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                                {{user.mobile_number}}
                                                                                
                                                                        </td>
                                                                         <td class="text-center">
                                                                           
                                                                                {{user.group_name}}
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
                                                                           
                                                                             <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="user.email_status === true">             Verified
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="user.email_status === false">             UnVerified
                                                                            </button>
                                                                                
                                                                        </td>
                                                                         <td class="text-center">
                                                                           
                                                                             <button class="btn btn-default btn-bg-c-blue btn-outline-default btn-round btn-action" ng-if="user.status === true">             Active
                                                                            </button>

                                                                            <button class="btn btn-default btn-bg-c-yellow btn-outline-default btn-round btn-action" ng-if="user.status === false">             Inactive
                                                                            </button>
                                                                                
                                                                        </td>
                                                                        <td class="text-center">
<!--                                                                      <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-id="{{record.id}}" ng-click="View_and_edit($event)" name="edit" ng-if="record.status === false">Edit</button>-->
<!--                                                                      <a class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round" data-target="modal-product-form" ng-click="editUser(user.employee_id)" name="edit">Edit</a>-->
                                                                          <button class="btn btn-default btn-bg-c-blue btn-outline-primary btn-round modal-trigger" data-target="modal-product-form" ng-click="editUser(user.id)">Edit</button> 
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
                                            <!-- Marketing End -->
                                            <div id="modal-product-form" class="modal">
                                                <div class="modal-content" id="addUser">
                                                    <h5 class="text-c-red m-b-25">Add User<a class="modal-action modal-close waves-effect waves-light float-right m-t-5" ><i class="icofont icofont-ui-close"></i></a></h5>

                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="id" type="hidden" id="form-name"/>
                                                            <input ng-model="username" type="text" class="validate col-lg-12" id="form-name" placeholder="Username"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="emp_id" type="text" class="validate col-lg-12" id="form-name" placeholder="Employee Id"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="firstname" type="text" class="validate col-lg-12" id="form-name" placeholder="Firstname"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="lastname" type="text" class="validate col-lg-12" id="form-name" placeholder="Lastname"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="password" type="password" class="validate col-lg-12" id="form-name" placeholder="Password"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="confirm_password" type="password" class="validate col-lg-12" id="form-name" placeholder="Confirm Password"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="email" type="email" class="validate col-lg-12" id="form-name" placeholder="Email"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Domain</label>-->
                                                            <input ng-model="supervisor_email" type="email" class="validate col-lg-12" id="form-name" placeholder="Supervisor Email"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <!--<label for="name">Feature</label>-->
                                                            <input ng-model="mobile_number" type="text" class="validate  col-lg-12" id="form-name" placeholder="Mobile Number"/>
                                                        </div>
                                                        <div class="form-group" ng-init="getAll()">
                                                            <!--<label for="name">Feature</label>-->
                                                            <select  ng-model="group_option" ng-change="assignstart(myOption)"
                                                                        ng-options="names.id as names.group_name for names in names">
                                                                        <option>-select group-</option>
                                                                </select>
                                                        </div>
                                                        <div class="form-group">
                                                            
                                                            <label for="status" style="vertical-align:middle">Status:</label>
                                                            <label class="switch m-r-50"  style="vertical-align:middle">
                                                                <input type="checkbox" ng-model="status">
                                                                <span class="slider round"></span>
                                                             </label>
                                                            
                                                            <label for="status" style="vertical-align:middle">Is Email Verify:</label>
                                                            <label class="switch m-r-50"  style="vertical-align:middle">
                                                                <input type="checkbox" ng-model="emailVerify">
                                                                <span class="slider round"></span>
                                                             </label>
                                                            
<!--                                                            <label for="name">Is Admin</label>
                                                            <input ng-model="pages" style="width:auto"type="checkbox" class="validate  col-lg-12" id="form-name" placeholder="Route Pages"/>-->
                                                        </div>
                                                        <div class="input-field text-right">
                                                            <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right cUser" ng-click="createuser()">Add User</button>
                                                            <button id="btn-create-product" class="waves-effect waves-light btn margin-bottom-1em float-right uUser" ng-click="updateuser()" style="display: none;">Update User</button>
                                                        </div>
                                                </div>
                                            </div>
                                            
<%@include file="footer.jsp" %>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>-->
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('RecordCtrl1',function($scope,$window, $http)
        {
            
               this.data = [];
//              $scope.features = [
//                { fid:'1',domain:'d1',fea: 'feature1'},
//                { fid:'2',domain:'d1',fea: 'feature2'},
//                { fid:'3',domain:'d2',fea: 'feature3'},
//                { fid:'4',domain:'d2',fea: 'feature4'}
//              ]; 
            $scope.getAll = function () {
//                                                 $scope.names = data.data.userGroupList;
                            $http.get("getAllUserGroup.action").then(function(response, data, status, headers, config){
//                                            console.log(JSON.stringify(response.data.result_data));
//alert(JSON.stringify();
//                                            var data = response.data.result_data;
//                                        alert(JSON.stringify(response));
//                                        alert("<s:property value="result_data_obj"/>");
                            var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
                            $scope.names = data;
//                                        alert(JSON.stringify($scope.names));
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
            $scope.getAllDomain_and_Features = function(){
//                alert("getall");
                $http.get("features_listing.action").then(function(response, data, status, headers, config){
//                        alert("<s:property value="result_data_obj"/>");
                        var data = JSON.parse("<s:property value="result_data_obj"/>".replace(/&quot;/g,'"'));
                        $scope.features = data;
                });
            }
                    
                    
            $scope.sort = function(keyname)
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            $scope.removeRow = function(fid)
            {				
		var index = -1;		
		var comArr = eval( $scope.features );
		for( var i = 0; i < comArr.length; i++ ) 
                {
                    if( comArr[i].fid === fid ) 
                    {
                        index = i;
                        break;
                    }
		}
		if( index === -1 ) 
                {
			alert( "Something gone wrong" );
		}
                
		$scope.features.splice( index, 1 );		
            };
            // read all vehicle
            $scope.getAllVehicle = function(){
//                alert("getall");
                $http.get("vehicleversion_listing.action").then(function(response, data, status, headers, config){

                        var data = JSON.parse("<s:property value="vehmod_map_result_obj"/>".replace(/&quot;/g,'"'));
//                        alert(JSON.stringify(data));
                        $scope.records = data;
                });
            };
            $scope.showCreateForm = function(){
                $(".cUser").show();
                $(".uUser").hide();
            };
            $scope.editUser = function(id){
                $('.modal-trigger').leanModal();
                $http.get("userDetails?employee_id="+id).then(function(data){
                    //console.log(data.data.userDetails);
                    $scope.id = data.data.userDetails[0].id;
                    $scope.username = data.data.userDetails[0].username;
                    $scope.emp_id = data.data.userDetails[0].employee_id;
                    $scope.firstname = data.data.userDetails[0].firstname;
                    $scope.lastname = data.data.userDetails[0].lastname;
                    $scope.password = data.data.userDetails[0].password;
                    $scope.confirm_password = data.data.userDetails[0].password;
                    $scope.email = data.data.userDetails[0].email;
                    $scope.supervisor_email = data.data.userDetails[0].supervisor_email;
                    $scope.mobile_number = data.data.userDetails[0].mobile_number;
                    $scope.group_option = data.data.userDetails[0].group_id;
                    $scope.status = data.data.userDetails[0].status;
                    $scope.emailVerify = data.data.userDetails[0].email_status;
                });
                $(".cUser").hide();
                $(".uUser").show();
            };
            $scope.updateuser = function(){
                if($scope.username == undefined || $scope.emp_id == undefined || $scope.firstname == undefined || $scope.lastname == undefined || $scope.password == undefined || $scope.email == undefined || $scope.supervisor_email == undefined || $scope.mobile_number == undefined || $scope.group_option == undefined){
                    alert("Please fill all fields");
                    return ;
                }
                if($scope.status == undefined ){
                    $scope.status == false;
                }
                if($scope.password !== $scope.confirm_password){
                    alert("Password doesn't match");
                    return ;
                }
                var user_detail = {
                    "id" : $scope.id,
                    "user_name" : $scope.username,
                    "emp_id" : $scope.emp_id,
                    "first_name" : $scope.firstname,
                    "last_name" : $scope.lastname,
                    "password" : $scope.password,
                    "email" : $scope.email,
                    "supervisor_email" : $scope.supervisor_email,
                    "mobile_number" : $scope.mobile_number,
                    "group_id" : $scope.group_option
                }
                var data = {
                    "user_details" : user_detail,
                    "status" : $scope.status,
                    "emailVerify" : $scope.emailVerify
                }
                //console.log(data);
                $http({
                    url : 'userUpdate',
                    method : "POST",
                    data : data
                })
                .then(function success(data){
                    var resposeJson = data.data.updateStatus;
                    if(resposeJson.hasOwnProperty('empStatus') && resposeJson.hasOwnProperty('emailStatus')) {
                        alert("Email & Employee ID already exists");
                        return;
                    }
                    if(resposeJson.hasOwnProperty('empStatus')){
                        alert(resposeJson.empStatus);
                        return;
                    }else if(resposeJson.hasOwnProperty('emailStatus')){
                        alert(resposeJson.emailStatus);
                        return;
                    }else{
                        alert(resposeJson.updateStatus);
                        $("#addUser .modal-close").click();
                        $scope.getUsers();
//                        $window.open("users.action","_self");
                        return;
                    }
                    }, function error() {
                        alert("Error in Updating User");
                    });
            };
            $scope.createuser = function()
            {
                if($scope.username == undefined || $scope.emp_id == undefined || $scope.firstname == undefined || $scope.lastname == undefined || $scope.password == undefined || $scope.email == undefined || $scope.supervisor_email == undefined || $scope.mobile_number == undefined || $scope.group_option == undefined){
                    alert("Please fill all fields");
                    return ;
                }
                if($scope.status == undefined ){
                    $scope.status == false;
                }
                if($scope.emailVerify == undefined ){
                    $scope.emailVerify == false;
                }
                if($scope.password !== $scope.confirm_password){
                    alert("Password doesn't match");
                    return ;
                }
                var user_detail = {
                    "user_name" : $scope.username,
                    "emp_id" : $scope.emp_id,
                    "first_name" : $scope.firstname,
                    "last_name" : $scope.lastname,
                    "password" : $scope.password,
                    "email" : $scope.email,
                    "supervisor_email" : $scope.supervisor_email,
                    "mobile_number" : $scope.mobile_number,
                    "group_id" : $scope.group_option
                }
                var data = {
                    "user_details" : user_detail,
                    "status" : $scope.status,
                    "emailVerify" : $scope.emailVerify
                }
                //console.log(data);
                $http({
                    url : 'userCreation',
                    method : "POST",
                    data : data
                })
                .then(function success(data){
                    var resposeJson = data.data.returnStatus;
                    if(resposeJson.hasOwnProperty('empStatus') && resposeJson.hasOwnProperty('emailStatus')) {
                        alert("Email & Employee ID already exists");
                        return;
                    }
                    if(resposeJson.hasOwnProperty('empStatus')){
                        alert(resposeJson.empStatus);
                        return;
                    }else if(resposeJson.hasOwnProperty('emailStatus')){
                        alert(resposeJson.emailStatus);
                        return;
                    }else if(resposeJson.hasOwnProperty('mailStatus')){
                        alert(resposeJson.mailStatus);
                        return;
                    }else{
//                        alert(JSON.stringify(resposeJson));
                        alert("User created Successfully");
//                        alert(resposeJson.insertStatus);
                        $("#addUser .modal-close").click();
                        $window.open("users.action","_self");                        
                        return;
                    }
                    }, function error() {
                        alert("Error in Adding User");
                    });
            };
            
            $scope.getUsers = function(){
//                alert("getall");
                $http.get("userList").then(function(data){
                    //console.log(data.data.userList);
                    $scope.users = data.data.userList;
                });
            }
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    </script>   
</body>

</html>                                            