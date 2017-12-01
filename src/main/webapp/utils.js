function activateInput(input) {
    var inputElement = document.getElementById(input);
    var errorsElement = document.getElementById(input + ".errors");
    var checkboxElement = document.getElementById(input + ".checkbox");

    if (inputElement == null) {
        throw new Error("Element " + input + " is not exist");
    }

    if (checkboxElement == null) {
        throw new Error("Element " + input + ".checkbox" + " is not exist");
    }

    if (errorsElement) {
        checkboxElement.checked = true;
        inputElement.disabled = false;
    } else {
        inputElement.disabled = !checkboxElement.checked;

        if (inputElement.disabled) {
            inputElement.value = "";

            if (errorsElement != null) {
                errorsElement.innerHTML = "";
            }
        } else {
            if (inputElement.value == "") {
                inputElement.focus();
            }
        }
    }
}

function formatDate(date, includeTime, delimiter) {
    var formattedDateString = ('0' + date.getDate()).slice(-2)
        + '.' + ('0' + (date.getMonth() + 1)).slice(-2)
        + '.' + date.getFullYear();
    if (includeTime) {
        if (!delimiter) {
            delimiter = ' ';
        }

        formattedDateString += delimiter + ('0' + date.getHours()).slice(-2)
            + ':' + ('0' + date.getMinutes()).slice(-2);
    }

    return formattedDateString;
}

function getQueryParam(param) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == param) {
            return pair[1];
        }
    }
    return (false);
}

/**Заменяет запись в списке на новую*/
function changeDataInList(list, newData, oldData) {
    var index = list.indexOf(oldData);
    if (index != -1) {
        list[index] = newData;
    }
}

/**
 * Получить дату с первым днём текущего месяца
 * @returns {Date}
 */
function getFirstDayOfCurrentMonthDate() {
    var date = new Date();
    return new Date(date.getFullYear(), date.getMonth(), 1);
}

/**
 * Получить дату 1 января текущего года
 * @returns {Date}
 */
function getFirstDayOfCurrentYear() {
    return new Date(new Date().getFullYear(), 0, 1);
}

/**
 * Получить дату последний день текущего месяца
 * @returns {Date}
 */
function getLastDayOfCurrentMonth() {
    return new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0);
}

/**
 * Получить дату 31 декабря текущего года
 * @returns {Date}
 */
function getLastDayOfCurrentYear() {
    return new Date(new Date().getFullYear(), 11, 31);
}

/**
 * Фильтр по интервалу дат для заданного свойства объекта
 * @param property свойство объекта с датой
 * @param startDate дата начала
 * @param endDate дата окончания
 * @returns {Function} функция для фильтра {true - входит в интервал}
 */
function dateRangeFilter(property, startDate, endDate) {
    return function (item) {
        if (startDate == null) {
            startDate = getFirstDayOfCurrentMonthDate();
        }
        if (endDate == null) {
            endDate = new Date();
        }
        if (item[property] !== null) {
            return new Date(item[property]) >= startDate && new Date(item[property]) <= endDate;
        }
        return false;
    }
}

/**
 * Фильтр на придмет совпадения значений полей
 * @param property наименование поля по которому происходит фильтрование
 * @param field значение поля по которому необходимо фильтровать
 * @returns {Function}
 */
function filterByField(property, field) {
    return function (item) {
        if (item[property] === field) {
            return item[property];
        }
        return false;
    }
}

/**
 * Ищет первый элемент с классом ошибки и скролит к нему
 */
function scrollToError() {
    if (!angular.isUndefinedOrNull(angular.element('.ng-invalid').first()[0])) {
        angular.element('.ng-invalid').first()[0].scrollIntoView();
    }
}

/**
 * паттерн для проверки бакв и цифр
 * @type {string}
 */
//Набор из букв и цифр (латиница + кириллица):
var validationPattern = '/^[а-яА-ЯёЁa-zA-Z0-9]+$/';

/**
 * Фильтр массива объектов по интервалу дат
 * @param objectList массив
 * @param fromDate дата с
 * @param toDate дата по
 * @param {string} datePropertyName наименование проперти по которой идёт фильтр
 */
function filterCollectionByInterval(objectList, fromDate, toDate, datePropertyName) {
    if (objectList) {
        return objectList.filter(function (object) {
            return (object[datePropertyName] >= fromDate && object[datePropertyName] <= toDate);
        })
    }
    return objectList;
}