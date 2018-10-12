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
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn

            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
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
    var menuDate = $('#menuDate');
    date.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d'
    });

    menuDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d'
    });
});