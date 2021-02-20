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
-- Table structure for table `gasstations`
--

DROP TABLE IF EXISTS `gasstations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gasstations` (
  `ChainName` varchar(45) NOT NULL,
  `StationName` varchar(45) NOT NULL,
  `FuelType` varchar(45) NOT NULL,
  `StockQuantity` double NOT NULL,
  `ThresholdLevel` double DEFAULT NULL,
  PRIMARY KEY (`ChainName`,`StationName`,`FuelType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gasstations`
--

LOCK TABLES `gasstations` WRITE;
/*!40000 ALTER TABLE `gasstations` DISABLE KEYS */;
INSERT INTO `gasstations` VALUES ('delek','haifa','benzene',9950,1000),('delek','haifa','diesel',9656,1000),('delek','haifa','scooter',9366,1000),('delek','tel aviv','benzene',9790,1000),('delek','tel aviv','diesel',9342,1000),('delek','tel aviv','scooter',8544,1000),('paz','haifa','benzene',9825,1000),('paz','haifa','diesel',9838,1000),('paz','haifa','scooter',9120,1000),('paz','tel aviv','benzene',8861,1000),('paz','tel aviv','diesel',10000,1000),('paz','tel aviv','scooter',10000,1000),('ten','haifa','benzene',8400,1000),('ten','haifa','diesel',9576,1000),('ten','haifa','scooter',9215,1000),('ten','tel aviv','benzene',8397,1000),('ten','tel aviv','diesel',9346,1000),('ten','tel aviv','scooter',8666,1000);
/*!40000 ALTER TABLE `gasstations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-20 20:57:30
