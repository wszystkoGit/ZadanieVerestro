create table user_details
(
    id                             bigint primary key,
    username                       varchar(100) unique not null,
    phone_number                   varchar(9) unique   not null,
    email                          varchar(255) unique not null,
    preferred_notification_channel varchar(20)         not null
);

create table user_auth
(
    id              bigint primary key,
    username        varchar(100) unique not null,
    password        varchar(80)         not null,
    user_details_id int                 not null,
    foreign key (user_details_id) references user_details (id)
);

create table user_balance
(
    id         bigint primary key,
    identifier varchar(20),
    amount     bigint not null,
    foreign key (id) references user_details (id)
);

create sequence user_details_id_seq
    start with 1
    increment by 50;

create sequence user_auth_id_seq
    start with 1
    increment by 50;