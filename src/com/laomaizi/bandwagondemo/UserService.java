package com.laomaizi.bandwagondemo;

import java.io.InputStream;
import java.util.Map;

/**
 * 
 * @author Tony
 * @20160409
 * 用户业务逻辑的接口，然后使用userserviceimpl类来继承这个接口，这样可以方便的实现重载
 * 即可以定义不同的userservice对象来继承同一个UserService类，而实现不同的接口，比如
 *  UserService userservice = new UserServiceImpl();
 *  UserService userserviceanother = new UserServiceAnotherImpl();
 *  
 *
 */
public interface UserService {
	public void userLogin(String loginName, String loginPassword)
			throws Exception;

	public void userRegister(String regName, String regPassword, String regEmail)
			throws Exception;
	
	public String userPortraitUpload(InputStream in,Map<String, String> data, String pathName ) 
			throws Exception;


}
