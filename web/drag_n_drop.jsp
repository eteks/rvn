<!DOCTYPE html>
<html ng-app="plunker">

  <head>
    <meta charset="utf-8" />
    <title>AngularJS Plunker</title>
    <script>document.write('<base href="' + document.location + '" />');</script>
    <link rel="stylesheet" href="style.css" />
    <script data-require="angular.js@1.2.x" src="http://code.angularjs.org/1.2.13/angular.js" data-semver="1.2.13"></script>
    <!--<script src="app.js"></script>-->
    <style>
        /**
         * The dnd-list should always have a min-height,
         * otherwise you can't drop to it once it's empty
         */
        .simpleDemo ul[dnd-list] {
            min-height: 42px;
            padding-left: 0px;
        }

        /**
         * The dndDraggingSource class will be applied to
         * the source element of a drag operation. It makes
         * sense to hide it to give the user the feeling
         * that he's actually moving it.
         */
        .simpleDemo ul[dnd-list] .dndDraggingSource {
            display: none;
        }

        /**
         * An element with .dndPlaceholder class will be
         * added to the dnd-list while the user is dragging
         * over it.
         */
        .simpleDemo ul[dnd-list] .dndPlaceholder {
            background-color: #ddd;
            display: block;
            min-height: 42px;
        }

        .simpleDemo ul[dnd-list] li {
            background-color: #fff;
            border: 1px solid #ddd;
            border-top-right-radius: 4px;
            border-top-left-radius: 4px;
            display: block;
            padding: 10px 15px;
            margin-bottom: -1px;
        }

        /**
         * Show selected elements in green
         */
        .simpleDemo ul[dnd-list] li.selected {
            background-color: #dff0d8;
            color: #3c763d;
        }
    </style>
  </head>

  <body ng-controller="MainCtrl">
        <ul dnd-list="list">
            <li ng-repeat="item in list"
                dnd-draggable="item"
                dnd-moved="list.splice($index, 1)"
                dnd-effect-allowed="move"
                dnd-selected="models.selected = item"
                ng-class="{'selected': models.selected === item}"
                >
                {{item.label}}
            </li>
        </ul>
  </body>
  <script>
    var app = angular.module('plunker', []);
    app.controller('MainCtrl', function($scope) {
      $scope.models = {
        selected: null,
        lists: {"A": [], "B": []}
    };

    // Generate initial model
    for (var i = 1; i <= 3; ++i) {
        $scope.models.lists.A.push({label: "Item A" + i});
        $scope.models.lists.B.push({label: "Item B" + i});
    }

    // Model to JSON for demo purpose
    $scope.$watch('models', function(model) {
        $scope.modelAsJson = angular.toJson(model, true);
    }, true);
    });
  </script>
</html>