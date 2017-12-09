angular.module('app')
    .controller('UserEditCtrl', UserEditCtrl);
angular.module('app').filter('reverse', function() {
    return function(items) {
        if (angular.isUndefinedOrNull(items)) {
            return items;
        }
        return items.slice().reverse();
    };
});
function UserEditCtrl($scope, $http, $filter, $state, UserService,  modal, FileUploadService) {

    var vm = this,
        fileService = vm.fileService = FileUploadService,
        service = vm.service = UserService;

    vm.isLoaded = false;

    vm.loadPosts = function (departmentId, index) {
        service.getPosts(departmentId)
            .then(function (response) {
                processServerResponse(response.data, null, function (data) {
                    $scope.departmentPosts[departmentId] = data;
                    var admin = $filter('filter')($scope.departmentPosts[departmentId], {name: 'Администратор'});
                    if (admin.length > 0 && $scope.userForm.serviceInformation && $scope.userForm.serviceInformation[index] && $scope.userForm.serviceInformation[index].post == null) {
                        var adminId = admin[0].id;
                        $scope.userForm.serviceInformation[index].post = adminId;
                    }
                }, true)
            })
    };

    $scope.$watch('vm.isLoaded', function(result) {
        if (!result) {
            $scope.settings = {
                isEdit: true,
                pressedOnce: false
            };

            $scope.departmentPosts = [];

            service.edit($state.params.id)
                .then(function (response) {
                    processServerResponse(response.data, null, function (data) {
                        $scope.userForm = data.userForm;
                        $scope.categoryList = data.categoryList;
                        $scope.typesJobs = data.typesJobs;
                        $scope.departmentsList = data.departmentsList;
                        $scope.userStatus = data.userStatus;

                        for (var index in $scope.userForm.serviceInformation) {
                            $scope.userForm.serviceInformation[index].post = $scope.userForm.serviceInformation[index].post * 1;
                            $scope.departmentPosts.push({});
                            vm.loadPosts($scope.userForm.serviceInformation[index].departmentId, index);
                            //department posts should be array

                        }

                        // load photo
                        if ($scope.userForm.user.photo != null) {
                            service.getPhoto($scope.userForm.user.photo)
                                .then(function (response) {
                                    processServerResponse(response.data, null, function (data) {
                                        $scope.userForm.filename = data.name;
                                    }, true)
                                })
                        }

                        if ($scope.userForm.userCategories.length == 0) $scope.addCategory();
                        if ($scope.userForm.educations.length == 0) $scope.addEducation();
                        if ($scope.userForm.certifications.length == 0) $scope.addCertification();
                        if ($scope.userForm.userJobs.length == 0) $scope.addJob();

                        vm.isLoaded = true;
                    }, true)
                });
        }
    });

    $scope.saveUser = function () {

        //checking if we can submit by broadcasting event formSubmit to validate form fields
        $scope.validationResults = [];
        $scope.$broadcast('formSubmit');
        $scope.valid = $scope.validationResults.indexOf(false) == -1;

        var validationDates = [];
        $scope.userForm.serviceInformation.forEach(function (obj, index) {
            validationDates.push(new Date(obj.dateReceipt));
        });

        var validationDatesResult = true;
        var n = validationDates.length;
        for (var i = 0; i < n - 1; i++) {
            for (var j = i + 1; j < n; j++) {
                if (validationDates[i].getDate() == validationDates[j].getDate() && validationDates[i].getMonth() == validationDates[j].getMonth() && validationDates[i].getFullYear() == validationDates[j].getFullYear()) {
                    validationDatesResult = false;
                    break;
                }
            }
        }
        if (!validationDatesResult) {
            $scope.valid = false;
            $scope.message = "Одинаковая дата";
        }

        if ($scope.userForm.photo && !fileService.isFileIsTemplateImage($scope.userForm.photo)) {
            $scope.validationResults.push(false);
            toastr.error('Допустимые форматы для загрузки изображений: .jpg .png .gif');
            $scope.valid = false;
        }

        if ($scope.userForm.photo && fileService.isFileTemplateSize($scope.userForm.photo)) {
            $scope.validationResults.push(false);
            toastr.error('Размер фотографии не должен превышать 2Mb');
            $scope.valid = false;
        }

        if ($scope.valid) {
            //continue

            var photo = $scope.userForm.photo ? $scope.userForm.photo : null;

            $scope.jobDescriptions = null;
            $scope.fileNameJD = null;

            $scope.userForm.photo = null;

            //set user fields
            if (!$scope.userForm.user.id) {
                $scope.userForm.user.id = null;
            }
            if (!$scope.userForm.user.sensorNotification) {
                $scope.userForm.user.sensorNotification = null;
            }
            if (!$scope.userForm.user.statusId) {
                $scope.userForm.user.statusId = 0;
            }
            if (!$scope.userForm.user.trainingFinalDataId) {
                $scope.userForm.user.trainingFinalDataId = 0;
            }
            if (!$scope.userForm.user.trainingDescription) {
                $scope.userForm.user.trainingDescription = '';
            }
            if (!$scope.userForm.user.trainingResult) {
                $scope.userForm.user.trainingResult = '';
            }
            if (!$scope.userForm.user.trainingVisited) {
                $scope.userForm.user.trainingVisited = false;
            }
            if (!$scope.userForm.user.departmentId) {
                $scope.userForm.user.departmentId = 0;
            }

            if(photo != null){
                $scope.userForm.filename = photo.name;
            }

            var formData = new FormData();
            formData.append('file', photo);
            formData.append('form', angular.toJson($scope.userForm));
            $http.post('settings/users/save.html', formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                $scope.userForm.user.photo = response.data.photo;
                processServerResponse(response.data, null, function (data) {
                    $state.go('index.users.list');
                }, true)
            });
        }
    };

    // ADD ROWS
    $scope.addServiceInformation = function () {
        $scope.departmentPosts.push({});
        $scope.settings.pressedOnce = true;
        $scope.userForm.serviceInformation.push({});

        var department = $filter('filter')($scope.departmentsList, {name: 'Руководство'})[0];
        var index = $scope.userForm.serviceInformation.length - 1;
        if (angular.isDefined(department)) {
            var departmentId = department.id;
            $scope.userForm.serviceInformation[index].departmentId = departmentId;
            vm.loadPosts(departmentId, index);
        }
    };

    $scope.addEducation = function () {
        $scope.userForm.educations.push({})
    };

    $scope.addCategory = function () {
        $scope.userForm.userCategories.push({});
    };

    $scope.addCertification = function () {
        $scope.userForm.certifications.push({})
    };

    $scope.addJob = function (userId) {
        $scope.userForm.userJobs.push({})
    };

    // REMOVE ROWS
    $scope.removeServiceInformation = function (index) {
        $scope.userForm.serviceInformation.splice(index, 1);
        $scope.settings.pressedOnce = false;
    };

    $scope.removeEducation = function (index) {
        $scope.userForm.educations.splice(index, 1);
    };

    $scope.removeCategory = function (index) {
        $scope.userForm.userCategories.splice(index, 1);
    };

    $scope.removeCertification = function (index) {
        $scope.userForm.certifications.splice(index, 1);
    };

    $scope.removeJob = function (index) {
        $scope.userForm.userJobs.splice(index, 1);
    };
}