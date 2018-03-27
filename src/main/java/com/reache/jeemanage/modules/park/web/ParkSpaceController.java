/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

import java.util.Date;
import java.util.List;

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
import com.reache.jeemanage.modules.park.entity.ParkSpace;
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
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// Page<ParkSpace> page = parkSpaceService.findPage(new
		// Page<ParkSpace>(request, response), parkSpace);
		// model.addAttribute("page", page);
		List<IdleParkSpaceDto> dtoList = parkSpaceService
				.findIdleParkSpacePage();
		model.addAttribute("dtoList", dtoList);
		return "modules/park/parkSpaceList";
	}

	@RequiresPermissions("park:parkSpace:view")
	@RequestMapping(value ="pay")
	public String payList(ParkSpace parkSpace,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ParkSpace> page = parkSpaceService.findPage(new Page<ParkSpace>(
				request, response), parkSpace);
		List<ParkSpace> list = page.getList();
		for(int i =0;i<list.size();i++){
			ParkSpace ps = list.get(i);
			Date start = ps.getUpdateDate();
			Date end = new Date();
			long times = end.getTime()-start.getTime();
			ps.setTimes(times/60000);
			ps.setPay((int)(times/1800000+1)*30);
		}
		model.addAttribute("page", page);
		return "modules/park/inUseList";
	}

	@RequiresPermissions("park:parkSpace:view")
	@RequestMapping(value = "form")
	public String form(ParkSpace parkSpace, Model model) {
		model.addAttribute("parkSpace", parkSpace);
		return "modules/park/parkSpaceForm";
	}

	@RequiresPermissions("park:parkSpace:edit")
	@RequestMapping(value = "save")
	public String save(ParkSpace parkSpace, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parkSpace)) {
			return form(parkSpace, model);
		}
		parkSpaceService.save(parkSpace);
		addMessage(redirectAttributes, "保存停车位成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkSpace/?repage";
	}

	@RequiresPermissions("park:parkSpace:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkSpace parkSpace,
			RedirectAttributes redirectAttributes) {
		parkSpaceService.delete(parkSpace);
		addMessage(redirectAttributes, "删除停车位成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkSpace/?repage";
	}

}