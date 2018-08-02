DROP TABLE IF EXISTS `test_data`;

CREATE TABLE `test_data` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属用户',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `area_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属区域',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `in_date` date DEFAULT NULL COMMENT '加入日期',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `test_data_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务数据表';

/*借款流程业务表 */
CREATE TABLE `oa_loan` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `process_instance_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例编号',
  `user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `summary` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '借款事由',
  `fee` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '借款金额',
  `reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '借款原因',
  `actbank` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开户行',
  `actno` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `actname` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账号名',
  `financial_text` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '财务预审',
  `lead_text` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '部门领导意见',
  `main_lead_text` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '总经理意见',
  `teller` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '出纳支付',
    
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `OA_TEST_AUDIT_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='借款流程测试表';