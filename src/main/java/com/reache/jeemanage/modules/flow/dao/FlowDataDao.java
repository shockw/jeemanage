/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.flow.dao;

import com.reache.jeemanage.common.persistence.CrudDao;
import com.reache.jeemanage.common.persistence.annotation.MyBatisDao;
import com.reache.jeemanage.modules.flow.entity.FlowData;

/**
 * 流程数据共享DAO接口
 * @author shockw
 * @version 2019-04-01
 */
@MyBatisDao
public interface FlowDataDao extends CrudDao<FlowData> {
	
}