CREATE TABLE IF NOT EXISTS public.book_comments
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    text varchar(150) NOT NULL,
    book_id bigint NOT NULL ,
    CONSTRAINT book_comments_pk PRIMARY KEY (id),
    CONSTRAINT comments_book_fk FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE
);

INSERT INTO public.book_comments (text, book_id) VALUES('Comment 1', 1);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 2', 2);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 3', 3);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 4', 4);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 5', 5);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 6', 6);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 7', 7);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 8', 8);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 9', 8);
INSERT INTO public.book_comments (text, book_id) VALUES('Comment 10', 8);