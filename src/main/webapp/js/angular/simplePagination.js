(function() {
  "use strict";

  var paginationModule = angular.module('simplePagination', []);

  paginationModule.factory('Pagination', function() {

    var pagination = {};

    pagination.getNew = function(perPage, page) {

      perPage = perPage === undefined ? 10 : perPage;
      page = page === undefined ? 0 : page;

      var paginator = {
        pageLimits: [10, 25, 50, 100],
        numPages: 1,
        perPage: perPage,
        page: page,
        total: 0,
        from: 0,
        to: 0
      };

      paginator.prevPage = function(per) {
        if (paginator.page > 0) {
          paginator.page -= 1;
          paginator.from -= per;
          paginator.to = paginator.from + per;
        }
      };

      paginator.nextPage = function(per, dataLength) {
        if (paginator.page < paginator.numPages - 1) {
          paginator.page += 1;
          paginator.from = paginator.from + per;
          paginator.to = paginator.to + per;
        }
        if (paginator.page == paginator.numPages - 1) {
          paginator.to = paginator.to + per + (dataLength - (paginator.to + per));
        }
      };

      paginator.toPageId = function(id, per, dataLength) {
        id = (id > paginator.numPages - 1) ? paginator.numPages - 1 : id;
        if (id >= 0 && id <= paginator.numPages - 1 && id != pagination.page) {
          paginator.page = id;
          paginator.from = (id * per) + 1;
          paginator.to = (id == paginator.numPages - 1) ? paginator.to + per + (dataLength - (paginator.to + per)) : (id * per) + per;
        }
      };

      return paginator;
    };

    return pagination;
  });

  paginationModule.filter('startFrom', function() {
    return function(input, start) {
      if (input === undefined) {
        return input;
      } else {
        return input.slice(+start);
      }
    };
  });

  paginationModule.filter('range', function() {
    return function(input, total) {
      total = parseInt(total);
      for (var i = 0; i < total; i++) {
        input.push(i);
      }
      return input;
    };
  });

})();
