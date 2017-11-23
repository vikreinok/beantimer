<!DOCTYPE html>
<html>
<head>
    <title>Metrics</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../static/style.css">
</head>
<body>

<a href="/">Index page</a>
|
<a href="/showMetrics">Sort by bean init. time</a>
|
<a href="/showMetrics?sort=beanName&dir=ASC">Sort by beanName</a>
|
<a href="/showMetrics?sort=durationAvg&dir=DESC">Sort by duration</a>

<select id="users" onChange="addQueryParameter(this.value);">
    <option value="" disabled selected>Select user</option>
</select>

<h2>Table of metrics</h2>

<table>
    <tr>
        <th>Bean name</th>
        <th>Bean type</th>
        <th>Bean scope</th>
        <th>Is primary bean</th>
        <th>Average</th>
        <th>Minimal</th>
        <th>Maximal</th>
        <th>Count</th>
    </tr>

<#list metrics as metric>
    <tr>
        <td>${metric.beanName}</td>
        <td>${metric.beanType}</td>
        <td>${metric.beanScope!'N/A'}</td>
        <td>${metric.primaryBean?string('yes', '')}</td>
        <td align="right">${(metric.durationAvg/1000000)?string["0.0"]}</td>
        <td align="right">${(metric.durationMin/1000000)?string["0.0"]}</td>
        <td align="right">${(metric.durationMax/1000000)?string["0.0"]}</td>
        <td align="right">${metric.count}</td>
    </tr>
</#list>
</table>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

    function addQueryParameter(value) {
        var url = window.location.href;
        if (url.indexOf('?') > -1){
            url += '&username=' + value;
        }else{
            url += '?username=' + value;
        }
        window.location.href = url;
    };

    $(document).ready(function() {
        $.ajax({
            url: "./user",
            type: "GET",
            headers: {
                "accept": "application/json;",
            },
            success: function(data){
                $.each(data, function (key, value) {
                    console.log("key " + key + " val " + value);
                    $("#users").append($("<option></option>")
                            .val(value.ID)
                            .html(value.username));
                });
            },
            error: function(error){
                alert(JSON.stringify(error));
            }
        });


    });

</script>

</html>