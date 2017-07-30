<!DOCTYPE html>
<html>
<head>
    <title>Metrics</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<h2>List of metrics</h2>

<table>
    <tr>
        <th>Bean name</th>
        <th>Bean type</th>
        <th>Duration</th>
    </tr>

<#list metrics as metric>
    <tr>
        <td>${metric.beanName}</td>
        <td>${metric.beanType}</td>
        <td>${metric.duration}</td>
    </tr>
</#list>
</table>
</body>
</html>