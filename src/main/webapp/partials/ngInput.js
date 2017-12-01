angular.module('app').directive('ngInput', function() {
    return {
        restrict : 'E',
        scope : {
            ngLabel : '@?',
            ngModel : '=ngModel',
            ngType : '@?',
            ngRequired : '=?',
            ngDisabled : '=?',
            ngPlaceholder : '@?',
            ngMin : '@?',
            ngMax : '@?',
            ngMaxlength : '@?',
            ngRemote : '@?',
            ngRemoteMessage : '@?',
            ngEvent : '@?',
            asyncValidatorFn : '&?',
            asyncValidatorErrorMessage : '@?'
        },
        templateUrl : 'partials/ngInput.htm',
        replace : true,
        link: function($scope, elem, attr, ctrl) {},
        controller : [ '$scope', 'UtilService', function($scope, UtilService) {

            $scope.init = function (){
                if(!angular.isDefined($scope.ngRequired)){
                    $scope.ngRequired = false;
                }

                if(!angular.isDefined($scope.ngDisabled)){
                    $scope.ngDisabled = false;
                }
                if(!angular.isDefined($scope.ngPlaceholder)){
                    $scope.ngPlaceholder = null;
                }
                if(!angular.isDefined($scope.ngEvent)){
                    $scope.ngEvent = 'formSubmit';
                }
                $scope.valid = null;
                $scope.$on($scope.ngEvent, function(e){
                    if(!$scope.ngDisabled){
                        $scope.$parent.validationResults.push($scope.validate());
                    }
                })
            }

            $scope.validate = function(){

                if(!angular.isDefined($scope.ngModel) || $scope.ngModel == null){
                    $scope.ngModel="";
                }

                // REQUIRED
                if($scope.ngRequired) {
                    if($scope.ngModel.length == 0){
                        $scope.valid = false;
                        $scope.message = "Заполните поле";
                        return false;
                    }
                }

                // LOGIN VALIDITY
                if ($scope.ngType == 'login') {
                    var result = /^[a-zA-Z]+[a-zA-Z0-9_]+$/.test($scope.ngModel)
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Логин должен начинаться с буквы и может содержать только латинские буквы, цифры, и знаки подчеркивания";
                        return false;
                    }
                }



                // TEXT ONLY
                if($scope.ngType == 'text'){
                    var result = /^[a-zA-Zа-яА-Я]+(-[a-zA-Zа-яА-Я]+)?[a-zA-Zа-яА-Я]+$/.test($scope.ngModel);
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Допустимые символы: а-я, А-Я, a-z, A-Z, '-'";
                        return false;
                    }
                }
                // TEXT AND NUMBERS ONLY //TODO delete after components review
                if($scope.ngType == 'textNumber'){
                    var result = /^[а-яА-ЯёЁa-zA-Z0-9.]+$/.test($scope.ngModel);
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Допустимые символы: а-я, А-Я, a-z, A-Z, '0-9'";
                        return false;
                    }
                }
                // PHONE
                if($scope.ngType == 'phone'){
                    var result = /^\+[0-9]{3}-?[0-9]{2}-?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$/.test($scope.ngModel);
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Номер телефона должен быть в формате: +xxx-yy-zzz-zz-zz или +xxxyyzzzzzzz, где x-код страны, y-код оператора, z-номер телефона";
                        return false;
                    }
                }

                //EMAIL
                if($scope.ngType == 'email'){
                    var result = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/.test($scope.ngModel);
                    if(!result && (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)) {
                        $scope.valid = result;
                        $scope.message = "Введите корректный email-адрес";
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

                if( $scope.ngType == "number" ){
                    var result = isNaN(parseInt($scope.ngModel));
                    if(!result){
                        if($scope.ngMin){
                            var result = $scope.ngModel * 1 < $scope.ngMin * 1? false : true;
                            if(!result /*&& (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)*/) {
                                $scope.valid = result;
                                $scope.message = "Введите число не менее " + $scope.ngMin;
                                return false;
                            }
                        }
                        if($scope.ngMax){
                            var result = $scope.ngModel * 1 > $scope.ngMax * 1? false : true;
                            if(!result /*&& (($scope.ngModel.length > 0 && !$scope.ngRequired) || $scope.ngRequired)*/) {
                                $scope.valid = result;
                                $scope.message = "Введите число не более " + $scope.ngMax;
                                return false;
                            }
                        }
                    } else if ($scope.ngRequired){
                        $scope.valid = false;
                        $scope.message = "Введите число";
                        return false
                    }
                }

                if($scope.asyncValidatorFn){
                    $scope.asyncValidatorFn({arg: $scope.ngModel}).then(function(result) {
                            if ( result===false) {
                                $scope.valid = false;
                                $scope.message = $scope.asyncValidatorErrorMessage||"Ошибка";
                                if ($scope.$parent.validationResults) {
                                    $scope.$parent.validationResults.push(false);
                                }
                            } else {
                                $scope.valid = true;
                                $scope.message = null;
                            }
                        }
                    );
                }


                if(angular.isDefined($scope.ngRemote) ){
                    new UtilService.post($scope.ngRemote, $scope.ngModel).then(function(response){
                        var result = response.data
                        if(!result){
                            $scope.valid = result;
                            $scope.message = $scope.ngRemoteMessage;
                            return false;
                        }
                    })
                }

                $scope.valid = true;
                return true;
            }

            $scope.init();

        } ]
    }
})