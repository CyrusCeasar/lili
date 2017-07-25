/**
 * Created by luomin on 2017/7/12.
 */

var cookieOptions = {};
cookieOptions.path = "/";
cookieOptions.expires = 365;

function buildUrl(pathname) {

  //  var host = "39.108.179.107";
    var host = "localhost";
    console.log(host);
    if (host == null || host == "null") {
        console.log("host is null");
        host = window.location.hostname;
        console.log("host is null,use window.location.hostname:" + host);
    }

    var httpPort = "8080";
    if (httpPort == null || httpPort == "null") {
        httpPort = window.location.port;
    }

    var httpsPort = null;
    if (httpsPort == null || httpsPort == "null") {
        httpsPort = window.location.port;
    }

    if (window.location.protocol == "https:") {
        return "https://" + host + ":" + httpsPort + pathname;
    }
    else {
        return "http://" + host + ":" + httpPort + pathname;
    }
}

function debugAlert(msg) {
    console.log(msg);
}

function doPost(location, dto, success, fail) {
    restful(location, dto, success, fail, "post");
}

function doPut(location, dto, success, fail) {
    restful(location, dto, success, fail, "put");
}

function doGet(location, dto, success, fail) {
    restful(location, dto, success, fail, "get");
}

function doDelete(location, dto, success, fail) {
    restful(location, dto, success, fail, "delete");
}

function doPostSyn(location, dto, success, fail) {
    restfulSyn(location, dto, success, fail, "post");
}

function doPutSyn(location, dto, success, fail) {
    restfulSyn(location, dto, success, fail, "put");
}

function doGetSyn(location, dto, success, fail) {
    restfulSyn(location, dto, success, fail, "get");
}

function doDeleteSyn(location, dto, success, fail) {
    restfulSyn(location, dto, success, fail, "delete");
}

function restful(location, dto, success, fail, method) {
    _restful(true, location, dto, success, fail, method);
}

//同步发送
function restfulSyn(location, dto, success, fail, method) {
    _restful(false, location, dto, success, fail, method);
}

function _restful(async, location, dto, success, fail, method) {
    var dtoObj;
    if (method == "get") {
        dtoObj = dto;
    } else if (method == "post" || method == "put" || method == "delete") {
        dtoObj = JSON.stringify(dto);
    }
    $.ajax({
        url: buildUrl(location),
        data: dtoObj,
        type: method,
        contentType: "application/json",
        dataType: "json",
        traditional: true,
        timeout: 20000,
        async: async,
        beforeSend: function (XHR) {
            debugAlert("begin send");
        },
        complete: function (XMLHttpRequest, status) {
            if (status == "error") {
                alert("请检查是否正确连接到网络！");
            }
            if (status == "timeout") {
                alert("网络不给力，请稍后再次尝试！");
            }
        },
        success: function (data) {
            var resultCode = data.result_code;
            debugAlert(method + " - " + location + " : " + resultCode + " -- " + data.result_msg);
            debugAlert(data);
            if (resultCode != "0") {
                if (fail != null) {
                    fail(data.result_msg);
                } else {
                    alert(data.result_msg);
                }
            } else {
                success(data);
            }
        }
    });
}