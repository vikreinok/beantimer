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
            data.addRow(
                [
                    metric.beanName,
                    metric.beanType + " .",
                    metric.primaryBean + " .",
                    new Date(2014, 2, 22),
                    new Date(2014, 5, 20),
                    Number.parseFloat(metric.durationAvg),
                    100,
                    lastName
                ]
            );
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