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
  const li = "<li id=alert" + alert.alertNo + " class='alert'><a onclick=isRead('" + alert.redirectPath + "'," + alert.alertNo + ");>" + alert.name + "</a></li>"
  $(li).prependTo("#notification_list");
}

document.addEventListener("DOMContentLoaded", function() {
  var notificationToggle = document.getElementById("notificationToggle");
  var notificationContent = document.getElementById("notification");

  notificationToggle.addEventListener("click", function() {
	notificationContent.style.display = notificationContent.style.display === "block" ? "none" : "block";
	}
  );

  // Close notification if user clicks outside
  window.addEventListener("click", function(event) {
	if (event.target !== notificationToggle && !notificationContent.contains(event.target)) {
	  notificationContent.style.display = "none";
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
        $("#notification_list").append("<li id=alert" + alerts[key].alertNo + " class='alert'><a onclick=isRead('" + alerts[key].redirectPath + "'," + alerts[key].alertNo + ");>" + alerts[key].name + "</a></li>");
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

$(function () {
    loadAlert();
    connect();
});