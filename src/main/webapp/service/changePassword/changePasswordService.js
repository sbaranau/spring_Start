angular.module('app.services')
    .factory('ChangePasswordService', ChangePasswordService);
function ChangePasswordService($http) {
    return {
        savePassword : function(data) {
            return $http.post('settings/password.json', data)
                .then(function (response) {
                    console.log(response);
                })
            }
    }
 };