/**
 * Обрабатывает ответ сервера
 * 
 * @param response -
 *            объект ответа сервера
 * @param messageFunction - функиця для вывода сообщения в случае успеха, если null то
 *            выводится "Успех"
 * @param callback -
 *            функция котороая отработает в случае успеха
 * @param noSuccessMessage --
 *            флаг необходимости вывода сообщения в случае успеха
 * @param resultMessages --
 *            объект с сообщениями результатов
 * @returns
 */
function processServerResponse(response, messageFunction, callback, noSuccessMessage, resultMessages) {
    var messages = {
        success : 'Сохранено',
        serverError : 'Ошибка сервера',
        dbError : 'Ошибка редактирования базы',
        validationError : 'Ошибка валидации',
        dbDeleteError: 'Ошибка удаления! Есть связь с другими объектами'
    };

    if (angular.isDefined(resultMessages)) {
        messages = resultMessages
    }
    if (response.resultCode == 0) {
        if (!noSuccessMessage) {
            if (typeof messageFunction == 'function') {
                messageFunction(response.data);
            } else {
                toastr.success(messages.success)
            }
        }
        callback(response.data);
    } else if (response.resultCode == -1) {
        toastr.error(messages.serverError)
    } else if (response.resultCode == -2) {
        toastr.error(messages.dbError)
    } else if (response.resultCode == -3) {
        toastr.error(messages.validationError)
    } else if (response.resultCode == -4) {
        toastr.error(messages.dbDeleteError)
    }
    return null;
}

/**
 * Проверяет значение boolean переменной и возвращает противоположное
 */
function toggle(value) {
    return !value;
}