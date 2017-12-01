angular.module('app')
    .controller('SettingsManageUsersCtrl', SettingsManageUsersCtrl);

function SettingsManageUsersCtrl($scope, UserService, PaginationXFactory, modal) {

    var vm = this,
        service = vm.service = UserService,
        pgX = vm.pgX = PaginationXFactory;

    vm.isLoaded = false;

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            service.getAllUsers()
                .then(function(response) {
                    vm.userList = response.data;
                    vm.usersOnDisplay = angular.copy(vm.userList);
                    if (pgX.pagination.load) {
                        pgX.pagination.load(angular.copy(vm.userList));
                    }
                    vm.isLoaded = true;
                })
        }
    });

    vm.edit = function (user) {
        modal.create('modal/users/userModal.edit.htm', 'UserEditModalPasswordCtrl', user)
            .then(function (response) {
                vm.isLoaded = false;
            });
    };

    vm.resetPassword = function (user) {
        modal.create('modals/users/resetPassword.htm', 'ResetUserPasswordCtrl', user).then();
    };

    pgX.columns = [
        {title:"settings.manageUsers.name",dataKey:"fullName",sortKey:"fullName",width:"30%"},
        {title:"settings.manageUsers.post",dataKey:"positionName",sortKey:"positionName",width:"25%"},
        {title:"settings.manageUsers.department",dataKey:"departmentName",sortKey:"departmentName",width:"25%"}
    ];
    pgX.actionColumnOptions.colWidth = "20%";
    pgX.actionColumnOptions.actions = [
        {name:"Edit", type:"Link", htmlAttrbs:'class="edit-icon"'},
        {name:"Reset", type:"Button", label:"settings.manageUsers.resetPassword", htmlAttrbs:'style="margin:-25px 0 0 25px;"', conditions:{field:"login", type:"ngDisabled", action:"exist"}}
    ];
    pgX.actionHandlers = {
        "Edit":vm.edit,
        "Reset":vm.resetPassword
    };

}