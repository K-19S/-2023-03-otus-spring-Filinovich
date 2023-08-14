CREATE TABLE IF NOT EXISTS public.books_authors
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT book_authors_pk PRIMARY KEY (id),
    CONSTRAINT book_authors_book_fk FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE,
    CONSTRAINT book_authors_authors_fk FOREIGN KEY (author_id) REFERENCES public.authors(id) ON DELETE CASCADE,
);

INSERT INTO public.books_authors (book_id, author_id) VALUES(1, 1);
INSERT INTO public.books_authors (book_id, author_id) VALUES(2, 2);
INSERT INTO public.books_authors (book_id, author_id) VALUES(3, 3);
INSERT INTO public.books_authors (book_id, author_id) VALUES(4, 4);
INSERT INTO public.books_authors (book_id, author_id) VALUES(5, 5);
INSERT INTO public.books_authors (book_id, author_id) VALUES(6, 6);
INSERT INTO public.books_authors (book_id, author_id) VALUES(7, 7);
INSERT INTO public.books_authors (book_id, author_id) VALUES(8, 8);
