CREATE DATABASE flight_booking;
USE flight_booking;
DROP DATABASE flight_booking;

create table roles
(
    id          int         not null auto_increment primary key,
    name        varchar(50) not null,
    description varchar(50) not null
);

create table users
(
    id             int              not null auto_increment primary key,
    fullname       varchar(100)     not null,
    username       varchar(100)     not null unique,
    password       varchar(200)     not null,
    email          varchar(100)     not null unique,
    address        varchar(200)     null,
    phone          varchar(12)      null,
    avatar         varchar(200)     null,
    activated      bit(1) default 1 not null,
    remember_token varchar(200)
);

create table users_roles
(
    id      int primary key not null auto_increment,
    user_id int             not null,
    role_id int             not null,
    constraint users_roles_roles_fk foreign key (role_id) references roles (id),
    constraint users_roles_users_fk foreign key (user_id) references users (id)
);

CREATE TABLE airports
(
    airportId   INT AUTO_INCREMENT PRIMARY KEY,
    airportCode VARCHAR(5) NOT NULL UNIQUE,
    airportName VARCHAR(100),
    city        VARCHAR(50),
    country     VARCHAR(50)
);


CREATE TABLE flights
(
    flightId           INT AUTO_INCREMENT PRIMARY KEY,
    flightNumber       VARCHAR(10) NOT NULL UNIQUE,
    airline            VARCHAR(50),
    departureAirportId INT,
    arrivalAirportId   INT,
    departureTime      DATETIME,
    arrivalTime        DATETIME,
    seatCapacity       INT,
    price              DECIMAL(10, 2),
    userId             INT,
    FOREIGN KEY (departureAirportId) REFERENCES airports(airportId),
    FOREIGN KEY (arrivalAirportId) REFERENCES airports(airportId),
    FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE seats
(
    seatId             INT AUTO_INCREMENT PRIMARY KEY,
    flightId           INT NOT NULL,
    seatNumber         VARCHAR(10) NOT NULL,
    class              VARCHAR(50) NOT NULL,
    availabilityStatus ENUM('available', 'booked', 'reserved', 'unavailable') NOT NULL DEFAULT 'available',
    holdExpiration     DATETIME,
    FOREIGN KEY (flightId) REFERENCES flights(flightId)
);

CREATE TABLE bookings
(
    bookingId     INT AUTO_INCREMENT PRIMARY KEY,
    userId        INT,
    flightId      INT,
    bookingDate   DATETIME,
    numberOfSeats INT,
    totalPrice    DECIMAL(10, 2),
    status        VARCHAR(50),
    name          VARCHAR(100),
    age           INT,
    gender        VARCHAR(10),
    country       VARCHAR(50),
    code_booking  VARCHAR(6),
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (flightId) REFERENCES flights (flightId)
);

CREATE TABLE pay_method
(
    paymethod_id   INT PRIMARY KEY AUTO_INCREMENT,
    paymethod_name VARCHAR(100)
);

CREATE TABLE payments
(
    paymentId     INT AUTO_INCREMENT PRIMARY KEY,
    bookingId     INT,
    paymethod_id  INT,
    paymentDate   DATETIME,
    amount        DECIMAL(10, 2),
    paymentStatus VARCHAR(50),
    FOREIGN KEY (bookingId) REFERENCES bookings (bookingId),
    FOREIGN KEY (paymethod_id) REFERENCES pay_method (paymethod_id)
);

CREATE TABLE order_detail
(
    order_id  INT PRIMARY KEY AUTO_INCREMENT,
    userId    INT,
    bookingId INT,
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (bookingId) REFERENCES bookings (bookingId)
);

DELIMITER $$

CREATE TRIGGER after_flight_insert
    AFTER INSERT ON flights
    FOR EACH ROW
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE seat_row INT DEFAULT 1;
    DECLARE seat_letter CHAR(1);
    DECLARE seat_count INT DEFAULT 120;

    WHILE i <= seat_count DO
        CASE MOD(i, 6)
            WHEN 1 THEN SET seat_letter = 'A';
    WHEN 2 THEN SET seat_letter = 'B';
    WHEN 3 THEN SET seat_letter = 'C';
    WHEN 4 THEN SET seat_letter = 'D';
    WHEN 5 THEN SET seat_letter = 'E';
    WHEN 0 THEN SET seat_letter = 'F';
END CASE;

INSERT INTO seats (availability_status, class_type, seat_number, flight_id)
VALUES ('AVAILABLE', 'Economy', CONCAT(seat_row, seat_letter), NEW.flight_id);

IF MOD(i, 6) = 0 THEN
            SET seat_row = seat_row + 1;
END IF;

        SET i = i + 1;
END WHILE;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER after_flight_delete
    AFTER DELETE ON flights
    FOR EACH ROW
BEGIN
    DELETE FROM seats WHERE flight_id = OLD.flight_id;
    END$$

    DELIMITER ;

    insert into roles (name, description)
    values ('ROLE_ADMIN', 'Quản trị viên'),
           ('ROLE_EMPLOYEE', 'Nhân viên'),
           ('ROLE_CUSTOMER', 'Khách hàng')
    ;

    insert into users (fullname, username, password, email, address, phone, avatar, activated, remember_token)
    values ('Đào Huy Hoàng', 'hoangadmin', '$2a$10$oztyYOzexbKMwNQi.xfE4uOVjKByoNiuAHKO9zL83LMA0czAXtP3.',
            'hoang.admin@gmail.com',
            'Hồ Chí Minh', '0908983906', 'avatar1.png', true,
            'rAPHFeXDlQCjenQ6nffqe56hC9EulnyQTDKGzhuKjCIrVI4Cy0hWGEtsvJdA') -- password: 123456
         , ('Hoàng Đình Bảo Kỳ', 'kyuser', '$2a$10$oztyYOzexbKMwNQi.xfE4uOVjKByoNiuAHKO9zL83LMA0czAXtP3.',
            'kynguyen.user@gmail.com',
            'Hồ Chí Minh', '0985678910', 'avatar1.jpg', true,
            'sDh9x4HXrBCOJzgBH5qeZwcjVgN8Uv4u1WZBVQsYbp0moh7eDG260xJe07dF') -- password: 123456
         , ('Nguyễn Hữu Trí', 'tringuyenuser', '$2a$10$oztyYOzexbKMwNQi.xfE4uOVjKByoNiuAHKO9zL83LMA0czAXtP3.',
            'tringuyen.user@gmail.com',
            'Hồ Chí Minh', '0981234567', 'avatar1.jpg', true,
            '2iV7Lpa1sgCafdEOkbh2wVeYKamoc7kAb0CF6kAQJSVymts7g1uHZO9iUMI7') -- password: 123456
    ;



    insert into users_roles (user_id, role_id)
    values (1, 1)
         , (1, 2)
         , (1, 3)
         , (2, 2)
         , (3, 3)
    ;

    INSERT INTO flight_booking.airports (airport_id, airport_code, airport_name, city, country) VALUES
                                                                                                    (1, 'HN', 'Nội Bài', 'Hà Nội', 'Việt Nam'),
                                                                                                    (2, 'HCM', 'Tân Sơn Nhất', 'Hồ Chí Minh', 'Việt Nam'),
                                                                                                    (3, 'DAD', 'Đà Nẵng', 'Đà Nẵng', 'Việt Nam'),
                                                                                                    (4, 'CXR', 'Cam Ranh', 'Nha Trang', 'Việt Nam'),
                                                                                                    (5, 'VCA', 'Trà Nóc', 'Cần Thơ', 'Việt Nam'),
                                                                                                    (6, 'HPH', 'Cát Bi', 'Hải Phòng', 'Việt Nam'),
                                                                                                    (7, 'VII', 'Vinh', 'Vinh', 'Việt Nam'),
                                                                                                    (8, 'PQC', 'Phú Quốc', 'Phú Quốc', 'Việt Nam'),
                                                                                                    (9, 'UIH', 'Phù Cát', 'Quy Nhơn', 'Việt Nam'),
                                                                                                    (10, 'VDH', 'Đồng Hới', 'Đồng Hới', 'Việt Nam');


    INSERT INTO flight_booking.flights (flight_id, airline, arrival_time, departure_time, flight_number, price, seat_capacity, arrival_airport_id, departure_airport_id, user_id) VALUES
                                                                                                                                                                                      (1, 'VietNamEline', '2024-08-20 15:00:00.000000', '2024-08-20 12:11:00.000000', 'VN123', 3200000, 150, 2, 1, null),
                                                                                                                                                                                      (2, 'VietJet', '2024-08-22 17:00:00.000000', '2024-08-22 14:00:00.000000', 'VJ456', 2990000, 150, 3, 1, null),
                                                                                                                                                                                      (3, 'BambooAirways', '2024-08-21 09:00:00.000000', '2024-08-21 06:30:00.000000', 'QH789', 2990000, 150, 4, 1, null),
                                                                                                                                                                                      (4, 'VietNamEline', '2024-08-23 19:00:00.000000', '2024-08-23 16:00:00.000000', 'VN321', 2990000, 150, 5, 2, null),
                                                                                                                                                                                      (5, 'VietJet', '2024-08-24 21:00:00.000000', '2024-08-24 18:00:00.000000', 'VJ654', 2990000, 150, 6, 2, null),
                                                                                                                                                                                      (6, 'BambooAirways', '2024-08-24 08:00:00.000000', '2024-08-24 05:00:00.000000', 'QH987', 1990000, 150, 7, 3, null),
                                                                                                                                                                                      (7, 'VietNamEline', '2024-08-25 11:00:00.000000', '2024-08-25 08:00:00.000000', 'VN654', 1390000, 150, 8, 3, null),
                                                                                                                                                                                      (8, 'VietJet', '2024-08-25 13:00:00.000000', '2024-08-25 10:00:00.000000', 'VJ321', 3390000, 150, 9, 4, null),
                                                                                                                                                                                      (9, 'BambooAirways', '2024-08-25 16:00:00.000000', '2024-08-25 13:00:00.000000', 'QH654', 2290000, 150, 10, 4, null),
                                                                                                                                                                                      (10, 'VietNamEline', '2024-08-23 18:00:00.000000', '2024-08-23 15:00:00.000000', 'VN987', 3290000, 150, 1, 5, null),
                                                                                                                                                                                      (11, 'SkyJourney', '2024-08-20 15:00:00.000000', '2024-08-20 12:11:00.000000', 'SJ123', 3200000, 150, 2, 1, null),
                                                                                                                                                                                      (12, 'SkyJourney', '2024-08-22 17:00:00.000000', '2024-08-22 14:00:00.000000', 'SJ456', 2990000, 150, 3, 1, null),
                                                                                                                                                                                      (13, 'SkyJourney', '2024-08-21 09:00:00.000000', '2024-08-21 06:30:00.000000', 'SJ789', 2990000, 150, 4, 1, null),
                                                                                                                                                                                      (14, 'SkyJourney', '2024-08-23 19:00:00.000000', '2024-08-23 16:00:00.000000', 'SJ321', 2990000, 150, 5, 2, null),
                                                                                                                                                                                      (15, 'SkyJourney', '2024-08-24 21:00:00.000000', '2024-08-24 18:00:00.000000', 'SJ654', 2990000, 150, 6, 2, null),
                                                                                                                                                                                      (16, 'SkyJourney', '2024-08-24 08:00:00.000000', '2024-08-24 05:00:00.000000', 'SJ987', 1990000, 150, 7, 3, null),
                                                                                                                                                                                      (17, 'SkyJourney', '2024-08-25 11:00:00.000000', '2024-08-25 08:00:00.000000', 'SJ654', 1390000, 150, 8, 3, null),
                                                                                                                                                                                      (18, 'SkyJourney', '2024-08-25 13:00:00.000000', '2024-08-25 10:00:00.000000', 'SJ321', 3390000, 150, 9, 4, null),
                                                                                                                                                                                      (19, 'SkyJourney', '2024-08-25 16:00:00.000000', '2024-08-25 13:00:00.000000', 'SJ654', 2290000, 150, 10, 4, null),
                                                                                                                                                                                      (20, 'SkyJourney', '2024-08-23 18:00:00.000000', '2024-08-23 15:00:00.000000', 'SJ987', 3290000, 150, 1, 5, null);

