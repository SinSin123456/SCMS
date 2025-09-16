const sweetAlert2Util = {
    msgConfig: Swal.mixin({
        html: '<p style="font-size:10px;"></p>',
        width: 400,
        showCancelButton: false,
        allowOutsideClick: true,
        allowEscapeKey: true,
        showConfirmButton: true,
        backdrop: true,
    }),
    success: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Transaction Success"
        });
    },
    error: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "error",
            title: "Transaction Error",
        });
    },
    successWithMessage: function (message) {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Transaction Success",
            text: message
        });
    },
    warningWithTitleAndMessage: function (title, message) {
        sweetAlert2Util.msgConfig.fire({
            icon: "warning",
            title: title,
            text: message
        });
    },
    errorWithMessage: function (code, message) {
        sweetAlert2Util.msgConfig.fire({
            icon: "error",
            title: `Transaction Error (${code})`,
            text: message
        });
    },
    successWithTitle: function (msg) {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: msg
        });
    },

    saveSuccess: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Saved Successfully"
        });
    },
    cancelSuccess: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Cancelled Successfully"
        });
    },
    updateSuccess: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Updated Successfully",
        });
       
    },
    deleteSuccess: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Deleted Successfully"
        });
    },
    completeSuccess: function () {
        sweetAlert2Util.msgConfig.fire({
            icon: "success",
            title: "Completed Task"
        });
    }
}