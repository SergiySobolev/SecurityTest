create table principal (
  principal_id numeric(10) NOT NULL,
  login varchar2(50) NOT NULL,
  password varchar2(256) NOT NULL,
  enabled numeric(1) NOT NULL,
  salt varchar2(50) default null
  CONSTRAINT  principal_pk PRIMARY KEY (principal_id),
  CONSTRAINT enabled_chk check (enabled in (0,1))
);

create table role (
  role_id numeric(10) NOT NULL,
  role_name varchar2(50) NOT NULL,
  CONSTRAINT  role_pk PRIMARY KEY (role_id)
);

create table principal_role (
  principal_id numeric(10),
  role_id numeric(10),
  CONSTRAINT fk_role_principal
  FOREIGN KEY (principal_id)
  REFERENCES principal(principal_id),
  CONSTRAINT fk_role
  FOREIGN KEY (role_id)
  REFERENCES role(role_id)
);

CREATE TABLE acl_sid (
    id numeric(10) NOT NULL ,
    principal numeric(1) NOT NULL,
    sid varchar(100) NOT NULL,
    CONSTRAINT acl_sid_pk PRIMARY KEY (id),
    CONSTRAINT sid_principal_uniq UNIQUE (sid,principal)
);

CREATE TABLE acl_class (
  id numeric(10) NOT NULL ,
  class varchar(255) NOT NULL,
  CONSTRAINT acl_class_pk PRIMARY KEY (id),
  CONSTRAINT acl_class_uniq UNIQUE (class)
);

CREATE TABLE acl_entry (
  id numeric(10) NOT NULL ,
  acl_object_identity numeric(10) NOT NULL,
  ace_order numeric(10) NOT NULL,
  sid numeric(10) NOT NULL,
  mask numeric(10) NOT NULL,
  granting numeric(1) NOT NULL,
  audit_success numeric(1) NOT NULL,
  audit_failure numeric(1) NOT NULL,
  CONSTRAINT acl_entry_pk PRIMARY KEY (id),
  CONSTRAINT acl_entry_uniq UNIQUE (acl_object_identity, ace_order),
  CONSTRAINT fk_acl_entry_sid FOREIGN KEY (sid) REFERENCES acl_sid(id)
);

CREATE TABLE acl_object_identity(
  id numeric(10) NOT NULL ,
  object_id_class numeric(10) NOT NULL,
  object_id_identity numeric(10) NOT NULL,
  parent_object numeric(10) DEFAULT NULL,
  owner_sid numeric(10) DEFAULT NULL,
  entries_inheriting numeric(1)  NOT NULL,
  CONSTRAINT acl_object_identity_pk PRIMARY KEY (id),
  CONSTRAINT acl_object_identity_uniq UNIQUE (object_id_class, object_id_identity),
  CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object)
        REFERENCES acl_object_identity(id),
  CONSTRAINT fk_acl_object_identity_sid FOREIGN KEY (owner_sid) REFERENCES acl_sid(id)
);


INSERT  ALL
INTO acl_sid (id, principal, sid) VALUES (1, 1, 'john')
INTO acl_sid (id, principal, sid) VALUES (2, 1, 'jane')
INTO acl_sid (id, principal, sid) VALUES (3, 1, 'mike')

INSERT  ALL
INTO acl_class (id, class) VALUES (1, 'sbk.sprtest.acldomain.AdminPost')
INTO acl_class (id, class) VALUES(2, 'sbk.sprtest.acldomain.PersonalPost')
INTO acl_class (id, class) VALUES(3, 'sbk.sprtest.acldomain.PublicPost')


insert into principal values(1, 'sobik', 'int');
insert into roles values (2, 'role_user');
insert into roles values (3, 'role_visitor');
insert into roles values (1, 'role_admin');

INSERT ALL
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(1, 1, 1, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(2, 1, 2, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(3, 1, 3, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(4, 2, 1, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(5, 2, 2, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(6, 2, 3, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(7, 3, 1, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(8, 3, 2, NULL, 1, 0)
INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES(9, 3, 3, NULL, 1, 0)


INSERT ALL
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(1, 1, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(2, 2, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(3, 3, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(4, 1, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(5, 2, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(6, 3, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(7, 4, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(8, 5, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(9, 6, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(10, 7, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(11, 8, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(12, 9, 1, 1, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(13, 7, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(14, 8, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(15, 9, 2, 1, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(28, 4, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(29, 5, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(30, 6, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(31, 4, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(32, 5, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(33, 6, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(34, 7, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(35, 8, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(36, 9, 3, 2, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(37, 7, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(38, 8, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(39, 9, 4, 2, 2, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(40, 7, 5, 3, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(41, 8, 5, 3, 1, 1, 1, 1)
INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES(42, 9, 5, 3, 1, 1, 1, 1)

CREATE TABLE public_post (
  id numeric(20) NOT NULL ,
  created_date TIMESTAMP NOT NULL,
  message varchar(255) NOT NULL,
  CONSTRAINT public_post_pk PRIMARY KEY (id)
);

CREATE TABLE admin_post (
  id numeric(20) NOT NULL ,
  created_date TIMESTAMP NOT NULL,
  message varchar(255) NOT NULL,
  CONSTRAINT admin_post_pk PRIMARY KEY (id)
);

CREATE TABLE personal_post (
  id numeric(20) NOT NULL ,
  created_date TIMESTAMP NOT NULL,
  message varchar(255) NOT NULL,
  CONSTRAINT public_post_pk PRIMARY KEY (id)
);

INSERT ALL
INTO personal_post (id, created_date, message) VALUES (1, to_timestamp('2011-01-10 21:37:58', 'YYYY-MM-DD HH24:MI:SS'), 'Custom post #1 from public')
INTO personal_post (id, created_date, message) VALUES  (2, to_timestamp('2011-01-11 21:38:39','YYYY-MM-DD HH24:MI:SS'), 'Custom post #2 from public')
INTO personal_post (id, created_date, message) VALUES  (3, to_timestamp('2011-01-12 21:39:37','YYYY-MM-DD HH24:MI:SS'), 'Custom post #3 from public')
