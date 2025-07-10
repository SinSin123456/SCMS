// validtion
function validationField(value, labelElement, inputElement) {
    const stringValue = String(value || "").trim(); 

    if (stringValue === "") {
        labelElement.css("color", "red");
        inputElement.addClass("is-invalid");
        return false;
    } else {
        labelElement.css("color", "");
        inputElement.removeClass("is-invalid");
        return true;
    }
}


const method = {
    GET: "GET",
    POST: "POST"
};

// const context = "/";
const route = {
    USER_TABLE: context + "/scms/admin-users/datatable",
    USER_CREATE: context + "/scms/admin-users/create",
};

const page = {
    id: null,
    table: null,
    selector: {
        datatable: $("#usersTable"),
        inpFullname: $("#fullName"),
        inpUsername: $("#username"),
        inpEmail: $("#email"),
        inpPassword: $("#password"),
        inpRoles: $("#roles"),
        inpJoinDate: $("#joinDate"),
        btnCancel: $("#btncancel"),
        btnSave: $("#btnsave"),
        btnAddUsers: $("#btnAddUsers"),
        // userModalLabel: $("#userModalLabel"),
        createUsersModal: $("#createUsersModal"),
        editUserModal: $("#edituserModal"),
        editFullname: $("#editfullName"),
        editUsername: $("#editusername"),
        editEmail: $("#editemail"),
        editRoles: $("#editroles"),
        editPassword: $("#editpassword"),
        btnUpdate: $("#btnupdate")
    },

    alert: {
        Toast: Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 5000,
        }),
        success: function () {
            page.alert.Toast.fire({
                icon: "success",
                title: "Transaction Completed",
            });
        },
        error: function () {
            page.alert.Toast.fire({
                icon: "error",
                title: "Transaction Error",
            });
        },
        message: function (value) {
            page.alert.Toast.fire({
                icon: "error",
                title: "Message",
                text: value,
            });
        },
        alertWithMessage: function (value) {
            $.alert(value);
        },
        errorWithMessage: function (value) {
            $.alert({
                title: "",
                content: value,
                type: "red",
            });
        },
        SweetAlert2: Swal.mixin({
            width: 400,
            showCancelButton: false,
            allowOutsideClick: true,
            allowEscapeKey: true,
            showConfirmButton: true,
        }),
        sweetSave: function () {
            page.alert.SweetAlert2.fire({
                icon: "success",
                title: "Save Success",
            });
        },
        sweetUpdate: function () {
            page.alert.SweetAlert2.fire({
                icon: "success",
                title: "Update Success",
            });
        },
        sweetDelete: function () {
            page.alert.SweetAlert2.fire({
                icon: "success",
                title: "Delete Success",
            });
        },
        sweetError: function () {
            page.alert.SweetAlert2.fire({
                icon: "error",
                title: "Error",
            });
        },

    },


    ajax: {
        createUsers: function (data) {
            return page.util.ajaxRequestParam(route.USER_CREATE, method.POST, data);
        },


        loadDatatable: function () {
            page.table = page.selector.datatable.DataTable({
                paging: true,
                searching: true,
                info: true,
                ordering: true,
                responsive: true,
                autoWidth: true,
                processing: true,
                serverSide: true,
                orderCellsTop: true,

                ajax: {
                    url: route.USER_TABLE,
                    type: "POST",
                    contentType: "application/json",
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                },
                columnDefs: [{
                    targets: [0],
                    visible: false,
                    searchable: false,
                }],
                oLanguage: {
                    sSearch: "Search By Fullname"
                },
                columns: [
                    { data: "id" },
                    {
                        data: null,
                        className: "text-center",
                        render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                        },
                    },
                    { data: "fullname", className: "text-center" },
                    { data: "username", className: "text-center" },
                    { data: "email", className: "text-center" },
                    { data: "roles", className: "text-center" },
                    {
                        data: "joinDate",
                        className: "text-center",
                        render: function (data) {
                            if (!data) return "";
                            let date = new Date(data);
                            let day = date.getDate().toString().padStart(2, "0");
                            let month = date.toLocaleString("en-GB", { month: "short" });
                            let year = date.getFullYear();
                            return `${day}-${month}-${year}`;
                        },
                    },
                    {
                        data: null,
                        className: "text-center",
                        render: function () {
                            return `
                                    <div class="btn-group btn-group-sm">
                                        <a href="#" class="btn btn-sm btn-outline-info btnEdit" data-bs-toggle="tooltip" title="EDIT"><i class="fas fa-edit"></i></a>
                                        <a href="#" class="btn btn-sm btn-outline-danger btnDelete" data-bs-toggle="tooltip" title="DELETE"><i class="fas fa-trash-alt"></i></a>
                                    </div>`;
                        },
                    },
                ],
                drawCallback: function () {
                    if (typeof page.util.tooltips === "function") {
                        page.util.tooltips();
                    }
                }
            });
        },
        ClearAllInput: function () {
            page.selector.inpFullname.val("");
            page.selector.inpEmail.val("");
            page.selector.inpRoles.val("");
            page.selector.inpUsername.val("");
            page.selector.inpRoles.al("");
            page.selector.editEmail.val("");
            page.selector.editFullname.val("");
            // page.selector.editPassword.val("");
            page.selector.editRoles.val("");
            page.id = null;
            page.selector.inpFullname
                .children("label")
                .css({ color: "black" })
                .text(
                    page.selector.inpFullname
                        .children("label")
                        .text()
                        .replace("is required", "")
                );
            page.selector.inpFullname
                .children("input, select")
                .css({ "border-color": "#CEE4DA" });
            page.selector.inpEmail
                .children("label")
                .css({ color: "black" })
                .text(
                    page.selector.inpEmail
                        .children("label")
                        .text()
                        .replace("is required", "")
                );
            page.selector.inpEmail
                .children("input, select")
                .css({ "border-color": "#CEE4DA" })
            page.selector.inpRoles
                .children("label")
                .css({ color: "black" })
                .text(
                    page.selector.inpRoles
                        .children("label")
                        .text()
                        .replace("is required", "")
                );
            page.selector.inpRoles
                .children("input, select")
                .css({ "border-color": "#CEE4DA" })
            page.selector.inpUsername
                .children("label")
                .css({ color: "black" })
                .text(
                    page.selector.inpUsername
                        .children("label")
                        .text()
                        .replace("is required", "")
                );
            page.selector.inpUsername
                .children("input, select")
                .css({ "border-color": "#CEE4DA" });
        },

    },
    fire: {
        loading: function () {
            $("#container").waitMe({
                effect: "win8",
                text: "",
                waitTime: -1,
                textPos: "vertical",
                effect: "win8",
                text: "",
                color: "#000",
                maxSize: "",
                waitTime: -1,
                textPos: "vertical",
                fontSize: "",
                source: "",
                onClose: function () { },
            });
        },

        onClickSave: function () {
            page.selector.btnSave.on("click", function () {
                $.confirm({
                    title: "",
                    content: "Are you sure?",
                    type: "dark",
                    buttons: {
                        confirm: {
                            text: 'Yes',
                            btnClass: 'btn-blue',
                            action: function () {
                                var data = {
                                    fullname: page.selector.inpFullname.val(),
                                    email: page.selector.inpEmail.val(),
                                    roles: page.selector.inpRoles.val(),
                                    username: page.selector.inpUsername.val(),
                                    joinDate: page.selector.inpJoinDate.val(),
                                };

                                var validationfieldFullname = validationField(
                                    data.fullname,
                                    page.selector.inpFullname.parent().find("label").first(),
                                    page.selector.inpFullname,
                                );
                                var validationfieldEmail = validationField(
                                    data.email,
                                    page.selector.inpEmail.parent().find("label").first(),
                                    page.selector.inpEmail,
                                );
                                var validationfieldRoles = validationField(
                                    data.roles,
                                    page.selector.inpRoles.parent().find("label").first(),
                                    page.selector.inpRoles,
                                );
                                var validationfieldUsername = validationField(
                                    data.username,
                                    page.selector.inpUsername.parent().find("label").first(),
                                    page.selector.inpUsername
                                );
                                var validationfieldJoinDate = validationField(
                                    data.joinDate,
                                    page.selector.inpJoinDate.parent().find("label").first(),
                                    page.selector.inpJoinDate
                                );

                                if (
                                    validationfieldEmail &&
                                    validationfieldFullname &&
                                    validationfieldJoinDate &&
                                    validationfieldRoles &&
                                    validationfieldUsername
                                ) {
                                    page.ajax.createUsers(data).then((res) => {
                                        if (res.success) {
                                            SweetAlert2Util.success();
                                            page.table.ajax.reload(null, false);
                                            page.selector.createUsersModal.modal("hide");
                                            reloadPage();
                                        } else {
                                            SweetAlert2Util.errorWithMessage(res.code, res.message);
                                        }
                                    });
                                }
                            }
                        },
                        cancel: function () {

                        }
                    }
                });
            });
        },
    },

    util: {
        ajaxRequestParam: function (route, method, data) {
            return new Promise((resolve, reject) => {
                page.fire.loading();

                $.ajax({
                    url: route,
                    type: method,
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    dataType: "json",
                    cache: false,
                    timeout: 600000,
                    success: function (res) {
                        resolve(res);
                    },
                    error: function (e) {
                        reject(e);
                    },
                });
            })
                .finally(() => {
                    $("#container").waitMe("hide");
                })
                .catch((err) => {
                    console.log(err);
                    page.alert.error();
                });
        },
        tooltips: function () {
            $('[data-toggle="tooltip"]').each(function () {
                var options = {
                    html: true,
                };

                if ($(this)[0].hasAttribute("data-type")) {
                    options["template"] =
                        '<div class="tooltip ' + $(this).attr("data-type") + '" role="tooltip">' +
                        '<div class="arrow"></div>' +
                        '<div class="tooltip-inner"></div>' +
                        '</div>';
                }
                $(this).tooltip(options);
            });
        },

        ajaxRequestNoParam: function (route, method) {
            return new Promise((resolve, reject) => {
                page.fire.loading();
                $.ajax({
                    url: route,
                    type: method,
                    contentType: "application/json",
                    dataType: "json",
                    cache: false,
                    timeout: 600000,
                    success: function (res) {
                        resolve(res);
                    },
                    error: function (e) {
                        reject(e);
                    },
                });
            })
                .finally(() => {
                    $("#container").waitMe("hide");
                })
                .catch((err) => {
                    console.log(err);
                    page.alert.error();
                });
        },
    },

    initView: function () { },
    initData: function () {
        page.ajax.loadDatatable();
    },
    initEvent: function () {
        page.fire.onClickSave();

    },

    init: function () {
        page.initView();
        page.initData();
        page.initEvent();
    },

};
$(function () {
    page.init();
});