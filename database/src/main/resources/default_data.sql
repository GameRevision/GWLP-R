-- MySQL dump 10.13  Distrib 5.5.29, for Linux (x86_64)
--
-- Host: localhost    Database: gwlpr
-- ------------------------------------------------------
-- Server version	5.5.29

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
INSERT INTO `accounts` VALUES
(1,'root@gwlp.ps','root','',NULL,0),
(2,'mail@sdk\'las.com','dkdkd','',NULL,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characters`
--

LOCK TABLES `characters` WRITE;
/*!40000 ALTER TABLE `characters` DISABLE KEYS */;
INSERT INTO `characters` VALUES
(1,12,'Test Char',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,2,7,4,12,0,30),
(1,13,'Abc Def',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,31,0,7,0,1,3),
(2,14,'Meow Meow',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,31,4,17,1,0,28);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps`
--

LOCK TABLES `maps` WRITE;
/*!40000 ALTER TABLE `maps` DISABLE KEYS */;
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
  `x` float(4,0) NOT NULL,
  `y` float(4,0) NOT NULL,
  `plane` int(4) NOT NULL,
  `radius` int(4) NOT NULL,
  PRIMARY KEY (`ID`,`MapID`),
  KEY `SpawnPointsMapID` (`MapID`),
  CONSTRAINT `SpawnPointsMapID` FOREIGN KEY (`MapID`) REFERENCES `maps` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spawnpoints`
--

LOCK TABLES `spawnpoints` WRITE;
/*!40000 ALTER TABLE `spawnpoints` DISABLE KEYS */;
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

-- Dump completed on 2013-02-08 14:16:05
