SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for filedownload
-- ----------------------------
DROP TABLE IF EXISTS `filedownload`;
CREATE TABLE `filedownload` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '被下载的文件的名称',
  `downloadip` varchar(255) NOT NULL COMMENT '下载者的IP地址',
  `downloadtime` varchar(255) NOT NULL COMMENT '下载时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `fileinfo`;
CREATE TABLE `fileinfo` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '上传的文件ID',
  `filename` varchar(255) NOT NULL COMMENT '上传的文件名',
  `uploadtime` varchar(255) NOT NULL COMMENT '上传文件的时间',
  `filetype` varchar(255) NOT NULL COMMENT '文件的类型',
  `uploadip` varchar(255) NOT NULL COMMENT '上传者IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;