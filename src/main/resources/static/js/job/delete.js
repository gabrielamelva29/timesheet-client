/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function deleteReg(id,ids) {
    $("#deleteForm").attr("class", "delete-" + id);
    event.preventDefault();
    const urlAct = "/job/" + id+"/"+ids;

    $.ajax({
        url: urlAct,
        type: 'POST',
        dataType: 'json',
        data: $('.delete-' + id).serialize(),
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

function deleteById(id,ids) {
    event.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteReg(id,ids);
            successMessageDelete("Deleted was Successfully",ids);
            
        }
    });
}

function successMessageDelete(berhasil,ids) {
    Swal.fire({
        icon: 'success',
        title: berhasil
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = ids;
        }
    });
}

