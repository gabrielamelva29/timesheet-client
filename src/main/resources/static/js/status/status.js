$(document).ready(() => {
    load();
});
function load() {
    $.ajax({
        url: '/status/getAll',
        type: 'GET',
        data: 'JSON',
        success: function (data) {
            var row = $('<tr><td>' + data[1].name + '</td><td>' + data[1].count +
                    '</td><td style="background-color: #CDE9FD">' + data[3].name + '</td><td style="background-color: #CDE9FD">' + data[3].count +
                    '</td><td>' + data[0].name + '</td><td>' + data[0].count +
                    '</td></tr>');
            var row2 = $('<tr><td style="background-color: #CDE9FD">' + data[2].name + '</td><td style="background-color: #CDE9FD">' + data[2].count +
                    '</td><td>' + data[4].name + '</td><td>' + data[4].count +
                    '</td><td style="background-color: #CDE9FD">' + data[5].name + '</td><td style="background-color: #CDE9FD">' + data[5].count +
                    '</td></tr>');
            $('#statusTable').append(row);
            $('#statusTable').append(row2);
        },
        async: true,
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + textStatus + ' - ' + errorThrown);
        }
    });
}