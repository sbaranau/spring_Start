angular.module('app.services')
    .factory('PaginationXFactory', PaginationXFactory);

function PaginationXFactory() {
    return {
        pagination: {},
        features: {
            "search":true,
            "pageSize":true,
            "paginationText":true,
            "actionColumn":true,
            "selectColumn":false,
            "columnSearch":false,
            "colSearchLabel":"Search+",
            "export":false
        },
        columns: [],
        exportOptions: [],
        pageSizeOptions: {
            pageSizeMenu:['10','15','25','50','100'],
            defaultSize:'10'
        },
        toolbarOptions: {
            toolbarType:"link",
            linkSize:10
        },
        actionColumnOptions: {
            title:"",
            colWidth:"10%",
            htmlAttrbs:'id="act-col-id" class="act-col-class"',
            actions:[]
        },
        actionHandlers: {}
    }
}