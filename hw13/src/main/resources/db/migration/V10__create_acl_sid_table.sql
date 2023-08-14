CREATE TABLE IF NOT EXISTS public.acl_sid
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    sid varchar(50) NOT NULL,
    principal int NOT NULL,
    CONSTRAINT acl_sid_pk PRIMARY KEY (id)
);

INSERT INTO public.acl_sid (sid, principal) VALUES ('ROLE_USER', 0);
INSERT INTO public.acl_sid (sid, principal) VALUES ('ROLE_ADMIN', 0);