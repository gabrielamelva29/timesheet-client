function load(id) {
    var rtable = $('#status');
    $.ajax({
        url: '/history/'+id,
        type: 'GET',
        data: 'JSON',
        success: function (data) {
            rtable.destroy();
            $('#status').dataTable({
                "data": data,
                columns: [
                    {'data': 'id', render: function (data, type, row, meta) {
                            return meta.row + 1;
                        }},
                    {'data': 'name', render: function (data, type, row, meta) {
                            return row.firstName + " " + row.lastName;
                        }},
                    {'data': 'email'},
                    {'data': 'job.title'},
                    {'data': 'department.name'},
                    {'data': 'department.manager.firstName'},
                    {'data': 'id', render: function (data, type, row, meta) {
                            var updateButton = `<a data-bs-toggle="modal" data-bs-target="#createEmployeeAjax" class="btn btn-warning btn-sm update-region me-3" onclick="update(${row.id}, '${row.firstName}', '${row.lastName}', '${row.email}', '${row.phoneNumber}', '${row.hireDate}', '${row.salary}', '${row.commissionPct}', '${row.job.id}', '${row.department.id}', '${row.department.manager.id}')">Update</a>`;
                            var deleteButton = '<form action="/region/' + row.id + '" class="delete-' + row.id + '" "method="POST" id="deleteForm"> \n\
                                            <input type="hidden" name="_method" value="DELETE">\
                                            <button class="btn btn-sm btn-danger" onclick="deleteConfirmation(' + row.id + ')">\
                                            Delete\
                                            </button></form>';
                            return updateButton + deleteButton;
                        }}
                ]
            });
        }
    });
}