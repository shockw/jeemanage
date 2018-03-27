/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.dao;

import com.reache.jeemanage.common.persistence.CrudDao;
import com.reache.jeemanage.common.persistence.annotation.MyBatisDao;
import com.reache.jeemanage.modules.park.entity.ParkDoorControl;

/**
 * 门禁日志DAO接口
 * @author shock
 * @version 2018-03-19
 */
@MyBatisDao
public interface ParkDoorControlDao extends CrudDao<ParkDoorControl> {
	
}