angular.module('app').directive('ngInputPdfFile', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngModel : '=ngModel',
            ngDisabled : '=?',
            ngFilename : '=?',
            ngRequired : '=?',
            ngEvent : '@?'
        },
        templateUrl : 'partials/ngInputPdfFile.htm',
        replace : true,
        controller : [ '$scope', function($scope) {
            $scope.init = function (){
                if((!angular.isDefined($scope.ngFilename)||$scope.ngFilename == null) && (angular.isDefined($scope.ngModel) && $scope.ngModel != null)){
                    $scope.ngFilename = $scope.ngModel.name;
                }
                if(!$scope.ngRequired){
                    $scope.ngRequired = false;
                }
                if(!angular.isDefined($scope.ngDisabled)){
                    $scope.ngDisabled = false;
                }
                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.$on($scope.ngEvent, function(e){
                    if(!$scope.ngDisabled){
                        $scope.$parent.validationResults.push($scope.validate());
                    }
                })
                $scope.valid = null;
            };
            $scope.$watch('ngModel', function(){
                if(angular.isDefined($scope.ngFilename) && $scope.ngFilename != null){
                    $scope.modelName = $scope.ngFilename;
                    $scope.ngFilename = null;
                } else {
                    if(angular.isDefined($scope.ngModel) && $scope.ngModel != null){
                        $scope.modelName = $scope.ngModel.name;
                        $scope.validate();
                    }
                }
            })
            $scope.validate = function (){
                if($scope.ngRequired) {
                    var nameResult = $scope.modelName == null ? false : true; 
                    
                    if($scope.modelName != null){                    	
                    	 var fileName = $scope.modelName;
                         var index = fileName.lastIndexOf(".");
                         var ext = fileName.substring(index, fileName.length);
                         var extResult = ext.toLowerCase() == '.pdf'? true:false;
                    }
                         
                    var result = nameResult && extResult;
                    if(!result){
                        $scope.valid = result;
                        $scope.message = "Выберите PDF файл.";
                        return false;
                    }
                }
                $scope.valid = null;
                return true;
            }
            $scope.init();
        } ]
    }
})