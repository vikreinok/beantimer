<!DOCTYPE html>
<html>
<head>
    <title>Metrics</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<a href="/">Index page</a>
|
<a href="/showMetrics">Sort by bean init. time</a>
|
<a href="/showMetrics?sort=beanName&dir=ASC">Sort by beanName</a>
|
<a href="/showMetrics?sort=duration&dir=DESC">Sort by duration</a>

<select id="users" onChange="addQueryParameter(this);">
    <option value="null">Any user</option>
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
        <td align="right">${(metric.durationAvg/1)?string["0.0"]}</td>
        <td align="right">${(metric.durationMin/1)?string["0.0"]}</td>
        <td align="right">${(metric.durationMax/1)?string["0.0"]}</td>
        <td align="right">${metric.count}</td>
    </tr>
</#list>

</table>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/main.js"></script>

</html>