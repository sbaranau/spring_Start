angular.module('app')
    .controller('SettingsCommonCtrl', SettingsCommonCtrl);

function SettingsCommonCtrl($scope, SettingsService, $state) {

    var vm = this,
        service = vm.service = SettingsService;

    vm.isLoaded = false;

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            service.getCommonSettings()
                .then(function (response) {
                    vm.items = response.data;
                    vm.isLoaded = true;
                })
        }
    });

    vm.save = function () {
        service.saveCommonSettings(vm.items)
            .then(function (response) {
                $state.reload('index');
            });
    };

}