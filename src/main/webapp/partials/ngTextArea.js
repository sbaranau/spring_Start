angular.module('app').directive('ngTextArea', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngPlaceholder : '@?',
            ngModel : '=ngModel',
            ngRequired : '@?',
            ngMin : '@?',
            ngMax : '@?',
            ngEvent : '@?',
            ngDisabled : '=?'
        },
        templateUrl : 'partials/ngTextArea.htm',
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
                if(!angular.isDefined($scope.ngPlaceholder)){
                    $scope.ngPlaceholder = null;
                }
                $scope.$on($scope.ngEvent, function(e){
                    $scope.$parent.validationResults.push($scope.validate());
                })
            };

            $scope.validate = function(){
                if(!angular.isDefined($scope.ngModel) || $scope.ngModel == null){
                    $scope.ngModel="";
                }

                // REQUIRED
                if($scope.ngRequired) {
                    if($scope.ngModel.length == 0){
                        $scope.valid = false;
                        $scope.message = "Заполните поле"
                        return false; 
                    }
                }

                //INPUT STRING LENGTH
                if($scope.ngMin && $scope.ngType != "number"){
                    var result = $scope.ngModel.length < $scope.ngMin ? false : true;
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Длина значения поля должна быть не менее чем " + $scope.ngMin + " символов";
                        return false;
                    }
                }
                if($scope.ngMax && $scope.ngType != "number"){
                    var result = $scope.ngModel.length > $scope.ngMax ? false : true;
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Длина значения поля должна быть не более чем " + $scope.ngMax + " символов";
                        return false;
                    }
                }

                $scope.valid = true;
                return true;
            };

            $scope.init();
        } ]
    }
});