CREATE TABLE IF NOT EXISTS public.acl_entry
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1),
    acl_object_identity bigint NOT NULL,
    ace_order bigint NOT NULL,
    sid bigint NOT NULL,
    mask int NOT NULL,
    granting int NOT NULL,
    audit_success boolean,
    audit_failure boolean,
    CONSTRAINT acl_entry_pk PRIMARY KEY (id),
    CONSTRAINT acl_entry_object_identity_fk FOREIGN KEY (acl_object_identity) REFERENCES public.acl_object_identity(id),
    CONSTRAINT acl_entry_sid_fk FOREIGN KEY (sid) REFERENCES public.acl_sid(id)
);

INSERT INTO public.acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
(1, 1, 1, 1, 1, 1, 1),
(1, 2, 2, 1, 1, 1, 1),
(1, 3, 2, 2, 1, 1, 1),

(2, 1, 1, 1, 1, 1, 1),
(2, 2, 2, 1, 1, 1, 1),
(2, 3, 2, 2, 1, 1, 1),

(3, 1, 1, 1, 1, 1, 1),
(3, 2, 2, 1, 1, 1, 1),
(3, 3, 2, 2, 1, 1, 1),

(4, 1, 1, 1, 1, 1, 1),
(4, 2, 2, 1, 1, 1, 1),
(4, 3, 2, 2, 1, 1, 1),

(5, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 2, 1, 1, 1),

(6, 1, 2, 1, 1, 1, 1),
(6, 2, 2, 2, 1, 1, 1),

(7, 1, 2, 1, 1, 1, 1),
(7, 2, 2, 2, 1, 1, 1),

(8, 1, 2, 1, 1, 1, 1),
(8, 2, 2, 2, 1, 1, 1);