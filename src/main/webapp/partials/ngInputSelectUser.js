angular.module('app')
    .directive('ngInputSelectUser', ngInputSelectUser);

function ngInputSelectUser() {
    return {
        restrict: 'E',
        scope: {
            ngLabel: '@?',
            ngModel: '=ngModel',
            ngItems: '=ngItems',
            ngItemLabel: '@?',
            ngItemId: '@?',
            ngRequired: '=?',
            ngFunction: '&?',
            ngDisabled: '=?',
            ngSkipzero: '=?',
            ngPost: '=ngPost',
            ngDepartment: '=ngDepartment',
            ngEvent: '@?',
            ngPlaceholder: '@?'
        },
        templateUrl: 'partials/ngInputSelectUser.htm',
        replace: true,
        link: function ($scope, elem, attr, ctrl) {
        },
        controller: ngInputSelectUserCtrl
    }
}

function ngInputSelectUserCtrl($scope) {

    $scope.valid = null;

    if (!$scope.ngRequired) {
        $scope.ngRequired = false;
    }
    if (!$scope.ngSkipzero) {
        $scope.ngSkipzero = false;
    }
    if (!$scope.ngItemLabel) {
        $scope.ngItemLabel = 'name';
    }
    if (!$scope.ngItemId) {
        $scope.ngItemId = 'id';
    }
    if (!$scope.ngEvent) {
        $scope.ngEvent = 'formSubmit';
    }
    $scope.$on($scope.ngEvent, function (e) {
        $scope.$parent.validationResults.push($scope.validate());
    });

    $scope.validate = function (param) {
        if (!$scope.ngModel || $scope.ngModel == null) {
            $scope.ngModel = '';
            $scope.ngPost = $scope.ngDepartment = null;
        }
        if ($scope.ngFunction && param) {
            $scope.ngFunction({arg: $scope.ngModel});
        }

        // REQUIRED
        if ($scope.ngRequired) {
            if (!$scope.ngSkipzero && ($scope.ngModel.length == 0 || $scope.ngModel == 0)) {
                $scope.valid = false;
                $scope.message = "Выберите пункт меню";
                return false;
            } else if ($scope.ngModel.length == 0) {
                $scope.valid = false;
                $scope.message = "Выберите пункт меню";
                return false;
            }
        }
        $scope.valid = true;
        return true;
    };
}