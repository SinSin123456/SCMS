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
    ALL_STUDENTS: context + "/scms/admin-student/students",
    GET_MAJORS: context + "/scms/admin-student/majors",
    GET_YEARS: context + "/scms/admin-student/year"
}

const page = {
    id: "null",
    table: "null",
    selector: {
        name: $("#StudentName"),
        sex: $("#studentSex"),
        phone: $("#studentPhone"),
        studentMajor: $("#studentMajor"),
        year: $("#studentYear"),
        registerDate: $("#registerDate"),
        createModal: $("#addStudentModal"),
        btnSave: $("#btnsavestudent"),
        studentTable: $("#studentTable"),
        editname: $("#editStudentName"),
        editsex: $("#editstudentSex"),
        editphone: $("#editstudentPhone"),
        edityear: $("#editStudentYear"),
        editstudentMajor: $("#editStudentMajor"),
        editregisterDate: $("#editregisterDate"),
        btnUpdate: $("#btnupdatestudent"),
        editModal: $("#editStudentModal")
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
                        data: "phone",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-1" title="${data}">${data}</span>`;
                        }
                    },

                    {
                        data: "majorName",
                        className: "text-center",
                        render: function (data) {
                            return `<span class="text-truncate-2" title="${data}">${data}</span>`;
                        }
                    },

                    {
                        data: "yearName",
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

        loadDataAllStudents: function () {
            $.ajax({
                url: route.ALL_STUDENTS,
                method: 'GET',
                success: function (students) {
                    let $studentSelect = $("#StudentName");
                    $studentSelect.empty().append('<option value="">Select Student</option>');
                    students.forEach(p => {
                        $studentSelect.append(`<option value="${p.id}">${p.fullName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load students", err);
                }
            });
        },

        loadDataEditAllStudents: function () {
            $.ajax({
                url: route.ALL_STUDENTS,
                method: 'GET',
                success: function (students) {
                    let $studentSelect = $("#editStudentName");
                    $studentSelect.empty().append('<option value="">-- Select Student --</option>');
                    students.forEach(p => {
                        $studentSelect.append(`<option value="${p.id}">${p.fullName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load students", err);
                }
            });
        },

        loadDataMajors: function () {

            $.ajax({
                url: route.GET_MAJORS,
                method: 'GET',
                success: function (majors) {
                    let $majorSelect = $("#studentMajor");
                    $majorSelect.empty().append('<option value="">-- Select Major --</option>');
                    majors.forEach(p => {
                        $majorSelect.append(`<option value="${p.id}">${p.majorName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load majors", err);
                }
            });
        },

        loadEditDataMajors: function () {
            $.ajax({
                url: route.GET_MAJORS,
                method: 'GET',
                success: function (majors) {
                    let $majorSelect = $("#editStudentMajor");
                    $majorSelect.empty().append('<option value="">-- Select Major --</option>');
                    majors.forEach(p => {
                        $majorSelect.append(`<option value="${p.id}">${p.majorName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load majors", err);
                }
            });
        },

        loadDataAllYear: function () {
            $.ajax({
                url: route.GET_YEARS,
                method: 'GET',
                success: function (years) {
                    let $yearSelect = $("#studentYear");
                    $yearSelect.empty().append('<option value="">-- Select Year --</option>');
                    years.forEach(p => {
                        $yearSelect.append(`<option value="${p.id}">${p.yearName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load years", err);
                }
            });
        },

        loadEditDataAllYear: function () {
            $.ajax({
                url: route.GET_YEARS,
                method: 'GET',
                success: function (years) {
                    let $yearSelect = $("#editStudentYear");
                    $yearSelect.empty().append('<option value="">-- Select Year --</option>');
                    years.forEach(p => {
                        $yearSelect.append(`<option value="${p.id}">${p.yearName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load years", err);
                }
            });
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
                        cancel: { btnClass: "btn-default" },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                const userIdVal = page.selector.name.val();
                                const sexVal = page.selector.sex.val();
                                const phoneVal = page.selector.phone.val();
                                const yearIdVal = page.selector.year.val();


                                let majorVal = [];
                                page.selector.studentMajor.find('option:selected').each(function () {
                                    majorVal.push($(this).val());
                                });

                                if (!userIdVal) {
                                    sweetAlert2Util.errorWithMessage("", "Please select a student");
                                    return;
                                }
                                if (!yearIdVal) {
                                    sweetAlert2Util.errorWithMessage("", "Please select a year");
                                    return;
                                }

                                const newStudent = {
                                    userId: userIdVal,
                                    sex: sexVal,
                                    phone: phoneVal,
                                    yearId: yearIdVal,
                                    majorName: majorVal, // now an array
                                    registerDate: page.selector.registerDate.val()
                                        ? new Date(page.selector.registerDate.val()).toISOString()
                                        : null
                                };

                                console.log('Sending data:', newStudent);

                                page.ajax.addStudent(newStudent)
                                    .then((res) => {
                                        if (res.success) {
                                            sweetAlert2Util.saveSuccess();
                                            page.table.ajax.reload();
                                            page.selector.createModal.modal("hide");
                                            $('#addStudentModal form')[0].reset();
                                        } else {
                                            sweetAlert2Util.errorWithMessage(res.code, res.message);
                                        }
                                    })
                                    .catch((err) => {
                                        console.error(err);
                                        sweetAlert2Util.errorWithMessage("", "Server error occurred");
                                    });
                            }
                        }
                    }
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
                        // page.selector.editemail.val(res.email || "");
                        page.selector.editphone.val(res.phone || "");
                        if (res.registerDate) {
                            const date = new Date(res.registerDate);

                            const formattedDate = date.toISOString().split('T')[0];
                            page.selector.editregisterDate.val(formattedDate);
                        } else {
                            page.selector.editregisterDate.val("");
                        }

                        // page.selector.editstudentsubject.val(res.subjectNames.join(", "));
                        page.selector.edityear.val(res.yearName || "");

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
                    content: "Are you sure you want to update this student?",
                    type: "dark",
                    buttons: {
                        cancel: { btnClass: "btn-default" },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                const studentIdVal = page.id;
                                const studentNameVal = page.selector.editname.find('option:selected').text().trim();
                                const userIdVal = page.selector.editname.val();
                                const sexVal = page.selector.editsex.val();
                                const phoneVal = page.selector.editphone.val();
                                const yearIdVal = page.selector.edityear.val();
                                let majorVal = page.selector.editstudentMajor.val() || [];

                                if (!Array.isArray(majorVal)) majorVal = [majorVal];

                                if (!studentIdVal) {
                                    sweetAlert2Util.errorWithMessage("", "Student ID is missing");
                                    return;
                                }
                                if (!userIdVal) {
                                    sweetAlert2Util.errorWithMessage("", "Please select a student");
                                    return;
                                }
                                if (!yearIdVal) {
                                    sweetAlert2Util.errorWithMessage("", "Please select a year");
                                    return;
                                }

                                // Convert selected majors to Long IDs
                                const majorIdVal = majorVal
                                    .filter(v => v)
                                    .map(v => parseInt(v.trim()));

                                const updateStudentData = {
                                    studentId: studentIdVal,
                                    fullName: studentNameVal,
                                    userId: parseInt(userIdVal),
                                    sex: sexVal || null,
                                    phone: phoneVal || null,
                                    yearId: yearIdVal ? parseInt(yearIdVal) : null,
                                    majorId: majorIdVal,
                                    registerDate: page.selector.editregisterDate.val()
                                };

                                console.log('Updating student data:', updateStudentData);

                                page.ajax.updateStudent(updateStudentData)
                                    .then((res) => {
                                        if (res.success) {
                                            sweetAlert2Util.updateSuccess();
                                            page.table.ajax.reload();
                                            page.selector.editModal.modal("hide");
                                        } else {
                                            sweetAlert2Util.errorWithMessage(res.code, res.message);
                                        }
                                    })
                                    .catch((err) => {
                                        console.error(err);
                                        sweetAlert2Util.errorWithMessage("", "Server error occurred");
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
                            btnClass: "btn-default",
                        },
                        confirm: {
                            btnClass: "btn-blue",
                            action: function () {
                                console.log(d.id);
                                page.ajax.deleteStudent(d.id).then((res) => {
                                    if (res.success) {
                                        try {
                                            sweetAlert2Util.deleteSuccess();
                                            page.table.ajax.reload();
                                        } catch (e) {
                                            console.log("Error deleting data", e);
                                        }
                                    } else {
                                        console.log("Failed to delete");
                                    }
                                })
                            }
                        }
                    }
                })
            })
        }

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
        page.ajax.loadDataAllStudents();
        page.ajax.loadDataEditAllStudents();
        page.ajax.loadDataMajors();
        page.ajax.loadEditDataMajors();
        page.ajax.loadDataAllYear();
        page.ajax.loadEditDataAllYear();
    },

    initEvent: function () {
        page.fire.OnClickAddStudent();

        $('#addStudentModal').on('show.bs.modal', function () {
            page.ajax.loadDataAllStudents();
            page.ajax.loadDataMajors();
            page.ajax.loadDataAllYear();
        });

        $('#editStudentModal').on('show.bs.modal', function () {
            page.ajax.loadDataEditAllStudents();
            page.ajax.loadEditDataMajors();
            page.ajax.loadEditDataAllYear();
        });

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

