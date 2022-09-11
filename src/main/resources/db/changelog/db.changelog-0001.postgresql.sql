
drop table if exists t_account cascade;
drop table if exists t_role cascade;
drop table if exists t_variable cascade;
drop table if exists t_successful_authentication cascade;
drop table if exists t_failed_authentication cascade;
drop table if exists t_password_history cascade;
drop table if exists t_password_reissue_info cascade;
drop table if exists t_failed_password_reissue cascade;
drop table if exists t_email_change_request cascade;
drop table if exists t_file_managed cascade;
drop table if exists t_mail_send_history cascade;
drop table if exists t_permission_role cascade;
drop table if exists t_temp_file cascade;
drop table if exists t_workflow cascade;
drop table if exists t_failed_email_change_request cascade;

-----

create table t_account (
    username varchar (128),
    status character varying(1) not null,
    version bigint default 1 not null,
    created_by character varying(255) not null,
    created_date timestamp(0) without time zone not null,
    last_modified_by character varying(255) not null,
    last_modified_date timestamp(0) without time zone not null,
    password varchar (88) not null,
    first_name varchar (128) not null,
    last_name varchar (128),
    email varchar (128) not null,
    department character varying(255),
    url varchar (255),
    profile text,
    allowed_ip character varying(255),
    api_key character varying(255),
    constraint t_account_pkey primary key (username)
);

comment on table t_account is 'アカウント';
comment on column t_account.username is 'ユーザid';
comment on column t_account.status is 'ステータス';
comment on column t_account.version is 'バージョン';
comment on column t_account.created_by is '作成者';
comment on column t_account.created_date is '作成日時';
comment on column t_account.last_modified_by is '最終更新者';
comment on column t_account.last_modified_date is '最終更新日時';
comment on column t_account.password is 'パスワード';
comment on column t_account.first_name is '名';
comment on column t_account.last_name is '姓';
comment on column t_account.email is 'メールアドレス';
comment on column t_account.department is '所属部門';
comment on column t_account.url is 'url';
comment on column t_account.profile is 'プロフィール';
comment on column t_account.allowed_ip is 'ログイン許可ipアドレス';
comment on column t_account.api_key is 'api key';

--------

create table t_role (
    username varchar(128),
    role varchar(10) not null,
    dummy varchar(1),
    constraint pk_tbl_role primary key (username, role),
    constraint fk_tbl_role foreign key (username) references t_account(username)
);
comment on table t_role is 'ユーザ・ロール関連';
comment on column t_role.username is 'ユーザid';
comment on column t_role.role is 'ロール';

------

create table t_successful_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    dummy varchar(1),
    constraint pk_tbl_sa primary key (username, authentication_timestamp)
--    constraint fk_tbl_sa foreign key (username) references t_account(username)
);
create index idx_tbl_aasl_t on t_successful_authentication (authentication_timestamp);

comment on table t_successful_authentication is '認証成功履歴';
comment on column t_successful_authentication.username is 'ユーザid';
comment on column t_successful_authentication.authentication_timestamp is '成功日時';


----

create table t_failed_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    dummy varchar(1),
    constraint pk_tbl_fa primary key (username, authentication_timestamp)
--    constraint fk_tbl_fa foreign key (username) references t_account(username)
);

create index idx_tbl_aafl_t on t_failed_authentication (authentication_timestamp);
comment on table t_failed_authentication is '認証失敗履歴';
comment on column t_failed_authentication.username is 'ユーザid';
comment on column t_failed_authentication.authentication_timestamp is '失敗日時';

----

create table t_password_history (
    username varchar(128),
    password varchar(128) not null,
    use_from timestamp,
    constraint pk_tbl_ph primary key (username, use_from)
--    constraint fk_tbl_ph foreign key (username) references t_account(username)
);
comment on table t_password_history is 'パスワード履歴';
comment on column t_password_history.username is 'ユーザid';
comment on column t_password_history.password is 'パスワード';
comment on column t_password_history.use_from is '利用開始日時';

----

