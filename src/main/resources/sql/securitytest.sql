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



insert into principal values(1, 'sobik', 'int');
insert into roles values (2, 'role_user');
insert into roles values (3, 'role_visitor');
insert into roles values (1, 'role_admin');



insert into principal_role values(1,1);
insert into principal_role values(1,2);
insert into principal_role values(1,3);