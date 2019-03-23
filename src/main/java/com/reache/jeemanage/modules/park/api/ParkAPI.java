package com.reache.jeemanage.modules.park.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reache.jeemanage.common.config.Global;
import com.reache.jeemanage.common.utils.FtpUtil;
import com.reache.jeemanage.common.utils.IdGen;
import com.reache.jeemanage.modules.park.entity.ParkJiffyStand;
import com.reache.jeemanage.modules.park.entity.ParkOrder;
import com.reache.jeemanage.modules.park.entity.ParkPay;
import com.reache.jeemanage.modules.park.entity.ParkPayRule;
import com.reache.jeemanage.modules.park.service.ParkJiffyStandService;
import com.reache.jeemanage.modules.park.service.ParkOrderService;
import com.reache.jeemanage.modules.park.service.ParkPayRuleService;
import com.reache.jeemanage.modules.park.service.ParkPayService;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping(value = "${adminPath}/park/api")
public class ParkAPI {
	private static Logger logger = LoggerFactory.getLogger(ParkAPI.class);
	public static final String LOCAL_DIR = "/Users/shock/Downloads/";
	public static final String SUCCESS_RESULT = "\"{\\\"result\\\":1,\\\"success\\\":true}\"";
	public static final String ERROR_RESULT = "\"{\\\"result\\\":0,\\\"success\\\":false}\"";
	public static final String IN_URL = "http://192.168.4.108:8090";
	public static final String OUT_URL = "http://192.168.4.109:8090";
	public static final String FTP_IP = "192.168.4.108";
	public static final String FTP_OUT = "192.168.4.109";
	public static final int FTP_PORT = 8010;

	@Autowired
	private ParkJiffyStandService parkJiffyStandService;
	@Autowired
	private ParkOrderService parkOrderService;
	@Autowired
	private ParkPayRuleService parkPayRuleService;
	@Autowired
	private ParkPayService parkPayService;

