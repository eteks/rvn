<!DOCTYPE html>
<html ng-app="plunker">

  <head>
    <meta charset="utf-8" />
    <title>AngularJS Plunker</title>
    <script>document.write('<base href="' + document.location + '" />');</script>
    <link rel="stylesheet" href="style.css" />
    <script data-require="angular.js@1.2.x" src="http://code.angularjs.org/1.2.13/angular.js" data-semver="1.2.13"></script>
    <!--<script src="app.js"></script>-->
  </head>

  <body ng-controller="MainCtrl">
      <div ng-repeat="article in articles">
       <div ng-repeat="tag in article.tags">{{tag}}  
            <input type="radio" ng-model="tags" ng-value="tag"> 
      </div>
    </div> 
    <input type="text" ng-model="tags">
    <div ng-repeat="article in articles | filter:tag">
      <h3>{{article.name}}</h3>
      <p ng-repeat="tag in article.tags">{{tag}}</p>
    </div>
  </body>
  <script>
    var app = angular.module('plunker', []);

    app.controller('MainCtrl', function($scope) {
      $scope.articles = [
        {
          name: "NBA",
          tags: [
            "sport",
            "basketball"
          ]
        },
        {
          name: "NFL",
          tags: [
            "sport",
            "football"
          ]
        }
      ];

      $scope.tag = function(message) {
//          alert(message);
        if ($scope.tags) {
          return $scope.tags.replace(/\s*,\s*/g, ',').split(',').every(function(tag) {
            return message.tags.some(function(objTag){
              return objTag.indexOf(tag) !== -1;
            });
          });
        }
        else {
          return true;
        }
      };

    });
  </script>
</html>