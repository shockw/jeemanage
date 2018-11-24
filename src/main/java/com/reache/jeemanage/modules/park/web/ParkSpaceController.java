/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.reache.jeemanage.modules.park.entity.IdleParkSpaceDto;
import com.reache.jeemanage.modules.park.entity.ParkDoorControl;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.entity.ParkPayRule;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.service.ParkDoorControlService;
import com.reache.jeemanage.modules.park.service.ParkPayRuleService;
import com.reache.jeemanage.modules.park.service.ParkPayService;
import com.reache.jeemanage.modules.park.service.ParkSpaceService;

/**
 * 停车位Controller
 * 
 * @author shock
 * @version 2018-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkSpace")
public class ParkSpaceController extends BaseController {

	@Autowired
	private ParkSpaceService parkSpaceService;
	@Autowired
	private ParkPayRuleService parkPayRuleService;
	@Autowired
	private ParkDoorControlService parkDoorControlService;
	@Autowired
	private ParkPayService parkPayService;

	@ModelAttribute
	public ParkSpace get(@RequestParam(required = false) String id) {
		ParkSpace entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = parkSpaceService.get(id);
		}
		if (entity == null) {
			entity = new ParkSpace();
		}
		return entity;
	}

	@RequiresPermissions("park:parkSpace:view")
	@RequestMapping(value = { "list", "" })
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		// Page<ParkSpace> page = parkSpaceService.findPage(new
		// Page<ParkSpace>(request, response), parkSpace);
		// model.addAttribute("page", page);
		List<IdleParkSpaceDto> dtoList = parkSpaceService.findIdleParkSpacePage();
		model.addAttribute("dtoList", dtoList);
		return "modules/park/parkSpaceList";
	}

	@RequiresPermissions("park:parkSpace:view")
	@RequestMapping(value = "pay")
	public String payList(ParkSpace parkSpace, HttpServletRequest request, HttpServletResponse response, Model model) {
		parkSpace.setIsuse("1");
		Page<ParkSpace> page = parkSpaceService.findPage(new Page<ParkSpace>(request, response), parkSpace);
		List<ParkSpace> list = page.getList();
		ParkPayRule ppr = parkPayRuleService.get("1");
		for (int i = 0; i < list.size(); i++) {
			ParkSpace ps = list.get(i);
			Date start = ps.getUpdateDate();
			Date end = new Date();
			long times = end.getTime() - start.getTime();
			ps.setTimes(times / 60000);
			ps.setPay((int) (times / (Integer.valueOf(ppr.getPeriod()) * 60000) + 1) * Integer.valueOf(ppr.getPrice()));
		}
		model.addAttribute("page", page);
		return "modules/park/inUseList";
	}

	@RequiresPermissions("park:parkSpace:view")
	@RequestMapping(value = "form")
	public String form(ParkSpace parkSpace, Model model) {
		if("0".equals(parkSpace.getIsuse())) {
			parkSpace.setIsuse("1");
		}else {
			parkSpace.setIsuse("0");
		}
		model.addAttribute("parkSpace", parkSpace);
		return "modules/park/parkSpaceForm";
	}

	@RequiresPermissions("park:parkSpace:edit")
	@RequestMapping(value = "save")
	public String save(ParkSpace parkSpace, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parkSpace)) {
			return form(parkSpace, model);
		}
		
		parkSpace.setIsuse("1");
		parkSpaceService.save(parkSpace);
		ParkDoorControl parkDoorControl = new ParkDoorControl();
		parkDoorControl.setFloor(parkSpace.getFloor());
		parkDoorControl.setIsuse("1");
		parkDoorControl.setJiffyStand(parkSpace.getJiffyStand());
		parkDoorControl.setSpace(parkSpace.getSpace());
		parkDoorControl.setDoorControlId("人工");
		parkDoorControl.setNumber(parkSpace.getNumber());
		parkDoorControlService.save(parkDoorControl);
		addMessage(redirectAttributes, "保存停车位成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkSpace/?repage";
	}

	@RequiresPermissions("park:parkSpace:edit")
	@RequestMapping(value = "getCar")
	public String getCar(ParkSpace parkSpace, Model model, RedirectAttributes redirectAttributes) {
		// 费用记录
		ParkPayRule ppr = parkPayRuleService.get("1");
		ParkPay pp = new ParkPay();
		pp.setFloor(parkSpace.getFloor());
		pp.setRemarks("0");
		pp.setJiffyStand(parkSpace.getJiffyStand());
		pp.setSpace(parkSpace.getSpace());
		pp.setNumber(parkSpace.getNumber());
		pp.setStartDate(parkSpace.getUpdateDate());
		pp.setEndDate(new Date());
		long times = pp.getEndDate().getTime() - pp.getStartDate().getTime();
		pp.setCost(
				(int) (times / (Integer.valueOf(ppr.getPeriod()) * 60000) + 1) * Integer.valueOf(ppr.getPrice()) + "");
		parkPayService.save(pp);
		// 日志记录
		ParkDoorControl parkDoorControl = new ParkDoorControl();
		parkDoorControl.setDoorControlId("人工");
		parkDoorControl.setIsuse("0");
		parkDoorControl.setNumber(parkSpace.getNumber());
		parkDoorControl.setFloor(parkSpace.getFloor());
		parkDoorControl.setJiffyStand(parkSpace.getJiffyStand());
		parkDoorControl.setSpace(parkSpace.getSpace());
		parkDoorControlService.save(parkDoorControl);
		// 释放车位
		parkSpace.setIsuse("0");
		parkSpace.setNumber("");
		parkSpaceService.save(parkSpace);

		return "redirect:" + Global.getAdminPath() + "/park/parkPay/list?repage";
	}

	@RequiresPermissions("park:parkSpace:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkSpace parkSpace, RedirectAttributes redirectAttributes) {
		parkSpaceService.delete(parkSpace);
		addMessage(redirectAttributes, "删除停车位成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkSpace/?repage";
	}
	
	

}