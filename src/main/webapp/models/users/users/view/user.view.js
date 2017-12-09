angular.module('app')
    .controller('UserViewCtrl', UserViewCtrl);

function UserViewCtrl($scope, $state, UserService) {

    var vm = this,
        service = vm.service = UserService;

    vm.isLoaded = false;

    $scope.$watch('vm.isLoaded', function (result) {
        if (!result) {
            service.view($state.params.id)
                .then(function (response) {
                    vm.userData = response.data.userData;
                    vm.userCategories = response.data.userCategories;
                    vm.amountTests = response.data.amountTests;
                    vm.isLoaded = true;
                })
        }
    });

    vm.isNotificationExists = function () {
        return vm.isThresholdsExcessExist();
    };


    vm.isEmpty = function (data) {
        return !(data && data.length > 0)
    };

    vm.close = function() {
        $state.go('index.users.list');
    };

    vm.getReport = function () {
        service.getReport(vm.userData.user.id);
    };
}