create table if not exists USERS
(
    ID       int auto_increment,
    USERNAME varchar not null,
    PASSWORD varchar not null,
    constraint USERS_PK
        primary key (ID)
);

create unique index USERS_ID_UINDEX
    on USERS (ID);

create unique index USERS_PASSWORD_UINDEX
    on USERS (PASSWORD);

INSERT INTO "USERS" ("USERNAME", "PASSWORD") VALUES ('Charlie Scene', 'Gdy7wQ');
INSERT INTO "USERS" ("USERNAME", "PASSWORD") VALUES ('J-Dog', 'o8eQfF');
INSERT INTO "USERS" ("USERNAME", "PASSWORD") VALUES ('Funny Man', 'YxeO22');
INSERT INTO "USERS" ("USERNAME", "PASSWORD") VALUES ('Johnny 3 Tears', 'D5hVnE');
INSERT INTO "USERS" ("USERNAME", "PASSWORD") VALUES ('Danny', 'tqLyZ1');
