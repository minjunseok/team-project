drop table if exists users restrict;
drop table if exists comments restrict;
drop table if exists likes restrict;
drop table if exists logs restrict;
drop table if exists schools restrict;
drop table if exists tags restrict;
drop table if exists member_tags restrict;
drop table if exists school_users restrict;
drop table if exists tokens restrict;
drop table if exists categories restrict;
drop table if exists classes restrict;
drop table if exists class_users restrict;
drop table if exists school_tags restrict;
drop table if exists logIndexes restrict;
drop table if exists purchases restrict;
drop table if exists grades restrict;
drop table if exists follows restrict;
drop table if exists dm restrict;
drop table if exists gm restrict;
drop table if exists files restrict;
drop table if exists alerts restrict;
drop table if exists posts restrict;




insert into users
(name, pwd, address, phone, email, nickname, birth,
gender, grade, login_type, created_at, profile, manner_point, photo)
values('가가가', '123456', '충청남도 보령시',
'010-3245-1391', 'rlatmdcjf10@naver.com',
'fkfkfkfk', '20241119',0,0,0,'2024-01-01','asdewfssdfdsfdsfd',36,'test');

insert into users
(name, pwd, address, phone, email, nickname, birth,
gender, grade, login_type, created_at, profile, manner_point, photo)
values('김승철', '123456', '충청남도 익산시',
'010-3245-1321', 'godstoryyy@naver.com',
'fkfkfkfeewfk', '20231119',0,0,0,'2023-05-01','asdewfssdfdwefewfewfsfdsfd',37,'test2');

insert into users
(name, pwd, address, phone, email, nickname, birth,
gender, grade, login_type, created_at, profile, manner_point, photo)
values('임꺽정', '123456', '충청남도 아산시',
'010-3245-1321', 'aaa@test.com',
'fkfkfkfefk', '20231119',0,0,0,'2023-05-01','asdewfssdfdwefewfewfsfdsfd',37,'test3');

insert into users
(name, pwd, address, phone, email, nickname, birth,
gender, grade, login_type, created_at, profile, manner_point, photo)
values('유관순', '123456', '충청남도 천안시',
'010-3245-1321', 'aaa2@test.com',
'fkfkfkfewfewfk', '20231119',0,0,0,'2023-05-01','asdewfssdfdwefewfewfsfdsfd',37,'test4');

insert into users
(name, pwd, address, phone, email, nickname, birth,
gender, grade, login_type, created_at, profile, manner_point, photo)
values('이순신', '123456', '충청남도 공주시',
'010-3245-1321', 'aaa3@test.com',
'kfkfewfewfk', '20231119',0,0,0,'2023-05-01','asdewfssdfdwefewfewfsfdsfd',37,'test5');



insert into schools
(name, content, limited_man, photo, open_range)
values ('school1', '이것은 스쿨입니다1', 30,'test1',1);
insert into schools
(name, content, limited_man, photo, open_range)
values ('school2', '이것은 스쿨입니다2', 30,'test2',1);
insert into schools
(name, content, limited_man, photo, open_range)
values ('school3', '이것은 스쿨입니다3', 30,'test3',1);
insert into schools
(name, content, limited_man, photo, open_range)
values ('school4', '이것은 스쿨입니다4', 30,'test4',1);
insert into schools
(name, content, limited_man, photo, open_range)
values ('school5', '이것은 스쿨입니다5', 30,'test5',1);



insert into grades
(name) values ('블랙리스트');
insert into grades
(name) values ('회원');
insert into grades
(name) values ('부매니저');
insert into grades
(name) values ('매니저');
insert into grades
(name) values ('가입대기');


