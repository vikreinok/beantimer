google.charts.load('current', {'packages': ['gantt']});
google.charts.setOnLoadCallback(drawChart);


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
            var lastName = null;
            var percent = Math.abs(Number.parseFloat(metric.durationAvg) - Number.parseFloat(metric.duration)) / Number.parseFloat(metric.durationAvg);
            var data = [
                metric.beanName,
                metric.beanType,
                metric.primaryBean,
                null,
                null,
                Number.parseFloat(metric.duration),
                percent,
                lastName
            ];
            data.addRow(data);
            lastName = metric.beanName;
        }
    }

    var options = {
        height: 400,
        gantt: {
            trackHeight: 30
        }
    };

    var chart = new google.visualization.Gantt(document.getElementById('chart_div'));

    chart.draw(data, options);


}