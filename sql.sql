/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 : Database - wms_liu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wms_liu` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `wms_liu`;

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `resultType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`username`,`operation`,`method`,`params`,`ip`,`createDate`,`resultType`) values (263,'管理员','登录记录','com.wms.controller.UserLoginController.login','\"管理员\"','127.0.0.1','2021-04-22 17:39:08','\"redirect:/account/redirect/main\"'),(264,'管理员','登录记录','com.wms.controller.UserLoginController.login','\"管理员\"','127.0.0.1','2021-04-22 17:48:06','\"redirect:/account/redirect/main\"'),(265,'管理员','登录记录','com.wms.controller.UserLoginController.login','\"管理员\"','127.0.0.1','2021-04-22 17:55:40','\"redirect:/account/redirect/main\"'),(266,'管理员','登录记录','com.wms.controller.UserLoginController.login','\"管理员\"','127.0.0.1','2021-04-22 18:10:22','\"redirect:/account/redirect/main\"'),(267,'管理员','删除仓库信息','com.wms.controller.WarehouseController.deleteById','1011','127.0.0.1','2021-04-22 18:11:42','\"redirect:/warehouseManage/redirect/wms_warehouseManagement\"'),(268,'管理员','删除仓库信息','com.wms.controller.WarehouseController.deleteById','1010','127.0.0.1','2021-04-22 18:11:45','\"redirect:/warehouseManage/redirect/wms_warehouseManagement\"'),(269,'管理员','删除仓库信息','com.wms.controller.WarehouseController.deleteById','1009','127.0.0.1','2021-04-22 18:11:47','\"redirect:/warehouseManage/redirect/wms_warehouseManagement\"'),(270,'管理员','删除客户信息','com.wms.controller.CustomerManageController.deleteById','1220','127.0.0.1','2021-04-22 18:12:02','\"redirect:/customerManage/redirect/wms_customerManagement\"'),(271,'管理员','删除客户信息','com.wms.controller.CustomerManageController.deleteById','1214','127.0.0.1','2021-04-22 18:12:08','\"redirect:/customerManage/redirect/wms_customerManagement\"');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(11) DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nickname` varchar(11) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `registerdate` date DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `perms` varchar(20) DEFAULT NULL,
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `w_admin_wId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`nickname`,`gender`,`telephone`,`registerdate`,`address`,`role`,`perms`,`salt`,`w_admin_wId`) values (1,'zhangsan','123123','张三','男','13576765678','2019-02-03','科技路','admin','manage',NULL,0),(21,'管理员','63fe00a781e703828ce665885fea7ee8','管理员小号','男','13437574820','2021-03-27','施工方','admin','manage','ZcJG13hyi+RtNVZQo8zcsA==',1003),(22,'用户','8eb70a08f770024222dcc7d8b6d9c7dc','用户1','男','13437574820','2021-03-27','刘付日','user','perms','tvgL6oQ436CcgPv8U4DeTA==',1004);

/*Table structure for table `wms_customer` */

DROP TABLE IF EXISTS `wms_customer`;

CREATE TABLE `wms_customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(30) NOT NULL,
  `customer_person` varchar(10) NOT NULL,
  `customer_tel` varchar(20) NOT NULL,
  `customer_email` varchar(20) NOT NULL,
  `customer_address` varchar(30) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1221 DEFAULT CHARSET=utf8;

/*Data for the table `wms_customer` */

insert  into `wms_customer`(`customer_id`,`customer_name`,`customer_person`,`customer_tel`,`customer_email`,`customer_address`) values (1215,'中山市松林达电子有限公司','刘明','13437574820','85264958@126.com','中国 广东 深圳市宝安区 深圳市宝安区福永社区桥头村桥塘路育'),(1216,'绿之源饮品有限公司 ','赵志敬','13437574820','87092165@qq.com','中国 河南 郑州市 郑州市嘉亿东方大厦609'),(1218,'高深科技','陈娟','17716786888','23369888@136.com','中国 湖南 醴陵市 嘉树乡玉茶村柏树组'),(1219,'纬创','刘明','13437574820','85264958@126.com','中国 广东 深圳市宝安区 深圳市宝安区福永社区桥头村桥塘路育');

/*Table structure for table `wms_goods` */

DROP TABLE IF EXISTS `wms_goods`;

CREATE TABLE `wms_goods` (
  `good_id` int(11) NOT NULL AUTO_INCREMENT,
  `good_name` varchar(30) NOT NULL,
  `good_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `good_size` varchar(20) DEFAULT NULL,
  `good_value` double NOT NULL,
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

/*Data for the table `wms_goods` */

insert  into `wms_goods`(`good_id`,`good_name`,`good_type`,`good_size`,`good_value`) values (103,'五孔插座西门子墙壁开关','电器','86*86',1.85),(104,'陶瓷马克杯','陶瓷','9*9*11',3.5),(105,'精酿苹果醋','饮料','312ml',300),(109,'真皮马格','皮料','25*25',120),(110,'红酒','饮料','800ml',120);

/*Table structure for table `wms_operation_record` */

DROP TABLE IF EXISTS `wms_operation_record`;

CREATE TABLE `wms_operation_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `operation_name` varchar(30) NOT NULL,
  `operation_time` datetime NOT NULL,
  `operation_result` varchar(15) NOT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wms_operation_record` */

/*Table structure for table `wms_record_in` */

DROP TABLE IF EXISTS `wms_record_in`;

