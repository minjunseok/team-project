  let sender = user;
  let receiver = receiverInfo;
  let chatList = chat;
  let room = roomInfo;
  let photo = "";

  let dmFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/dm/";
  let dmFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/dm/";
  let userFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/user/";
  let userFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/";
  let fileSize_40 = "?type=f&w=40&h=40";

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
          sender : {no : sender.no, nickname : sender.nickname, photo : sender.photo},
          receiver : {no : receiver.no, nickname:receiver.nickname, photo:receiver.photo},
          message : $("#message").val(),
          photo : "",
          roomNo : room.no
      }

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
              'sender': {no : sender.no, nickname : sender.nickname, photo : sender.photo},
              'receiver': {no : result.receiver.no, nickname : result.receiver.nickname, photo : result.receiver.photo},
              'message': result.message,
              'photo': result.photo,
              'roomNo': room.no,
              'sendDate': result.sendDate
            })
          });
        },
        error: function () {
            console.log("error: ajax 전송 테스트");
        }
      });
  }

  function showMessage(dm) {
  console.log(dm)
      let d = dm.sendDate.substring(0, 10);
      let h = dm.sendDate.substring(11, 13);
      let m = dm.sendDate.substring(14, 16);

      if(dm.receiver.no != sender.no) {
        if(dm.message != null && dm.message.length > 0) {
          $("#conversation").append(
          "<div class='chat-wrap sender-wrap'>" +
            "<div class = 'message-text'>" + dm.message + "</div>" +
            "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
           "</div>"
          );
        }
        if(dm.photo != null && dm.photo.length > 0) {
          $("#conversation").append(
          "<div class='chat-wrap sender-wrap'>" +
              "<div class = 'message-text'>" +
                  "<img src=" + dmFileCdnDomain + dm.photo + fileSize_40 + " data-link='" + dmFilePath + dm.photo  + "' onclick='clickFileDm(this)'>" +
              "</div>" +
              "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
          "</div>");
        }
      } else {
        let uProfileImg = "<img class='uPhotoItem profile-wrap' src=" + userFileCdnDomain + dm.sender.photo + fileSize_40 + " onerror=profileImgError(this)>";

        if(dm.message != null && dm.message.length > 0) {
          $("#conversation").append(
          "<div class='chat-wrap receiver-wrap'>" +
            "<div class='profile-wrap'>" +
                "<a class='uProfileLink'>" +
                    "<div class='uPhoto'>" +
                        "<div class='uPhotoInner'>" +
                          "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                        "</div>" +
                    "</div>" +
                "</a>" +
            "</div>" +
            "<div class='contents-wrap'>" +
                "<div class='username receiver'>" + dm.sender.nickname + "</div>" +
                "<div class='message-wrap'>" +
                    "<div class = 'message-text'>" + dm.message + "</div>" +
                    "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>" +
                "</div>" +
            "</div>" +
          "</div>"

          );
        }
        if(dm.photo != null && dm.photo.length > 0) {
          $("#conversation").append(
          "<div class='chat-wrap receiver-wrap'>" +
            "<div class='profile-wrap'>" +
                "<a class='uProfileLink'>" +
                    "<div class='uPhoto'>" +
                        "<div class='uPhotoInner'>" +
                          "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                        "</div>" +
                    "</div>" +
                "</a>" +
            "</div>" +
            "<div class='contents-wrap'>" +
                "<div class='username receiver'>" + dm.sender.nickname + "</div>" +
                "<div class='message-wrap'>" +
                    "<div class = 'message-text'>" +
                        "<img src=" + dmFileCdnDomain + dm.photo + fileSize_40 + " data-link='" + dmFilePath + dm.photo  + "' onclick='clickFileDm(this)'>" +
                    "</div>" +
                    "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>" +
                "</div>" +
            "</div>" +
          "</div>"


          );
        }
      }
      $("#message").val("");
      $("#photo").val("");
      $("#file-count").text(0);
      $('#conversation').scrollTop($('#conversation')[0].scrollHeight);
  }

  function loadChat(chatList) {
  console.log(chatList)
      if(chatList != null) {
        for(chat in chatList) {
          let d = chatList[chat].sendDate.substring(0, 10);
          let h = chatList[chat].sendDate.substring(11, 13);
          let m = chatList[chat].sendDate.substring(14, 16);
          if(chatList[chat].sender.no == sender.no) {
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<div class='chat-wrap sender-wrap'>" +
                "<div class = 'message-text'>" + chatList[chat].message + "</div>" +
                "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
              "</div>"
              );
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<div class='chat-wrap sender-wrap'>" +
                "<div class = 'message-text'>" +
                    "<img src=" + dmFileCdnDomain + chatList[chat].photo + fileSize_40 + " data-link='" + dmFilePath + chatList[chat].photo  + "' onclick='clickFileDm(this)'>" +
                "</div>" +
                "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
              "</div>"
              );
            }
          } else {
            let uProfileImg = "<img class='uPhotoItem profile-wrap' src=" + userFileCdnDomain + chatList[chat].sender.photo + fileSize_40 + " onerror=profileImgError(this)>";
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(

              "<div class='chat-wrap receiver-wrap'>" +
                "<div class='profile-wrap'>" +
                    "<a class='uProfileLink'>" +
                        "<div class='uPhoto'>" +
                            "<div class='uPhotoInner'>" +
                              "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                            "</div>" +
                        "</div>" +
                    "</a>" +
                "</div>" +
                "<div class='contents-wrap'>" +
                    "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
                    "<div class='message-wrap'>" +
                        "<div class = 'message-text'>" + chatList[chat].message + "</div>" +
                        "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>" +
                    "</div>" +
                "</div>" +
              "</div>"

              );
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(

              "<div class='chat-wrap receiver-wrap'>" +
                "<div class='profile-wrap'>" +
                    "<a class='uProfileLink'>" +
                        "<div class='uPhoto'>" +
                            "<div class='uPhotoInner'>" +
                              "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                            "</div>" +
                        "</div>" +
                    "</a>" +
                "</div>" +
                "<div class='contents-wrap'>" +
                    "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
                    "<div class='message-wrap'>" +
                        "<div class = 'message-text'>" +
                            "<img src=" + dmFileCdnDomain + chatList[chat].photo + fileSize_40 + " data-link='" + dmFilePath + chatList[chat].photo  + "' onclick='clickFileDm(this)'>" +
                        "</div>" +
                        "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>" +
                    "</div>" +
                "</div>" +
              "</div>"
              );
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

  function profileImgError(obj) {
      $(obj).attr("src", "/img/moyeoraLogo2.png");
    }

  function clickFileDm(obj) {
    window.open(obj.dataset.link, 'file_view_dm', 'width=700px,height=800px,scrollbars=yes');
  }

  $(function () {
      connect();
      $("form").on('submit', (e) => e.preventDefault());
      $( "#disconnect" ).click(() => disconnect());
      $( "#send" ).click(() => sendMessage());
      $( "#photo" ).change(() => setFiles());
      $( "#message" ).change(() => setMessage());
  });