angular.module('app.services')
    .factory('SessionService', SessionService);

/**
 * Сервис сессии и ролей
 */
function SessionService($http) {
    var service =  {
        roles: null,
        currentUser: null,
        isAuth: function () {
            return $http.get('settings/is_auth.json');
        },
        hasAccess: function (roles) {
            if (!roles) {
                return false;
            }
            if (isAdmin(this.roles) || (this.currentUser && this.currentUser.userId === 1)) {
                return true;
            } else {
                for (var role in roles) {
                    if (this.roles && this.roles.includes(roles[role])) {
                        return true;
                    }
                }
            }
            return false;
        },
        getCurrentUser: function () {
            return $http.get('settings/current_user.json');
        }
    };

    return service;
}
