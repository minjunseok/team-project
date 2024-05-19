var user = loginUser;
console.log("header.js");
console.log(user);

function connect() {
  stompClient.activate();
}

const stompClient = new StompJs.Client({
  brokerURL: 'ws://175.45.194.120:80/ws'
});

stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  stompClient.subscribe('/sub/user/' + user.no, (alert) => {
    setAlert(JSON.parse(alert.body));
  });
};

function setAlert(alert) {
  console.log(alert);
  const li = "<li class='notificationItem'><a class='notificationLink' onclick=updateAlert('" + alert.redirectPath + "'," + alert.alertNo + ");>" + alert.name + "</a></li>"
  $(li).prependTo("#notificationList");
}

document.addEventListener("DOMContentLoaded", function() {
  var notificationToggle = document.getElementById("notificationToggle");
  var notificationContent = document.getElementById("notification");
  var uChatToggle = document.getElementById("uChatToggle");
  var uChatContent = document.getElementById("uChat");
  var uMenuToggle = document.getElementById("uMenuToggle");
  var uMenuContent = document.getElementById("uMenu");

  if ( notificationToggle != null ) {
    notificationToggle.addEventListener("click", function() {
         notificationContent.style.display = notificationContent.style.display === "block" ? "none" : "block";
    });
  }

  if ( uMenuToggle != null ) {
    uChatToggle.addEventListener("click", function() {
      uChatContent.style.display = uChatContent.style.display === "block" ? "none" : "block";
    });
  }

  if ( uMenuToggle != null ) {
    uMenuToggle.addEventListener("click", function() {
       uMenuContent.style.display = uMenuContent.style.display === "block" ? "none" : "block";
    });
  }


  // Close notification if user clicks outside
  window.addEventListener("click", function(event) {
   if (event.target !== notificationToggle && !notificationContent.contains(event.target)) {
       notificationContent.style.display = "none";
   }
   if (event.target !== uChatToggle && !uChatContent.contains(event.target)) {
       uChatContent.style.display = "none";
    }
   if (event.target !== uMenuToggle && !uMenuContent.contains(event.target)) {
       uMenuContent.style.display = "none";
    }
  });
});