CREATE TABLE `wms_record_in` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `record_supplierId` int(11) NOT NULL,
  `record_goodId` int(11) NOT NULL,
  `record_number` int(11) NOT NULL,
  `record_time` datetime NOT NULL,
  `record_person` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `record_wId` int(11) NOT NULL,
  PRIMARY KEY (`record_id`),
  KEY `record_supplierId` (`record_supplierId`),
  KEY `record_goodId` (`record_goodId`),
  KEY `record_wId` (`record_wId`),
  CONSTRAINT `wms_record_in_ibfk_1` FOREIGN KEY (`record_supplierId`) REFERENCES `wms_supplier` (`supplier_id`),
  CONSTRAINT `wms_record_in_ibfk_2` FOREIGN KEY (`record_goodId`) REFERENCES `wms_goods` (`good_id`),
  CONSTRAINT `wms_record_in_ibfk_3` FOREIGN KEY (`record_wId`) REFERENCES `wms_warehouse` (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

/*Data for the table `wms_record_in` */

insert  into `wms_record_in`(`record_id`,`record_supplierId`,`record_goodId`,`record_number`,`record_time`,`record_person`,`record_wId`) values (95,1013,109,300,'2021-04-22 09:57:27','管理员',1003),(96,1013,105,300,'2021-04-22 13:40:13','管理员',1004);

/*Table structure for table `wms_record_out` */

DROP TABLE IF EXISTS `wms_record_out`;

CREATE TABLE `wms_record_out` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `record_customerId` int(11) NOT NULL,
  `record_goodID` int(11) NOT NULL,
  `record_number` int(11) NOT NULL,
  `record_time` datetime NOT NULL,
  `record_person` varchar(10) NOT NULL,
  `record_wId` int(11) NOT NULL,
  PRIMARY KEY (`record_id`),
  KEY `record_customerId` (`record_customerId`),
  KEY `record_goodID` (`record_goodID`),
  KEY `record_wId` (`record_wId`),
  CONSTRAINT `wms_record_out_ibfk_1` FOREIGN KEY (`record_customerId`) REFERENCES `wms_customer` (`customer_id`),
  CONSTRAINT `wms_record_out_ibfk_2` FOREIGN KEY (`record_goodID`) REFERENCES `wms_goods` (`good_id`),
  CONSTRAINT `wms_record_out_ibfk_3` FOREIGN KEY (`record_wId`) REFERENCES `wms_warehouse` (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

/*Data for the table `wms_record_out` */

insert  into `wms_record_out`(`record_id`,`record_customerId`,`record_goodID`,`record_number`,`record_time`,`record_person`,`record_wId`) values (57,1215,109,150,'2021-04-22 09:57:51','管理员',1003);

/*Table structure for table `wms_record_storage` */

DROP TABLE IF EXISTS `wms_record_storage`;

CREATE TABLE `wms_record_storage` (
  `record_goodId` int(11) NOT NULL AUTO_INCREMENT,
  `record_wId` int(11) NOT NULL,
  `record_number` int(11) NOT NULL,
  PRIMARY KEY (`record_goodId`,`record_wId`),
  KEY `record_wId` (`record_wId`),
  CONSTRAINT `wms_record_storage_ibfk_1` FOREIGN KEY (`record_goodId`) REFERENCES `wms_goods` (`good_id`),
  CONSTRAINT `wms_record_storage_ibfk_2` FOREIGN KEY (`record_wId`) REFERENCES `wms_warehouse` (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

/*Data for the table `wms_record_storage` */

insert  into `wms_record_storage`(`record_goodId`,`record_wId`,`record_number`) values (105,1004,300),(109,1003,150),(109,1004,150);

/*Table structure for table `wms_supplier` */

DROP TABLE IF EXISTS `wms_supplier`;

CREATE TABLE `wms_supplier` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(30) NOT NULL,
  `supplier_person` varchar(10) NOT NULL,
  `supplier_tel` varchar(20) NOT NULL,
  `supplier_email` varchar(20) NOT NULL,
  `supplier_address` varchar(30) NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=utf8;

/*Data for the table `wms_supplier` */

insert  into `wms_supplier`(`supplier_id`,`supplier_name`,`supplier_person`,`supplier_tel`,`supplier_email`,`supplier_address`) values (1013,'浙江温州皮革厂','黄鹤','13777771126','86827868@126.com','中国 浙江 温州市龙湾区 龙湾区永强大道1648号'),(1014,'广东中山饮料公司','赵予熙','13974167256','23267999@126.com','中国 湖南 醴陵市 东正街15号'),(1015,'洛阳古镇旅游公司','大忽悠','13437574820','22390898@qq.com','中国 广东 佛山市顺德区 北滘镇怡和路2号怡和中心14楼'),(1030,'电器有限公司','王泽伟','13777771126','86827868@126.com','中国 浙江 温州市龙湾区 龙湾区永强大道1648号'),(1031,'实业有限公司','温仙容','13974167256','23267999@126.com','中国 湖南 醴陵市 东正街15号');

/*Table structure for table `wms_warehouse` */

DROP TABLE IF EXISTS `wms_warehouse`;

CREATE TABLE `wms_warehouse` (
  `warehouse_id` int(11) NOT NULL AUTO_INCREMENT,
  `warehouse_address` varchar(30) NOT NULL,
  `warehouse_status` varchar(20) NOT NULL,
  `warehouse_area` varchar(20) NOT NULL,
  `warehouse_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8;

/*Data for the table `wms_warehouse` */

insert  into `wms_warehouse`(`warehouse_id`,`warehouse_address`,`warehouse_status`,`warehouse_area`,`warehouse_desc`) values (1003,'浙江温州南彩工业园区彩祥西路9号','可用','11000㎡','提供服务完整'),(1004,'广州白云石井石潭路大基围工业区','可用','1000㎡','物流极为便利'),(1005,' 香港北区文锦渡路（红桥新村）','可用','5000.00㎡',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
