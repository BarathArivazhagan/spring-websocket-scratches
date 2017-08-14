var stompClient = null;


function getInventories() {
	console.log("get inventories is called");
	if( stompClient !=null){
		console.log("stompClient is not null");
		stompClient.send("/app/get", {}, {});
	}
}


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
	
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}



$(function () {
    connect();   

   //setInterval(getInventories, 5000);
});
