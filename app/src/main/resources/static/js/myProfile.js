let page2 = 0
let profileUserNo2
let flag2=false
$(window).on('scroll', function() {

  if($(window).scrollTop() + $(window).innerHeight() >= $(window)[0].scrollHeight) {
        if(page2>=1) {
         profile(profileUserNo2);
        }
      }
})

const followList2 = (u,t) => {
  profileUserNo2 = u;
  axios.get("/mypage/followList?userNo="+profileUserNo2+'&type='+t)
  .then((e)=>{
    page=0;
    $("#post-body2").empty();
    let followhtml=''
    followhtml += '<div class="row">'
    for(let dt of e.data) {
    if(profileUserNo2==dt.userNo) {
     continue;
    }
      followhtml += '<div class="col-md-6 col-lg-6 mb-3"><div class="iq-friendlist-block">'
      followhtml += '<div class="d-flex align-items-center justify-content-between">'
      followhtml += '<div class="d-flex align-items-center"><a style="cursor:pointer">'
      followhtml += '<input type="hidden" value='+dt.userNo+' class="hidden-user-no">'
      if(dt.photo==null) {
        followhtml += '<img style="width:80px; height:80px;"src="/img/default-photo.jpeg" alt="profile-img" class="img-fluid user-img"></a>'

      } else {
        followhtml += '<img src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+dt.photo+'?type=f&w=80&h=80" alt="profile-img" class="img-fluid user-img"></a>'
      }

      followhtml += '<div class="friend-info ms-3">'
      followhtml += '<h5>'+dt.nickname+'</h5><p class="mb-0">'+dt.followerCount+' follower</p></div></div>'
      followhtml += '<div class="card-header-toolbar d-flex align-items-center add-follow-btn">'
      followhtml += '<div class="dropdown">'
      console.log(dt.state)
      if(dt.state==1) {
        followhtml += '<span class="dropdown-toggle btn btn-outline-secondary me-2 follow-check-btn" data-bs-toggle="dropdown" aria-expanded="true" role="button">'
        followhtml += '<i class="ri-line me-1 text-white follow-check"></i>Follow</span>'
      }
      if(dt.state==2) {
        followhtml += '<span class="dropdown-toggle btn btn-secondary me-2 follow-check-btn" data-bs-toggle="dropdown" aria-expanded="true" role="button">'
        followhtml += '<i class="ri-check-line me-1 text-white follow-check"></i>Follow</span>'
      }
      followhtml += '</div></div></div></div></div>'
    }
    followhtml += '</div>'
    $('#post-body2').append(followhtml);
  })
}
$(document).on('click', '#post-click2', function(){
  profile2(profileUserNo2)
})
$(document).on('click', '#follower-click2', function(){
  followList2(profileUserNo2,0);
})
$(document).on('click', '#following-click2', function(){
  followList2(profileUserNo2,1);
})

