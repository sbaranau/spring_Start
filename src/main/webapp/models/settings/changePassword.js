angular.module('app')
    .controller('ChangePasswordCtrl', ChangePasswordCtrl);
function ChangePasswordCtrl(ChangePasswordService,  $state) {
    var
    vm =this,
    api = ChangePasswordService;

    vm.saveChange = function () {
        api.savePassword({password: vm.password, newPassword: vm.newPassword, confirm: vm.confirm})
            .then(function(response){ $state.go('index.profile.task') })
    };
}