function loadAlert() {
  $.ajax({
    type: "GET",
    url: "/alert/list?no=" + user.no,
    data: {},
    success: function (response) {
      let alerts = response;
      for(key in alerts) {
        let dateTime = alerts[key].createdAt.substring(0, 10) + " " + alerts[key].createdAt.substring(12, 16);
        let thumbnailImg = alerts[key].photo != null && alerts[key].photo.length > 0 ? "<img class='thumbnailItem' src=" + alerts[key].filePath + alerts[key].photo + " onerror=thumbnailImgError(this)>" : "";
        let notificationItem = alerts[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem unread'>";
        $("#notificationList").append(
        notificationItem +
            "<a class='notificationLink' onclick=updateAlert('" + alerts[key].redirectPath + "'," + alerts[key].alertNo + ");>" +
                "<div class='notificationThumbnail'>" +
                    "<div class='thumbnailInner'>" +
                        "<span class='thumbnailItem'>" + thumbnailImg + "</span>" +
                    "</div>" +
                "</div>" +
                "<dl class='notificationInfoBox'>" +
                    "<dd class='notificationInfo'>" +
                        "<strong class='headLineText'>" +
                            "<span class='nameText'>" + alerts[key].name + "</span>" +
                        "</strong>" +
                    "</dd>" +
                    "<dd class='infoText alertContent'>" + alerts[key].content + "</dd>" +
                    "<dd class='origin'>" +
                        "<span class='infoText originText'>" + dateTime + "</span>" +
                    "</dd>" +
                "</dl>" +
            "</a>" +
        "</li>"
        );
      }

      if ( alerts.length < 1 ) {
          $("#notificationList").append("<li class='notificationItem empty pt-4'>소식 목록이 없습니다.</li>");
      }

    }
  })
}

function loadChatGm() {
  $.ajax({
    type: "GET",
    url: "/gmListOnlyLast?no=" + user.no,
    data: {},
    success: function (result) {
      console.log("gmListOnlyLast success");
      console.log(result);
      for(key in result) {
        let thumbnailImg = "<img class='thumbnailItem' src=" + result[key].filePath + result[key].school.photo + " onerror=thumbnailImgError(this)>";
        let uChatItem = result[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem unread'>";
        let msg = ( result[key].message != null && result[key].message != "" && result[key].message.length > 0 ) ? result[key].message : "사진을 보냈습니다.";

        $("#gmList").append(
        uChatItem +
            "<a class='chatLink' onclick=chatLink('" + result[key].school.no + "'," + user.no + ");>" +
                "<div class='notificationThumbnail'><div class='thumbnailInner'><span class='thumbnailItem'>" + thumbnailImg + "</span></div></div>" +
                "<dl class='notificationInfoBox'>" +
                    "<dd class='notificationInfo'>" +
                        "<string class='headLineText'><span class='nameText'>" + result[key].school.name + "</span></strong>" +
                    "</dd>" +
                    "<dd class='infoText alertContent'>" + msg + "</dd>" +
                    "<dd class='origin'><span class='infoText originText'>" + result[key].sendDate + "</span></dd>" +
                "</dl>" +
            "</a>" +
        "</li>"
        );
      }

       if ( result.length < 1 ) {
           $("#gmList").append("<li class='notificationItem empty pt-4'>스쿨 채팅 목록이 없습니다.</li>");
       }
    }
  })
}

function loadChatDm() {
  $.ajax({
    type: "GET",
    url: "/dmListOnlyLast?no=" + user.no,
    data: {},
    success: function (result) {
      console.log("dmListOnlyLast success");
      console.log(result);
      for(key in result) {
        console.log(result[key].message)
        let thumbnailImg = "<img class='thumbnailItem' src=" + result[key].filePath + result[key].photo + " onerror=thumbnailImgError(this)>";
        let uChatItem = result[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem unread'>";
        let msg = ( result[key].message != null && result[key].message != "" && result[key].message.length > 0 ) ? result[key].message : "사진을 보냈습니다.";

        $("#dmList").append(
        uChatItem +
           /* "<a class='chatLink' onclick=chatLink('" + result[key].school.no + "'," + user.no + ");>" +*/
            "<a class='chatLink'>" +
                "<div class='notificationThumbnail'><div class='thumbnailInner'><span class='thumbnailItem'>" + thumbnailImg + "</span></div></div>" +
                "<dl class='notificationInfoBox'>" +
                    "<dd class='notificationInfo'>" +
                        "<string class='headLineText'><span class='nameText'>#이름출력</span></strong>" +
                    "</dd>" +
                    "<dd class='infoText alertContent'>" + msg + "</dd>" +
                    "<dd class='origin'><span class='infoText originText'>" + result[key].sendDate + "</span></dd>" +
                "</dl>" +
            "</a>" +
        "</li>"
        );
      }

       if ( result.length < 1 ) {
            $("#dmList").append("<li class='notificationItem empty pt-4'>내 채팅 목록이 없습니다.</li>");
       }
    }
  })
}

function updateAlert(url,no) {
  $.ajax({
    type: "GET",
    url: "/alert/update?no=" + no,
    data: {},
    success: function () {
      location.href=url;
    }
  })
}

function thumbnailImgError(obj) {
  $(obj).attr("src", "/img/moyeoraLogo2.png");
}

function updateAlerts() {
  $.ajax({
    type: "GET",
    url: "/alert/updateAll?no=" + user.no,
    data: {},
    success: function (result) {
      console.log("updateAll success");
      console.log(result);
    }
  })
}

function chatLink(schoolNo, sender) {
  window.open("/gm?schoolNo=" + schoolNo + "&sender=" + sender, 'gm', 'width=700px,height=800px,scrollbars=yes');
}

function onclickChatTab(obj) {
    document.getElementById("chatTabDm").classList.remove("active");
    document.getElementById("chatTabGm").classList.remove("active");
    obj.classList.add("active");

    document.getElementById("dmListWrap").style.display = "none";
    document.getElementById("gmListWrap").style.display = "none";

    if ( obj.dataset.type == "dm" ) {
        document.getElementById("dmListWrap").style.display = "";
    } else {
        document.getElementById("gmListWrap").style.display = "";
    }
}

$(function () {
    if ( user != null ) {
        connect();
        loadAlert();
        loadChatGm();
        loadChatDm();
    }
    $( "#readAll" ).click(() => updateAlerts());
});