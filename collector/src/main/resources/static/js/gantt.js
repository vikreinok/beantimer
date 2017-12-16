google.charts.load('current', {'packages': ['gantt']});
google.charts.setOnLoadCallback(drawChart);

function daysToMilliseconds(days) {
    return days * 24 * 60 * 60 * 1000;
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function drawChart() {

    var data = new google.visualization.DataTable();

    data.addColumn('string', 'Bean name');
    data.addColumn('string', 'Bean type');
    data.addColumn('string', 'isPrimaryBean');
    data.addColumn('date', 'Start Date');
    data.addColumn('date', 'End Date');
    data.addColumn('number', 'Duration');
    data.addColumn('number', 'Percent');
    data.addColumn('string', 'Dependencies');

    for (var index in response) {
        if (response.hasOwnProperty(index)) {
            var metric = response[index];
            // console.table(metric);
            var last = null;
            var percent = Math.abs(Number.parseFloat(metric.durationAvg) - Number.parseFloat(metric.duration)) / Number.parseFloat(metric.durationAvg);
            var startDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis));
            var endDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis) + Number.parseFloat(metric.duration));



            var values = [
                metric.beanType + " ",
                metric.beanName + " ",
                metric.primaryBean ? "primary" : "not primary",
                startDate,
                endDate,
                null,
                percent,
                null
            ];
            // console.table(values);
            data.addRow(values);
            last = metric.beanType + "";
         }
    }

    var options = {
        height: response.length * 17 + 160
    };

    var chart = new google.visualization.Gantt(document.getElementById('chart_div'));

    chart.draw(data, options);

}