const profile2 = (u) => {
  if(u=='undefined' || u==null) {
    profileUserNo2=0;
  } else {
    profileUserNo2 = u
  }
  axios.get("/mypage/profile?userNo="+profileUserNo2+"&page="+(++page2))
  .then((e)=>{

    if(page2===1) {
      $("#post-body2").empty();
      $("#profile-user-img2").empty();
      if(e.data.photo==null) {
        $("#profile-user-img2").append('<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img src="/img/default-photo.jpeg" class="avatar-130 img-fluid user-img" />')
      } else {
        $("#profile-user-img2").append('<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+e.data.photo+'?type=f&w=80&h=80" alt="profile-img" class="avatar-130 img-fluid user-img" />')
      }
      $("#profile-user-nickname2").empty()
      $("#profile-user-nickname2").append('<h3 class="">'+e.data.nickname+'</h3>')
      $("#post-count2").text(e.data.postCount)
      $("#follower-count2").text(e.data.followerCount)
      $("#following-count2").text(e.data.followingCount)
      if(e.data.grade===0) {
        $('#grade-btn').text('구독하기')
        $('#grade-btn').removeClass('btn-outline-danger')
        $('#grade-btn').addClass('btn-outline-success')
        $('#grade-text').text('회원님은 Normal 등급입니다')
      }
      if(e.data.grade===1) {
        $('#grade-text').text('회원님은 VIP 등급입니다')
      }
      if(e.data.grade===1) {
      $('#grade-text').text('회원님은 VVIP 등급입니다')
      }
    }
  if(e.data.posts[0].postNo === 0) {
        page--;
        return;
      }

    let posthtml=''
    for(let post of e.data.posts) {
    posthtml += '<div class="post-item">'
    posthtml += '<div class="user-post-data py-3">'
    posthtml += '<input type="hidden" class="post-hidden-no" value='+post.postNo+'>'
    posthtml += '<div class="d-flex justify-content-between">'
    posthtml += '<div class=" me-3">'
    posthtml += '<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img style="cursor:pointer" class="rounded-circle avatar-60 user-img" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+e.data.photo+'?type=f&w=80&h=80" alt="">'
    posthtml += '</div><div class="w-100"><div class="d-flex justify-content-between"><div class="">'
    posthtml += '<h5 class="mb-0 d-inline-block me-1"><a class="">'+e.data.nickname+'</a></h5>'
    posthtml += '<p class=" mb-0 d-inline-block">'+e.data.name+'</p>'
     const postTime = moment(post.createdAt).format('YY-MM-DD hh:mm');
    posthtml += '<p class="mb-0">'+postTime+'</p></div>'
    posthtml += '<div class="card-post-toolbar">'

    posthtml += '<div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<i class="ri-more-fill"></i></span>'
    posthtml += '<div class="dropdown-menu m-0 p-0"><a class="dropdown-item p-3">'
    posthtml += '<div class="d-flex align-items-top"><i class="ri-pencil-line h4"></i>'
    posthtml += '<div class="data ms-2"><h6>글 수정하기</h6>'
    posthtml += '<p class="mb-0">글 내용을 수정하시려면 이곳을 클릭해주세요</p>'
    posthtml += '<div class="dropdown"></div></div></a>'
    posthtml += '<a class="dropdown-item p-3">'
    posthtml += '<div class="d-flex align-items-top">'
    posthtml += '<i class="ri-delete-bin-7-line h4"></i><div class="data ms-2"><h6>Delete</h6>'
    posthtml += '<p class="mb-0">Remove thids Post on Timeline</p>'
    posthtml +='</div></div></a></div></div></div>'

    posthtml += '</div></div></div></div>'
    posthtml += '<div class="user-post text-center">'+post.content+'</div>'
    posthtml += '<div class="comment-area mt-3">'
    posthtml += '<div class="d-flex justify-content-between align-items-center flex-wrap">'
    posthtml += '<div class="like-block position-relative d-flex align-items-center">'
    posthtml += '<div class="d-flex align-items-center like-click"><div class="like-data"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<img src="/profile/images/icon/01.png" class="img-fluid" alt=""></span>'
    posthtml += '</div></div>'
    posthtml += '<div class="total-like-block ms-2 me-3"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<a class="like-count">'+post.likeCount+'</a>Likes</span></div></div></div>'
    posthtml += '<div class="total-comment-block"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += ''+post.commentCount+' Comment</span></div></div></div></div><hr>'
    for(let comment of post.comments) {
      posthtml += '<ul class="post-comments p-0 m-0"><li class="mb-2">'
      posthtml += '<div class="d-flex flex-wrap"><div><input class="comment-user-no" type="hidden" value='+comment.commentUser.commentUserNo+'>'
      if(comment.commentUser.commentUserPhoto==null) {
      posthtml += '<img style="cursor:pointer" src="/img/default-photo.jpeg" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'

      } else {
        posthtml += '<img style="cursor:pointer" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+comment.commentUser.commentUserPhoto+'?type=f&w=80&h=80" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'
      }

      posthtml += '</div>'
      posthtml += '<div class="comment-data-block ms-3"><h6>'+comment.commentUser.commentUserNickname+'</h6>'
      posthtml += '<input type="hidden" class="user-hidden-no" value='+comment.commentNo+'><p class="mb-0">'+comment.commentContent+'</p>'
      posthtml += '<div class="d-flex flex-wrap align-items-center comment-activity">'
      const commentTime = moment(comment.commentCreatedAt).format('YY-MM-DD hh:mm');
      posthtml += '<span>'+commentTime+'</span>'
      posthtml += '</div></div></div></li></ul>'
    }
    posthtml += '<form class="comment-text d-flex align-items-center mt-3" action="javascript:void(0);">'
    posthtml += '<input type="text" class="form-control rounded" placeholder="Enter Your Comment"></form></div></div>'

    }
    $("#post-body2").append(posthtml);
    $(".my-posts").addClass('active')
    $(".my-school-posts").removeClass('active')
    $(".my-follower-posts").removeClass('active')
    $(".my-hot-posts").removeClass('active')
  })
  .catch((e)=>{
    page--;
  })
}

$(document).on('click', '.user-img', function(){
  profileUserNo2 = $(this).prev().val()
  if(flag2===false) {
      const myModal = new bootstrap.Modal(document.getElementById('profile-modal'), {
      })
      myModal.show();
      flag2===true;
  }
})

$(document).on('click', '.my-school-posts', function(){
    axios.get('/mypage/schoolpost?page='+(++page2))
    .then((e)=> {
    $("#post-body2").empty();
     if(e.data.posts[0].postNo === 0) {
            page--;
            return;
          }
        let posthtml=''
        for(let post of e.data.posts) {
        posthtml += '<div class="post-item">'
        posthtml += '<div class="user-post-data py-3">'
        posthtml += '<input type="hidden" class="post-hidden-no" value='+post.postNo+'>'
        posthtml += '<div class="d-flex justify-content-between">'
        posthtml += '<div class=" me-3">'
        posthtml += '<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img style="cursor:pointer" class="rounded-circle avatar-60 user-img" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+e.data.photo+'?type=f&w=80&h=80" alt="">'
        posthtml += '</div><div class="w-100"><div class="d-flex justify-content-between"><div class="">'
        posthtml += '<h5 class="mb-0 d-inline-block me-1"><a class="">'+e.data.nickname+'</a></h5>'
        posthtml += '<p class=" mb-0 d-inline-block">'+e.data.name+'</p>'
         const postTime = moment(post.createdAt).format('YY-MM-DD hh:mm');
        posthtml += '<p class="mb-0">'+postTime+'</p></div>'
        posthtml += '<div class="card-post-toolbar">'

        posthtml += '<div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<i class="ri-more-fill"></i></span>'
        posthtml += '<div class="dropdown-menu m-0 p-0"><a class="dropdown-item p-3">'
        posthtml += '<div class="d-flex align-items-top"><i class="ri-pencil-line h4"></i>'
        posthtml += '<div class="data ms-2"><h6>글 수정하기</h6>'
        posthtml += '<p class="mb-0">글 내용을 수정하시려면 이곳을 클릭해주세요</p>'
        posthtml += '<div class="dropdown"></div></div></a>'
        posthtml += '<a class="dropdown-item p-3">'
        posthtml += '<div class="d-flex align-items-top">'
        posthtml += '<i class="ri-delete-bin-7-line h4"></i><div class="data ms-2"><h6>Delete</h6>'
        posthtml += '<p class="mb-0">Remove thids Post on Timeline</p>'
        posthtml +='</div></div></a></div></div></div>'

        posthtml += '</div></div></div></div>'
        posthtml += '<div class="user-post text-center">'+post.content+'</div>'
        posthtml += '<div class="comment-area mt-3">'
        posthtml += '<div class="d-flex justify-content-between align-items-center flex-wrap">'
        posthtml += '<div class="like-block position-relative d-flex align-items-center">'
        posthtml += '<div class="d-flex align-items-center like-click"><div class="like-data"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<img src="/profile/images/icon/01.png" class="img-fluid" alt=""></span>'
        posthtml += '</div></div>'
        posthtml += '<div class="total-like-block ms-2 me-3"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<a class="like-count">'+post.likeCount+'</a>Likes</span></div></div></div>'
        posthtml += '<div class="total-comment-block"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += ''+post.commentCount+' Comment</span></div></div></div></div><hr>'
        for(let comment of post.comments) {
          posthtml += '<ul class="post-comments p-0 m-0"><li class="mb-2">'
          posthtml += '<div class="d-flex flex-wrap"><div><input class="comment-user-no" type="hidden" value='+comment.commentUser.commentUserNo+'>'
          if(comment.commentUser.commentUserPhoto==null) {
          posthtml += '<img style="cursor:pointer" src="/img/default-photo.jpeg" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'

          } else {
            posthtml += '<img style="cursor:pointer" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+comment.commentUser.commentUserPhoto+'?type=f&w=80&h=80" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'
          }

          posthtml += '</div>'
          posthtml += '<div class="comment-data-block ms-3"><h6>'+comment.commentUser.commentUserNickname+'</h6>'
          posthtml += '<input type="hidden" class="user-hidden-no" value='+comment.commentNo+'><p class="mb-0">'+comment.commentContent+'</p>'
          posthtml += '<div class="d-flex flex-wrap align-items-center comment-activity">'
          const commentTime = moment(comment.commentCreatedAt).format('YY-MM-DD hh:mm');
          posthtml += '<span>'+commentTime+'</span>'
          posthtml += '</div></div></div></li></ul>'
        }
        posthtml += '<form class="comment-text d-flex align-items-center mt-3" action="javascript:void(0);">'
        posthtml += '<input type="text" class="form-control rounded" placeholder="Enter Your Comment"></form></div></div>'

        }
        $("#post-body2").append(posthtml);

        $(".my-posts").removeClass('active')
        $(".my-school-posts").addClass('active')
        $(".my-follower-posts").removeClass('active')
        $(".my-hot-posts").removeClass('active')
      })
      .catch((e)=>{
        page--;
      })
})

$(document).on('click', '.my-follower-posts', function(){
    axios.get('/mypage/followerpost?page='+(++page2))
    .then((e)=> {
    $("#post-body2").empty();
     if(e.data.posts[0].postNo === 0) {
            page--;
            return;
          }

        let posthtml=''
        for(let post of e.data.posts) {
        posthtml += '<div class="post-item">'
        posthtml += '<div class="user-post-data py-3">'
        posthtml += '<input type="hidden" class="post-hidden-no" value='+post.postNo+'>'
        posthtml += '<div class="d-flex justify-content-between">'
        posthtml += '<div class=" me-3">'
        posthtml += '<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img style="cursor:pointer" class="rounded-circle avatar-60 user-img" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+e.data.photo+'?type=f&w=80&h=80" alt="">'
        posthtml += '</div><div class="w-100"><div class="d-flex justify-content-between"><div class="">'
        posthtml += '<h5 class="mb-0 d-inline-block me-1"><a class="">'+e.data.nickname+'</a></h5>'
        posthtml += '<p class=" mb-0 d-inline-block">'+e.data.name+'</p>'
         const postTime = moment(post.createdAt).format('YY-MM-DD hh:mm');
        posthtml += '<p class="mb-0">'+postTime+'</p></div>'
        posthtml += '<div class="card-post-toolbar">'

        posthtml += '<div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<i class="ri-more-fill"></i></span>'
        posthtml += '<div class="dropdown-menu m-0 p-0"><a class="dropdown-item p-3">'
        posthtml += '<div class="d-flex align-items-top"><i class="ri-pencil-line h4"></i>'
        posthtml += '<div class="data ms-2"><h6>글 수정하기</h6>'
        posthtml += '<p class="mb-0">글 내용을 수정하시려면 이곳을 클릭해주세요</p>'
        posthtml += '<div class="dropdown"></div></div></a>'
        posthtml += '<a class="dropdown-item p-3">'
        posthtml += '<div class="d-flex align-items-top">'
        posthtml += '<i class="ri-delete-bin-7-line h4"></i><div class="data ms-2"><h6>Delete</h6>'
        posthtml += '<p class="mb-0">Remove thids Post on Timeline</p>'
        posthtml +='</div></div></a></div></div></div>'

        posthtml += '</div></div></div></div>'
        posthtml += '<div class="user-post text-center">'+post.content+'</div>'
        posthtml += '<div class="comment-area mt-3">'
        posthtml += '<div class="d-flex justify-content-between align-items-center flex-wrap">'
        posthtml += '<div class="like-block position-relative d-flex align-items-center">'
        posthtml += '<div class="d-flex align-items-center like-click"><div class="like-data"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<img src="/profile/images/icon/01.png" class="img-fluid" alt=""></span>'
        posthtml += '</div></div>'
        posthtml += '<div class="total-like-block ms-2 me-3"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += '<a class="like-count">'+post.likeCount+'</a>Likes</span></div></div></div>'
        posthtml += '<div class="total-comment-block"><div class="dropdown">'
        posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
        posthtml += ''+post.commentCount+' Comment</span></div></div></div></div><hr>'
        for(let comment of post.comments) {
          posthtml += '<ul class="post-comments p-0 m-0"><li class="mb-2">'
          posthtml += '<div class="d-flex flex-wrap"><div><input class="comment-user-no" type="hidden" value='+comment.commentUser.commentUserNo+'>'
          if(comment.commentUser.commentUserPhoto==null) {
          posthtml += '<img style="cursor:pointer" src="/img/default-photo.jpeg" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'

          } else {
            posthtml += '<img style="cursor:pointer" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+comment.commentUser.commentUserPhoto+'?type=f&w=80&h=80" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'
          }

          posthtml += '</div>'
          posthtml += '<div class="comment-data-block ms-3"><h6>'+comment.commentUser.commentUserNickname+'</h6>'
          posthtml += '<input type="hidden" class="user-hidden-no" value='+comment.commentNo+'><p class="mb-0">'+comment.commentContent+'</p>'
          posthtml += '<div class="d-flex flex-wrap align-items-center comment-activity">'
          const commentTime = moment(comment.commentCreatedAt).format('YY-MM-DD hh:mm');
          posthtml += '<span>'+commentTime+'</span>'
          posthtml += '</div></div></div></li></ul>'
        }
        posthtml += '<form class="comment-text d-flex align-items-center mt-3" action="javascript:void(0);">'
        posthtml += '<input type="text" class="form-control rounded" placeholder="Enter Your Comment"></form></div></div>'

        }
        $("#post-body2").append(posthtml);
         $(".my-posts").removeClass('active')
            $(".my-school-posts").removeClass('active')
            $(".my-follower-posts").addClass('active')
            $(".my-hot-posts").removeClass('active')
      })
      .catch((e)=>{
        page--;
      })

})


$(document).on('click', '.my-hot-posts', function(){
     axios.get('/mypage/likepost?page='+(++page2))
     .then((e)=> {
 if(e.data.posts[0].postNo === 0) {
        page--;
        return;
      }
    $("#post-body2").empty();
    let posthtml=''
    for(let post of e.data.posts) {
    posthtml += '<div class="post-item">'
    posthtml += '<div class="user-post-data py-3">'
    posthtml += '<input type="hidden" class="post-hidden-no" value='+post.postNo+'>'
    posthtml += '<div class="d-flex justify-content-between">'
    posthtml += '<div class=" me-3">'
    posthtml += '<input type="hidden" class="user-hidden-no" value='+e.data.userNo+'><img style="cursor:pointer" class="rounded-circle avatar-60 user-img" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+e.data.photo+'?type=f&w=80&h=80" alt="">'
    posthtml += '</div><div class="w-100"><div class="d-flex justify-content-between"><div class="">'
    posthtml += '<h5 class="mb-0 d-inline-block me-1"><a class="">'+e.data.nickname+'</a></h5>'
    posthtml += '<p class=" mb-0 d-inline-block">'+e.data.name+'</p>'
     const postTime = moment(post.createdAt).format('YY-MM-DD hh:mm');
    posthtml += '<p class="mb-0">'+postTime+'</p></div>'
    posthtml += '<div class="card-post-toolbar">'

    posthtml += '<div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<i class="ri-more-fill"></i></span>'
    posthtml += '<div class="dropdown-menu m-0 p-0"><a class="dropdown-item p-3">'
    posthtml += '<div class="d-flex align-items-top"><i class="ri-pencil-line h4"></i>'
    posthtml += '<div class="data ms-2"><h6>글 수정하기</h6>'
    posthtml += '<p class="mb-0">글 내용을 수정하시려면 이곳을 클릭해주세요</p>'
    posthtml += '<div class="dropdown"></div></div></a>'
    posthtml += '<a class="dropdown-item p-3">'
    posthtml += '<div class="d-flex align-items-top">'
    posthtml += '<i class="ri-delete-bin-7-line h4"></i><div class="data ms-2"><h6>Delete</h6>'
    posthtml += '<p class="mb-0">Remove thids Post on Timeline</p>'
    posthtml +='</div></div></a></div></div></div>'

    posthtml += '</div></div></div></div>'
    posthtml += '<div class="user-post text-center">'+post.content+'</div>'
    posthtml += '<div class="comment-area mt-3">'
    posthtml += '<div class="d-flex justify-content-between align-items-center flex-wrap">'
    posthtml += '<div class="like-block position-relative d-flex align-items-center">'
    posthtml += '<div class="d-flex align-items-center like-click"><div class="like-data"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<img src="/profile/images/icon/01.png" class="img-fluid" alt=""></span>'
    posthtml += '</div></div>'
    posthtml += '<div class="total-like-block ms-2 me-3"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += '<a class="like-count">'+post.likeCount+'</a>Likes</span></div></div></div>'
    posthtml += '<div class="total-comment-block"><div class="dropdown">'
    posthtml += '<span class="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">'
    posthtml += ''+post.commentCount+' Comment</span></div></div></div></div><hr>'
    for(let comment of post.comments) {
      posthtml += '<ul class="post-comments p-0 m-0"><li class="mb-2">'
      posthtml += '<div class="d-flex flex-wrap"><div><input class="comment-user-no" type="hidden" value='+comment.commentUser.commentUserNo+'>'
      if(comment.commentUser.commentUserPhoto==null) {
      posthtml += '<img style="cursor:pointer" src="/img/default-photo.jpeg" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'

      } else {
        posthtml += '<img style="cursor:pointer" src="https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/user/'+comment.commentUser.commentUserPhoto+'?type=f&w=80&h=80" alt="userimg" class="avatar-35 rounded-circle img-fluid user-img">'
      }

      posthtml += '</div>'
      posthtml += '<div class="comment-data-block ms-3"><h6>'+comment.commentUser.commentUserNickname+'</h6>'
      posthtml += '<input type="hidden" class="user-hidden-no" value='+comment.commentNo+'><p class="mb-0">'+comment.commentContent+'</p>'
      posthtml += '<div class="d-flex flex-wrap align-items-center comment-activity">'
      const commentTime = moment(comment.commentCreatedAt).format('YY-MM-DD hh:mm');
      posthtml += '<span>'+commentTime+'</span>'
      posthtml += '</div></div></div></li></ul>'
    }
    posthtml += '<form class="comment-text d-flex align-items-center mt-3" action="javascript:void(0);">'
    posthtml += '<input type="text" class="form-control rounded" placeholder="Enter Your Comment"></form></div></div>'

    }
    $("#post-body2").append(posthtml);
    $(".my-posts").removeClass('active')
    $(".my-school-posts").removeClass('active')
    $(".my-follower-posts").removeClass('active')
    $(".my-hot-posts").addClass('active')
  })
  .catch((e)=>{
    page--;
  })
})

$('#grade-btn').on('click', function(){
    if($(this).text()=='구독하기') {
        location.href = '/payment/form'
    } else {
        Swal.fire({
          title: "정말로 구독을 해지하시겠습니까?",
          text: "다음 달 결제일 이전까지는 효과가 지속됩니다.",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "네, 해지 하겠습니다"
        }).then((result) => {
          if (result.isConfirmed) {
            axios.post('/payment/stop')
            .then((e)=>{
                Swal.fire({
                  title: "구독 해지가 완료되었습니다."
                }).then((e)=>{
                    location.reload();
                })

            })
          }
        });
    }
})




