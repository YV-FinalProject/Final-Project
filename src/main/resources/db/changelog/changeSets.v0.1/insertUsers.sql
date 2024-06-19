-- liquibase formatted sql

-- changeset yulia:insert_users
insert into Users (Name, Email, PhoneNumber, PasswordHash, Role)
values
    ('Bill Smith','billsmith@example.com', '+4915245783',  'passOne', 'CLIENT'),
    ('Jane Miller','janemiller@example.com', '+4915736548',  'passTwo', 'CLIENT'),
    ('Big Boss','bigboss@example.com', '+491586666',  'adminPass', 'ADMINISTRATOR'),
    ('Little Boss','littleboss@example.com', '+4915325466',  'adminPass', 'ADMINISTRATOR');