-- MySQL dump 10.13  Distrib 5.5.30, for Linux (x86_64)
--
-- Host: localhost    Database: gwlpr
-- ------------------------------------------------------
-- Server version	5.5.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `EMail` varchar(64) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `GUI` blob NOT NULL,
  `MaterialStorage` int(4) DEFAULT NULL,
  `GoldStorage` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `AccountsMaterialStorage` (`MaterialStorage`),
  CONSTRAINT `AccountsMaterialStorage` FOREIGN KEY (`MaterialStorage`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'root@gwlp.ps','root','',NULL,0),(2,'test@gwlp.ps','test','',NULL,0);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributepoints`
--

DROP TABLE IF EXISTS `attributepoints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attributepoints` (
  `CharacterID` int(4) NOT NULL,
  `AttributeID` int(4) NOT NULL,
  `Amount` int(4) NOT NULL,
  PRIMARY KEY (`CharacterID`,`AttributeID`),
  KEY `AttributePointsAttributeID` (`AttributeID`),
  CONSTRAINT `AttributePointsAttributeID` FOREIGN KEY (`AttributeID`) REFERENCES `attributes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `AttributePointsCharacterID` FOREIGN KEY (`CharacterID`) REFERENCES `characters` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attributepoints`
--

LOCK TABLES `attributepoints` WRITE;
/*!40000 ALTER TABLE `attributepoints` DISABLE KEYS */;
/*!40000 ALTER TABLE `attributepoints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributes`
--

DROP TABLE IF EXISTS `attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attributes` (
  `ID` int(4) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Profession` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `AttributesProfession` (`Profession`),
  CONSTRAINT `AttributesProfession` FOREIGN KEY (`Profession`) REFERENCES `professions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attributes`
--

