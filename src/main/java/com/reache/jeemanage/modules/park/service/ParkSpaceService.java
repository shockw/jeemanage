/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.service.CrudService;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.dao.ParkSpaceDao;

/**
 * 停车位Service
 * @author shock
 * @version 2018-02-25
 */
@Service
@Transactional(readOnly = true)
public class ParkSpaceService extends CrudService<ParkSpaceDao, ParkSpace> {

	public ParkSpace get(String id) {
		return super.get(id);
	}
	
	public List<ParkSpace> findList(ParkSpace parkSpace) {
		return super.findList(parkSpace);
	}
	
	public Page<ParkSpace> findPage(Page<ParkSpace> page, ParkSpace parkSpace) {
		return super.findPage(page, parkSpace);
	}
	
	@Transactional(readOnly = false)
	public void save(ParkSpace parkSpace) {
		super.save(parkSpace);
	}
	
	@Transactional(readOnly = false)
	public void delete(ParkSpace parkSpace) {
		super.delete(parkSpace);
	}
	
}