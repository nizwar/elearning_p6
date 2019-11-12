CREATE DATABASE `biodata_elearning6`;
USE `biodata_elearning6`;
CREATE TABLE `isi_biodata` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nim` char(8) NOT NULL,
  `nama` varchar(128) NOT NULL,
  `alamat` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1; 