angular.module('app')
    .directive('ngInputMultiselect', ngInputMultiselect);

function ngInputMultiselect() {
    return {
        restrict: 'E',
        scope: {
            ngLabel: '@?',
            ngModel: '=ngModel',
            ngItems: '=ngItems',
            ngRequired: '=?',
            ngFunction: '&?',
            ngEvent: '@?',
            ngMaxSelected: '@?',
            ngDisabled: '=?',
            ngPlaceholder: '@?'
        },
        templateUrl: 'partials/ngInputMultiselect.htm',
        replace: true,
        link: function ($scope, elem, attr, ctrl) {
            $scope.$watch('ngModel', $scope.validateAfterSubmit, true);
        },
        controller: ngInputMultiselectCtrl
    }
}

function ngInputMultiselectCtrl($scope) {

    $scope.submitClicked = false;
    $scope.valid = null;

    if (!$scope.ngRequired) {
        $scope.ngRequired = false;
    }
    if (!$scope.ngMaxSelected) {
        $scope.ngRequired = false;
    }
    if (!$scope.ngEvent) {
        $scope.ngEvent = 'formSubmit';
    }
    $scope.$on($scope.ngEvent, function (e) {
        $scope.submitClicked = true;
        $scope.$parent.validationResults.push($scope.validate());
    });

    $scope.convertToInt = function (value) {
        return parseInt(value, 10);
    };

    $scope.validateAfterSubmit = function () {
        if ($scope.submitClicked) {
            $scope.validate();
        }
    };

    $scope.validate = function () {
        if (!$scope.ngModel || $scope.ngModel == null) {
            $scope.ngModel = [];
        } else {
            if ($scope.ngFunction) {
                $scope.ngFunction({arg: $scope.ngModel})
            }
        }

        if ($scope.ngRequired && !$scope.ngDisabled) {
            if (!$scope.ngModel || $scope.ngModel.length == 0) {
                $scope.valid = false;
                $scope.message = "Заполните поле";
                return $scope.valid;
            }
        }
        $scope.valid = true;
        return $scope.valid;
    };
}