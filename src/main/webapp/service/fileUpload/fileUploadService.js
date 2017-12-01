angular.module('app.services')
    .service('FileUploadService', FileUploadService);

function FileUploadService($http, $q) {

    const fileSize = 2097152;
    var vm = this;

    /**
     * проверяет есть ли файл,
     * если есть тогда
     * соответствует ли типу application/vnd.oasis.opendocument.text и если соответствует
     * тогда проверяем расширение файла .odt или нет
     *
     * @param template
     *
     * @returns boolean значение
     */
    vm.isTemplateIsOdt = function (template) {
        return template && template.type === 'application/vnd.oasis.opendocument.text' && (template.name && template.name.split('\.').pop() === 'odt');
    };

    /**
     * проверяет есть ли файл,
     * если есть тогда
     * соответствует ли одному из типов image/jpeg или image/png или image/gif
     * и если соответствует
     * тогда проверяем расширение файла, .jpg .png .gif или нет
     *
     * @param template
     *
     * @returns boolean значение
     */
    vm.isFileIsTemplateImage = function (template) {
        return   template &&
                (template.type === 'image/jpeg'|| template.type === 'image/png' || template.type === 'image/gif') && template.name
                && (template.name.split('\.').pop() === 'jpg' || template.name.split('\.').pop() === 'png' || template.name.split('\.').pop() === 'gif'
                || template.name.split('\.').pop() === 'jpeg');
    };

    vm.isFileTemplateSize = function (template) {
        return template.size > fileSize;
    };

    vm.uploadFile = function (file) {
        var deferred = $q.defer();
        var formData = new FormData();
        formData.append('file', file);
        $http.post('resources/upload_file.json', formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (res) {
            deferred.resolve(res.data);
        });
        return deferred.promise;
    };

    vm.deleteFile = function (fileId) {
        return $http.delete('resources/delete_file.json?fileId=' + fileId)
    };
}
