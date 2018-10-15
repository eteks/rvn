<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<style>
    .pcoded[theme-layout="vertical"][vertical-nav-type="expanded"] .pcoded-header .pcoded-left-header, .pcoded[theme-layout="vertical"][vertical-nav-type="expanded"] .pcoded-navbar{
        display:none;
    }
</style>
<div ng-controller="filterDemo as ctrl">

   
    <input type="radio" value="_id" ng-model="ctrl.searchField"/>
    <input type="radio" value="name" ng-model="ctrl.searchField"/>
    <input type="radio" value="phone" ng-model="ctrl.searchField"/>
    <input type="radio" value="dob" ng-model="ctrl.searchField"/>
    <span ng-click="ctrl.filterList = {type: 1}">Type 1</span> | 
    <span ng-click="ctrl.filterList = {type: 2}">Type 2</span> |
    <span ng-click="ctrl.filterList = {type: 3}">Type 3</span> |
    <!-- multiple filter - not working -->
    <span ng-click="ctrl.filterList = myFunction">Types 1 & 3</span> |
    <span ng-click="ctrl.filterList = null">No filter</span>

    <label>Search For: <input ng-model="ctrl.searchText"></label>
    <table id="searchTextResults">
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Birthday</th>
      </tr>
      <tr ng-repeat="friend in ctrl.friends | filter:ctrl.filterList">
        <td>{{friend._id}}</td>
        <td>{{friend.name}}</td>
        <td>{{friend.phone}}</td>
        <th>{{friend.dob.toDateString()}}</th>
      </tr>
    </table>
  </div> 
</div>  
</div>   
</div>   
<%@include file="footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <!--<script src="js/dirPagination.js"></script>-->
    <script>
//        var app = angular.module('angularTable', ['angularUtils.directives.dirPagination']);

        app.controller('filterDemo',function($scope, $http)
        {
            
              let vm = this;
                vm.searchField = ""
                    vm.searchText = ""
                    vm.friends = [{
                      _id: 12323,
                      name: 'Will',
                      phone: '555-1276',
                      dob: new Date(1990,00,20)
                    }, {
                      _id: 34645764576,
                      name: 'Mike',
                      phone: '555-4321',
                      dob: new Date(1967,01,02)
                    }, {
                      _id: 6565656795,
                      name: 'Toni',
                      phone: '555-5678',
                      dob: new Date(1967,05,21)
                    }, {
                      _id: 2565656,
                      name: 'Leilani',
                      phone: '808-BIG-WAVE',
                      dob: new Date(2007,01,01)
                    }, {
                      _id: 67567567,
                      name: 'Julie',
                      phone: '555-8765',
                      dob: new Date(1991,12,01)
                    }, {
                      _id: 477676767,
                      name: 'Juliette',
                      phone: '555-5678',
                      dob: new Date(1991,12,01)
                    }, {
                      _id: 2565656,
                      name: 'Mary',
                      phone: '800-BIG-MARY',
                      dob: new Date(1991,12,01)
                    }]

                    vm.filterList = filterList

                    function filterList(row) {
                      if (vm.searchField && vm.searchText) {
                        let propVal = row[vm.searchField]
                        if (propVal) {
                            return propVal.toString().toUpperCase().indexOf(vm.searchText.toUpperCase()) > -1;
                        } else {
                          return false;
                        }
                      }
                      return true;
                    };
                    app.filter('customSplitString', function() 
                    {
                        return function(input) 
                        {
                            var arr = input.split(',');
                            return arr;
                        };     
                    });
            
        });

    $(document).ready(function(){
        // initialize modal
        $('.modal-trigger').leanModal();
    });
    
    </script>   
</body>

</html>                                            