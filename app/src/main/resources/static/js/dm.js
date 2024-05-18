  var sender = user;
  var receiver = receiverInfo;
  var chatList = chat;
  var room = roomInfo;
  var photo = "";

  var dmFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/dm/";
  var dmFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/dm/";
  var fileSize_40 = "?type=f&w=40&h=40";

  function connect() {
      stompClient.activate();
  }

  const stompClient = new StompJs.Client({
      brokerURL: 'ws://localhost:8080/ws'
  });

  stompClient.onConnect = (frame) => {
      setConnected(true);
      console.log('Connected: ' + frame);
      loadChat(chatList);

      stompClient.subscribe('/sub/dm/' + room.no, (dm) => {
          showMessage(JSON.parse(dm.body));
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
      let dm = {
          sender : {no : sender.no, nickname : sender.nickname},
          receiver : {no : receiver.no, nickname:receiver.nickname},
          message : $("#message").val(),
          photo : "",
          roomNo : room.no
      }
      console.log(dm);

      let formData = new FormData();
      if(photo != "") {
        Array.from(photo).forEach((el) => {
        formData.append("photo", el);
        });
      }
      formData.append("dm", new Blob([JSON.stringify(dm)], {type: "application/json"}));

      $.ajax({
        type: "POST",
        url: "/addDm",
        data: formData,
        contentType: false,
        processData: false,
        success: function (result) {
            console.log("success: " + result.message);
            stompClient.publish({
            destination: "/pub/dm",
            body: JSON.stringify({
              'sender': {no : result.sender.no},
              'receiver': {no : result.receiver.no},
              'message': result.message,
              'photo': result.photo,
              'roomNo': room.no,
            })
          });
        },
        error: function () {
            console.log("error: ajax 전송 테스트");
            }
      });
  }

  function showMessage(dm) {
      if(dm.sender.no == sender.no) {
        if(dm.message != null && dm.message.length > 0) {
          $("#conversation").append(
          "<div class='username sender'>" + sender.nickname + "</div>" +
          "<div class = 'message-container sent-message'><div class = 'message-text'>" + dm.message +
          "</div></div>");
        }
        if(dm.photo != null && dm.photo.length > 0) {
          $("#conversation").append(
          "<div class='username sender'>" + sender.nickname + "</div>" +
          "<div class = 'message-container sent-message'><div class = 'message-text'>" +
          "<a href=" + dmFilePath + dm.photo + ">" +
          "<img src=" + dmFileCdnDomain + dm.photo + fileSize_40 + "></a>" +
          "</div></div>");
        }
      } else {
        if(dm.message != null && dm.message.length > 0) {
          $("#conversation").append(
          "<div class='username receiver'>" + receiver.nickname + "</div>" +
          "<div class = 'message-container received-message'><div class = 'message-text'>" + dm.message +
          "</div></div>");
        }
        if(dm.photo != null && dm.photo.length > 0) {
          $("#conversation").append(
          "<div class='username receiver'>" + receiver.nickname + "</div>" +
          "<div class = 'message-container received-message'><div class = 'message-text'>" +
          "<a href=" + dmFilePath + dm.photo + ">" +
          "<img src=" + dmFileCdnDomain + dm.photo + fileSize_40 + "></a>" + "</div></div>");
        }
      }
      $("#message").val("");
      $("#photo").val("");
      $("#file-count").text(0);
  }

  function loadChat(chatList) {
      if(chatList != null) {
        for(chat in chatList) {
          if(chatList[chat].sender.no == sender.no) {
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<div class='username sender'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container sent-message'><div class = 'message-text'>" + chatList[chat].message +
              "</div></div>");
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<div class='username sender'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container sent-message'><div class = 'message-text'>" +
              "<a href=" + dmFilePath + chatList[chat].photo + ">" +
              "<img src=" + dmFileCdnDomain + chatList[chat].photo + fileSize_40 + "></a>" +
              "</div></div>");
            }
          } else {
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container received-message'><div class = 'message-text'>" + chatList[chat].message +
              "</div></div>");
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container received-message'><div class = 'message-text'>" +
              "<a href=" + dmFilePath + chatList[chat].photo + ">" +
              "<img src=" + dmFileCdnDomain + chatList[chat].photo + fileSize_40 + "></a>" +
              "</div></div>");
            }
          }
        }
      }
      $('#conversation').scrollTop($('#conversation')[0].scrollHeight);
  }

  function setFiles() {
    photo = $("#photo")[0].files;
    $("#message").val("");
    $("#file-count").text(photo.length);
  }

  function setMessage() {
    $("#photo").val('');
    photo = $("#photo")[0].files;
    $("#file-count").text(0);
  }

  $(function () {
      connect();
      $("form").on('submit', (e) => e.preventDefault());
      $( "#disconnect" ).click(() => disconnect());
      $( "#send" ).click(() => sendMessage());
      $( "#photo" ).change(() => setFiles());
      $( "#message" ).change(() => setMessage());
  });