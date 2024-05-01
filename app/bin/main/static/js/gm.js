var schoolNo = $("#schoolNo").val();

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    // loadChat(chatList)

    stompClient.subscribe('/sub/gm/' + schoolNo, (greeting) => {
        showGreeting(JSON.parse(greeting.body).message);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
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
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/pub/gm",
        body: JSON.stringify({
        'schoolNo': schoolNo,
        'sender': $("#sender").val(),
        'message': $("#message").val(),
        'photo': "",
        'sendDate': moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        })
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function loadChat(chatList) {
    if(chatList != null) {
      for(chat in chatList) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
      }
    }
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});