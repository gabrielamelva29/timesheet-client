/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    var date_input = $('input[name="date"]');
    var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
    date_input.datepicker({
        format: 'dd MM yyyy',
//                    container: container,
        todayHighlight: true,
        autoclose: true,
        calendarWeeks: true,
        clearBtn: true
//                    startDate: '-3d'
    });
});
