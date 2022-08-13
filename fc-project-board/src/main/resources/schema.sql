drop table if exists article_comment;
drop table if exists article;
drop table if exists user_account;

create table user_account
(
    user_id       varchar(50)  not null primary key,
    user_password varchar(255) not null,
    nickname      varchar(100),
    email         varchar(100),
    memo          varchar(255),
    created_at    datetime(6)  not null,
    created_by    varchar(100) not null,
    modified_at   datetime(6)  not null,
    modified_by   varchar(100) not null
);

create table article
(
    id          bigint         not null auto_increment primary key,
    user_id     varchar(50)    not null,
    title       varchar(255)   not null,
    content     varchar(10000) not null,
    hashtag     varchar(255),
    created_at  datetime(6)    not null,
    created_by  varchar(100)   not null,
    modified_at datetime(6)    not null,
    modified_by varchar(100)   not null
);

create table article_comment
(
    id          bigint       not null auto_increment,
    created_at  datetime(6)  not null,
    created_by  varchar(100) not null,
    modified_at datetime(6)  not null,
    modified_by varchar(100) not null,
    content     varchar(500) not null,
    article_id  bigint       not null,
    user_id     varchar(50)  not null,
    primary key (id)
);

create index userAccountUserIdIndex on user_account (user_id);
create index userAccountCreatedAtIndex on user_account (created_at);
create index userAccountCreatedByIndex on user_account (created_by);
create index articleTileIndex on article (title);
create index articleHashtagIndex on article (hashtag);
create index articleCreatedByIndex on article (created_by);
create index articleCreatedAtIndex on article (created_at);
create index articleCommentCreatedByIndex on article_comment (created_by);
create index articleCommentCreatedAtIndex on article_comment (created_at);
create index articleCommentContentIndex on article_comment (content);

alter table user_account
    add constraint userAccountUKByEmail unique (email);

alter table user_account
    add constraint userAccountUKByUserId unique (user_id);

alter table article
    add constraint articleUserAccountFK foreign key (user_id) references user_account (user_id);
alter table article_comment
    add constraint articleCommentArticleFK foreign key (article_id) references article (id);
alter table article_comment
    add constraint articleCommentUserAccountFK foreign key (user_id) references user_account (user_id);
