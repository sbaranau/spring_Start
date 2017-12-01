angular.module('app').directive('ngInputSelect', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngModel : '=ngModel',
            ngItems : '=ngItems',
            ngItemLabel: '@?',
            ngRequired : '=?',
            ngFunction : '&?',
            ngDisabled : '=?',
            ngSkipzero : '=?',
            ngEvent : '@?',
            ngPlaceholder : '@?'
        },
        templateUrl : 'partials/ngInputSelect.htm',
        replace : true,
        link: function($scope, elem, attr, ctrl) {},
        controller : [ '$scope', function($scope) {
            
            $scope.convertToInt = function(value){
            	if(typeof value === 'string') {
            		return value;
            	}
                return parseInt(value, 10);
            }
            
            $scope.init = function (){
                if(!$scope.ngRequired){
                    $scope.ngRequired = false;
                }
                if(!angular.isDefined($scope.ngSkipzero)){
                    $scope.ngSkipzero = false;
                }
                if(!angular.isDefined($scope.ngItemLabel)){
                    $scope.ngItemLabel = 'name';
                }
                $scope.valid = null;
                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.$on($scope.ngEvent, function(e){
                    $scope.$parent.validationResults = $scope.$parent.validationResults ? $scope.$parent.validationResults : [];
                    $scope.$parent.validationResults.push($scope.validate());

                })
            }

            $scope.validate = function(param){
                if(!angular.isDefined($scope.ngModel) || $scope.ngModel == null){
                    $scope.ngModel='';
                }
                if(angular.isDefined($scope.ngFunction) && angular.isDefined(param)){
                    $scope.ngFunction({arg : $scope.ngModel});
                }

                // REQUIRED
                if($scope.ngRequired) {
                    if (!$scope.ngSkipzero && ($scope.ngModel.length == 0 || $scope.ngModel == 0)) {
                        $scope.valid = false;
                        $scope.message = "Выберите пункт меню";
                        return false;
                    } else if($scope.ngModel.length == 0) {
                        $scope.valid = false;
                        $scope.message = "Выберите пункт меню";
                        return false;
                    }
                }
                $scope.valid = true;
                return true;
            }

            $scope.init();
        } ]
    }
})