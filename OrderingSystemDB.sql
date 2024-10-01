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
ADD Status BIT NOT NULL;

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

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    Type NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX)
);


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


CREATE TABLE ProductImage (
    ImageID INT PRIMARY KEY IDENTITY(1,1),
    ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
	IsAvatar BIT,
	ImgURL nvarchar(200) NOT NULL

);

CREATE TABLE CartItem (
    CartItemID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
	ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
	Quantity INT DEFAULT 1,
	ShopID INT NOT NULL FOREIGN KEY REFERENCES Shop(ShopID)
);

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






