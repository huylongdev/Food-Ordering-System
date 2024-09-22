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




select * from Users

delete from users where UserName like 'huesuong'

ALTER TABLE Users
ADD code nvarchar(25);




