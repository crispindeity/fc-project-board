drop table if exists article_comment;
drop table if exists article;

create table article
(
    id          bigint auto_increment
        primary key,
    content     varchar(10000) not null,
    created_at  datetime(6)    not null,
    created_by  varchar(100)   not null,
    hashtag     varchar(255)   null,
    modified_at datetime(6)    not null,
    modified_by varchar(100)   not null,
    title       varchar(255)   not null
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
    id          bigint auto_increment
        primary key,
    content     varchar(500) not null,
    created_at  datetime(6)  not null,
    created_by  varchar(100) not null,
    modified_at datetime(6)  not null,
    modified_by varchar(100) not null,
    article_id  bigint       not null,
    constraint articleFK
        foreign key (article_id) references article (id)
);

create index articleCommentCreatedByIndex
    on article_comment (created_by);

create index articleCommentCreatedAtIndex
    on article_comment (created_at);

create index articleCommentContentIndex
    on article_comment (content);
