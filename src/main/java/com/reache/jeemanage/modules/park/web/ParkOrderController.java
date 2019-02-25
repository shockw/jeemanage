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
import com.reache.jeemanage.modules.park.entity.ParkOrder;
import com.reache.jeemanage.modules.park.service.ParkOrderService;

/**
 * 停车订单Controller
 * @author shockw
 * @version 2019-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkOrder")
public class ParkOrderController extends BaseController {

	@Autowired
	private ParkOrderService parkOrderService;
	
	@ModelAttribute
	public ParkOrder get(@RequestParam(required=false) String id) {
		ParkOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parkOrderService.get(id);
		}
		if (entity == null){
			entity = new ParkOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("park:parkOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParkOrder parkOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParkOrder> page = parkOrderService.findPage(new Page<ParkOrder>(request, response), parkOrder); 
		model.addAttribute("page", page);
		return "modules/park/parkOrderList";
	}

	@RequiresPermissions("park:parkOrder:view")
	@RequestMapping(value = "form")
	public String form(ParkOrder parkOrder, Model model) {
		model.addAttribute("parkOrder", parkOrder);
		return "modules/park/parkOrderForm";
	}

	@RequiresPermissions("park:parkOrder:edit")
	@RequestMapping(value = "save")
	public String save(ParkOrder parkOrder, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, parkOrder)){
//			return form(parkOrder, model);
//		}
		parkOrderService.save(parkOrder);
		addMessage(redirectAttributes, "保存停车订单成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkOrder/?repage";
	}
	
	@RequiresPermissions("park:parkOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkOrder parkOrder, RedirectAttributes redirectAttributes) {
		parkOrderService.delete(parkOrder);
		addMessage(redirectAttributes, "删除停车订单成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkOrder/?repage";
	}

}