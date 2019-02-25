/**
 * Copyright &copy; 2012-2016 <a href="http://shockw.github.io/">JeeManage</a> All rights reserved.
 */
package com.reache.jeemanage.modules.park.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
import com.reache.jeemanage.modules.park.api.ParkAPI;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.entity.ParkPayRule;
import com.reache.jeemanage.modules.park.entity.ParkSpace;
import com.reache.jeemanage.modules.park.service.ParkPayRuleService;
import com.reache.jeemanage.modules.park.service.ParkPayService;
import com.reache.jeemanage.modules.park.service.ParkSpaceService;

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
	@Autowired
	private ParkSpaceService parkSpaceService;
	@Autowired
	private ParkPayRuleService parkPayRuleService;
	
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
		parkPay.setRemarks("0");
		Page<ParkPay> page = parkPayService.findPage(new Page<ParkPay>(request, response), parkPay); 
		model.addAttribute("page", page);
		
		ParkSpace parkSpace = new ParkSpace();
		parkSpace.setIsuse("1");
		parkSpace.setJiffyStand(parkPay.getJiffyStand());
		parkSpace.setFloor(parkPay.getFloor());
		Page<ParkSpace> page1 = parkSpaceService.findPage(new Page<ParkSpace>(request, response), parkSpace);
		List<ParkSpace> list = page1.getList();
		ParkPayRule ppr = parkPayRuleService.get("1");
		for (int i = 0; i < list.size(); i++) {
			ParkSpace ps = list.get(i);
			Date start = ps.getUpdateDate();
			Date end = new Date();
			long times = end.getTime() - start.getTime();
			ps.setTimes(times / 60000);
			ps.setPay((int) (times / (Integer.valueOf(ppr.getPeriod()) * 60000) + 1) * Integer.valueOf(ppr.getPrice()));
		}
		model.addAttribute("page1", page1);
		return "modules/park/parkPayList";
	}
	
	@RequiresPermissions("park:parkPay:view")
	@RequestMapping(value = {"history", ""})
	public String history(ParkPay parkPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		parkPay.setRemarks("1");
		Page<ParkPay> page = parkPayService.findPage(new Page<ParkPay>(request, response), parkPay); 
		model.addAttribute("page", page);
		return "modules/park/parkPayHistoryList";
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
	
	@RequiresPermissions("park:parkPay:edit")
	@RequestMapping(value = "paymark")
	public String paymark(ParkPay parkPay,  RedirectAttributes redirectAttributes) {
		// 开门
		try {
//			CloseableHttpClient httpclient = HttpClients.createDefault();
//			HttpPost httpPost = new HttpPost(ParkAPI.OUT_URL + "/device/openDoorControl");
//			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			nvps.add(new BasicNameValuePair("pass", "88888888"));
//			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//			httpclient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		parkPay.setRemarks("1");
		parkPayService.save(parkPay);
		addMessage(redirectAttributes, "标记缴费成功");
		return "redirect:" + Global.getAdminPath() + "/park/parkPay/list?repage";
	}

}