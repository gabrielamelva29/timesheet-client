$(document).ready(() => {
    load();
});
$("#submitEmployee").on("click", function () {
    event.preventDefault();
    const data = {
        projectName: $('#projectName').val(),
        divisi: $('#divisi').val(),
        name: $('#name').val(),
        miiId: $('#miiId').val()
    };
    const urlAct = $('#jobForm').attr('action');
    console.log(JSON.stringify(data));
    $.ajax({
        url: urlAct,
        type: 'POST',
        data: $('#jobForm').serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        dataType: 'json',
        success: (result) => {
            console.log("benar");
            console.log(result.message);
            if (result.message == null) {
                successMessage(" Updated");
            } else {
                successMessage(result.message);
            }
//            load();
//            $("#createEmployeeAjax").modal("hide");
        },
        error: (err) => {
            console.log("salah");
            $('#errorProjectName').html("Please input your projectName !");
            $('#errorProjectName').show();
            $('#errorDivisi').html("Please input your divisi !");
            $('#errorDivisi').show('slow');
            $('#errorName').html("Please input name !");
            $('#errorName').show('slow');
            $('#errorMiiId').html("Please input your miiId !");
            $('#errorPhoneNumber').show('slow');
            console.log(err);
        }
    });
});