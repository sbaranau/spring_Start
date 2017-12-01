angular.module('app')
    .controller('MainFooterCtrl', MainFooterCtrl);

function MainFooterCtrl($scope) {
    var vm = this;

    vm.isLoaded = false;

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            //loadData
        }
    });
}