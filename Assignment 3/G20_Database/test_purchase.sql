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
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `PurchaseID` int NOT NULL,
  `CustomerId` int NOT NULL,
  `LicenseNumber` int NOT NULL,
  `Date` varchar(45) NOT NULL,
  `Time` varchar(45) NOT NULL,
  `Quantity` double NOT NULL,
  `Price` double NOT NULL,
  `Payment` varchar(40) NOT NULL,
  `ChainName` varchar(45) NOT NULL,
  `StationName` varchar(45) NOT NULL,
  `OperationName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PurchaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,312162952,11222,'14/05/2020','12:13',50,1282.5,'credit card','delek','haifa','weekend sale'),(2,312162952,11223,'14/05/2020','12:15',25,213.75,'credit card','delek','haifa','weekend sale'),(3,312162953,112233,'16/05/2020','12:16',50,1270.08,'credit card','ten','tel aviv','weekend sale'),(4,312162954,55664,'14/05/2020','12:16',30,637.1460000000001,'credit card','paz','haifa','weekend sale'),(5,312162954,66554,'15/05/2020','12:17',20,141.588,'credit card','paz','haifa','weekend sale'),(6,312162955,99887,'14/05/2020','12:17',90,1330.535808,'credit card','delek','haifa','weekend sale'),(7,312162956,88997,'14/05/2020','12:18',503,12901.95,'cash','ten','tel aviv','weekend sale'),(8,312162957,66778,'16/05/2020','12:19',200,3386.88,'cash','delek','haifa','weekend sale'),(9,312162957,77664,'15/05/2020','12:19',345,2921.184,'cash','delek','tel aviv','weekend sale'),(10,312162958,55668,'14/05/2020','12:20',25,349.272,'credit card','paz','haifa','weekend sale'),(11,312162958,88995,'14/05/2020','12:20',50,698.544,'credit card','paz','haifa','weekend sale'),(12,312162958,779963,'20/06/2020','12:24',36,279.4176,'credit card','delek','tel aviv',NULL),(13,312162959,116645,'20/06/2020','12:24',88,737.5103999999999,'credit card','delek','haifa',NULL),(14,312162952,11222,'02/06/2019','12:28',17,411.825,'credit card','delek','tel aviv','monthly sale'),(15,312162952,11223,'03/06/2019','12:29',168,1356.6,'credit card','delek','tel aviv','monthly sale'),(16,312162953,112233,'04/06/2019','12:30',89,2135.1456000000003,'credit card','paz','haifa','monthly sale'),(17,312162954,55664,'05/06/2019','12:31',34,681.9822,'cash','paz','tel aviv','monthly sale'),(18,312162954,66554,'06/06/2019','12:31',647,4325.9067000000005,'cash','paz','haifa','monthly sale'),(19,312162955,99887,'07/06/2019','12:32',145,2024.5498559999996,'credit card','ten','haifa','monthly sale'),(20,312162956,88997,'08/06/2019','12:33',365,8842.125,'credit card','ten','haifa','monthly sale'),(21,312162956,88997,'09/06/2019','12:33',874,21172.65,'credit card','ten','tel aviv','monthly sale'),(22,312162957,66778,'10/06/2019','12:34',654,10459.8144,'credit card','ten','tel aviv','monthly sale'),(23,312162957,77664,'11/06/2019','12:34',457,3654.5376,'credit card','ten','tel aviv','monthly sale'),(24,312162957,77664,'12/06/2019','12:35',877,7013.1936,'credit card','ten','tel aviv','monthly sale'),(25,312162958,55668,'13/06/2019','12:35',87,1147.94064,'credit card','paz','haifa','monthly sale'),(26,312162958,88995,'14/06/2019','12:40',54,712.51488,'credit card','delek','haifa','monthly sale'),(27,312162958,88995,'15/06/2019','12:41',658,8682.12576,'credit card','delek','tel aviv','monthly sale'),(28,312162958,779963,'16/06/2019','12:42',784,5172.33024,'credit card','delek','tel aviv','monthly sale'),(29,312162959,116645,'17/06/2019','12:42',365,2600.1432,'credit card','delek','haifa','monthly sale'),(30,312162959,116645,'18/06/2019','12:42',785,5592.0888,'credit card','ten','haifa','monthly sale'),(31,312162952,11222,'01/01/2020','12:46',50,1425,'credit card','delek','tel aviv',NULL),(32,312162952,11223,'01/03/2020','12:47',156,1482,'credit card','delek','haifa',NULL),(33,312162953,112233,'02/02/2020','12:47',142,4007.808,'credit card','ten','tel aviv',NULL),(34,312162954,55664,'03/03/2020','12:48',874,20624.652,'credit card','paz','tel aviv',NULL),(35,312162954,66554,'04/04/2020','12:48',213,1675.4579999999999,'credit card','paz','haifa',NULL),(36,312162955,99887,'01/04/2020','12:49',154,2529.6606720000004,'credit card','ten','haifa',NULL),(37,312162956,88997,'05/04/2020','12:49',1235,35197.5,'credit card','ten','haifa',NULL),(38,312162957,66778,'02/03/2020','12:50',125,2352,'credit card','ten','haifa',NULL),(39,312162957,77664,'02/05/2020','12:50',123,1157.1840000000002,'credit card','delek','tel aviv',NULL),(40,312162953,112233,'20/06/2020','13:24',34,959.616,'credit card','ten','tel aviv',NULL),(41,312162953,112233,'20/06/2020','13:25',56,1317.12,'credit card','paz','haifa',NULL),(42,312162954,55664,'20/06/2020','13:26',231,4542.615000000001,'credit card','paz','tel aviv',NULL),(43,312162952,11222,'20/06/2020','20:52',143,4075.5,'cash','delek','tel aviv',NULL);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
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
