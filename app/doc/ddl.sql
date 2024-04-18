-- 게시글 분류
CREATE TABLE `categories` (
	`category_no` INT         NOT NULL, -- 게시글 분류 번호
	`name`        VARCHAR(30) NOT NULL  -- 게시글 분류명
);

-- 게시글 분류
ALTER TABLE `categories`
	ADD CONSTRAINT `PK_categories` -- 게시글 분류 기본키
	PRIMARY KEY (
	`category_no` -- 게시글 분류 번호
	);

ALTER TABLE `categories`
	MODIFY COLUMN `category_no` INT NOT NULL AUTO_INCREMENT;

-- 일대일채팅
CREATE TABLE `dm` (
	`dm_no`      INT          NOT NULL, -- 일대일채팅번호
	`user_no2`   INT          NOT NULL, -- 보내는이
	`user_no`    INT          NOT NULL, -- 받는이
	`message`    MEDIUMTEXT   NOT NULL, -- 메시지
	`created_at` DATETIME     NOT NULL, -- 보낸시간
	`photo`      VARCHAR(255) NULL      -- 사진
);

-- 일대일채팅
ALTER TABLE `dm`
	ADD CONSTRAINT `PK_dm` -- 일대일채팅 기본키
	PRIMARY KEY (
	`dm_no`,    -- 일대일채팅번호
	`user_no2`, -- 보내는이
	`user_no`   -- 받는이
	);

ALTER TABLE `dm`
	MODIFY COLUMN `dm_no` INT NOT NULL AUTO_INCREMENT;

-- 좋아요
CREATE TABLE `likes` (
	`user_no` INT NOT NULL, -- 회원 번호
	`post_no` INT NOT NULL  -- 게시글 번호
);

-- 좋아요
ALTER TABLE `likes`
	ADD CONSTRAINT `PK_likes` -- 좋아요 기본키
	PRIMARY KEY (
	`user_no`, -- 회원 번호
	`post_no`  -- 게시글 번호
	);

-- 스쿨태그
CREATE TABLE `school_tags` (
	`tag_no`    INT NOT NULL, -- 태그번호
	`school_no` INT NOT NULL  -- 스쿨번호
);

-- 스쿨태그
ALTER TABLE `school_tags`
	ADD CONSTRAINT `PK_school_tags` -- 스쿨태그 기본키
	PRIMARY KEY (
	`tag_no`,    -- 태그번호
	`school_no`  -- 스쿨번호
	);

-- 활동 인덱싱
CREATE TABLE `logIndexes` (
	`action_no` INT         NOT NULL, -- 활동번호
	`name`      VARCHAR(30) NOT NULL  -- 활동명
);

-- 활동 인덱싱
ALTER TABLE `logIndexes`
	ADD CONSTRAINT `PK_logIndexes` -- 활동 인덱싱 기본키
	PRIMARY KEY (
	`action_no` -- 활동번호
	);

ALTER TABLE `logIndexes`
	MODIFY COLUMN `action_no` INT NOT NULL AUTO_INCREMENT;

-- users
CREATE TABLE `users` (
	`user_no`      INT           NOT NULL, -- 회원 번호
	`name`         VARCHAR(30)   NOT NULL, -- 이름
	`pwd`          VARCHAR(50)   NULL,     -- 비밀번호
	`address`      VARCHAR(255)  NOT NULL, -- 주소
	`phone`        VARCHAR(30)   NOT NULL, -- 전화번호
	`email`        VARCHAR(30)   NOT NULL, -- 이메일
	`nickname`     VARCHAR(30)   NOT NULL, -- 닉네임
	`birth`        VARCHAR(10)   NOT NULL, -- 생년월일
	`gender`       TINYINT       NOT NULL, -- 성별
	`grade`        INT           NOT NULL DEFAULT 0, -- 회원 등급
	`login_type`   INT           NOT NULL DEFAULT 0, -- 로그인 형태
	`created_at`   DATETIME      NOT NULL, -- 가입일
	`profile`      VARCHAR(1000) NULL,     -- 자기소개
	`manner_point` INT           NOT NULL, -- 매너온도
	`photo`        VARCHAR(255)  NOT NULL  -- 회원사진
);

