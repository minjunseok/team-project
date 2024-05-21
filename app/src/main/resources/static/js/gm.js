  let school = sc;
  let sender = userGm;
  let chatList = chat;
  let photo = "";
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
      stompClientGm.activate();
  }

  const stompClientGm = new StompJs.Client({
      brokerURL: 'ws://localhost:8080/ws'
  });

  stompClientGm.onConnect = (frame) => {
      setConnected(true);
      console.log('Connected: ' + frame);
      loadChat(chatList);

      stompClientGm.subscribe('/sub/gm/' + school.no, (gm) => {
          showMessage(JSON.parse(gm.body));
          console.log("subscribe");
      });
  };

  stompClientGm.onWebSocketError = (error) => {
      console.error('Error with websocket', error);
  };

  stompClientGm.onStompError = (frame) => {
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
      stompClientGm.activate();
  }

  function disconnect() {
      stompClientGm.deactivate();
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
            stompClientGm.publish({
            destination: "/pub/gm",
            body: JSON.stringify({
              'school': {no : school.no, name : school.name, photo : school.photo },
              'sender': {no : result.sender.no, nickname : sender.nickname, photo : sender.photo, name : sender.name},
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
          "<div class='chat-wrap sender-wrap'>" +
          "<div class = 'message-text'>" + gm.message + "</div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
          "</div>"
          );
        }
        if(gm.photo != null && gm.photo.length > 0) {
          let filePath = (sender.photo.length > 0) ? gmFileCdnDomain + "user/" + sender.photo + fileSize_40 : "/img/user-icons-default.png";
          $("#conversation").append(
          "<div class='chat-wrap sender-wrap'>" +
          "<div class = 'message-container sent-message'><div class = 'message-text'>" +
          "<a href=" + gmFilePath + gm.photo + ">" +
          "<img src=" + gmFileCdnDomain + gm.photo + fileSize_40 + "></a>" +
          "</div></div>" +
          "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
          "</div>"
          );
        }
      } else {
        let uProfileImg = "<img class='uPhotoItem profile-wrap' src=" + userFileCdnDomain + gm.sender.photo + fileSize_40 + " onerror=profileImgError(this)>";
        if(gm.message != null && gm.message.length > 0) {
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
                  "<div class='username receiver'>" + gm.sender.nickname + "</div>" +
                  "<div class='message-wrap'>" +
                      "<div class = 'message-text'>" + gm.message + "</div>" +
                      "<div class='origin'>" + "<span class='originText'>" + h + ":" + m + "</span>" + "</div>" +
                  "</div>" +
              "</div>" +
          "</div>"
          );
        }
        if(gm.photo != null && gm.photo.length > 0) {
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
                  "<div class='username receiver'>" + gm.sender.nickname + "</div>" +
                  "<div class='message-wrap'>" +
                      "<div class = 'message-text'>" +
                          "<img src=" + gmFileCdnDomain + gm.photo + fileSize_40 + " data-link='" + gmFilePath + gm.photo  + "' onclick='clickFile(this)'>" +
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
      if(chatList != null) {
        for(chat in chatList) {
          let d = chatList[chat].sendDate.substring(0, 10);
          let h = chatList[chat].sendDate.substring(11, 13);
          let m = chatList[chat].sendDate.substring(14, 16);
          if(chatList[chat].sender.no == sender.no) {
            if(chatList[chat].message.length > 0) {
              $("#conversation").append(
              "<div class='chat-wrap sender-wrap'>" +
              "<div class = 'message-text'>" + chatList[chat].message +
              "</div>" +
              "<div class='origin'>" + "<span class='originText'>" +h + ":" + m + "</span>" + "</div>" +
              "</div>"
              );
            }
            if(chatList[chat].photo.length > 0) {
              $("#conversation").append(
              "<div class='chat-wrap sender-wrap'>" +
              "<div class = 'message-text'>" +
                "<img src=" + gmFileCdnDomain + chatList[chat].photo + fileSize_40 + " data-link='" + gmFilePath + chatList[chat].photo  + "' onclick='clickFile(this)'>" +
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
                              "<img src=" + gmFileCdnDomain + chatList[chat].photo + fileSize_40 + " data-link='" + gmFilePath + chatList[chat].photo  + "' onclick='clickFile(this)'>" +
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
    console.log(photo);
  }

  function setMessage() {
    $("#photo").val('');
    photo = $("#photo")[0].files;
    $("#file-count").text(0);
  }

  function profileImgError(obj) {
    $(obj).attr("src", "/img/moyeoraLogo2.png");
  }

  function clickFile(obj) {
    window.open(obj.dataset.link, 'file_view', 'width=700px,height=800px,scrollbars=yes');
  }

  $(function () {
      connect();
      $("form").on('submit', (e) => e.preventDefault());
      $( "#disconnect" ).click(() => disconnect());
      $( "#send" ).click(() => sendMessage());
      $( "#photo" ).change(() => setFiles());
      $( "#message" ).change(() => setMessage());
  });