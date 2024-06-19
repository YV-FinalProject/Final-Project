-- liquibase formatted sql

-- changeset yulia:insert_favorites
insert into Favorites (ProductID, UserID)
values
    (1, 3),
    (1, 1),
    (3, 1),
    (2, 2);