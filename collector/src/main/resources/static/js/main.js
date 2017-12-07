function updateUrlParameter(url, param, value) {
    var regex = new RegExp('(' + param + '=)[^\&]+');
    return url.replace(regex, '$1' + value);
}

function createUrlParameters(url, param, value) {
    if (url.indexOf('?') > -1) {
        url += '&' + param + '=' + value;
    } else {
        url += '?' + param + '=' + value;
    }
    return url;
}

function addQueryParameter(control) {
    var name = control.name;
    var value = control.value;
    var innerHTML = control.innerHTML;
    var url = window.location.href;
    if (url.indexOf('username=') > -1) {
        console.log(" name " + name);
        url = updateUrlParameter(url, 'username', value)
    } else {
        url = createUrlParameters(url, 'username', value);
    }


    window.location.href = url;
};

$(document).ready(function () {
    $.ajax({
        url: "./user",
        type: "GET",
        headers: {
            "accept": "application/json;",
        },
        success: function (data) {
            $.each(data, function (key, value) {
                console.log("key " + key + " val " + value);
                var opt = document.createElement("option");
                opt.value = value.username;
                opt.innerHTML = value.username;
                $("#users").append(opt);
            });
        },
        error: function (error) {
            alert(JSON.stringify(error));
        }
    });
});