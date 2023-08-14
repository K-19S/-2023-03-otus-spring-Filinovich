CREATE TABLE IF NOT EXISTS public.users_roles
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT users_roles_pk PRIMARY KEY (id),
    CONSTRAINT users_roles_users_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
    CONSTRAINT users_roles_roles_fk FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE,
);

INSERT INTO public.users_roles (user_id, role_id) VALUES(1, 2);
INSERT INTO public.users_roles (user_id, role_id) VALUES(2, 1);
INSERT INTO public.users_roles (user_id, role_id) VALUES(3, 2);