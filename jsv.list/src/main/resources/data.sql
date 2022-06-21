
create table "Users_test"
(
    ID       INTEGER auto_increment,
    NAME     CHARACTER VARYING not null,
    PASSWORD CHARACTER VARYING not null,
    constraint USERS_TEST_PK
        primary key (ID)
);

create unique index USERS_TEST_ID_UINDEX
    on "Users_test" (ID);

create unique index USERS_TEST_PASSWORD_UINDEX
    on "Users_test" (PASSWORD);


INSERT INTO "Users_test" ("NAME", "PASSWORD") VALUES ('Charlie Scene', 'Gdy7wQ');
INSERT INTO "Users_test" ("NAME", "PASSWORD") VALUES ('J-Dog', 'o8eQfF');
INSERT INTO "Users_test" ("NAME", "PASSWORD") VALUES ('Funny Man', 'YxeO22');
INSERT INTO "Users_test" ("NAME", "PASSWORD") VALUES ('Johnny 3 Tears', 'D5hVnE');
INSERT INTO "Users_test" ("NAME", "PASSWORD") VALUES ('Danny', 'tqLyZ1');
