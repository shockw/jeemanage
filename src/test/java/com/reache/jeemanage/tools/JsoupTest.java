package com.reache.jeemanage.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {
	public static void main(String[] args) throws IOException {
		 // 第一次请求
        Connection con = Jsoup
                .connect("http://passport.kdgcsoft.com/login?service=http%3A%2F%2F60.174.249.206%3A9999%2Fin%2Fmodules%2Flogin%2Fcas%2Fsso.php");// 获取连接
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        List<Element> et = d1.select("#fm1");
        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<>();
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("username")) {
                e.attr("value", "wang.zhen");// 设置用户名
            }
            if (e.attr("name").equals("password")) {
                e.attr("value", "zwang2017"); // 设置用户密码
            }
            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }
        //提交表单进行登陆
        Connection con2 = Jsoup
                .connect("http://passport.kdgcsoft.com/login");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map = login.cookies();
        //获取流程共享数据
        Connection con3 = Jsoup
                .connect("http://60.174.249.206:9999/in/modules/xoa/wf_qry_h.php");
        con3.cookies(map);
        con3.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Map<String, String> datas3 = new HashMap<>();
        datas3.put("time1", "2019-03-02");
        datas3.put("time2", "2019-04-02");
        datas3.put("flow_post","提交");
        con3.data(datas3);
        Document doc = con3.post();  
        Elements  ees =  doc.select("tr.odd");
        for(int i=0;i<ees.size();i++) {
        	System.out.println(ees.get(i).select("td").text());
        }
        
        
    }
}
