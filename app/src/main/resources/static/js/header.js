let userInfo = loginUser;

console.log(userInfo);

function connect() {
  stompClient.activate();
}

const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  stompClient.subscribe('/sub/user/' + userInfo.no, (alert) => {
    setAlert(JSON.parse(alert.body));
  });
};

function setAlert(alert) {
  //let dateTime = alert.createdAt.substring(0, 10) + " " + alert.createdAt.substring(12, 16);
  let dateTime = '';
  let thumbnailImg = alert.photo != null && alert.photo.length > 0 ? "<img class='thumbnailItem' src=" + alert.filePath + alert.photo + " onerror=thumbnailImgError(this)>" : "";
  let notificationItem = alert.isRead == "1" ? "<li class='notificationItem' name='notificationLi'>" : "<li class='notificationItem unread' name='notificationLi'>";
  let li = notificationItem;

  li += '<a class="notificationLink" data-path="'+ alert.redirectPath +'" data-no="'+ alert.alertNo +'" onclick="updateAlert(this)">';
  li += '   <div class="notificationThumbnail">';
  li += '       <div class="thumbnailInner">';
  li += '       <span class="thumbnailItem">' + thumbnailImg + '</span>';
  li += '       </div>';
  li += '   </div>';
  li += '   <dl class="notificationInfoBox">';
  li += '       <dd class="notificationInfo">';
  li += '           <strong class="headLineText">';
  li += '               <span class="nameText">' + alert.name + '</span>';
  li += '           </strong>';
  li += '       </dd>';
  li += '       <dd class="infoText alertContent">' + alert.content +'</dd>';
  li += '       <dd class="origin"><span class="infoText originText">'+ dateTime +'</span></dd>';
  li += '   </dl>';
  li += '</a>';
  li += '</li>';


  $(li).prependTo("#notificationList");
  document.getElementById('newNotification').style.display = '';
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


  // Close notification if userInfo clicks outside
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
    url: "/alert/list?no=" + userInfo.no,
    data: {},
    success: function (response) {
      let alerts = response;
      let checkNewNotification = false;

      for(key in alerts) {
        let dateTime = alerts[key].createdAt.substring(0, 10) + " " + alerts[key].createdAt.substring(12, 16);
        if (  alerts[key].isRead == "0" ) {
            checkNewNotification = true;
        }
        let thumbnailImg = alerts[key].photo != null && alerts[key].photo.length > 0 ? "<img class='thumbnailItem' src=" + alerts[key].filePath + alerts[key].photo + " onerror=thumbnailImgError(this)>" : "";
        let notificationItem = alerts[key].isRead == "1" ? "<li class='notificationItem' name='notificationLi'>" : "<li class='notificationItem unread' name='notificationLi'>";
        $("#notificationList").append(
        notificationItem +
            "<a class='notificationLink' data-path='"+ alerts[key].redirectPath +"' data-no='"+ alerts[key].alertNo +"' onclick='updateAlert(this)'>" +
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

      if ( checkNewNotification ) {
        document.getElementById('newNotification').style.display = '';
      }

    }
  })
}

function loadChatGm() {
  $.ajax({
    type: "GET",
    url: "/gmListOnlyLast?no=" + userInfo.no,
    data: {},
    success: function (result) {
      let schoolFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/school/";
      let fileSize_40 = "?type=f&w=40&h=40";
      for(key in result) {
        let thumbnailImg = "<img class='thumbnailItem' src=" + schoolFileCdnDomain + result[key].school.photo + fileSize_40 + " onerror=thumbnailImgError(this)>";
        let uChatItem = result[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem '>";
        let msg = ( result[key].message != null && result[key].message != "" && result[key].message.length > 0 ) ? result[key].message : "사진을 보냈습니다.";

        $("#gmList").append(
        uChatItem +
            "<a class='chatLink' onclick=chatLink('" + result[key].school.no + "'," + userInfo.no + ");>" +
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
    url: "/dmListOnlyLast?no=" + userInfo.no,
    data: {},
    success: function (result) {
      let userFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/";
      let fileSize_40 = "?type=f&w=40&h=40";
      for(key in result) {
        let thumbnailImg = "<img class='thumbnailItem' src=" + userFileCdnDomain + result[key].receiver.photo + fileSize_40 + " onerror=thumbnailImgError(this)>";
        let uChatItem = result[key].isRead == "1" ? "<li class='notificationItem'>" : "<li class='notificationItem '>";
        let msg = ( result[key].message != null && result[key].message != "" && result[key].message.length > 0 ) ? result[key].message : "사진을 보냈습니다.";

        $("#dmList").append(
        uChatItem +
            "<a class='chatLink' onclick=chatDmLink('" + result[key].receiver.no + "');>" +
                "<div class='notificationThumbnail'><div class='thumbnailInner'><span class='thumbnailItem'>" + thumbnailImg + "</span></div></div>" +
                "<dl class='notificationInfoBox'>" +
                    "<dd class='notificationInfo'>" +
                        "<string class='headLineText'><span class='nameText'>" + result[key].receiver.nickname + "</span></strong>" +
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

function updateAlert(obj) {
  $.ajax({
    type: "GET",
    url: "/alert/update?no=" + obj.dataset.no,
    data: {},
    success: function () {
      location.href=obj.dataset.path;
    }
  })
}

function thumbnailImgError(obj) {
  $(obj).attr("src", "/img/moyeoraLogo2.png");
}

function updateAlerts() {
  $.ajax({
    type: "GET",
    url: "/alert/updateAll?no=" + userInfo.no,
    data: {},
    success: function (result) {
      let list = document.getElementsByName('notificationLi');
      for(let i=0; i<list.length; i++) {
        list[i].classList.remove('unread');
      }
    }
  })
}

function chatLink(schoolNo, sender) {
    window.open("/gm?schoolNo=" + schoolNo + "&sender=" + sender, 'gm', 'width=600px,height=610px,scrollbars=yes');
}

function chatDmLink(receiver) {
    window.open("/dm?receiver=" + receiver, "dm", 'width=600px,height=610px,scrollbars=yes');
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

function setProfilePhoto() {
    let userFileCdnDomain = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/";
    let fileSize_40 = "?type=f&w=40&h=40";
    let img = "<img class='iconImg' src=" + userFileCdnDomain + userInfo.photo + fileSize_40 + " onerror=headerProfileImgError(this)>";

    document.getElementById('uMenuToggle').innerHTML = '';
    document.getElementById('uMenuToggle').innerHTML = img;
}

function headerProfileImgError(obj) {
    $(obj).attr("src", "/img/moyeoraLogo2.png");
}

$(function () {
    if ( userInfo != null ) {
        connect();
        loadAlert();
        loadChatGm();
        loadChatDm();
        setProfilePhoto();
    }
    $( "#readAll" ).click(() => updateAlerts());
});