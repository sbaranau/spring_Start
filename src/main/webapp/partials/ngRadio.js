angular.module('app').directive('ngRadio', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngModel : '=ngModel',
            ngItems : '=ngItems',
            ngRequired : '@?',
            ngEvent : '@?'
        },
        templateUrl : 'partials/ngRadio.htm',
        replace : true,
        link: function($scope, elem, attr, ctrl) {},
        controller : [ '$scope', function($scope) {

            $scope.init = function (){
                if(!angular.isDefined($scope.ngRequired)){
                    $scope.ngRequired = false;
                }
                $scope.valid = null;
                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.$on($scope.ngEvent, function(e){
                    $scope.$parent.validationResults.push($scope.validate());
                })
            }

            $scope.toggle = function(id){
                $scope.ngModel = id;
                if($scope.ngRequired){
                    $scope.validate();
                }
            }

            $scope.validate = function(){
                if(!angular.isDefined($scope.ngModel) || $scope.ngModel == null){
                    $scope.ngModel="";
                }

                // REQUIRED
                if($scope.ngRequired) {
                    
                    if($scope.ngModel.length == 0 || $scope.ngModel == 0){
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