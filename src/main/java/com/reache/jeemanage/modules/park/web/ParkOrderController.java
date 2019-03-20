/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reache.jeemanage.common.config.Global;
import com.reache.jeemanage.common.persistence.Page;
import com.reache.jeemanage.common.web.BaseController;
import com.reache.jeemanage.common.utils.StringUtils;
import com.reache.jeemanage.modules.park.entity.ParkOrder;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.entity.ParkPayRule;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.service.ParkOrderService;

/**
 * 停车订单Controller
 * 
 * @author shockw
 * @version 2019-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkOrder")
public class ParkOrderController extends BaseController {

	@Autowired
	private ParkOrderService parkOrderService;

	@ModelAttribute
	public ParkOrder get(@RequestParam(required = false) String id) {
		ParkOrder entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = parkOrderService.get(id);
		}
		if (entity == null) {
			entity = new ParkOrder();
		}
		return entity;
	}

	@RequiresPermissions("park:parkOrder:view")
	@RequestMapping(value = { "list", "" })
	public String list(ParkOrder parkOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParkOrder> pop = new Page<ParkOrder>(request, response);
		Page<ParkOrder> page = parkOrderService.findPage(pop, parkOrder);
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
	public String save(ParkOrder parkOrder, @RequestParam("file") MultipartFile file,
			@RequestParam("file1") MultipartFile file1, Model model, RedirectAttributes redirectAttributes) {
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			String data = encoder.encode(file.getBytes());
			String data1 = encoder.encode(file1.getBytes());
			parkOrder.setInPic(data);
			parkOrder.setOutPic(data1);
			parkOrderService.save(parkOrder);
			addMessage(redirectAttributes, "保存停车订单成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + Global.getAdminPath() + "/park/parkOrder/list?repage";
	}

	@RequiresPermissions("park:parkOrder:edit")
	@RequestMapping(value = { "pay", "" })
	public String pay(HttpServletRequest request, HttpServletResponse response, Model model) {
		ParkOrder parkOrder = new ParkOrder();
		Page<ParkOrder> pop = new Page<ParkOrder>(request, response);
		pop.setOrderBy("endTime asc");
		parkOrder.setStatus("3");
		Page<ParkOrder> page = parkOrderService.findPage(pop, parkOrder);
		List<ParkOrder> list = page.getList();
		if (list.size() > 0) {
			model.addAttribute("message", "请扫码付款！");
			model.addAttribute("parkOrder", list.get(0));
		} else {
			model.addAttribute("message", "暂时没有符合条件的账单");
		}
		return "modules/park/parkOrderPayList";
	}

	@RequiresPermissions("park:parkOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkOrder parkOrder, RedirectAttributes redirectAttributes) {
		parkOrderService.delete(parkOrder);
		addMessage(redirectAttributes, "删除停车订单成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkOrder/list?repage";
	}

}