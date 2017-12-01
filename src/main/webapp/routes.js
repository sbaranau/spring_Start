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

    // Профиль
    var PROFILE = 'index.profile';
    var PROFILE_TASK_LIST = 'index.profile.task';
    var PROFILE_DOCUMENT_LIST = 'index.profile.document';
    var PROFILE_TRAINING_LIST = 'index.profile.training';
    var PROFILE_USER_LIST = 'index.profile.user';
    var PROFILE_EQUIPMENT_LIST = 'index.profile.equipment';
    var PROFILE_DOCLIST_LIST = 'index.profile.doclist';
    $stateProvider
        .state(PROFILE, {
            url: '/profile',
            template: DEFAULT_VIEW,
            redirectTo: PROFILE_TASK_LIST
        })
        .state(PROFILE_TASK_LIST, {
            url: '/task/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileTask.htm',
                    controller: 'ProfileTaskCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(PROFILE_DOCUMENT_LIST, {
            url: '/documents/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileDocuments.htm',
                    controller: 'ProfileDocumentsCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(PROFILE_TRAINING_LIST, {
            url: '/trainings/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileTrainings.htm',
                    controller: 'ProfileTrainingsCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(PROFILE_USER_LIST, {
            url: '/users/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileUsers.htm',
                    controller: 'ProfileUsersCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(PROFILE_EQUIPMENT_LIST, {
            url: '/equipments/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileEquipment.htm',
                    controller: 'ProfileEquipmentCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(PROFILE_DOCLIST_LIST, {
            url: '/doclist/list',
            views: {
                '': {
                    templateUrl: 'models/profile/profileDocumentList.htm',
                    controller: 'ProfileDocumentListCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Управление обучением
    var TRAININGS = 'index.trainings';
    var TRAININGS_TRAINING = 'index.trainings.training';
    var TRAININGS_LEARNING_JOURNAL = 'index.trainings.learningJournal';
    var TRAININGS_TEACHERS = 'index.trainings.teachers';
    var TRAININGS_REPORTS = 'index.trainings.reports';
    $stateProvider
        .state(TRAININGS, {
            url: '/trainings',
            template: DEFAULT_VIEW,
            redirectTo: TRAININGS_TRAINING
        })
        .state(TRAININGS_TRAINING, {
            url: '/training',
            views: {
                '': {
                    templateUrl: 'models/trainings/training/training.htm',
                    controller: 'TrainingsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'TRAININGS');
                }
            }
        })
        .state(TRAININGS_LEARNING_JOURNAL, {
            url: '/learning_journal',
            views: {
                '': {
                    templateUrl: 'models/trainings/learningJournal/learningJournal.htm',
                    controller: 'LearningJournalCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'TRAININGS');
                }
            }
        })
        .state(TRAININGS_TEACHERS, {
            url: '/teachers',
            views: {
                '': {
                    templateUrl: 'models/trainings/teachers/teachers.htm',
                    controller: 'TrainingTeachersCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'TRAININGS');
                }
            }
        })
        .state(TRAININGS_REPORTS, {
            url: '/reports',
            views: {
                '': {
                    templateUrl: 'models/trainings/reports/trainingReports.htm',
                    controller: 'TrainingReportsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'TRAININGS');
                }
            }
        });

    // Справочники
    var REFERENCES = 'index.references';
    var REFERENCES_ROOMS = 'index.references.rooms';
    var REFERENCES_NEW_ROOM = 'index.references.rooms.new';
    var REFERENCES_EDIT_ROOM = 'index.references.rooms.edit';
    var REFERENCES_CERTIFICATION = 'index.references.certification';
    var REFERENCES_MEASURES = 'index.references.measures';
    var REFERENCES_CATEGORY = 'index.references.category';
    var REFERENCES_GROUP = 'index.references.group';
    var REFERENCES_DEPARTMENTS = 'index.references.departments';
    var REFERENCES_POSTS = 'index.references.posts';
    var REFERENCES_REASONS = 'index.references.reasons';
    var REFERENCES_JOBS = 'index.references.jobs';
    var REFERENCES_NEW_JOB = 'index.references.jobs.new';
    var REFERENCES_EDIT_JOB = 'index.references.jobs.edit';
    var REFERENCES_TESTS = 'index.references.tests';
    var REFERENCES_NEW_TEST = 'index.references.tests.new';
    var REFERENCES_EDIT_TEST = 'index.references.tests.edit';
    var REFERENCES_PRODUCERS = 'index.references.producers';
    var REFERENCES_NEW_PRODUCER = 'index.references.producers.new';
    var REFERENCES_EDIT_PRODUCER = 'index.references.producers.edit';
    var REFERENCES_VIEW_PRODUCER = 'index.references.producers.view';
    var REFERENCES_CODES = 'index.references.codes';
    var REFERENCES_NEW_CODE = 'index.references.codes.new';
    var REFERENCES_EDIT_CODE = 'index.references.codes.edit';
    var REFERENCES_VIEW_CODE = 'index.references.codes.view';
    var REFERENCES_UNITS = 'index.references.units';
    $stateProvider
        .state(REFERENCES, {
            url: '/references',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_CERTIFICATION
        })
        .state(REFERENCES_ROOMS, {
            url: '/rooms',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_NEW_ROOM,
             views: {
                 '': {
                     templateUrl: 'models/references/rooms/rooms.ref.htm',
                     controller: 'RoomsRefCtrl',
                     controllerAs: 'vm'
                 }
             }
        })
        .state(REFERENCES_NEW_ROOM, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/references/rooms/add/rooms.add.htm',
                    controller: 'SaveRoomCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_EDIT_ROOM, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/references/rooms/edit/rooms.edit.htm',
                    controller: 'EditRoomCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_GROUP, {
            url: '/group/list',
            views: {
                '': {
                    templateUrl: 'models/references/group/group.ref.htm',
                    controller: 'GroupRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_CERTIFICATION, {
            url: '/certification',
            views: {
                '': {
                    templateUrl: 'models/references/certification/certification.ref.htm',
                    controller: 'CertificationRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_MEASURES, {
            url: '/measures',
            views: {
                '': {
                    templateUrl: 'models/references/measures/measures.ref.htm',
                    controller: 'MeasuresRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_CATEGORY, {
            url: '/category',
            views: {
                '': {
                    templateUrl: 'models/references/category/category.ref.htm',
                    controller: 'CategoryRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_DEPARTMENTS, {
            url: '/departments',
            views: {
                '': {
                    templateUrl: 'models/references/departments/departments.ref.htm',
                    controller: 'DepartmentsRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_POSTS, {
            url: '/posts',
            views: {
                '': {
                    templateUrl: 'models/references/posts/posts.ref.htm',
                    controller: 'PostRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_REASONS, {
            url: '/reasons',
            views: {
                '': {
                    templateUrl: 'models/references/reasons/reasons.ref.htm',
                    controller: 'ReasonsRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_JOBS, {
            url: '/jobs',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_NEW_JOB,
            views: {
                '': {
                    templateUrl: 'models/references/jobs/jobs.ref.htm',
                    controller: 'JobsRefCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(REFERENCES_NEW_JOB, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/references/jobs/add/job.add.htm',
                    controller: 'SaveJobCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_EDIT_JOB, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/references/jobs/edit/job.edit.htm',
                    controller: 'EditJobCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_TESTS, {
            url: '/tests',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_NEW_TEST,
            views: {
                '': {
                    templateUrl: 'models/references/tests/tests.ref.htm',
                    controller: 'TestsRefCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(REFERENCES_NEW_TEST, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/references/tests/add/test.add.htm',
                    controller: 'AddTestCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_EDIT_TEST, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/references/tests/edit/test.edit.htm',
                    controller: 'EditTestCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_CODES, {
            url: '/codes',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_NEW_CODE,
            views: {
                '': {
                    templateUrl: 'models/references/codes/codes.ref.htm',
                    controller: 'CodesRefCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state(REFERENCES_NEW_CODE, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/references/codes/add/code.add.htm',
                    controller: 'AddCodeCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_EDIT_CODE, {
            url: '/:id/edit',
            views: {
                '': {
                    templateUrl: 'models/references/codes/edit/code.edit.htm',
                    controller: 'EditCodeCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_VIEW_CODE, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/references/codes/view/code.view.htm',
                    controller: 'ViewCodeCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_PRODUCERS, {
            url: '/producers',
            template: DEFAULT_VIEW,
            redirectTo: REFERENCES_NEW_PRODUCER,
            views: {
                '': {
                    templateUrl: 'models/references/producers/producers.ref.htm',
                    controller: 'ProducersRefCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(REFERENCES_NEW_PRODUCER, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/references/producers/add/producer.add.htm',
                    controller: 'AddProducerCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        })
        .state(REFERENCES_EDIT_PRODUCER, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/references/producers/edit/producer.edit.htm',
                    controller: 'EditProducerCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        }).state(REFERENCES_VIEW_PRODUCER, {
        url: '/:id',
        views: {
            '': {
                templateUrl: 'models/references/producers/view/producer.view.htm',
                controller: 'ViewProducerCtrl',
                controllerAs: 'vm'
            }
        },
        resolve: {
            security: function($q, SessionService) {
                return hasAccess($q, SessionService, 'REFERENCES');
            }
        }
        })
        .state(REFERENCES_UNITS, {
            url: '/units',
            views: {
                '': {
                    templateUrl: 'models/references/units/units.ref.htm',
                    controller: 'UnitsRefCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REFERENCES');
                }
            }
        });

    // Изменения и несоответствия
    var CHANGES = 'index.changes';
    var CHANGES_INIT = 'index.changes.initiation';
    var CHANGES_REGISTRY = 'index.changes.registry';
    $stateProvider
        .state(CHANGES, {
            url: '/changes',
            template: DEFAULT_VIEW,
            redirectTo: CHANGES_INIT
        })
        .state(CHANGES_INIT, {
            url: '/initiation',
            views: {
                '': {
                    templateUrl: 'models/changes/init/changes.initiation.htm',
                    controller: 'ControlChangesInitCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(CHANGES_REGISTRY, {
            url: '/registry',
            views: {
                '': {
                    templateUrl: 'models/changes/registry/changes.registry.htm',
                    controller: 'ControlChangesRegistryCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Управление тех. документацией
    var DOCS = 'index.techDoc';
    var DOCS_NOTES = 'index.techDoc.notes';

    var DOCS_DOCUMENTATION = 'index.techDoc.incomingDocumentation';
    var DOCS_DOCUMENTATION_LIST = 'index.techDoc.incomingDocumentation.list';
    var DOCS_DOCUMENTATION_NEW = 'index.techDoc.incomingDocumentation.new';
    var DOCS_DOCUMENTATION_EDIT = 'index.techDoc.incomingDocumentation.edit';
    var DOCS_DOCUMENTATION_VIEW = 'index.techDoc.incomingDocumentation.view';
    var DOCUMENT_CATEGORY = 'index.techDoc.documentCategory';
    $stateProvider
        .state(DOCS, {
            url: '/techdoc',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_NOTES
        })
        .state(DOCS_NOTES, {
            url: '/notes',
            views: {
                '': {
                    templateUrl: 'models/techDoc/notes/notes.htm',
                    controller: 'OfficeNotesCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_NOTES');
                }
            }
        })
        .state(DOCUMENT_CATEGORY, {
            url: '/document_category',
            views: {
                '': {
                    templateUrl: 'models/techDoc/documentCategory/documentCategory.htm',
                    controller: 'DocumentCategoryCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_TYPES');
                }
            }
        })
        .state(DOCS_DOCUMENTATION, {
            url: '/incoming_documentation',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_DOCUMENTATION_LIST
        })
        .state(DOCS_DOCUMENTATION_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/incomingDocumentation/doc.list.htm',
                    controller: 'IncomingDocListCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'INCOMING_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_NEW, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/techDoc/incomingDocumentation/doc.add.htm',
                    controller: 'IncomingDocAddCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'INCOMING_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_EDIT, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/techDoc/incomingDocumentation/doc.edit.htm',
                    controller: 'IncomingDocEditCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'INCOMING_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_VIEW, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/techDoc/incomingDocumentation/doc.view.htm',
                    controller: 'IncomingDocViewCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'INCOMING_DOCUMENTATION');
                }
            }
        });

    // План разработки документации
    var DOCS_DOCUMENTATION_DEV = 'index.techDoc.process';
    var DOCS_DOCUMENTATION_DEV_LIST = 'index.techDoc.process.list';
    var DOCS_DOCUMENTATION_DEV_NEW = 'index.techDoc.process.new';
    var DOCS_DOCUMENTATION_DEV_EDIT = 'index.techDoc.process.edit';
    var DOCS_DOCUMENTATION_DEV_VIEW = 'index.techDoc.process.view';
    $stateProvider
        .state(DOCS_DOCUMENTATION_DEV, {
            url: '/documentation_development',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_DOCUMENTATION_DEV_LIST
        })
        .state(DOCS_DOCUMENTATION_DEV_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/process/process.htm',
                    controller: 'ProcessCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PLAN_OF_DEVELOPMENT_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_DEV_NEW, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/techDoc/process/process.add.htm',
                    controller: 'ProcessAddCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PLAN_OF_DEVELOPMENT_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_DEV_EDIT, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/techDoc/process/process.edit.htm',
                    controller: 'ProcessEditCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PLAN_OF_DEVELOPMENT_DOCUMENTATION');
                }
            }
        })
        .state(DOCS_DOCUMENTATION_DEV_VIEW, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/techDoc/process/process.view.htm',
                    controller: 'ProcessViewCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PLAN_OF_DEVELOPMENT_DOCUMENTATION');
                }
            }
        });

    // Реестр разработки документации
    var DOCS_DOCUMENTATION_REGISTRY = 'index.techDoc.registry';
    var DOCS_DOCUMENTATION_REGISTRY_LIST = 'index.techDoc.registry.list';
    $stateProvider
        .state(DOCS_DOCUMENTATION_REGISTRY, {
            url: '/documentation_registry',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_DOCUMENTATION_REGISTRY_LIST
        })
        .state(DOCS_DOCUMENTATION_REGISTRY_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/registry/registry.htm',
                    controller: 'RegistryCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'REGISTER_OF_DEVELOPMENT_DOCUMENTATION');
                }
            }
        });

    //Журнал учёта документов
    var DOCS_JOURNAL_DOCS = 'index.techDoc.journalDocs';
    var DOCS_JOURNAL_DOCS_LIST = 'index.techDoc.journalDocs.list';
    var DOCS_JOURNAL_DOCS_NEW = 'index.techDoc.journalDocs.new';
    var DOCS_JOURNAL_DOCS_EDIT = 'index.techDoc.journalDocs.edit';
    var DOCS_JOURNAL_DOCS_VIEW = 'index.techDoc.journalDocs.view';
    $stateProvider
        .state(DOCS_JOURNAL_DOCS, {
            url: '/journal_docs',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_JOURNAL_DOCS_LIST
        })
        .state(DOCS_JOURNAL_DOCS_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/journalDocs/docOld.htm',
                    controller: 'DocOldCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ACCOUNTING');
                }
            }
        })
        .state(DOCS_JOURNAL_DOCS_NEW, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/techDoc/journalDocs/docOld.add.htm',
                    controller: 'DocOldAddCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ACCOUNTING');
                }
            }
        })
        .state(DOCS_JOURNAL_DOCS_EDIT, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/techDoc/journalDocs/docOld.edit.htm',
                    controller: 'DocOldEditCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ACCOUNTING');
                }
            }
        })
        .state(DOCS_JOURNAL_DOCS_VIEW, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/techDoc/journalDocs/docOld.view.htm',
                    controller: 'DocOldViewCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ACCOUNTING');
                }
            }
        });

    // Архив
    var DOCS_ARCHIVE = 'index.techDoc.archive';
    var DOCS_ARCHIVE_LIST = 'index.techDoc.archive.list';
    $stateProvider
        .state(DOCS_ARCHIVE, {
            url: '/archive',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_ARCHIVE_LIST
        })
        .state(DOCS_ARCHIVE_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/archive/archive.htm',
                    controller: 'TechdocArchiveCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ARCHIVE');
                }
            }
        });

    // Журнал уничтожения архивных документов
    var DOCS_ARCHIVE_DESTROY = 'index.techDoc.archiveDestroy';
    var DOCS_ARCHIVE_DESTROY_LIST = 'index.techDoc.archiveDestroy.list';
    $stateProvider
        .state(DOCS_ARCHIVE_DESTROY, {
            url: '/archive_destroy',
            template: DEFAULT_VIEW,
            redirectTo: DOCS_ARCHIVE_DESTROY_LIST
        })
        .state(DOCS_ARCHIVE_DESTROY_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/archiveDestroy/archiveDestroy.htm',
                    controller: 'TechdocArchiveDestroyCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'DOCUMENT_ARCHIVE_DESTROY');
                }
            }
        });

    //Управление производством
    // Заполняемые формы
    var FILLABLE_FORMS = 'index.techDoc.fillableForms';
    var FILLABLE_FORMS_LIST = 'index.techDoc.fillableForms.list';
    $stateProvider
        .state(FILLABLE_FORMS, {
            url: '/fillable_forms',
            template: DEFAULT_VIEW,
            redirectTo: FILLABLE_FORMS_LIST
        })
        .state(FILLABLE_FORMS_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/techDoc/fillableForms/fillableForms.htm',
                    controller: 'FillableFormsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PRODUCT_MANAGEMENT');
                }
            }
        });

    // Управление оборудованием
    var EQUIPMENTS = 'index.equipments';
    var EQUIPMENTS_CONTROL_OPTIONS = 'index.controlOptions';
    var EQUIPMENTS_REPAIRS = 'index.repairs';
    var EQUIPMENTS_MAINTENANCE = 'index.maintenance';
    var EQUIPMENTS_LIST = 'index.equipments.list';
    var EQUIPMENTS_NEW = 'index.equipments.new';
    var EQUIPMENTS_EDIT = 'index.equipments.edit';
    var EQUIPMENTS_VIEW = 'index.equipments.view';
    var EQUIPMENTS_PLAN_MAINTANCE_REPAIRS = 'index.plan_maintance_repairs';
    $stateProvider
        .state(EQUIPMENTS, {
            url: '/equipments',
            template: DEFAULT_VIEW,
            redirectTo: EQUIPMENTS_LIST
        })
        .state(EQUIPMENTS_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/equipments/equipment/equipment.htm',
                    controller: 'EquipmentCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENTS');
                }
            }
        })
        .state(EQUIPMENTS_NEW, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/equipments/equipment/add/equipment.add.htm',
                    controller: 'EquipmentAddCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENTS');
                }
            }
        })
        .state(EQUIPMENTS_EDIT, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/equipments/equipment/edit/equipment.edit.htm',
                    controller: 'EquipmentEditCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENTS');
                }
            }
        })
        .state(EQUIPMENTS_VIEW, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/equipments/equipment/view/equipment.view.htm',
                    controller: 'EquipmentViewCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENTS');
                }
            }
        })
        .state(EQUIPMENTS_CONTROL_OPTIONS, {
            url: '/control_options',
            views: {
                '': {
                    templateUrl: 'models/equipments/controlOptions/controlOptions.htm',
                    controller: 'ControlOptionsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENT_CONTROL_OPTIONS');
                }
            }
        })
        .state(EQUIPMENTS_REPAIRS, {
            url: '/repairs',
            views: {
                '': {
                    templateUrl: 'models/equipments/repairs/repairs.htm',
                    controller: 'EquipmentRepairsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENT_REPAIR');
                }
            }
        })
        .state(EQUIPMENTS_MAINTENANCE, {
            url: '/maintenance',
            views: {
                '': {
                    templateUrl: 'models/equipments/maintenance/maintenance.htm',
                    controller: 'MaintenanceCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'EQUIPMENT_MAINTENANCE');
                }
            }
        })
        .state(EQUIPMENTS_PLAN_MAINTANCE_REPAIRS, {
            url: '/plan_maintance_repairs',
            views: {
                '': {
                    templateUrl: 'models/equipments/plan/plan_maintance_repairs.htm',
                    controller: 'PlanMaintenanceRepairsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PLAN_OF_EQUIPMENT_MAINTENANCE_AND_REPAIR');
                }
            }
        });

    // Управление персоналом
    var USERS = 'index.users';
    var USERS_LIST = 'index.users.list';
    var USERS_EDIT_USER = 'index.users.edit';
    var USERS_NEW_USER = 'index.users.new';
    var USERS_VIEW_USER = 'index.users.view';
    $stateProvider
        .state(USERS, {
            url: '/users',
            template: DEFAULT_VIEW,
            redirectTo: USERS_LIST
        })
        .state(USERS_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/users/users/users.htm',
                    controller: 'UsersCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'USERS');
                }
            }
        })
        .state(USERS_NEW_USER, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/users/users/add/user.add.htm',
                    controller: 'UserAddCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'USERS');
                }
            }
        })
        .state(USERS_EDIT_USER, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/users/users/edit/user.edit.htm',
                    controller: 'UserEditCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'USERS');
                }
            }
        })
        .state(USERS_VIEW_USER, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'models/users/users/view/user.view.htm',
                    controller: 'UserViewCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Склад
    var STOCKROOM = 'index.stockroom';
    var STOCKROOM_CARD_GOODS = 'index.stockroom.cardGoods';
    var STOCKROOM_CARD_GOOD_VIEW = 'index.stockroom.cardGoods.view';
    var STOCKROOM_SUPPLY_MATERIAL = 'index.stockroom.supplyMaterial';
    var STOCKROOM_SUPPLY_MATERIAL_LIST = 'index.stockroom.supplyMaterial.list';
    var STOCKROOM_SUPPLY_MATERIAL_NEW = 'index.stockroom.supplyMaterial.new';
    var STOCKROOM_SUPPLY_MATERIAL_EDIT = 'index.stockroom.supplyMaterial.edit';
    $stateProvider
        .state(STOCKROOM, {
            url: '/stockroom',
            template: DEFAULT_VIEW,
            redirectTo: STOCKROOM_CARD_GOODS
        })
        .state(STOCKROOM_CARD_GOODS, {
            url: '/card_goods',
            views: {
                '': {
                    templateUrl: 'models/stockroom/cardGoods/cardGoods.htm',
                    controller: 'CardGoodsCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(STOCKROOM_CARD_GOOD_VIEW, {
            url: '/:id/view',
            views: {
                '': {
                    templateUrl: 'modal/stockroom/cardGoods/cardGoods.view.htm',
                    controller: 'CardGoodsViewCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(STOCKROOM_SUPPLY_MATERIAL, {
            url: '/supply_material',
            template: DEFAULT_VIEW,
            redirectTo: STOCKROOM_SUPPLY_MATERIAL_LIST
        })
        .state(STOCKROOM_SUPPLY_MATERIAL_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/stockroom/supplyMaterials/supplyMaterials.htm',
                    controller: 'SupplyMaterialsCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(STOCKROOM_SUPPLY_MATERIAL_NEW, {
            url: '/new',
            views: {
                '': {
                    templateUrl: 'models/stockroom/supplyMaterials/supplyMaterials.add.htm',
                    controller: 'AddSupplyMaterialCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(STOCKROOM_SUPPLY_MATERIAL_EDIT, {
            url: '/:id',
            views: {
                '': {
                    templateUrl: 'models/stockroom/supplyMaterials/supplyMaterials.edit.htm',
                    controller: 'EditSupplyMaterialCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Управление проектами
    var PROJECT = 'index.project';
    var PROJECT_LIST = 'index.project.list';
    $stateProvider
        .state(PROJECT, {
            url: '/projects',
            template: DEFAULT_VIEW,
            redirectTo: PROJECT_LIST
        })
        .state(PROJECT_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/projects/projects.htm',
                    controller: 'ProjectsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                security: function($q, SessionService) {
                    return hasAccess($q, SessionService, 'PROJECTS');
                }
            }
        });

    // Управление производством
    var PRODUCTION = 'index.production';
    var PRODUCTION_ELECTRICITY = 'index.production.electricity';
    $stateProvider
        .state(PRODUCTION, {
            url: '/production',
            template: DEFAULT_VIEW,
            redirectTo: PRODUCTION_ELECTRICITY
        })
        .state(PRODUCTION_ELECTRICITY, {
            url: '/electricity',
            views: {
                '': {
                    templateUrl: 'models/production/electricity/electricity.htm',
                    controller: 'ElectricityCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Управление качеством
    var QUALITY = 'index.quality';
    var QUALITY_NOTICES = 'index.quality.notices';
    var QUALITY_NOTICES_LIST = 'index.quality.notices.list';
    var QUALITY_DISTRIBUTION = 'index.quality.distribution';
    $stateProvider
        .state(QUALITY, {
            url: '/quality',
            template: DEFAULT_VIEW,
            redirectTo: QUALITY_NOTICES
        })
        .state(QUALITY_NOTICES, {
            url: '/journal_notice',
            template: DEFAULT_VIEW,
            redirectTo: QUALITY_NOTICES_LIST
        })
        .state(QUALITY_NOTICES_LIST, {
            url: '/list',
            views: {
                '': {
                    templateUrl: 'models/quality/notices/noticesList.htm',
                    controller: 'NoticeMaterialsCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state(QUALITY_DISTRIBUTION, {
            url: '/distribution/:id',
            views: {
                '': {
                    templateUrl: 'models/quality/distributionSample/samplingAct.htm',
                    controller: 'SamplingActCtrl',
                    controllerAs: 'vm'
                }
            }
        });

    // Настройки
    var SETTINGS = 'index.settings';
    var SETTINGS_COMMON = 'index.settings.common';
    var SETTINGS_TEMPLATES = 'index.settings.templates';
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
        .state(SETTINGS_TEMPLATES, {
            url: '/templates',
            views: {
                '': {
                    templateUrl: 'models/settings/templates/templates.htm',
                    controller: 'SettingsTemplatesCtrl',
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

    //Протоколирование действий
    var LOGGING = 'index.logging';
    var LOGGING_DATA_DOWNLOAD_LOG = 'index.logging.data_download_log';
    $stateProvider
        .state(LOGGING, {
            url: '/logging',
            template: DEFAULT_VIEW,
            redirectTo: LOGGING_DATA_DOWNLOAD_LOG
        })
        .state(LOGGING_DATA_DOWNLOAD_LOG, {
            url: '/data_download_log',
            views: {
                '': {
                    templateUrl: 'models/logging/dataDownloadLog.htm',
                    controller: 'DataDownloadLogCtrl',
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