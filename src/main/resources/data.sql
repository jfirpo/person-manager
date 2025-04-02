INSERT INTO persons (name) VALUES ('Kovács Balázs');
INSERT INTO persons (name) VALUES ('Kiss Virág');
INSERT INTO addresses (type, city, street, person_id) VALUES ('állandó','Budapest','Fő utca 1.', 1);
INSERT INTO addresses (type, city, street, person_id) VALUES ('ideiglenes','Szeged','Kossuth tér 5.', 1);
INSERT INTO contacts (contact_type, contact_value, address_id) VALUES ('email','balazs.kovacs@example.com',1);
INSERT INTO contacts (contact_type, contact_value, address_id) VALUES ('phone','+36123456789',1);
