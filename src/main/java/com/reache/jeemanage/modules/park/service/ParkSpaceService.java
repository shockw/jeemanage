/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.service.CrudService;
import com.reache.jeemanage.modules.park.entity.IdleParkSpaceDto;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.dao.ParkSpaceDao;

/**
 * 停车位Service
 * 
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

	public List<IdleParkSpaceDto> findIdleParkSpacePage() {
		List<HashMap> list = dao.findIdleList();
		List<IdleParkSpaceDto> idleParkSpaceList = new ArrayList<IdleParkSpaceDto>();
		for (int i = 0; i < list.size(); i++) {
			IdleParkSpaceDto dto = new IdleParkSpaceDto();
			dto.setFloor((String) (list.get(i).get("floor")));
			dto.setJiffyStand((String) (list.get(i).get("jiffy_stand")));
			dto.setIdleCount(list.get(i).get("idle_count") + "");
			dto.setCount(list.get(i).get("count") + "");
			String inUseStr = (String) list.get(i).get("inuse_spaces");
			if (inUseStr != null) {
				String[] inUseArray = inUseStr.split(",");
				for (int j = 0; j < inUseArray.length; j++) {
					ParkSpace ps = dao.get(inUseArray[j]);
					dto.getInuseSpaces().add(ps);
				}
			}

			String idleStr = (String) list.get(i).get("idle_spaces");
			if (idleStr != null) {
				String[] idleArray = idleStr.split(",");
				System.out.println(idleStr);
				for (int k = 0; k < idleArray.length; k++) {
					ParkSpace ps = dao.get(idleArray[k]);
					dto.getIdleSpaces().add(ps);
				}
			}

			idleParkSpaceList.add(dto);
		}
		return idleParkSpaceList;
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