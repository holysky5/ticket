SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE biz_scenic_detail;
DROP TABLE biz_distributor;
DROP TABLE biz_notice;
DROP TABLE biz_balance_his;
DROP TABLE biz_supplier;
DROP TABLE biz_supplier_distributor;
DROP TABLE biz_product_direct_price;
DROP TABLE biz_direct_balance_his;




/* Create Tables */

CREATE TABLE biz_scenic_detail
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	scenic_id bigint COMMENT '景区编号',
	type varchar(2) COMMENT '类型',
	content text COMMENT '内容',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_distributor
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	username varchar(40) COMMENT '用户名',
	password varchar(100) COMMENT '密码',
	name varchar(40),
	company varchar(100) COMMENT '公司名称',
	phone varchar(20) COMMENT '电话',
	mobile varchar(20) COMMENT '手机',
	email varchar(100) COMMENT '邮箱',
	qq varchar(20) COMMENT 'QQ',
	business_license varchar(100) COMMENT '营业执照',
	address varchar(100) COMMENT '联系地址',
	introduce text COMMENT '公司简介',
	group_name varchar(40) COMMENT '分组',
	sort int COMMENT '排序',
	status varchar(2) COMMENT '状态',
	balance double COMMENT '余额',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_notice
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	title varchar(100) COMMENT '标题',
	content text COMMENT '内容',
	type varchar(40) COMMENT '查看对象',
	status varchar(2) COMMENT '状态',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_balance_his
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	direct_id bigint COMMENT '用户编号',
	begin double COMMENT '充值前',
	amount double COMMENT '金额',
	type int COMMENT '1充值-1消费',
	end double COMMENT '变动后',
	reason varchar(100) COMMENT '变动原因',
	status varchar(2) COMMENT '状态 0成功 -1失败',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_supplier
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	no varchar(20) COMMENT '供应商帐号',
	name varchar(100) COMMENT '供应商名称',
	area varchar(100) COMMENT '所在地区',
	address varchar(200) COMMENT '详细地址',
	contact_name varchar(100) COMMENT '联系人姓名',
	contact_mobile varchar(20) COMMENT '联系人手机',
	contact_phone varchar(20) COMMENT '联系人电话',
	contact_email varchar(100) COMMENT '联系人邮箱',
	sort int COMMENT '排序',
	status varchar(2) COMMENT '状态 0启用 1停用 2待审核',
	balance double DEFAULT 0 COMMENT '账户余额',
	uu_id varchar(20) COMMENT '景区ID',
	uu_saler_id varchar(20) COMMENT '商家编号',
	uu_terminal_id varchar(20) COMMENT '终端编号',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_supplier_distributor
(
	ID bigint NOT NULL UNIQUE AUTO_INCREMENT COMMENT 'ID',
	supplier_id bigint COMMENT '供应商编号',
	distributor_id bigint COMMENT '分销商编号',
	status varchar(0) COMMENT '状态',
	apply_status varchar(2) COMMENT '申请状态',
	balance double COMMENT '账户余额',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (ID)
);


CREATE TABLE biz_product_direct_price
(
	id bigint NOT NULL AUTO_INCREMENT,
	direct_id bigint COMMENT '直通编号',
	product_id bigint COMMENT '产品ID',
	price double COMMENT '产品价格',
	PRIMARY KEY (id)
);


CREATE TABLE biz_direct_balance_his
(
	id bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
	direct_id bigint COMMENT '直通编号',
	begin double COMMENT '充值前',
	amount double COMMENT '金额',
	type int COMMENT '1充值-1消费',
	end double COMMENT '变动后',
	reason varchar(100) COMMENT '变动原因',
	status varchar(2) COMMENT '状态 0成功 -1失败',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);



