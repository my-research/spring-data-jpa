CREATE TABLE todo
(
    id        SERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    completed BOOLEAN      NOT NULL DEFAULT FALSE
);

INSERT INTO todo (title, completed)
VALUES ('Buy groceries', false),
       ('Read a book', true),
       ('Clean the house', false);