create table t_password_reissue_info (
    token varchar(128),
    username varchar(128) not null,
    secret varchar(88) not null,
    expiry_date timestamp not null,
    constraint pk_tbl_pri primary key (token)
--    constraint fk_tbl_pri foreign key (username) references t_account(username)
);
comment on table t_password_reissue_info is 'パスワード再発行認証情報';
comment on column t_password_reissue_info.username is 'ユーザid';
comment on column t_password_reissue_info.token is 'トークン';
comment on column t_password_reissue_info.secret is '秘密情報';
comment on column t_password_reissue_info.expiry_date is '有効期限';

----
create table t_failed_password_reissue (
    token varchar(128),
    attempt_date timestamp,
    dummy varchar(1),
    constraint pk_tbl_prfl primary key (token, attempt_date),
    constraint fk_tbl_prfl foreign key (token) references t_password_reissue_info(token)
);
create index idx_tbl_prfl on t_failed_password_reissue (token);
comment on table t_failed_password_reissue is 'パスワード再発行失敗履歴';
comment on column t_failed_password_reissue.token is 'トークン';
--------

create table t_variable (
    id bigserial not null,
    version bigint default 1 not null,
    created_by character varying(255) not null,
    created_date timestamp(6) without time zone not null,
    last_modified_by character varying(255) not null,
    last_modified_date timestamp(6) without time zone not null,
    status character varying(255) not null,
    type character varying(255) not null,
    code character varying(255) not null,
    value1 character varying(255) not null,
    value2 character varying(255),
    value3 character varying(255),
    value4 character varying(255),
    value5 character varying(255),
    value6 character varying(255),
    value7 character varying(255),
    value8 character varying(255),
    value9 character varying(255),
    value10 character varying(255),
    date1 date,
    date2 date,
    date3 date,
    date4 date,
    date5 date,
    valint1 integer,
    valint2 integer,
    valint3 integer,
    valint4 integer,
    valint5 integer,
    file1_uuid character varying(255),
    textarea text,
    remark character varying(255),
    primary key (id)
);

comment on table t_variable is 'バリアブル';
comment on column t_variable.id is '内部id';
comment on column t_variable.version is 'バージョン';
comment on column t_variable.created_by is '作成者';
comment on column t_variable.created_date is '作成日時';
comment on column t_variable.last_modified_by is '最終更新者';
comment on column t_variable.last_modified_date is '最終更新日時';
comment on column t_variable.status is 'ステータス';
comment on column t_variable.type is 'タイプ';
comment on column t_variable.code is 'コード';
comment on column t_variable.value1 is '値1';
comment on column t_variable.value2 is '値2';
comment on column t_variable.value3 is '値3';
comment on column t_variable.value4 is '値4';
comment on column t_variable.value5 is '値5';
comment on column t_variable.value6 is '値6';
comment on column t_variable.value7 is '値7';
comment on column t_variable.value8 is '値8';
comment on column t_variable.value9 is '値9';
comment on column t_variable.value10 is '値10';
comment on column t_variable.date1 is '日付1';
comment on column t_variable.date2 is '日付2';
comment on column t_variable.date3 is '日付3';
comment on column t_variable.date4 is '日付4';
comment on column t_variable.date5 is '日付5';
comment on column t_variable.valint1 is '整数1';
comment on column t_variable.valint2 is '整数2';
comment on column t_variable.valint3 is '整数3';
comment on column t_variable.valint4 is '整数4';
comment on column t_variable.valint5 is '整数5';
comment on column t_variable.file1_uuid is 'ファイル1';
comment on column t_variable.textarea is 'テキストエリア';
comment on column t_variable.remark is '備考';

-- email_change_request definition

create table t_email_change_request (
    "token" varchar(255) not null,
    username varchar(128) not null,
    secret varchar(255) not null,
    new_mail varchar(255) not null,
    expiry_date timestamp not null,
    constraint t_email_change_request_pkey primary key (token)
);

comment on table t_email_change_request is 'メールアドレス変更要求';
comment on column t_email_change_request.token is 'トークン';
comment on column t_email_change_request.username is 'ユーザid';
comment on column t_email_change_request.secret is '暗証番号';
comment on column t_email_change_request.new_mail is '新メールアドレス';
comment on column t_email_change_request.expiry_date is '有効期限';

-- cmsboot.failed_email_change_request definition
create table t_failed_email_change_request (
    "token" varchar(255) not null,
    attempt_date timestamp not null,
    dummy varchar(1),
    constraint t_failed_email_change_request_pkey primary key (token, attempt_date)
);

