CREATE TABLE persons (
                         id BIGINT IDENTITY PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);
CREATE TABLE addresses (
                           id BIGINT IDENTITY PRIMARY KEY,
                           type VARCHAR(20) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           street VARCHAR(100) NOT NULL,
                           person_id BIGINT NOT NULL,
                           CONSTRAINT FK_person FOREIGN KEY(person_id) REFERENCES persons(id)
);
CREATE TABLE contacts (
                          id BIGINT IDENTITY PRIMARY KEY,
                          contact_type VARCHAR(50) NOT NULL,
                          contact_value VARCHAR(255) NOT NULL,
                          address_id BIGINT NOT NULL,
                          CONSTRAINT FK_address FOREIGN KEY(address_id) REFERENCES addresses(id)
);
