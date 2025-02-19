DROP DATABASE IF EXISTS `SGU_EIS`;
CREATE DATABASE IF NOT EXISTS `SGU_EIS`;
USE `SGU_EIS`;


/*________________________________________________________________________ TODO: Product tables_______________________________________________________________________ */
DROP TABLE IF EXISTS `Category`;
CREATE TABLE IF NOT EXISTS `Category`(
    `Id`    		INT UNSIGNED        PRIMARY KEY    AUTO_INCREMENT,
    `CategoryName`  NVARCHAR(255)       NOT NULL
);

DROP TABLE IF EXISTS `Brand`;
CREATE TABLE IF NOT EXISTS `Brand`(
    `Id`       		INT UNSIGNED        PRIMARY KEY    AUTO_INCREMENT,
    `BrandName`     NVARCHAR(255)       NOT NULL
);

DROP TABLE IF EXISTS `Product`;
CREATE TABLE IF NOT EXISTS `Product`(
    `Id`         INT UNSIGNED        PRIMARY KEY    AUTO_INCREMENT,
    `ProductName`       NVARCHAR(1000)      NOT NULL,
    `Status`            BOOLEAN            	NOT NULL,
    `CreateTime`        DATETIME           	NOT NULL,
    `Image`             VARCHAR(255)        ,

    `Origin`            NVARCHAR(255)       ,
    `Capacity`          INT UNSIGNED        ,
    `ABV`               INT UNSIGNED        ,
    `Description`       TEXT				,


    `BrandId`           INT UNSIGNED   		NOT NULL,
	`CategoryId`	    INT UNSIGNED   		NOT NULL,
    FOREIGN KEY (`BrandId`)     	REFERENCES `Brand`(`Id`),
    FOREIGN KEY (`CategoryId`)  	REFERENCES `Category`(`Id`)
);

DROP TABLE IF EXISTS `Batch`;
CREATE TABLE IF NOT EXISTS `Batch`(
	`Id` 				INT UNSIGNED 		PRIMARY KEY 	AUTO_INCREMENT,
	`UnitPrice`         INT UNSIGNED        NOT NULL,
    `Quantity`          INT UNSIGNED        NOT NULL,
	`MaxQuantity`       INT UNSIGNED        NOT NULL,
    `ReceivingTime`		DATETIME			NOT NULL,
    `ProductId`			INT UNSIGNED		NOT NULL,
    
    FOREIGN KEY (`ProductId`)     REFERENCES `Product`(`Id`)
);


-- /*________________________________________________________________________ TODO: Account tables_______________________________________________________________________ */

-- DROP TABLE IF EXISTS `UserInformation`;
-- CREATE TABLE IF NOT EXISTS `UserInformation`(
--     `Id`            INT UNSIGNED       PRIMARY KEY    AUTO_INCREMENT,
--     `Email`         NVARCHAR(255)                   UNIQUE,
--     `Address`       NVARCHAR(255),
--     `Birthday`      DATE,
--     `Fullname`      NVARCHAR(255),
--     `Gender`        ENUM("Male", "Female", "Other"),
--     `PhoneNumber`   NVARCHAR(20) 
-- );


-- DROP TABLE IF EXISTS `Account`;
-- CREATE TABLE IF NOT EXISTS `Account`(
--     `Id`                INT UNSIGNED            PRIMARY KEY    AUTO_INCREMENT,
--     `Password`          NVARCHAR(800)                               	NOT NULL,
--     `CreateTime`        DATETIME                                    	NOT NULL,
--     `Status`            BOOLEAN                                     	NOT NULL,
--     `Active`			BOOLEAN 		                            	NOT NULL,
--     `Role`              ENUM("User", "Employee", "Manager", "Admin")    NOT NULL            DEFAULT "User",
--     `UserInformationId` INT UNSIGNED                                	NOT NULL,
--     FOREIGN KEY (`UserInformationId`) REFERENCES `UserInformation`(`Id`)
-- );


-- DROP TABLE IF EXISTS `Token`;
-- CREATE TABLE IF NOT EXISTS `Token`(
--     `Id`                INT UNSIGNED       PRIMARY KEY      AUTO_INCREMENT,
--     `Token`             CHAR(36)           NOT NULL         UNIQUE,
--     `CreateTime`	    DATETIME		   NOT NULL,
--     `Expiration`    	DATETIME           NOT NULL,
--     `AccountId`         INT UNSIGNED       NOT NULL,
--     FOREIGN KEY (`AccountId`) REFERENCES `Account`(`Id`)
-- );


