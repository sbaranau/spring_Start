angular.module('app')
    .component('saveButton', {
        bindings: {
            remoteMethod: "&",
            translateName: "@"
        },
        templateUrl: 'partials/saveButton/saveButton.htm',
        controller: SaveButtonComponentCtrl
    });

function SaveButtonComponentCtrl($q) {
    var vm = this;

    this.$onInit = function () {
        this.disabled = false;
    };

    this.save = function () {
        this.disabled = true;
        $q.when(this.remoteMethod())
            .then(function () {
                vm.disabled = false;
            }, function () {
                vm.disabled = false;
            });
    }
}