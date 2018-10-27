
# WEBSOCKET SIMPTEMPLATE

### A Simple Spring Boot Application using websockets,Stomp Js.

<p>

This application uses SimpTemplate to send the messages to the websocket.


To do this : 

In the controller class, 

```
@Autowired
private SimpMessagingTemplate template;

template.convertAndSend(destination,payload);

```



</p>



