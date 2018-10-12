var ajaxUrl = "ajax/dishes/";
var datatableApi;
var restaurantId;
var date;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "getDishesByDate",
        data: searchDishesByDate()
    }).done(updateTableByData);
}

function clearSearch() {
    $("#getDishesByDate")[0].reset();
}

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "description"
            },
            {
                "data": "cost"
            },
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
    }));

    $.datetimepicker.setLocale(localeCode);

//  http://xdsoft.net/jqplugins/datetimepicker/
    var date = $('#date');
    date.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d'
    });
});