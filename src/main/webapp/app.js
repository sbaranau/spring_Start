var app = angular.module('app', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'ui.router',
    'ngSanitize',
    'ui.bootstrap',
    'ui.bootstrap.datetimepicker',
    'app.services',
    'ngFileUpload',
    'angularjs-dropdown-multiselect',
    'datatables',
    'chart.js',
    'ngTable',
    'ui.select',
    'simplePagination',
    'angular.chosen',
    'pascalprecht.translate',
    'ui.tree',
    'cgBusy',
    'paginationX'
])
    .config(RequestConfig)
    .config(ChartConfig)
    .config(RoutingConfig)
    .config(TranslateConfig)
    .run(RunConfig);

function RunConfig($rootScope, $state, $location, $translate, SessionService, $q) {

    $rootScope.oldPageState = function () {
        return $location.path() === '/';
    };

    $rootScope.openUrl = function (url) {
        window.open(url, '_self');
    };

    $rootScope.setBaseUrl = function (url) {
        $rootScope.baseUrl = url;
    };

    /* Reset error when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
    });

    $rootScope.activePromises = [];

    $rootScope.initialized = true;

    // use translate dictionary
    $translate.use('ru');

    /**
     * Листенер для редиректа из одного стейта в другое redirectTo
     */
    $rootScope.$on('$stateChangeStart', function (evt, to, params) {
        if (to.redirectTo) {
            evt.preventDefault();
            $state.go(to.redirectTo, params, {location: 'replace'});
        }
    });
    /**
     * Листенер ошибок если доступа к стейту нет
     */
    $rootScope.$on('$stateChangeError', function(e, toState, toParams, fromState, fromParams, error) {
        console.log('i am here, in settings page, but i dont have an access');
        if (error && error === "Denied") {
            $state.go("index.profile.task");
        }
    });

    if (!SessionService.currentUser || !SessionService.roles) {
        $q.all([
            SessionService.isAuth(),
            SessionService.getCurrentUser(),
        ]).then(function (values) {
            SessionService.roles = values[0].data;
            SessionService.currentUser = values[1].data;
        });
    }
}

function RequestConfig($httpProvider) {
    $httpProvider.useApplyAsync(true);
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.interceptors.push(authInterceptor);
    $httpProvider.interceptors.push(busyInterceptor);
}

function ChartConfig(ChartJsProvider) {
    ChartJsProvider.setOptions({
        colors: ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360']
    });
}

angular.isUndefinedOrNull = function (val) {
    return angular.isUndefined(val) || val === null
};

/**
 * Отлавливает статус 401(UNAUTHORIZED) и редиректит на страницу логина
 * @param $rootScope
 * @param $q
 * @returns {{request: request, response: response, responseError: responseError, requestError: requestError}}
 */
function authInterceptor($rootScope, $q) {
    return {
        'request': function (config) {
            return config;
        },

        'response': function (response) {
            return response;
        },

        'responseError': function (rejection) {
            if (rejection.status === 401) {
                window.location = $rootScope.baseUrl + '/login.html';
                return;
            } if (rejection.status === 400) {
                if (rejection.data && rejection.data.message) {
                    toastr.error(rejection.data.message)
                } else {
                    toastr.error("Ошибка обработки данных")
                }
            }
            return $q.reject(rejection);
        },

        'requestError': function (rejection) {
            return rejection;
        }
    };
}

function busyInterceptor($rootScope, $q) {
    return {
        request: onRequest,
        response: onResponse,
        responseError: onResponseError
    };

    function onResponseError(response) {
        if (response.config && response.config.defer) {
            response.config.defer.reject();
        }
        return $q.reject(response);
    }

    function onResponse(response) {
        if (response.config && response.config.defer) {
            response.config.defer.resolve();
        }
        return $q.when(response);
    }

    function onRequest(config) {
        registerPromise(config);
        return $q.when(config);
    }

    function registerPromise(config) {
        var defer = $q.defer();
        $rootScope.activePromises.push(defer.promise);
        $rootScope.ajaxLoader = defer.promise;
        config.defer = defer;
        // once the promise is fulfilled we remove it from the activePromises array
        defer.promise.finally(function () {
            angular.forEach($rootScope.activePromises, function (promise, index) {
                if (promise === defer.promise) {
                    $rootScope.activePromises.splice(index, 1);
                }
            });
        });
    }
}