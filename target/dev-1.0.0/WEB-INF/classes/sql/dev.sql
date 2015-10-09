/*
Navicat MySQL Data Transfer

Source Server         : 10.252.25.31
Source Server Version : 50529
Source Host           : 10.252.25.31:3306
Source Database       : dev_postgraduate

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2014-05-11 21:55:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `edu_major`
-- ----------------------------
DROP TABLE IF EXISTS `edu_major`;
CREATE TABLE `edu_major` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `major_code` varchar(10) NOT NULL,
  `school_id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edu_major
-- ----------------------------
INSERT INTO `edu_major` VALUES ('1', 'JSJ', '1', '计算机', '2', null, null, null, null, '0');
INSERT INTO `edu_major` VALUES ('2', 'ENGLISH', '1', '英语', '1', '2014-05-11 20:05:59', '1', '2014-05-11 20:05:59', '英语', '0');

-- ----------------------------
-- Table structure for `edu_school`
-- ----------------------------
DROP TABLE IF EXISTS `edu_school`;
CREATE TABLE `edu_school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `school_code` varchar(10) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edu_school
-- ----------------------------
INSERT INTO `edu_school` VALUES ('1', 'QINGHUA', '清华大学', '北京', null, null, null, null, null, '0');
INSERT INTO `edu_school` VALUES ('2', 'BJ001', '北京大学', '北京路19号', '1', '2014-05-11 18:48:11', '1', '2014-05-11 18:49:04', '12', '0');

-- ----------------------------
-- Table structure for `edu_student`
-- ----------------------------
DROP TABLE IF EXISTS `edu_student`;
CREATE TABLE `edu_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(40) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `no` varchar(40) DEFAULT NULL COMMENT '学号',
  `classes` varchar(40) DEFAULT NULL COMMENT '班级',
  `address` varchar(200) DEFAULT NULL COMMENT '家庭住址',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edu_student
-- ----------------------------
INSERT INTO `edu_student` VALUES ('1', 'test', '男', '20', 'admi1', '三年级一班、二班', '上海', null, null, '1', '2014-05-11 21:47:30', null, '1');
INSERT INTO `edu_student` VALUES ('2', '韩梅梅', '女', '20', 'S20140001', '三年级一班', '北京路19号', '1', '2014-05-11 21:49:03', '1', '2014-05-11 21:49:03', null, '0');

-- ----------------------------
-- Table structure for `edu_stu_major_grade`
-- ----------------------------
DROP TABLE IF EXISTS `edu_stu_major_grade`;
CREATE TABLE `edu_stu_major_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_id` bigint(20) NOT NULL,
  `major_id` bigint(20) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edu_stu_major_grade
-- ----------------------------
INSERT INTO `edu_stu_major_grade` VALUES ('5', '2', '1', '100', '3', '2014-05-11 21:52:04', '2', '2014-05-11 21:54:10', null, '0');
INSERT INTO `edu_stu_major_grade` VALUES ('6', '2', '2', null, '3', '2014-05-11 21:54:46', '3', '2014-05-11 21:54:46', null, '0');

-- ----------------------------
-- Table structure for `edu_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `edu_teacher`;
CREATE TABLE `edu_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `no` varchar(20) DEFAULT NULL COMMENT '工号',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `title` varchar(100) DEFAULT NULL COMMENT '职称',
  `course` varchar(100) DEFAULT NULL COMMENT '课程',
  `classes` varchar(100) DEFAULT NULL COMMENT '班级',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edu_teacher
-- ----------------------------
INSERT INTO `edu_teacher` VALUES ('1', '莫言', 'T20140001', '男', '教授', '英语', '三年级一班', '1212121212', '15923813444', '1', '2014-05-11 21:48:09', '1', '2014-05-11 21:48:09', null, '0');

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '区域名称',
  `type` char(1) DEFAULT NULL COMMENT '区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_area_parent_ids` (`parent_ids`) USING BTREE,
  KEY `sys_area_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', '0,', '100000', '中国', '1', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('2', '1', '0,1,', '110000', '北京市', '2', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('3', '2', '0,1,2,', '110101', '东城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('4', '2', '0,1,2,', '110102', '西城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('5', '2', '0,1,2,', '110103', '朝阳区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('6', '2', '0,1,2,', '110104', '丰台区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('7', '2', '0,1,2,', '110105', '海淀区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('8', '1', '0,1,', '370000', '山东省', '2', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_area` VALUES ('9', '8', '0,1,2,', '370531', '济南市', '3', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('10', '8', '0,1,2,', '370532', '历城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('11', '8', '0,1,2,', '370533', '历城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('12', '8', '0,1,2,', '370534', '历下区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('13', '8', '0,1,2,', '370535', '天桥区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('14', '8', '0,1,2,', '370536', '槐荫区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '正常', '0', 'del_flag', '删除标记', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '删除', '1', 'del_flag', '删除标记', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '显示', '1', 'show_hide', '显示/隐藏', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('4', '隐藏', '0', 'show_hide', '显示/隐藏', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('5', '是', '1', 'yes_no', '是/否', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('6', '否', '0', 'yes_no', '是/否', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('7', '红色', 'red', 'color', '颜色值', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('8', '绿色', 'green', 'color', '颜色值', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('9', '蓝色', 'blue', 'color', '颜色值', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('10', '黄色', 'yellow', 'color', '颜色值', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('11', '橙色', 'orange', 'color', '颜色值', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('12', '默认主题', 'default', 'theme', '主题方案', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('13', '天蓝主题', 'cerulean', 'theme', '主题方案', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('14', '橙色主题', 'readable', 'theme', '主题方案', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('15', '红色主题', 'united', 'theme', '主题方案', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('16', 'Flat主题', 'flat', 'theme', '主题方案', '60', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('17', '国家', '1', 'sys_area_type', '区域类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('18', '省份、直辖市', '2', 'sys_area_type', '区域类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('19', '地市', '3', 'sys_area_type', '区域类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('20', '区县', '4', 'sys_area_type', '区域类型', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('21', '公司', '1', 'sys_office_type', '机构类型', '60', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('22', '部门', '2', 'sys_office_type', '机构类型', '70', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('23', '一级', '1', 'sys_office_grade', '机构等级', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('24', '二级', '2', 'sys_office_grade', '机构等级', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('25', '三级', '3', 'sys_office_grade', '机构等级', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('26', '四级', '4', 'sys_office_grade', '机构等级', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('27', '所有数据', '1', 'sys_data_scope', '数据范围', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('28', '所在公司及以下数据', '2', 'sys_data_scope', '数据范围', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('29', '所在公司数据', '3', 'sys_data_scope', '数据范围', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('30', '所在部门及以下数据', '4', 'sys_data_scope', '数据范围', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('31', '所在部门数据', '5', 'sys_data_scope', '数据范围', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('32', '仅本人数据', '8', 'sys_data_scope', '数据范围', '90', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('33', '按明细设置', '9', 'sys_data_scope', '数据范围', '100', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('34', '系统管理', '1', 'sys_user_type', '用户类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('35', '教师', '2', 'sys_user_type', '用户类型', '20', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:36:58', null, '0');
INSERT INTO `sys_dict` VALUES ('36', '家长', '3', 'sys_user_type', '用户类型', '30', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:37:19', null, '0');
INSERT INTO `sys_dict` VALUES ('37', '基础主题', 'basic', 'cms_theme', '站点主题', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('38', '蓝色主题', 'blue', 'cms_theme', '站点主题', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('39', '红色主题', 'red', 'cms_theme', '站点主题', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('40', '文章模型', 'article', 'cms_module', '栏目模型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('41', '图片模型', 'picture', 'cms_module', '栏目模型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('42', '下载模型', 'download', 'cms_module', '栏目模型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('43', '链接模型', 'link', 'cms_module', '栏目模型', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('44', '专题模型', 'special', 'cms_module', '栏目模型', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('45', '默认展现方式', '0', 'cms_show_modes', '展现方式', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('46', '首栏目内容列表', '1', 'cms_show_modes', '展现方式', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('47', '栏目第一条内容', '2', 'cms_show_modes', '展现方式', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('48', '发布', '0', 'cms_del_flag', '内容状态', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('49', '删除', '1', 'cms_del_flag', '内容状态', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('50', '审核', '2', 'cms_del_flag', '内容状态', '15', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('51', '首页焦点图', '1', 'cms_posid', '推荐位', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('52', '栏目页文章推荐', '2', 'cms_posid', '推荐位', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('53', '咨询', '1', 'cms_guestbook', '留言板分类', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('54', '建议', '2', 'cms_guestbook', '留言板分类', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('55', '投诉', '3', 'cms_guestbook', '留言板分类', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('56', '其它', '4', 'cms_guestbook', '留言板分类', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('57', '公休', '1', 'oa_leave_type', '请假类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('58', '病假', '2', 'oa_leave_type', '请假类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('59', '事假', '3', 'oa_leave_type', '请假类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('60', '调休', '4', 'oa_leave_type', '请假类型', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('61', '婚假', '5', 'oa_leave_type', '请假类型', '60', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('62', '接入日志', '1', 'sys_log_type', '日志类型', '30', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('63', '异常日志', '2', 'sys_log_type', '日志类型', '40', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('64', '正常', '0', 'month_card_status', '正常', '10', '1', '2014-05-02 10:30:05', '1', '2014-05-02 10:57:14', null, '0');
INSERT INTO `sys_dict` VALUES ('65', '注销', '1', 'month_card_status', '注销', '20', '1', '2014-05-02 10:30:50', '1', '2014-05-02 10:57:22', null, '0');
INSERT INTO `sys_dict` VALUES ('66', '挂失', '2', 'month_card_status', '挂失', '30', '1', '2014-05-02 10:31:04', '1', '2014-05-02 10:57:29', null, '0');
INSERT INTO `sys_dict` VALUES ('67', '学生', '4', 'sys_user_type', '用户类型', '40', '1', '2014-05-05 20:37:47', '1', '2014-05-05 20:37:59', null, '0');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型（1：接入日志；2：异常日志）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=532 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('464', '1', '1', '2014-05-11 16:42:43', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=106&icon=&sort=5&parent.id=71&name=成绩查看&target=&permission=&href=/edu/eduScore/myView&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('465', '1', '1', '2014-05-11 16:44:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=27&icon=&sort=100&parent.id=1&name=系统首页&target=&permission=&href=&parent.name=顶级菜单&isShow=1', '');
INSERT INTO `sys_log` VALUES ('466', '1', '1', '2014-05-11 17:25:36', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/delete', 'GET', 'id=111', '');
INSERT INTO `sys_log` VALUES ('467', '1', '1', '2014-05-11 17:25:53', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=72&icon=&sort=10&parent.id=71&name=专业选择&target=&permission=&href=/edu/eduMajor/select&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('468', '1', '1', '2014-05-11 17:26:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=95&icon=&sort=30&parent.id=79&name=专业管理&target=&permission=&href=/edu/eduMajor&parent.name=教师模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('469', '1', '1', '2014-05-11 17:26:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=109&icon=&sort=30&parent.id=95&name=查看&target=&permission=edu:eduMajor:view&href=&parent.name=专业管理&isShow=0', '');
INSERT INTO `sys_log` VALUES ('470', '1', '1', '2014-05-11 17:26:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=110&icon=&sort=30&parent.id=95&name=修改&target=&permission=edu:eduMajor:edit&href=&parent.name=专业管理&isShow=0', '');
INSERT INTO `sys_log` VALUES ('471', '1', '1', '2014-05-11 17:27:10', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=77&icon=&sort=40&parent.id=79&name=成绩管理&target=&permission=&href=/edu/eduStuMajorGrade&parent.name=教师模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('472', '1', '1', '2014-05-11 17:27:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=112&icon=&sort=30&parent.id=77&name=查看&target=&permission=edu:eduStuMajorGrade:view&href=&parent.name=成绩管理&isShow=0', '');
INSERT INTO `sys_log` VALUES ('473', '1', '1', '2014-05-11 17:27:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=113&icon=&sort=30&parent.id=77&name=修改&target=&permission=edu:eduStuMajorGrade:edit&href=&parent.name=成绩管理&isShow=0', '');
INSERT INTO `sys_log` VALUES ('474', '1', '1', '2014-05-11 17:27:42', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=106&icon=&sort=5&parent.id=71&name=成绩查看&target=&permission=&href=/edu/eduStuMajorGrade/myView&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('475', '2', '1', '2014-05-11 17:27:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduSchool', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Table \'dev_postgraduate.edu_edu_school\' doesn\'t exist; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Table \'dev_postgraduate.edu_edu_school\' doesn\'t exist');
INSERT INTO `sys_log` VALUES ('476', '2', '1', '2014-05-11 17:30:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduStuMajorGradeList.jsp at line 27\r\n\r\n24: 	<form:form id=\"searchForm\" modelAttribute=\"eduStuMajorGrade\" action=\"${ctx}/edu/eduStuMajorGrade/\" method=\"post\" class=\"breadcrumb form-search\">\r\n25: 		<input id=\"pageNo\" name=\"pageNo\" type=\"hidden\" value=\"${page.pageNo}\"/>\r\n26: 		<input id=\"pageSize\" name=\"pageSize\" type=\"hidden\" value=\"${page.pageSize}\"/>\r\n27: 		<label>鍚嶇О 锛�/label><form:input path=\"name\" htmlEscape=\"false\" maxlength=\"50\" class=\"input-medium\"/>\r\n28: 		&nbsp;<input id=\"btnSubmit\" class=\"btn btn-primary\" type=\"submit\" value=\"鏌ヨ\"/>\r\n29: 	</form:form>\r\n30: 	<tags:message content=\"${message}\"/>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('477', '2', '1', '2014-05-11 18:16:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('478', '2', '1', '2014-05-11 18:18:37', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('479', '2', '1', '2014-05-11 18:20:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('480', '2', '1', '2014-05-11 18:21:08', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('481', '2', '1', '2014-05-11 18:21:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('482', '2', '1', '2014-05-11 18:24:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.student\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.student\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('483', '2', '1', '2014-05-11 18:24:42', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.orm.hibernate3.HibernateSystemException: could not deserialize; nested exception is org.hibernate.type.SerializationException: could not deserialize');
INSERT INTO `sys_log` VALUES ('484', '2', '1', '2014-05-11 18:24:45', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.orm.hibernate3.HibernateSystemException: could not deserialize; nested exception is org.hibernate.type.SerializationException: could not deserialize');
INSERT INTO `sys_log` VALUES ('485', '2', '1', '2014-05-11 18:25:50', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'this_.major\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'this_.major\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('486', '2', '1', '2014-05-11 18:28:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'edumajor2_.school\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'edumajor2_.school\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('487', '2', '1', '2014-05-11 18:29:14', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade', 'GET', '', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'edumajor2_.school\' in \'field list\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'edumajor2_.school\' in \'field list\'');
INSERT INTO `sys_log` VALUES ('488', '1', '1', '2014-05-11 18:48:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduSchool/save', 'POST', 'id=&address=北京路19号&name=北京大学&remarks=&schoolCode=BJ001&oldCode=', '');
INSERT INTO `sys_log` VALUES ('489', '1', '1', '2014-05-11 18:49:05', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduSchool/save', 'POST', 'id=2&address=北京路19号&name=北京大学&remarks=12&schoolCode=BJ001&oldCode=BJ001', '');
INSERT INTO `sys_log` VALUES ('490', '2', '1', '2014-05-11 18:54:22', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/form', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorForm.jsp at line 45\r\n\r\n42: 		<div class=\"control-group\">\r\n43: 			<label class=\"control-label\">涓撲笟浠ｇ爜:</label>\r\n44: 			<div class=\"controls\">\r\n45: 				<form:input path=\"code\" htmlEscape=\"false\" maxlength=\"200\" class=\"required\"/>\r\n46: 			</div>\r\n47: 		</div>\r\n48: 		<div class=\"control-group\">\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('491', '2', '1', '2014-05-11 19:48:00', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/checkCode', 'GET', 'schoolCode=&oldCode=&majorCode=1', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'schoolCode\' in \'where clause\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'schoolCode\' in \'where clause\'');
INSERT INTO `sys_log` VALUES ('492', '2', '1', '2014-05-11 19:48:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/checkCode', 'GET', 'schoolCode=&oldCode=&majorCode=JS', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'schoolCode\' in \'where clause\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'schoolCode\' in \'where clause\'');
INSERT INTO `sys_log` VALUES ('493', '2', '1', '2014-05-11 19:48:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/checkCode', 'GET', 'schoolCode=&oldCode=&majorCode=J', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'schoolCode\' in \'where clause\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'schoolCode\' in \'where clause\'');
INSERT INTO `sys_log` VALUES ('494', '2', '1', '2014-05-11 19:48:04', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/checkCode', 'GET', 'schoolCode=&oldCode=&majorCode=JSJ', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'schoolCode\' in \'where clause\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'schoolCode\' in \'where clause\'');
INSERT INTO `sys_log` VALUES ('495', '2', '1', '2014-05-11 19:48:41', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/checkCode', 'GET', 'schoolCode=&oldCode=&majorCode=J', 'org.springframework.dao.InvalidDataAccessResourceUsageException: Unknown column \'schoolCode\' in \'where clause\'; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: Unknown column \'schoolCode\' in \'where clause\'');
INSERT INTO `sys_log` VALUES ('496', '2', '1', '2014-05-11 20:01:51', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/save', 'POST', 'id=&name=英语&remarks=&schoolCode=QINGHUA&oldCode=&majorCode=ENGLISH', 'org.springframework.dao.DataIntegrityViolationException: Column \'school_id\' cannot be null; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: Column \'school_id\' cannot be null');
INSERT INTO `sys_log` VALUES ('497', '1', '1', '2014-05-11 20:06:00', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduMajor/save', 'POST', 'id=&name=英语&remarks=英语&schoolCode=QINGHUA&oldCode=&majorCode=ENGLISH', '');
INSERT INTO `sys_log` VALUES ('498', '2', '1', '2014-05-11 20:18:26', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduStuMajorGradeList.jsp at line 37\r\n\r\n34: 		<c:forEach items=\"${page.list}\" var=\"eduStuMajorGrade\">\r\n35: 			<tr>\r\n36: 				<td><a href=\"${ctx}/edu/eduStuMajorGrade/form?id=${eduStuMajorGrade.id}\">${eduStuMajorGrade.student.name}</a></td>\r\n37: 				<td>${eduStuMajorGrade.school.name}</td>\r\n38: 				<td>${eduStuMajorGrade.major.name}</td>\r\n39: 				<td>${eduStuMajorGrade.score}</td>\r\n40: 				<shiro:hasPermission name=\"edu:eduStuMajorGrade:edit\"><td>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('499', '2', '1', '2014-05-11 20:19:34', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/form', 'GET', 'id=1', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduStuMajorGradeForm.jsp at line 39\r\n\r\n36: 		<div class=\"control-group\">\r\n37: 			<label class=\"control-label\">鍚嶇О:</label>\r\n38: 			<div class=\"controls\">\r\n39: 				<form:input path=\"name\" htmlEscape=\"false\" maxlength=\"200\" class=\"required\"/>\r\n40: 			</div>\r\n41: 		</div>\r\n42: 		<div class=\"control-group\">\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('500', '1', '1', '2014-05-11 20:44:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=1&score=100', '');
INSERT INTO `sys_log` VALUES ('501', '1', '1', '2014-05-11 21:11:47', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=72&icon=&sort=10&parent.id=71&name=专业选择&target=&permission=&href=/edu/eduMajor/majorSelect&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('502', '1', '1', '2014-05-11 21:12:40', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=72&icon=&sort=10&parent.id=71&name=专业选择&target=&permission=&href=/edu/eduStuMajorGrade/majorSelect&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('503', '2', '1', '2014-05-11 21:14:36', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major.school.id=1&student.id=1', 'org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing: com.ourlife.dev.modules.edu.entity.EduStuMajorGrade.major -> com.ourlife.dev.modules.edu.entity.EduMajor; nested exception is java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing: com.ourlife.dev.modules.edu.entity.EduStuMajorGrade.major -> com.ourlife.dev.modules.edu.entity.EduMajor');
INSERT INTO `sys_log` VALUES ('504', '2', '1', '2014-05-11 21:16:06', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 63\r\n\r\n60: 		<div class=\"control-group\">\r\n61: 			<label class=\"control-label\">瀛︽牎:</label>\r\n62: 			<div class=\"controls\">\r\n63: 				<form:select path=\"eduStuMajorGrade.major.school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('505', '2', '1', '2014-05-11 21:16:11', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 63\r\n\r\n60: 		<div class=\"control-group\">\r\n61: 			<label class=\"control-label\">瀛︽牎:</label>\r\n62: 			<div class=\"controls\">\r\n63: 				<form:select path=\"eduStuMajorGrade.major.school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('506', '2', '1', '2014-05-11 21:16:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 63\r\n\r\n60: 		<div class=\"control-group\">\r\n61: 			<label class=\"control-label\">瀛︽牎:</label>\r\n62: 			<div class=\"controls\">\r\n63: 				<form:select path=\"eduStuMajorGrade.major.school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('507', '2', '1', '2014-05-11 21:16:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 63\r\n\r\n60: 		<div class=\"control-group\">\r\n61: 			<label class=\"control-label\">瀛︽牎:</label>\r\n62: 			<div class=\"controls\">\r\n63: 				<form:select path=\"eduStuMajorGrade.major.school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('508', '1', '1', '2014-05-11 21:17:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major.school=1&student.id=1&major=1', '');
INSERT INTO `sys_log` VALUES ('509', '1', '1', '2014-05-11 21:18:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major.school=1&student.id=1&major=1', '');
INSERT INTO `sys_log` VALUES ('510', '2', '1', '2014-05-11 21:25:27', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 66\r\n\r\n63: 				<select id=\"major-school\" name=\"major-school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n67: 					</c:forEach>\r\n68: 				</select>\r\n69: 			</div>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('511', '2', '1', '2014-05-11 21:26:07', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 66\r\n\r\n63: 				<select id=\"major-school\" name=\"major-school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n67: 					</c:forEach>\r\n68: 				</select>\r\n69: 			</div>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('512', '2', '1', '2014-05-11 21:26:36', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 66\r\n\r\n63: 				<select id=\"major-school\" name=\"major-school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n67: 					</c:forEach>\r\n68: 				</select>\r\n69: 			</div>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('513', '2', '1', '2014-05-11 21:26:40', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/majorSelect', 'GET', '', 'org.apache.jasper.JasperException: An exception occurred processing JSP page /WEB-INF/views/modules/edu/eduMajorSelect.jsp at line 66\r\n\r\n63: 				<select id=\"major-school\" name=\"major-school\" class=\"required\" onchange=\"populateMajorDropDownList(this);\"> \r\n64: 					<option value=\"\">---璇烽�鎷�--</option>\r\n65: 					<c:forEach items=\"${ schoolList}\" var=\"school\">\r\n66: 						<form:option value=\"${school.id }\">${school.name }</form:option>\r\n67: 					</c:forEach>\r\n68: 				</select>\r\n69: 			</div>\r\n\r\n\r\nStacktrace:');
INSERT INTO `sys_log` VALUES ('514', '2', '1', '2014-05-11 21:28:20', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1&student.id=1', 'org.springframework.dao.DataIntegrityViolationException: Column \'stu_id\' cannot be null; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: Column \'stu_id\' cannot be null');
INSERT INTO `sys_log` VALUES ('515', '2', '1', '2014-05-11 21:28:50', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1', 'org.springframework.dao.DataIntegrityViolationException: Column \'stu_id\' cannot be null; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: Column \'stu_id\' cannot be null');
INSERT INTO `sys_log` VALUES ('516', '2', '1', '2014-05-11 21:29:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1', 'org.springframework.dao.DataIntegrityViolationException: Column \'stu_id\' cannot be null; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: Column \'stu_id\' cannot be null');
INSERT INTO `sys_log` VALUES ('517', '1', '1', '2014-05-11 21:30:47', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStudent/save', 'POST', 'id=1&classes=三年级一班、二班&oldNo=S123&sex=男&address=上海&no=admin&name=test&age=20', '');
INSERT INTO `sys_log` VALUES ('518', '1', '1', '2014-05-11 21:31:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1', '');
INSERT INTO `sys_log` VALUES ('519', '1', '1', '2014-05-11 21:32:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=1&score=200', '');
INSERT INTO `sys_log` VALUES ('520', '1', '1', '2014-05-11 21:38:57', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1', '');
INSERT INTO `sys_log` VALUES ('521', '1', '1', '2014-05-11 21:42:57', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=106&icon=&sort=5&parent.id=71&name=成绩查看&target=&permission=&href=/edu/eduStuMajorGrade&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('522', '1', '1', '2014-05-11 21:43:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=72&icon=&sort=10&parent.id=71&name=专业选择&target=&permission=&href=/edu/eduStuMajorGrade&parent.name=学生模块&isShow=1', '');
INSERT INTO `sys_log` VALUES ('523', '1', '1', '2014-05-11 21:47:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStudent/save', 'POST', 'id=1&classes=三年级一班、二班&oldNo=admin&sex=男&address=上海&no=admi1&name=test&age=20', '');
INSERT INTO `sys_log` VALUES ('524', '1', '1', '2014-05-11 21:47:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStudent/delete', 'GET', 'id=1', '');
INSERT INTO `sys_log` VALUES ('525', '1', '1', '2014-05-11 21:48:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduTeacher/save', 'POST', 'id=&classes=三年级一班&course=英语&oldNo=&sex=男&title=教授&phone=1212121212&no=T20140001&name=莫言&mobile=15923813444', '');
INSERT INTO `sys_log` VALUES ('526', '1', '1', '2014-05-11 21:49:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStudent/save', 'POST', 'id=&classes=三年级一班&oldNo=&sex=女&address=北京路19号&no=S20140001&name=韩梅梅&age=20', '');
INSERT INTO `sys_log` VALUES ('527', '1', '1', '2014-05-11 21:51:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=107&icon=&sort=30&parent.id=106&name=修改&target=&permission=edu:eduStuMajorGrade:edit&href=&parent.name=成绩查看&isShow=0', '');
INSERT INTO `sys_log` VALUES ('528', '1', '1', '2014-05-11 21:51:44', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/sys/menu/save', 'POST', 'id=108&icon=&sort=30&parent.id=106&name=查看&target=&permission=edu:eduStuMajorGrade:view&href=&parent.name=成绩查看&isShow=0', '');
INSERT INTO `sys_log` VALUES ('529', '1', '3', '2014-05-11 21:52:04', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=1', '');
INSERT INTO `sys_log` VALUES ('530', '1', '2', '2014-05-11 21:54:10', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=5&score=100', '');
INSERT INTO `sys_log` VALUES ('531', '1', '3', '2014-05-11 21:54:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36', '/dev/edu/eduStuMajorGrade/save', 'POST', 'id=&major-school=1&majorSelect=2', '');

-- ----------------------------
-- Table structure for `sys_mdict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_mdict`;
CREATE TABLE `sys_mdict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_mdict_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_mdict_parent_ids` (`parent_ids`) USING BTREE,
  KEY `sys_mdict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_mdict
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `href` varchar(255) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标（mainFrame、 _blank、_self、_parent、_top）',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示（1：显示；0：不显示）',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_menu_parent_ids` (`parent_ids`) USING BTREE,
  KEY `sys_menu_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '顶级菜单', null, null, null, '0', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '0,1,', '系统设置', null, null, null, '900', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('3', '2', '0,1,2,', '系统设置', null, null, null, '980', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,1,2,3,', '菜单管理', '/sys/menu/', null, 'list-alt', '30', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('5', '4', '0,1,2,3,4,', '查看', null, null, null, '30', '0', 'sys:menu:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('6', '4', '0,1,2,3,4,', '修改', null, null, null, '30', '0', 'sys:menu:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '3', '0,1,2,3,', '角色管理', '/sys/role/', null, 'lock', '50', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '7', '0,1,2,3,7,', '查看', null, null, null, '30', '0', 'sys:role:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '7', '0,1,2,3,7,', '修改', null, null, null, '30', '0', 'sys:role:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '0,1,2,3,', '字典管理', '/sys/dict/', null, 'th-list', '60', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '10', '0,1,2,3,10,', '查看', null, null, null, '30', '0', 'sys:dict:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '10', '0,1,2,3,10,', '修改', null, null, null, '30', '0', 'sys:dict:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '2', '0,1,2,', '机构用户', null, null, null, '970', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '13', '0,1,2,13,', '区域管理', '/sys/area/', null, 'th', '50', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '14', '0,1,2,13,14,', '查看', null, null, null, '30', '0', 'sys:area:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '14', '0,1,2,13,14,', '修改', null, null, null, '30', '0', 'sys:area:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '13', '0,1,2,13,', '机构管理', '/sys/office/', null, 'th-large', '40', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '17', '0,1,2,13,17,', '查看', null, null, null, '30', '0', 'sys:office:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '17', '0,1,2,13,17,', '修改', null, null, null, '30', '0', 'sys:office:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '13', '0,1,2,13,', '用户管理', '/sys/user/', null, 'user', '30', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '20', '0,1,2,13,20,', '查看', null, null, null, '30', '0', 'sys:user:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '20', '0,1,2,13,20,', '修改', null, null, null, '30', '0', 'sys:user:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '0,1,', '系统首页', '', '', '', '100', '1', '', '1', '2013-05-27 08:00:00', '1', '2014-05-11 16:44:13', null, '0');
INSERT INTO `sys_menu` VALUES ('28', '27', '0,1,27,', '个人信息', '', '', '', '990', '0', '', '1', '2013-05-27 08:00:00', '1', '2014-05-01 19:37:45', null, '0');
INSERT INTO `sys_menu` VALUES ('29', '28', '0,1,27,28,', '个人信息', '/sys/user/info', null, 'user', '30', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('30', '28', '0,1,27,28,', '修改密码', '/sys/user/modifyPwd', null, 'lock', '40', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('67', '2', '0,1,2,', '日志查询', null, null, null, '985', '1', null, '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('68', '67', '0,1,2,67,', '日志查询', '/sys/log', null, 'pencil', '30', '1', 'sys:log:view', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('71', '27', '0,1,27,', '学生模块', '', '', '', '20', '1', '', '1', '2014-05-01 19:33:18', '1', '2014-05-11 16:22:25', null, '0');
INSERT INTO `sys_menu` VALUES ('72', '71', '0,1,27,71,', '专业选择', '/edu/eduStuMajorGrade', '', '', '10', '1', '', '1', '2014-05-01 19:33:39', '1', '2014-05-11 21:43:02', null, '0');
INSERT INTO `sys_menu` VALUES ('77', '79', '0,1,27,79,', '成绩管理', '/edu/eduStuMajorGrade', '', '', '40', '1', '', '1', '2014-05-01 19:38:57', '1', '2014-05-11 17:27:10', null, '0');
INSERT INTO `sys_menu` VALUES ('79', '27', '0,1,27,', '教师模块', '', '', '', '30', '1', '', '1', '2014-05-01 19:40:32', '1', '2014-05-11 16:22:13', null, '0');
INSERT INTO `sys_menu` VALUES ('94', '79', '0,1,27,79,', '学校管理', '/edu/eduSchool', '', '', '30', '1', '', '1', '2014-05-03 09:52:09', '1', '2014-05-11 16:18:46', null, '0');
INSERT INTO `sys_menu` VALUES ('95', '79', '0,1,27,79,', '专业管理', '/edu/eduMajor', '', '', '30', '1', '', '1', '2014-05-03 09:52:28', '1', '2014-05-11 17:26:09', null, '0');
INSERT INTO `sys_menu` VALUES ('96', '27', '0,1,27,', '人员管理', '', '', '', '30', '1', '', '1', '2014-05-05 19:42:39', '1', '2014-05-11 16:31:18', null, '0');
INSERT INTO `sys_menu` VALUES ('97', '96', '0,1,27,96,', '教师管理', '/edu/eduTeacher', '', '', '30', '1', '', '1', '2014-05-05 19:43:02', '1', '2014-05-11 16:31:26', null, '0');
INSERT INTO `sys_menu` VALUES ('98', '96', '0,1,27,96,', '家长信息管理', '/edu/eduParent', '', '', '30', '1', '', '1', '2014-05-05 19:43:16', '1', '2014-05-06 00:39:32', null, '1');
INSERT INTO `sys_menu` VALUES ('99', '96', '0,1,27,96,', '学生管理', '/edu/eduStudent', '', '', '30', '1', '', '1', '2014-05-05 19:43:31', '1', '2014-05-11 16:31:36', null, '0');
INSERT INTO `sys_menu` VALUES ('100', '97', '0,1,27,96,97,', '查看', '', '', '', '30', '0', 'edu:eduTeacher:view', '1', '2014-05-05 22:14:12', '1', '2014-05-05 22:14:12', null, '0');
INSERT INTO `sys_menu` VALUES ('101', '97', '0,1,27,96,97,', '修改', '', '', '', '30', '0', 'edu:eduTeacher:edit', '1', '2014-05-05 22:14:23', '1', '2014-05-05 22:14:23', null, '0');
INSERT INTO `sys_menu` VALUES ('102', '98', '0,1,27,96,98,', '查看', '', '', '', '30', '0', 'edu:eduParent:view', '1', '2014-05-06 00:39:51', '1', '2014-05-06 00:39:51', null, '1');
INSERT INTO `sys_menu` VALUES ('103', '98', '0,1,27,96,98,', '修改', '', '', '', '30', '0', 'edu:eduParent:edit', '1', '2014-05-06 00:40:05', '1', '2014-05-06 00:40:05', null, '1');
INSERT INTO `sys_menu` VALUES ('104', '99', '0,1,27,96,99,', '查看', '', '', '', '30', '0', 'edu:eduStudent:view', '1', '2014-05-06 00:41:09', '1', '2014-05-06 00:41:09', null, '0');
INSERT INTO `sys_menu` VALUES ('105', '99', '0,1,27,96,99,', '修改', '', '', '', '30', '0', 'edu:eduStudent:edit', '1', '2014-05-06 00:41:23', '1', '2014-05-06 00:41:23', null, '0');
INSERT INTO `sys_menu` VALUES ('106', '71', '0,1,27,71,', '成绩查看', '/edu/eduStuMajorGrade', '', '', '5', '1', '', '1', '2014-05-06 15:18:49', '1', '2014-05-11 21:42:56', null, '0');
INSERT INTO `sys_menu` VALUES ('107', '106', '0,1,27,71,106,', '修改', '', '', '', '30', '0', 'edu:eduStuMajorGrade:edit', '1', '2014-05-06 15:27:54', '1', '2014-05-11 21:51:31', null, '0');
INSERT INTO `sys_menu` VALUES ('108', '106', '0,1,27,71,106,', '查看', '', '', '', '30', '0', 'edu:eduStuMajorGrade:view', '1', '2014-05-06 15:30:27', '1', '2014-05-11 21:51:42', null, '0');
INSERT INTO `sys_menu` VALUES ('109', '95', '0,1,27,79,95,', '查看', '', '', '', '30', '0', 'edu:eduMajor:view', '1', '2014-05-06 18:46:26', '1', '2014-05-11 17:26:19', null, '0');
INSERT INTO `sys_menu` VALUES ('110', '95', '0,1,27,79,95,', '修改', '', '', '', '30', '0', 'edu:eduMajor:edit', '1', '2014-05-06 18:47:35', '1', '2014-05-11 17:26:31', null, '0');
INSERT INTO `sys_menu` VALUES ('111', '1', '0,1,', '查看', '', '', '', '30', '0', 'edu:eduBbsTopic:view', '1', '2014-05-06 20:10:52', '1', '2014-05-06 20:10:52', null, '1');
INSERT INTO `sys_menu` VALUES ('112', '77', '0,1,27,79,77,', '查看', '', '', '', '30', '0', 'edu:eduStuMajorGrade:view', '1', '2014-05-06 20:11:35', '1', '2014-05-11 17:27:19', null, '0');
INSERT INTO `sys_menu` VALUES ('113', '77', '0,1,27,79,77,', '修改', '', '', '', '30', '0', 'edu:eduStuMajorGrade:edit', '1', '2014-05-06 20:12:06', '1', '2014-05-11 17:27:30', null, '0');
INSERT INTO `sys_menu` VALUES ('114', '72', '0,1,27,71,72,', '回复主题', '', '', '', '30', '0', 'edu:eduBbsReply:edit', '1', '2014-05-06 20:56:25', '1', '2014-05-06 20:56:25', null, '1');
INSERT INTO `sys_menu` VALUES ('115', '72', '0,1,27,71,72,', '查看主题', '', '', '', '30', '0', 'edu:eduBbsReply:view', '1', '2014-05-06 21:00:24', '1', '2014-05-06 21:00:24', null, '1');
INSERT INTO `sys_menu` VALUES ('116', '94', '0,1,27,79,94,', '查看', '', '', '', '30', '0', 'edu:eduSchool:view', '1', '2014-05-07 01:02:34', '1', '2014-05-11 16:20:24', null, '0');
INSERT INTO `sys_menu` VALUES ('117', '94', '0,1,27,79,94,', '修改', '', '', '', '30', '0', 'edu:eduSchool:edit', '1', '2014-05-07 01:02:51', '1', '2014-05-11 16:20:36', null, '0');

-- ----------------------------
-- Table structure for `sys_office`
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `area_id` bigint(20) NOT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `type` char(1) NOT NULL COMMENT '机构类型（1：公司；2：部门；3：小组）',
  `grade` char(1) NOT NULL COMMENT '机构等级（1：一级；2：二级；3：三级；4：四级）',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_office_parent_ids` (`parent_ids`) USING BTREE,
  KEY `sys_office_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '2', '100000', '新东方教育集团', '1', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_office` VALUES ('2', '1', '0,1,', '2', '100001', '小学部', '2', '1', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:08:10', '', '0');
INSERT INTO `sys_office` VALUES ('3', '1', '0,1,', '2', '100002', '初中部', '2', '1', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:08:23', '', '0');
INSERT INTO `sys_office` VALUES ('4', '1', '0,1,', '2', '100003', '高中部', '2', '1', '', '', '', '', '', '', '1', '2014-05-05 20:10:47', '1', '2014-05-05 20:11:04', '', '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `office_id` bigint(20) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围（0：所有数据；1：所在公司及以下数据；2：所在公司数据；3：所在部门及以下数据；4：所在部门数据；8：仅本人数据；9：按明细设置）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '系统管理员', 'deptLeader', 'assignment', '1', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_role` VALUES ('2', '1', '教师', 'teacher', 'teacher', '5', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:11:20', null, '0');
INSERT INTO `sys_role` VALUES ('3', '1', '家长', 'parent', 'parent', '5', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:12:55', null, '1');
INSERT INTO `sys_role` VALUES ('4', '1', '学生', 'student', 'student', '5', '1', '2013-05-27 08:00:00', '1', '2014-05-05 20:13:46', null, '0');
INSERT INTO `sys_role` VALUES ('5', '1', '普通用户', 'f', 'assignment', '8', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '21');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '23');
INSERT INTO `sys_role_menu` VALUES ('1', '24');
INSERT INTO `sys_role_menu` VALUES ('1', '25');
INSERT INTO `sys_role_menu` VALUES ('1', '26');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('1', '32');
INSERT INTO `sys_role_menu` VALUES ('1', '33');
INSERT INTO `sys_role_menu` VALUES ('1', '34');
INSERT INTO `sys_role_menu` VALUES ('1', '35');
INSERT INTO `sys_role_menu` VALUES ('1', '36');
INSERT INTO `sys_role_menu` VALUES ('1', '37');
INSERT INTO `sys_role_menu` VALUES ('1', '38');
INSERT INTO `sys_role_menu` VALUES ('1', '39');
INSERT INTO `sys_role_menu` VALUES ('1', '40');
INSERT INTO `sys_role_menu` VALUES ('1', '41');
INSERT INTO `sys_role_menu` VALUES ('1', '42');
INSERT INTO `sys_role_menu` VALUES ('1', '43');
INSERT INTO `sys_role_menu` VALUES ('1', '44');
INSERT INTO `sys_role_menu` VALUES ('1', '45');
INSERT INTO `sys_role_menu` VALUES ('1', '46');
INSERT INTO `sys_role_menu` VALUES ('1', '47');
INSERT INTO `sys_role_menu` VALUES ('1', '48');
INSERT INTO `sys_role_menu` VALUES ('1', '49');
INSERT INTO `sys_role_menu` VALUES ('1', '50');
INSERT INTO `sys_role_menu` VALUES ('1', '51');
INSERT INTO `sys_role_menu` VALUES ('1', '52');
INSERT INTO `sys_role_menu` VALUES ('1', '53');
INSERT INTO `sys_role_menu` VALUES ('1', '54');
INSERT INTO `sys_role_menu` VALUES ('1', '55');
INSERT INTO `sys_role_menu` VALUES ('1', '56');
INSERT INTO `sys_role_menu` VALUES ('1', '57');
INSERT INTO `sys_role_menu` VALUES ('1', '58');
INSERT INTO `sys_role_menu` VALUES ('1', '59');
INSERT INTO `sys_role_menu` VALUES ('1', '60');
INSERT INTO `sys_role_menu` VALUES ('1', '61');
INSERT INTO `sys_role_menu` VALUES ('1', '62');
INSERT INTO `sys_role_menu` VALUES ('1', '63');
INSERT INTO `sys_role_menu` VALUES ('1', '64');
INSERT INTO `sys_role_menu` VALUES ('1', '65');
INSERT INTO `sys_role_menu` VALUES ('1', '66');
INSERT INTO `sys_role_menu` VALUES ('1', '67');
INSERT INTO `sys_role_menu` VALUES ('1', '68');
INSERT INTO `sys_role_menu` VALUES ('1', '69');
INSERT INTO `sys_role_menu` VALUES ('1', '70');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '28');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '30');
INSERT INTO `sys_role_menu` VALUES ('2', '77');
INSERT INTO `sys_role_menu` VALUES ('2', '79');
INSERT INTO `sys_role_menu` VALUES ('2', '94');
INSERT INTO `sys_role_menu` VALUES ('2', '95');
INSERT INTO `sys_role_menu` VALUES ('2', '109');
INSERT INTO `sys_role_menu` VALUES ('2', '110');
INSERT INTO `sys_role_menu` VALUES ('2', '112');
INSERT INTO `sys_role_menu` VALUES ('2', '113');
INSERT INTO `sys_role_menu` VALUES ('2', '116');
INSERT INTO `sys_role_menu` VALUES ('2', '117');
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '27');
INSERT INTO `sys_role_menu` VALUES ('3', '28');
INSERT INTO `sys_role_menu` VALUES ('3', '29');
INSERT INTO `sys_role_menu` VALUES ('3', '30');
INSERT INTO `sys_role_menu` VALUES ('3', '71');
INSERT INTO `sys_role_menu` VALUES ('3', '72');
INSERT INTO `sys_role_menu` VALUES ('3', '77');
INSERT INTO `sys_role_menu` VALUES ('3', '79');
INSERT INTO `sys_role_menu` VALUES ('3', '95');
INSERT INTO `sys_role_menu` VALUES ('3', '109');
INSERT INTO `sys_role_menu` VALUES ('3', '112');
INSERT INTO `sys_role_menu` VALUES ('3', '113');
INSERT INTO `sys_role_menu` VALUES ('3', '114');
INSERT INTO `sys_role_menu` VALUES ('3', '115');
INSERT INTO `sys_role_menu` VALUES ('4', '1');
INSERT INTO `sys_role_menu` VALUES ('4', '27');
INSERT INTO `sys_role_menu` VALUES ('4', '28');
INSERT INTO `sys_role_menu` VALUES ('4', '29');
INSERT INTO `sys_role_menu` VALUES ('4', '30');
INSERT INTO `sys_role_menu` VALUES ('4', '71');
INSERT INTO `sys_role_menu` VALUES ('4', '72');
INSERT INTO `sys_role_menu` VALUES ('4', '106');
INSERT INTO `sys_role_menu` VALUES ('4', '107');
INSERT INTO `sys_role_menu` VALUES ('4', '108');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '27');
INSERT INTO `sys_role_menu` VALUES ('5', '28');
INSERT INTO `sys_role_menu` VALUES ('5', '29');
INSERT INTO `sys_role_menu` VALUES ('5', '30');
INSERT INTO `sys_role_menu` VALUES ('5', '71');
INSERT INTO `sys_role_menu` VALUES ('5', '72');
INSERT INTO `sys_role_menu` VALUES ('5', '79');
INSERT INTO `sys_role_menu` VALUES ('5', '95');
INSERT INTO `sys_role_menu` VALUES ('6', '1');
INSERT INTO `sys_role_menu` VALUES ('6', '2');
INSERT INTO `sys_role_menu` VALUES ('6', '3');
INSERT INTO `sys_role_menu` VALUES ('6', '4');
INSERT INTO `sys_role_menu` VALUES ('6', '5');
INSERT INTO `sys_role_menu` VALUES ('6', '6');
INSERT INTO `sys_role_menu` VALUES ('6', '7');
INSERT INTO `sys_role_menu` VALUES ('6', '8');
INSERT INTO `sys_role_menu` VALUES ('6', '9');
INSERT INTO `sys_role_menu` VALUES ('6', '10');
INSERT INTO `sys_role_menu` VALUES ('6', '11');
INSERT INTO `sys_role_menu` VALUES ('6', '12');
INSERT INTO `sys_role_menu` VALUES ('6', '13');
INSERT INTO `sys_role_menu` VALUES ('6', '14');
INSERT INTO `sys_role_menu` VALUES ('6', '15');
INSERT INTO `sys_role_menu` VALUES ('6', '16');
INSERT INTO `sys_role_menu` VALUES ('6', '17');
INSERT INTO `sys_role_menu` VALUES ('6', '18');
INSERT INTO `sys_role_menu` VALUES ('6', '19');
INSERT INTO `sys_role_menu` VALUES ('6', '20');
INSERT INTO `sys_role_menu` VALUES ('6', '21');
INSERT INTO `sys_role_menu` VALUES ('6', '22');
INSERT INTO `sys_role_menu` VALUES ('6', '23');
INSERT INTO `sys_role_menu` VALUES ('6', '24');
INSERT INTO `sys_role_menu` VALUES ('6', '25');
INSERT INTO `sys_role_menu` VALUES ('6', '26');
INSERT INTO `sys_role_menu` VALUES ('6', '27');
INSERT INTO `sys_role_menu` VALUES ('6', '28');
INSERT INTO `sys_role_menu` VALUES ('6', '29');
INSERT INTO `sys_role_menu` VALUES ('6', '30');
INSERT INTO `sys_role_menu` VALUES ('6', '31');
INSERT INTO `sys_role_menu` VALUES ('6', '32');
INSERT INTO `sys_role_menu` VALUES ('6', '33');
INSERT INTO `sys_role_menu` VALUES ('6', '34');
INSERT INTO `sys_role_menu` VALUES ('6', '35');
INSERT INTO `sys_role_menu` VALUES ('6', '36');
INSERT INTO `sys_role_menu` VALUES ('6', '37');
INSERT INTO `sys_role_menu` VALUES ('6', '38');
INSERT INTO `sys_role_menu` VALUES ('6', '39');
INSERT INTO `sys_role_menu` VALUES ('6', '40');
INSERT INTO `sys_role_menu` VALUES ('6', '41');
INSERT INTO `sys_role_menu` VALUES ('6', '42');
INSERT INTO `sys_role_menu` VALUES ('6', '43');
INSERT INTO `sys_role_menu` VALUES ('6', '44');
INSERT INTO `sys_role_menu` VALUES ('6', '45');
INSERT INTO `sys_role_menu` VALUES ('6', '46');
INSERT INTO `sys_role_menu` VALUES ('6', '47');
INSERT INTO `sys_role_menu` VALUES ('6', '48');
INSERT INTO `sys_role_menu` VALUES ('6', '49');
INSERT INTO `sys_role_menu` VALUES ('6', '50');
INSERT INTO `sys_role_menu` VALUES ('6', '51');
INSERT INTO `sys_role_menu` VALUES ('6', '52');
INSERT INTO `sys_role_menu` VALUES ('6', '53');
INSERT INTO `sys_role_menu` VALUES ('6', '54');
INSERT INTO `sys_role_menu` VALUES ('6', '55');
INSERT INTO `sys_role_menu` VALUES ('6', '56');
INSERT INTO `sys_role_menu` VALUES ('6', '57');
INSERT INTO `sys_role_menu` VALUES ('6', '58');
INSERT INTO `sys_role_menu` VALUES ('6', '59');
INSERT INTO `sys_role_menu` VALUES ('6', '60');
INSERT INTO `sys_role_menu` VALUES ('6', '61');
INSERT INTO `sys_role_menu` VALUES ('6', '62');
INSERT INTO `sys_role_menu` VALUES ('6', '63');
INSERT INTO `sys_role_menu` VALUES ('6', '64');
INSERT INTO `sys_role_menu` VALUES ('6', '65');
INSERT INTO `sys_role_menu` VALUES ('6', '66');
INSERT INTO `sys_role_menu` VALUES ('6', '67');
INSERT INTO `sys_role_menu` VALUES ('6', '68');
INSERT INTO `sys_role_menu` VALUES ('6', '69');
INSERT INTO `sys_role_menu` VALUES ('6', '70');
INSERT INTO `sys_role_menu` VALUES ('7', '1');
INSERT INTO `sys_role_menu` VALUES ('7', '2');
INSERT INTO `sys_role_menu` VALUES ('7', '3');
INSERT INTO `sys_role_menu` VALUES ('7', '4');
INSERT INTO `sys_role_menu` VALUES ('7', '5');
INSERT INTO `sys_role_menu` VALUES ('7', '6');
INSERT INTO `sys_role_menu` VALUES ('7', '7');
INSERT INTO `sys_role_menu` VALUES ('7', '8');
INSERT INTO `sys_role_menu` VALUES ('7', '9');
INSERT INTO `sys_role_menu` VALUES ('7', '10');
INSERT INTO `sys_role_menu` VALUES ('7', '11');
INSERT INTO `sys_role_menu` VALUES ('7', '12');
INSERT INTO `sys_role_menu` VALUES ('7', '13');
INSERT INTO `sys_role_menu` VALUES ('7', '14');
INSERT INTO `sys_role_menu` VALUES ('7', '15');
INSERT INTO `sys_role_menu` VALUES ('7', '16');
INSERT INTO `sys_role_menu` VALUES ('7', '17');
INSERT INTO `sys_role_menu` VALUES ('7', '18');
INSERT INTO `sys_role_menu` VALUES ('7', '19');
INSERT INTO `sys_role_menu` VALUES ('7', '20');
INSERT INTO `sys_role_menu` VALUES ('7', '21');
INSERT INTO `sys_role_menu` VALUES ('7', '22');
INSERT INTO `sys_role_menu` VALUES ('7', '23');
INSERT INTO `sys_role_menu` VALUES ('7', '24');
INSERT INTO `sys_role_menu` VALUES ('7', '25');
INSERT INTO `sys_role_menu` VALUES ('7', '26');
INSERT INTO `sys_role_menu` VALUES ('7', '27');
INSERT INTO `sys_role_menu` VALUES ('7', '28');
INSERT INTO `sys_role_menu` VALUES ('7', '29');
INSERT INTO `sys_role_menu` VALUES ('7', '30');
INSERT INTO `sys_role_menu` VALUES ('7', '31');
INSERT INTO `sys_role_menu` VALUES ('7', '32');
INSERT INTO `sys_role_menu` VALUES ('7', '33');
INSERT INTO `sys_role_menu` VALUES ('7', '34');
INSERT INTO `sys_role_menu` VALUES ('7', '35');
INSERT INTO `sys_role_menu` VALUES ('7', '36');
INSERT INTO `sys_role_menu` VALUES ('7', '37');
INSERT INTO `sys_role_menu` VALUES ('7', '38');
INSERT INTO `sys_role_menu` VALUES ('7', '39');
INSERT INTO `sys_role_menu` VALUES ('7', '40');
INSERT INTO `sys_role_menu` VALUES ('7', '41');
INSERT INTO `sys_role_menu` VALUES ('7', '42');
INSERT INTO `sys_role_menu` VALUES ('7', '43');
INSERT INTO `sys_role_menu` VALUES ('7', '44');
INSERT INTO `sys_role_menu` VALUES ('7', '45');
INSERT INTO `sys_role_menu` VALUES ('7', '46');
INSERT INTO `sys_role_menu` VALUES ('7', '47');
INSERT INTO `sys_role_menu` VALUES ('7', '48');
INSERT INTO `sys_role_menu` VALUES ('7', '49');
INSERT INTO `sys_role_menu` VALUES ('7', '50');
INSERT INTO `sys_role_menu` VALUES ('7', '51');
INSERT INTO `sys_role_menu` VALUES ('7', '52');
INSERT INTO `sys_role_menu` VALUES ('7', '53');
INSERT INTO `sys_role_menu` VALUES ('7', '54');
INSERT INTO `sys_role_menu` VALUES ('7', '55');
INSERT INTO `sys_role_menu` VALUES ('7', '56');
INSERT INTO `sys_role_menu` VALUES ('7', '57');
INSERT INTO `sys_role_menu` VALUES ('7', '58');
INSERT INTO `sys_role_menu` VALUES ('7', '59');
INSERT INTO `sys_role_menu` VALUES ('7', '60');
INSERT INTO `sys_role_menu` VALUES ('7', '61');
INSERT INTO `sys_role_menu` VALUES ('7', '62');
INSERT INTO `sys_role_menu` VALUES ('7', '63');
INSERT INTO `sys_role_menu` VALUES ('7', '64');
INSERT INTO `sys_role_menu` VALUES ('7', '65');
INSERT INTO `sys_role_menu` VALUES ('7', '66');
INSERT INTO `sys_role_menu` VALUES ('7', '67');
INSERT INTO `sys_role_menu` VALUES ('7', '68');
INSERT INTO `sys_role_menu` VALUES ('7', '69');
INSERT INTO `sys_role_menu` VALUES ('7', '70');

-- ----------------------------
-- Table structure for `sys_role_office`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `office_id` bigint(20) NOT NULL COMMENT '机构编号',
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-机构';

-- ----------------------------
-- Records of sys_role_office
-- ----------------------------
INSERT INTO `sys_role_office` VALUES ('7', '7');
INSERT INTO `sys_role_office` VALUES ('7', '8');
INSERT INTO `sys_role_office` VALUES ('7', '9');
INSERT INTO `sys_role_office` VALUES ('7', '10');
INSERT INTO `sys_role_office` VALUES ('7', '11');
INSERT INTO `sys_role_office` VALUES ('7', '12');
INSERT INTO `sys_role_office` VALUES ('7', '13');
INSERT INTO `sys_role_office` VALUES ('7', '14');
INSERT INTO `sys_role_office` VALUES ('7', '15');
INSERT INTO `sys_role_office` VALUES ('7', '16');
INSERT INTO `sys_role_office` VALUES ('7', '17');
INSERT INTO `sys_role_office` VALUES ('7', '18');
INSERT INTO `sys_role_office` VALUES ('7', '19');
INSERT INTO `sys_role_office` VALUES ('7', '20');
INSERT INTO `sys_role_office` VALUES ('7', '21');
INSERT INTO `sys_role_office` VALUES ('7', '22');
INSERT INTO `sys_role_office` VALUES ('7', '23');
INSERT INTO `sys_role_office` VALUES ('7', '24');
INSERT INTO `sys_role_office` VALUES ('7', '25');
INSERT INTO `sys_role_office` VALUES ('7', '26');

-- ----------------------------
-- Table structure for `sys_upload_info`
-- ----------------------------
DROP TABLE IF EXISTS `sys_upload_info`;
CREATE TABLE `sys_upload_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `file_path` varchar(200) DEFAULT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `file_type` varchar(100) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_upload_info
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `company_id` bigint(20) NOT NULL COMMENT '归属公司',
  `office_id` bigint(20) NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`) USING BTREE,
  KEY `sys_user_login_name` (`login_name`) USING BTREE,
  KEY `sys_user_company_id` (`company_id`) USING BTREE,
  KEY `sys_user_update_date` (`update_date`) USING BTREE,
  KEY `sys_user_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '1', 'admin', '02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032', '0001', '管理员', 'shangwenpeng@126.com', '8675', '8675', '1', '127.0.0.1', '2014-05-11 21:51:45', '1', '2013-05-27 08:00:00', '1', '2014-05-02 10:37:32', '最高管理员', '0');
INSERT INTO `sys_user` VALUES ('2', '1', '1', 'T20140001', 'b397bf744922c5e4b3c50a669179ee6b283194aab5dcd02e58b043c0', 'T20140001', '莫言', null, '1212121212', '15923813444', '2', '127.0.0.1', '2014-05-11 21:53:21', '1', '2014-05-11 21:48:06', '1', '2014-05-11 21:48:06', null, '0');
INSERT INTO `sys_user` VALUES ('3', '1', '1', 'S20140001', 'e5980dd1f1f5062881b712bc5d64474af0cfeb60084a119e6c475cd6', 'S20140001', '韩梅梅', null, null, null, '4', '127.0.0.1', '2014-05-11 21:54:33', '1', '2014-05-11 21:49:03', '1', '2014-05-11 21:49:03', null, '0');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '4');
INSERT INTO `sys_user_role` VALUES ('4', '2');
INSERT INTO `sys_user_role` VALUES ('5', '2');
INSERT INTO `sys_user_role` VALUES ('6', '4');
INSERT INTO `sys_user_role` VALUES ('7', '4');
INSERT INTO `sys_user_role` VALUES ('9', '2');
INSERT INTO `sys_user_role` VALUES ('10', '2');
INSERT INTO `sys_user_role` VALUES ('11', '4');
INSERT INTO `sys_user_role` VALUES ('12', '3');
