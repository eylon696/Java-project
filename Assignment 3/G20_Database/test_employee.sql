-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `FirstName` varchar(40) NOT NULL,
  `LastName` varchar(40) NOT NULL,
  `ID` int NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Role` varchar(40) NOT NULL,
  `OrganizationalAffiliation` varchar(40) NOT NULL,
  `GasStation` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('eylon','ben simon',1,'1','aylon8@gmail.com','marketing representative','administration','all'),('saar','victor',2,'2','saarms5@gmail.com','marketing manager','administration','all'),('shiraz','karov',3,'3','shirazkarov@gmail.com','supplier','administration','all'),('shoval','david',4,'4','shovaldavid@gmail.com','chain manager','paz','all'),('gal','toledano',5,'5','gal1010100@gmail.com','station manager','paz','haifa'),('boris','misha',6,'6','ddd@gmail.com','chain manager','ten','all'),('ido','kozikaro',7,'7','ido555@gmail.com','station manager','ten','haifa'),('himon',' limon',8,'8','hmimo33@gmail.com','station manager','ten','tel aviv'),('rami','levi',9,'9','ramlev@gmail.com','chain manager','delek','all'),('bill','bytes',10,'10','billbits@gmail.com','station manager','delek','haifa'),('michael','jorden',11,'11','airjorden55@gmail.com','station manager','delek','tel aviv'),('dubi','sss',312162952,'123123','employeecustomer@gmail.com','station manager','paz','tel aviv');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-20 20:57:31
