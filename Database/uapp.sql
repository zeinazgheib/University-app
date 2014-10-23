-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 23, 2014 at 07:35 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `uapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE IF NOT EXISTS `branch` (
  `branchid` int(11) NOT NULL AUTO_INCREMENT,
  `branchname` varchar(50) NOT NULL,
  PRIMARY KEY (`branchid`),
  UNIQUE KEY `branchid` (`branchid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branchid`, `branchname`) VALUES
(1, 'Beirut'),
(2, 'Tripoli'),
(3, 'Bikfaya'),
(4, 'Nahr Ibrahim');

-- --------------------------------------------------------

--
-- Table structure for table `departement`
--

CREATE TABLE IF NOT EXISTS `departement` (
  `departmentid` int(11) NOT NULL AUTO_INCREMENT,
  `departmentname` varchar(50) NOT NULL,
  PRIMARY KEY (`departmentid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `departement`
--

INSERT INTO `departement` (`departmentid`, `departmentname`) VALUES
(1, 'Informatique'),
(2, 'Electrotechnique'),
(3, 'Gestion'),
(4, 'Mecanique');

-- --------------------------------------------------------

--
-- Table structure for table `feeds`
--

CREATE TABLE IF NOT EXISTS `feeds` (
  `feedid` int(11) NOT NULL AUTO_INCREMENT,
  `feedtitle` varchar(100) NOT NULL,
  `feedtext` text NOT NULL,
  `branchid` int(11) NOT NULL,
  `departmentid` int(11) NOT NULL,
  `datetime` double NOT NULL,
  PRIMARY KEY (`feedid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `feeds`
--

INSERT INTO `feeds` (`feedid`, `feedtitle`, `feedtext`, `branchid`, `departmentid`, `datetime`) VALUES
(1, 'dfasdfas', 'dfaasdfasdfasdf', 1, 1, 0),
(2, 'sdfsdf', 'sdfsdfsdf', 1, 1, 0),
(3, 'sdfsdf', 'sdfsdfsdf', 1, 1, 0),
(4, 'annnonce informatique', ';asodkfoksn kdkkdkdkdkdkkdkdkkd kdkkdkdkm kdkkdk', 1, 1, 0),
(5, 'sdfsdf', 'sdfsdfsdfsdf', 1, 1, 1414066553303),
(6, 'sddddppoooooo', 'llolloolololololo', 1, 1, 1414066704806),
(7, 'lsdklvkslcklvksldk', 'alspdlpaslpdlpalspldplapslpdl', 1, 3, 1414084691039);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `occupation` varchar(50) NOT NULL,
  `isadmin` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phonenumber` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`name`, `username`, `password`, `email`, `occupation`, `isadmin`, `id`, `phonenumber`) VALUES
('Pamela', 'pamela', 'pamela', 'pamelaabidoumet@gmail.com', 'developer', 1, 1, 70212019),
('Zeina', 'zeina', 'zeina', 'zeinazgheib@gmail.com', 'developer', 0, 2, 70984530),
('blablio', 'bla', 'bla', 'bla@hotmail.com', 'nsjdnjndjfn', 0, 3, 88663636),
('nadim', 'nadim', 'nadim', 'nadim@hotmail.com', '', 0, 4, 1234567890),
('wadih', 'wadih', 'wadih', 'wadih@ hotmail.com', '', 0, 5, 1234556),
('wadih', 'wadih', 'wadih', 'wadih@ hotmail.com', '', 0, 6, 1234556),
('fadi', 'fadi', 'fadi', 'fadi@hotmail.com', '', 0, 7, 888888),
('blanche', 'blanche', 'blanche', 'blanche@gmail.com', '', 0, 8, 1234567);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
