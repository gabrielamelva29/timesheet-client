/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// 1 detik = 1000
//            setTimeout(function() {
//            location.reload();
//        }, 500);

//const arrayToCount = [1, 2, 3, 5, 2, 8, 9, 2];
//const result = arrayToCount.filter(i => i === 2).length;
//console.log('number of the found elements: ' + result);
window.setTimeout("waktu()", 0);
window.setTimeout("status()", 0);

function waktu() {
    const arrbulan = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var tanggal = new Date();
    var bulan = new Date().getMonth();
    document.getElementById("periode").innerHTML = arrbulan[bulan] + " " + tanggal.getFullYear();
}

function status() {
    $('#statusAttendance').load("/history");
}