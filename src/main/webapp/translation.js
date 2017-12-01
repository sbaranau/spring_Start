function TranslateConfig($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix: 'lang/locale-',
        suffix: '.js?ts=' + +new Date()
    });
    $translateProvider.preferredLanguage('ru');
}