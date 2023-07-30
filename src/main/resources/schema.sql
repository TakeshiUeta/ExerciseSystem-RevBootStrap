/** 各テーブル定義*/
/** ①会員テーブル*/
create table IF NOT EXISTS user(
  user_id varchar(6),
  user_name varchar(32),
  user_telno varchar(13),
  user_postcode varchar(8),
  user_address varchar(40),
  user_password varchar(6),
  primary key(user_id)
);


/**③筋トレテーブル*/
create table IF NOT EXISTS exercise (
  exercise_id varchar(6),
  user_id varchar(6),
  exercise_name varchar(32),
  exercise_cal DECIMAL (10, 3),
  primary key(exercise_id)
);


/**④筋トレ結果テーブル*/
create table IF NOT EXISTS result (
  result_id varchar(6),
  user_id varchar(6),
  result_cal DECIMAL (10, 3),
  result_distance DECIMAL (10, 3),
  result_count integer,
  result_date date,
  primary key(result_id)
);
