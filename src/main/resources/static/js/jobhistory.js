$(document).ready(() => {
    const urlParams = new URLSearchParams(window.location.search);

    window.console.log(window.location.search);
    window.console.log(urlParams.get('created'));
    if (urlParams.get('created') == 'false') {
        successMessage("This Month is Already");
    }
});


function successMessage(berhasil) {
    Swal.fire({
        icon: 'error',
        title: berhasil
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "history";
        }
    });
}
function create() {
    event.preventDefault();
    const urlAct = "/history/add";

    $.ajax({
        url: urlAct,
        type: 'POST',
        dataType: 'json',
        data: $('#jobForm').serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        success: (result) => {
            window.location.href = "add-form-employee.html";
        },
        error: (err) => {
            console.log(err);
        }
    });
}
