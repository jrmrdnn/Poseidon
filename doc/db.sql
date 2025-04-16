DROP TABLE IF EXISTS `BidList`;
DROP TABLE IF EXISTS `Trade`;
DROP TABLE IF EXISTS `CurvePoint`;
DROP TABLE IF EXISTS `Rating`;
DROP TABLE IF EXISTS `Rule`;
DROP TABLE IF EXISTS `Users`;

CREATE TABLE IF NOT EXISTS `BidList` (
  `BidListId` int NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(30) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `bidQuantity` DOUBLE,
  `askQuantity` DOUBLE,
  `bid` DOUBLE,
  `ask` DOUBLE,
  `benchmark` VARCHAR(125),
  `bidListDate` TIMESTAMP,
  `commentary` VARCHAR(125),
  `security` VARCHAR(125),
  `status` VARCHAR(10),
  `trader` VARCHAR(125),
  `book` VARCHAR(125),
  `creationName` VARCHAR(125),
  `creationDate` TIMESTAMP,
  `revisionName` VARCHAR(125),
  `revisionDate` TIMESTAMP,
  `dealName` VARCHAR(125),
  `dealType` VARCHAR(125),
  `sourceListId` VARCHAR(125),
  `side` VARCHAR(125),
  PRIMARY KEY (`BidListId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Trade` (
  `TradeId` int NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(30) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `buyQuantity` DOUBLE,
  `sellQuantity` DOUBLE,
  `buyPrice` DOUBLE,
  `sellPrice` DOUBLE,
  `tradeDate` TIMESTAMP,
  `security` VARCHAR(125),
  `status` VARCHAR(10),
  `trader` VARCHAR(125),
  `benchmark` VARCHAR(125),
  `book` VARCHAR(125),
  `creationName` VARCHAR(125),
  `creationDate` TIMESTAMP,
  `revisionName` VARCHAR(125),
  `revisionDate` TIMESTAMP ,
  `dealName` VARCHAR(125),
  `dealType` VARCHAR(125),
  `sourceListId` VARCHAR(125),
  `side` VARCHAR(125),
  PRIMARY KEY (`TradeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `CurvePoint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `CurveId` int,
  `asOfDate` TIMESTAMP,
  `term` DOUBLE,
  `value` DOUBLE,
  `creationDate` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Rating` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `moodysRating` VARCHAR(125),
  `sandPRating` VARCHAR(125),
  `fitchRating` VARCHAR(125),
  `orderNumber` tinyint,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `RuleName` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125),
  `description` VARCHAR(125),
  `json` VARCHAR(125),
  `template` VARCHAR(512),
  `sqlStr` VARCHAR(125),
  `sqlPart` VARCHAR(125),
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Users` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(125),
  `password` VARCHAR(125),
  `fullname` VARCHAR(125),
  `role` VARCHAR(125),
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Users`(`fullname`, `username`, `password`, `role`) VALUES 
('Administrator', 'admin', '$2a$10$6qkJ0dPqYlxgALjdmxQtpuqMBXPBOWPqE2Q1H8t3wsqQNajPwCkuu', 'ADMIN'),
('User', 'user', '$2a$10$6qkJ0dPqYlxgALjdmxQtpuqMBXPBOWPqE2Q1H8t3wsqQNajPwCkuu', 'USER');

INSERT INTO `BidList`(`account`, `type`, `bidQuantity`) VALUES 
('Account1', 'Type1', 1000),
('Account2', 'Type2', 2000),
('Account3', 'Type3', 3000),
('Account4', 'Type4', 4000),
('Account5', 'Type5', 5000),
('Account6', 'Type6', 6000),
('Account7', 'Type7', 7000),
('Account8', 'Type8', 8000),
('Account9', 'Type9', 9000),
('Account10', 'Type10', 10000);

INSERT INTO `CurvePoint`(`CurveId`, `asOfDate`, `term`, `value`, `creationDate`) VALUES 
(1, '2023-01-01 00:00:00', 1.0, 0.01, '2023-01-01 00:00:00'),
(1, '2023-01-02 00:00:00', 2.0, 0.02, '2023-01-02 00:00:00'),
(2, '2023-01-03 00:00:00', 3.0, 0.03, '2023-01-03 00:00:00'),
(2, '2023-01-04 00:00:00', 4.0, 0.04, '2023-01-04 00:00:00'),
(3, '2023-01-05 00:00:00', 5.0, 0.05, '2023-01-05 00:00:00'),
(3, '2023-01-06 00:00:00', 6.0, 0.06, '2023-01-06 00:00:00'),
(4, '2023-01-07 00:00:00', 7.0, 0.07, '2023-01-07 00:00:00'),
(4, '2023-01-08 00:00:00', 8.0, 0.08, '2023-01-08 00:00:00'),
(5, '2023-01-09 00:00:00', 9.0, 0.09, '2023-01-09 00:00:00'),
(5, '2023-01-10 00:00:00', 10.0, 0.10, '2023-01-10 00:00:00');

INSERT INTO `Rating`(`moodysRating`, `sandPRating`, `fitchRating`, `orderNumber`) VALUES 
('Aaa', 'AAA', 'AAA', 1),
('Aa1', 'AA+', 'AA+', 2),
('Aa2', 'AA', 'AA', 3),
('Aa3', 'AA-', 'AA-', 4),
('A1', 'A+', 'A+', 5),
('A2', 'A', 'A', 6),
('A3', 'A-', 'A-', 7),
('Baa1', 'BBB+', 'BBB+', 8),
('Baa2', 'BBB', 'BBB', 9),
('Baa3', 'BBB-', 'BBB-', 10);

INSERT INTO `RuleName`(`name`, `description`, `json`, `template`, `sqlStr`, `sqlPart`) VALUES 
('Rule1', 'Description1', '{}', 'Template1', 'SELECT * FROM BidList', 'WHERE status = `active`'),
('Rule2', 'Description2', '{}', 'Template2', 'SELECT * FROM Trade', 'WHERE status = `completed`'),
('Rule3', 'Description3', '{}', 'Template3', 'SELECT * FROM CurvePoint', 'WHERE term > 5'),
('Rule4', 'Description4', '{}', 'Template4', 'SELECT * FROM Rating', 'WHERE orderNumber < 5');

INSERT INTO `Trade`(`account`, `type`, `buyQuantity`) VALUES 
('Account1', 'Type1', 1000),
('Account2', 'Type2', 2000),
('Account3', 'Type3', 3000),
('Account4', 'Type4', 4000),
('Account5', 'Type5', 5000),
('Account6', 'Type6', 6000),
('Account7', 'Type7', 7000),
('Account8', 'Type8', 8000),
('Account9', 'Type9', 9000),
('Account10', 'Type10', 10000);