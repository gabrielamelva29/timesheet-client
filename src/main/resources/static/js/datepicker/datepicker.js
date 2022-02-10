/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getByDate(idEmployee) {
    $("#datepicker").attr("class", "form-control date-"+ idEmployee);
    const urlAct = "/job/findByEmployee/"+ idEmployee;
    $.ajax({
        url: urlAct,
        type: 'GET',
        dataType: 'json',
        data: $('.date-' + idEmployee).serialize(),
        beforeSend: (req) => {
            addRequestHeader();
        },
        success: function (data) {
            var a = [];

            for (let i = 0; i < data.length; i++) {
                a = a.concat(data[i].date);
            }
            var date_input = $('input[name="date"]');
            var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
            date_input.datepicker({
//              language: 'id',
                format: 'dd MM yyyy',
//                    container: container,
                todayHighlight: true,
                autoclose: true,
//              calendarWeeks: true,
                datesDisabled: a,
                clearBtn: true
            });
            console.log(a);
        },
        error: (err) => {
            console.log(err);
        }
    });
}
