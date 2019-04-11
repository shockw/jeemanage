/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.service.CrudService;
import com.reache.jeemanage.modules.flow.entity.FlowData;
import com.reache.jeemanage.modules.flow.dao.FlowDataDao;

/**
 * 流程数据共享Service
 * @author shockw
 * @version 2019-04-01
 */
@Service
@Transactional(readOnly = true)
public class FlowDataService extends CrudService<FlowDataDao, FlowData> {

	public FlowData get(String id) {
		return super.get(id);
	}
	
	public List<FlowData> findList(FlowData flowData) {
		return super.findList(flowData);
	}
	
	public Page<FlowData> findPage(Page<FlowData> page, FlowData flowData) {
		return super.findPage(page, flowData);
	}
	
	@Transactional(readOnly = false)
	public void save(FlowData flowData) {
		super.save(flowData);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlowData flowData) {
		super.delete(flowData);
	}
	
}