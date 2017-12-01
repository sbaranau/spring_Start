angular.module('app')
    .controller('MainHeaderCtrl', MainHeaderCtrl);

function MainHeaderCtrl($rootScope, $scope, MainService) {
    var vm = this,
        service = vm.service = MainService;

    vm.isLoaded = false;
    vm.data = null;

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            service.getHeaderData()
                .then(function (response) {
                    vm.data = response.data;
                    vm.isLoaded = true;
                });
        }
    });

    vm.menu = [
        {
            name:'header.tasks',
            link:$rootScope.baseUrl + '/app#/profile/task/list'
        },
        {
            name:'header.documents',
            link:$rootScope.baseUrl + '/app#/profile/documents/list'
        },
        {
            name:'header.trainings',
            link:$rootScope.baseUrl + '/app#/profile/trainings/list'
        },
        {
            name:'header.users',
            link:$rootScope.baseUrl + '/app#/profile/users/list'
        },
        {
            name:'header.equipments',
            link:$rootScope.baseUrl + '/app#/profile/equipments/list'
        },
        {
            name:'header.doclist',
            link:$rootScope.baseUrl + '/app#/profile/doclist/list'
        }
    ]
}