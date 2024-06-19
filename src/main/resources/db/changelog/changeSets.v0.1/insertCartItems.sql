-- liquibase formatted sql

-- changeset yulia:insert_cartitems
insert into CartItems (CartID,Quantity,ProductID)
values
    (1,5,1),
    (2,5,1),
    (2,8,2),
    (3,10,3),
    (1,22,2);