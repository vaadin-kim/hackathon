insert into news (id, article) values (1, 'Welcome to the <b>Vaadin HACKathon</b>!');

-- Password masking not implemented in first iteration
insert into users (id, username, password) values (1, 'admin','e46c51f28b92c31db4c9dee3c3d08cbf1dc1c857465468d9947595b598b3bbc6');

insert into role_mapping (user_id, role) values (1, 'NORMAL'), (1,'ADMIN');