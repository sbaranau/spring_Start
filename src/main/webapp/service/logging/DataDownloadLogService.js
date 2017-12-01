angular.module('app.services')
    .service('dataDownloadLogService', dataDownloadLogService)
    .factory('dataDownloadLogApi', dataDownloadLogApi);

function dataDownloadLogService(dataDownloadLogApi) {

    var vm = this,
        vm = vm.api = dataDownloadLogApi;
}

function dataDownloadLogApi($http) {
    return {
        list: function () {
            return $http.get('logging/data_download_log/data.json');
        }
    }
}
