var stompClient = null;


function sendMessage() {
	console.log("send message is called");
	if( stompClient !=null){
		console.log("stompClient is not null");
		stompClient.send("/hello", {}, JSON.stringify({ "message" : $("#message").val() }));
	}
}


function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        
        console.log('Connected: ' + frame);
        stompClient.subscribe('/queue/data', function (data) {
        	console.log("subscription is called");
            handleResponse(data);
        });
    });
}

function handleResponse(data){
	
	console.log("response is obtained "+data.body);
	var source   = $("#message-response-template").html();
	var template = Handlebars.compile(source);
	var html    = template(data.body);
	$("#renderHere").append(html);
	
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

  
});