	@RequestMapping(value = { "index", "" })
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/park/index";
	}

	// 申请停车，占用车位，开始拍照
	@RequestMapping(value = "/park")
	public String park(HttpServletRequest request, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			// 占用车位
			ParkJiffyStand pjs = parkJiffyStandService.occupy();
			// 如果占用车位成功，则开始人脸注册
			if (pjs != null) {
				CloseableHttpClient httpclient = HttpClients.createDefault();
				String personId = IdGen.uuid();
				// 人员注册,需要注册两次
				HttpPost httpPost = new HttpPost(IN_URL + "/person/create");
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("pass", "88888888"));
				String personInfo = "{\"id\":\"" + personId + "\",\"idcardNum\":\"\",\"name\":\"" + personId + "\"}";
				nvps.add(new BasicNameValuePair("person", personInfo));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				httpclient.execute(httpPost);

				HttpPost httpPostO = new HttpPost(OUT_URL + "/person/create");
				httpPostO.setHeader("Content-Type", "application/x-www-form-urlencoded");
				List<NameValuePair> nvpsO = new ArrayList<NameValuePair>();
				nvpsO.add(new BasicNameValuePair("pass", "88888888"));
				nvpsO.add(new BasicNameValuePair("person", personInfo));
				httpPostO.setEntity(new UrlEncodedFormEntity(nvpsO));
				httpclient.execute(httpPostO);
				// 照片注册,注册一次，另外一个需要照片同步
				HttpPost httpPost1 = new HttpPost(IN_URL + "/face/takeImg");
				httpPost1.setHeader("Content-Type", "application/x-www-form-urlencoded");
				List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
				nvps1.add(new BasicNameValuePair("pass", "88888888"));
				nvps1.add(new BasicNameValuePair("personId", personId));
				httpPost1.setEntity(new UrlEncodedFormEntity(nvps1));
				httpclient.execute(httpPost1);

				// 形成订单
				ParkOrder po = new ParkOrder();
				po.setFloor(pjs.getFloor());
				po.setPersonId(personId);
				po.setJiffyStand(pjs.getJiffyStand());
				po.setStartTime(new Date());
				po.setStatus("0");
				parkOrderService.save(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "正在申请停车,请对准摄像头进行拍照!");
		return "redirect:" + Global.getAdminPath() + "/park/api/index?repage";

	}

	// 人脸注册拍照后进行回调，完成开门.
	@RequestMapping(value = "/order")
	@ResponseBody
	public String order(String personId, String newImgPath) {
		ParkOrder po = new ParkOrder();
		po.setPersonId(personId);
		List<ParkOrder> list = parkOrderService.findList(po);
		ParkOrder parkOrder = list.get(0);
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 将照片同步到出口机器上
			FtpUtil.downloadFtpFile(FTP_IP, FTP_PORT, "/faceRegister", LOCAL_DIR,
					newImgPath.substring(newImgPath.indexOf("faceRegister") + 13));
			String base64code = getImageStr(LOCAL_DIR + newImgPath.substring(newImgPath.indexOf("faceRegister") + 13));
			HttpPost httpPost1 = new HttpPost(OUT_URL + "/face/create");
			httpPost1.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
			nvps1.add(new BasicNameValuePair("pass", "88888888"));
			nvps1.add(new BasicNameValuePair("personId", personId));
			nvps1.add(new BasicNameValuePair("faceId", ""));
			nvps1.add(new BasicNameValuePair("imgBase64", base64code));
			System.out.println(nvps1);
			httpPost1.setEntity(new UrlEncodedFormEntity(nvps1));
			httpclient.execute(httpPost1);
			// 开门
			HttpPost httpPost = new HttpPost(IN_URL + "/device/openDoorControl");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("pass", "88888888"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpclient.execute(httpPost);
			// 更新订单状态为已存车，将拍照图片上传至数据库保存
			parkOrder.setPath(newImgPath);
			parkOrder.setStatus("1");
			parkOrder.setInPic(base64code);
			parkOrderService.save(parkOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS_RESULT;
	}

	// 申请入门，只有人脸注册的人才可以通过人脸识别进入，接口给进门的人脸识备使用
	@RequestMapping(value = "/enter")
	@ResponseBody
	public String enter(String personId) {
		if (!"STRANGERBABY".equals(personId)) {
			// 车子落架
			System.out.println("车辆所在层已经放到地面，可以进行取车！");
			ParkOrder po = new ParkOrder();
			po.setPersonId(personId);
			List<ParkOrder> list = parkOrderService.findList(po);
			ParkOrder parkOrder = list.get(0);
			try {
				// 开门
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(IN_URL + "/device/openDoorControl");
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("pass", "88888888"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				httpclient.execute(httpPost);
				// 更新订单状态为正在取车
				parkOrder.setStatus("2");
				parkOrderService.save(parkOrder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("---识别为陌生人，不允许开门---");
		}
		return SUCCESS_RESULT;
	}

	// 出门通过人脸识别生成账单，接口给出门的人脸设备使用
	@RequestMapping(value = "/bill")
	@ResponseBody
	public String bill(String personId, String path, String type) {
		// 识别成功
		if ("face_0".equals(type)) {
			// 费用记录
			System.out.println("---生成账单---");
			ParkPayRule ppr = parkPayRuleService.get("1");
			ParkOrder po = new ParkOrder();
			po.setPersonId(personId);
			List<ParkOrder> list = parkOrderService.findList(po);
			ParkOrder parkOrder = list.get(0);
			// 获取识别图片下载，并保存至数据库
			String ss[] = path.split("/");
			FtpUtil.downloadFtpFile(FTP_OUT, FTP_PORT, "/recordsImg/" + ss[4], LOCAL_DIR, ss[5]);
			String base64code = getImageStr(LOCAL_DIR + ss[5]);
			parkOrder.setOutPic(base64code);
			parkOrder.setEndTime(new Date());
			parkOrder.setStatus("3");
			long times = parkOrder.getEndTime().getTime() - parkOrder.getStartTime().getTime();
			parkOrder.setCost(
					(int) (times / (Integer.valueOf(ppr.getPeriod()) * 60000) + 1) * Integer.valueOf(ppr.getPrice())
							+ "");
			parkOrderService.save(parkOrder);

			// 没有成功识别
		} else {
			System.out.println("---人脸识别失败,无法生成账单----");
		}
		return SUCCESS_RESULT;
	}

	//扫码付款完成后回调此接口，开门
	@RequestMapping(value = "/pay")
	@ResponseBody
	public String pay(String personId) {
		ParkOrder po = new ParkOrder();
		po.setPersonId(personId);
		List<ParkOrder> list = parkOrderService.findList(po);
		ParkOrder parkOrder = list.get(0);
		parkOrder.setStatus("4");
		parkOrder.setPayTime(new Date());
		parkOrderService.save(parkOrder);
		try {
			//开门
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(OUT_URL + "/device/openDoorControl");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("pass", "88888888"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpclient.execute(httpPost);
			//删除入口设备和出口设备上的注册信息，
			HttpPost httpPost2 = new HttpPost(IN_URL + "/person/delete");
			httpPost2.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
			nvps2.add(new BasicNameValuePair("pass", "88888888"));
			nvps2.add(new BasicNameValuePair("id", personId));
			httpPost2.setEntity(new UrlEncodedFormEntity(nvps2));
			httpclient.execute(httpPost2);
			
			HttpPost httpPost1 = new HttpPost(OUT_URL + "/person/delete");
			httpPost1.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
			nvps1.add(new BasicNameValuePair("pass", "88888888"));
			nvps1.add(new BasicNameValuePair("id", personId));
			httpPost1.setEntity(new UrlEncodedFormEntity(nvps1));
			httpclient.execute(httpPost1);
		} catch (Exception e) {
		}
		return SUCCESS_RESULT;
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	private static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

}
