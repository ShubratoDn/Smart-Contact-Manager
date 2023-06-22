-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2023 at 08:11 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `contact_manager`
--

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(255) NOT NULL,
  `work_place` varchar(300) NOT NULL,
  `image` varchar(600) NOT NULL,
  `description` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `contact`
--

INSERT INTO `contact` (`id`, `user_id`, `name`, `phone`, `email`, `work_place`, `image`, `description`) VALUES
(16, 17, 'Sub', '01733704494', 'shubratodn44985@gmail.com', 'sirajganj', '17_7465139b61e726c786dfIMG_20230101_163714.jpg', '<p><strong>Shubrato </strong>my vondu</p>'),
(17, 16, 'hridoy debnath', '01914667199', 'hridoy@gmail.com', 'dhaka', 'contact.png', ''),
(19, 19, 'Virat Kohli', '+915544713981', 'abc@gmail.com', 'Mumbai', '19_22652da52d8b51f6e83ctest.jpg', '<h1>Indian Cricket Team</h1>'),
(20, 15, 'Shubrato Debnath', '01759458961', 'shubrato@gmail.com', 'sirajganj', '15_62acd3a5f58ffe2c7b9fIMG_20230101_162617.jpg', '<h1>This is shubrato Debnath</h1>'),
(21, 15, 'rafi', '01759458961', 'shubrato@gmail.com', 'sirajganj', '15_706a100b9854e873a78aIMG_20230101_162850.jpg', '<h1>This is shubrato Debnath</h1>'),
(22, 15, 'Modhu Sodhan', '01759458961', 'shubrato@gmail.com', 'dhaka', '15_f4a4b0c661193b62f55cIMG_20230101_165219.jpg', '<h1>Modhu Chodon</h1>'),
(25, 15, 'hridoy debnath', '016969696969', 'hridoy@gmail.com', 'sirajganj', '15_8f50b3da5c4dd884ef5d20170512_175911.jpg', 'I\'m a user of Smart Contact Manager');

-- --------------------------------------------------------

--
-- Table structure for table `password_recover`
--

CREATE TABLE `password_recover` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `otp` int(11) NOT NULL,
  `token` varchar(200) NOT NULL,
  `expired` int(1) NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `password_recover`
--

INSERT INTO `password_recover` (`id`, `user_id`, `otp`, `token`, `expired`, `created`) VALUES
(16, 15, 7718, 'fviwfnsnwcwxvakdjwncevbupabbppyanduhhrhyubgmsvunil', 0, '2023-02-04 18:16:21'),
(17, 17, 2415, 'udzfhfrxsuxxxpgesbpenvbcjfppcpxvomddwaqgvodnqfdxio', 0, '2023-02-04 18:18:33'),
(18, 19, 6761, 'vqgmrycxekcuxwkphlryamhgukzcoyzpxjbykxibkjxeienfma', 0, '2023-02-04 18:22:25');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
  `status` varchar(10) NOT NULL DEFAULT 'deactivate',
  `name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(600) NOT NULL,
  `image` varchar(200) NOT NULL DEFAULT 'user.jpg',
  `about` varchar(1000) DEFAULT 'I''m a user of Smart Contact Manager ***',
  `register_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `role`, `status`, `name`, `email`, `password`, `image`, `about`, `register_date`) VALUES
(14, 'ROLE_ADMIN', 'active', 'Shubrato Debnath', 'sub@gmail.com', '$2a$10$yXC02Sjh1HCCgV.0AhuEWOAU7Xwo/2/5QCoIrTfN2pfPykC8jiBZS', 'user.jpg', 'This is Shurbato Debnath', '2023-01-25 13:51:21'),
(15, 'ROLE_USER', 'active', 'Shubrato Debnath', 'shubratodn44985@gmail.com', '$2a$10$MO0C.4kQI5xXtHNN.YQBqurXb4OL96FIAY4yuSw7lCVpdj/AlTKPm', 'user.jpg', 'I\'m Shubrato Debnath', '2023-01-26 07:11:04'),
(16, 'ROLE_USER', 'active', 'Madhu Sodhan Datt', 'modhuofficial@gmail.com', '$2a$10$h7pzYLxTpkGo6ctbYHjHbOakKGX5bTJXRI2uoAnZgilmYD2gQwmCG', 'user.jpg', 'I\'m a bokachoda', '2023-01-28 16:22:24'),
(17, 'ROLE_USER', 'active', 'My Computer', 'mycomputer44985@gmail.com', '$2a$10$XSfVqWK2wdDtLnWP4a6uxe6triFsZPQib6dRjiHt2wRNY.WNica3G', 'user.jpg', 'This is My Computer', '2023-01-30 11:35:53'),
(18, 'ROLE_USER', 'active', 'MODHUSODAN DEVNATH', 'modhuofficial2@gmail.com', '$2a$10$tYYEICLsOx/YtPj8uqFgJ.S8WU8r2RSdbryHUbKZIzAKgTUg.LoEq', 'user.jpg', 'i\'m modhu', '2023-01-30 18:53:07'),
(19, 'ROLE_USER', 'active', 'Anisul islam', 'anisul@gmail.com', '$2a$10$O.u8dnpLTiHvtp//0H2Obu/I0q7acFjw2b9bBGWN73lZ86bHT6RDG', 'user.jpg', 'I\'m the user of Smart Contact Manager', '2023-01-31 17:10:41');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `password_recover`
--
ALTER TABLE `password_recover`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `password_recover`
--
ALTER TABLE `password_recover`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `contact`
--
ALTER TABLE `contact`
  ADD CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `password_recover`
--
ALTER TABLE `password_recover`
  ADD CONSTRAINT `password_recover_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
