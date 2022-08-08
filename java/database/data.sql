BEGIN TRANSACTION;


INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('user2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO pictures (user_id, s3_link, description, time)  VALUES (1, 'https://funnypicture.com', 'this is a funny pic', NOW());
INSERT INTO pictures (user_id, s3_link, description, time)  VALUES (2, 'hahahahahaha', 'WOW', NOW());
INSERT INTO pictures (user_id, s3_link, description, time)  VALUES (3, 'BOOMER FOOD PICTURE', 'yum xD', NOW());
INSERT INTO pictures (user_id, s3_link, description, time)  VALUES (1, 'blurry pic', 'i am artsy :p', NOW());

INSERT INTO comments (comment, user_id, picture_id) VALUES ('wow this is cool' , 3, 2);
INSERT INTO comments (comment, user_id, picture_id) VALUES ('nice hahahahahah wowowowow' , 3, 3);
INSERT INTO comments (comment, user_id, picture_id) VALUES ('wow that looks good gerry !!!!! -love mom', 1, 4);
INSERT INTO comments (comment, user_id, picture_id) VALUES ('art school is rlly paying off huh ????' , 2, 5);

INSERT INTO liked_by_user VALUES (1, 2, TRUE);
INSERT INTO liked_by_user VALUES (2, 2, TRUE);
INSERT INTO liked_by_user VALUES (3, 2, TRUE);
INSERT INTO liked_by_user VALUES (4, 2, TRUE);


COMMIT TRANSACTION;
