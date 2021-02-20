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
-- Table structure for table `purchasesreport`
--

DROP TABLE IF EXISTS `purchasesreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchasesreport` (
  `ChainName` varchar(45) NOT NULL,
  `StationName` varchar(45) NOT NULL,
  `FuelType` varchar(45) NOT NULL,
  `NumOfPurchases` int NOT NULL,
  `TotalSumOfPurchases` double NOT NULL,
  `Quarter` int NOT NULL,
  `Year` int NOT NULL,
  PRIMARY KEY (`ChainName`,`StationName`,`FuelType`,`Quarter`,`Year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchasesreport`
--

LOCK TABLES `purchasesreport` WRITE;
/*!40000 ALTER TABLE `purchasesreport` DISABLE KEYS */;
INSERT INTO `purchasesreport` VALUES ('paz','haifa','benzene',1,637.1460000000001,2,2020),('paz','haifa','diesel',2,1047.816,2,2020),('paz','haifa','scooter',2,1817.0459999999998,2,2020),('paz','tel aviv','benzene',1,4542.615000000001,2,2020),('paz','tel aviv','diesel',0,0,2,2020),('paz','tel aviv','scooter',0,0,2,2020);
/*!40000 ALTER TABLE `purchasesreport` ENABLE KEYS */;
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
