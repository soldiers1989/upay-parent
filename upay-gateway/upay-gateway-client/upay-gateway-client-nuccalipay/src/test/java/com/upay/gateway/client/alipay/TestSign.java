package com.upay.gateway.client.alipay;

import org.junit.Test;

import com.union.api.UnionUAC;
import com.union.connect.UnionStart;
import com.union.message.UnionMessage;

public class TestSign {
	 private static UnionUAC api = new UnionUAC();
	  
//	@Test
//	public void testServEA10() {
//		UnionMessage um = api.servEA10("4002507279");
//		System.out.println("响应码: " + um.getHeadField("responseCode"));
//		System.out.println("备注: " + um.getHeadField("responseRemark"));
//		System.out.println("algFlag:: " + um.getBodyField("algFlag"));
//		System.out.println("pkValue:: " + um.getBodyField("pkValue"));
//	}

	
//	@Test
//	public void testServEA11() {
//		UnionMessage um = api.servEA11("4002507279", "312424234", 0);
//		System.out.println("响应码: " + um.getHeadField("responseCode"));
//		System.out.println("备注: " + um.getHeadField("responseRemark"));
////		System.out.println("algFlag:: " + um.getBodyField("algFlag"));
////		System.out.println("pkValue:: " + um.getBodyField("pkValue"));
//		System.out.println("sign: " + um.getBodyField("sign"));
//	}
	
	@Test
	public void testServEA12() {
		UnionMessage um = api.servEA12("4002507279", "312424234", 0, "MEUCIQCmxpPlk/KXMcZ6tsuviGzYO4qkq9nE15zfdLmrqbqm6wIgbzqCk/YI5T8kkOwBDshRLjrorfMQ9GDvZlJcM5FALWk=");
		System.out.println("响应码: " + um.getHeadField("responseCode"));
		System.out.println("备注: " + um.getHeadField("responseRemark"));
		System.out.println("algFlag:: " + um.getBodyField("algFlag"));
		System.out.println("pkValue:: " + um.getBodyField("pkValue"));
	}
	
}
