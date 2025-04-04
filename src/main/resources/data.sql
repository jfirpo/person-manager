INSERT INTO person (name) VALUES ('Kovács Balázs');
INSERT INTO person (name) VALUES ('Kiss Virág');
INSERT INTO address (type, city, street, person_id) VALUES ('PERMANENT','Budapest','Fő utca 1.', 1);
INSERT INTO address (type, city, street, person_id) VALUES ('TEMPORARY','Szeged','Kossuth tér 5.', 1);
INSERT INTO contact (contact_type, contact_value, address_id) VALUES ('email','balazs.kovacs@example.com',1);
INSERT INTO contact (contact_type, contact_value, address_id) VALUES ('phone','+36123456789',1);
