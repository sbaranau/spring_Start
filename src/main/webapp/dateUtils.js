function getFirstDayOfYear() {
    return new Date(new Date().getFullYear(), 0, 1);
};

function getLastDayOfYear() {
    return new Date(new Date().getFullYear(), 11, 31);
};

function addYears(years) {
    var now = new Date();
    return new Date(now.getFullYear() + years, now.getMonth(), now.getDate());
};