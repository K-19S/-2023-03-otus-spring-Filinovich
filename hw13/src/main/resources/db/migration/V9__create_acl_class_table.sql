CREATE TABLE IF NOT EXISTS public.acl_class
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    class varchar(100) NOT NULL,
    CONSTRAINT acl_class_pk PRIMARY KEY (id)
);

INSERT INTO public.acl_class (class) VALUES ('ru.otus.filinovich.domain.Book');