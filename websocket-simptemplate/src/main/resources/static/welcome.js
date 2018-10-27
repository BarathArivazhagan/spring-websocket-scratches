var stompClient = null;


function sendUser() {
	console.log("send user is called");
	if( stompClient !=null){
		console.log("stompClient is not null");
		stompClient.send("/app/user", {}, $("#message").val());
	}
}


function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/welcome/user', function (data) {
        	console.log("subscription is called");
            $("#welcome").html("welcome"+data.body);
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



$(function () {
    connect();


});
