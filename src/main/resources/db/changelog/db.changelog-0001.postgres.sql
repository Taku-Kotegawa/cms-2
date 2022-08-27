
drop table if exists account cascade;
drop table if exists role cascade;
drop table if exists variable cascade;
drop table if exists successful_authentication cascade;
drop table if exists failed_authentication cascade;
drop table if exists password_history cascade;
drop table if exists password_reissue_info cascade;
drop table if exists failed_password_reissue cascade;

create table account (
  username varchar (128)
, status character varying(1) not null
, version bigint DEFAULT 1 not null
, created_by character varying(255) not null
, created_date timestamp(0) without time zone not null
, last_modified_by character varying(255) not null
, last_modified_date timestamp(0) without time zone not null
, password varchar (88) not null
, first_name varchar (128) not null
, last_name varchar (128) not null
, email varchar (128) not null
, department character varying(255)
, url varchar (255) not null
, profile text not null
, allowed_ip character varying(255)
, api_key character varying(255)
, constraint pk_tbl_account primary key (username)
);

comment on table account is 'アカウント';
comment on column account.username is 'ユーザID';
comment on column account.status is 'ステータス';
comment on column account.version is 'バージョン';
comment on column account.created_by is '作成者';
comment on column account.created_date is '作成日時';
comment on column account.last_modified_by is '最終更新者';
comment on column account.last_modified_date is '最終更新日時';
comment on column account.password is 'パスワード';
comment on column account.first_name is '名';
comment on column account.last_name is '姓';
comment on column account.email is 'メールアドレス';
comment on column account.department is '所属部門';
comment on column account.url is 'URL';
comment on column account.profile is 'プロフィール';
comment on column account.allowed_ip is 'ログイン許可IPアドレス';
comment on column account.api_key is 'API KEY';

--------

create table role (
    username varchar(128),
    role varchar(10) not null,
    dummy varchar(1),
    constraint pk_tbl_role primary key (username, role),
    constraint fk_tbl_role foreign key (username) references account(username)
);
COMMENT ON TABLE role IS 'ユーザ・ロール関連';
COMMENT ON COLUMN role.username IS 'ユーザID';
COMMENT ON COLUMN role.role IS 'ロール';
COMMENT ON COLUMN role.dummy IS 'ダミー';

------

create table successful_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    dummy varchar(1),
    constraint pk_tbl_sa primary key (username, authentication_timestamp),
    constraint fk_tbl_sa foreign key (username) references account(username)
);
COMMENT ON TABLE successful_authentication IS '認証成功履歴';
COMMENT ON COLUMN successful_authentication.username IS 'ユーザID';
COMMENT ON COLUMN successful_authentication.authentication_timestamp IS '成功日時';
COMMENT ON COLUMN successful_authentication.dummy IS 'ダミー項目';


----

create table failed_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    dummy varchar(1),
    constraint pk_tbl_fa primary key (username, authentication_timestamp),
    constraint fk_tbl_fa foreign key (username) references account(username)
);
create index idx_tbl_aasl_t on successful_authentication (authentication_timestamp);
create index idx_tbl_aafl_t on failed_authentication (authentication_timestamp);
COMMENT ON TABLE failed_authentication IS '認証失敗履歴';
COMMENT ON COLUMN failed_authentication.username IS 'ユーザID';
COMMENT ON COLUMN failed_authentication.authentication_timestamp IS '失敗日時';
COMMENT ON COLUMN failed_authentication.dummy IS 'ダミー項目';

----

create table password_history (
    username varchar(128),
    password varchar(128) not null,
    use_from timestamp,
    constraint pk_tbl_ph primary key (username, use_from),
    constraint fk_tbl_ph foreign key (username) references account(username)
);
COMMENT ON TABLE password_history IS 'パスワード履歴';
COMMENT ON COLUMN password_history.username IS 'ユーザID';
COMMENT ON COLUMN password_history.password IS 'パスワード';
COMMENT ON COLUMN password_history.use_from IS '利用開始日時';

----

create table password_reissue_info (
    username varchar(128) not null,
    token varchar(128),
    secret varchar(88) not null,
    expiry_date timestamp not null,
    constraint pk_tbl_pri primary key (token),
    constraint fk_tbl_pri foreign key (username) references account(username)
);
COMMENT ON TABLE password_reissue_info IS 'パスワード再発行認証情報';
COMMENT ON COLUMN password_reissue_info.username IS 'ユーザID';
COMMENT ON COLUMN password_reissue_info.token IS 'トークン';
COMMENT ON COLUMN password_reissue_info.secret IS '秘密情報';
COMMENT ON COLUMN password_reissue_info.expiry_date IS '有効期限';

----

create table failed_password_reissue (
    token varchar(128),
    attempt_date timestamp,
    dummy varchar(1),
    constraint pk_tbl_prfl primary key (token, attempt_date),
    constraint fk_tbl_prfl foreign key (token) references password_reissue_info(token)
);
create index idx_tbl_prfl on failed_password_reissue (token);
COMMENT ON TABLE failed_password_reissue IS 'パスワード再発行失敗履歴';
COMMENT ON COLUMN failed_password_reissue.token IS 'トークン';
COMMENT ON COLUMN failed_password_reissue.dummy IS 'ダミー項目';
--------

create table variable (
  id serial not null
  , version bigint default 1 not null
  , created_by character varying(255) not null
  , created_date timestamp(6) without time zone not null
  , last_modified_by character varying(255) not null
  , last_modified_date timestamp(6) without time zone not null
  , status character varying(255) not null
  , type character varying(255) not null
  , code character varying(255) not null
  , value1 character varying(255) not null
  , value2 character varying(255)
  , value3 character varying(255)
  , value4 character varying(255)
  , value5 character varying(255)
  , value6 character varying(255)
  , value7 character varying(255)
  , value8 character varying(255)
  , value9 character varying(255)
  , value10 character varying(255)
  , date1 date
  , date2 date
  , date3 date
  , date4 date
  , date5 date
  , valint1 integer
  , valint2 integer
  , valint3 integer
  , valint4 integer
  , valint5 integer
  , file1uuid character varying(255)
  , textarea text
  , remark character varying(255)
  , primary key (id)
);

comment on table variable is 'バリアブル';
comment on column variable.id is '内部ID';
comment on column variable.version is 'バージョン';
comment on column variable.created_by is '作成者';
comment on column variable.created_date is '作成日時';
comment on column variable.last_modified_by is '最終更新者';
comment on column variable.last_modified_date is '最終更新日時';
comment on column variable.status is 'ステータス';
comment on column variable.type is 'タイプ';
comment on column variable.code is 'コード';
comment on column variable.value1 is '値1';
comment on column variable.value2 is '値2';
comment on column variable.value3 is '値3';
comment on column variable.value4 is '値4';
comment on column variable.value5 is '値5';
comment on column variable.value6 is '値6';
comment on column variable.value7 is '値7';
comment on column variable.value8 is '値8';
comment on column variable.value9 is '値9';
comment on column variable.value10 is '値10';
comment on column variable.date1 is '日付1';
comment on column variable.date2 is '日付2';
comment on column variable.date3 is '日付3';
comment on column variable.date4 is '日付4';
comment on column variable.date5 is '日付5';
comment on column variable.valint1 is '整数1';
comment on column variable.valint2 is '整数2';
comment on column variable.valint3 is '整数3';
comment on column variable.valint4 is '整数4';
comment on column variable.valint5 is '整数5';
comment on column variable.file1uuid is 'ファイル1';
comment on column variable.textarea is 'テキストエリア';
comment on column variable.remark is '備考';

