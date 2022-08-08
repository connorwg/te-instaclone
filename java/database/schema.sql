BEGIN;
DROP TABLE IF EXISTS is_favorited, liked_by_user,comments, pictures,users;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    password_hash character varying(200) NOT NULL,
    role character varying(50) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS public.pictures
(
    picture_id serial NOT NULL,
    user_id integer NOT NULL,
    s3_link character varying NOT NULL,
    description character varying(200),
    "time" timestamp with time zone NOT NULL,
    PRIMARY KEY (picture_id)
);

CREATE TABLE IF NOT EXISTS public.comments
(
    comment_id serial NOT NULL,
    comment character varying(200) NOT NULL,
    user_id integer NOT NULL,
    picture_id integer,
    PRIMARY KEY (picture_id)
);

CREATE TABLE IF NOT EXISTS public.liked_by_user
(
    user_id integer NOT NULL,
    picture_id integer NOT NULL,
    liked_by_user boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS public.is_favorited
(
    picture_id integer NOT NULL,
    user_id integer NOT NULL,
    is_favorited boolean NOT NULL
);

ALTER TABLE public.pictures
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.liked_by_user
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.liked_by_user
    ADD FOREIGN KEY (picture_id)
    REFERENCES public.pictures (picture_id)
    NOT VALID;


ALTER TABLE public.is_favorited
    ADD FOREIGN KEY (picture_id)
    REFERENCES public.pictures (picture_id)
    NOT VALID;


ALTER TABLE public.is_favorited
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;


ALTER TABLE public.comments
    ADD FOREIGN KEY (picture_id)
    REFERENCES public.pictures (picture_id)
    NOT VALID;


ALTER TABLE public.comments
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    NOT VALID;

END;