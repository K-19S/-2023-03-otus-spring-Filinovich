CREATE TABLE IF NOT EXISTS public.books
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    name varchar(50) NOT NULL,
    genre_id bigint,
    CONSTRAINT books_pk PRIMARY KEY (id),
    CONSTRAINT book_genre_fk FOREIGN KEY (genre_id) REFERENCES public.genres(id) ON DELETE CASCADE,
);

INSERT INTO public.books (name, genre_id) VALUES('Ten Little Niggers', 1);
INSERT INTO public.books (name, genre_id) VALUES('The Murder House', 2);
INSERT INTO public.books (name, genre_id) VALUES('IT', 3);
INSERT INTO public.books (name, genre_id) VALUES('At the Mountains of Madness', 3);
INSERT INTO public.books (name, genre_id) VALUES('Lord John and the Private Matter', 4);
INSERT INTO public.books (name, genre_id) VALUES('Sackett', 5);
INSERT INTO public.books (name, genre_id) VALUES('A Game of Thrones', 6);
INSERT INTO public.books (name, genre_id) VALUES('Harry Potter and the Deathly Hallows', 6);