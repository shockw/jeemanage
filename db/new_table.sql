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

CREATE TABLE `park_pay` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `floor` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '楼层',
  `jiffy_stand` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车架',
  `space` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车位',
  `number` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '车牌号',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `cost` int(11) NOT NULL COMMENT '费用',
  
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='费用记录';

CREATE TABLE `park_door_control` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `door_control_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '门卡编号',
  `number` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '车牌号',
  `floor` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '楼层',
  `jiffy_stand` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车架',
  `space` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车位',
  `isuse` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停车',
  
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='门禁日志';


CREATE TABLE `park_pay_rule` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `period` int(11) NOT NULL COMMENT '计费周期，单位为分钟',
  `price` int(11) NOT NULL COMMENT '单位周期内费用',
  
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='计费规则';

CREATE TABLE `park_pay_code` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `count` int(11) NOT NULL COMMENT '费用',
  `code`  longblob COMMENT '对应二维码费用图片',
  
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='费用二维码';



CREATE TABLE `park_space_control` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `door_control_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '门卡编号',
  `number` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '车牌号',
  `floor` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '楼层',
  `jiffy_stand` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车架',
  `space` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '停车位',
  `isuse` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停车',
  
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `park_space_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='平台日志';