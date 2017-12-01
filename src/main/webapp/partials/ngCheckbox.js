angular.module('app')
    .directive('ngCheckbox', ngCheckboxDirective);

function ngCheckboxDirective() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngModel : '=ngModel',
            ngRequired : '=?',
            ngEvent : '@?',
            ngDisabled :'=?',
            ngChecked :'=?'
        },
        templateUrl : 'partials/ngCheckbox.htm',
        replace : true,
        link: function($scope, elem, attr, ctrl) {},
        controller : NgCheckboxController
    }
}

function NgCheckboxController($scope) {

    $scope.valid = null;

    console.log($scope.ngRequired);
    if(!$scope.ngRequired){
        $scope.ngRequired = false;
    }
    if(!$scope.ngEvent){
        $scope.ngEvent = 'formSubmit';
    }

    $scope.$on($scope.ngEvent , function(e){
        $scope.$parent.validationResults.push($scope.validate());
    });

    $scope.toggle = function(){
        if ($scope.ngDisabled) {
            return;
        }
        if($scope.ngModel){
            $scope.ngModel = false;
        } else {
            $scope.ngModel = true;
        }
        if (angular.isDefined($scope.ngChecked)) {
            $scope.ngModel = $scope.ngChecked && $scope.ngModel;
        }
        if($scope.ngRequired){
            $scope.validate();
        }
    };

    $scope.validate = function(){
        if(!$scope.ngModel || $scope.ngModel == null){
            $scope.ngModel = false;
        }

        // REQUIRED
        if($scope.ngRequired) {
            if($scope.ngModel.length == 0){
                $scope.valid = false;
                $scope.message = "Заполните поле";
                return false;
            }
        }

        $scope.valid = true;
        return true;
    }
}