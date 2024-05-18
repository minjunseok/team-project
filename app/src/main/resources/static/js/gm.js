  var school = sc;
  var sender = user;
  var chatList = chat;
  var photo = "";
  console.log(school);
  console.log(sender);
  console.log(chatList);

 var gmFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/gm/";
 var gmFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/gm/";
 var userFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/user/";
 var userFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/";
 var schoolFilePath = "https://kr.object.ncloudstorage.com/moyeorastorage/school/";
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

      stompClient.subscribe('/sub/gm/' + school.no, (gm) => {
          showMessage(JSON.parse(gm.body));
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
      let gm = {
          school : {no : school.no},
          sender : {no : sender.no, nickname : sender.nickname},
          message : $("#message").val(),
          photo : ""
      }

      let formData = new FormData();
      if(photo != "") {
        Array.from(photo).forEach((el) => {
        formData.append("photo", el);
        });
      }
      formData.append("gm", new Blob([JSON.stringify(gm)], {type: "application/json"}));

      $.ajax({
        type: "POST",
        url: "/addGm",
        data: formData,
        contentType: false,
        processData: false,
        success: function (result) {
            console.log(result.sendDate);
            stompClient.publish({
            destination: "/pub/gm",
            body: JSON.stringify({
              'school': {no : result.school.no},
              'sender': {no : result.sender.no, nickname : sender.nickname},
              'message': result.message,
              'photo': result.photo,
              'sendDate': result.sendDate
            })
          });
        },
        error: function () {
            console.log("error: ajax 전송 테스트");
        }
      });
  }

  function showMessage(gm) {
      console.log(gm);
      let d = gm.sendDate.substring(0, 10);
      let h = gm.sendDate.substring(11, 13);
      let m = gm.sendDate.substring(14, 16);
      if(gm.sender.no == sender.no) {
        if(gm.message != null && gm.message.length > 0) {
          $("#conversation").append(
          "<div class='username sender'>" + sender.nickname + "</div>" +
          "<div class = 'message-container sent-message'><div class = 'message-text'>" + gm.message +
          "</div></div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
          );
        }
        if(gm.photo != null && gm.photo.length > 0) {
          let filePath = (sender.photo.length > 0) ? gmFileCdnDomain + "user/" + sender.photo + fileSize_40 : "/img/user-icons-default.png";
          $("#conversation").append(
          "<div class='username sender'>" + sender.nickname + "</div>" +
          "<div class = 'message-container sent-message'><div class = 'message-text'>" +
          "<a href=" + gmFilePath + gm.photo + ">" +
          "<img src=" + gmFileCdnDomain + gm.photo + fileSize_40 + "></a>" +
          "</div></div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
          );
        }
      } else {
        if(gm.message != null && gm.message.length > 0) {
          $("#conversation").append(
          "<div class='username receiver'>" + gm.sender.nickname + "</div>" +
          "<div class = 'message-container received-message'><div class = 'message-text'>" + gm.message +
          "</div></div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
          );
        }
        if(gm.photo != null && gm.photo.length > 0) {
          $("#conversation").append(
          "<div class='username receiver'>" + gm.sender.nickname + "</div>" +
          "<div class = 'message-container received-message'><div class = 'message-text'>" +
          "<a href=" + gmFilePath + gm.photo + ">" +
          "<img src=" + gmFileCdnDomain + gm.photo + fileSize_40 + "></a>" +
          "</div></div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
          );
        }
      }
      $("#main-form")[0].reset();
      $("#file-count").text(0);
  }

  function loadChat(chatList) {
      if(chatList != null) {
        for(chat in chatList) {
          let d = chatList[chat].sendDate.substring(0, 10);
          let h = chatList[chat].sendDate.substring(11, 13);
          let m = chatList[chat].sendDate.substring(14, 16);
          if(chatList[chat].sender.no == sender.no) {
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<div class='username sender'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container sent-message'><div class = 'message-text'>" + chatList[chat].message +
              "</div></div>" +
              "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
              );
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<div class='username sender'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container sent-message'><div class = 'message-text'>" +
              "<a href=" + gmFilePath + chatList[chat].photo + ">" +
              "<img src=" + gmFileCdnDomain + chatList[chat].photo + fileSize_40 + "></a>" +
              "</div></div>" +
              "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>"
              );
            }
          } else {
            let uProfileImg = "<img class='uPhotoItem' src=" + userFileCdnDomain + chatList[chat].sender.photo + fileSize_40 + " onerror=profileImgError(this)>";
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<a class='uProfileLink'>" +
                  "<div class='uPhoto'>" +
                      "<div class='uPhotoInner'>" +
                        "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                      "</div>" +
                  "</div>" +
              "</a>" +
              "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container received-message'><div class = 'message-text'>" + chatList[chat].message +
              "</div></div>" +
              "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>"
              );
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<a class='uProfileLink'>" +
                  "<div class='uPhoto'>" +
                      "<div class='uPhotoInner'>" +
                        "<span class='uPhotoItem'>" + uProfileImg + "</span>" +
                      "</div>" +
                  "</div>" +
              "</a>" +
              "<div class='username receiver'>" + chatList[chat].sender.nickname + "</div>" +
              "<div class = 'message-container received-message'><div class = 'message-text'>" +
              "<a href=" + gmFilePath + chatList[chat].photo + ">" +
              "<img src=" + gmFileCdnDomain + chatList[chat].photo + fileSize_40 + "></a>" +
              "</div></div>" +
              "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>"
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
    console.log(photo);
  }

  function setMessage() {
    $("#photo").val('');
    photo = $("#photo")[0].files;
    $("#file-count").text(0);
  }

  function profileImgError(obj) {
    $(obj).attr("src", "/img/모여라.png");
  }

  $(function () {
      connect();
      $("form").on('submit', (e) => e.preventDefault());
      $( "#disconnect" ).click(() => disconnect());
      $( "#send" ).click(() => sendMessage());
      $( "#photo" ).change(() => setFiles());
      $( "#message" ).change(() => setMessage());
  });