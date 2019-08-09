insert into news (id, article) values (1, 'Welcome to the <b>Vaadin HACKathon</b>!');

-- Password masking not implemented in first iteration
insert into users (id, username, password) values (1, 'admin','admin');

insert into role_mapping (user_id, role) values (1, 'NORMAL'), (1,'ADMIN');