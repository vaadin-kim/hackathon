insert into news (article) values ('<b>Welcome to the Vaadin HACKathon</b>');

-- Password masking not implemented in first iteration
insert into users (id, username, password) values (0, 'admin','admin');

insert into role_mapping (user_id, role) values (0, 'NORMAL'), (0,'ADMIN');