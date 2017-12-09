angular.module('app.services')
    .factory('PaginationService', PaginationService);

function PaginationService(Pagination) {
    return {
        pagination: {},
        pageLimit: 0,
        getNew: function(value, page) {
            this.pageLimit = value;
            this.pagination = Pagination.getNew(value, page);
            return this.pagination;
        },
        createPagination: function(perPage, data) {
            var length = 0;
            if (data) {
                length = data.length;
            }
            this.pagination = this.getNew(perPage);
            this.pagination.numPages = Math.ceil(length / this.pagination.perPage);
            this.pagination.total = length;
            this.pagination.from = (perPage * this.pagination.page) + 1;
            this.pagination.endPage = length % perPage > 0 ? Math.ceil(length/perPage) + 1 : Math.ceil(length/perPage);
            if (length >= perPage) {
                this.pagination.to = this.pagination.page + perPage;
            } else {
                this.pagination.to = length;
            }
            return this.pagination;
        },
        showPages: function (n) {
            var prevCurrentPage = (n >= this.pagination.page - 1 == 0 ? n >= this.pagination.page : n >= this.pagination.page - 1);
            return (n < this.pagination.page + 3 && prevCurrentPage) || n >= this.pagination.endPage - 2;
        }
    }
}
