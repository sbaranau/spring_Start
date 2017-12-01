angular.module('app.services', [ 'ngResource' ])
    .factory('UtilService', UtilService)
    .factory('MainService', MainService)
    .factory('GroupRefService', GroupRefService)
    .factory('ProfileUserService', ProfileUserService)
    ;


function UtilService($resource, $http){
    return {
        post : function(url, params){
            return $http.post(url, params);
        }
    }
}
/**
 * Сервис получения основных данных,
 * которые должны быть доступны всегда,
 * после авторизации пользователя
 * @param $http
 * @returns {{getHeaderData: promise}}
 */
function MainService($http){
    return{
        getHeaderData : function() {
            return $http.get('main/header/header.json');
        }
    }
}

function GroupRefService($http) {
    return {
        group: null,
        list: function() {
            return $http.get('references/group/list.json');
        },
        get: function(id) {
            return $http.get('references/group/' + id + '/group.json');
        },
        getRolesList: function() {
            return $http.get('references/group/roles.json');
        },
        save: function(params) {
            return $http.post('references/group/save.json', params);
        },
        delete: function(id) {
            return $http.post('references/group/' + id + '/delete.json');
        }
    }
}
function ProfileUserService($http) {
    return {
        list: function (){
            return $http.get('profile/users/list.json');
        }
    }
}

