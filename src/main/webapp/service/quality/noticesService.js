angular.module('app.services')
    .factory('NoticeMaterialsService', NoticeMaterialsService);

function NoticeMaterialsService($http) {
    return {
        list: function () {
            return $http.get('quality/notices/list.json');
        }
    }
}