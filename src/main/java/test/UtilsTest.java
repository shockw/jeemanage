package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.reache.jeemanage.common.utils.FtpUtil;
import com.reache.jeemanage.common.utils.IdGen;
import com.reache.jeemanage.modules.park.api.ParkAPI;

public class UtilsTest {
	public static final String LOCAL_DIR = "/Users/shock/Downloads/";
	public static final String SUCCESS_RESULT = "\"{\\\"result\\\":1,\\\"success\\\":true}\"";
	public static final String ERROR_RESULT = "\"{\\\"result\\\":0,\\\"success\\\":false}\"";
	public static final String IN_URL = "http://192.168.4.108:8090";
	public static final String OUT_URL = "http://192.168.4.109:8090";
	public static final String FTP_IP = "192.168.4.108";
	public static final int FTP_PORT = 8010;
	private static final String[] ROUTE_KEY = new String[] { "OIP-SrvFault", "OIP-ServCode", "OIP-EsbId", "OIP-MsgId",
			"OIP-Sender", "OIP-Resender", "OIP-ResendFlag", "OIP-TransId", "OIP-AtomFlag", "OIP-AUTHCODE",
			"OIP-ParentEsbId", "OIP-AtomServAddress", "OIP-AppId", "OIP-APPSRCRET", "OIP-RESENDID" };
	private static final String PREFIX_OIP_UPPERCASE = "OIP-";
	private static final String PREFIX_OIP_LOWERCASE = "Oip-";

	public static void main(String[] args) {
//		String ftpHost = "192.168.4.108";
//        int ftpPort = 8010;
//		FtpUtil.downloadFtpFile(ftpHost, ftpPort, "/faceRegister", "/Users/shock/Downloads", "458c536e57884d37bb7730c3d7f9dfb1_da255292750d4ec69b59c20e2457f036.jpg");
//		
//	String str = "ftp://192.168.4.108:8010/faceRegister/458c536e57884d37bb7730c3d7f9dfb1_da255292750d4ec69b59c20e2457f036.jpg";
//	System.out.println(str.substring(str.indexOf("faceRegister")+13));
//	
//	String base64code = ParkAPI.getImageStr("/Users/shock/Downloads/1479b096f87b44eb957714c579cfe52e_ad2681cccbe941a1af8e6bf1f1491593.jpg");
//	System.out.println(base64code);
//		FtpUtil.downloadFtpFile(ftpHost, ftpPort, "/faceRegister", "/Users/shock/Downloads",
//				"3b3989690a964afd97d1bba9eae1cc48_dd61def3e3864967ace3c4f7cf975d19.jpg");
//		String str = "ftp://192.168.5.101:8010/recordsImg/2018-02-28/00100_1519786791566.jpg";
//		String ss[] = str.split("/");
//		System.out.println( "/recordsImg/"+ss[4]);
//		String newImagePath = "ftp://192.168.4.108:8010/faceRegister/3cbbcf79b56d4d8d91832d4cd8886333_a8f42bb1ce63479bb919d6aed5384f0e.jpg";
//		FtpUtil.downloadFtpFile(FTP_IP, FTP_PORT, "/faceRegister", LOCAL_DIR,
//				newImagePath.substring(newImagePath.indexOf("faceRegister") + 13));
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentSpanId", "1111");
		map.put("sampledFlag", "1");
		map.put("debugFlag", "1");
		map.put("x-real-ip", "127.0.0.01");
		map.put("oip-sender", "1101.07");
		Map<String, String> map1 = httpHeaderIgnoreCase(map);
		System.out.println(map1);
	}

	public static Map<String, String> httpHeaderIgnoreCase(Map<String, String> httpHeader) {
		// 遍历时不能在原map操作，只能新建。
		List<String> delKeys = new ArrayList<String>();
		Map<String, String> addheader = new HashMap<String, String>();
		for (String _key : httpHeader.keySet()) {
			String upperKey = _key.toUpperCase();
			if (upperKey.startsWith(PREFIX_OIP_UPPERCASE)) {
				for (String key : ROUTE_KEY) {
					if (key.equalsIgnoreCase(_key)) {
						addheader.put(key, httpHeader.get(_key));
						delKeys.add(_key);
					}
				}
			}
		}
		for (String delkey : delKeys) {
			httpHeader.remove(delkey);
		}
		httpHeader.putAll(addheader);
		return httpHeader;
	}
}
