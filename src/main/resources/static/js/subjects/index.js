const method = {
    GET: "GET",
    POST: "POST",
}

const route = {
    ADD_ASSIGNSUB: context + "/scms/admin-assignsubject/addAssignSub",
    GET_SUBJECT: context + "/scms/admin-assignsubject/subjects",
    GET_PERSON: context + "/scms/admin-assignsubject/persons",
    DATA_TABLE: context + "/scms/admin-assignsubject/listAssignSubjects",
    GET_ASSIGN_SUBJECTS: context + "/scms/admin-assignsubject/listAssignSubjects",
    EDIT_ASSIGN: context + "/scms/admin-assignsubject/editassign",
    UPDATE_ASSIGN: context + "/scms/admin-assignsubject/updateassignsub",
    DELETE_ASSIGN: context + "/scms/admin-assignsubject/deleteassignsub"
}

const page = {
    id: "null",
    table: "null",
    selector: {
        personName: $("#person"),
        role: $("#role"),
        subject: $("#subject"),
        term: $("#term"),
        btnsavesujects: $("#btnsavesujects"),
        createassignModal: $("#assignModal"),
        subjectsTable: $("#subjectsTable"),
        editpersonName: $("#editperson"),
        editrole: $("#editrole"),
        editsubject: $("#editsubject"),
        editerm: $("#editterm"),
        btnupdateassign: $("#btnupdatesujects"),
        editassingModal: $("#editassignModal")


    },

    ajax: {
        addAssignSub: function (data) {
            return page.util.ajaxRequestParam(route.ADD_ASSIGNSUB, method.POST, data);
        },

        loadSubjects: function () {
            $.ajax({
                url: route.GET_SUBJECT,
                method: 'POST',
                success: function (subjects) {
                    let $subjectSelect = $('#subject');
                    $subjectSelect.empty().append('<option value="">Select Subject</option>');
                    subjects.forEach(sub => {
                        $subjectSelect.append(`<option value="${sub.id}">${sub.name}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load subjects:", err);
                }
            });
        },

        loadPersons: function () {
            $.ajax({
                url: route.GET_PERSON,
                method: 'POST',
                success: function (persons) {
                    let $personSelect = $("#person");
                    $personSelect.empty().append('<option value="">Select Person</option>');
                    persons.forEach(p => {
                        $personSelect.append(`<option value="${p.id}">${p.fullName} (${p.role})</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load subjects:", err);
                }
            });
        },

        loadAssignSubjectsTable: function () {
            page.table = page.selector.subjectsTable.DataTable({
                processing: true,
                serverSide: true,
                responsive: true,
                autoWidth: false,
                ordering: false,
                ajax: {
                    url: route.DATA_TABLE,
                    type: 'POST',
                    contentType: 'application/json',
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                    dataSrc: function (res) {
                        if (!res) {
                            console.error("Failed to fetch data:", res);
                            return [];
                        }
                        return res.data;
                    }
                },
                columns: [
                    { data: null, className: "text-center", render: (data, type, row, meta) => meta.row + 1 },
                    // { data: "id", className: "text-center" },
                    {
                        data: null,
                        className: "text-center",
                        render: function (data) {
                            return data.studentName || data.teacherName;
                        }
                    },
                    {
                        data: null,
                        className: "text-center",
                        render: function (data) {
                            return data.studentName ? "STUDENT" : "TEACHER";
                        }
                    },
                    { data: "subjectName", className: "text-center" },
                    { data: "term", className: "text-center" },
                    {
                        data: null,
                        orderable: false,
                        searchable: false,
                        className: "text-center",
                        render: function () {
                            return `
                        <button class="btn btn-sm btn-outline-info editAssignBtn" data-bs-toggle="modal" data-bs-target="#editassignModal">Edit</button>
                        <button class="btn btn-sm btn-outline-danger deleteAssignBtn">Delete</button>
                    `;
                        }
                    }
                ]
            });
        },

        editAssignSub: function (id) {
            return page.util.ajaxRequestNoParam(route.EDIT_ASSIGN + "/" + id, method.GET);
        },

        loadDataEditPerson: function () {
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: route.GET_PERSON,
                    type: 'POST',
                    contentType: 'application/json',
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                    success: function (response) {
                        const persons = response.data || response;
                        const $select = $('#editperson');

                        $select.empty();

                        $select.append($('<option>', { value: '', text: '-- Select Person --' }));

                        persons.forEach(function (person) {
                            if (person && person.id != null && typeof person.fullName === 'string' && person.fullName.trim() !== '') {
                                $select.append($('<option>', {
                                    value: person.id,
                                    text: `${person.fullName} (${person.role})`
                                }));
                            }
                        });

                        resolve($select);
                    },
                    error: function (err) {
                        reject(err);
                    }
                });
            });
        },

        loadDataEditSubject: function () {
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: route.GET_SUBJECT,
                    type: 'POST',
                    contentType: 'application/json',
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                    success: function (response) {
                        const subjects = response.data || response;
                        const $select = $('#editsubject');
                        $select.empty();
                        $select.append($('<option>', { value: '', text: '-- Select Subject --' }));
                        subjects.forEach(function (subject) {
                            if (subject && subject.id != null && typeof subject.name == "string" && subject.name.trim() !== '') {
                                $select.append($('<option>', {
                                    value: subject.id,
                                    text: subject.name
                                }));
                            }
                        });
                        resolve();
                    },
                    error: function (err) {
                        reject(err);
                    }
                });
            });
        },

        updateAssignSubject: function (data) {
            return page.util.ajaxRequestParam(route.UPDATE_ASSIGN, method.POST, data);
        },

        deleteAssignSubject: function (id) {
            return page.util.ajaxRequestNoParam(route.DELETE_ASSIGN + "/" + id, method.POST);
        },

    },

    fire: {
        OnClickAddAssignSub: function () {
            page.selector.btnsavesujects.on('click', function (e) {
                e.preventDefault();

                if (this.checkValidity() === false) {
                    this.reportValidity();
                    return;
                }

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
                                var newdata = {
                                    studentId: $('#person').val(),
                                    teacherId: $('#person').val(),
                                    role: $('#role').val(),
                                    subjectId: $('#subject').val(),
                                    term: $('#term').val()
                                };

                                page.ajax.addAssignSub(newdata).then((res) => {
                                    if (res.success) {
                                        sweetAlert2Util.saveSuccess();
                                        page.table.ajax.reload();
                                        $('#assignModal').modal('hide');
                                        $('#assignForm')[0].reset();
                                    } else {
                                        console.log("error adding data:", res.message);
                                    }
                                });
                            },
                        },
                    },
                });
            });
        },

        OnClickEditAssignSub: function () {
            $('body').on('click', ".editAssignBtn", function (e) {
                e.preventDefault();

                var d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;

                $.when(
                    page.ajax.loadDataEditPerson(),
                    page.ajax.loadDataEditSubject()
                )
                    .then(function () {
                        return page.ajax.editAssignSub(d.id);
                    })
                    .then(function (res) {
                        if (res) {
                            $('#editassignmentId').val(res.id || d.id);
                            $('#editrole').val(res.role);

                            var $personSelect = $('#editperson');
                            if ($personSelect.find("option[value='" + res.personId + "']").length === 0) {
                                $personSelect.append(
                                    $('<option>', {
                                        value: res.personId,
                                        text: res.personName
                                    })
                                );
                            }
                            $personSelect.val(res.personId ? String(res.personId) : "");

                            $('#editsubject').val(res.subjectId ? String(res.subjectId) : "");
                            $('#editterm').val(res.term);

                            $('#editassignModal').modal('show');
                        }
                    })
                    .fail(function (err) {
                        console.error("Error loading edit form data: ", err);
                    });
            });
        },


        OnClickUpdateAssignSub: function () {
            page.selector.btnupdateassign.on('click', function (e) {
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
                                var updatedata = {
                                    studentId: page.selector.editpersonName.val(),
                                    teacherId: page.selector.editpersonName.val(),
                                    role: page.selector.editrole.val(),
                                    subjectId: page.selector.editsubject.val(),
                                    term: page.selector.editerm.val(),
                                    id: page.id
                                };
                                page.ajax.updateAssignSubject(updatedata).then((res) => {
                                    if (res.success) {
                                        // Close modal properly
                                        var modalEl = document.getElementById('editassignModal');
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
                                });
                            },
                        },
                    },
                });
            });
        },

        OnClickDeleteAssignSub: function () {
            $('body').on('click', ".deleteAssignBtn", function (e) {
                e.preventDefault();
                const d = page.table.row($(this).parents("tr")).data();
                page.id = d.id;
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
                                page.ajax.deleteAssignSubject(d.id).then((res) => {
                                    if (res.success) {
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
        page.ajax.loadSubjects();
        page.ajax.loadPersons();
        page.ajax.loadAssignSubjectsTable();
        page.ajax.loadDataEditPerson();
        page.ajax.loadDataEditSubject();
    },

    initEvent: function () {
        page.fire.OnClickAddAssignSub();

        $('#assignModal').on('show.bs.modal', function () {
            page.ajax.loadSubjects();
            page.ajax.loadPersons();
        });

        page.fire.OnClickEditAssignSub();
        page.fire.OnClickUpdateAssignSub();
        page.fire.OnClickDeleteAssignSub();

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

