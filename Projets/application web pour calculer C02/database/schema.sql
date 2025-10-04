-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 16 Avril 2025 à 19:40
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `or`
--

DROP DATABASE IF EXISTS OR_DB;
CREATE DATABASE OR_DB;
USE OR_DB; 


-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `mdp` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `date` date NOT NULL,
  `role` enum('admin','user') NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`nom`, `prenom`, `mdp`, `email`, `date`, `role`) VALUES
('louaifi', 'azwaw', 'azwaw', 'azwaw0gmail.com', '2025-04-01', 'admin'),
('alex', 'marie', '0', 'alex@example.com', '2025-04-01', 'admin'),
('Doe', 'John', '0', 'john.doe@example.com', '2025-04-16', 'user');

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `email` varchar(40) NOT NULL,
  `date` datetime NOT NULL,
  `duree` float NOT NULL,
  `co2` float NOT NULL,
  `distance` float NOT NULL,
  `villeD` varchar(40) NOT NULL,
  `villeA` varchar(40) NOT NULL,
  PRIMARY KEY (`email`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `hmt`
--

CREATE TABLE IF NOT EXISTS `hmt` (
  `email` varchar(40) NOT NULL,
  `date` datetime NOT NULL,
  `idT` int(11) NOT NULL,
  PRIMARY KEY (`email`,`date`,`idT`),
  KEY `fk_hmt_transport_idT` (`idT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `transport`
--

CREATE TABLE IF NOT EXISTS `transport` (
  `idT` int(11) NOT NULL AUTO_INCREMENT,
  `nomT` varchar(40) NOT NULL,
  `constante` double NOT NULL,
  PRIMARY KEY (`idT`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `transport`
--

-- INSERT INTO `transport` (`idT`, `nomT`, `constante`) VALUES
-- (3, 'bus', 40);
INSERT INTO `transport` (`idT`, `nomT`, `constante`) VALUES
 (0, 'foot-walking', 1);
 INSERT INTO `transport` (`idT`, `nomT`, `constante`) VALUES
 (1, 'cycling-regular', 6);
 INSERT INTO `transport` (`idT`, `nomT`, `constante`) VALUES
 (2, 'driving-car', 35);
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `historique`
--
ALTER TABLE `historique`
  ADD CONSTRAINT `fk_hmt_compte_email` FOREIGN KEY (`email`) REFERENCES `compte` (`email`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_historique_compte_email` FOREIGN KEY (`email`) REFERENCES `compte` (`email`) ON DELETE CASCADE;

--
-- Contraintes pour la table `hmt`
--
ALTER TABLE `hmt`
  ADD CONSTRAINT `fk_htm_compte_email` FOREIGN KEY (`email`) REFERENCES `compte` (`email`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_hmt_transport_idT` FOREIGN KEY (`idT`) REFERENCES `transport` (`idT`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
