CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    role varchar(40) NOT NULL,
    CONSTRAINT roles_pk PRIMARY KEY (id)
);

INSERT INTO public.roles (role) VALUES('ROLE_ADMIN');
INSERT INTO public.roles (role) VALUES('ROLE_USER');