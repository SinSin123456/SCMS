const method = {
    GET: "GET",
    POST: "POST",
}

const route = {
    ADD_USER: context + "/scms/admin-users/create",
    USER_TABLE: context + "/scms/admin-users/datatable",
    EDIT_USER: context + "/scms/admin-users/edit",
    UPDATE_USER: context + "/scms/admin-users/update",
    DELETE_USER: context + "/scms/admin-users/delete",
}

const page = {
    id: "null",
    table: "null",
    selector: {
        fullname: $("#fullName"),
        username: $("#username"),
        email: $("#email"),
        roles: $("#roles"),
        joinDate: $("#joinDate"),
        createModal: $("#createUsersModal"),
        btnAdd: $("#btnadd"),
        usertable: $("#usersTable"),
        editfullname: $("#editfullName"),
        editusername: $("#editusername"),
        editemail: $("#editemail"),
        editroles: $("#editroles"),
        editjoinDate: $("#editjoinDate"),
        btnupdate: $("#btnupdate"),
        editModal: $("#editUserModal"),

    },

    ajax: {
        addUser: function (data) {
            return page.util.ajaxRequestParam(route.ADD_USER, method.POST, data)
        },

        loadDataTable: function () {
            page.table = page.selector.usertable.DataTable({
                processing: true,
                serverSide: true,
                orderCellsTop: true,
                ordering: false,
                ajax: {
                    url: route.USER_TABLE,
                    type: "POST",
                    contentType: "application/json",
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                },

                columns: [
                    {
                        data: null,
                        className: "text-center",
                        render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                        }
                    },
                    { data: "id" },
                    {
                        data: "fullname",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-2-lines" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "username",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-2-lines" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "email",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-2-lines" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "roles",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-2-lines" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "joinDate",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-2-lines" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: null,
                        orderable: false,
                        searchable: false,
                        className: "text-center",
                        render: function (data, type, row) {
                            return `
                                <button class="btn btn-sm btn-outline-info editbtn" data-bs-toggle="modal" data-bs-target="#editUserModal">Edit</button>
                                <button class="btn btn-sm btn-outline-danger deletebtn">Delete</button>
                            `;
                        }
                    }
                ]
            });
        },

        editUser: function (id) {
            return page.util.ajaxRequestNoParam(route.EDIT_USER + "/" + id, method.GET)
        },

        updateUser: function (data) {
            return page.util.ajaxRequestParam(route.UPDATE_USER, method.POST, data)
        },

        deleteUser: function (id) {
            return page.util.ajaxRequestNoParam(route.DELETE_USER  + "/" + id, method.POST)
        },

        allClearInput: function () {
            page.selector.fullname.val("");
            page.selector.username.val("");
            page.selector.email.val("");
            page.selector.roles.val("");
            page.selector.joinDate.val("");
            page.id = "null"

        }
    },

    fire: {
        OnClickaddUser: function () {
            page.selector.btnAdd.on('click', function (e) {
                e.preventDefault();
                $.confirm({
                    title: "",
                    content: "Are you sure?",
                    type: "dark",
                    buttons: {
                        cancle: {
                            btnClass: "btn-default",
                        },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                var newUser = {
                                    fullname: page.selector.fullname.val(),
                                    username: page.selector.username.val(),
                                    email: page.selector.email.val(),
                                    joinDate: page.selector.joinDate.val(),
                                    roles: page.selector.roles.val(),
                                }
                                page.ajax.addUser(newUser).then((res) => {
                                    if (res.success) {
                                        sweetAlert2Util.saveSuccess();
                                        page.table.ajax.reload();
                                        page.selector.createModal.modal("hide");

                                        page.selector.fullname.val("");
                                        page.selector.username.val("");
                                        page.selector.email.val("");
                                        page.selector.roles.val("");
                                        page.selector.joinDate.val("");
                                    } else {
                                        console.log("error adding user");
                                    }
                                });
                            },
                        },
                    },
                });
            });
        },

        OnClickEditUser: function () {
            $('body').on('click', '.editbtn', function (e) {
                e.preventDefault();
                var d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;
                page.ajax.editUser(d.id).then((res) => {
                    if (res && res.fullname) {
                        page.selector.editfullname.val(res.fullname || "");
                        page.selector.editemail.val(res.email || "");
                        page.selector.editusername.val(res.username || "");
                        page.selector.editjoinDate.val(res.joinDate || "");
                        page.selector.editroles.val(res.roles || "");

                        try {
                            var modalEl = document.getElementById('editUserModal');
                            var modal = new bootstrap.Modal(modalEl);
                            modal.show();
                        } catch (e) {
                            console.log("Error getting data", e);
                        }
                    } else {
                        console.log("Error fetching user data");
                    }
                });
            });
        },

        OnClickUpdateUser: function () {
            page.selector.btnupdate.on('click', function (e) {
                e.preventDefault();
                $.confirm({
                    title: "",
                    content: "Are you Sure?",
                    type: "dark",
                    buttons: {
                        cancel: {
                            btnClass: "btn-default",
                        },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                var updatedUser = {
                                    id: page.id,
                                    fullname: page.selector.editfullname.val(),
                                    username: page.selector.editusername.val(),
                                    email: page.selector.editemail.val(),
                                    joinDate: page.selector.editjoinDate.val(),
                                    roles: page.selector.editroles.val(),
                                };

                                page.ajax.updateUser(updatedUser).then((res) => {
                                    if (res.success) {
                                        page.selector.editModal.modal("hide");
                                        $('.modal-backdrop').remove();
                                        $('body').removeClass('modal-open');
                                        page.table.ajax.reload();
                                        sweetAlert2Util.updateSuccess();
                                    } else {
                                        console.log("Error updating user");
                                    }
                                });
                            },
                        },
                    },
                });
            });
        },

        OnClickDeleteUser: function () {
            $('body').on('click', '.deletebtn', function (e) {
                e.preventDefault();
                var d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;
                $.confirm ({
                    title: "",
                    content: "Are you sure?",
                    type: "dark",
                    buttons: {
                        cancel: {
                            btnClass: "btn-default",
                        },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                page.ajax.deleteUser(d.id).then((res) => {
                                    if(res.success) {
                                        sweetAlert2Util.deleteSuccess();
                                        page.table.ajax.reload();
                                    } else {
                                        sweetAlert2Util.errorWithMessage(res.code, res.message);
                                    }
                                });
                            },
                        },
                    },
                });
            });
        },
    },

    util: {
        ajaxRequestParam: function (route, method, data) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: route,
                    type: method,
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    dataType: "json",
                    cache: false,
                    timeout: 600000,
                    success: function (res) {
                        resolve(res)
                    },
                    error: function (e) {
                        reject(e)
                    }
                });
            }).finally(() => {

            }).catch((err) => {
                console.log(err);
            });
        },

        ajaxRequestNoParam: function (route, method) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: route,
                    type: method,
                    contentType: "application/json",
                    dataType: "json",
                    caches: false,
                    timeout: 600000,
                    success: function (res) {
                        resolve(res);
                    },
                    error: function (e) {
                        reject(e);
                    }
                });
            });
        },

    },

    initView: function () {

    },

    initData: function () {
        page.ajax.loadDataTable();
    },

    initEvent: function () {
        page.fire.OnClickaddUser();
        page.fire.OnClickEditUser();
        page.fire.OnClickUpdateUser();
        page.fire.OnClickDeleteUser();
    },

    init: function () {
        page.initView();
        page.initData();
        page.initEvent();
    }
};

$(document).ready(function () {
    page.init();
});

