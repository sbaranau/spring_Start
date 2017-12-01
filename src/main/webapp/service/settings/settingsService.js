angular.module('app.services')
    .factory('SettingsService', SettingsService);

/**
 * Сервис настроек
 */
function SettingsService($http, $filter, $q) {
    return  {
        getCommonSettings: function () {
            return $http.get('settings/common/list.json');
        },
        saveCommonSettings: function (params) {
            return $http.post('settings/common/save.json', params);
        },
        isAuth: function () {
            return $http.get('settings/is_auth.json');
        },
        /**Получить допустимый размер загружаемого файла с единицей измерения на конце (# 15 Мбайт)*/
        getMaxSizeString: function () {
            var deferred = $q.defer();
            this.getMaxSize()
                .then(function (res) {
                    deferred.resolve(res + ' ' + $filter('translate')('modal.training.edit.Mb'));
                });
            return deferred.promise;
        },
        /**Получить допустимый размер загружаемого файла*/
        getMaxSize: function () {
            var deferred = $q.defer();
            this.getCommonSettings()
                .then(function (res) {
                    deferred.resolve($filter('filter')(res.data, {key: 'common.file.maxSize'})[0].value);
                });
            return deferred.promise;
        }
    };
}