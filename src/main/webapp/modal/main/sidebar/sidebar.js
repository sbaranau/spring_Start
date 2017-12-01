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
                if (vm.selectedLinkMenu === 'list' && arr[arr.length - 2] === 'sensors') {
                    vm.selectedLinkMenu = 'map';
                }
                if (vm.selectedLinkMenu === 'common') {
                    if (arr[arr.length - 2] === 'report') {
                        vm.selectedLinkMenu = 'commonReports';
                    }
                }
            } else {
                arr = $location.path().split('/');
                vm.selectedLinkMenu = arr[arr.length - 1];
                if (vm.selectedLinkMenu === 'list') {
                    switch (arr[arr.length - 2]) {
                        case 'users':
                            vm.selectedLinkMenu = 'users';
                            break;
                        case 'equipments':
                            vm.selectedLinkMenu = 'equipments';
                            break;
                        case 'journal_notice':
                            vm.selectedLinkMenu = 'journal_notice';
                            break;
                        case 'group':
                            vm.selectedLinkMenu = 'group';
                            break;
                        case 'documentation_registry':
                            vm.selectedLinkMenu = 'documentation_registry';
                            break;
                        case 'fillable_forms':
                            vm.selectedLinkMenu = 'fillable_forms';
                            break;
                        case 'journal_docs':
                            vm.selectedLinkMenu = 'journal_docs';
                            break;
                        case 'archive':
                            vm.selectedLinkMenu = 'archive';
                            break;
                        case 'archive_destroy':
                            vm.selectedLinkMenu = 'archive_destroy';
                            break;
                        case 'incoming_documentation':
                            vm.selectedLinkMenu = 'documentation';
                            break;
                        case 'data_download_log':
                            vm.selectedLinkMenu = 'data_download_log';
                            break;
                        case 'manage_users':
                            vm.selectedLinkMenu = 'manageUsers';
                            break;
                        case 'supply_material':
                            vm.selectedLinkMenu = 'supply_material';
                            break;
                    }
                }
                if (vm.selectedLinkMenu === 'common') {
                    if (arr[arr.length - 2] === 'settings') {
                        vm.selectedLinkMenu = 'commonSettings';
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
        // Контроль микроклимата
        // {
        //     title: {
        //         linked:false,
        //         tag:'temperatureControl',
        //         name: 'sidebar.temperatureControl.title'
        //     },
        //     links: {
        //         sensors: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/sensors.html',
        //             name:'sidebar.temperatureControl.sensors'
        //         },
        //         map: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/sensors/list.html',
        //             name:'sidebar.temperatureControl.map'
        //         },
        //         microclimate: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/report/microclimate.html',
        //             name:'sidebar.temperatureControl.microclimateJournals'
        //         }
        //     }
        // },
        // Управление лабораторной информацией (LIMS)
        // {
        //     title: {
        //         linked:false,
        //         tag:'laboratoryControl',
        //         name:'sidebar.laboratoryControl.title'
        //     },
        //     links: {
        //         chemistryDepartment: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.laboratoryControl.chemistryDepartment'
        //         },
        //         microbiologicalDepartment: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.laboratoryControl.microbiologicalDepartment'
        //         },
        //         archiveStorage: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.laboratoryControl.archiveStorage'
        //         },
        //         reagentsAndSolutions: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.laboratoryControl.reagentsAndSolutions'
        //         }
        //     }
        // },
        // Управление персоналом
        {
            roles:['USERS'],
            title: {
                linked:false,
                tag:'workforceControl',
                name:'sidebar.workforceControl.title'
            },
            links: {
                users: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/users/list',
                    name:'sidebar.workforceControl.employees',
                    roles:['USERS']
                }
            }
        },
        // Управление обучением
         {
            roles:['TRAININGS'],
             title: {
                 linked:false,
                 tag:'trainingControl',
                 name:'sidebar.trainingControl.title'
             },
             links: {
                 training: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/trainings/training',
                     name:'sidebar.trainingControl.educationPlan',
                     roles:['TRAININGS']
                 },
                 learningJournal: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/trainings/learning_journal',
                     name:'sidebar.trainingControl.learningJournal',
                     roles:['TRAININGS']
                 },
                 teachers: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/trainings/teachers',
                     name:'sidebar.trainingControl.teacherList',
                     roles:['TRAININGS']
                 },
                 reports: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/trainings/reports',
                     name:'sidebar.trainingControl.trainingReport',
                     roles:['TRAININGS']
                 }
             }
         },
        // Управление документацией
        {
            roles:[
                'INCOMING_DOCUMENTATION', 'PLAN_OF_DEVELOPMENT_DOCUMENTATION',
                'REGISTER_OF_DEVELOPMENT_DOCUMENTATION', 'DOCUMENT_ACCOUNTING',
                'DOCUMENT_ARCHIVE', 'DOCUMENT_ARCHIVE_DESTROY', 'DOCUMENT_TYPES', 'DOCUMENT_NOTES'
            ],
            title: {
                linked:false,
                tag:'techDocControl',
                name:'sidebar.techDocControl.title'
            },
            links: {
                documentation: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/incoming_documentation/list',
                    name:'sidebar.techDocControl.incoming',
                    roles:['INCOMING_DOCUMENTATION']
                },
                documentation_development: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/documentation_development/list',
                    name:'sidebar.techDocControl.dev',
                    roles:['PLAN_OF_DEVELOPMENT_DOCUMENTATION']
                },
                documentation_registry: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/documentation_registry/list',
                    name:'sidebar.techDocControl.registry',
                    roles:['REGISTER_OF_DEVELOPMENT_DOCUMENTATION']
                },
                journal_docs: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/journal_docs/list',
                    name:'sidebar.techDocControl.journal',
                    roles:['DOCUMENT_ACCOUNTING']
                },
                archive: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/archive/list',
                    name:'sidebar.techDocControl.archive',
                    roles:['DOCUMENT_ARCHIVE']
                },
                archive_destroy: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/archive_destroy/list',
                    name:'sidebar.techDocControl.archiveDestroy',
                    roles:['DOCUMENT_ARCHIVE_DESTROY']
                },
                document_category: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/document_category',
                    name:'sidebar.techDocControl.categories',
                    roles:['DOCUMENT_TYPES']
                },
                notes: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/notes',
                    name:'sidebar.techDocControl.notes',
                    roles:['DOCUMENT_NOTES']
                }
            }
        },
        //Управление производством
        {
            roles:['PRODUCT_MANAGEMENT'],
            title: {
                linked:false,
                tag:'productionManagement',
                name:'sidebar.productionManagement.title'
            },
            links: {
                fillable_forms: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/techdoc/fillable_forms',
                    name:'sidebar.productionManagement.fillableForms',
                    roles:['PRODUCT_MANAGEMENT']
                }
            }
        },
        // Управление оборудованием
        {
            roles:[
                'EQUIPMENTS', 'EQUIPMENT_CONTROL_OPTIONS', 'EQUIPMENT_MAINTENANCE',
                'EQUIPMENT_REPAIR', 'PLAN_OF_EQUIPMENT_MAINTENANCE_AND_REPAIR'
            ],
            title: {
                linked:false,
                tag:'equipmentControl',
                name:'sidebar.equipmentControl.title'
            },
            links: {
                equipments: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/equipments/list',
                    name:'sidebar.equipmentControl.equipment',
                    roles:['EQUIPMENTS']
                },
                plan_maintance_repairs: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/plan_maintance_repairs',
                    name:'sidebar.equipmentControl.planMaintanceRepairs',
                    roles:['PLAN_OF_EQUIPMENT_MAINTENANCE_AND_REPAIR']
                },
                controlOptions: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/control_options',
                    name:'sidebar.equipmentControl.options',
                    roles:['EQUIPMENT_CONTROL_OPTIONS']
                },
                maintenance: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/maintenance',
                    name:'sidebar.equipmentControl.maintenance',
                    roles:['EQUIPMENT_MAINTENANCE']
                },
                repairs: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/repairs',
                    name:'sidebar.equipmentControl.repair',
                    roles:['EQUIPMENT_REPAIR']
                }
                // equipmentReport: {
                //     linked:false,
                //     link:$rootScope.baseUrl + '/equipment/equipmentReport.html',
                //     name:'sidebar.equipmentControl.reports'
                // }
            }
        },
        // Управление проектами
        {
            roles:['PROJECTS'],
            title: {
                linked:false,
                tag:'projectControl',
                name:'sidebar.projectControl.title'
            },
            links: {
                project: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/projects/list',
                    name:'sidebar.projectControl.project',
                    roles:['PROJECTS']
                }
            }
        },
        // Управление изменениями и несоответствиями
        // {
        //     title: {
        //         linked:false,
        //         tag:'nonconformityControl',
        //         name:'sidebar.nonconformityControl.title'
        //     },
        //     links: {
        //         initiation: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/app#/changes/initiation',
        //             name:'sidebar.nonconformityControl.initiation'
        //         },
        //         registry: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/app#/changes/registry',
        //             name:'sidebar.nonconformityControl.registry'
        //         }
        //     }
        // },
        // Управление контролем качества
         {
             title: {
                 linked:false,
                 tag:'qualityControl',
                 name:'sidebar.qualityControl.title'
             },
             links: {
                 journal_notice: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/quality/journal_notice/list',
                     name:'sidebar.qualityControl.notice'
                 }
                 //,
        //         journalDistributionSample: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/journalDistributionSample.html',
        //             name:'sidebar.qualityControl.distributionSample'
        //         },
        //         journalIdentification: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/journalIdentification.html',
        //             name:'sidebar.qualityControl.identification'
        //         },
        //         journalTransmissionTestOtherOrg: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/journalTransmissionTestOtherOrg.html',
        //             name:'sidebar.qualityControl.transmission'
        //         },
        //         journalEntranceControlMaterial: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/journalEntranceControlMaterial.html',
        //             name:'sidebar.qualityControl.entranceControlMaterial'
        //         },
        //         stabilityGraph: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.qualityControl.stabilityGraph'
        //         },
        //         stabilityRegistration: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.qualityControl.stabilityRegistration'
        //         }
             }
         },
        // Склад
         {
             title: {
                 linked:false,
                 tag:'stockroom',
                 name:'sidebar.stockroom.title'
             },
             links: {
                 card_goods: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/stockroom/card_goods',
                     name:'sidebar.stockroom.cardGoods'
                 },
                 supply_material: {
                     linked:false,
                     link:$rootScope.baseUrl + '/app#/stockroom/supply_material',
                     name:'sidebar.stockroom.materials'
                 },
                 movement: {
                     linked:false,
                     link:null,
                     name:'sidebar.stockroom.movement'
                 },
                 quarantine: {
                     linked:false,
                     link:null,
                     name:'sidebar.stockroom.quarantine'
                 },
                 wholesale: {
                     linked:false,
                     link:null,
                     name:'sidebar.stockroom.wholesale'
                 },
                 journalProtocols: {
                     linked:false,
                     link:$rootScope.baseUrl + '/journalProtocols.html',
                     name:'sidebar.stockroom.protocols'
                 },
                 bids: {
                     linked:false,
                     link:null,
                     name:'sidebar.stockroom.bids'
                 }
             }
         },
        // Валидация и квалификация
        // {
        //     title: {
        //         linked:false,
        //         tag:'validqual',
        //         name:'sidebar.validqual.title'
        //     },
        //     links: {
        //         validationequipment: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/validationequipment.html',
        //             name:'sidebar.validqual.equipment'
        //         },
        //         processes: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.validqual.processes'
        //         },
        //         methods: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.validqual.methods'
        //         }
        //     }
        // },
        // Модуль непрерывного мониторинга параметров чистых помещений
        // {
        //     title: {
        //         linked:false,
        //         tag:'pressure',
        //         name:'sidebar.pressure.title'
        //     },
        //     links: {
        //         drops: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.pressure.drops'
        //         },
        //         speedAirFlow: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.pressure.speedAirFlow'
        //         },
        //         countingParticleConcentration: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.pressure.countingParticleConcentration'
        //         }
        //     }
        // },
        // Досье серии
        // {
        //     title: {
        //         linked:false,
        //         tag:'dossier',
        //         name:'sidebar.dossier.title'
        //     },
        //     links: {
        //
        //     }
        // },
        // Управление производством
        // {
        //     title: {
        //         linked:false,
        //         tag:'production',
        //         name:'sidebar.production.title'
        //     },
        //     links: {
        //         pannel: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.pannel'
        //         },
        //         suppliment: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.suppliment'
        //         },
        //         collection: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.collection'
        //         },
        //         maps: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.maps'
        //         },
        //         journal_use: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/journal_use.html',
        //             name:'sidebar.production.use'
        //         },
        //         sampling: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.sampling'
        //         },
        //         control: {
        //             linked:false,
        //             link:null,
        //             name:'sidebar.production.control'
        //         },
        //         electricity: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/app#/production/electricity',
        //             name:'sidebar.production.consumption'
        //         }
        //     }
        // },
        // Отчеты
        // {
        //     title: {
        //         linked:false,
        //         tag:'reports',
        //         name:'sidebar.reports.title'
        //     },
        //     links: {
        //         commonReports: {
        //             linked:false,
        //             link:$rootScope.baseUrl + '/report/common.html',
        //             name:'sidebar.reports.common'
        //         }
        //     }
        // },
        // Справочники
        {
            roles:['REFERENCES'],
            title: {
                linked:false,
                tag:'references',
                name:'sidebar.references.title'
            },
            links: {
                rooms: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/rooms',
                    name:'sidebar.references.rooms',
                    roles:['REFERENCES']
                },
                claims: {
                    linked:false,
                    link:$rootScope.baseUrl + '/claims.html',
                    name:'sidebar.references.claims',
                    roles:['REFERENCES']
                },
                producers: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/producers',
                    name:'sidebar.references.contractors',
                    roles:['REFERENCES']
                },
                tests: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/tests',
                    name:'sidebar.references.tests',
                    roles:['REFERENCES']
                },
                group: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/group/list',
                    name:'sidebar.references.group',
                    roles:['REFERENCES']
                },
                departments: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/departments',
                    name:'sidebar.references.companyStructure',
                    roles:['REFERENCES']
                },
                posts: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/posts',
                    name:'sidebar.references.posts',
                    roles:['REFERENCES']
                },
                category: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/category',
                    name:'sidebar.references.category',
                    roles:['REFERENCES']
                },
                types_jobs: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/jobs',
                    name:'sidebar.references.typesJobs',
                    roles:['REFERENCES']
                },
                measures: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/measures',
                    name:'sidebar.references.measurement',
                    roles:['REFERENCES']
                },
                certification: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/certification',
                    name:'sidebar.references.certification',
                    roles:['REFERENCES']
                },
                reason: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/reasons',
                    name:'sidebar.references.reasons',
                    roles:['REFERENCES']
                },
                codes: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/codes',
                    name:'sidebar.references.codes',
                    roles:['REFERENCES']
                },
                units_of_measure: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/references/units',
                    name:'sidebar.references.units',
                    roles:['REFERENCES']
                }
            }
        },
        // Настройки
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
                },
                microclimateMap: {
                    linked:false,
                    link:$rootScope.baseUrl + '/settings/microclimateMap.html',
                    name:'sidebar.settings.microclimateMap',
                    roles:['SETTINGS']
                },
                mail: {
                    linked:false,
                    link:$rootScope.baseUrl + '/settings/mail.html',
                    name:'sidebar.settings.mail',
                    roles:['SETTINGS']
                },
                fileDataLoad: {
                    linked:false,
                    link:$rootScope.baseUrl + '/settings/fileDataLoad.html',
                    name:'sidebar.settings.fileDataLoad',
                    roles:['SETTINGS']
                },
                templates: {
                    linked:false,
                    link:$rootScope.baseUrl + '/app#/settings/templates',
                    name:'sidebar.settings.templates',
                    roles:['SETTINGS']
                }
            }
        },
        //Протоколирование действий
        {
            roles: ['ADMIN'],
            title: {
                linked: false,
                tag: 'logging',
                name: 'sidebar.logging.title'
            },
            links: {
                data_download_log: {
                    linked: false,
                    link: $rootScope.baseUrl + '/app#/logging/data_download_log',
                    name: 'sidebar.logging.data_download_log',
                    roles: ['ADMIN']
                }
            }
        }
    ];
}