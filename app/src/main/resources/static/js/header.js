var user = loginUser;
var alerts = "";
console.log("header.js");
console.log(user);

function connect() {
  stompClient.activate();
}

const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  stompClient.subscribe('/sub/user/' + user.no, (alert) => {
    setAlert(JSON.parse(alert.body));
  });
};

function setAlert(alert) {
  console.log(alert);
  const li = "<li class='notificationItem'><a class='notificationLink' onclick=update('" + alert.redirectPath + "'," + alert.alertNo + ");>" + alert.name + "</a></li>"
  $(li).prependTo("#notificationList");
}

document.addEventListener("DOMContentLoaded", function() {
  var notificationToggle = document.getElementById("notificationToggle");
  var notificationContent = document.getElementById("notification");
  var uMenuToggle = document.getElementById("uMenuToggle");
  var uMenuContent = document.getElementById("uMenu");

  if ( notificationToggle != null ) {
    notificationToggle.addEventListener("click", function() {
  	    notificationContent.style.display = notificationContent.style.display === "block" ? "none" : "block";
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
      alerts = response;
      for(key in alerts) {
        let dateTime = alerts[key].createdAt.substring(0, 10) + " " + alerts[key].createdAt.substring(12, 16);
        let thumbnailImg = alerts[key].photo != null && alerts[key].photo.length > 0 ? "<img class='thumbnailImg' src=" + alerts[key].filePath + alerts[key].photo + " onerror=thumbnailImgError(this)>" : "";
        let notificationItem = alerts[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem unread'>";
        $("#notificationList").append(
        notificationItem +
            "<a class='notificationLink' onclick=update('" + alerts[key].redirectPath + "'," + alerts[key].alertNo + ");>" +
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
    }
  })
}

function loadChat() {
  $.ajax({
    type: "GET",
    url: "/chat/chatList?no=" + user.no,
    data: {},
    success: function () {
      location.href=url;
    }
  })
}

function update(url,no) {
  $.ajax({
    type: "GET",
    url: "/ChatList?no=" + user.no,
    data: {},
    success: function () {
      location.href=url;
    }
  })
}

function thumbnailImgError(obj) {
  $(obj).attr("src", "/img/모여라.png");
}

function updateAlerts() {
  $.ajax({
    type: "GET",
    url: "/alert/updateAll?no=" + user.no,
    data: {},
    success: function () {
      console.log("updateAll success");
    }
  })
}

$(function () {
    if ( user != null ) {
        connect();
        loadAlert();
        loadChat();
    }
    $( "#readAll" ).click(() => updateAlerts());
});