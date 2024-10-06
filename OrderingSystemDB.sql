create database ordering_system
go
use ordering_system
go

create table Users
(
   UserID int PRIMARY KEY,
   UserName VARCHAR(100) NOT NULL,
   Pass varchar(255) NOT NULL,
   FullName nvarchar(50) NOT NULL,
   PhoneNumber VARCHAR(10) CHECK (PhoneNumber LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
   Email varchar(50),
   Address NVARCHAR(50)  NULL,
   AvtImg nvarchar(200) null,
   ShopID int,
   Role int not null
)

go

CREATE TRIGGER trg_InsertUser
ON Users
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @UserID INT;
    DECLARE @UserName varchar(100) ;
    DECLARE @Pass varchar(255);
    DECLARE @FullName nvarchar (50);
    DECLARE @PhoneNumber VARCHAR(10);
    DECLARE @Email varchar(50);
	DECLARE @Address nvarchar(50);
	DECLARE @AvtImg nvarchar(200) ;
    DECLARE @ShopID int;
    DECLARE @Role int ;


    DECLARE insert_cursor CURSOR FOR
    SELECT UserName, Pass, FullName, PhoneNumber, Email,Address, AvtImg,ShopID, Role
    FROM inserted;

    OPEN insert_cursor;

    FETCH NEXT FROM insert_cursor INTO @UserName, @Pass, @FullName, @PhoneNumber, @Email, @Address,@AvtImg,@ShopID, @Role;

    WHILE @@FETCH_STATUS = 0
    BEGIN

        WHILE 1=1
        BEGIN
            SET @UserID = ABS(CHECKSUM(NEWID()) % 100000000);

            IF NOT EXISTS (SELECT 1 FROM Users WHERE UserID = @UserID)
            BEGIN

                INSERT INTO Users (UserID, UserName, Pass, FullName, PhoneNumber, Email, Address, AvtImg,ShopID, Role)
                VALUES (@UserID,@UserName, @Pass, @FullName, @PhoneNumber, @Email,@Address,@AvtImg, @ShopID, @Role);
                BREAK;
            END
        END

        FETCH NEXT FROM insert_cursor INTO @UserName, @Pass, @FullName, @PhoneNumber, @Email,@Address, @AvtImg, @ShopID, @Role;
    END

    CLOSE insert_cursor;
    DEALLOCATE insert_cursor;
END;

GO


CREATE TABLE password_reset_tokens (
    id INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expiration_time DATETIME NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (UserID) REFERENCES Users(UserID)
);




delete from users where UserName like 'huesuong'

select * from Users


ALTER TABLE Users
ADD code nvarchar(25);

-- start here------------------------------

ALTER TABLE Users
ADD Status BIT;

CREATE TABLE Shop (
    ShopID INT PRIMARY KEY IDENTITY(1,1),
    Name NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX),
    Status BIT NOT NULL,
    ShopImage NVARCHAR(255),
    Address NVARCHAR(255),
    TimeOpen TIME,
    TimeClose TIME
);

INSERT INTO Shop (Name, Description, Status, ShopImage, Address, TimeOpen, TimeClose)
VALUES 
('Hana Chicken', 'Fried chicken restaurants.', 1, 'https://i.pinimg.com/564x/f8/a7/01/f8a70144eb881afe78df0164e657e966.jpg', '321 ABC, Da Nang, VietNam', '09:00', '21:00'),
('Viet Restaurants', 'Hemispheres Steak & Seafood Grill', 1, 'https://i.pinimg.com/564x/a9/14/a1/a914a1fd5994a1997154155256d751cc.jpg', '321 ABC, Ha Noi, VietNam', '10:00', '22:00');

select * from Shop

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    Type NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX)
);

INSERT INTO Categories (Type, Description)
VALUES 
    ('Food', 'All kinds of dishes, snacks, and meals.'),
    ('Drink', 'A variety of beverages including soft drinks and hot drinks.'),
    ('Dessert', 'Sweet treats and baked goods.'),
    ('Snacks', 'Quick bites and light refreshments.'),
    ('Beverages', 'A wide range of cold and hot drinks, from sodas to specialty teas.');



