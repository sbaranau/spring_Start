angular.module('app')
    .controller('UsersCtrl', UsersCtrl);

function UsersCtrl($scope, UserService) {

    var vm = this,
        service = vm.service = UserService;

    vm.isLoaded = false;

    vm.reestablish = function (userId) {
        return service.fire({userId:userId, fired:false})
            .then(function (response) {
                vm.isLoaded = false;
                $scope.popoverFire.open = false;
            });
    };

    vm.fire = function (userId) {
        return service.fire({userId:userId, fired:true})
            .then(function (response) {
                vm.isLoaded = false;
                $scope.popoverReestablish.open = false;
            });
    };

    vm.withFired = function () {
        vm.isFired = !vm.isFired;
        vm.isLoaded = false;
    };

    vm.loadUsers = function(isDone) {
        if (!isDone) {
            vm.isLoaded = false;
            var filter = {withFired : vm.isFired, searchString : vm.searchString, offset: vm.offset};
            service.list(filter)
                .then(function (response) {
                    vm.userList = response.data;
                    vm.isLoaded = true;
                });
        }
    };


    $scope.popoverReestablish = {
        userId: null,
        open: false,
        templateUrl: "modal/users/users.reestablish.htm"
    };

    $scope.togglePopover = function (popover) {
        if (popover.open) {
            popover.open = false;
            popover.userId = null;
        } else {
            popover.open = true;
        }
    };

    $scope.$watch('vm.isLoaded', vm.loadUsers);
    vm.generatingNewDate = new Date().getTime();
}
