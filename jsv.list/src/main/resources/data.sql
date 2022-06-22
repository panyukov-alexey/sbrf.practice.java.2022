create table if not exists USERS
(
    ID       int auto_increment,
    USERNAME varchar not null,
    PASSWORD varchar not null,
    CREATED_AT timestamp not null,
    UPDATED_AT timestamp,
    constraint TABLE_NAME_PK
        primary key (ID)

);

create unique index TABLE_NAME_ID_UINDEX
    on USERS (ID);

create unique index TABLE_NAME_USERNAME_UINDEX
    on USERS (USERNAME);

INSERT INTO "USERS" ("USERNAME", "PASSWORD", "CREATED_AT", "UPDATED_AT")
VALUES ('Charlie Scene', 'Gdy7wQz7', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('J-Dog', 'o8eQfFpf', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Funny Man', 'YxeO22e1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Johnny 3 Tears', 'D5hxcVnE', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Danny', 'tqLyZ10q', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());