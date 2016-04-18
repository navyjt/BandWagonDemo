package com.laomaizi.bandwagondemo;
/**
 * 
 * @author Tony
 * @20160412
 *  服务异常 抛出message的类，自定义异常可以继承这个类
 */

public class ServiceRulesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4174634228008209943L;
	
	
	public ServiceRulesException(String message){
		
		super(message);
		
	}

}
