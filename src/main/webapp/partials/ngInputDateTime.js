angular.module('app')
    .directive('ngInputDateTime', ngInputDateTime);

function ngInputDateTime() {
    return {
        restrict: 'E',
        scope: {
            ngLabel: '@',
            ngModel: '=ngModel',
            ngRequired: '=?',
            ngDisabled: '=?',
            ngPlaceholder: '@?',
            ngFunction: '&?',
            ngEvent: '@?'
        },
        templateUrl: 'partials/ngInputDateTime.htm',
        replace: true,
        link: function ($scope, elem, attr, ctrl) {},
        controller: ngInputDateTimeCtrl
    }
}


function ngInputDateTimeCtrl($scope) {

    $scope.format = 'dd.MM.yyyy HH:mm';
    $scope.isOpen = false;
    $scope.valid = null;

    $scope.open = function () {
        $scope.isOpen = true;
    };

    if (!$scope.ngRequired) {
        $scope.ngRequired = false;
    }
    if (!$scope.ngModel) {
        $scope.ngModel = null;
    }
    if (!$scope.ngPlaceholder) {
        $scope.ngPlaceholder = null;
    }
    if (!$scope.ngEvent) {
        $scope.ngEvent = 'formSubmit';
    }
    $scope.$on($scope.ngEvent, function (e) {
        $scope.$parent.validationResults.push($scope.validate());
    });

    $scope.validate = function (param) {
        if (!$scope.ngModel && $scope.ngRequired) {
            $scope.valid = false;
            $scope.message = "Заполните поле";
            return false;
        } else {
            if ($scope.ngFunction && param) {
                $scope.ngFunction({arg: $scope.ngModel})
            }
        }

        $scope.valid = true;
        return true;
    }
}