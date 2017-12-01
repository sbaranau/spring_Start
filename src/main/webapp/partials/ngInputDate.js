angular.module('app').directive('ngInputDate', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@',
            ngModel : '=ngModel',
            ngRequired : '=?',
            ngDisabled : '=?',
            ngPlaceholder : '@?',
            ngFunction : '&?',
            ngMin : '=?',
            ngMax : '=?',
            ngEvent : '@?',
            ngDisableInvalidDates :'=?'
        },
        templateUrl : 'partials/ngInputDate.htm',
        replace : true,
        link: function($scope, elem, attr, ctrl) {},
        controller : [ '$scope', function($scope) {

            $scope.init = function (){
                if(!angular.isDefined($scope.ngRequired)){
                    $scope.ngRequired = false;
                }
                var min = new Date(1960, 1 , 1);
                var max = new Date();

                if(!angular.isDefined($scope.ngModel)){
                    $scope.ngModel = null;
                }
                if(angular.isDefined($scope.ngMin)){
                    min = $scope.ngMin;
                }
                if(angular.isDefined($scope.ngMax)){
                    max = $scope.ngMax;
                }
                if(!angular.isDefined($scope.ngPlaceholder)){
                    $scope.ngPlaceholder = null;
                }
                $scope.settings = {
                        maxDate : max,
                        minDate : min,
                        format : 'dd.MM.yyyy',
                        open : false,
                        dateDisabled: $scope.ngDisableInvalidDates ? function(date) {
                            var t = (angular.isDate(date.date) ? date.date.getTime() : date.date)/1000/60/60/24;
                            var tmin = (angular.isDate($scope.ngMin) ? $scope.ngMin.getTime() : $scope.ngMin)/1000/60/60/24;
                            var tmax = (angular.isDate($scope.ngMax) ? $scope.ngMax.getTime() : $scope.ngMax)/1000/60/60/24;

                            return (tmin && t<tmin) || (tmax && t>tmax);
                        } : null
                }
                $scope.open = function(popup) {
                    popup.open = true;
                }

                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.$on($scope.ngEvent, function(e){
                    $scope.$parent.validationResults.push($scope.validate());
                })
                $scope.valid = null;
            }

            $scope.validate = function(param) {
                if ((!angular.isDefined($scope.ngModel) || $scope.ngModel == null) && $scope.ngRequired) {
                    $scope.valid = false;
                    $scope.message = "Заполните поле";
                    return false;
                } else {
                    if (angular.isDefined($scope.ngFunction) && angular.isDefined(param)) {
                        $scope.ngFunction({arg: $scope.ngModel})
                    }
                }

                //DATE VALIDITY
                var date = moment($scope.ngModel).format("DD.MM.YYYY");
                var result = moment(date, "DD.MM.YYYY", true).isValid();
                if (!result && (($scope.ngModel != null && !$scope.ngRequired) || $scope.ngRequired)) {
                    $scope.valid = result;
                    $scope.message = "Формат даты должен быть: DD.MM.YYYY";
                    return false;
                }
                if ($scope.ngMin != null && angular.isDate($scope.ngMin)) {
                    $scope.ngMin.setMilliseconds(0);
                    $scope.ngMin.setSeconds(0);
                    $scope.ngMin.setMinutes(0);
                    $scope.ngMin.setHours(0);
                }
                if($scope.ngMin != null && moment($scope.ngModel) < $scope.ngMin){
                        $scope.valid = false;
                        $scope.message = "Дату нельзя выбрать раньше даты: " + moment($scope.ngMin).format("DD.MM.YYYY");
                        return false;
                }

                if( $scope.ngMax != null && moment($scope.ngModel) > $scope.ngMax){
                    $scope.valid = false;
                    $scope.message = "Дату нельзя выбрать позже даты: " + moment($scope.ngMax).format("DD.MM.YYYY");
                    return false;
                }
                $scope.valid = true;
                return true;
            }
            $scope.init();
        } ]
    }
})
