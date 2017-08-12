var pieChart = dc.pieChart("#pieChart");
d3.csv("http://dc-js.github.io/dc.js/examples/morley.csv", function(error, experiments) {
  var ndx           = crossfilter(experiments),
      runDimension  = ndx.dimension(function(d) {return "run-"+d.Run;})
      speedSumGroup = runDimension.group().reduceSum(function(d) {return d.Speed * d.Run;});
  pieChart
  .minWidth(200)
  .minHeight(300)
  .width(400)
    
    .slicesCap(4)
    .innerRadius(0)
    .dimension(runDimension)
    .group(speedSumGroup)
    .legend(dc.legend())
    // workaround for #703: not enough data is accessible through .label() to display percentages
    .on('pretransition', function(chart) {
    	pieChart.selectAll('text.pie-slice').text(function(d) {
            return d.data.key + ' ' + dc.utils.printSingleValue((d.endAngle - d.startAngle) / (2*Math.PI) * 100) + '%';
        })
    });
  pieChart.render();
});


var barChart = dc.barChart("#barChart");
d3.csv("http://dc-js.github.io/dc.js/examples/morley.csv", function(error, experiments) {
  experiments.forEach(function(x) {
    x.Speed = +x.Speed;
  });
  var ndx                 = crossfilter(experiments),
      runDimension        = ndx.dimension(function(d) {return +d.Run;}),
      speedSumGroup       = runDimension.group().reduceSum(function(d) {return d.Speed * d.Run / 1000;});
  barChart
  .minWidth(200)
  .minHeight(300)
  .width(400)
   
    .x(d3.scale.linear().domain([6,20]))
    .brushOn(false)
    .yAxisLabel("This is the Y Axis!")
    .dimension(runDimension)
    .group(speedSumGroup)
    .on('renderlet', function(chart) {
    	barChart.selectAll('rect').on("click", function(d) {
            console.log("click!", d);
        });
    });
  barChart.render();
});


var analyticsChart = dc.barChart("#analyticsChart");
d3.csv("http://dc-js.github.io/dc.js/examples/morley.csv", function(error, experiments) {
    experiments.forEach(function(x) {
        x.Speed = +x.Speed;
    });
    var ndx                 = crossfilter(experiments),
        runDimension        = ndx.dimension(function(d) {return +d.Run;}),
        speedSumGroup       = runDimension.group().reduce(function(p, v) {
            p[v.Expt] = (p[v.Expt] || 0) + v.Speed;
            return p;
        }, function(p, v) {
            p[v.Expt] = (p[v.Expt] || 0) - v.Speed;
            return p;
        }, function() {
            return {};
        });
    function sel_stack(i) {
        return function(d) {
            return d.value[i];
        };
    }
    analyticsChart
    .minWidth(200)
    .minHeight(300)
    .width(400)
        .x(d3.scale.linear().domain([1,21]))
        .margins({left: 80, top: 20, right: 10, bottom: 20})
        .brushOn(false)
        .clipPadding(10)
        .title(function(d) {
            return d.key + '[' + this.layer + ']: ' + d.value[this.layer];
        })
        .dimension(runDimension)
        .group(speedSumGroup, "1", sel_stack('1'))
        .renderLabel(true);
    analyticsChart.legend(dc.legend());
    dc.override(analyticsChart, 'legendables', function() {
        var items = analyticsChart._legendables();
        return items.reverse();
    });
    for(var i = 2; i<6; ++i)
    	analyticsChart.stack(speedSumGroup, ''+i, sel_stack(i));
    analyticsChart.render();
});

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/app/analytics', function (data) {
        	handleResponse(data.body);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendFeedBack() {
	
	
	
	var request={
			
		name : "",
		likes : 0,
		loves : 0
			
	}
    stompClient.send("/app/feedback", {}, JSON.stringify(request));
}

function handleFeedbackEvent(event){
	console.log("handling feedback event ");
	
	  var $this = $(event.currentTarget),
      count = $this.attr('data-count'),
      active = $this.hasClass('active'),
      multiple = $this.hasClass('multiple-count');
  

	  $this.attr('data-count', ! active || multiple ? ++count : --count  )[multiple ? 'noop' : 'toggleClass']('active');


}

function handleResponse(data) {
	
	if( data !=null && typeof data ==="string"){
		data=JSON.parse(data);
	}
	for(var i=0; i<data.length;i++){
		
	}
   
}

$(function () {
  
   connect(); 
  
   $('.btn-counter').on('click', function(event) {
	 
	   		handleFeedbackEvent(event);
	 
	   
	 });
});