-- /*________________________________________________________________________ TODO: Order tables_______________________________________________________________________ */
-- DROP TABLE IF EXISTS `CartItem`;
-- CREATE TABLE IF NOT EXISTS `CartItem` (
--     `ProductId`         INT UNSIGNED       NOT NULL,
--     `AccountId`         INT UNSIGNED       NOT NULL,
--     `Quantity`          INT UNSIGNED       NOT NULL,
--     `UnitPrice`         INT UNSIGNED       NOT NULL,
--     `Total`             INT UNSIGNED       NOT NULL,

--     PRIMARY KEY (`ProductId`, `AccountId`),
--     FOREIGN KEY (`ProductId`)     REFERENCES `Product`(`Id`),
--     FOREIGN KEY (`AccountId`)     REFERENCES `Account`(`Id`)
-- );

-- DROP TABLE IF EXISTS `Order`;
-- CREATE TABLE IF NOT EXISTS `Order` (
--     `Id`                CHAR(12)           NOT NULL    PRIMARY KEY,
--     `OrderTime`         DATETIME           NOT NULL,
--     `TotalPrice`        INT UNSIGNED       NOT NULL,
--     `Note`              TEXT,		
--     `Payment` 			ENUM("COD", "VNPAY"),
--     `AccountId`         INT UNSIGNED,
--     FOREIGN KEY (`AccountId`) REFERENCES `Account` (`Id`)
-- );

-- DROP TABLE IF EXISTS `OrderStatus`;
-- CREATE TABLE IF NOT EXISTS `OrderStatus` (
--     `OrderId`       CHAR(12)                                                        NOT NULL,
--     `Status`        ENUM("ChoDuyet", "DaDuyet", "DangGiao", "GiaoThanhCong", "Huy") NOT NULL,
--     `UpdateTime`    DATETIME                                                        NOT NULL,
--     PRIMARY KEY (`OrderId`, `Status`),
--     FOREIGN KEY (`OrderId`) REFERENCES `Order`(`Id`)
-- );

-- DROP TABLE IF EXISTS `OrderDetail`;
-- CREATE TABLE IF NOT EXISTS `OrderDetail` (
--     `OrderId`       CHAR(12)           NOT NULL,
--     `ProductId`     INT UNSIGNED       NOT NULL,
--     `Quantity`      INT UNSIGNED       NOT NULL,
--     `UnitPrice`     INT UNSIGNED       NOT NULL,
--     `Total`         INT UNSIGNED       NOT NULL,
--     FOREIGN KEY (`OrderId`) REFERENCES `Order`(`Id`),
--     FOREIGN KEY (`ProductId`)     REFERENCES `Product`(`Id`),
--     PRIMARY KEY (`ProductId`, `OrderId`)
-- );


-- /*______________________________________________________________________INVENTORY_________________________________________________________________________________________ */
-- DROP TABLE IF EXISTS `InventoryReport`;
-- CREATE TABLE IF NOT EXISTS `InventoryReport` (
--     `Id`           	INT UNSIGNED       	PRIMARY KEY    AUTO_INCREMENT,
--     `CreateTime`   	DATETIME           	NOT NULL                    ,
--     `Supplier` 		NVARCHAR(255)									,
--     `SupplierPhone`	NVARCHAR(100)									,
--     `TotalPrice`	INT UNSIGNED		NOT NULL
-- );

-- DROP TABLE IF EXISTS `InventoryReportDetail`;
-- CREATE TABLE IF NOT EXISTS `InventoryReportDetail` (
--     `InventoryReportId`       	INT UNSIGNED       	NOT NULL,
--     `ProductId`        			INT UNSIGNED       	NOT NULL,
--     `Quantity`      			INT UNSIGNED       	NOT NULL,
--     `UnitPrice`     			INT UNSIGNED       	NOT NULL,
--     `Total`         			INT UNSIGNED       	NOT NULL,
--     `Profit`					INT UNSIGNED		NOT NULL,
--     FOREIGN KEY (`InventoryReportId`) REFERENCES `InventoryReport`(`Id`),
--     FOREIGN KEY (`ProductId`)     REFERENCES `Product`(`Id`),
--     PRIMARY KEY (`ProductId`, `InventoryReportId`)
-- );