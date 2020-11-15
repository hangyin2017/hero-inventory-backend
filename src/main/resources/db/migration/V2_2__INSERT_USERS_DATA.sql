INSERT INTO users (username, encoded_password) VALUES
('admin', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a'),
('sales', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a');

INSERT INTO authorities (permission) VALUES
('item:read'),
('item:write'),
('ROLE_ADMIN'),
('ROLE_SALES');

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='admin' and authorities.permission='ROLE_ADMIN';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='admin' and authorities.permission='item:read';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='admin' and authorities.permission='item:write';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='sales' and authorities.permission='ROLE_SALES';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='sales' and authorities.permission='item:read';