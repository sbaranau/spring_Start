angular.module('app')
    .controller('ResetUserPasswordCtrl', ResetUserPasswordCtrl);

function ResetUserPasswordCtrl($uibModalInstance, UserService, data) {

    var vm = this,
        service = vm.service = UserService;

    vm.ok = function () {
        service.resetPassword(data.userId)
            .then(function (response) {
                $uibModalInstance.close(response.data);
            });
    };

    vm.close = function () {
        $uibModalInstance.dismiss('cancel');
    };

}