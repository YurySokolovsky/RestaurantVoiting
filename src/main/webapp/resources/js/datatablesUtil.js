function makeEditable() {
    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    getRestaurantId ();
    $("#editRow").modal();
}

// https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
function extendsOpts(opts) {
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "language": {
                "search": i18n["common.search"],
                "dataTables_empty": i18n["common.empty"]
            },
            "initComplete": makeEditable
        }
    );
    return opts;
}

function updateRow(id) {
    $("#modalTitle").html(i18n["editTitle"]);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        getRestaurantId ();
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable();
        successNoty("common.deleted");
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("common.saved");
    });
}

function viewMenu(id){
     window.location.href = '/dishes?restaurantId=' + id;
}

function getDishes(id) {
    if (id !== null) {
        restaurantId = id;
    }
    document.cookie = "restaurantId=" + restaurantId;
    $.ajax({
        type: "GET",
        url: "ajax/dishes/",
        data: "restaurantId=" + restaurantId
    }).done(updateTableByData);
}

function searchDishesByDate(){
    var obj;
    if (document.getElementById("date").value !== undefined
                                                && document.getElementById("date").value !== null
                                                && document.getElementById("date").value !== "") {
        date = document.getElementById("date").value;
        obj = $("#getDishesByDate").serialize();
    } else {
        if (date === undefined) {
            date = null;
        }
        document.getElementById("date").value = date;
        obj = $("#getDishesByDate").serialize();
        $("#getDishesByDate")[0].reset();
    }
    return obj;
}

function getRestaurantId () {
    if (document.getElementById("restaurantId") !== undefined && document.getElementById("restaurantId") !== null) {
        document.getElementById("restaurantId").value = restaurantId;
    }
    if (document.getElementById("dishRestaurantId") !== undefined && document.getElementById("dishRestaurantId") !== null) {
       document.getElementById("dishRestaurantId").value = restaurantId;
    }
}

function getCookie(cookieName) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + cookieName.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ))
    return matches ? decodeURIComponent(matches[1]) : undefined
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    var errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function renderMenuBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='viewMenu(" + row.id + ");'><span class='fa fa-list'></span></a>";
    }
}

function renderVoteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='vote(" + row.id + ");'><span class='fa fa-thumbs-o-up'></span></a>";
    }
}