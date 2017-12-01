// TODO доделать
angular.module('app')
    .component('dropdownSearch', {
        transclude:true,
        bindings: {
            labelWithPlaceholder: "@",
            modelList: "="
        },
        templateUrl: 'partials/dropdownSearch/dropdownSearch.htm',
        controller: DropdownSearchComponentCtrl
    });

function DropdownSearchComponentCtrl() {
    this.$onInit = function () {

    };
}