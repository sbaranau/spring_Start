angular.module('app.services')
    .service('modal', modal);

/**
 * Позволяет создать модальное окно
 * @param $uibModal
 * @returns {{create: create}}
 */
function modal($uibModal) {
    var vm = this;

    vm.create = function(template, controllerName) {
        return $uibModal.open({
            animation: true,
            templateUrl: template,
            controller: controllerName,
            controllerAs: 'vm',
            size: "medium"
        }).result
    };

    vm.create = function(template, controllerName, data) {
        return $uibModal.open({
            animation: true,
            templateUrl: template,
            controller: controllerName,
            controllerAs: 'vm',
            size: "medium",
            resolve: {
                data: function () {
                    return data;
                }
            }
        }).result
    };

    vm.delete = function (controllerName, data) {
        return $uibModal.open({
            animation: true,
            templateUrl: 'modal/template.delete.htm',
            controller: controllerName,
            controllerAs: 'vm',
            size: "medium",
            resolve: {
                data: function () {
                    return data;
                }
            }
        }).result
    };

}
