/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var _csrf_name = $("meta[name=_csrf_header]").attr("content");
var _csrf_value = $("meta[name=_csrf]").attr("content");

function addRequestHeader() {
    $(document).ajaxSend((evt, req) => {
        req.setRequestHeader(_csrf_name, _csrf_value);
    });
}

