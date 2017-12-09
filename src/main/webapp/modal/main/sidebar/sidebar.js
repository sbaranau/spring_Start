angular.module('app')
    .controller('MainSidebarCtrl', MainSidebarCtrl);

function MainSidebarCtrl($rootScope, $scope, $location, SessionService) {
    var vm = this,
        session = vm.session = SessionService;

    vm.isLoaded = false;
    vm.selectedLink = null;
    vm.selectedLinkMenu = null;

    // TODO когда избавимся от jsp убрать это всё
    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            var arr;
            if ($location.path() === '/') {
                var selectedUrl = $location.absUrl();
                var beforeExtensionStr = selectedUrl.substr(0, selectedUrl.indexOf('.html'));
                arr = beforeExtensionStr.split('/');
                vm.selectedLinkMenu = arr[arr.length - 1];
            } else {
                arr = $location.path().split('/');
                vm.selectedLinkMenu = arr[arr.length - 1];
                if (vm.selectedLinkMenu === 'list') {
                    switch (arr[arr.length - 2]) {
                        case 'commonSettings':
                            vm.selectedLinkMenu = 'commonSettings';
                            break;
                            //TODO add menu if not shown
                        case 'manageUsers':
                            vm.selectedLinkMenu = 'manageUsers';
                            break;
                    }
                }

            }

            selectByTag(vm.selectedLinkMenu);
            vm.isLoaded = true;
        }
    });

    vm.select = function (data) {
        var index = vm.menu.indexOf(data);
        if (index != -1) {
            vm.menu[index].title.linked = !vm.menu[index].title.linked;
        }
    };

    vm.selectLink = function (item, value) {
        if (vm.selectedLink) {
            selectByTag(vm.selectedLink);
        }
        var itemIndex = vm.menu.indexOf(item);
        var itemLinks = vm.menu[itemIndex].links;
        for(var link in itemLinks) {
            if (itemLinks[link] === value) {
                vm.selectedLink = link;
                selectByTag(link);
            }
        }
        if (vm.selectedLinkMenu) {
            selectByTag(vm.selectedLinkMenu);
        }
        vm.selectedLinkMenu = null;
    };

    vm.hasAccess = function (roles) {
        return session.hasAccess(roles) || (session.currentUser && session.currentUser.userId === 1);
    };

    function selectByTag(tag) {
        vm.menu.forEach(function(menuItem) {
            if (getKeysOfObject(menuItem.links).includes(tag)) {
                menuItem.title.linked = true;
                menuItem.links[tag].linked = !menuItem.links[tag].linked;
            }
        })
    }

    function getKeysOfObject(object) {
        var keys = [];
        for(var key in object) {
            keys.push(key);
        }
        return keys;
    }

    vm.menu = [
        {
            roles:['SETTINGS', 'ADMIN'],
            title: {
                linked:false,
                tag:'settings',
                name:'sidebar.settings.title'
            },
            links: {
                commonSettings: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/settings/common',
                    name:'sidebar.settings.common',
                    roles:['SETTINGS']
                },
                manageUsers: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/settings/manage_users/list',
                    name:'sidebar.settings.manageUsers',
                    roles:['ADMIN']
                }

            }
        }

    ];
}