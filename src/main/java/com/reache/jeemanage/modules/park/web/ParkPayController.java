/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

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
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.service.ParkPayService;

/**
 * 停车费用Controller
 * @author shock
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkPay")
public class ParkPayController extends BaseController {

	@Autowired
	private ParkPayService parkPayService;
	
	@ModelAttribute
	public ParkPay get(@RequestParam(required=false) String id) {
		ParkPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parkPayService.get(id);
		}
		if (entity == null){
			entity = new ParkPay();
		}
		return entity;
	}
	
	@RequiresPermissions("park:parkPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParkPay parkPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParkPay> page = parkPayService.findPage(new Page<ParkPay>(request, response), parkPay); 
		model.addAttribute("page", page);
		return "modules/park/parkPayList";
	}

	@RequiresPermissions("park:parkPay:view")
	@RequestMapping(value = "form")
	public String form(ParkPay parkPay, Model model) {
		model.addAttribute("parkPay", parkPay);
		return "modules/park/parkPayForm";
	}

	@RequiresPermissions("park:parkPay:edit")
	@RequestMapping(value = "save")
	public String save(ParkPay parkPay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parkPay)){
			return form(parkPay, model);
		}
		parkPayService.save(parkPay);
		addMessage(redirectAttributes, "保存停车费用成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkPay/?repage";
	}
	
	@RequiresPermissions("park:parkPay:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkPay parkPay, RedirectAttributes redirectAttributes) {
		parkPayService.delete(parkPay);
		addMessage(redirectAttributes, "删除停车费用成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkPay/?repage";
	}

}