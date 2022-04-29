CREATE TABLE IF NOT EXISTS `table_dto` (
    id int primary key auto_increment,
    number int unique,
    min_capacity int,
    max_capacity int
);


CREATE TABLE IF NOT EXISTS `reservation_dto`(
    id int primary key auto_increment,
    code text,
    date date,
    time time,
    note text null,
    persons int,
    table_number int
);

CREATE TABLE IF NOT EXISTS `customer`(
    reservation_dto int primary key references reservation_dto(id),
    name varchar(100),
    email varchar(100),
    telefon_number varchar(20)
    );

INSERT INTO `table_dto` (id, number, min_capacity, max_capacity)
VALUES (1, 1, 6, 8);

INSERT INTO `table_dto` (id, number, min_capacity, max_capacity)
VALUES (2, 2, 5, 6);

INSERT INTO `table_dto` (id, number, min_capacity, max_capacity)
VALUES (3, 3, 3, 4);

INSERT INTO `table_dto` (id, number, min_capacity, max_capacity)
VALUES (4, 4, 1, 2);

INSERT INTO `table_dto` (id, number, min_capacity, max_capacity)
VALUES (5, 5, 1, 2);