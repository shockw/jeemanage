/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.service.CrudService;
import com.reache.jeemanage.modules.park.entity.ParkDoorControl;
import com.reache.jeemanage.modules.park.dao.ParkDoorControlDao;

/**
 * 门禁日志Service
 * @author shock
 * @version 2018-03-19
 */
@Service
@Transactional(readOnly = true)
public class ParkDoorControlService extends CrudService<ParkDoorControlDao, ParkDoorControl> {

	public ParkDoorControl get(String id) {
		return super.get(id);
	}
	
	public List<ParkDoorControl> findList(ParkDoorControl parkDoorControl) {
		return super.findList(parkDoorControl);
	}
	
	public Page<ParkDoorControl> findPage(Page<ParkDoorControl> page, ParkDoorControl parkDoorControl) {
		return super.findPage(page, parkDoorControl);
	}
	
	@Transactional(readOnly = false)
	public void save(ParkDoorControl parkDoorControl) {
		super.save(parkDoorControl);
	}
	
	@Transactional(readOnly = false)
	public void delete(ParkDoorControl parkDoorControl) {
		super.delete(parkDoorControl);
	}
	
}