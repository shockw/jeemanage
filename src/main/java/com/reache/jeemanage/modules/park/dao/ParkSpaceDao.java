/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reache.jeemanage.common.persistence.CrudDao;
import com.reache.jeemanage.common.persistence.annotation.MyBatisDao;
import com.reache.jeemanage.modules.park.entity.ParkSpace;

/**
 * 停车位DAO接口
 * @author shock
 * @version 2018-02-25
 */
@MyBatisDao
public interface ParkSpaceDao extends CrudDao<ParkSpace> {
	public List<HashMap> findIdleList();
	public List<ParkSpace> findIdle();
	public ParkSpace findOne(String number);
}