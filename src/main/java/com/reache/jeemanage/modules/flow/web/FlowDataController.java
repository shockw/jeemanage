/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.flow.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reache.jeemanage.common.config.Global;
import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.web.BaseController;
import com.reache.jeemanage.common.utils.StringUtils;
import com.reache.jeemanage.modules.flow.entity.FlowData;
import com.reache.jeemanage.modules.flow.service.FlowDataService;

/**
 * 流程数据共享Controller
 * @author shockw
 * @version 2019-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowData")
public class FlowDataController extends BaseController {

	@Autowired
	private FlowDataService flowDataService;
	
	@ModelAttribute
	public FlowData get(@RequestParam(required=false) String id) {
		FlowData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowDataService.get(id);
		}
		if (entity == null){
			entity = new FlowData();
		}
		return entity;
	}
	
	@RequiresPermissions("flow:flowData:view")
	@RequestMapping(value = {"list", ""})
	public String list(FlowData flowData, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<FlowData> page = flowDataService.findPage(new Page<FlowData>(request, response), flowData); 
		model.addAttribute("page", page);
		return "modules/flow/flowDataList";
	}

	@RequiresPermissions("flow:flowData:view")
	@RequestMapping(value = "form")
	public String form(FlowData flowData, Model model) {
		model.addAttribute("flowData", flowData);
		return "modules/flow/flowDataForm";
	}

	@RequiresPermissions("flow:flowData:edit")
	@RequestMapping(value = "save")
	public String save(FlowData flowData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, flowData)){
			return form(flowData, model);
		}
		flowDataService.save(flowData);
		addMessage(redirectAttributes, "保存流程数据可视化成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowData/?repage";
	}
	
	@RequiresPermissions("flow:flowData:edit")
	@RequestMapping(value = "delete")
	public String delete(FlowData flowData, RedirectAttributes redirectAttributes) {
		flowDataService.delete(flowData);
		addMessage(redirectAttributes, "删除流程数据可视化成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowData/?repage";
	}

}