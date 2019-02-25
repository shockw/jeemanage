package test;

import java.util.Random;

import com.reache.jeemanage.common.utils.IdGen;

public class UtilsTest {
	public static void main(String[] args) {
		
		String personId = IdGen.uuid();
		String person = "{\"id\":\""+personId+"\",\"idcardNum\":\"\",\"name\":\""+personId+"\"}";
		System.out.println(person);
	}
}
