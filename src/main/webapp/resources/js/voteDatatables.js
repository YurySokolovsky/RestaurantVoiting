var ajaxUrl = "ajax/votes";
var datatableApi;
var date;
var vote;

function vote(restaurantId) {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: getVote(restaurantId)
    }).done(function() {
        updateTable();
        successNoty("vote.add");
    });
}

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "/getVotesByDate",
        data: searchVotesByDate()
    }).done(updateTableByData);
}

function clearSearch() {
    $("#getVotesByDate")[0].reset();
}

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "restaurantName"
            },
            {
                "data": "address"
            },
            {
                "data": "voteCount"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderMenuBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderVoteBtn
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
    date.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d'
    });
})

function getVote(restaurantId) {
    if (date !== undefined && date !== "") {
        voteObject = {date: date, restaurantId: restaurantId}
    } else {
        voteObject = {date: null, restaurantId: restaurantId}
    }
    return voteObject;
}

function searchVotesByDate(){
    var obj;
    if (document.getElementById("date").value !== undefined
                                                && document.getElementById("date").value !== null
                                                && document.getElementById("date").value !== "") {
        date = document.getElementById("date").value;
        obj = $("#getVotesByDate").serialize();
    } else {
        if (date === undefined) {
            date = null;
        }
        document.getElementById("date").value = date;
        obj = $("#getVotesByDate").serialize();
        $("#getVotesByDate")[0].reset();
    }
    return obj;
}
