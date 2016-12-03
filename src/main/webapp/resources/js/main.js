// define the Collaboration app module.
var collabAppModule = angular.module("collabApp", []);

// main controller
var mainController = collabAppModule.controller("mainController", function($scope) {
    $scope.helloTo = {};
    $scope.helloTo.title = "AngularJS";
});

// profile item controller
var profileItemController = collabAppModule.controller("profileItemController", function($scope) {
    $scope.profile = {};
    $scope.profile.full_name = '';
    $scope.profile.position = '';
    $scope.profile.location = '';
    $scope.profile.email = '';
    $scope.profile.phone = '';
});