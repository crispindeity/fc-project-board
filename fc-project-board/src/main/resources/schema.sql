drop table if exists article_comment;
drop table if exists article;
drop table if exists user_account;

create table user_account
(
    id            bigint       not null auto_increment,
    created_at    datetime(6)  not null,
    created_by    varchar(100) not null,
    modified_at   datetime(6)  not null,
    modified_by   varchar(100) not null,
    email         varchar(100),
    memo          varchar(255),
    nick_name     varchar(100),
    user_id       varchar(50)  not null,
    user_password varchar(255) not null,
    primary key (id)
);

alter table user_account
    add constraint userAccountUK unique (email);

create index userAccountUserIdIndex on user_account (user_id);
create index userAccountCreatedAtIndex on user_account (created_at);
create index userAccountCreatedByIndex on user_account (created_by);

create table article
(
    id              bigint         not null auto_increment,
    created_at      datetime(6)    not null,
    created_by      varchar(100)   not null,
    modified_at     datetime(6)    not null,
    modified_by     varchar(100)   not null,
    content         varchar(10000) not null,
    hashtag         varchar(255),
    title           varchar(255)   not null,
    user_account_id bigint         not null,
    primary key (id),
    foreign key (user_account_id) references user_account (id)
);

create index articleTileIndex
    on article (title);

create index articleHashtagIndex
    on article (hashtag);

create index articleCreatedByIndex
    on article (created_by);

create index articleCreatedAtIndex
    on article (created_at);


create table article_comment
(
    id              bigint       not null auto_increment,
    created_at      datetime(6)  not null,
    created_by      varchar(100) not null,
    modified_at     datetime(6)  not null,
    modified_by     varchar(100) not null,
    content         varchar(500) not null,
    article_id      bigint       not null,
    user_account_id bigint       not null,
    primary key (id),
    foreign key (article_id) references article (id),
    foreign key (user_account_id) references user_account (id)
);

create index articleCommentCreatedByIndex
    on article_comment (created_by);

create index articleCommentCreatedAtIndex
    on article_comment (created_at);

create index articleCommentContentIndex
    on article_comment (content);

