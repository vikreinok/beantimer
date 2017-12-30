<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/jsgantt/jquery-ui-1.8.4.css">
    <link rel="stylesheet" type="text/css" href="/css/jsgantt/reset.css">
    <link rel="stylesheet" type="text/css" href="/css/jsgantt/jquery.ganttView.css">
    <style type="text/css">
        body {
            font-family: tahoma, verdana, helvetica;
            font-size: 0.8em;
            padding: 10px;
        }
    </style>
    <title>jQuery Gantt</title>

<body data-gr-c-s-loaded="true">


<div id="eventMessage"></div>
<div id="ganttChart" style="width: 443px;"></div>


<script type="text/javascript" src="/js/jsgantt/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/js/jsgantt/date.js"></script>
<script type="text/javascript" src="/js/jsgantt/jquery-ui-1.8.4.js"></script>
<script type="text/javascript" src="/js/jsgantt/jquery.ganttView.js"></script>
<script type="text/javascript">



    var ganttData = [
        {
            id: 1, name: "Beans", series: [
            ]
        }
    ];

    console.table(ganttData);

    var response = JSON.parse('${metrics}');

    for (var index in response) {
        if (response.hasOwnProperty(index)) {
            var metric = response[index];
            var percent = Math.abs(Number.parseFloat(metric.durationAvg) - Number.parseFloat(metric.duration)) / Number.parseFloat(metric.durationAvg);
            var startDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis));
            var endDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis) + Number.parseFloat(metric.duration));

            var value = {
                name: metric.beanName,
                start: startDate,
                end: endDate
            };

            ganttData[0].series.push(value);
        }
    }

    $(function () {
        $("#ganttChart").ganttView({
            data: ganttData,
            slideWidth: 600,
            vHeaderWidth: 300,
            behavior: {
                onClick: function (data) {
                    var msg = "You clicked on an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                    $("#eventMessage").text(msg);
                },
                onResize: function (data) {
                    var msg = "You resized an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                    $("#eventMessage").text(msg);
                },
                onDrag: function (data) {
                    var msg = "You dragged an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                    $("#eventMessage").text(msg);
                }
            }
        });

    });
</script>


</body>
</html>