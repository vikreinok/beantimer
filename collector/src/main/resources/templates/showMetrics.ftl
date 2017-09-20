<!DOCTYPE html>
<html>
<head>
    <title>Metrics</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="static/style.css" />
</head>
<body>

<a href="/" >Index page</a>

<h2>Table of metrics</h2>

<table>
    <tr>
        <th>Bean name</th>
        <th>Bean type</th>
        <th>Average</th>
        <th>Minimal</th>
        <th>Maximal</th>
    </tr>

<#list metrics as metric>
    <tr>
        <td>${metric.beanName}</td>
        <td>${metric.beanType}</td>
        <td>${metric.durationAvg}</td>
        <td>${metric.durationMin}</td>
        <td>${metric.durationMax}</td>
    </tr>
</#list>
</table>
</body>
</html>