-- users
ALTER TABLE `users`
	ADD CONSTRAINT `PK_users` -- users 기본키
	PRIMARY KEY (
	`user_no` -- 회원 번호
	);

-- users 유니크 인덱스
CREATE UNIQUE INDEX `UIX_users`
	ON `users` ( -- users
		`nickname` ASC -- 닉네임
	);

-- users 유니크 인덱스2
CREATE UNIQUE INDEX `UIX_users2`
	ON `users` ( -- users
		`email` ASC -- 이메일
	);

ALTER TABLE `users`
	MODIFY COLUMN `user_no` INT NOT NULL AUTO_INCREMENT;

-- 알림
CREATE TABLE `alerts` (
	`alert_no`      INT          NOT NULL, -- 알림번호
	`user_no`       INT          NOT NULL, -- 회원 번호
	`name`          VARCHAR(50)  NOT NULL, -- 알림명
	`type`          INT          NOT NULL, -- 알림구분
	`content`       VARCHAR(255) NOT NULL, -- 알림내용
	`created_at`    DATETIME     NOT NULL, -- 생성일시
	`is_read`       TINYINT      NOT NULL DEFAULT 0, -- 읽음
	`photo`         VARCHAR(255) NOT NULL, -- 사진
	`redirect_path` VARCHAR(255) NOT NULL  -- 리다이렉트경로
);

-- 알림
ALTER TABLE `alerts`
	ADD CONSTRAINT `PK_alerts` -- 알림 기본키
	PRIMARY KEY (
	`alert_no` -- 알림번호
	);

ALTER TABLE `alerts`
	MODIFY COLUMN `alert_no` INT NOT NULL AUTO_INCREMENT;

-- 구매내역
CREATE TABLE `purchases` (
	`purchase_no`     INT          NOT NULL, -- 구매번호
	`user_no`         INT          NOT NULL, -- 회원 번호
	`price`           INT          NOT NULL, -- 금액
	`purchase_at`     DATETIME     NOT NULL, -- 구매일
	`payment_type`    VARCHAR(30)  NOT NULL, -- 결제수단
	`vigo`            VARCHAR(255) NULL,     -- 비고
	`expiration_date` DATETIME     NOT NULL  -- 유효기간
);

-- 구매내역
ALTER TABLE `purchases`
	ADD CONSTRAINT `PK_purchases` -- 구매내역 기본키
	PRIMARY KEY (
	`purchase_no`, -- 구매번호
	`user_no`      -- 회원 번호
	);

ALTER TABLE `purchases`
	MODIFY COLUMN `purchase_no` INT NOT NULL AUTO_INCREMENT;

-- 로그인토큰
CREATE TABLE `tokens` (
	`COL`  INT          NULL, -- 회원번호
	`COL2` VARCHAR(255) NULL, -- 액세스토큰
	`COL4` VARCHAR(255) NULL  -- 리프레시토큰
);

-- 팔로잉
CREATE TABLE `follows` (
	`user_no2` INT NOT NULL, -- 팔로잉
	`user_no`  INT NOT NULL  -- 팔로워
);

-- 팔로잉
ALTER TABLE `follows`
	ADD CONSTRAINT `PK_follows` -- 팔로잉 기본키
	PRIMARY KEY (
	`user_no2`, -- 팔로잉
	`user_no`   -- 팔로워
	);

-- 일정 참여 멤버
CREATE TABLE `class_users` (
	`user_no`   INT NOT NULL, -- 회원 번호
	`school_no` INT NOT NULL, -- 스쿨번호
	`class_no`  INT NOT NULL  -- 일정번호
);

