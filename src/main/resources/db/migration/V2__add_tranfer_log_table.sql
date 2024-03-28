create table transfer_log
(
    id                bigint primary key,
    transfer_date     timestamp   not null,
    source_identifier varchar(20) not null,
    target_identifier varchar(20) not null,
    amount            bigint      not null
);

create sequence transfer_log_id_seq
    start with 1
    increment by 200;