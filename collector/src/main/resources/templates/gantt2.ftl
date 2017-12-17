<!DOCTYPE html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>Load data from JSON file</title>
</head>
	<script src="/js/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
	<#--<script src="/js/dhtmlxgantt_tooltip.js" type="text/javascript" charset="utf-8"></script>-->
	<link rel="stylesheet" href="/css/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">
	<script type="text/javascript" src="/js/testdata.js"></script>
<body>
<style type="text/css">
    html, body{ padding:0px; margin:0px; height:100%; }
</style>
<div id="gantt_here" style='width:100%; height:100%;'></div>
<script type="text/javascript">
    var response = JSON.parse('${metrics}');
    // console.log(response);
</script>
<script type="text/javascript">


    gantt.attachEvent("onLoadStart", function(){
        gantt.message("Loading...");
    });
    gantt.attachEvent("onLoadEnd", function(){
        gantt.message({text:"Loaded " + gantt.getTaskCount() + " tasks, " + gantt.getLinkCount() + " links", expire:8*1000});
    });

    gantt.config.min_column_width = 30;
    gantt.config.scale_height = 60;
    gantt.config.date_scale = "%d";
    gantt.config.subscales = [
        {unit:"month", step:1, date:"%F, %Y" },
        {unit:"year", step:1, date:"%Y" }
    ];


    gantt.config.row_height = 22;

    gantt.config.static_background = true;
    gantt.config.start_date = new Date(2017, 0, 1);
    gantt.config.end_date = new Date(2018, 0, 1);
    gantt.init("gantt_here");

    resetData(1500);

    function resetData(taskCount){
        var start = Date.now();
        gantt.message("Fetching data");

        var data = generateData(taskCount);

        gantt.clearAll();
        gantt.parse(data);

        var end = Date.now();
        gantt.message("Generated and parsed <b>" + gantt.getTaskByTime().length + "</b> tasks in <b>" + (end - start)/1000 + "</b> seconds");
    }

    function randomDate(start, end) {
        return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
    }

    function generateData(tasksCount){
        var tasks =  {
            data:[],
            links:[]
        };

        var project_id = 1;
        tasks.data.push({
            id:  project_id,
            text: "Beantimer",
            type: gantt.config.types.project,
            open:true
        });

        var count = 0;
        for (var index in response) {
            if (response.hasOwnProperty(index)) {
                var metric = response[index];
                // console.table(metric);
                var last = null;
                var percent = Math.abs(Number.parseFloat(metric.durationAvg) - Number.parseFloat(metric.duration)) / Number.parseFloat(metric.durationAvg);
                var startDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis));
                var endDate = new Date(Number.parseFloat(metric.initialisationStartTimeMillis) + Number.parseFloat(metric.duration));


                var task = {
                    id: count,
                    start_date: startDate,
                    end_date: endDate,
                    text: metric.beanName,
                    duration: metric.duration,
                    open: false
                };

                // if(gantt.date.add(date, 8, "day").valueOf() > gantt.config.end_date.valueOf()){
                //     date = new Date(gantt.config.start_date);
                //     project_id = i + 1;
                //     delete task.parent;
                //     task.open = true;
                // }


                // console.table(task);
                last = metric.beanType + "";
                count++;
                tasks.data.push(task);
            }
        }

        return tasks;
    }

</script>
</body>