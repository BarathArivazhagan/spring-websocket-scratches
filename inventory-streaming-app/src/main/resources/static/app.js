var stompClient = null;




function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        
        console.log('Connected: ' + frame);
        stompClient.subscribe('/analytics/data', function (data) {
        	console.log("subscription is called");
            handleResponse(data);
        });
    });
}

function handleResponse(data){
	
	console.log("inventories "+data.body);
	drawData(data.body);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}



$(function () {
    //connect();  
    drawData();
});


var analyticsChart = dc.barChart("#analyticsChart");
function drawData(experiments) {
    console.log("drawing the chart");
    experiments=[{"locationName":"us","productName":"choco","quantity":67,"threshold":10},{"locationName":"SouthZone","productName":"COKE","quantity":23,"threshold":20},{"locationName":"SouthZone","productName":"DAIRYMILK","quantity":67,"threshold":20},{"locationName":"india","productName":"fan","quantity":67,"threshold":30},{"locationName":"SouthZone","productName":"MAZZA","quantity":67,"threshold":20},{"locationName":"SouthZone","productName":"MilkBikies","quantity":67,"threshold":50},{"locationName":"SouthZone","productName":"OREO","quantity":67,"threshold":20},{"locationName":"SouthZone","productName":"Snickers","quantity":67,"threshold":20},{"locationName":"SouthZone","productName":"SPRITE","quantity":67,"threshold":30}]

    experiments.forEach(function(x) {
        x.Speed = +x.Speed;
    });
    var ndx                 = crossfilter(experiments),
        locationDimension        = ndx.dimension(function(d) {return d.locationName;}),
        thresholdGroup       = ndx.group().reduce(function(p, v) {
            p[v.threshold] = (p[v.threshold] || 0) + v.quantity;
            return p;
        }, function(p, v) {
            p[v.threshold] = (p[v.threshold] || 0) - v.quantity;
            return p;
        }, function() {
            return {};
        });
        console.log(thresholdGroup);
    function sel_stack(i) {
        return function(d) {
            return d.value[i];
        };
    }
    analyticsChart
    .minWidth(200)
    .minHeight(300)
    .xAxisLabel("Location Name")
    .yAxisLabel("Threshold ")
    .width(400)
        .x(d3.scale.ordinal().domain(locationDimension)
         .xUnits(dc.units.ordinal);
        .margins({left: 80, top: 20, right: 10, bottom: 20})
        .brushOn(false)
        .clipPadding(10)
        .title(function(d) {
            return d.key + '[' + this.layer + ']: ' + d.value[this.layer];
        })
        .dimension(locationDimension)
        .group(thresholdGroup, "1", sel_stack('1'))
        .renderLabel(true);
    analyticsChart.legend(dc.legend());
    dc.override(analyticsChart, 'legendables', function() {
        var items = analyticsChart._legendables();
        return items.reverse();
    });
    for(var i = 2; i<6; ++i)
    	analyticsChart.stack(thresholdGroup, ''+i, sel_stack(i));
    analyticsChart.render();
}

