-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 15, 2018 at 07:27 PM
-- Server version: 5.7.21-log
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tutorial`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `bill_no` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `total_cost` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`bill_no`, `booking_id`, `employee_id`, `total_cost`) VALUES
(5, 8, 2, 6000);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `room_no` int(11) NOT NULL,
  `check_in` date DEFAULT NULL,
  `check_out` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_id`, `customer_id`, `employee_id`, `room_no`, `check_in`, `check_out`) VALUES
(8, 17, 2, 102, '2018-10-16', '2018-10-23'),
(9, 18, 2, 100, '2018-10-17', '2018-10-26');

--
-- Triggers `booking`
--
DELIMITER $$
CREATE TRIGGER `default_checkin_date` BEFORE INSERT ON `booking` FOR EACH ROW if ( isnull(new.check_in) ) then
 set new.check_in=curdate();
end if
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email_address` varchar(100) NOT NULL,
  `phone_number` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `first_name`, `last_name`, `email_address`, `phone_number`) VALUES
(17, 'hgvcx', 'khv', 'jgv', '2836'),
(18, 'jdgv', 'jdhv', 'khvd', 'kshv');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `department` enum('reception','house keeping','culinary','recreation') NOT NULL DEFAULT 'house keeping',
  `salary` int(11) NOT NULL DEFAULT '15000'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `username`, `password`, `name`, `department`, `salary`) VALUES
(1, 'vitz_6', 'abcd', 'svineet', 'house keeping', 15000),
(2, 'vivek_1998299', 'helloworld', 'cvivek', 'reception', 15000),
(3, 'jgc', 'hgc', 'bb ', 'house keeping', 15000),
(4, 'cvivek299', 'something', 'yeees', 'reception', 15000),
(5, 'hgc', 'jgc', 'jgc', 'house keeping', 15000),
(6, 'chauhan79', 'hihowareu', 'someone', 'recreation', 15000);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_no` int(11) NOT NULL,
  `room_size` enum('1BHK','2BHK','3BHK') NOT NULL DEFAULT '1BHK',
  `room_type` enum('deluxe','superior','standard') NOT NULL DEFAULT 'standard',
  `is_reserved` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_no`, `room_size`, `room_type`, `is_reserved`) VALUES
(100, '1BHK', 'standard', 1),
(101, '1BHK', 'standard', 0),
(102, '1BHK', 'superior', 1);

-- --------------------------------------------------------

--
-- Table structure for table `room_price`
--

CREATE TABLE `room_price` (
  `room_size` enum('1BHK','2BHK','3BHK') NOT NULL DEFAULT '1BHK',
  `room_type` enum('deluxe','superior','standard') NOT NULL DEFAULT 'standard',
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room_price`
--

INSERT INTO `room_price` (`room_size`, `room_type`, `price`) VALUES
('1BHK', 'deluxe', 8000),
('1BHK', 'superior', 6000),
('1BHK', 'standard', 5000),
('2BHK', 'deluxe', 10000),
('2BHK', 'superior', 8000),
('2BHK', 'standard', 6000),
('3BHK', 'deluxe', 13000),
('3BHK', 'superior', 11000),
('3BHK', 'standard', 9000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`bill_no`),
  ADD KEY `booking_id` (`booking_id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `room_no` (`room_no`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_no`),
  ADD KEY `room_size` (`room_size`,`room_type`);

--
-- Indexes for table `room_price`
--
ALTER TABLE `room_price`
  ADD PRIMARY KEY (`room_size`,`room_type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `bill_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_to_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
  ADD CONSTRAINT `bill_to_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`);

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_to_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `booking_to_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  ADD CONSTRAINT `booking_to_room` FOREIGN KEY (`room_no`) REFERENCES `room` (`room_no`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_to_price` FOREIGN KEY (`room_size`,`room_type`) REFERENCES `room_price` (`room_size`, `room_type`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