LOCK TABLES `attributes` WRITE;
/*!40000 ALTER TABLE `attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `characters`
--

DROP TABLE IF EXISTS `characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `characters` (
  `AccountID` int(4) NOT NULL,
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Gold` int(4) DEFAULT NULL,
  `SkillPoints` int(4) DEFAULT NULL,
  `SkillPointsTotal` int(4) DEFAULT NULL,
  `Experience` int(4) DEFAULT NULL,
  `LastOutpost` int(4) DEFAULT NULL,
  `Level` int(4) DEFAULT NULL,
  `PrimaryProfession` int(4) DEFAULT NULL,
  `SecondaryProfession` int(4) DEFAULT NULL,
  `ActiveWeaponset` int(4) DEFAULT NULL,
  `Equipment` int(4) DEFAULT NULL,
  `ShowHelm` tinyint(1) DEFAULT NULL,
  `ShowCape` tinyint(1) DEFAULT NULL,
  `ShowCostumeHead` tinyint(1) DEFAULT NULL,
  `ShowCostumeBody` tinyint(1) DEFAULT NULL,
  `Backpack` int(4) DEFAULT NULL,
  `Beltpouch` int(4) DEFAULT NULL,
  `Bag1` int(4) DEFAULT NULL,
  `Bag2` int(4) DEFAULT NULL,
  `EquipmentPack` int(4) DEFAULT NULL,
  `Campaign` tinyint(1) DEFAULT NULL,
  `Face` tinyint(1) DEFAULT NULL,
  `Haircolor` tinyint(1) DEFAULT NULL,
  `Hairstyle` tinyint(1) DEFAULT NULL,
  `Height` tinyint(1) DEFAULT NULL,
  `Sex` tinyint(1) DEFAULT NULL,
  `Skin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CharactersAccountID` (`AccountID`),
  KEY `CharactersBackpack` (`Backpack`),
  KEY `CharactersBag1` (`Bag1`),
  KEY `CharactersBag2` (`Bag2`),
  KEY `CharactersBeltpouch` (`Beltpouch`),
  KEY `CharactersEquipment` (`Equipment`),
  KEY `CharactersEquipmentPack` (`EquipmentPack`),
  KEY `CharactersLastOutpost` (`LastOutpost`),
  KEY `CharactersLevel` (`Level`),
  KEY `CharactersPrimaryProfession` (`PrimaryProfession`),
  KEY `CharactersSecondaryProfession` (`SecondaryProfession`),
  CONSTRAINT `CharactersAccountID` FOREIGN KEY (`AccountID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersBackpack` FOREIGN KEY (`Backpack`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersBag1` FOREIGN KEY (`Bag1`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersBag2` FOREIGN KEY (`Bag2`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersBeltpouch` FOREIGN KEY (`Beltpouch`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersEquipment` FOREIGN KEY (`Equipment`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersEquipmentPack` FOREIGN KEY (`EquipmentPack`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersLastOutpost` FOREIGN KEY (`LastOutpost`) REFERENCES `maps` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersLevel` FOREIGN KEY (`Level`) REFERENCES `levels` (`Level`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersPrimaryProfession` FOREIGN KEY (`PrimaryProfession`) REFERENCES `professions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CharactersSecondaryProfession` FOREIGN KEY (`SecondaryProfession`) REFERENCES `professions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characters`
--

LOCK TABLES `characters` WRITE;
/*!40000 ALTER TABLE `characters` DISABLE KEYS */;
INSERT INTO `characters` VALUES (1,12,'Test Char',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,2,7,4,12,0,30),(1,13,'Abc Def',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,31,0,7,0,1,3),(2,14,'Meow Meow',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,31,4,17,1,0,28);
/*!40000 ALTER TABLE `characters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commands`
--

DROP TABLE IF EXISTS `commands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commands` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`,`Name`),
  KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commands`
--

LOCK TABLES `commands` WRITE;
/*!40000 ALTER TABLE `commands` DISABLE KEYS */;
/*!40000 ALTER TABLE `commands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factions`
--

DROP TABLE IF EXISTS `factions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factions` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factions`
--

LOCK TABLES `factions` WRITE;
/*!40000 ALTER TABLE `factions` DISABLE KEYS */;
/*!40000 ALTER TABLE `factions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factionstats`
--

DROP TABLE IF EXISTS `factionstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factionstats` (
  `AccountID` int(4) NOT NULL,
  `Type` int(4) NOT NULL,
  `Amount` int(4) NOT NULL,
  `Max` int(4) NOT NULL,
  `Total` int(4) NOT NULL,
  PRIMARY KEY (`AccountID`,`Type`),
  KEY `FactionStatsType` (`Type`),
  CONSTRAINT `FactionStatsAccountID` FOREIGN KEY (`AccountID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FactionStatsType` FOREIGN KEY (`Type`) REFERENCES `factions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factionstats`
--

LOCK TABLES `factionstats` WRITE;
/*!40000 ALTER TABLE `factionstats` DISABLE KEYS */;
/*!40000 ALTER TABLE `factionstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grouppermissions`
--

DROP TABLE IF EXISTS `grouppermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grouppermissions` (
  `GroupID` int(4) NOT NULL,
  `CommandID` int(4) NOT NULL,
  PRIMARY KEY (`GroupID`,`CommandID`),
  KEY `GroupPermissionsCommandID` (`CommandID`),
  CONSTRAINT `GroupPermissionsCommandID` FOREIGN KEY (`CommandID`) REFERENCES `commands` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `GroupPermissionsGroupID` FOREIGN KEY (`GroupID`) REFERENCES `groups` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grouppermissions`
--

LOCK TABLES `grouppermissions` WRITE;
/*!40000 ALTER TABLE `grouppermissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `grouppermissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `Prefix` varchar(4) NOT NULL,
  `ChatColor` int(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hstring`
--

DROP TABLE IF EXISTS `hstring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hstring` (
  `StringID` smallint(2) NOT NULL,
  `BlockID` tinyint(1) NOT NULL,
  `EncryptedString` blob NOT NULL,
  `EncryptedLength` int(4) NOT NULL,
  `Displacement` int(4) NOT NULL,
  `CompressingValue` int(4) NOT NULL,
  PRIMARY KEY (`BlockID`,`StringID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hstring`
--

LOCK TABLES `hstring` WRITE;
/*!40000 ALTER TABLE `hstring` DISABLE KEYS */;
/*!40000 ALTER TABLE `hstring` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventories`
--

DROP TABLE IF EXISTS `inventories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventories` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Slots` int(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventories`
--

LOCK TABLES `inventories` WRITE;
/*!40000 ALTER TABLE `inventories` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itembases`
--

DROP TABLE IF EXISTS `itembases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itembases` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `FileID` int(4) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Type` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itembases`
--

LOCK TABLES `itembases` WRITE;
/*!40000 ALTER TABLE `itembases` DISABLE KEYS */;
/*!40000 ALTER TABLE `itembases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `BaseID` int(4) NOT NULL,
  `Quantity` int(4) NOT NULL,
  `Flags` int(4) NOT NULL,
  `DyeColor` int(4) NOT NULL,
  `CustomizedFor` int(4) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ItemsBaseID` (`BaseID`),
  KEY `ItemsCustomizedFor` (`CustomizedFor`),
  CONSTRAINT `ItemsBaseID` FOREIGN KEY (`BaseID`) REFERENCES `itembases` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ItemsCustomizedFor` FOREIGN KEY (`CustomizedFor`) REFERENCES `characters` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemstats`
--

DROP TABLE IF EXISTS `itemstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemstats` (
  `ItemID` int(4) NOT NULL,
  `StatID` int(4) NOT NULL,
  `Parameter1` int(4) NOT NULL,
  `Parameter2` int(4) NOT NULL,
  PRIMARY KEY (`StatID`,`ItemID`),
  KEY `ItemStatsItemID` (`ItemID`),
  CONSTRAINT `ItemStatsItemID` FOREIGN KEY (`ItemID`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemstats`
--

LOCK TABLES `itemstats` WRITE;
/*!40000 ALTER TABLE `itemstats` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levels`
--

DROP TABLE IF EXISTS `levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `levels` (
  `Level` int(4) NOT NULL AUTO_INCREMENT,
  `AttributePoints` int(4) NOT NULL,
  PRIMARY KEY (`Level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levels`
--

LOCK TABLES `levels` WRITE;
/*!40000 ALTER TABLE `levels` DISABLE KEYS */;
/*!40000 ALTER TABLE `levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maps`
--

DROP TABLE IF EXISTS `maps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `GameID` int(4) NOT NULL,
  `Hash` int(4) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `PvP` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=729 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps`
--

LOCK TABLES `maps` WRITE;
/*!40000 ALTER TABLE `maps` DISABLE KEYS */;
INSERT INTO `maps` VALUES (1,1,0,'Gladiators Arena',1),(2,2,0,'DEV Test Arena (1v1)',1),(3,3,0,'Test Map',0),(4,4,0,'Warriors Isle',1),(5,5,0,'Hunters Isle',1),(6,6,2,'Wizards Isle',0),(7,7,0,'Warriors Isle',0),(8,8,8,'Hunters Isle',0),(9,9,2,'Wizards Isle',0),(10,10,0,'Bloodstone Fen',0),(11,11,5,'The Wilds',0),(12,12,31330000,'Aurora Glade',0),(13,13,583,'Diessa Lowlands',0),(14,14,338,'Gates of Kryta',0),(15,15,7,'D`Alessio Seaboard',0),(16,16,81830000,'Divine Coast',0),(17,17,0,'Talmark Wilderness',0),(18,18,0,'The Black Curtain',0),(19,19,60910000,'Sanctum Cay',0),(20,20,6,'Droknar`s Forge',0),(21,21,0,'The Frost Gate',0),(22,22,82,'Ice Caves of Sorrow',0),(23,23,2,'Thunderhead Keep',0),(24,24,2,'Iron Mines of Moladune',0),(25,25,0,'Borlis Pass',0),(26,26,0,'Talus Chute',0),(27,27,0,'Griffons Mouth',1),(28,28,360000,'The Great Northern Wall',0),(29,29,52360000,'Fort Ranik',0),(30,30,53360000,'Ruins of Surmia',0),(31,31,0,'Xaquang Skyway',0),(32,32,59360000,'Nolani Academy',0),(33,33,593,'Old Ascalon',0),(34,34,0,'The Fissure of Woe',1),(35,35,0,'Ember Light Camp',0),(36,36,583,'Grendich Courthouse',0),(37,37,0,'Glints Challenge',0),(38,38,65,'Augury Rock',0),(39,39,593,'Sardelac Sanitarium',0),(40,40,75,'Piken Square',0),(41,41,639,'Sage Lands',0),(42,42,699,'Mamnoon Lagoon',0),(43,43,6,'Silverwood',0),(44,44,2147483647,'Ettins Back',0),(45,45,629,'Reed Bog',0),(46,46,689,'The Falls',0),(47,47,619,'Dry Top',0),(48,48,6,'Tangle Root',0),(49,49,379,'Henge of Denravi',0),(50,50,0,'Test Map',1),(51,51,0,'Senji`s Corner',0),(52,52,4,'Burning Isle',0),(53,53,0,'Tears of the Fallen',0),(54,54,0,'Scoundrels Rise',0),(55,55,0,'Lion`s Arch',0),(56,56,0,'Cursed Lands',0),(57,57,0,'Bergen Hot Springs',0),(58,58,2147483647,'North Kryta Province',0),(59,59,0,'Nebo Terrace',0),(60,60,10860000,'Majestys Rest',0),(61,61,11860000,'Twin Serpent Lakes',0),(62,62,0,'Watchtower Coast',0),(63,63,0,'Stingray Strand',0),(64,64,0,'Kessex Peak',0),(65,65,0,'DAlessio Arena',1),(66,66,0,'All Call Click Point 1',1),(67,67,4,'Burning Isle',0),(68,68,65,'Frozen Isle',0),(69,69,68,'Nomads Isle',0),(70,70,0,'Druids Isle',1),(71,71,9,'Isle of the Dead',0),(72,72,0,'The Underworld',1),(73,73,0,'Riverside Province',0),(74,74,0,'Tournament 6',1),(75,75,0,'The Hall of Heroes',1),(76,76,0,'Broken Tower',1),(77,77,2147483647,'House zu Heltzer',0),(78,78,0,'The Courtyard',1),(79,79,0,'Unholy Temples',1),(80,80,0,'Burial Mounds',1),(81,81,24560000,'Ascalon City',0),(82,82,0,'Tomb of the Primeval Kings',0),(83,83,0,'The Vault',1),(84,84,0,'The Underworld',1),(85,85,0,'Ascalon Arena',1),(86,86,0,'Sacred Temples',1),(87,87,0,'Icedome',0),(88,88,0,'Iron Horse Mine',0),(89,89,1,'Anvil Rock',0),(90,90,2,'Lornars Pass',0),(91,91,0,'Snake Dance',0),(92,92,5,'Tascas Demise',0),(93,93,0,'Spearhead Peak',1),(94,94,6,'Ice Floe',0),(95,95,7,'Witmans Folly',0),(96,96,8,'Mineral Springs',0),(97,97,9,'Dreadnoughts Drift',0),(98,98,0,'Frozen Forest',0),(99,99,0,'Travelers Vale',0),(100,100,0,'Deldrimor Bowl',0),(101,101,74,'Regent Valley',0),(102,102,75,'The Breach',0),(103,103,76,'Ascalon Foothills',0),(104,104,77,'Pockmark Flats',0),(105,105,78,'Dragons Gullet',0),(106,106,78,'Flame Temple Corridor',0),(107,107,7,'Eastern Frontier',0),(108,108,0,'The Scar',0),(109,109,0,'The Amnoon Oasis',0),(110,110,55,'Diviners Ascent',0),(111,111,56,'Vulture Drifts',0),(112,112,5,'The Arid Sea',0),(113,113,63,'Prophets Path',0),(114,114,64,'Salt Flats',0),(115,115,65,'Skyward Reach',0),(116,116,66,'Dunes of Despair',0),(117,117,67,'Thirsty River',0),(118,118,68,'Elona Reach',0),(119,119,65,'Augury Rock',0),(120,120,6,'The Dragons Lair',0),(121,121,0,'Perdition Rock',0),(122,122,0,'Ring Of Fire',0),(123,123,7,'Abaddon`s Mouth',0),(124,124,8,'Hell`s Precipice',0),(125,125,0,'Titans Tears',1),(126,126,0,'Golden Gates',1),(127,127,0,'Scarred Earth',1),(128,128,1,'The Eternal Grove',0),(129,129,0,'Lutgardis Conservatory',0),(130,130,1,'Vasburg Armory',0),(131,131,77,'Serenity Temple',0),(132,132,1,'Icetooth Cave',0),(133,133,2,'Beacon`s Perch',0),(134,134,0,'Yak`s bend',0),(135,135,7,'Frontier Gate',0),(136,136,0,'Beetletun',0),(137,137,0,'Fisherman`s Haven',0),(138,138,0,'Temple of the Ages',0),(139,139,2147483647,'Ventari`s Refuge',0),(140,140,639,'Druid`s OVerlook',0),(141,141,6,'Maguuma Stade',0),(142,142,6,'Quarrel Falls',0),(143,143,0,'Ascalon Academy',1),(144,144,1,'Gyala Hatchery',0),(145,145,30,'The Catacombs',0),(146,146,7,'Lakeside County',0),(147,147,39,'The Northlands',0),(148,148,7,'Pre Ascalon City',0),(149,149,0,'Ascalon Academy',1),(150,150,0,'Ascalon Academy',0),(151,151,0,'Ascalon Academy',0),(152,152,63,'Heroes` Audience',0),(153,153,64,'Seeker`s Passage',0),(154,154,0,'Destiny`s Gorge',0),(155,155,0,'Camp Rankor',0),(156,156,5,'The Granite Citadel',0),(157,157,6,'Marhan`s Grotto',0),(158,158,7,'Port Sledge',0),(159,159,0,'Copperhammer Mines',0),(160,160,0,'Green Hills County',1),(161,161,0,'Wizards Folly',1),(162,162,1,'Regent Valley',0),(163,163,26,'The Barradin Estate',0),(164,164,7,'Ashford Abbey',0),(165,165,0,'Foible`s Fair',0),(166,166,1,'Pre Fort Ranik',0),(167,167,0,'Burning Isle',1),(168,168,0,'Druids Isle',1),(169,169,0,'[null]',1),(170,170,0,'Frozen Isle',1),(171,171,0,'Warriors Isle',1),(172,172,0,'Hunters Isle',1),(173,173,0,'Wizards Isle',1),(174,174,0,'Nomads Isle',1),(175,175,0,'Isle of the Dead',1),(176,176,0,'Frozen Isle',1),(177,177,0,'Nomads Isle',1),(178,178,0,'Druids Isle',1),(179,179,0,'Isle of the Dead',1),(180,180,0,'Fort Koga',1),(181,181,0,'Shiverpeak Arena',0),(182,182,0,'Amnoon Arena',1),(183,183,0,'Deldrimor Arena',1),(184,184,0,'The Crag',1),(185,185,0,'The Underworld',1),(186,186,0,'The Underworld',1),(187,187,0,'The Underworld',1),(188,188,0,'Random Arenas',0),(189,189,38850200,'Team Arenas',0),(190,190,0,'Sorrows Furnace',1),(191,191,89520200,'Grenths Footprint',0),(192,192,0,'All Call Click Point 2',1),(193,193,2147483647,'Cavalon',0),(194,194,0,'Kaineng Center',0),(195,195,9,'Drazach Thicket',0),(196,196,26650200,'Jaya Bluffs',0),(197,197,0,'Shenzun Tunnels',0),(198,198,0,'Archipelagos',0),(199,199,0,'Maishang Hills',0),(200,200,0,'Mount Qinkai',0),(201,201,0,'Melandrus Hope',0),(202,202,0,'Rheas Crater',0),(203,203,0,'Silent Surf',0),(204,204,2147483647,'Unwaking Waters',0),(205,205,0,'Morostav Trail',0),(206,206,89520200,'Deldrimor War Camp',0),(207,207,0,'Dragons Thieves',1),(208,208,0,'Heroes Crypt',1),(209,209,3,'Mourning Veil Falls',0),(210,210,345,'Ferndale',0),(211,211,0,'Pongmei Valley',0),(212,212,29650200,'Monastery Overlook',0),(213,213,0,'Zen Daijun',0),(214,214,9,'Minister Cho`s Estate',0),(215,215,0,'Vizunah Square',0),(216,216,0,'Nahpui Quarter',0),(217,217,0,'Tahnnakai Temple',0),(218,218,2147483647,'Arborstone',0),(219,219,2147483647,'Boreas Seabed',0),(220,220,0,'Sunjiang District',0),(221,221,0,'Fort Aspenwood',1),(222,222,1,'The Eternal Grove',0),(223,223,0,'The Jade Quarry',1),(224,224,1,'Gyala Hatchery',0),(225,225,1,'Raisu Palace',0),(226,226,1,'Imperial Sanctum',0),(227,227,2147483647,'Unwaking Waters',0),(228,228,0,'Grenz Frontier',1),(229,229,0,'The Ancestral Lands',1),(230,230,2147483647,'Amatz Basin',0),(231,231,0,'Kaanai Canyon',1),(232,232,0,'Shadows Passage',0),(233,233,1,'Raisu Palace',0),(234,234,2147483647,'The Aurios Mines',0),(235,235,5,'Panjiang Peninsula',0),(236,236,78650200,'Kinya Province',0),(237,237,97650200,'Haiju Lagoon',0),(238,238,9,'Sunqua Vale',0),(239,239,0,'Wajjun Bazaar',0),(240,240,0,'Bukdek Byway',0),(241,241,0,'The Undercity',0),(242,242,18,'Shing Jea Monastery',0),(243,243,2147483647,'Shing Jea Arena',0),(244,244,2147483647,'Arborstone',0),(245,245,9,'Minister Chos Estate',0),(246,246,0,'Zen Daijun',0),(247,247,2147483647,'Boreas Seabed',0),(248,248,0,'Great Temple of Balthazar',0),(249,249,9,'Tsumei Village',0),(250,250,29650200,'Seitung Harbor',0),(251,251,78650200,'Ran Musu Gardens',0),(252,252,29650200,'Linnok Courtyard',0),(253,253,0,'Dwayna Vs Grenth',1),(254,254,0,'Dwaynas Camp',1),(255,255,0,'Grenths Camp',1),(256,256,338,'Gates of Kryta',0),(257,257,0,'Minister Chos Estate',1),(258,258,0,'Zen Daijun',1),(259,259,0,'The Jade Quarry (Kurzick)',1),(260,260,0,'Nahpui Quarter',1),(261,261,0,'Tahnnakai Temple',1),(262,262,0,'Arborstone',1),(263,263,0,'Boreas Seabed',1),(264,264,0,'Sunjiang District',1),(265,265,0,'Nahpui Quarter',0),(266,266,0,'Urgozs Warren',0),(267,267,0,'The Eternal Grove',1),(268,268,0,'Gyala Hatchery',1),(269,269,0,'Tahnnakai Temple',0),(270,270,0,'Raisu Palace',1),(271,271,0,'Imperial Sanctum',1),(272,272,2147483647,'Altrumm Ruins',0),(273,273,2147483647,'Zos Shrivros Channel',0),(274,274,0,'Dragon`s Throat',0),(275,275,0,'Isle of Weeping Stone',1),(276,276,0,'Isle of Jade',1),(277,277,2147483647,'Harvest Temple',0),(278,278,0,'Breaker Hollow',0),(279,279,1,'Leviathan Pits',0),(280,280,0,'Isle of the Nameless',0),(281,281,0,'Zaishen Challenge',0),(282,282,0,'Zaishen Elite',0),(283,283,0,'Maatu Keep',0),(284,284,0,'ZinKu Corridor',0),(285,285,0,'Monastery Overlook',1),(286,286,9,'Brauer Academy',0),(287,287,3,'Durheim Archives',0),(288,288,0,'Bai Paasu Reach',0),(289,289,0,'Seafarer`s Rest',0),(290,290,0,'Bejunkan Pier',0),(291,291,0,'Vizunah Square (Local Quarter',0),(292,292,0,'Vizunah Square (Foreign Quart',0),(293,293,1,'Fort Aspenwood (Luxon)',0),(294,294,1,'Fort Aspenwood (Kurzick)',0),(295,295,100000,'The Jade Quarry (Luxon)',0),(296,296,100000,'The Jade Quarry (Kurzick)',0),(297,297,2147483647,'Unwaking Waters (Luxon)',0),(298,298,2147483647,'Unwaking Waters (Kurzick)',0),(299,299,0,'Saltspray Beach',1),(300,300,0,'Etnaran Keys',1),(301,301,1,'Raisu Pavilion',0),(302,302,0,'Kaineng Docks',0),(303,303,0,'The Marketplace',0),(304,304,0,'Vizunah Square (Local Quarter',1),(305,305,0,'Vizunah Square (Foreign Quart',1),(306,306,0,'The Jade Quarry (Luxon)',1),(307,307,2147483647,'The Deep',0),(308,308,0,'Ascalon Arena',1),(309,309,0,'Annihilation Training',1),(310,310,0,'Kill Count Training',1),(311,311,0,'Priest Annihilation Training',1),(312,312,0,'Obelisk Annihilation Training',1),(313,313,29650200,'Saoshang Trail',0),(314,314,0,'Shiverpeak Arena',1),(315,315,0,'Travel',1),(316,316,0,'Travel',1),(317,317,0,'Travel',1),(318,318,0,'DAlessio Arena',0),(319,319,0,'Amnoon Arena',0),(320,320,0,'Fort Koga',0),(321,321,0,'Heroes Crypt',0),(322,322,0,'Shiverpeak Arena',0),(323,323,0,'Fort Aspenwood (Kurzick)',1),(324,324,0,'Fort Aspenwood (Luxon)',1),(325,325,0,'The Harvest Ceremony',1),(326,326,0,'The Harvest Ceremony',1),(327,327,0,'Imperial Sanctum',1),(328,328,0,'Saltspray Beach (Luxon)',1),(329,329,0,'Saltspray Beach (Kurzick)',1),(330,330,9,'Heroes` Ascent',0),(331,331,0,'Grenz Frontier (Luxon)',1),(332,332,0,'Grenz Frontier (Kurzick)',1),(333,333,0,'The Ancestral Lands (Luxon)',1),(334,334,0,'The Ancestral Lands (Kurzick)',1),(335,335,0,'Etnaran Keys (Luxon)',1),(336,336,0,'Etnaran Keys (Kurzick)',1),(337,337,0,'Kaanai Canyon (Luxon)',1),(338,338,0,'Kaanai Canyon (Kurzick)',1),(339,339,0,'DAlessio Arena',1),(340,340,0,'Amnoon Arena',1),(341,341,0,'Fort Koga',1),(342,342,0,'Heroes Crypt',1),(343,343,0,'Shiverpeak Arena',1),(344,344,0,'The Hall of Heroes',1),(345,345,0,'The Courtyard',1),(346,346,0,'Scarred Earth',1),(347,347,0,'The Underworld',1),(348,348,0,'Tanglewood Copse',0),(349,349,345,'Saint Anjeka`s Shrine',0),(350,350,0,'Eredon Terrace',0),(351,351,0,'Divine Path',1),(352,352,0,'Brawlers Pit',1),(353,353,0,'Petrified Arena',1),(354,354,0,'Seabed Arena',1),(355,355,0,'Isle of Weeping Stone',1),(356,356,0,'Isle of Jade',1),(357,357,0,'Imperial Isle',1),(358,358,0,'Isle of Meditation',1),(359,359,0,'Imperial Isle',1),(360,360,84870200,'Isle Of Meditation',0),(361,361,1,'Isle of Weeping Stone',0),(362,362,25660200,'Isle of Jade',0),(363,363,36870200,'Imperial Isle',0),(364,364,84870200,'Isle of Meditation',0),(365,365,0,'Random Arenas Test',1),(366,366,2147483647,'Shing Jea Arena',0),(367,367,0,'All Skills',1),(368,368,0,'Dragon Arena',1),(369,369,0,'Jahai Bluffs',0),(370,370,0,'Kamadan, Jewel of Istan',1),(371,371,273,'Marga Coast',0),(372,372,0,'Fahranur, The First City',1),(373,373,6,'Sunward Marches',0),(374,374,0,'oleplaying Character //Buuug?',1),(375,375,0,'Vortex',0),(376,376,0,'Camp Hojanu',0),(377,377,13490300,'Bahdok Caverns',0),(378,378,0,'Wehhan Terraces',1),(379,379,97480300,'Dejarin Estate',0),(380,380,0,'Arkjok Ward',0),(381,381,0,'Yohlon Haven',1),(382,382,73480300,'Gandara, the Moon Fortress',0),(383,383,0,'Vortex',1),(384,384,35440300,'The Floodplain of Mahnkelon',0),(385,385,0,'Lions Arch',1),(386,386,2,'Turais Procession',0),(387,387,384,'Sunspear Sanctuary',0),(388,388,1,'Aspenwood Gate (Kurzick)',0),(389,389,1,'Aspenwood Gate (Luxon)',0),(390,390,100000,'Jade Flats (Kurzick)',0),(391,391,100000,'Jade Flats (Luxon)',0),(392,392,2147483647,'Yatendi Canyons',0),(393,393,2147483647,'Chantry of Secrets',0),(394,394,544,'Garden of Seborhin',0),(395,395,334,'Holdings of Chokhin',0),(396,396,83380300,'Mihanu Township',0),(397,397,3,'Vehjin Mines',0),(398,398,0,'Basalt Grotto',0),(399,399,3,'Forum Highlands',0),(400,400,0,'Kaineng Center',1),(401,401,0,'Sebelkeh Basilica',1),(402,402,0,'Resplendent Makuun',0),(403,403,0,'Honur Hill',0),(404,404,0,'Wilderness of Bahdza',0),(405,405,0,'Sun Docks',1),(406,406,63530300,'Vehtendi Valley',0),(407,407,0,'Yahnur Market',0),(408,408,0,'Here Be Dragons',1),(409,409,0,'Here Be Dragons',1),(410,410,0,'Here Be Dragons',1),(411,411,0,'Here Be Dragons',1),(412,412,0,'Here Be Dragons',1),(413,413,2147483647,'The Hidden City of Ahdashim',0),(414,414,74,'The Kodash Bazaar',0),(415,415,0,'Lions Gate',0),(416,416,29650200,'Monastery Overlook',0),(417,417,0,'Bejunkan Pier',1),(418,418,0,'Lions Gate',1),(419,419,0,'The Mirror of Lyss',0),(420,420,0,'Secure the Refuge',1),(421,421,0,'Venta Cemetery',1),(422,422,0,'Kamadan, Jewel of Istan',1),(423,423,0,'The Tribunal',1),(424,424,6,'Kodonur Crossroads',0),(425,425,35440300,'Rilohn Refuge',0),(426,426,0,'Pogahn Passage',0),(427,427,13490300,'Moddok Crevice',0),(428,428,3,'Tihark Orchard',0),(429,429,0,'Consulate',0),(430,430,0,'Plains of Jarin',0),(431,431,51360300,'Sunspear Great Hall',0),(432,432,0,'Cliffs of Dohjok',0),(433,433,0,'Dzagonur Bastion',0),(434,434,2147483647,'Dasha Vestibule',0),(435,435,0,'Grand Court of Sebelkeh',0),(436,436,384,'Command Post',0),(437,437,16350300,'Jokos Domain',0),(438,438,16350300,'Bone Palace',0),(439,439,74350300,'The Ruptured Heart',0),(440,440,74350300,'The Mouth of Torment',0),(441,441,0,'The Shattered Ravines',0),(442,442,83350300,'Lair of the Forgotten',0),(443,443,0,'Poisoned Outcrops',0),(444,444,794,'The Sulfurous Wastes',0),(445,445,0,'The Ebony Citadel of Mallyx',1),(446,446,94350300,'The Alkali Pan',0),(447,447,0,'Cliffs of Dohjok',1),(448,448,0,'Crystal Overlook',1),(449,449,0,'Kamadan, Jewel of Istan',0),(450,450,2,'Gate of Torment',0),(451,451,0,'Gate of Anguish',1),(452,452,0,'Secure the Refuge',1),(453,453,0,'Evacuation',1),(454,454,0,'Test Map',1),(455,455,0,'Nightfallen Garden',1),(456,456,0,'Churrhir Fields',0),(457,457,0,'Beknur Harbor',1),(458,458,0,'Kodonur Crossroads',1),(459,459,0,'Rilohn Refuge',1),(460,460,0,'Pogahn Passage',1),(461,461,0,'The Underworld',1),(462,462,0,'Heart of Abaddon',1),(463,463,0,'The Underworld',1),(464,464,0,'Nightfallen Coast',1),(465,465,0,'Nightfallen Jahai',1),(466,466,0,'Depths of Madness',1),(467,467,0,'Rollerbeetle Racing',1),(468,468,32580300,'Domain of Fear',0),(469,469,2,'Gate of Fear',0),(470,470,0,'Domain of Pain',0),(471,471,0,'Bloodstone Fen',1),(472,472,0,'Domain of Secrets',1),(473,473,2,'Gate of Secrets',0),(474,474,2,'Gate of Anguish',0),(475,475,0,'Jelly Den',1),(476,476,203,'Jennurs Horde',0),(477,477,273,'Nundu Bay',0),(478,478,3,'Gate of Desolation',0),(479,479,0,'Champions Dawn',0),(480,480,94350300,'Ruins of Morah',0),(481,481,114,'Fahranur, The First City',0),(482,482,6,'Bjora Marches',0),(483,483,1,'Zehlon Reach',0),(484,484,344,'Lahtenda Bog',0),(485,485,2147483647,'Arbor Bay',0),(486,486,27370300,'Issnur Isles',0),(487,487,27370300,'Beknur Harbor',0),(488,488,444,'Mehtani Keys',0),(489,489,2,'Kodlonu Hamlet',0),(490,490,0,'Island of Shehkah',1),(491,491,0,'Jokanur Diggings',0),(492,492,0,'Blacktide Den',0),(493,493,0,'Consulate Docks',0),(494,494,2,'Gate of Pain',0),(495,495,2,'Gate of Madness',0),(496,496,98440300,'Abaddons Gate',0),(497,497,4,'Sunspear Arena',0),(498,498,0,'Travel',1),(499,499,2,'Ice Cliff Chasms',0),(500,500,0,'Bokka Amphitheatre',1),(501,501,51700400,'Riven Earth',0),(502,502,0,'The Astralarium',0),(503,503,0,'Throne Of Secrets',1),(504,504,0,'Churranu Island Arena',1),(505,505,0,'Shing Jea Monastery',1),(506,506,0,'Haiju Lagoon',1),(507,507,0,'Jaya Bluffs',1),(508,508,0,'Seitung Harbor',1),(509,509,0,'Tsumei Village',1),(510,510,0,'Seitung Harbor',1),(511,511,0,'Tsumei Village',1),(512,512,0,'Minister Chos Estate',1),(513,513,1680400,'Drakkar Lake',0),(514,514,0,'Island of Shehkah',0),(515,515,0,'Jokanur Diggings',1),(516,516,0,'Blacktide Den',1),(517,517,0,'Consulate Docks',1),(518,518,0,'Tihark Orchard',1),(519,519,0,'Dzagonur Bastion',1),(520,520,0,'Hidden City of Ahdashim',1),(521,521,0,'Grand Court of Sebelkeh',1),(522,522,0,'Jennurs Horde',1),(523,523,0,'Nundu Bay',1),(524,524,0,'Gates of Desolation',1),(525,525,0,'Ruins of Morah',1),(526,526,0,'Domain of Pain',1),(527,527,0,'Gate of Madness',1),(528,528,0,'Abaddons Gate',1),(529,529,0,'Uncharted Isle',1),(530,530,0,'Isle of Wurms',1),(531,531,56300300,'Uncharted Isle',0),(532,532,1,'Isle of Wurms',0),(533,533,0,'Uncharted Isle',1),(534,534,0,'Isle of Wurms',1),(535,535,0,'Ahmtur Arena',1),(536,536,0,'Sunspear Arena',1),(537,537,2147483647,'Corrupted Isle',0),(538,538,0,'Isle of Solitude',0),(539,539,2147483647,'Corrupted Isle',0),(540,540,0,'Isle of Solitude',0),(541,541,0,'Corrupted Isle',1),(542,542,0,'Isle of Solitude',1),(543,543,0,'Sun Docks',0),(544,544,0,'Chahbek Village',0),(545,545,8,'Remains of Sahlahja',0),(546,546,7,'Jaga Moraine',0),(547,547,0,'Bombardment',1),(548,548,3,'Norrhart Domains',0),(549,549,0,'Hero Battles',0),(550,550,0,'Hero Battles',1),(551,551,0,'The Crossing',1),(552,552,0,'Desert Sands',1),(553,553,506,'Varajar Fells',0),(554,554,383,'Dajkah Inlet',0),(555,555,0,'The Shadow Nexus',1),(556,556,0,'Chahbek Village',1),(557,557,0,'Throne Of Secrets',1),(558,558,5630400,'Sparkfly Swamp',0),(559,559,2,'Gate of the Nightfallen Lands',0),(560,560,0,'Cathedral of Flames',1),(561,561,0,'Gate of Torment',1),(562,562,0,'Fortress of Jahai',1),(563,563,0,'Halls of Chokhin',1),(564,564,0,'Citadel of Dzagon',1),(565,565,0,'Dynastic Tombs',1),(566,566,0,'Verdant Cascades',0),(567,567,0,'Cathedral of Flames: Level 2',1),(568,568,0,'Cathedral of Flames: Level 3',1),(569,569,27640400,'Magus Stones',0),(570,570,0,'Catacombs of Kathandrax',1),(571,571,0,'Catacombs of Kathandrax: Leve',1),(572,572,47650400,'Alcazia Tangle',0),(573,573,0,'Rragars Menagerie',1),(574,574,0,'Rragars Menagerie: Level 2',1),(575,575,0,'Rragars Menagerie: Level 3',1),(576,576,0,'Jelly Den: Level 2',1),(577,577,0,'Slavers Exile',1),(578,578,0,'Oolas Lab',1),(579,579,0,'Oolas Lab: Level 2',1),(580,580,0,'Oolas Lab: Level 3',1),(581,581,0,'Shards of Orr',1),(582,582,0,'Shards of Orr: Level 2',1),(583,583,0,'Shards of Orr: Level 3',1),(584,584,0,'Arachnis Haunt',1),(585,585,0,'Arachnis Haunt: Level 2',1),(586,586,0,'Burning Embers',1),(587,587,0,'Burning Furnace',1),(588,588,0,'Burning Temple',1),(589,589,0,'Catacombs Lush',1),(590,590,0,'Catacombs Stone',1),(591,591,0,'Catacombs Tomb',1),(592,592,0,'5 team test',1),(593,593,0,'Fetid River',1),(594,594,0,'Overlook',1),(595,595,0,'Cemetery',1),(596,596,0,'Forgotten Shrines',1),(597,597,0,'Track',1),(598,598,0,'The Antechamber',1),(599,599,0,'Collision',1),(600,600,0,'The Hall of Heroes',1),(601,601,0,'Frozen Crevasse',1),(602,602,0,'Frozen Depleted',1),(603,603,0,'Frozen Fort',1),(604,604,0,'Vloxen Excavations',1),(605,605,0,'Vloxen Excavations: Level 2',1),(606,606,0,'Vloxen Excavations: Level 3',1),(607,607,0,'Heart of the Shiverpeaks',0),(608,608,0,'Heart of the Shiverpeaks: Lev',1),(609,609,0,'Heart of the Shiverpeaks: Lev',1),(610,610,0,'The Journey to Nornheim',1),(611,611,0,'The Journey to Nornheim',1),(612,612,0,'Bloodstone Caves',1),(613,613,0,'Bloodstone Caves: Level 2',1),(614,614,0,'Bloodstone Caves: Level 3',1),(615,615,0,'Bogroot Growths',1),(616,616,0,'Bogroot Growths: Level 2',1),(617,617,0,'Ravens Point',1),(618,618,0,'Ravens Point: Level 2',1),(619,619,0,'Ravens Point: Level 3',1),(620,620,0,'Slavers Exile',1),(621,621,0,'Slavers Exile',1),(622,622,0,'Slavers Exile',1),(623,623,0,'Slavers Exile',1),(624,624,2147483647,'Vloxs Falls',0),(625,625,0,'Battledepths',0),(626,626,0,'Battledepths: Level 2',1),(627,627,0,'Battledepths: Level 3',1),(628,628,0,'Sepulchre of Dragrimmar',1),(629,629,0,'Sepulchre of Dragrimmar: Leve',1),(630,630,0,'Frostmaws Burrows',1),(631,631,0,'Frostmaws Burrows: Level 2',1),(632,632,0,'Frostmaws Burrows: Level 3',1),(633,633,0,'Frostmaws Burrows: Level 4',1),(634,634,0,'Frostmaws Burrows: Level 5',1),(635,635,0,'Darkrime Delves',1),(636,636,0,'Darkrime Delves: Level 2',1),(637,637,0,'Darkrime Delves: Level 3',1),(638,638,5630400,'Gadds Encampment',0),(639,639,0,'Umbral Grotto',0),(640,640,27640400,'Rata Sum',0),(641,641,47650400,'Tarnished Haven',0),(642,642,2,'Eye of the North',0),(643,643,1680400,'Sifhalla',0),(644,644,3,'Gunnars Hold',0),(645,645,506,'Olafstead',0),(646,646,2,'Hall of Monuments',0),(647,647,0,'Dalada Uplands',0),(648,648,0,'Doomlore Shrine',0),(649,649,856,'Grothmar Wardowns',0),(650,650,856,'Longeyes Ledge',0),(651,651,44710400,'Sacnoth Valley',0),(652,652,866,'Central Transfer Chamber',0),(653,653,0,'Revealing the Curse',1),(654,654,0,'My Side of the Mountain',1),(655,655,0,'Mountain Owl',1),(656,656,0,'Mountain Owl',1),(657,657,0,'Mountain Owl',1),(658,658,0,'Oolas Laboratory',1),(659,659,0,'Oolas Laboratory',1),(660,660,0,'Oolas Laboratory',1),(661,661,0,'Completing Gadds Research',1),(662,662,0,'Completing Gadds Research',1),(663,663,0,'Completing Gadds Research',1),(664,664,0,'Genius Operated Living Enchan',1),(665,665,0,'Assaulting the Charr Camp',1),(666,666,0,'Freeing Pyres Warband',1),(667,667,0,'Freeing Pyres Warband',1),(668,668,0,'Freeing Pyres Warband',1),(669,669,0,'Freeing the Vanguard',1),(670,670,0,'Source of Destruction',1),(671,671,0,'Source of Destruction',1),(672,672,0,'Source of Destruction',1),(673,673,17,'A Time for Heroes',0),(674,674,0,'Steppe Practice',1),(675,675,2,'Boreal Station',0),(676,676,0,'Catacombs of Kathandrax: Leve',1),(677,677,0,'Hall of Primordus',1),(678,678,0,'Mountain Holdout',1),(679,679,0,'Cinematic Cave Norn Cursed',1),(680,680,0,'Cinematic Steppe Interrogatio',1),(681,681,0,'Cinematic Interior Research',1),(682,682,1,'Cinematic Eye Vision A',0),(683,683,1,'Cinematic Eye Vision B',0),(684,684,1,'Cinematic Eye Vision C',0),(685,685,1,'Cinematic Eye Vision D',0),(686,686,0,'Polymock Coliseum',1),(687,687,0,'Polymock Glacier',1),(688,688,0,'Polymock Crossing',1),(689,689,0,'Cinematic Mountain Resolution',1),(690,690,0,'<Mountain Polar OP>',1),(691,691,66750400,'Beneath Lions Arch',0),(692,692,66750400,'Tunnels Below Cantha',0),(693,693,66750400,'Caverns Below Kamadan',0),(694,694,0,'Cinematic Mountain Dwarfs',1),(695,695,0,'The Eye of the North',1),(696,696,0,'The Eye of the North',1),(697,697,0,'The Eye of the North',1),(698,698,0,'Hero Tutorial',1),(699,699,0,'Prototype Map',1),(700,700,0,'The Norn Fighting Tournament',1),(701,701,0,'Hundars Resplendent Treasure',1),(702,702,0,'Norn Brawling Championship',1),(703,703,0,'Kilroys Punchout Training',1),(704,704,0,'Fronis Irontoe`s Lair',0),(705,705,0,'INTERIOR_WATCH_OP1_FINAL_BATT',1),(706,706,0,'Designer Test Map',1),(707,707,0,'The Great Norn Alemoot',1),(708,708,0,'(Mountain Traverse)',1),(709,709,0,'Battle for Nornheim',1),(710,710,0,'Destroyer Ending',1),(711,711,0,'Alcazia Tangle',1),(712,712,0,'Mission Pack Test',1),(713,713,0,'North Kryta Province',1),(714,714,0,'Bukdek Byway',1),(715,715,0,'Plains of Jarin',1),(716,716,0,'Plains of Jarin',1),(717,717,0,'Plains of Jarin',1),(718,718,0,'Plains of Jarin',1),(719,719,0,'Plains of Jarin',1),(720,720,0,'Plains of Jarin',1),(721,721,0,'Plains of Jarin',1),(722,722,0,'Plains of Jarin',1),(723,723,0,'Plains of Jarin',1),(724,724,0,'Plains of Jarin',1),(725,725,0,'Plains of Jarin',1),(726,726,3,'Plains of Jarin',0),(727,727,0,'Plains of Jarin',1),(728,728,0,'Crash! boom!',1);
/*!40000 ALTER TABLE `maps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `npcs`
--

DROP TABLE IF EXISTS `npcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `npcs` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Model` int(4) NOT NULL,
  `Allegiance` int(4) NOT NULL,
  `Weapons` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `NPCsWeapons` (`Weapons`),
  CONSTRAINT `NPCsWeapons` FOREIGN KEY (`Weapons`) REFERENCES `weapons` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `npcs`
--

LOCK TABLES `npcs` WRITE;
/*!40000 ALTER TABLE `npcs` DISABLE KEYS */;
/*!40000 ALTER TABLE `npcs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionaccess`
--

DROP TABLE IF EXISTS `professionaccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionaccess` (
  `CharacterID` int(4) NOT NULL,
  `Profession` int(4) NOT NULL,
  PRIMARY KEY (`CharacterID`,`Profession`),
  KEY `ProfessionAccessProfession` (`Profession`),
  CONSTRAINT `ProfessionAccessCharacterID` FOREIGN KEY (`CharacterID`) REFERENCES `characters` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ProfessionAccessProfession` FOREIGN KEY (`Profession`) REFERENCES `professions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionaccess`
--

LOCK TABLES `professionaccess` WRITE;
/*!40000 ALTER TABLE `professionaccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `professionaccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professions`
--

DROP TABLE IF EXISTS `professions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professions` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professions`
--

LOCK TABLES `professions` WRITE;
/*!40000 ALTER TABLE `professions` DISABLE KEYS */;
INSERT INTO `professions` VALUES (1,'Warrior'),(2,'Ranger'),(3,'');
/*!40000 ALTER TABLE `professions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skillaccess`
--

DROP TABLE IF EXISTS `skillaccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skillaccess` (
  `CharacterID` int(4) NOT NULL,
  `SkillID` int(4) NOT NULL,
  PRIMARY KEY (`CharacterID`,`SkillID`),
  KEY `SkillAccessSkillID` (`SkillID`),
  CONSTRAINT `SkillAccessCharacterID` FOREIGN KEY (`CharacterID`) REFERENCES `characters` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SkillAccessSkillID` FOREIGN KEY (`SkillID`) REFERENCES `skills` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skillaccess`
--

LOCK TABLES `skillaccess` WRITE;
/*!40000 ALTER TABLE `skillaccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `skillaccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skills` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Attribute` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SkillsAttribute` (`Attribute`),
  CONSTRAINT `SkillsAttribute` FOREIGN KEY (`Attribute`) REFERENCES `attributes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skillsequipped`
--

DROP TABLE IF EXISTS `skillsequipped`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skillsequipped` (
  `CharacterID` int(4) NOT NULL,
  `SkillID` int(4) NOT NULL,
  `Slot` int(4) NOT NULL,
  PRIMARY KEY (`CharacterID`,`SkillID`,`Slot`),
  KEY `SkillsEquippedSkillID` (`SkillID`),
  CONSTRAINT `SkillsEquippedCharacterID` FOREIGN KEY (`CharacterID`) REFERENCES `characters` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SkillsEquippedSkillID` FOREIGN KEY (`SkillID`) REFERENCES `skills` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skillsequipped`
--

LOCK TABLES `skillsequipped` WRITE;
/*!40000 ALTER TABLE `skillsequipped` DISABLE KEYS */;
/*!40000 ALTER TABLE `skillsequipped` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spawnpoints`
--

DROP TABLE IF EXISTS `spawnpoints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spawnpoints` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `MapID` int(4) NOT NULL,
  `X` float(4,0) NOT NULL,
  `Y` float(4,0) NOT NULL,
  `Plane` int(4) NOT NULL,
  `Radius` int(4) NOT NULL,
  PRIMARY KEY (`ID`,`MapID`),
  KEY `SpawnPointsMapID` (`MapID`),
  CONSTRAINT `SpawnPointsMapID` FOREIGN KEY (`MapID`) REFERENCES `maps` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spawnpoints`
--

LOCK TABLES `spawnpoints` WRITE;
/*!40000 ALTER TABLE `spawnpoints` DISABLE KEYS */;
INSERT INTO `spawnpoints` VALUES (1,3,-1807,-1578,0,0),(2,6,5593,8689,0,0),(3,7,4026,8844,0,0),(4,8,4982,8457,0,0),(5,9,5387,9282,0,0),(6,10,9999,-7638,0,0),(7,11,9999,-9999,0,0),(8,12,-9999,4523,0,0),(9,12,-9999,4584,0,0),(10,13,-9999,-9999,0,0),(11,14,-441,9999,0,0),(12,15,9999,9750,0,0),(13,16,9999,-6724,0,0),(14,17,9999,769,0,0),(15,18,-5205,9999,0,0),(16,19,-9999,6378,0,0),(17,20,1600,3530,0,0),(18,20,3764,4289,0,0),(19,20,425,5283,0,0),(20,20,1640,3389,0,0),(21,21,7098,9999,0,0),(22,22,-9999,-6881,0,0),(23,23,-9999,-9999,0,0),(24,24,-4097,-9999,0,0),(25,25,9999,-2217,0,0),(26,26,-9999,9999,0,0),(27,26,8612,-9999,0,0),(28,28,9999,-7439,0,0),(29,29,9999,-9999,0,0),(30,30,-2850,-9999,0,0),(31,31,5382,-9999,0,0),(32,32,-477,9999,0,0),(33,33,-4747,-65,0,0),(34,33,9999,5147,0,0),(35,33,9999,9999,0,0),(36,35,3081,-9999,0,0),(37,36,-614,9999,0,0),(38,37,-6740,-2361,0,0),(39,38,-9999,-224,0,0),(40,39,-6091,526,0,0),(41,39,-5459,-9,0,0),(42,40,9999,9016,0,0),(43,41,2610,-9302,0,0),(44,42,7224,5147,0,0),(45,43,2384,-1697,0,0),(46,44,-9999,182,0,0),(47,45,7336,6852,0,0),(48,46,9999,3713,0,0),(49,47,5018,-7114,0,0),(50,48,9999,9999,0,0),(51,49,4386,-3375,0,0),(52,51,8514,-9999,0,0),(53,52,-4439,-2088,0,0),(54,53,5167,-6772,0,0),(55,54,-1467,-6363,0,0),(56,55,4720,4162,0,0),(57,55,4594,4097,0,0),(58,56,-4729,-9999,0,0),(59,57,9999,-9999,0,0),(60,58,6202,-9999,0,0),(61,59,-9999,9999,0,0),(62,60,9999,-5465,0,0),(63,61,-4592,-9999,0,0),(64,62,9999,-9115,0,0),(65,63,7514,-9999,0,0),(66,64,8843,9999,0,0),(67,67,-2810,-483,0,0),(68,68,-1244,5296,0,0),(69,69,6252,2862,0,0),(70,71,-3940,-3633,0,0),(71,73,-9999,8059,0,0),(72,77,6403,2467,0,0),(73,81,6565,4578,0,0),(74,81,1020,1796,0,0),(75,81,653,3188,0,0),(76,82,389,2294,0,0),(77,87,-5651,-7545,0,0),(78,88,-9999,2326,0,0),(79,89,-9999,9999,0,0),(80,90,-8258,9999,0,0),(81,91,-7116,9999,0,0),(82,92,-9105,9999,0,0),(83,94,9999,9999,0,0),(84,95,-9999,7023,0,0),(85,96,-9999,-9999,0,0),(86,97,-5093,7463,0,0),(87,98,9999,9999,0,0),(88,99,9115,3364,0,0),(89,100,9999,-9999,0,0),(90,101,9999,6955,0,0),(91,102,9999,7173,0,0),(92,103,-7015,7305,0,0),(93,104,-6884,9999,0,0),(94,105,-2906,463,0,0),(95,106,-9999,-9999,0,0),(96,107,-9999,4276,0,0),(97,108,9999,-9999,0,0),(98,109,2991,-8915,0,0),(99,109,4493,-2230,0,0),(100,110,-5661,3704,0,0),(101,111,-4826,-9999,0,0),(102,112,-9999,6626,0,0),(103,113,9999,-374,0,0),(104,114,-9999,7327,0,0),(105,115,-9999,3695,0,0),(106,116,9999,9155,0,0),(107,117,8930,9568,0,0),(108,118,9999,5896,0,0),(109,119,-8999,-321,0,0),(110,120,-9999,9999,0,0),(111,121,3912,-8006,0,0),(112,122,5513,-6834,0,0),(113,123,9999,9999,0,0),(114,124,6395,8767,0,0),(115,128,9999,1543,0,0),(116,129,-9999,2564,0,0),(117,130,9999,9937,0,0),(118,131,-6833,9999,0,0),(119,132,-9773,9999,0,0),(120,133,-9821,9999,0,0),(121,134,8365,4939,0,0),(122,135,-9999,6187,0,0),(123,136,9999,-9999,0,0),(124,137,2929,9921,0,0),(125,138,-5069,9999,0,0),(126,139,-9999,2411,0,0),(127,140,5334,-9999,0,0),(128,141,5208,-9999,0,0),(129,142,1021,-5356,0,0),(130,144,8204,-9999,0,0),(131,145,9999,2135,0,0),(132,146,6602,4485,0,0),(133,146,-9999,-6233,0,0),(134,147,-9999,-9999,0,0),(135,148,9487,4758,0,0),(136,148,9497,4835,0,0),(137,148,9704,7988,0,0),(138,150,5143,-2022,0,0),(139,151,8483,-2991,0,0),(140,152,-9999,-9999,0,0),(141,153,-9999,9999,0,0),(142,154,-9999,9999,0,0),(143,155,7353,-9999,0,0),(144,156,-9999,9999,0,0),(145,157,3777,-9999,0,0),(146,158,-7111,-6614,0,0),(147,159,-9999,9999,0,0),(148,162,-9999,-9999,0,0),(149,162,-9999,9999,0,0),(150,163,-6279,1039,0,0),(151,164,-9999,-5674,0,0),(152,164,-9999,-8459,0,0),(153,164,-9999,-8577,0,0),(154,164,-9999,-5715,0,0),(155,165,213,9942,0,0),(156,166,9999,9999,0,0),(157,181,7970,8037,0,0),(158,188,3854,3874,0,0),(159,188,2204,1746,0,0),(160,189,-1873,352,0,0),(161,191,-2800,-4077,0,0),(162,193,8765,-1404,0,0),(163,194,-973,-1609,0,0),(164,195,9999,6565,0,0),(165,196,9999,-9999,0,0),(166,197,9999,9999,0,0),(167,198,-9999,9941,0,0),(168,199,-9999,9999,0,0),(169,200,9999,-1867,0,0),(170,201,-7371,2010,0,0),(171,202,-9980,-9999,0,0),(172,203,9619,9999,0,0),(173,204,9999,-4983,0,0),(174,205,9999,9999,0,0),(175,206,-3150,-7101,0,0),(176,209,9999,5218,0,0),(177,210,9999,-9999,0,0),(178,211,-9999,7857,0,0),(179,212,-2132,1054,0,0),(180,213,9999,9999,0,0),(181,214,8503,-8326,0,0),(182,215,-3432,-9999,0,0),(183,216,-9999,9999,0,0),(184,217,-9999,-9999,0,0),(185,218,-4511,8206,0,0),(186,219,-9999,2739,0,0),(187,220,-9999,-5944,0,0),(188,222,-3672,9999,0,0),(189,224,1967,9999,0,0),(190,225,-9999,-5335,0,0),(191,226,-9999,-1526,0,0),(192,227,3464,1917,0,0),(193,230,-1549,-122,0,0),(194,232,3326,9999,0,0),(195,233,9999,474,0,0),(196,234,-6795,5514,0,0),(197,235,9687,9999,0,0),(198,236,9999,9999,0,0),(199,237,9999,-9999,0,0),(200,238,9999,-8150,0,0),(201,239,9999,9999,0,0),(202,240,-6737,9999,0,0),(203,241,-9999,-6877,0,0),(204,242,-8818,8549,0,0),(205,243,-1407,-3342,0,0),(206,244,9552,-9999,0,0),(207,245,-9999,-1421,0,0),(208,246,-9999,5657,0,0),(209,247,9999,-9999,0,0),(210,248,-6558,-6010,0,0),(211,248,-5592,-4935,0,0),(212,248,-6831,-5280,0,0),(213,248,-6885,-5158,0,0),(214,248,-6884,-5253,0,0),(215,248,-6458,-5939,0,0),(216,249,-8738,-9999,0,0),(217,250,9999,9475,0,0),(218,251,9999,9999,0,0),(219,252,-3281,9442,0,0),(220,256,679,9999,0,0),(221,265,9999,9999,0,0),(222,266,9999,9112,0,0),(223,269,2845,-9999,0,0),(224,272,2941,6262,0,0),(225,273,2287,4288,0,0),(226,274,-9999,6276,0,0),(227,277,4077,3376,0,0),(228,278,9999,-1897,0,0),(229,279,9999,-9999,0,0),(230,280,-6036,-2519,0,0),(231,281,-5089,5036,0,0),(232,282,116,457,0,0),(233,283,-9999,9999,0,0),(234,284,8913,-9999,0,0),(235,286,9999,3350,0,0),(236,287,9999,8567,0,0),(237,288,-9999,9999,0,0),(238,289,-9999,-9999,0,0),(239,290,-2439,1732,0,0),(240,291,-7862,-9999,0,0),(241,292,-6610,7361,0,0),(242,293,-645,7137,0,0),(243,294,-5248,-2047,0,0),(244,295,3226,9999,0,0),(245,296,-3953,-5479,0,0),(246,297,9999,6955,0,0),(247,298,-8335,872,0,0),(248,301,-9999,-4616,0,0),(249,302,9999,9999,0,0),(250,303,9999,9999,0,0),(251,307,-9999,4264,0,0),(252,313,9999,9999,0,0),(253,318,5026,-2499,0,0),(254,319,3282,9999,0,0),(255,320,6321,181,0,0),(256,321,-3331,-5193,0,0),(257,322,9344,9999,0,0),(258,330,-4007,-4958,0,0),(259,330,1976,-3174,0,0),(260,348,-9999,-9999,0,0),(261,349,-9999,-9999,0,0),(262,350,9999,9999,0,0),(263,360,-584,7426,0,0),(264,361,-1536,8591,0,0),(265,362,8470,4024,0,0),(266,363,1638,9999,0,0),(267,364,-966,8473,0,0),(268,366,2298,2956,0,0),(269,369,-7797,7669,0,0),(270,371,9999,-9999,0,0),(271,373,9999,-8534,0,0),(272,375,-9999,9999,0,0),(273,376,-9999,9999,0,0),(274,377,-9999,-7473,0,0),(275,379,-9999,-9999,0,0),(276,380,-9999,-9999,0,0),(277,382,8631,9999,0,0),(278,384,9999,9999,0,0),(279,386,-9999,9999,0,0),(280,387,-1705,1838,0,0),(281,388,-8311,-4244,0,0),(282,389,-3518,9999,0,0),(283,390,-1953,-9999,0,0),(284,391,7287,9999,0,0),(285,392,-5027,-1642,0,0),(286,393,-9999,2187,0,0),(287,394,1904,-9999,0,0),(288,395,9999,-9999,0,0),(289,396,1765,1803,0,0),(290,397,9999,-9999,0,0),(291,398,-2037,2491,0,0),(292,399,-3186,9999,0,0),(293,402,-9999,9999,0,0),(294,403,-9999,9999,0,0),(295,404,-9999,757,0,0),(296,406,9999,-9999,0,0),(297,407,-3774,796,0,0),(298,413,1679,-9999,0,0),(299,414,681,-2136,0,0),(300,415,-124,-2530,0,0),(301,416,-2132,1054,0,0),(302,419,3149,7883,0,0),(303,424,1168,2773,0,0),(304,425,-9999,9999,0,0),(305,426,3316,-540,0,0),(306,427,-9999,-9999,0,0),(307,428,-238,9999,0,0),(308,429,-5271,9999,0,0),(309,430,9999,81,0,0),(310,431,-4829,6150,0,0),(311,432,9999,5772,0,0),(312,433,2680,-779,0,0),(313,434,3110,-9999,0,0),(314,435,2192,7529,0,0),(315,436,-30,4611,0,0),(316,437,9999,9999,0,0),(317,438,-9999,8605,0,0),(318,439,-5013,-6571,0,0),(319,440,-2859,-5681,0,0),(320,441,416,9518,0,0),(321,442,1163,-1325,0,0),(322,443,9999,-9769,0,0),(323,444,9999,-9999,0,0),(324,446,-2060,7080,0,0),(325,449,-8227,9476,0,0),(326,449,-8285,9999,0,0),(327,449,-7552,6859,0,0),(328,450,-1789,8272,0,0),(329,456,-7119,5457,0,0),(330,468,9917,4961,0,0),(331,469,9999,9999,0,0),(332,470,-9999,937,0,0),(333,473,9999,-4397,0,0),(334,474,7433,-9999,0,0),(335,476,-2332,-2653,0,0),(336,477,-9999,-8281,0,0),(337,478,-1253,-863,0,0),(338,479,9999,8369,0,0),(339,479,9999,9999,0,0),(340,479,9999,8397,0,0),(341,480,-3271,9999,0,0),(342,481,9999,9999,0,0),(343,482,9999,-9999,0,0),(344,483,9999,-3759,0,0),(345,484,6765,9999,0,0),(346,485,9999,9999,0,0),(347,486,-9999,9999,0,0),(348,487,-9999,9999,0,0),(349,488,-9999,9999,0,0),(350,489,663,-983,0,0),(351,491,1124,-340,0,0),(352,492,-289,2527,0,0),(353,493,-1949,9999,0,0),(354,494,-9999,4353,0,0),(355,495,-9999,-9999,0,0),(356,496,9999,-4461,0,0),(357,497,-1102,-1097,0,0),(358,499,2233,77,0,0),(359,501,-9999,-3996,0,0),(360,502,-2016,2494,0,0),(361,513,9999,9999,0,0),(362,514,9999,6479,0,0),(363,531,-210,-5380,0,0),(364,532,8561,2996,0,0),(365,537,-5209,4468,0,0),(366,538,2482,3295,0,0),(367,538,4612,3017,0,0),(368,539,-4314,4134,0,0),(369,540,3187,3145,0,0),(370,543,-5299,9999,0,0),(371,544,5125,-5082,0,0),(372,544,4419,-7349,0,0),(373,544,3444,-4550,0,0),(374,545,-2367,1583,0,0),(375,546,-9999,-9999,0,0),(376,548,9999,-6137,0,0),(377,549,902,3177,0,0),(378,553,-2252,831,0,0),(379,554,-2653,-2142,0,0),(380,558,-9451,-9999,0,0),(381,559,-9999,9999,0,0),(382,566,-9999,6138,0,0),(383,569,9999,9999,0,0),(384,572,9999,-9990,0,0),(385,607,9999,9999,0,0),(386,624,9999,9999,0,0),(387,625,9999,9999,0,0),(388,638,-8816,-9999,0,0),(389,639,-9999,9999,0,0),(390,640,9999,9999,0,0),(391,641,9999,-9999,0,0),(392,642,-1056,3339,0,0),(393,643,9999,9999,0,0),(394,644,9999,-6759,0,0),(395,644,9999,-8150,0,0),(396,644,9999,-6759,0,0),(397,644,9999,-6863,0,0),(398,644,9999,-6740,0,0),(399,644,9999,-8236,0,0),(400,644,9999,-6086,0,0),(401,644,9999,-6131,0,0),(402,644,9999,-6191,0,0),(403,645,67,-612,0,0),(404,645,41,220,0,0),(405,646,-5198,5595,0,0),(406,647,-9999,9999,0,0),(407,648,-9999,9999,0,0),(408,649,-9999,9999,0,0),(409,650,-9999,9999,0,0),(410,651,9999,9999,0,0),(411,652,-1858,2863,0,0),(412,673,-9999,9999,0,0),(413,675,8789,-9999,0,0),(414,682,-9999,591,0,0),(415,683,-9999,591,0,0),(416,684,-9999,591,0,0),(417,685,-9999,591,0,0),(418,691,2395,2838,0,0),(419,692,9999,-2092,0,0),(420,693,-6653,6105,0,0),(421,704,-9999,-9999,0,0),(422,726,9999,-9999,0,0);
/*!40000 ALTER TABLE `spawnpoints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storagetabs`
--

DROP TABLE IF EXISTS `storagetabs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storagetabs` (
  `AccountID` int(4) NOT NULL,
  `Number` int(4) NOT NULL,
  `InventoryID` int(4) NOT NULL,
  PRIMARY KEY (`AccountID`,`Number`),
  KEY `StorageTabsInventoryID` (`InventoryID`),
  CONSTRAINT `StorageTabsAccountID` FOREIGN KEY (`AccountID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StorageTabsInventoryID` FOREIGN KEY (`InventoryID`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storagetabs`
--

LOCK TABLES `storagetabs` WRITE;
/*!40000 ALTER TABLE `storagetabs` DISABLE KEYS */;
/*!40000 ALTER TABLE `storagetabs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeditems`
--

DROP TABLE IF EXISTS `storeditems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeditems` (
  `InventoryID` int(4) NOT NULL,
  `Slot` int(4) NOT NULL,
  `ItemID` int(4) NOT NULL,
  PRIMARY KEY (`InventoryID`,`Slot`),
  KEY `StoredItemsItemID` (`ItemID`),
  CONSTRAINT `StoredItemsInventoryID` FOREIGN KEY (`InventoryID`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StoredItemsItemID` FOREIGN KEY (`ItemID`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeditems`
--

LOCK TABLES `storeditems` WRITE;
/*!40000 ALTER TABLE `storeditems` DISABLE KEYS */;
/*!40000 ALTER TABLE `storeditems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weapons`
--

DROP TABLE IF EXISTS `weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weapons` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `Leadhand` int(4) NOT NULL,
  `Offhand` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `WeaponsLeadhand` (`Leadhand`),
  KEY `WeaponsOffhand` (`Offhand`),
  CONSTRAINT `WeaponsLeadhand` FOREIGN KEY (`Leadhand`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `WeaponsOffhand` FOREIGN KEY (`Offhand`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapons`
--

LOCK TABLES `weapons` WRITE;
/*!40000 ALTER TABLE `weapons` DISABLE KEYS */;
/*!40000 ALTER TABLE `weapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weaponsets`
--

DROP TABLE IF EXISTS `weaponsets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weaponsets` (
  `CharacterID` int(4) NOT NULL,
  `Number` tinyint(1) NOT NULL,
  `Weapons` int(4) NOT NULL,
  PRIMARY KEY (`Number`,`CharacterID`),
  KEY `WeaponsetsCharacterID` (`CharacterID`),
  KEY `WeaponsetsWeapons` (`Weapons`),
  CONSTRAINT `WeaponsetsCharacterID` FOREIGN KEY (`CharacterID`) REFERENCES `characters` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `WeaponsetsWeapons` FOREIGN KEY (`Weapons`) REFERENCES `weapons` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weaponsets`
--

LOCK TABLES `weaponsets` WRITE;
/*!40000 ALTER TABLE `weaponsets` DISABLE KEYS */;
/*!40000 ALTER TABLE `weaponsets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-23 22:45:58