CREATE TABLE Product (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
    Name NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX),
    Price DECIMAL(18, 2) NOT NULL,
    Status BIT NOT NULL,
    ShopID INT FOREIGN KEY REFERENCES Shop(ShopID),
    CategoryID INT FOREIGN KEY REFERENCES Categories(CategoryID),
    PurchaseCount INT DEFAULT 0,
    Rating DECIMAL(3, 2) CHECK (Rating BETWEEN 0 AND 5)
);


-- Food Category
INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating)
VALUES
    ('Grilled Salmon', 'Delicately grilled salmon fillet served with a variety of roasted vegetables, drizzled with a tangy lemon butter sauce for a flavorful experience.', 15.99, 1, 3, 1, 200, 4.6),
    ('Spaghetti Bolognese', 'Classic Italian dish with perfectly cooked spaghetti topped with a slow-cooked, savory meat sauce made from tomatoes, garlic, and ground beef.', 10.50, 1, 4, 1, 350, 4.7),
    ('Vegan Buddha Bowl', 'A nourishing bowl featuring quinoa, roasted sweet potatoes, avocado, chickpeas, and a creamy tahini dressing, offering a balanced and healthy meal.', 12.00, 1, 3, 1, 180, 4.5);

-- Drink Category
INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating)
VALUES
    ('Fresh Orange Juice', 'Made from 100% freshly squeezed oranges, this juice is naturally sweet and packed with vitamin C, giving you a perfect start to your morning.', 4.99, 1, 3, 2, 250, 4.8),
    ('Mocha Latte', 'Rich espresso combined with velvety steamed milk and chocolate syrup, topped with whipped cream and a drizzle of chocolate sauce for indulgence.', 5.50, 1, 4, 2, 400, 4.9),
    ('Lemonade', 'A refreshing homemade lemonade made with fresh lemon juice, cold water, and just a hint of honey, ideal for a hot summer day.', 3.99, 1, 4, 2, 300, 4.6);

-- Dessert Category
INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating)
VALUES
    ('Tiramisu', 'This Italian classic dessert features layers of espresso-soaked ladyfingers, creamy mascarpone cheese, and is dusted with cocoa powder for a rich flavor.', 6.99, 1, 3, 3, 220, 4.8),
    ('Cheesecake', 'Our creamy New York-style cheesecake with a buttery graham cracker crust is topped with a sweet strawberry compote for the perfect balance of flavors.', 7.50, 1, 4, 3, 350, 4.9),
    ('Apple Pie', 'A deliciously spiced apple filling wrapped in a flaky golden crust, served warm and topped with a scoop of vanilla ice cream for extra indulgence.', 5.99, 1, 3, 3, 300, 4.7);

-- Snacks Category
INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating)
VALUES
    ('Trail Mix', 'A delightful blend of roasted nuts, dried fruits, and a touch of chocolate, perfect for a quick, energizing snack during your busy day.', 3.50, 1, 4, 4, 450, 4.5),
    ('Pretzels', 'Crispy baked pretzels with a hint of salt, offering a satisfying crunch and great for snacking on their own or paired with dips.', 2.50, 1, 4, 4, 500, 4.4),
    ('Popcorn', 'Freshly popped popcorn seasoned lightly with salt and butter, ideal for movie nights or a quick, tasty snack at any time.', 1.99, 1, 3, 4, 600, 4.3);

-- Beverages Category
INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating)
VALUES
    ('Iced Green Tea', 'Refreshing iced green tea brewed from high-quality leaves, with a hint of lemon for a balanced and naturally sweet flavor.', 3.99, 1, 3, 5, 350, 4.7),
    ('Sparkling Water', 'Crisp and bubbly sparkling water with a touch of natural lime essence, offering a refreshing and hydrating experience any time of day.', 2.99, 1, 4, 5, 200, 4.5),
    ('Herbal Chamomile Tea', 'Calming chamomile tea made from organic flowers, perfect for relaxation and soothing your senses before bedtime.', 2.99, 1, 3, 5, 150, 4.8);

	select * from product

