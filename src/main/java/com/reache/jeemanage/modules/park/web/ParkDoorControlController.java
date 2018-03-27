/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reache.jeemanage.common.config.Global;
import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.web.BaseController;
import com.reache.jeemanage.common.utils.StringUtils;
import com.reache.jeemanage.modules.park.entity.IdleParkSpaceDto;
import com.reache.jeemanage.modules.park.entity.ParkDoorControl;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.service.ParkDoorControlService;
import com.reache.jeemanage.modules.park.service.ParkPayService;
import com.reache.jeemanage.modules.park.service.ParkSpaceService;

/**
 * 门禁日志Controller
 * @author shock
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkDoorControl")
public class ParkDoorControlController extends BaseController {

	@Autowired
	private ParkDoorControlService parkDoorControlService;
	@Autowired
	private ParkSpaceService parkSpaceService;
	@Autowired
	private ParkPayService parkPayService;
	
	@ModelAttribute
	public ParkDoorControl get(@RequestParam(required=false) String id) {
		ParkDoorControl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parkDoorControlService.get(id);
		}
		if (entity == null){
			entity = new ParkDoorControl();
		}
		return entity;
	}
	
	@RequiresPermissions("park:parkDoorControl:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParkDoorControl parkDoorControl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParkDoorControl> page = parkDoorControlService.findPage(new Page<ParkDoorControl>(request, response), parkDoorControl); 
		model.addAttribute("page", page);
		return "modules/park/parkDoorControlList";
	}

	@RequiresPermissions("park:parkDoorControl:view")
	@RequestMapping(value = "form")
	public String form(ParkDoorControl parkDoorControl, Model model) {
		model.addAttribute("parkDoorControl", parkDoorControl);
		return "modules/park/parkDoorControlForm";
	}

	@RequiresPermissions("park:parkDoorControl:edit")
	@RequestMapping(value = "save")
	public String save(ParkDoorControl parkDoorControl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parkDoorControl)){
			return form(parkDoorControl, model);
		}
		parkDoorControlService.save(parkDoorControl);
		addMessage(redirectAttributes, "保存门禁日志成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkDoorControl/?repage";
	}
	
	@RequiresPermissions("park:parkDoorControl:edit")
	@RequestMapping(value = "change")
	@ResponseBody
	public String change(ParkDoorControl parkDoorControl, Model model, RedirectAttributes redirectAttributes) {
		if(parkDoorControl.getIsuse().equals("1")){
			ParkSpace ps = parkSpaceService.findIdleList().get(0);
			parkDoorControl.setFloor(ps.getFloor());
			parkDoorControl.setJiffyStand(ps.getJiffyStand());
			parkDoorControl.setSpace(ps.getSpace());
			ps.setIsuse("1");
			ps.setNumber(parkDoorControl.getNumber());
			parkSpaceService.save(ps);
			parkDoorControlService.save(parkDoorControl);
		}
		if(parkDoorControl.getIsuse().equals("0")){
			ParkSpace ps = parkSpaceService.findOne(parkDoorControl.getNumber());
			if(ps!=null){
				ps.setIsuse("0");
				ps.setNumber("");
				parkSpaceService.save(ps);
				parkDoorControl.setFloor(ps.getFloor());
				parkDoorControl.setJiffyStand(ps.getJiffyStand());
				parkDoorControl.setSpace(ps.getSpace());
				parkDoorControlService.save(parkDoorControl);
				ParkPay pp = new ParkPay();
				pp.setFloor(ps.getFloor());
				pp.setJiffyStand(ps.getJiffyStand());
				pp.setSpace(ps.getSpace());
				pp.setNumber(parkDoorControl.getNumber());
				pp.setStartDate(ps.getUpdateDate());
				pp.setEndDate(new Date());
				long times = pp.getEndDate().getTime()-pp.getStartDate().getTime();
				pp.setCost((int)(times/1800000+1)*30+"");
				parkPayService.save(pp);
			}else{
				return "no car,error!";
			}
			
		}
		
		return "success";
	}
	
	@RequiresPermissions("park:parkDoorControl:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkDoorControl parkDoorControl, RedirectAttributes redirectAttributes) {
		parkDoorControlService.delete(parkDoorControl);
		addMessage(redirectAttributes, "删除门禁日志成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkDoorControl/?repage";
	}

}