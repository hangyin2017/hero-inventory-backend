INSERT INTO users (username, encoded_password, email, status) VALUES
('admin', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a', 'admin@hero.com', 'verified'),
('sales', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a', 'sales@hero.com', 'verified'),
('trainee', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a', 'trainee@hero.com', 'verified');

INSERT INTO authorities (permission) VALUES
('ROLE_ADMIN'),
('ROLE_SALES'),
('ROLE_TRAINEE');

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='admin' and authorities.permission='ROLE_ADMIN';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='sales' and authorities.permission='ROLE_SALES';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.username='trainee' and authorities.permission='ROLE_TRAINEE';