insert into school_users
(user_no, school_no, grade_no,created_at)
values(1,1,2,'2023-04-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(1,2,2,'2024-01-03');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(1,3,3,'2024-01-03');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(2,1,2,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(2,2,2,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(2,3,3,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(3,1,2,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(3,2,2,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(3,3,3,'2024-01-01');
insert into school_users
(user_no, school_no, grade_no,created_at)
values(4,1,4,'2024-01-01');


insert into tags
(name) values ('외식');
insert into tags
(name) values ('운동');
insert into tags
(name) values ('피크닉');
insert into tags
(name) values ('술');
insert into tags
(name) values ('스터디');
insert into tags
(name) values ('영화');
insert into tags
(name) values ('식물');
insert into tags
(name) values ('독서');
insert into tags
(name) values ('동물');
insert into tags
(name) values ('게임');
insert into tags
(name) values ('대화');
insert into tags
(name) values ('뷰티');





insert into school_tags
(tag_no, school_no) values (1,1);
insert into school_tags
(tag_no, school_no) values (1,2);
insert into school_tags
(tag_no, school_no) values (1,3);
insert into school_tags
(tag_no, school_no) values (2,1);
insert into school_tags
(tag_no, school_no) values (3,1);


insert into member_tags
(user_no, tag_no) values (1,1);
insert into member_tags
(user_no, tag_no) values (1,2);
insert into member_tags
(user_no, tag_no) values (1,3);
insert into member_tags
(user_no, tag_no) values (2,1);
insert into member_tags
(user_no, tag_no) values (3,1);


  insert into logIndexes
  (name) values ('글쓰기');
  insert into logIndexes
  (name) values ('좋아요');
  insert into logIndexes
  (name) values ('스쿨가입');


insert into categories
(name) values ('번개');
insert into categories
(name) values ('정모');
insert into categories
(name) values ('공지');
insert into categories
(name) values ('게시판');




insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (1, 1, 4, '타이틀입니다1', '번개 내용입니다1', '2023-04-01');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (1, 1, 4, '타이틀입니다2', '번개 내용입니다2', '2024-01-01');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (2, 1, 4, '1번과 다른 스쿨의 타이틀입니다1', '1번과 다른 스쿨의 번개 내용입니다1', '2024-01-04');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (3, 1, 1, '3번 스쿨 타이틀입니다1', '3번 스쿨 번개 내용입니다1', '2024-02-01');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (1, 2, 1, '1번과 다른 유저 타이틀입니다1', '1번과 다른 유저 번개 내용입니다1', '2024-03-01');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (1, 3, 1, '1번과 또 다른 유저 타이틀입니다1', '1번과 또 다른 유저 번개 내용입니다1', '2024-04-01');
insert into posts
(school_no, user_no, category_no, title, content, created_at)
values (1, 1, 1, '타이틀입니다1', '정모 내용입니다1', '2024-03-21');


insert into files(post_no,name,path,size,created_at,type)
  values(1,'가가가','test1','100','2023-05-01','jgp');
insert into files(post_no,name,path,size,created_at,type)
  values(1,'임꺽정','test2','100','2023-05-01','jgp');
insert into files(post_no,name,path,size,created_at,type)
  values(1,'이순신','test3','100','2023-05-01','jgp');


Insert into comments(content, created_at, user_no, post_no)
values('댓글내용입니다1','2024-01-01',1,1);
Insert into comments(content, created_at, user_no, post_no)
values('댓글내용입니다2','2024-02-01',2,4);
Insert into comments(content, created_at, user_no, post_no)
values('댓글내용입니다3','2024-03-01',3,3);
Insert into comments(content, created_at, user_no, post_no)
values('댓글내용입니다4','2024-04-01',2,2);
Insert into comments(content, created_at, user_no, post_no)
values('댓글내용입니다5','2024-05-01',4,2);


insert into classes
(school_no, user_no, name, security, content, location, now_at, created_at, ended_at, member, repeat_set,photo)
values (1, 1, '축구모임', 0, '재밌는 축구하실 분 모집', '비트축구장', '2024-05-09','2024-01-01','2024-01-10',11,0,'test1');
insert into classes
(school_no, user_no, name, security, content, location, now_at, created_at, ended_at, member, repeat_set,photo)
values (1, 1, '야구모임', 1, '재밌는 야구하실 분 모집', '비트야구장', '2024-04-09','2024-02-01','2024-02-10',9,1,'test2');
insert into classes
(school_no, user_no, name, security, content, location, now_at, created_at, ended_at, member, repeat_set,photo)
values (1, 1, '등산모임', 0, '재밌는 등산하실 분 모집', '비트산', '2024-06-09','2024-03-01','2024-03-10',11,0,'test3');
insert into classes
(school_no, user_no, name, security, content, location, now_at, created_at, ended_at, member, repeat_set,photo)
values (1, 1, '공부모임', 1, '재밌는 공부하실 분 모집', '비트스터디', '2024-03-23','2024-06-01','2024-06-10',11,0,'test4');
insert into classes
(school_no, user_no, name, security, content, location, now_at, created_at, ended_at, member, repeat_set,photo)
values (1, 1, '농구모임', 0, '재밌는 농구하실 분 모집', '비트농구장', '2024-08-09','2024-07-01','2024-10-10',11,0,'test5');


insert into class_users
(user_no, school_no, class_no) values (1, 1, 1);
insert into class_users
(user_no, school_no, class_no) values (2, 1, 2);
insert into class_users
(user_no, school_no, class_no) values (3, 1, 2);
insert into class_users
(user_no, school_no, class_no) values (1, 2, 2);
insert into class_users
(user_no, school_no, class_no) values (3, 1, 1);


insert into likes(user_no,post_no)
  values(1,1);
insert into likes(user_no,post_no)
  values(1,2);
insert into likes(user_no,post_no)
  values(2,1);

insert into follows(user_no2,user_no)
  values(1,1);
insert into follows(user_no2,user_no)
  values(1,2);
insert into follows(user_no2,user_no)
  values(2,1);

insert into logs(action_no,user_no,created_at)
  values(1,1,'2023-05-01');
insert into logs(action_no,user_no,created_at)
  values(2,1,'2023-05-01');
insert into logs(action_no,user_no,created_at)
  values(3,2,'2023-05-01');

insert into purchases(purchase_no, user_no, price, purchase_at, payment_type, expiration_date)
	values(1, 1, 100000, '2023-10-01', 'kakao_pay', '2024-10-01');
insert into purchases(purchase_no, user_no, price, purchase_at, payment_type, expiration_date)
	values(2, 2, 100000, '2023-11-01', 'card', '2024-11-01');
insert into purchases(purchase_no, user_no, price, purchase_at, payment_type, expiration_date)
	values(3, 3, 100000, '2023-11-10', 'naver_pay', '2024-11-10');
insert into purchases(purchase_no, user_no, price, purchase_at, payment_type, expiration_date)
	values(4, 4, 100000, '2023-12-01', 'card', '2024-12-01');
insert into purchases(purchase_no, user_no, price, purchase_at, payment_type, expiration_date)
	values(5, 5, 100000, '2023-12-15', 'kakao_pay', '2024-12-15');


insert into alerts(alert_no, user_no, name, type, content, created_at, is_read, redirect_path)
	values(1, 1, '댓글이 등록되었습니다.', 1, '안녕하세요', '2023-10-01', 0, '1');
insert into alerts(alert_no, user_no, name, type, content, created_at, is_read, redirect_path)
	values(2, 2, '회원님의 프로필을 좋아합니다.', 2, '', '2023-10-11', 0, '1');
insert into alerts(alert_no, user_no, name, type, content, created_at, is_read, redirect_path)
	values(3, 3, '스쿨에 가입했습니다.', 3, '', '2023-11-10', 0, '1');
insert into alerts(alert_no, user_no, name, type, content, created_at, is_read, redirect_path)
	values(4, 4, '구독 종료일이 임박했습니다.', 4, '', '2024-11-20', 0, '1');
insert into alerts(alert_no, user_no, name, type, content, created_at, is_read, redirect_path)
	values(5, 1, '일정이 등록되었습니다.', 5, '공부모임', '2024-3-23', 1, '4');



insert into dm(dm_no, user_no2, user_no, message, created_at)
	values(1, 1 ,2 , '안녕하세요', '2023-10-01 10:00:00');
insert into dm(dm_no, user_no2, user_no, message, created_at)
	values(2, 1 ,2 , '채팅입니다.', '2023-10-01 10:00:01');
insert into dm(dm_no, user_no2, user_no, message, created_at)
	values(3, 2 ,1 , '네. 안녕하세요.', '2023-10-01 10:01:00');
insert into dm(dm_no, user_no2, user_no, message, created_at, photo)
	values(4, 3 ,4 , '우산챙기세요.', '2023-10-01 11:02:01', '/photo/rain.jpg');
insert into dm(dm_no, user_no2, user_no, message, created_at)
	values(5, 4 ,3 , '고맙습니다.', '2023-10-01 11:02:11');
insert into dm(dm_no, user_no2, user_no, message, created_at, photo)
	values(6, 1 ,2 , '아이바오 귀여워.', '2023-10-01 11:03:01', '/photo/aibao.jpg');
insert into dm(dm_no, user_no2, user_no, message, created_at, photo)
	values(7, 2 ,1 , '러바오 귀여워.', '2023-10-01 11:03:10', '/photo/lebao.jpg');
insert into dm(dm_no, user_no2, user_no, message, created_at, photo)
	values(8, 1 ,2 , '바오가족 귀여워.', '2023-10-01 11:03:20', '/photo/baofamily.jpg');





