-- cmsboot.simple_entity definition
DROP TABLE IF EXISTS t_test cascade;
CREATE TABLE t_test (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	last_modified_by varchar(255) NOT NULL,
	last_modified_date timestamp NOT NULL,
	"version" int8 NOT NULL,
	attached_file01uuid varchar(255) NULL,
	checkbox01 varchar(255) NULL,
	combobox01 varchar(255) NULL,
	combobox02 varchar(255) NULL,
	date01 date NULL,
	datetime01 timestamp NULL,
	radio01 varchar(255) NULL,
	radio02 varchar(255) NULL,
	select01 varchar(255) NULL,
	select03 varchar(255) NULL,
	status varchar(255) NOT NULL,
	text01 varchar(255) NULL,
	text02 int4 NULL,
	text03 float4 NULL,
	text04 int2 NULL,
	textarea01 varchar(255) NULL,
	bool001 boolean,
	CONSTRAINT t_testpkey PRIMARY KEY (id)
);