-- 일정 참여 멤버
ALTER TABLE `class_users`
	ADD CONSTRAINT `PK_class_users` -- 일정 참여 멤버 기본키
	PRIMARY KEY (
	`user_no`,   -- 회원 번호
	`school_no`, -- 스쿨번호
	`class_no`   -- 일정번호
	);

-- 스쿨회원
CREATE TABLE `school_users` (
	`user_no`    INT      NOT NULL, -- 회원 번호
	`school_no`  INT      NOT NULL, -- 스쿨번호
	`grade_no`   INT      NOT NULL, -- 회원등급번호
	`created_at` DATETIME NOT NULL  -- 가입일시
);

-- 스쿨회원
ALTER TABLE `school_users`
	ADD CONSTRAINT `PK_school_users` -- 스쿨회원 기본키
	PRIMARY KEY (
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	);

-- schools
CREATE TABLE `schools` (
	`school_no`   INT           NOT NULL, -- 스쿨번호
	`name`        VARCHAR(30)   NOT NULL, -- 스쿨명
	`content`     VARCHAR(1000) NULL,     -- 스쿨설명
	`limited_man` INT           NOT NULL, -- 제한인원
	`photo`       VARCHAR(255)  NOT NULL DEFAULT 30, -- 스쿨사진
	`open_range`  INT           NOT NULL  -- 공개범위
);


-- schools
ALTER TABLE `schools`
	ADD CONSTRAINT `PK_schools` -- schools 기본키
	PRIMARY KEY (
	`school_no` -- 스쿨번호
	);

ALTER TABLE schools
ALTER COLUMN limited_man SET DEFAULT 30;

-- schools 유니크 인덱스
CREATE UNIQUE INDEX `UIX_schools`
	ON `schools` ( -- schools
		`name` ASC -- 스쿨명
	);

ALTER TABLE `schools`
	MODIFY COLUMN `school_no` INT NOT NULL AUTO_INCREMENT;

-- 일정
CREATE TABLE `classes` (
	`class_no`   INT          NOT NULL, -- 일정번호
	`school_no`  INT          NOT NULL, -- 스쿨번호
	`user_no`    INT          NOT NULL, -- 회원 번호
	`name`       VARCHAR(50)  NOT NULL, -- 일정명
	`security`   TINYINT      NOT NULL DEFAULT 0, -- 외부접근여부
	`content`    MEDIUMTEXT   NOT NULL, -- 일정내용
	`location`   VARCHAR(255) NOT NULL, -- 일정위치
	`now_at`     DATETIME     NOT NULL, -- 일정
	`created_at` DATETIME     NOT NULL, -- 일정 시작일
	`ended_at`   DATETIME     NOT NULL, -- 모집마감일
	`member`     INT          NOT NULL, -- 일정 인원
	`repeat_set` TINYINT      NOT NULL, -- 정기일정여부
	`photo`      VARCHAR(255) NOT NULL  -- 사진
);

-- 일정
ALTER TABLE `classes`
	ADD CONSTRAINT `PK_classes` -- 일정 기본키
	PRIMARY KEY (
	`class_no` -- 일정번호
	);

ALTER TABLE `classes`
	MODIFY COLUMN `class_no` INT NOT NULL AUTO_INCREMENT;

-- 파일
CREATE TABLE `files` (
	`file_no`    INT          NOT NULL, -- 파일번호
	`post_no`    INT          NOT NULL, -- 게시글 번호
	`name`       VARCHAR(255) NOT NULL, -- 파일명
	`path`       VARCHAR(255) NOT NULL, -- 파일경로
	`size`       VARCHAR(30)  NOT NULL, -- 파일크기
	`created_at` DATETIME     NOT NULL, -- 생성일자
	`type`       VARCHAR(10)  NULL      -- 파일타입
);

-- 파일
ALTER TABLE `files`
	ADD CONSTRAINT `PK_files` -- 파일 기본키
	PRIMARY KEY (
	`file_no` -- 파일번호
	);

