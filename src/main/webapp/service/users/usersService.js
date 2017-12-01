angular.module('app.services')
    .factory('UserService', UserService);

function UserService($http, $window) {
    return {
        user: null,
        list : function(params) {
            return $http.post('settings/users/list.json', params);
        },
        getAllUsers : function() {
            return $http.post('settings/users/all_users.json');
        },
        add : function() {
            return $http.post('settings/users/add.json');
        },
        edit : function(params) {
            return $http.post('settings/users/' + params + '/edit.json');
        },
        view : function(params) {
            return $http.get('settings/users/' + params + '/view.json');
        },
        getPosts : function (params){
            return $http.post('settings/users/department_posts.json', params);
        },
        getUsersByPostIdAndDate: function (params) {
            return $http.get('settings/users/post_users.html?postId=' + params.postId + '&date=' + params.date);
        },
        getPhoto : function(params){
            return $http.post('resources.html', params);
        },
        getBytes : function(params){
            return $http.post('resources_bytes.html', params);
        },
        getResponse : function(params){
            return $http.get('resources.html?id=' + params);
        },
        fire : function(params){
            return $http.post('settings/users/' + params.userId + '/fire.json?fired=' + params.fired);
        },
        resetPassword: function (userId) {
            return $http.post('settings/users/' + userId + '/reset.json');
        },
        changeStatus: function (userId, statusId) {
            return $http.post('settings/users/' + userId + '/change_status.json', statusId);
        },
        getReport: function (userId) {
            return $window.open("settings/users/report.json?userId=" + userId, '_blank');
        },
        getUserData: function (userId) {
            return $http.get("settings/users/"+userId+"/user.json");
        },
        update: function (userId, userForm) {
            return $http.post("settings/users/"+userId+"/update.json", userForm, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
        }
    }
}
