var app = angular.module('dashApp', ["ngRoute"]);

app.controller('MainDashBoardController', function($scope,$location,$window) {
	
  $scope.valueReveal = false;
  $scope.openNav= false;
  $scope.optionSelected = 0;
  
  //openBroker1
  $scope.openBroker1 = function(){
	  $scope.openNav= true;
	  
	  document.getElementById("mySidenav");
  };
  $scope.openSpan1 = function(){
	  $scope.valueReveal = true;
	  $scope.optionSelected = 1;
	  document.getElementById("navValue");
	 
  };
  $scope.openSpan2 = function(){
	  $scope.valueReveal = true;
	  $scope.optionSelected = 2;
	  document.getElementById("navValue");
	 
  };
  $scope.openSpan3 = function(){
	  $scope.valueReveal = true;
	  $scope.optionSelected = 3;
	  document.getElementById("navValue");
	 
  };
  $scope.openSpan4 = function(){
	  $scope.valueReveal = true;
	  $scope.optionSelected = 4;
	  document.getElementById("navValue");
	 
  };
  
 

  $scope.links = [ 
  {"InhibitPut":false,"Description":"","Sharable":true,"OpenInputCount":1,"InhibitGet":true,"TriggerControl":false,"MaximumMessageLength":4194304,"OpenOutputCount":0,"CurrentDepth":0,"MaximumDepth":5000}
];
  
  
  $scope.closeNav = function() {
	  $scope.valueReveal = false;
    document.getElementById("mySidenav").style.width = "0";
  }
  
  $scope.myJson = {"InhibitPut":false,"Description":"","Sharable":true,"OpenInputCount":1,"InhibitGet":true,"TriggerControl":false,"MaximumMessageLength":4194304,"OpenOutputCount":0,"CurrentDepth":0,"MaximumDepth":5000};
  
  $scope.myJson2 = {"TriggerControl":false,"MaximumMessageLength":4194304,"OpenOutputCount":0,"MaximumDepth":5000};
  //shyam
  
  $scope.fName = '';
$scope.lName = '';
$scope.passw1 = '';
$scope.passw2 = '';

$scope.edit = true;
$scope.error = false;
$scope.incomplete = false; 
$scope.hideform = true; 
$scope.editUser = function(id) {
  $scope.hideform = false;
  if (id == 'new') {
    $scope.edit = true;
    $scope.incomplete = true;
    $scope.fName = '';
    $scope.lName = '';
    } else {
    $scope.edit = false;
    $scope.fName = $scope.users[id-1].fName;
    $scope.lName = $scope.users[id-1].lName; 
  }
};

$scope.$watch('passw1',function() {$scope.test();});
$scope.$watch('passw2',function() {$scope.test();});
$scope.$watch('fName', function() {$scope.test();});
$scope.$watch('lName', function() {$scope.test();});

$scope.test = function() {
  if ($scope.passw1 !== $scope.passw2) {
    $scope.error = true;
    } else {
    $scope.error = false;
  }
  $scope.incomplete = false;
  if ($scope.edit && (!$scope.fName.length ||
  !$scope.lName.length ||
  !$scope.passw1.length || !$scope.passw2.length)) {
     $scope.incomplete = true;
  }
};
  //shyam
  
});