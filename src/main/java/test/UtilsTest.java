package test;

import java.util.Random;

import com.reache.jeemanage.common.utils.FtpUtil;
import com.reache.jeemanage.common.utils.IdGen;
import com.reache.jeemanage.modules.park.api.ParkAPI;

public class UtilsTest {
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
		String str = "ftp://192.168.5.101:8010/recordsImg/2018-02-28/00100_1519786791566.jpg";
		String ss[] = str.split("/");
		System.out.println( "/recordsImg/"+ss[4]);
	}
}
