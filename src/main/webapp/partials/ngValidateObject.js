angular.module('app').directive('ngValidateObject', function() {
    return {
        restrict : 'A',
        transclude: true,
        scope : {
            ngModel: '=',
            ngFields : '=?',
            ngEvent : '@?',
            ngRequired : '@?'
        },
        templateUrl : 'partials/ngValidateObject.htm',
        link: function($scope, elem, attr, ctrl) {
            $scope.$watch('ngModel', $scope.validateAfterSubmit,true);
        },
        controller : [ '$scope', function($scope) {
            $scope.submitClicked = false;

            $scope.validateAfterSubmit = function(){
                if($scope.submitClicked){
                    $scope.validate();
                }
            }

            $scope.init = function (){
                $scope.message = "Заполните поля";
                $scope.valid = null;
                if(!angular.isDefined($scope.ngRequired)){
                    $scope.ngRequired = false;
                }
                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.$on($scope.ngEvent, function(event, args){
                    if(!angular.isDefined(args)){
                        $scope.ngRequired = false;
                    } else {
                        $scope.ngRequired = args;
                    }
                    $scope.submitClicked = true;
                    $scope.$parent.validationResults.push($scope.validate());
                })
            }

            $scope.validate = function(){
                var found = false;

                for( var i = 0; i< $scope.ngFields.length; i++){
                    var key = $scope.ngFields[i];
                    if( $scope.ngModel[key] != null || $scope.ngModel.length == 0){
                        if($scope.ngModel[key].length != 0 ){
                            found = true;
                        }
                    }
                }
                if(found || $scope.ngRequired){
                    for(var i = 0; i< $scope.ngFields.length; i++){
                        var key = $scope.ngFields[i];
                        if($scope.ngModel[key] == null){
                            $scope.valid = false;
                            return false;
                        }
                        if($scope.ngModel[key].length == 0 ){
                            $scope.valid = false;
                            return false;
                        }
                    }
                }
                $scope.valid = true;
                return true;
            }

            $scope.init();
        } ]
    }
})