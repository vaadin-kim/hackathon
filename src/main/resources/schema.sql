drop table news if exists;
drop table users if exists;
drop table role_mapping if exists;

create table news (
	id INTEGER IDENTITY PRIMARY KEY,
	article varchar(3000)
);


create table users (
	id INTEGER IDENTITY PRIMARY KEY,
	username varchar(255),
	password varchar(255)
);

create table role_mapping(
	user_id int,
	role varchar(15)
);
