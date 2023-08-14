CREATE TABLE IF NOT EXISTS public.acl_object_identity
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    object_id_class bigint NOT NULL,
    object_id_identity bigint NOT NULL,
    parent_object bigint,
    owner_sid bigint NOT NULL,
    entries_inheriting int NOT NULL,
    CONSTRAINT acl_object_identity_pk PRIMARY KEY (id),
    CONSTRAINT acloi_object_id_class_fk FOREIGN KEY (object_id_class) REFERENCES public.acl_class(id),
    CONSTRAINT acloi_owner_sid_fk FOREIGN KEY (owner_sid) REFERENCES public.acl_sid(id)
);

INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES
(1, 1, NULL, 2, 0),
(1, 2, NULL, 2, 0),
(1, 3, NULL, 2, 0),
(1, 4, NULL, 2, 0),
(1, 5, NULL, 2, 0),
(1, 6, NULL, 2, 0),
(1, 7, NULL, 2, 0),
(1, 8, NULL, 2, 0);