CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    username varchar(40) NOT NULL,
    password varchar(100) NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

INSERT INTO public.users (username, password) VALUES('user', '$2a$10$ZibML4QpwoD8gx7lJ9TyCOY5rcGCV0qbwm.iEUwiIt2GX5CpFpEzi');
INSERT INTO public.users (username, password) VALUES('admin', '$2a$10$E4DFd5D3PvsBvnBI3Gku8uxF0vlOmGaf4uI6qdz089wWyGVHcpH3u');
INSERT INTO public.users (username, password) VALUES('otus', '$2a$10$At4A2TUCaoiEglS7hbT05OMSgJddsaF3.HBbPq4XYqBkY94X/2Y0.');