ALTER TABLE `files`
	MODIFY COLUMN `file_no` INT NOT NULL AUTO_INCREMENT;

-- 스쿨채팅
CREATE TABLE `gm` (
	`gm_no`      INT          NOT NULL, -- 스쿨채팅번호
	`school_no`  INT          NOT NULL, -- 스쿨번호
	`user_no`    INT          NOT NULL, -- 회원 번호
	`message`    MEDIUMTEXT   NOT NULL, -- 메시지
	`created_at` DATETIME     NOT NULL, -- 보낸시간
	`photo`      VARCHAR(255) NOT NULL  -- 사진
);

-- 스쿨채팅
ALTER TABLE `gm`
	ADD CONSTRAINT `PK_gm` -- 스쿨채팅 기본키
	PRIMARY KEY (
	`gm_no` -- 스쿨채팅번호
	);

ALTER TABLE `gm`
	MODIFY COLUMN `gm_no` INT NOT NULL AUTO_INCREMENT;

-- 태그
CREATE TABLE `tags` (
	`tag_no` INT         NOT NULL, -- 태그번호
	`name`   VARCHAR(30) NOT NULL  -- 태그이름
);

-- 태그
ALTER TABLE `tags`
	ADD CONSTRAINT `PK_tags` -- 태그 기본키
	PRIMARY KEY (
	`tag_no` -- 태그번호
	);

ALTER TABLE `tags`
	MODIFY COLUMN `tag_no` INT NOT NULL AUTO_INCREMENT;

-- grades
CREATE TABLE `grades` (
	`grade_no` INT         NOT NULL, -- 회원등급번호
	`name`     VARCHAR(30) NOT NULL  -- 등급명
);

-- grades
ALTER TABLE `grades`
	ADD CONSTRAINT `PK_grades` -- grades 기본키
	PRIMARY KEY (
	`grade_no` -- 회원등급번호
	);

ALTER TABLE `grades`
	MODIFY COLUMN `grade_no` INT NOT NULL AUTO_INCREMENT;

-- 게시글
CREATE TABLE `posts` (
	`post_no`     INT         NOT NULL, -- 게시글 번호
	`school_no`   INT         NOT NULL, -- 스쿨번호
	`user_no`     INT         NOT NULL, -- 회원 번호
	`category_no` INT         NOT NULL, -- 게시글 분류 번호
	`title`       VARCHAR(50) NOT NULL, -- 제목
	`content`     MEDIUMTEXT  NOT NULL  -- 내용
);

-- 게시글
ALTER TABLE `posts`
	ADD CONSTRAINT `PK_posts` -- 게시글 기본키
	PRIMARY KEY (
	`post_no` -- 게시글 번호
	);

ALTER TABLE `posts`
	MODIFY COLUMN `post_no` INT NOT NULL AUTO_INCREMENT;

-- 댓글
CREATE TABLE `comments` (
	`comment_no` INT           NOT NULL, -- 댓글번호
	`content`    varchar(1000) NOT NULL, -- 댓글내용
	`created_at` DATETIME      NOT NULL, -- 댓글 작성일
	`user_no`    INT           NOT NULL, -- 회원 번호
	`post_no`    INT           NOT NULL  -- 게시글 번호
);

-- 댓글
ALTER TABLE `comments`
	ADD CONSTRAINT `PK_comments` -- 댓글 기본키
	PRIMARY KEY (
	`comment_no` -- 댓글번호
	);

ALTER TABLE `comments`
	MODIFY COLUMN `comment_no` INT NOT NULL AUTO_INCREMENT;

-- 회원태그
CREATE TABLE `member_tags` (
	`user_no` INT NOT NULL, -- 회원 번호
	`tag_no`  INT NOT NULL  -- 태그번호
);

