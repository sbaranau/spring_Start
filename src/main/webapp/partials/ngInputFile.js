angular.module('app')
    .directive('ngInputFile', function() {
        return {
            restrict : 'E',
            scope : {
                ngLabel : '@?',
                ngModel : '=ngModel',
                ngFilename : '=?',
                ngRequired : '=?',
                ngDisabled : '=?',
                ngEvent : '@?',
                ngAccept : '@?',
                ngAutoUploadData : '=',
                ngRemovable : '=?',
                ngShowMaxSize : '=?'
            },
            templateUrl : 'partials/ngInputFile.htm',
            replace : true,
            controller : ngInputFileCtrl
        }
    });

function ngInputFileCtrl($scope, SettingsService, FileUploadService) {

    $scope.valid = null;

    if((!$scope.ngFilename || $scope.ngFilename === null) && ($scope.ngModel && $scope.ngModel !== null)){
        $scope.ngFilename = $scope.ngModel.name;
    }

    if(!$scope.ngRequired){
        $scope.ngRequired = false;
    }

    if(!$scope.ngDisabled){
        $scope.ngDisabled = false;
    }

    if(!$scope.ngEvent){
        $scope.ngEvent = 'formSubmit';
    }

    if ($scope.ngShowMaxSize) {
        SettingsService.getMaxSizeString()
            .then(function (res) {
                $scope.maxSizeString = res;
            });
        SettingsService.getMaxSize()
            .then(function (res) {
                $scope.maxSize = res;
            });
    }

    $scope.$on($scope.ngEvent, function(e){
        if(!$scope.ngDisabled){
            $scope.$parent.validationResults.push($scope.validate());
        }
    });

    $scope.$watch('ngModel', function(){
        if(!$scope.isFilenameUsed && !!$scope.ngFilename) {
            $scope.modelName = $scope.ngFilename;
            $scope.isFilenameUsed = true;
        } else {
            if(!!$scope.ngModel) {
                $scope.modelName = $scope.ngModel.name;
                $scope.ngFilename = $scope.ngModel.name;
                $scope.validate();
            }
        }
        if ($scope.ngAutoUploadData && $scope.ngModel && $scope.ngModel.size) {
            var res = [];
            validateMaxSize(res);
            validateFileType(res);
            if (res.indexOf(false) === -1) {
                FileUploadService.uploadFile($scope.ngModel)
                    .then(function (res) {
                        $scope.ngAutoUploadData = res;
                    })
            }
        }
    });

    $scope.validate = function () {
        var result = [];
        validateRequired(result);
        validateMaxSize(result);
        validateFileType(result);
        return result.indexOf(false) === -1;
    };

    $scope.removeFile = function() {
        $scope.ngFilename = null;
        $scope.modelName = null;
        $scope.ngModel = null;
        $scope.valid = null;
    };

    function validateMaxSize(result) {
        if ($scope.ngShowMaxSize && $scope.ngModel && $scope.ngModel.size) {
            if ($scope.ngModel.size <= ($scope.maxSize * 1024 * 1000)) {
                result.push(true);
            } else {
                $scope.message = "Файл превышает допустимый размер файла";
                $scope.valid = false;
                result.push(false);
            }
        }
    }

    function validateFileType(result) {
        if ($scope.ngAccept && $scope.ngModel && $scope.ngModel.type) {
            if ($scope.ngAccept === $scope.ngModel.type) {
                result.push(true);
            } else {
                $scope.message = "Не верное расширение файла";
                $scope.valid = false;
                result.push(false);
            }
        }
    }

    function validateRequired(result) {
        if($scope.ngRequired) {
            if(!$scope.modelName) {
                $scope.valid = result;
                $scope.message = "Выберите файл";
                result.push(false);
            }
        }
    }
}