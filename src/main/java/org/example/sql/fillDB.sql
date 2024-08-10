CREATE TABLE clients(
    id BIGINT not null primary key,
    name VARCHAR(255),
    surname VARCHAR(255),
    patronymic VARCHAR(255),
    phone_number VARCHAR(255),
    email VARCHAR(255),
    passport_number BIGINT not null
    );

    CREATE TABLE credit_offers (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        creditSum BIGINT NOT NULL,
        monthsOfCredit BIGINT NOT NULL,
        client_id BIGINT NOT NULL,
        FOREIGN KEY (client_id) REFERENCES clients(id)
    );