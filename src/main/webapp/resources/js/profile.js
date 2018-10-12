var ajaxUrl="/profile/";

function changePassword() {
    $("#modalTitle").html(i18n["editTitle"]);
    $("#detailsForm").find(":input").val("");
    $("#change").modal();
}

var oldPassword;
var newPassword;
var repeatPassword;
var object;

function savePassword () {
    oldPassword = jQuery("#oldPassword").val();
    newPassword = jQuery("#newPassword").val();
    repeatPassword = jQuery("#repeatPassword").val();
    object = {oldPassword:oldPassword, newPassword:newPassword};

    if (newPassword == repeatPassword) {
        if (newPassword.length>=5 || newPassword<=32) {
            $.ajaxSetup({cache: false});

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            $.ajax({
                type: "POST",
                url: ajaxUrl+"password",
                data: object
            }).done(function () {
                $("#change").modal("hide");
                successNoty("common.saved");
            }).fail(function () {
                failPasswordNoty("exception.password.wrong");
            });
        } else {
            failPasswordNoty("password.information");
        }
    } else {
        failPasswordNoty("password.notEquals");
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

function failPasswordNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + i18n[key],
        type: "error",
        layout: "bottomRight"
    }).show();
}


