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
-- Table structure for table `orderhomeheatingfuel`
--

DROP TABLE IF EXISTS `orderhomeheatingfuel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderhomeheatingfuel` (
  `OrderID` int NOT NULL,
  `CustomerID` varchar(45) NOT NULL,
  `Date` varchar(45) NOT NULL,
  `Quantity` double NOT NULL,
  `Address` varchar(45) NOT NULL,
  `EstimatedDeliveryTime` varchar(45) NOT NULL,
  `DeliveryType` varchar(45) NOT NULL,
  `Price` float NOT NULL,
  `Payment` varchar(45) NOT NULL,
  `Delivered` varchar(45) NOT NULL,
  PRIMARY KEY (`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderhomeheatingfuel`
--

LOCK TABLES `orderhomeheatingfuel` WRITE;
/*!40000 ALTER TABLE `orderhomeheatingfuel` DISABLE KEYS */;
INSERT INTO `orderhomeheatingfuel` VALUES (1,'312162952','20/06/2020',150,'haifa alon 4','20/06/2020','express',1530,'credit card','no'),(2,'312162952','20/06/2020',122,'naryia gozal 7','27/06/2020','ordinary',1220,'credit card','no'),(3,'312162953','20/06/2020',566,'afola kkk 5','20/06/2020','express',5773.2,'credit card','no'),(4,'312162954','20/06/2020',652,'nesher shikma 7','27/06/2020','ordinary',6324.4,'credit card','no'),(5,'312162955','20/06/2020',850,'ggg kkk 8','27/06/2020','ordinary',8160,'credit card','no'),(6,'312162955','20/06/2020',1234,'fff jjj 5','20/06/2020','express',12083.3,'credit card','no');
/*!40000 ALTER TABLE `orderhomeheatingfuel` ENABLE KEYS */;
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
