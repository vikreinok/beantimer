google.charts.load('current', {'packages': ['gantt']});
google.charts.setOnLoadCallback(drawChart);

function daysToMilliseconds(days) {
    return days * 24 * 60 * 60 * 1000;
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
            console.table(metric);
            var last = null;
            var percent = Math.abs(Number.parseFloat(metric.durationAvg) - parseInt(metric.duration)) / Number.parseFloat(metric.durationAvg);
            data.addRow([
                metric.beanType + " ",
                metric.beanName + " ",
                metric.primaryBean ? "primary" : "not primary",
                new Date(parseInt(metric.initialisationStartTimeMillis)),
                new Date(parseInt(metric.initialisationStartTimeMillis) + parseInt(metric.duration)),
                parseInt(metric.duration),
                percent,
                last
            ]);
            last = metric.beanType + " ";
         }
    }

    var options = {
        height: response.length * 18
    };

    var chart = new google.visualization.Gantt(document.getElementById('chart_div'));

    chart.draw(data, options);

}