CREATE TABLE ProductImage (
    ImageID INT PRIMARY KEY IDENTITY(1,1),
    ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
	IsAvatar BIT,
	ImgURL nvarchar(200) NOT NULL

);


INSERT INTO ProductImage (ProductID, IsAvatar, ImgURL)
VALUES
    (1, 1, 'https://i.pinimg.com/564x/78/b8/a8/78b8a8068cae03ae1918c553ebb5b59f.jpg'),  
    (1, 0, 'https://i.pinimg.com/564x/50/83/f6/5083f664913f20fc6efe9e378f2a62f1.jpg'), 
    (1, 0, 'https://i.pinimg.com/564x/e8/bd/db/e8bddb0b456b9b7f8417b5dd6bc6e75b.jpg'); 

CREATE TABLE CartItem (
    CartItemID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
	ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
	Quantity INT DEFAULT 1,
	ShopID INT NOT NULL FOREIGN KEY REFERENCES Shop(ShopID)
);
select * from CartItem



CREATE TRIGGER UpdateCartItemQuantity
ON CartItem
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @ProductID INT;
    DECLARE @Quantity INT;
    DECLARE @UserID INT;
    DECLARE @ShopID INT;

    SELECT @ProductID = ProductID, @Quantity = Quantity, @UserID = UserID, @ShopID = ShopID FROM inserted;

    IF EXISTS (SELECT 1 FROM CartItem WHERE ProductID = @ProductID AND UserID = @UserID AND ShopID = @ShopID)
    BEGIN

        UPDATE CartItem
        SET Quantity = Quantity + @Quantity
        WHERE ProductID = @ProductID AND UserID = @UserID AND ShopID = @ShopID;
    END 
    ELSE 
    BEGIN

        INSERT INTO CartItem (UserID, ProductID, Quantity, ShopID) 
        VALUES (@UserID, @ProductID, @Quantity, @ShopID);
    END
END;







CREATE TABLE Favourite (
    UserID INT PRIMARY KEY FOREIGN KEY REFERENCES Users(UserID),
	ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID)
);

CREATE TABLE Feedback (
    FeedbackID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
	ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
	Rating INT NOT NULL,
	Comment NVARCHAR (MAX),
	CreatedDate DATETIME DEFAULT GETDATE(),
);

CREATE TABLE Discount (
    DiscountID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),  
    DiscountName NVARCHAR(100) NOT NULL,
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    DiscountPercentage DECIMAL(5, 2) NOT NULL,   
    Type NVARCHAR(50) NOT NULL
);






CREATE TABLE [Order] (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),  
    Status NVARCHAR(50) NOT NULL,
    Address NVARCHAR(255),
    CreatedDate DATETIME DEFAULT GETDATE(),
    DeliveryOption NVARCHAR(50),
    TimePickup DATETIME,
    TotalAmount DECIMAL(18, 2) NOT NULL,
    DiscountID INT FOREIGN KEY REFERENCES Discount(DiscountID),  
    PaymentOption NVARCHAR(50) NOT NULL
);





CREATE TABLE OrderItem (
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    TotalPrice DECIMAL(18, 2) NOT NULL,
    PRIMARY KEY (OrderID, ProductID),
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)  

)

CREATE TABLE RewardRedemption (
    RedemptionID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT,
    NumberOfPoint INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


CREATE TABLE Post (
    PostID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
	ImgURL nvarchar(200) NOT NULL,
	Heading NVARCHAR (MAX) NOT NULL,
	Content NVARCHAR (MAX) NOT NULL,
	CreatedDate DATETIME DEFAULT GETDATE()
);

CREATE TABLE Comment(
    CommentID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
	PostID INT NOT NULL FOREIGN KEY REFERENCES Post(PostID),
	Content NVARCHAR (MAX) NOT NULL
);






