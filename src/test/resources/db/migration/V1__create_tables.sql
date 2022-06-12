create table users
(
    id        bigint primary key,
    name      varchar(50),
    last_name varchar(50),
    username  varchar(50),
    state     varchar(20),
    lang      varchar(10)
);

create table cities
(
    id             serial,
    translation_id bigint
);

create table translation
(
    id serial,
    translation_id bigint,
    lang varchar(2),
    value text

);

create table translation_info
(
    id serial,
    translation_type varchar(20)
);