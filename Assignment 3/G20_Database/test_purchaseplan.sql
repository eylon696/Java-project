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
-- Table structure for table `purchaseplan`
--

DROP TABLE IF EXISTS `purchaseplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchaseplan` (
  `CustomerID` int NOT NULL AUTO_INCREMENT,
  `PlanType` varchar(45) NOT NULL,
  `GasCompany1` varchar(45) DEFAULT NULL,
  `GasCompany2` varchar(45) DEFAULT NULL,
  `GasCompany3` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`),
  KEY `CustomerId` (`CustomerID`),
  CONSTRAINT `CustomerIdFk` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=666666667 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseplan`
--

LOCK TABLES `purchaseplan` WRITE;
/*!40000 ALTER TABLE `purchaseplan` DISABLE KEYS */;
INSERT INTO `purchaseplan` VALUES (312162952,'exclusive','delek',NULL,NULL),(312162953,'multiple','paz','ten',NULL),(312162954,'exclusive','paz',NULL,NULL),(312162955,'multiple','delek','ten','paz'),(312162956,'exclusive','ten',NULL,NULL),(312162957,'multiple','ten','delek',NULL),(312162958,'multiple','paz','delek',NULL),(312162959,'none',NULL,NULL,NULL);
/*!40000 ALTER TABLE `purchaseplan` ENABLE KEYS */;
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
