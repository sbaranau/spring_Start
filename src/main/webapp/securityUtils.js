/**
 * Есть ли у текущего пользователя такая роль
 * @param role наименование роли
 * @param roles список ролей пользователя
 */
function hasRole(role, roles) {
    if (roles) {
        return !!(isAdmin(roles) || roles.includes(role));
    }
    return false;
}

/**
 * Является ли пользователь администратором
 * @param roles список ролей пользователя
 * @returns {boolean|*}
 */
function isAdmin(roles) {
    if (roles) {
        return roles.includes('ADMIN');
    }
    return false;
}