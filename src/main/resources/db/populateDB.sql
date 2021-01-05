DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Buusa'),
       ('White Sushi'),
       ('Papa Jones');

INSERT INTO votes (date, rest_id, user_id)
VALUES ('2020-01-30', 100002, 100000),
       ('2020-01-31', 100003, 100000),
       ('2020-01-30', 100004, 100001),
       ('2020-01-31', 100002, 100001);

INSERT INTO dishes (name, date, rest_id, price)
VALUES ('margarita','2020-01-31', 100004, 500),
       ('pasta','2020-01-31', 100004, 500),
       ('black buusa', '2020-01-31', 100002, 240),
       ('wild buusa', '2020-01-31', 100002, 260),
       ('salmon sushi', '2020-01-31', 100003, 200),
       ('ebi sushi', '2020-01-31', 100003, 220);;