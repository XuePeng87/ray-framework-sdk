CREATE TABLE sys_user (
  id bigint identity primary key not null,
  user_account varchar(50) not null default '',
  user_secret varchar(36),
  user_name varchar(50),
  user_phone varchar(25),
  user_email varchar(200),
  user_avator varchar(200),
  user_availabled tinyint(1),
  user_remark varchar(200),
  fixed tinyint(1),
  deleted tinyint(1),
  create_user bigint,
  modify_user bigint,
  create_time timestamp,
  modify_time timestamp
);

CREATE TABLE sys_role (
  id bigint identity primary key not null,
  role_code varchar(25),
  role_name varchar(25),
  role_remark varchar(200),
  fixed tinyint(1),
  deleted tinyint(1),
  create_user bigint,
  modify_user bigint,
  create_time timestamp,
  modify_time timestamp
);

CREATE TABLE sys_role_user_relation (
  id bigint identity primary key not null,
  role_id bigint,
  user_id bigint
);

CREATE TABLE sys_role_function_relation (
  id bigint identity primary key not null,
  role_id bigint,
  function_id bigint,
  function_type int(11)
);

CREATE TABLE sys_role_api_relation (
  id bigint identity primary key not null,
  role_id bigint,
  api_url varchar(200)
);

CREATE TABLE sys_menu (
  id bigint identity primary key not null,
  menu_pid bigint,
  menu_title varchar(50),
  menu_icon varchar(200),
  menu_path varchar(200),
  menu_level smallint(6),
  menu_sort smallint(6),
  menu_remark varchar(200),
  fixed tinyint(1),
  deleted tinyint(1),
  create_user bigint,
  modify_user bigint,
  create_time timestamp,
  modify_time timestamp
);

CREATE TABLE sys_button (
  id bigint identity primary key,
  menu_id bigint,
  button_title varchar(25),
  button_icon varchar(200),
  button_remark varchar(200),
  deleted tinyint(1),
  create_user bigint,
  modify_user bigint,
  create_time timestamp,
  modify_time timestamp
);

CREATE TABLE sys_op_log (
  id bigint identity primary key not null,
  account varchar(50),
  module varchar(20),
  description varchar(50),
  src_ip varchar(15),
  create_time timestamp
);

CREATE TABLE sys_op_log_content (
  id bigint identity primary key not null,
  log_id bigint,
  content varchar(200)
);

CREATE TABLE sys_dept (
  id bigint identity primary key not null,
  dept_name varchar(20),
  dept_pid bigint,
  dept_phone_num varchar(20),
  dept_remark varchar(50),
  dept_availabled tinyint(1),
  deleted tinyint(1),
  create_user bigint,
  modify_user bigint,
  create_time timestamp,
  modify_time timestamp
);

CREATE TABLE sys_dept_user_relationrelation (
  id bigint identity primary key not null,
  dept_id bigint,
  user_id bigint,
  leader tinyint(1)
);