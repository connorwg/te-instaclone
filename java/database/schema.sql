BEGIN;
DROP TABLE IF EXISTS is_favorited, liked_by_user,comments, posts,users;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(150) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(60) NOT NULL,
    profile_picture_link character varying(200),
    password_hash character varying(200) NOT NULL,
    role character varying(50) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS public.posts
(
    post_id serial NOT NULL,
    user_id integer NOT NULL,
    s3_link character varying NOT NULL,
    description character varying(200),
    "time" timestamp with time zone NOT NULL,
    PRIMARY KEY (post_id)
);

CREATE TABLE IF NOT EXISTS public.comments
(
    comment_id serial NOT NULL,
    comment character varying(200) NOT NULL,
    user_id integer NOT NULL,
    post_id integer,
    PRIMARY KEY (post_id)
);

CREATE TABLE IF NOT EXISTS public.liked_by_user
(
    user_id integer NOT NULL,
    post_id integer NOT NULL,
    liked_by_user boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS public.is_favorited
(
    post_id integer NOT NULL,
    user_id integer NOT NULL,
    is_favorited boolean NOT NULL
);

ALTER TABLE public.posts
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.liked_by_user
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.liked_by_user
    ADD FOREIGN KEY (post_id)
    REFERENCES public.posts (post_id)
    NOT VALID;


ALTER TABLE public.is_favorited
    ADD FOREIGN KEY (post_id)
    REFERENCES public.posts (post_id)
    NOT VALID;


ALTER TABLE public.is_favorited
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.comments
    ADD FOREIGN KEY (post_id)
    REFERENCES public.posts (post_id)
    NOT VALID;


ALTER TABLE public.comments
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;

END;