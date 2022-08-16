BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role, email, first_name, last_name) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'fake@test.com', 'John', 'Smith');
INSERT INTO users (username,password_hash,role, email, first_name, last_name) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'red@test.com', 'John', 'John');
INSERT INTO users (username,password_hash,role, email, first_name, last_name) VALUES ('user2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'blue@test.com', 'Smith', 'Smith');
INSERT INTO users (username,password_hash,role, email, first_name, last_name) VALUES ('user3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'green@test.com', 'Smith', 'John');
INSERT INTO users (username,password_hash,role, email, first_name, last_name) VALUES ('testing','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'fake@gmail.com', 'a', 'b');


INSERT INTO posts (user_id, s3_link, description, time)  VALUES (1, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_90s_desktop_computer_full_set_10.jpg', 'Got a new PC!', NOW());
INSERT INTO posts (user_id, s3_link, description, time)  VALUES (1, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_logo.jpeg', 'Our Logo!', NOW());
INSERT INTO posts (user_id, s3_link, description, time)  VALUES (2, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_90s-pattern-46.jpg', 'Might wallpaper my living room with this... thoughts??', NOW());
INSERT INTO posts (user_id, s3_link, description, time)  VALUES (3, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_Image_from_iOS.jpg', 'Web3 Networking Event', NOW());
INSERT INTO posts (user_id, s3_link, description, time)  VALUES (4, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_Untitled-2022-06-08-1411.png', 'Just a snippet from my "How to Host Lucrative Events" E-class. On Sale for just $99 a month!', NOW());
INSERT INTO posts (user_id, s3_link, description, time)  VALUES (5, 'https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/6_photo_14598_(1).jpg', 'Wow, nice grass!', NOW());

INSERT INTO comments (comment, author_id, post_id) VALUES ('wow this is cool' , 3, 1);
INSERT INTO comments (comment, author_id, post_id) VALUES ('nice hahahahahah wowowowow' , 3, 3);
INSERT INTO comments (comment, author_id, post_id) VALUES ('wow that looks good gerry !!!!! -love mom', 1, 4);
INSERT INTO comments (comment, author_id, post_id) VALUES ('art school is rlly paying off huh ????' , 2, 2);
INSERT INTO comments (comment, author_id, post_id) VALUES ('i hate this picture' , 1, 2);
--2 follows 1
INSERT INTO following VALUES (2,1);
INSERT INTO following VALUES (3,1);
INSERT INTO following VALUES (4,1);

--INSERT INTO liked_by_user VALUES (1, 2, TRUE);
--INSERT INTO liked_by_user VALUES (2, 2, TRUE);
--INSERT INTO liked_by_user VALUES (3, 2, TRUE);
--INSERT INTO liked_by_user VALUES (4, 2, TRUE);

COMMIT TRANSACTION;
