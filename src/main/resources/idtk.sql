/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : idtk

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 20/11/2019 17:41:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_statistic
-- ----------------------------
DROP TABLE IF EXISTS `data_statistic`;
CREATE TABLE `data_statistic`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `version` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '软件版本号',
  `device_sn` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备串号',
  `ir_voltage` int(7) NULL DEFAULT NULL COMMENT '发射器剩余电量百分比',
  `counter_voltage` int(7) NULL DEFAULT NULL COMMENT '计数器剩余电量百分比',
  `data_time` datetime(0) NULL DEFAULT NULL COMMENT '数据时间年月日时分秒',
  `focus` int(1) NULL DEFAULT NULL COMMENT '对焦状态（0正常 1失焦）',
  `entry` int(11) NULL DEFAULT NULL COMMENT '进门数量',
  `exit` int(11) NULL DEFAULT NULL COMMENT '出门数量',
  `deleted` int(1) NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '数字类型数据均为10进制' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info`  (
  `sn` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备SN 串号',
  `command_type` int(2) NULL DEFAULT NULL COMMENT '请求数据的命令类型\r\n0x00  不包含校验时间与营业时间\r\n0x01  包含校验系统时间\r\n0x02  包含校验营业时间\r\n0x03  包含校验系统时间与营业时间\r\n不包含只是不做处理字段仍然存在。\r\n',
  `speed` int(1) NULL DEFAULT NULL COMMENT '设备探测速度 0x00 低速，0x01 是高速',
  `recording_cycle` int(8) NULL DEFAULT NULL COMMENT '记录周期的分钟数 .范围0x00 --0xff\r\n对应时间范围0-255分钟\r\n0 表示实时记录\r\n',
  `upload_cycle` int(8) NULL DEFAULT NULL COMMENT '上传周期.范围0x00 --0xff\r\n对应时间范围0-255分钟\r\n0表示实时上传。\r\n无论如何设置，营业时间开始,如果有未上传数据默认会上传\r\n',
  `fixed_upload_time` int(3) NULL DEFAULT NULL COMMENT '指定上传时间\r\n0  未使用\r\n1-4  使用定点时间数。从1-4 依次使用',
  `upload_time1` time(0) NULL DEFAULT NULL COMMENT '上传指定时间1时分',
  `upload_time2` time(0) NULL DEFAULT NULL COMMENT '上传指定时间2时分',
  `upload_time3` time(0) NULL DEFAULT NULL COMMENT '上传指定时间3时分',
  `upload_time4` time(0) NULL DEFAULT NULL COMMENT '上传指定时间4时分',
  `mode` int(1) NULL DEFAULT NULL COMMENT '运行模式\r\n0x00  联网模式0x00单机模式\r\n0x01  单机版不上传到服务器',
  `display_type` int(2) NULL DEFAULT NULL COMMENT '显示类型\r\n0x00   计数不在幕上显示\r\n0x01   显示总数\r\n0x02   显示双向\r\n',
  `mac1` varchar(14) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '定位mac地址1最后字节信号强度(有符号数)',
  `mac2` varchar(14) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '定位mac地址2最后字节信号强度(有符号数)',
  `mac3` varchar(14) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '定位mac地址3最后字节信号强度(有符号数)',
  `open_time` time(6) NULL DEFAULT NULL COMMENT '营业时间开店时分',
  `close_time` time(6) NULL DEFAULT NULL COMMENT '营业时间闭店时分',
  `latest_receive_time` datetime(0) NULL DEFAULT NULL COMMENT '最新接受数据时间',
  `latest_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最新修改数据时间',
  `focus` int(1) NULL DEFAULT NULL COMMENT '对焦状态（0正常，1失焦）',
  `deleted` int(1) UNSIGNED NULL DEFAULT 0 COMMENT '删除状态（0未删除，1删除）',
  `ir_voltage` int(7) NULL DEFAULT NULL COMMENT '发射器剩余电量百分比',
  `counter_voltage` int(7) NULL DEFAULT NULL COMMENT '计数器剩余电量百分比',
  PRIMARY KEY (`sn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '数字类型数据均为10进制' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
