


// const stompClient = new StompJs.Client({
//     brokerURL: 'ws://localhost:5000/gs-guide-websocket'
// });

const url = 'ws://localhost:5000/gs-guide-websocket';

const client = Stomp.client(url);

client.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    client.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
};

client.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

client.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

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
    client.activate();
}

function disconnect() {
    client.deactivate().then(() => {});
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    client.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});