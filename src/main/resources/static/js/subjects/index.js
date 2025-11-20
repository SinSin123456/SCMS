const method = {
    GET: "GET",
    POST: "POST",
}

const route = {
    TEACHERDATA: "/scms/admin-assignsubject/teacher",
    MAJORSDATA: "/scms/admin-assignsubject/getmajors",
    SUBJECTSDATA: "/scms/admin-assignsubject/getsubjects",
    DAYSDATA: "/scms/admin-assignsubject/getday",
    TIMESLOTDATA: "/scms/admin-assignsubject/gettimeslots",
    ROOMSDATA: "/scms/admin-assignsubject/getroom",
    YEARDATA: "/scms/admin-assignsubject/getyear",
    ADDASSIGN: "/scms/admin-assignsubject/addassignsubject"
}

const page = {
    id: "null",
    table: "null",
    selector: {
        teacherName: $("#teacherName"),
        major: $("#teachermajor"),
        subject: $("#teachersubject"),
        day: $("#teacherday"),
        timeSlot: $("#teachertimeslot"),
        room: $("#teacherroom"),
        term: $("#term"),
        year: $("#teacheryear"),
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
                    console.log("TEACHER API RESPONSE:", teachers);
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

        loadDataRooms: function () {
            $.ajax({
                url: route.ROOMSDATA,
                method: 'GET',
                success: function (rooms) {
                    let $roomslotSelect = $("#teacherroom");
                    $roomslotSelect.empty().append('<option value ="">Select Room</option>');
                    rooms.forEach(p => {
                        $roomslotSelect.append(`<option value="${p.id}">${p.roomName}</option>`);
                    });
                },
                error: function (err) {
                    console.error("Failed to load Rooms", err);
                }
            });
        },

        loadDataYear: function () {
            $.ajax({
                url: route.YEARDATA,
                method: 'Get',
                success: function (years) {
                    let $yearSelect = $("#teacheryear");
                    $yearSelect.empty().append('<option value ="">Select Year</option>');
                    years.forEach(p => {
                        $yearSelect.append(`<option value="${p.id}">${p.yearName}</option>`)
                    });
                },
                error: function (err) {
                    console.error("Failed to load Years", err);
                }
            });
        },

        addAssing: function (data) {
            return page.util.ajaxRequestParam(route.ADDASSIGN, method.POST, data);
        }
    },

    fire: {
        OnClickAddAssign: function () {
            page.selector.btnsavesujects.on('click', function (e) {
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
                                const teacherId = page.selector.teacherName.val();
                                const roomId = page.selector.room.val();
                                const dayId = page.selector.day.val();
                                const timeSlotId = page.selector.timeSlot.val();
                                const subjectId = page.selector.subject.val();
                                const yearId = page.selector.year.val();
                                const majorId = page.selector.major.val();
                                const term = page.selector.term.val();

                                var newAssign = {
                                    teacherId: teacherId,
                                    roomId: roomId,
                                    dayId: dayId,
                                    timeSlotId: timeSlotId,
                                    subjectId: subjectId,
                                    yearId: yearId,
                                    majorId: majorId,
                                    term: term
                                }
                                page.ajax.addAssing(newAssign).then((res) => {
                                    if (res.success) {
                                        sweetAlert2Util.saveSuccess();
                                        page.table.ajax.reload();
                                        page.selector.createassignModal.modal("hide");
                                    } else {
                                        console.log("cannnnnn not adding");
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
        page.ajax.loadTeacherData();
        page.ajax.loadDataMajors();
        page.ajax.loadDataSubjects();
        page.ajax.loadDataDay();
        page.ajax.loadDataTimeSlot();
        page.ajax.loadDataRooms();
        page.ajax.loadDataYear();
    },

    initEvent: function () {
        page.fire.OnClickAddAssign();
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

