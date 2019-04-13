-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2019 at 05:46 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jiss`
--

-- --------------------------------------------------------

--
-- Table structure for table `adjs`
--

CREATE TABLE `adjs` (
  `CIN` int(8) DEFAULT NULL,
  `scheduled_date` char(10) DEFAULT NULL,
  `slot` char(1) DEFAULT NULL,
  `reason` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adjs`
--

INSERT INTO `adjs` (`CIN`, `scheduled_date`, `slot`, `reason`) VALUES
(2, '07/04/2019', 'A', 'Evidence Missing');

-- --------------------------------------------------------

--
-- Table structure for table `cases`
--

CREATE TABLE `cases` (
  `CIN` int(8) DEFAULT NULL,
  `defName` varchar(20) DEFAULT NULL,
  `defAddr` varchar(40) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `location` varchar(20) DEFAULT NULL,
  `ao` varchar(20) DEFAULT NULL,
  `dateCrime` char(10) DEFAULT NULL,
  `dateArrest` char(10) DEFAULT NULL,
  `dateHearing` char(10) DEFAULT NULL,
  `dateStart` char(10) DEFAULT NULL,
  `dateComp` char(10) DEFAULT NULL,
  `pj` varchar(20) DEFAULT NULL,
  `pp` varchar(20) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `jsum` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cases`
--

INSERT INTO `cases` (`CIN`, `defName`, `defAddr`, `type`, `location`, `ao`, `dateCrime`, `dateArrest`, `dateHearing`, `dateStart`, `dateComp`, `pj`, `pp`, `status`, `jsum`) VALUES
(1, 'Debasis', 'DBA', 'Murder', 'NITR', 'Arindum', '04/04/2019', '05/04/2019', '06/04/2019', '06/04/2019', '15/04/2019', 'Soumya', 'Dibyanshu', 1, 'Sentenced to 10 yrs jail term'),
(2, 'Soumya', 'MSS', 'Robbery', 'NITR', 'Debasis', '05/04/2019', '05/04/2019', '15/04/2019', '07/04/2019', '20/05/2019', 'XYZ', 'ABC', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hearings`
--

CREATE TABLE `hearings` (
  `CIN` int(8) DEFAULT NULL,
  `scheduled_date` char(10) DEFAULT NULL,
  `slot` char(1) DEFAULT NULL,
  `summary` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hearings`
--

INSERT INTO `hearings` (`CIN`, `scheduled_date`, `slot`, `summary`) VALUES
(1, '06/04/2019', 'B', '-'),
(2, '15/04/2019', 'A', '-');

-- --------------------------------------------------------

--
-- Table structure for table `lawyers`
--

CREATE TABLE `lawyers` (
  `sl_no` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `no_of_views` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lawyers`
--

INSERT INTO `lawyers` (`sl_no`, `username`, `no_of_views`) VALUES
(3, 'L2', 7),
(4, 'L1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `sl_no` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `type` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`sl_no`, `username`, `password`, `type`) VALUES
(6, 'J1', 'J1', 'J'),
(7, 'L2', 'L2', 'L'),
(9, 'R', 'R', 'R'),
(10, 'L1', 'L1', 'L');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lawyers`
--
ALTER TABLE `lawyers`
  ADD PRIMARY KEY (`sl_no`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`sl_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lawyers`
--
ALTER TABLE `lawyers`
  MODIFY `sl_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `sl_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
