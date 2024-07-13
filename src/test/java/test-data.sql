INSERT INTO Products (ProductID, Name, Price) VALUES (1, 'Product 1', 10.0);
INSERT INTO OrderItems (OrderId, ProductID, Quantity, Price) VALUES (1, 1, 2, 10.0);
INSERT INTO Orders (OrderID, Status, CreatedAt) VALUES (1, 'PENDING_PAYMENT', CURRENT_DATE);