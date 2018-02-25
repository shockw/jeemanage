CREATE TABLE `park_space` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `floor` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '楼层',
  `jiffy_stand` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车架',
  `space` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车位',
  `isuse` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否使用',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='停车位';