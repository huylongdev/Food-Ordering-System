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

INSERT INTO Users ( UserName, Pass, FullName, PhoneNumber, Email, Address, AvtImg, Role)
VALUES ( 'john_doe', 'johndoe123', 'John Doe', '0123456789', 'john.doe@example.com', '123 Main St, Hometown', 'https://i.pinimg.com/564x/69/54/88/695488b8d0648689a41a5de24a2d6902.jpg', 1);

go
INSERT INTO Users ( UserName, Pass, FullName, PhoneNumber, Email, Address, AvtImg, Role)
VALUES ( 'jane_smith', 'jane123', 'Jane Smith', '0987654321', 'jane.smith@example.com', '456 Elm St, Hometown', 'https://i.pinimg.com/564x/69/54/88/695488b8d0648689a41a5de24a2d6902.jpg', 1);
go
select * from Users

delete from users where UserName like 'linh'

ALTER TABLE Users
ADD code nvarchar(25);



