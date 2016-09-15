// define the Collaboration app module.
var collabAppModule = angular.module("collabApp", ['fsm']);

// main controller
var mainController = collabAppModule.controller("mainController", function($scope) {
    $scope.helloTo = {};
    $scope.helloTo.title = "AngularJS";
});