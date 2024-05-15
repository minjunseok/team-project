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
  const li = "<li class='notificationItem'><a class='notificationLink' onclick=isRead('" + alert.redirectPath + "'," + alert.alertNo + ");>" + alert.name + "</a></li>"
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
        $("#notificationList").append(
        "<li class='notificationItem'>" +
            "<a class='notificationLink' onclick=isRead('" + alerts[key].redirectPath + "'," + alerts[key].alertNo + ");>" +
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

function isRead(url,no) {
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
    $(obj).attr("src", "/img/모여라.png");
}

$(function () {
    if ( user != null ) {
        loadAlert();
        connect();
    }
});