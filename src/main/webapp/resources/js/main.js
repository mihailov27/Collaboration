// define the Collaboration app module.
var collabAppModule = angular.module("collabApp", []);

// header controller
var headerController = collabAppModule.controller("headerController", function($scope) {

});

// main controller
var mainController = collabAppModule.controller("mainController", function($scope) {
    $scope.helloTo = {};
    $scope.helloTo.title = "AngularJS";
});