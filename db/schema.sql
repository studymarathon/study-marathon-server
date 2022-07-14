use study_marathon;

create table member
(
    id          bigint auto_increment
        primary key,
    email       varchar(50)    not null,
    password    varbinary(256) not null,
    nickname    varchar(15)    not null,
    created_at  datetime       null,
    modified_at datetime       null,
    constraint user_email_uindex
        unique (email),
    constraint user_nickname_uindex
        unique (nickname)
);

create table study_group
(
    id          bigint auto_increment
        primary key,
    name        varchar(60)  not null,
    info        varchar(200) null,
    owner_id    bigint       not null,
    created_at  datetime     null,
    modified_at datetime     null,
    constraint study_group_group_name_uindex
        unique (name),
    constraint study_group_user_id_fk
        foreign key (owner_id) references member (id)
);

create table study_group_member
(
    group_id    bigint   not null,
    member_id   bigint   not null,
    created_at  datetime null,
    modified_at datetime null,
    constraint study_group_member_study_group_id_fk
        foreign key (group_id) references study_group (id),
    constraint study_group_member_user_id_fk
        foreign key (member_id) references member (id)
);

create table user_role
(
    user_id     bigint      not null,
    role        varchar(20) not null,
    created_at  datetime    null,
    modified_at datetime    null
);