comment on table t_failed_email_change_request is 'メールアドレス変更の認証失敗の記録';
comment on column t_failed_email_change_request.token is 'トークン';
comment on column t_failed_email_change_request.attempt_date is '試行日時';

-- cmsboot.file_managed definition
create table t_file_managed (
    id int8 not null generated by default as identity,
    uuid varchar(255) null,
    "version" int8 not null,
    status varchar(255) null default '0' ::character varying,
    created_by varchar(255) not null,
    created_date timestamp not null,
    last_modified_by varchar(255) not null,
    last_modified_date timestamp not null,
    original_filename varchar(255) null,
    uri varchar(255) null,
    file_mime varchar(255) null,
    file_size int8 null,
    file_type varchar(255) null,
    constraint t_file_managed_idx1 unique (uuid),
    constraint t_file_managed_idx2 unique (uri),
    constraint t_file_managed_pkey primary key (id)
);

comment on table t_file_managed is 'ファイルマネージドエンティティ';
comment on column t_file_managed.id is 'ファイルマネージドid';
comment on column t_file_managed.uuid is 'ファイルを一意に特定する番号';
comment on column t_file_managed.version is 'バージョン';
comment on column t_file_managed.status is 'ステータス';
comment on column t_file_managed.created_by is '作成者';
comment on column t_file_managed.created_date is '作成日時';
comment on column t_file_managed.last_modified_by is '最終更新者';
comment on column t_file_managed.last_modified_date is '最終更新日';
comment on column t_file_managed.original_filename is 'ファイル名';
comment on column t_file_managed.uri is 'uri';
comment on column t_file_managed.file_mime is 'mimeタイプ';
comment on column t_file_managed.file_size is 'ファイルサイズ';
comment on column t_file_managed.file_type is 'ファイルの種類';

-- cmsboot.mail_send_history definition
create table t_mail_send_history (
    id int8 not null generated by default as identity,
    subject varchar(255) null,
    body varchar(1000) null,
    created_by varchar(255) null,
    created_date timestamp null,
    email_to varchar(255) null,
    email_cc varchar(255) null,
    email_bcc varchar(255) null,
    constraint t_mail_send_history_pkey primary key (id)
);
comment on table t_mail_send_history is 'メール送信履歴';
comment on column t_mail_send_history.subject is '件名';
comment on column t_mail_send_history.body is '本文';
comment on column t_mail_send_history.created_by is '作成者';
comment on column t_mail_send_history.created_date is '作成日時';
comment on column t_mail_send_history.email_to is 'to';
comment on column t_mail_send_history.email_cc is 'cc';
comment on column t_mail_send_history.email_bcc is 'bcc';

-- cmsboot.permission_role definition
create table t_permission_role (
    "role" varchar(255) not null,
    "permission" varchar(255) not null,
    dummy varchar(1),
    constraint t_permission_role_pkey primary key (role, permission)
);
comment on table t_permission_role is 'ロール・パーミッション';
comment on column t_permission_role.role is 'ロール';
comment on column t_permission_role.permission is 'パーミッション';

-- cmsboot.temp_file definition
create table t_temp_file (
    id varchar(255) not null,
    body oid null,
    original_name varchar(255) null,
    created_by varchar(255) null,
    created_date timestamp null,
    constraint t_temp_file_pkey primary key (id)
);
comment on table t_temp_file is 'ロール・パーミッション';
comment on column t_temp_file.id is 'id';
comment on column t_temp_file.body is '件名';
comment on column t_temp_file.original_name is 'ファイル名';
comment on column t_temp_file.created_by is '作成者';
comment on column t_temp_file.created_date is '作成日時';

-- cmsboot.workflow definition
create table t_workflow (
    id int8 not null generated by default as identity,
    "version" int8 not null,
    created_by varchar(255) not null,
    created_date timestamp not null,
    last_modified_by varchar(255) not null,
    last_modified_date timestamp not null,
    employee_id int8 not null,
    entity_id int8 not null,
    entity_type varchar(255) not null,
    step_no int4 not null,
    step_status int4 not null,
    weight int4 not null default 0,
    constraint t_workflow_idx1 unique (entity_type, entity_id, step_no, employee_id),
    constraint t_workflow_pkey primary key (id)
);
