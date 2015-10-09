SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE biz_product;
DROP TABLE biz_supplier;
DROP TABLE biz_product_history;
DROP TABLE biz_order;
DROP TABLE biz_order_log;




/* Create Tables */

CREATE TABLE biz_product
(
	product_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(100),
	category_name varchar(20),
	original_price float,
	purchase_price float,
	recommend_price float,
	status int,
	valid_date varchar(20),
	amount int,
	sort int,
	image varchar(100),
	notice varchar(100),
	product_introduction varchar(100),
	product_detail varchar(150),
	allow_cancel int,
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	supplier_id bigint,
	cancel_fee_category int,
	cancel_fee_amount float,
	atLeastAmount int,
	atMostAmount int,
	effect_time varchar(10),
	audit_flag int DEFAULT 0,
	platform_price float,
	PRIMARY KEY (product_id)
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
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_product_history
(
	id bigint NOT NULL AUTO_INCREMENT,
	product_id bigint NOT NULL,
	name varchar(100),
	category_name varchar(20),
	original_price float,
	purchase_price float,
	recommend_price float,
	status int,
	amount int,
	notice varchar(100),
	product_introduction varchar(100),
	product_detail varchar(150),
	allow_cancel int,
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	supplier_id bigint,
	cancel_fee_category int,
	cancel_fee_amount float,
	effect_time varchar(10),
	audit_flag int DEFAULT 0,
	platform_price float,
	action_code varchar(10),
	PRIMARY KEY (id)
);


CREATE TABLE biz_order
(
	id bigint NOT NULL AUTO_INCREMENT,
	name varchar(100) COMMENT '订单名称',
	order_id varchar(50),
	pro_history_id bigint,
	customer_name varchar(20),
	customer_mobile varchar(20),
	contact_mobile varchar(20),
	customer_id varchar(20),
	purchase_price double COMMENT '购买价格',
	purchase_amount int,
	totalPay double,
	use_date varchar(50),
	status varchar(2),
	pay_status varchar(2),
	uu_id varchar(20) COMMENT '景区ID',
	uu_saler_id varchar(20) COMMENT '商家编号',
	uu_pid varchar(20) COMMENT '门票编号',
	uu_ordernum varchar(20) COMMENT '票付通订单号',
	uu_code varchar(20) COMMENT '凭证号',
	uu_rremsg varchar(4) COMMENT '短信发送次数',
	uu_ctime varchar(20) COMMENT ' 取消时间',
	uu_error varchar(100) COMMENT '错误代码.',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE biz_order_log
(
	id bigint(0) NOT NULL UNIQUE AUTO_INCREMENT COMMENT '编号',
	order_id bigint COMMENT '订单编号',
	name varchar(200) COMMENT '描述',
	create_by bigint COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by bigint COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);



