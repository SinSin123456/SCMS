const method = {
    GET: "GET",
    POST: "POST",
}

const route = {
    TEACHERDATA: "/scms/admin-assignsubject/teacher",
    MAJORSDATA: "/scms/admin-assignsubject/getmajors",
    SUBJECTSDATA: "/scms/admin-assignsubject/getsubjects",
    DAYSDATA: "/scms/admin-assignsubject/getday",
    TIMESLOTDATA: "/scms/admin-assignsubject/gettimeslots"
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
        loadTeacherData: function () {
            $.ajax({
                url: route.TEACHERDATA,
                method: 'GET',
                success: function (teachers) {
                    let $teacherSelect = $("#teacherName");
                    $teacherSelect.empty().append('<option value="">Select Teacher</option>');
                    teachers.forEach(p => {
                        $teacherSelect.append(`<option value="${p.id}">${p.fullName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load Teachers", err);
                }
            });
        },

        loadDataMajors: function () {
            $.ajax({
                url: route.MAJORSDATA,
                method: 'GET',
                success: function (majors) {
                    let $majorSelect = $("#teachermajor");
                    $majorSelect.empty().append('<option value="">Select Major</option>');
                    majors.forEach(p => {
                        $majorSelect.append(`<option value="${p.id}">${p.majorName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load Majors", err);
                }
            });
        },

        loadDataSubjects: function () {
            $.ajax({
                url: route.SUBJECTSDATA,
                method: 'GET',
                success: function (subjects) {
                    let $subjectSelect = $("#teachersubject");
                    $subjectSelect.empty().append('<option value="">Select Subejct</option>');
                    subjects.forEach(p => {
                        $subjectSelect.append(`<option value="${p.id}">${p.subjectName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load Subjects", err);
                }
            });
        },

        loadDataDay: function () {
            $.ajax({
                url: route.DAYSDATA,
                method: 'GET',
                success: function (days) {
                    let $daySelect = $("#teacherday");
                    $daySelect.empty().append('<option value="">Select Day</option>');
                    days.forEach(p => {
                        $daySelect.append(`<option value="${p.id}">${p.dayName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load Days", err);
                }
            });
        },

        loadDataTimeSlot: function () {
            $.ajax({
                url: route.TIMESLOTDATA,
                method: 'GET',
                success: function (timeslots) {
                    let $timeslotSelect = $("#teachertimeslot");
                    $timeslotSelect.empty().append('<option value ="">Select TimeSlot</option>');
                    timeslots.forEach(p => {
                        $timeslotSelect.append(`<option value="${p.id}">${p.slotName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load TimeSlots", err);
                }
            });
        },
    },

    fire: {

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
        page.ajax.loadTeacherData();
        page.ajax.loadDataMajors();
        page.ajax.loadDataSubjects();
        page.ajax.loadDataDay();
        page.ajax.loadDataTimeSlot();
    },

    initEvent: function () {

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

