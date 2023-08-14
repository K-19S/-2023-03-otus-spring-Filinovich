CREATE TABLE IF NOT EXISTS public.authors
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    name varchar(20) NOT NULL,
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

INSERT INTO public.authors (name) VALUES('Agatha Christie');
INSERT INTO public.authors (name) VALUES('James Patterson');
INSERT INTO public.authors (name) VALUES('Stephen King');
INSERT INTO public.authors (name) VALUES('Howard Lovecraft');
INSERT INTO public.authors (name) VALUES('Diana Gabaldon');
INSERT INTO public.authors (name) VALUES('Louis Lamour');
INSERT INTO public.authors (name) VALUES('George R. R. Martin');
INSERT INTO public.authors (name) VALUES('Joanne Rowling');