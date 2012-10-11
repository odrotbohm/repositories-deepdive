insert into Customer (id, email, firstname, lastname) values (1, 'dave@dmband.com', 'Dave', 'Matthews');
insert into Customer (id, email, firstname, lastname) values (2, 'carter@dmband.com', 'Carter', 'Beauford');
insert into Customer (id, email, firstname, lastname) values (3, 'boyd@dmband.com', 'Boyd', 'Tinsley');

insert into Address (id, street, city, country, customer_id) values (1, '27 Broadway', 'New York', 'United States', 1);
insert into Address (id, street, city, country, customer_id) values (2, '27 Broadway', 'New York', 'United States', 1);

insert into Product (id, name, description, price) values (1, 'iPad', 'Apple tablet device', 499.0);
insert into Product (id, name, description, price) values (2, 'MacBook Pro', 'Apple notebook', 1299.0);
insert into Product (id, name, description, price) values (3, 'Dock', 'Dock for iPhone/iPad', 49.0);
