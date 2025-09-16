const method = {
    GET: "GET",
    POST: "POST",
}

const route = {
    ADD_STUDENT: context + "/scms/admin-student/addstudent",
    STUDENT_TABLE: context + "/scms/admin-student/studenttable",
    EDIT_STUDENT: context + "/scms/admin-student/editstudent",
    UPDATE_STUDENT: context + "/scms/admin-student/updatestudent",
    DELETE_STUDENT: context + "/scms/admin-student/deletestudent",
}

const page = {
    id: "null",
    table: "null",
    selector: {
        name: $("#studentName"),
        sex: $("#studentSex"),
        email: $("#studentEmail"),
        phone: $("#studentPhone"),
        studentsubject: $("#studentSubject"),
        class: $("#studentClass"),
        registerDate: $("#registerDate"),
        createModal: $("#addStudentModal"),
        btnSave: $("#btnsavestudent"),
        studentTable: $("#studentTable"),
        editname: $("#editstudentName"),
        editsex: $("#editstudentSex"),
        editemail: $("#editstudentEmail"),
        editphone: $("#editstudentPhone"),
        editstudentsubject: $("#editstudentSubject"),
        editclass: $("#editstudentClass"),
        editregisterDate: $("#editregisterDate"),
        editModal: $("#editStudentModal"),
        btnUpdate: $("#btnupdatestudent"),
    },

    ajax: {
        addStudent: function (data) {
            return page.util.ajaxRequestParam(route.ADD_STUDENT, method.POST, data);
        },

        loadDataTable: function () {
            page.table = page.selector.studentTable.DataTable({
                processing: true,
                serverSide: true,
                responsive: true,
                autoWidth: false,
                orderCellsTop: true,
                ordering: false,
                ajax: {
                    url: route.STUDENT_TABLE,
                    type: 'POST',
                    contentType: "application/json",
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                },
                columns: [
                    { data: "id", className: "text-center" },
                    {
                        data: "fullName",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-2" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "sex",
                        className: "text-center",
                        render: function (data) {
                            return `<span>${data}</span>`;
                        }
                    },
                    {
                        data: "email",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-1" title="${data}">${data}</span>`;
                        }
                    },

                    {
                        data: "phone",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-1" title="${data}">${data}</span>`;
                        }
                    },



                    {
                        data: "subjectNames",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-2" title="${data}">${data}</span>`;
                        }
                    },

                    {
                        data: "className",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-2" title="${data}">${data}</span>`;
                        }
                    },
                    {
                        data: "registerDate",
                        className: "text-center",
                        render: function (data) {
                            const date = new Date(data);
                            const year = date.getFullYear();
                            const month = String(date.getMonth() + 1).padStart(2, '0');
                            const day = String(date.getDate()).padStart(2, '0');
                            return `${year}-${month}-${day}`;
                        }
                    },

                    {
                        data: null,
                        orderable: false,
                        searchable: false,
                        className: "text-center",
                        render: function () {
                            return `
                               <button class="btn btn-sm btn-outline-info editstudentbtn" data-bs-toggle="modal" data-bs-target="#editStudentModal">Edit</button>
                                <button class="btn btn-sm btn-outline-danger deletestudentbtn">Delete</button>
                            `;
                        }
                    }
                ]
            });
        },

        editStudent: function (id) {
            return page.util.ajaxRequestNoParam(route.EDIT_STUDENT + "/" + id, method.GET);
        },

        updateStudent: function (data) {
            return page.util.ajaxRequestParam(route.UPDATE_STUDENT, method.POST, data);
        },

        deleteStudent: function (id) {
            return page.util.ajaxRequestNoParam(route.DELETE_STUDENT + "/" + id, method.POST);
        },
    },

    fire: {
        OnClickAddStudent: function () {
            page.selector.btnSave.on('click', function (e) {
                e.preventDefault();
                $.confirm({
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
                                var newstudent = {
                                    fullName: page.selector.name.val(),
                                    sex: page.selector.sex.val(),
                                    email: page.selector.email.val(),
                                    phone: page.selector.phone.val(),
                                    subjectNames: page.selector.studentsubject.val().split(',').map(s => s.trim()),
                                    className: page.selector.class.val(),
                                    registerDate: page.selector.registerDate.val(),
                                };

                                page.ajax.addStudent(newstudent).then((res) => {
                                    if (res.success) {
                                        sweetAlert2Util.saveSuccess();
                                        page.table.ajax.reload();
                                        page.selector.createModal.modal("hide");
                                        $('#addStudentModal form')[0].reset();
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

        OnClickEditStudent: function () {
            $('body').on('click', '.editstudentbtn', function (e) {
                e.preventDefault();
                var d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;
                page.ajax.editStudent(d.id).then((res) => {
                    if (res && res.fullName) {
                        page.selector.editname.val(res.fullName || "");
                        page.selector.editsex.val(res.sex || "");
                        page.selector.editemail.val(res.email || "");
                        page.selector.editphone.val(res.phone || "");
                        if (res.registerDate) {
                            const date = new Date(res.registerDate);

                            const formattedDate = date.toISOString().split('T')[0];
                            page.selector.editregisterDate.val(formattedDate);
                        } else {
                            page.selector.editregisterDate.val("");
                        }

                        page.selector.editstudentsubject.val(res.subjectNames.join(", "));
                        page.selector.editclass.val(res.className || "");

                        try {
                            var modalEl = document.getElementById('editStudentModal');
                            var modal = new bootstrap.Modal(modalEl);
                            modal.show();
                        } catch (e) {
                            console.log("Error getting data", e);
                        }

                    } else {
                        sweetAlert2Util.errorWithMessage("No Data Found", "No data found for the selected student.");
                    }

                });
            });
        },

        OnClickUpdateStudent: function () {
            page.selector.btnUpdate.on('click', function (e) {
                e.preventDefault();

                $.confirm({
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
                                // Prepare payload
                                var updateStudent = {
                                    id: page.id,
                                    fullName: page.selector.editname.val(),
                                    sex: page.selector.editsex.val(),
                                    email: page.selector.editemail.val(),
                                    phone: page.selector.editphone.val(),
                                    subjectNames: page.selector.editstudentsubject.val()
                                        .split(',')
                                        .map(s => s.trim())
                                        .filter(s => s.length > 0), // remove empty strings
                                    className: page.selector.editclass.val(),
                                    registerDate: page.selector.editregisterDate.val(),
                                    status: true
                                };

                                // Call AJAX POST
                                page.ajax.updateStudent(updateStudent)
                                    .then((res) => {
                                        if (res.success) {
                                            // Close modal properly
                                            var modalEl = document.getElementById('editStudentModal');
                                            var modal = bootstrap.Modal.getInstance(modalEl);
                                            if (modal) modal.hide();

                                            $('.modal-backdrop').remove();
                                            $('body').removeClass('modal-open');

                                            // Reload datatable
                                            page.table.ajax.reload(null, false);

                                            sweetAlert2Util.updateSuccess();
                                        } else {
                                            sweetAlert2Util.errorWithMessage(res.code, res.message);
                                        }
                                    })
                                    .catch(err => {
                                        console.log("Update error:", err);
                                        sweetAlert2Util.errorWithMessage("Error", "Failed to update student.");
                                    });
                            }
                        }
                    }
                });
            });
        },

        OnClickDeleteStudent: function () {
            $('body').on('click', '.deletestudentbtn', function (e) {
                e.preventDefault();
                var d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;
                $.confirm({
                    title: "",
                    content: "Are you sure?",
                    type: "dark",
                    buttons: {
                        cancel: {
                            btnClass: "btn-default"
                        },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                page.ajax.deleteStudent(d.id).then((res) => {
                                    // console.log("Delete Response:", res);
                                    if (res) {
                                        sweetAlert2Util.deleteSuccess();
                                        page.table.ajax.reload();
                                    } else {
                                        sweetAlert2Util.errorWithMessage("Delete failed");
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
        page.fire.OnClickAddStudent();
        page.fire.OnClickEditStudent();
        page.fire.OnClickUpdateStudent();
        page.fire.OnClickDeleteStudent();
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

