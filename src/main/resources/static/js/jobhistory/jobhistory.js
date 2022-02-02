$(document).ready(() => {
    const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.get('created') == 'false') {
        failMessage("This Month is Already");
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

function send(id) {
    $("#sentForm").attr("class", "sent-" + id);
    event.preventDefault();
    const urlAct = "/sent/" + id;

    $.ajax({
        url: urlAct,
        type: 'POST',
        dataType: 'json',
        data: $('.sent-' + id).serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        success: (result) => {
            console.log("berhasil");
            successMessage("Send was Successfully")

        },
        error: (err) => {
            console.log(err);
            failMessage("This Period Must be Full");
        }
    });
}

function confirmationSend(id) {
    event.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, sent it!'
    }).then((result) => {
        if (result.isConfirmed) {
            send(id);
        }
    });
}

function approved(id) {
    $("#approveForm").attr("class", "approve-" + id);
    event.preventDefault();
    const urlAct = "/approved/" + id;

    $.ajax({
        url: urlAct,
        type: 'POST',
        dataType: 'json',
        data: $('.approve-' + id).serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        success: (result) => {

        },
        error: (err) => {
            console.log(err);
        }
    });
}

function confirmationApproved(id) {
    event.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, approve it!'
    }).then((result) => {
        if (result.isConfirmed) {
            approved(id);
            successMessageReject("Approved was Successfully");
        }
    });
}

function reject(id) {
    $("#rejectForm").attr("class", "reject-" + id);
    event.preventDefault();
    const urlAct = "/rejected/" + id;

    $.ajax({
        url: urlAct,
        type: 'POST',
        dataType: 'json',
        data: $('.reject-' + id).serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        success: (result) => {

        },
        error: (err) => {
            console.log(err);
        }
    });
}


function confirmationReject(id) {
    event.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, reject it!'
    }).then((result) => {
        if (result.isConfirmed) {
            reject(id);
            successMessageReject("Rejected was Successfully")
        }
    });
}

function successMessageReject(berhasil) {
    Swal.fire({
        icon: 'success',
        title: berhasil
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "hr";
        }
    });
}
