$(document).ready(() => {
    const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.get('created') == 'false') {
        failMessage("This Month is Already");
    }
    if (urlParams.get('sent') == 'false') {
        failMessage("This Period Must be Full");
    }
    if (urlParams.get('sent') == 'true') {
        successMessage("This Period was Successfully Sent");
    }
});


function failMessage(gagal) {
    Swal.fire({
        icon: 'error',
        title: gagal
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "history";
        }
    });
}

function successMessage(berhasil) {
    Swal.fire({
        icon: 'success',
        title: berhasil
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "history";
        }
    });
}
