function RoutingConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    var DEFAULT_VIEW = '<div ui-view/>';

    // Абстрактные вьюхи
    var INDEX = 'index';
    $stateProvider
        .state('index.default', {
            url: '/',
            template: DEFAULT_VIEW
        })
        .state(INDEX, {
            abstract: true,
            views: {
                'header': {
                    templateUrl: 'modal/main/header/header.htm',
                    controller: 'MainHeaderCtrl',
                    controllerAs: 'vm'
                },
                'sidebar': {
                    templateUrl: 'modal/main/sidebar/sidebar.htm',
                    controller: 'MainSidebarCtrl',
                    controllerAs: 'vm'
                },
                'footer': {
                    templateUrl: 'modal/main/footer/footer.htm',
                    controller: 'MainFooterCtrl',
                    controllerAs: 'vm'
                },
                'main': {
                    templateUrl: 'modal/main/main.htm',
                    controller: 'MainCtrl',
                    controllerAs: 'vm'
                }
            }
        });

  // Настройки
    var SETTINGS = 'index.settings';
    var SETTINGS_COMMON = 'index.settings.common';
    var SETTINGS_PASSWORD = 'index.settings.password';
    var SETTINGS_MANAGE_USERS = 'index.settings.manageUsers';
    var SETTINGS_MANAGE_USERS_LIST = 'index.settings.manageUsers.list';
    $stateProvider
        .state(SETTINGS, {
            url: '/settings',
            template: DEFAULT_VIEW,
            redirectTo: SETTINGS_COMMON
        })
        .state(SETTINGS_COMMON, {
            url: '/common',
            views: {
                '': {
                    templateUrl: 'models/settings/commonSettings.htm',
                    controller: 'SettingsCommonCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'SETTINGS');
                }
            }
        })
        .state(SETTINGS_PASSWORD, {
            url:'/password',
            views:{
                '':{
                    templateUrl: 'models/settings/changePassword.htm',
                    controller: 'ChangePasswordCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(SETTINGS_MANAGE_USERS, {
            url: '/manage_users',
            template: DEFAULT_VIEW,
            redirectTo: SETTINGS_MANAGE_USERS_LIST
        })
        .state(SETTINGS_MANAGE_USERS_LIST, {
            url:'/list',
            views:{
                '':{
                    templateUrl: 'models/settings/manageUsers/manageUsers.htm',
                    controller: 'SettingsManageUsersCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'ADMIN');
                }
            }
        });


    /**
     * Имеет ли доступ к стейту текущий пользователь
     * @param $q
     * @param SessionService сервис сессии
     * @param roleName наименование роли, необходимой для доступа к стейту
     */
    function hasAccess($q, SessionService, roleName) {
        var roles = SessionService.roles;
        var currentUser = SessionService.currentUser;
        if (!roles || !currentUser) {
            loadRoles($q, SessionService)
                .then(function (response) {
                    return checkRole($q, roleName, SessionService.roles, SessionService.currentUser)
                });
        } else {
            return checkRole($q, roleName, roles, SessionService.currentUser)
        }
    }

    /**
     * Проверяет, есть ли в списке ролей пользователя, нужная роль для доступа
     * или id текущего пользователя равно 1, что значит что это админ
     * @param $q
     * @param roleName наименование роли для доступа
     * @param roles список ролей пользователя
     * @param currentUser текущий пользователь
     * @returns {*|Array|Promise}
     */
    function checkRole($q, roleName, roles, currentUser) {
        if (!hasRole(roleName, roles) && currentUser.userId !== 1) {
            return $q.reject("Denied");
        }
    }

    /**
     * Загружает список ролей
     * @param $q
     * @param SessionService сервис сессии
     * @returns {Promise}
     */
    function loadRoles($q, SessionService) {
        var deferred = $q.defer();
        $q.all([
            SessionService.isAuth(),
            SessionService.getCurrentUser()
        ]).then(function (values) {
            SessionService.roles = values[0].data;
            SessionService.currentUser = values[1].data;
            deferred.resolve();
        });
        return deferred.promise;
    }
}