-- 회원태그
ALTER TABLE `member_tags`
	ADD CONSTRAINT `PK_member_tags` -- 회원태그 기본키
	PRIMARY KEY (
	`user_no`, -- 회원 번호
	`tag_no`   -- 태그번호
	);

-- 로그
CREATE TABLE `logs` (
	`log_no`     INT      NOT NULL, -- 로그번호
	`action_no`  INT      NOT NULL, -- 활동번호
	`user_no`    INT      NOT NULL, -- 회원 번호
	`created_at` DATETIME NOT NULL  -- 날짜
);

-- 로그
ALTER TABLE `logs`
	ADD CONSTRAINT `PK_logs` -- 로그 기본키
	PRIMARY KEY (
	`log_no`,    -- 로그번호
	`action_no`, -- 활동번호
	`user_no`    -- 회원 번호
	);

ALTER TABLE `logs`
	MODIFY COLUMN `log_no` INT NOT NULL AUTO_INCREMENT;

-- 일대일채팅
ALTER TABLE `dm`
	ADD CONSTRAINT `FK_users_TO_dm` -- users -> 일대일채팅
	FOREIGN KEY (
	`user_no2` -- 보내는이
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 일대일채팅
ALTER TABLE `dm`
	ADD CONSTRAINT `FK_users_TO_dm2` -- users -> 일대일채팅2
	FOREIGN KEY (
	`user_no` -- 받는이
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 좋아요
ALTER TABLE `likes`
	ADD CONSTRAINT `FK_users_TO_likes` -- users -> 좋아요
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 좋아요
ALTER TABLE `likes`
	ADD CONSTRAINT `FK_posts_TO_likes` -- 게시글 -> 좋아요
	FOREIGN KEY (
	`post_no` -- 게시글 번호
	)
	REFERENCES `posts` ( -- 게시글
	`post_no` -- 게시글 번호
	);

-- 스쿨태그
ALTER TABLE `school_tags`
	ADD CONSTRAINT `FK_tags_TO_school_tags` -- 태그 -> 스쿨태그
	FOREIGN KEY (
	`tag_no` -- 태그번호
	)
	REFERENCES `tags` ( -- 태그
	`tag_no` -- 태그번호
	);

-- 스쿨태그
ALTER TABLE `school_tags`
	ADD CONSTRAINT `FK_schools_TO_school_tags` -- schools -> 스쿨태그
	FOREIGN KEY (
	`school_no` -- 스쿨번호
	)
	REFERENCES `schools` ( -- schools
	`school_no` -- 스쿨번호
	);

-- 알림
ALTER TABLE `alerts`
	ADD CONSTRAINT `FK_users_TO_alerts` -- users -> 알림
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 구매내역
ALTER TABLE `purchases`
	ADD CONSTRAINT `FK_users_TO_purchases` -- users -> 구매내역
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 팔로잉
ALTER TABLE `follows`
	ADD CONSTRAINT `FK_users_TO_follows` -- users -> 팔로잉
	FOREIGN KEY (
	`user_no2` -- 팔로잉
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 팔로잉
ALTER TABLE `follows`
	ADD CONSTRAINT `FK_users_TO_follows2` -- users -> 팔로잉2
	FOREIGN KEY (
	`user_no` -- 팔로워
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 일정 참여 멤버
ALTER TABLE `class_users`
	ADD CONSTRAINT `FK_school_users_TO_class_users` -- 스쿨회원 -> 일정 참여 멤버
	FOREIGN KEY (
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	)
	REFERENCES `school_users` ( -- 스쿨회원
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	);

-- 일정 참여 멤버
ALTER TABLE `class_users`
	ADD CONSTRAINT `FK_classes_TO_class_users` -- 일정 -> 일정 참여 멤버
	FOREIGN KEY (
	`class_no` -- 일정번호
	)
	REFERENCES `classes` ( -- 일정
	`class_no` -- 일정번호
	);

-- 스쿨회원
ALTER TABLE `school_users`
	ADD CONSTRAINT `FK_users_TO_school_users` -- users -> 스쿨회원
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 스쿨회원
ALTER TABLE `school_users`
	ADD CONSTRAINT `FK_schools_TO_school_users` -- schools -> 스쿨회원
	FOREIGN KEY (
	`school_no` -- 스쿨번호
	)
	REFERENCES `schools` ( -- schools
	`school_no` -- 스쿨번호
	);

-- 스쿨회원
ALTER TABLE `school_users`
	ADD CONSTRAINT `FK_grades_TO_school_users` -- grades -> 스쿨회원
	FOREIGN KEY (
	`grade_no` -- 회원등급번호
	)
	REFERENCES `grades` ( -- grades
	`grade_no` -- 회원등급번호
	);

-- 일정
ALTER TABLE `classes`
	ADD CONSTRAINT `FK_school_users_TO_classes` -- 스쿨회원 -> 일정
	FOREIGN KEY (
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	)
	REFERENCES `school_users` ( -- 스쿨회원
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	);

-- 파일
ALTER TABLE `files`
	ADD CONSTRAINT `FK_posts_TO_files` -- 게시글 -> 파일
	FOREIGN KEY (
	`post_no` -- 게시글 번호
	)
	REFERENCES `posts` ( -- 게시글
	`post_no` -- 게시글 번호
	);

-- 스쿨채팅
ALTER TABLE `gm`
	ADD CONSTRAINT `FK_school_users_TO_gm` -- 스쿨회원 -> 스쿨채팅
	FOREIGN KEY (
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	)
	REFERENCES `school_users` ( -- 스쿨회원
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	);

-- 게시글
ALTER TABLE `posts`
	ADD CONSTRAINT `FK_categories_TO_posts` -- 게시글 분류 -> 게시글
	FOREIGN KEY (
	`category_no` -- 게시글 분류 번호
	)
	REFERENCES `categories` ( -- 게시글 분류
	`category_no` -- 게시글 분류 번호
	);

-- 게시글
ALTER TABLE `posts`
	ADD CONSTRAINT `FK_school_users_TO_posts` -- 스쿨회원 -> 게시글
	FOREIGN KEY (
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	)
	REFERENCES `school_users` ( -- 스쿨회원
	`user_no`,   -- 회원 번호
	`school_no`  -- 스쿨번호
	);

-- 댓글
ALTER TABLE `comments`
	ADD CONSTRAINT `FK_users_TO_comments` -- users -> 댓글
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 댓글
ALTER TABLE `comments`
	ADD CONSTRAINT `FK_posts_TO_comments` -- 게시글 -> 댓글
	FOREIGN KEY (
	`post_no` -- 게시글 번호
	)
	REFERENCES `posts` ( -- 게시글
	`post_no` -- 게시글 번호
	);

-- 회원태그
ALTER TABLE `member_tags`
	ADD CONSTRAINT `FK_users_TO_member_tags` -- users -> 회원태그
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

-- 회원태그
ALTER TABLE `member_tags`
	ADD CONSTRAINT `FK_tags_TO_member_tags` -- 태그 -> 회원태그
	FOREIGN KEY (
	`tag_no` -- 태그번호
	)
	REFERENCES `tags` ( -- 태그
	`tag_no` -- 태그번호
	);

-- 로그
ALTER TABLE `logs`
	ADD CONSTRAINT `FK_logIndexes_TO_logs` -- 활동 인덱싱 -> 로그
	FOREIGN KEY (
	`action_no` -- 활동번호
	)
	REFERENCES `logIndexes` ( -- 활동 인덱싱
	`action_no` -- 활동번호
	);

-- 로그
ALTER TABLE `logs`
	ADD CONSTRAINT `FK_users_TO_logs` -- users -> 로그
	FOREIGN KEY (
	`user_no` -- 회원 번호
	)
	REFERENCES `users` ( -- users
	`user_no` -- 회원 번호
	);

