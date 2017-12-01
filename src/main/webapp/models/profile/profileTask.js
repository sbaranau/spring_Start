angular.module('app')
    .controller('ProfileTaskCtrl', ProfileTaskCtrl);

function ProfileTaskCtrl($scope, TaskService, PaginationService, ReferencesService, PaginationXFactory, $q, $state) {
    var vm = this,
        references = vm.references = ReferencesService,
        pgX = vm.pgX = PaginationXFactory,
        service = vm.service = TaskService;

    vm.isLoaded = false;
    vm.startDate = getFirstDayOfCurrentYear();
    vm.endDate = getLastDayOfCurrentMonth();

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            $q.all([
                service.list(),
                references.getDocumentTypes()
            ])
                .then(function(response) {
                    vm.taskList = response[0].data.documentList;
                    references.documentTypeList = response[1].data;
                    vm.taskList.forEach(function (value) {
                        if (value.documentType === 7) {
                            value.name = value.readableName;
                        } else {
                            value.name = vm.getNameByCode(value.name);
                        }
                        if (!angular.isUndefinedOrNull(value.taskExecutor)) {
                            var users = ''
                            value.taskExecutor.forEach(function (value2) {
                                users += value2.name;
                                users += ', ';
                            })
                            value.users = users;
                        }
                    });
                    vm.taskListOnDisplay = angular.copy(vm.taskList);
                    if (pgX.pagination.load) {
                        pgX.pagination.load(angular.copy(vm.taskList));
                    }
                    vm.isLoaded = true;
                })
        }
    });
    vm.view = function(task) {
        if (task.documentType === 1) {
            window.open('/journal_notice_info.html?noticeId=' + task.id, '_self');
        } else if (task.documentType === 2) {
            window.open('/redirectSamplingAct.html?id=' + task.id, '_self');
        } else if (task.documentType === 3) {
            window.open('/journalProtocol/edit_journal_protocol.html?journalProtocolId=' + task.id, '_self');
        } else if (task.documentType === 4) {
            window.open('/protocolTest.html?id=' + task.id, '_self');
        } else if (task.documentType === 5) {
            window.open('/protocolTestNotification.html?id=' + task.id + '&isAllowed=true', '_self');
        } else if (task.documentType === 6) {
            window.open('/protocolTestNotification.html?id=' +  task.id + '&isAllowed=false', '_self');
        } else if (task.documentType === 7) {
            $state.go('index.techDoc.process.view', {id: task.id});
        } else if (task.documentType === 8) {
            $state.go('index.equipments.view', {id: task.id});
        }
    };
    vm.isShow = function(code, name) {
        return code === name;
    };
    vm.edit = function(task) {
        if (task.show === 1) {
            window.open('/journal_notice_info.html?noticeId=' + task.id, '_self');
        } else if (task.documentType === 2) {
            window.open('/redirectSamplingAct.html?id=' + task.id, '_self');
        } else if (task.documentType === 3) {
            window.open('/journalProtocol/edit_journal_protocol.html?journalProtocolId=' + task.id, '_self');
        } else if (task.documentType === 4) {
            window.open('/protocolTest.html?id=' + task.id, '_self');
        } else if (task.documentType === 5) {
            window.open('/protocolTestNotification.html?id=' + task.id + '&isAllowed=true', '_self');
        } else if (task.documentType === 6) {
            window.open('/protocolTestNotification.html?id=' +  task.id + '&isAllowed=false', '_self');
        } else if (task.documentType === 7) {
            $state.go('index.techDoc.process.edit', {id: task.id});
        } else if (task.documentType === 8) {
            $state.go('index.equipments.edit', {id: task.id});
        }
    };
    vm.isShowArray = function(code, nameList) {
        return nameList.includes(code);
    };

    vm.getNameByCode = function (code) {
        return references.getNameFromRefById(references.documentTypeList, code).name;
    };

    pgX.columns = [
        {title:"profile.task.date",dataKey:"date",sortKey:"date",width:"10%", dateColumn:{withTime:false}},
        {title:"profile.task.name",dataKey:"name",sortKey:"name",width:"35%", actions:{type:'ngAction', action:'view'}},
        {title:"profile.task.number",dataKey:"number",sortKey:"number",width:"15%"},
        {title:"profile.task.statusName",dataKey:"statusName",sortKey:"statusName",width:"15%"},
        {title:"profile.task.executor",dataKey:"users",sortKey:"users",width:"20%"}
    ];
    pgX.actionColumnOptions.actions = [
        {name:"Edit", type:"Link", htmlAttrbs:'class="edit-icon"'}
    ];
    $scope.$watch('vm.startDate + vm.endDate', function() {
        if (pgX.pagination.reload) {
            pgX.pagination.reload(filterCollectionByInterval(angular.copy(vm.taskList), vm.startDate, vm.endDate, 'date'));
        }
    });
    /**
     * Странно, это должно быть обязательно после тех методов которые в нём вызываются,
     * пока не поонял почему он не тянет их при вызове, по-этому решил пегинатор делать снизу контроллера
     * */
    pgX.actionHandlers = {
        "view":vm.view,
        "Edit":vm.edit
    };
}
