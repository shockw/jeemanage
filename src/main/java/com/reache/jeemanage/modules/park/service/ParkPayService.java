/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.service.CrudService;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.dao.ParkPayDao;

/**
 * 停车费用Service
 * @author shock
 * @version 2018-03-19
 */
@Service
@Transactional(readOnly = true)
public class ParkPayService extends CrudService<ParkPayDao, ParkPay> {

	public ParkPay get(String id) {
		return super.get(id);
	}
	
	public List<ParkPay> findList(ParkPay parkPay) {
		return super.findList(parkPay);
	}
	
	public Page<ParkPay> findPage(Page<ParkPay> page, ParkPay parkPay) {
		return super.findPage(page, parkPay);
	}
	
	@Transactional(readOnly = false)
	public void save(ParkPay parkPay) {
		super.save(parkPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(ParkPay parkPay) {
		super.delete(parkPay);
	}
	
}