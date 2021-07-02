-- DatAdmin Native MySQL Dump

/*!40101 SET NAMES utf8 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE TABLE `users` ( 
    `pkid` int AUTO_INCREMENT NOT NULL, 
    `loginUsername` text NOT NULL, 
    `loginPassword` text NOT NULL, 
    `passwordHint` text NOT NULL, 
    `createDate` text NOT NULL, 
    `userSpace` int NOT NULL, 
    `userStatus` text NOT NULL, 
    `workingDirectory` text NOT NULL, 
    `theme` int NOT NULL,  
    PRIMARY KEY (`pkid`)
) ENGINE=InnoDB  AUTO_INCREMENT=2  COLLATE=latin1_swedish_ci ;
INSERT INTO `users` (`pkid`, `loginUsername`, `loginPassword`, `passwordHint`, `createDate`, `userSpace`, `userStatus`, `workingDirectory`, `theme`) VALUES
(1, '1', '7gjkzS2wp1SQ5E4It5kyoc/j07BzjrbqrKBA5w+UrOihal0Ro+Xqvg==', 'no password hint', '1', 10000, '1', 'E:\\', 4)

;/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
