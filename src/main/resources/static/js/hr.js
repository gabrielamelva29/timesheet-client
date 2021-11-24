$(document).ready(() => {
    load();
});



function load() {
    var rtable = $('#table').DataTable();
    $.ajax({
        url: '/getall',
        type: 'GET',
        data: 'JSON',
        success: function (data) {
            rtable.destroy();
            $('#table').dataTable({
                "data": data,
                columns: [
                    {'data': 'id', render: function (data, type, row, meta) {
                            return meta.row + 1;
                        }},
                    {'data': 'employee.miiId'},
                    {'data': 'employee.name'},
                    {'data': 'name', render: function (data, type, row, meta) {
                            return row.year + " " + row.month;
                        }},
                    {'data': 'employee.projectName', render: function (data, type, row, meta) {
                            if (row.employee == null) {
                                return "-";
                            } else {
                                return row.employee.projectName;
                            }
                        }},
                    {'data': 'status', render: function (data, type, row, meta) {
                            if (row.status != 'SENT') {
                                return row.status;
                            } else {
                                return "RECEIVED";
                            }
                        }},
                    {'data': 'approveDate', render: function (data, type, row, meta) {
                            if (row.approveDate == null) {
                                return "-";
                            } else {
                                return row.approveDate;
                            }
                        }},
                    {'data': 'id', render: function (data, type, row, meta) {
//                            var kondisi = row.status!="APPROVED";
                            if (row.status=="APPROVED") {
                                var button = ' <td>\n\
                                            <a href="/history/'+row.id+'" type="button" style="background-color: #453B3B" class="btn rounded-circle" data-bs-toggle="tooltip" data-bs-placement="top" title="View">\n\
                                            <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#fff" class="bi bi-eye-fill" viewBox="0 0 16 16">\n\
                                            <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>\n\
                                            <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>\n\
                                            </svg> </a>\n\
                                            <a href="/history/download/excel/'+row.id+'" class="btn rounded-circle" style="background-color: #715454" data-bs-toggle="tooltip" data-bs-placement="top" title="Export Excel">\n\
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#fff" class="bi bi-cloud-download-fill" viewBox="0 0 16 16">\n\
                                            <path fill-rule="evenodd" d="M8 0a5.53 5.53 0 0 0-3.594 1.342c-.766.66-1.321 1.52-1.464 2.383C1.266 4.095 0 5.555 0 7.318 0 9.366 1.708 11 3.781 11H7.5V5.5a.5.5 0 0 1 1 0V11h4.188C14.502 11 16 9.57 16 7.773c0-1.636-1.242-2.969-2.834-3.194C12.923 1.999 10.69 0 8 0zm-.354 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V11h-1v3.293l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>\n\
                                            </svg> </a>\n\
                                            </form>\n\
                                            </td>';
                            return button;
                            }else{
                            var button = ' <td>\n\
                                            <form action="/approved/'+row.id+'" method="POST" style="width: 100%" data-bs-toggle="tooltip" data-bs-placement="top" title="Approved">\n\
                                            <button type="submit" class="btn rounded-circle" style="background-color: #4BBF41">\n\
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#fff" class="bi bi-check-square-fill" viewBox="0 0 16 16">\n\
                                            <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm10.03 4.97a.75.75 0 0 1 .011 1.05l-3.992 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.75.75 0 0 1 1.08-.022z"/>\n\
                                            </svg> </button>\n\
                                            <a href="/history/'+row.id+'" type="button" style="background-color: #453B3B" class="btn rounded-circle" data-bs-toggle="tooltip" data-bs-placement="top" title="View">\n\
                                            <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#fff" class="bi bi-eye-fill" viewBox="0 0 16 16">\n\
                                            <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>\n\
                                            <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>\n\
                                            </svg> </a>\n\
                                            <a href="/history/download/excel/'+row.id+'" class="btn rounded-circle" style="background-color: #715454" data-bs-toggle="tooltip" data-bs-placement="top" title="Export Excel">\n\
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#fff" class="bi bi-cloud-download-fill" viewBox="0 0 16 16">\n\
                                            <path fill-rule="evenodd" d="M8 0a5.53 5.53 0 0 0-3.594 1.342c-.766.66-1.321 1.52-1.464 2.383C1.266 4.095 0 5.555 0 7.318 0 9.366 1.708 11 3.781 11H7.5V5.5a.5.5 0 0 1 1 0V11h4.188C14.502 11 16 9.57 16 7.773c0-1.636-1.242-2.969-2.834-3.194C12.923 1.999 10.69 0 8 0zm-.354 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V11h-1v3.293l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>\n\
                                            </svg> </a>\n\
                                            </form>\n\
                                            </td>';
                            return button;
                        }
//                            var a=null;
//                            var form = '<form th:action="@{/approved/'+row.id+'}" method="POST" style="width: 100%"';
//                            if (row.status=="APPROVED") {
//                                var a= 'data-bs-toggle="tooltip" data-bs-placement="top" title="Approved">\n\
//                                        <button th:if="${condition}" type="submit" class="btn rounded-circle" style="background-color: #4BBF41">\n\
//                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill=" #fff" class="bi bi-check-square-fill" viewBox="0 0 16 16">\n\
//                                        <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm10.03 4.97a.75.75 0 0 1 .011 1.05l-3.992 4.99a.75.75 0 0\n\
//                                        1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.75.75 0 0 1 1.08-.022z"/>\n\
//                                        </svg></button>';}
//                            var b = '<a th:href="@{/history/'+row.id+'}" type="button" style="background-color: #453B3B"\n\
//                                    class="btn rounded-circle" data-bs-toggle=" tooltip" data-bs-placement="top" title="View">\n\
//                                    <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="="#fff" class="bi bi-eye-fill" viewBox="0 0 16 16">\n\
//                                    <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>\n\
//                                    <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>\n\
//                                    </svg></a>';
//                            var c = '<a th:href="@{/history/download/excel/'+row.id+'}" class="btn rounded-circle" style="background-color: #715454"\n\
//                                    data-bs-toggle="tooltip" data-bs-placement="top" title="Export Excel">\n\
//                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="="#fff" class="bi bi-cloud-download-fill" viewBox="0 0 16 16">\n\
//                                    <path fill-rule="evenodd" d="M8 0a5.53 5.53 0 0 0-3.594 1.342c-.766.66-1.321 1.52-1.464 2.383C1.266 4.095 0 5.555 0 7.318 0 \n\
//                                    9.366 1.708 11 3.781 11H7.5V5.5a.5.5 0 0 1 1 0V11h4.188C14.502 11 16 9.57 16 7.773c0-1.636-1.242-2.969-2.834-3.194C12.923 1.999 10.69 0 8 \n\
//                                    0zm-.354 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V11h-1v3.293l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>\n\
//                                    </svg></a>';
//                                return form+a+b+c;
                        }}
                ]
            });
        }
    });
}


