angular.module('app')
    .controller('MainCtrl', MainCtrl);

function MainCtrl($scope) {
    var vm = this;

    vm.isLoaded = false;

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            //loadData
        }
    });
}