/**
 * 
 */
package com.upay.commons.encryptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.upay.commons.encryptor.UnionAPI;

/**加密机测试  注入的方式调用
 * @author zhanggr
 * 
 */
public class UnionAPITest {

	public static void main(String[] args) throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"classpath*:META-INF/spring/bean/service-bean.xml");
//		UnionAPI unionAPI = (UnionAPI) context.getBean("unionAPI");
//		unionAPI.getShortConnection();
		//注入
//		String s = unionAPI.encrypt("123456");
		//System.out.println(s);
//		String t = unionAPI.decipher(s);
		//System.out.println(t);
		
//		String a=unionAPI.encryptWT("4ab53feaee245ffd907ce76248a718d84f80a9ccf32082f88a87796bdfa24fde9b85794553e80a2f6a2ab897c4e75ca5ec215bb15335a839c737d2980cc25071dbf02a02f9ff24c6d05f1a6bcd8e2b582ff98be8d22e275467f982c1834cde33485aaf005cd4".toUpperCase());
//		System.out.println(unionAPI.encrypt(a));
	}
}
