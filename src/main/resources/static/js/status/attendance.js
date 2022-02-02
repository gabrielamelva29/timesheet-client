/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(() => {
    proses();
});
function proses() {
    var attendance = document.getElementById("attendance").value;
    var startTime = document.getElementById("startTime");
    var endTime = document.getElementById("endTime");
    if (attendance == "S" || attendance == "V" || attendance == "X") {
        startTime.setAttribute("readonly", "");
        startTime.value="";
        endTime.setAttribute("readonly", "");
        endTime.value="";
    } else {
        startTime.removeAttribute("readonly", "");
        endTime.removeAttribute("readonly", "");
    }

}

