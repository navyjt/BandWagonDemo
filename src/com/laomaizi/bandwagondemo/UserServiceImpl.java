package com.laomaizi.bandwagondemo;

/**
 * 
 * @author Tony
 * UserService接口的实现类，实现了UserService的具体业务
 * 这里主要是登录和注册业务
 * 使用mysql数据库来实现登录和注册
 * conn.ini为数据库连接文件，使用中根据数据库在远端vps上还是本地，
 * 需要导入不同的文件
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;
import com.laomaizi.bandwagondemo.ServiceRulesException;


public class UserServiceImpl implements UserService {
	protected static final String TAG = "BandWagon";
	private static final boolean ISREMOTE = true;
	@Override
	public void userLogin(String loginName, String loginPassword)
			throws Exception {

		URL url = null;
		HttpURLConnection urlConnection = null;

		Thread.sleep(500);

		Log.d(TAG, "====开始打开URL");
		if(ISREMOTE){
			
			url = new URL("http://104.224.144.115/tony/login.do");
		}
		else {
			
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/login.do");
		}
		urlConnection = (HttpURLConnection) url.openConnection();
		// 设置请求的超时时间
		urlConnection.setConnectTimeout(3000);
		// 设置响应的超时时间
		urlConnection.setReadTimeout(3000);
		 // 设置请求的头  
        urlConnection.setRequestProperty("Connection", "keep-alive");  
        // 设置请求的头  
        urlConnection.setRequestProperty("Content-Type",  
                "application/x-www-form-urlencoded");  
        // 设置请求的头  
        String content = "name="+ URLEncoder.encode(loginName, "UTF-8") +
        		"&password="+URLEncoder.encode(loginPassword, "UTF-8");
        Log.d(TAG, "=========="+content+"------------");
        urlConnection.setRequestProperty("Content-Length",  
                String.valueOf(content.getBytes().length));  
        // 设置请求的头  
        urlConnection  
                .setRequestProperty("User-Agent",  
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  

		// 读数据
		urlConnection.setDoInput(true);
		// 写数据
		urlConnection.setDoOutput(true);
		// 设置请求为post方法
		urlConnection.setRequestMethod("POST");
		// Post请求不能使用缓存
		urlConnection.setUseCaches(false);

		urlConnection.connect();
		Log.d(TAG, "====连接完成======");

		OutputStream out = urlConnection.getOutputStream();
		//out = (DataOutputStream) urlConnection.getOutputStream();
		Log.d(TAG, "====初始化outputstream======");


		out.write(content.getBytes());
		out.flush();
	
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
     
       // 获取响应的输入流对象  
            InputStream is = urlConnection.getInputStream();  
            // 创建字节输出流对象  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            // 定义读取的长度  
            int len = 0;  
            // 定义缓冲区  
            byte buffer[] = new byte[1024];  
            // 按照缓冲区的大小，循环读取  
            while ((len = is.read(buffer)) != -1) {  
                // 根据读取的长度写入到os对象中  
                baos.write(buffer, 0, len);  
            }  
            // 释放资源  
            is.close();  
            baos.close();  
            // 返回字符串  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("登录成功")) {
    			
    			System.out.println("success");

    		} else {
    			System.out.println("failed");
    			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_ERROR);
    		}

     

        } else {  
            System.out.println("链接失败.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}
	}
	@Override
	public void userRegister(String regName, String regPassword, String regEmail)
			throws Exception {
		URL url = null;
		HttpURLConnection urlConnection = null;

		Thread.sleep(500);

		if(ISREMOTE){
			
			url = new URL("http://104.224.144.115/tony/register.do");
		}
		else {
			
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/register.do");
		}


		Log.d(TAG, "====打开URL完成======");
		urlConnection = (HttpURLConnection) url.openConnection();
		// 设置请求的超时时间
		urlConnection.setConnectTimeout(3000);
		// 设置响应的超时时间
		urlConnection.setReadTimeout(3000);
		 // 设置请求的头  
        urlConnection.setRequestProperty("Connection", "keep-alive");  
        // 设置请求的头  
        urlConnection.setRequestProperty("Content-Type",  
                "application/x-www-form-urlencoded");  
        // 设置请求的头  
        String content = "name="+ URLEncoder.encode(regName, "UTF-8") +
        		"&password="+URLEncoder.encode(regPassword, "UTF-8")+
        		"&email="+URLEncoder.encode(regEmail, "UTF-8");
        Log.d(TAG, "=========="+content+"------------");
        urlConnection.setRequestProperty("Content-Length",  
                String.valueOf(content.getBytes().length));  
        // 设置请求的头  
        urlConnection  
                .setRequestProperty("User-Agent",  
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  

		// 读数据
		urlConnection.setDoInput(true);
		// 写数据
		urlConnection.setDoOutput(true);
		// 设置请求为post方法
		urlConnection.setRequestMethod("POST");
		// Post请求不能使用缓存
		urlConnection.setUseCaches(false);

		urlConnection.connect();
		Log.d(TAG, "====连接完成======");

		OutputStream out = urlConnection.getOutputStream();
		//out = (DataOutputStream) urlConnection.getOutputStream();
		Log.d(TAG, "====初始化outputstream======");


		out.write(content.getBytes());
		out.flush();
	
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
     
       // 获取响应的输入流对象  
            InputStream is = urlConnection.getInputStream();  
            // 创建字节输出流对象  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            // 定义读取的长度  
            int len = 0;  
            // 定义缓冲区  
            byte buffer[] = new byte[1024];  
            // 按照缓冲区的大小，循环读取  
            while ((len = is.read(buffer)) != -1) {  
                // 根据读取的长度写入到os对象中  
                baos.write(buffer, 0, len);  
            }  
            // 释放资源  
            is.close();  
            baos.close();  
            // 返回字符串  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("注册用户成功")) {
    			
    			System.out.println("success");

    		} else {
    			System.out.println("failed");
    			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_ERROR);
    		}

     

        } else {  
            System.out.println("链接失败.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}
		
	}
	@Override
	//用户头像上传功能@20160417
	public String userPortraitUpload(InputStream in, Map<String, String> data,String pathName) throws Exception {
		// TODO Auto-generated method stub
				HttpClient client = new DefaultHttpClient();
				URL url = null;

				if(ISREMOTE){
					
					url = new URL("http://104.224.144.115/tony/uploadportrait.do");
				}
				else {
					
					url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/uploadportrait.do");
				}

				HttpPost post = new HttpPost(url.toString());
				// 要把数据封装进post里

				MultipartEntityBuilder builder = MultipartEntityBuilder.create();

				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.setCharset(Charset.forName("UTF-8"));
				for (Map.Entry<String, String> entry : data.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					builder.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN));
				}

				File newFile = new File(pathName);
				FileBody fileBody = new FileBody(newFile);
				builder.addPart("image", fileBody);
				HttpEntity httpEntity = builder.build();

				post.setEntity(httpEntity);

				HttpResponse response = client.execute(post);

				int statuscode = response.getStatusLine().getStatusCode();

				if (statuscode != HttpStatus.SC_OK) {
					throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
				}

				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

				return result;

		
	/*	Log.d(TAG, "开始图片上传");
		URL url = null;
		HttpURLConnection urlConnection = null;
		String successString = null;
		if(ISREMOTE){
			url = new URL("http://104.224.144.115/tony/uploadportrait.do");
		}
		else {
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/uploadportrait.do");
		}

		Log.d(TAG, "图片上传url为"+url.toString());
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(3000);
		urlConnection.setReadTimeout(3000);
         允许Input、Output，不使用Cache 
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
         设置传送的method=POST 
        urlConnection.setRequestMethod("POST");
         setRequestProperty 
        urlConnection.setRequestProperty("Connection", "Keep-Alive");
        urlConnection.setRequestProperty("Charset", "UTF-8");
       // File file = new File(imgPath);
        //======================
        urlConnection.setRequestProperty("Content-Type",
                           "multipart/form-data; boundary=--------------laomaizi");
        OutputStream out = new DataOutputStream(urlConnection.getOutputStream());

        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
        	out.write(bufferOut, 0, bytes);
        }
        in.close();
        out.flush();
        //完成文件的上传
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(UserSettingActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
            InputStream is = urlConnection.getInputStream();  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int len = 0;  
            byte buffer[] = new byte[1024];  
            while ((len = is.read(buffer)) != -1) {  
                baos.write(buffer, 0, len);  
            }  
            is.close();  
            baos.close();  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("上传图片成功")) {
    			
    			System.out.println("success");
    			successString= "上传图片成功";


    		} else {
    			System.out.println("failed");
    			successString= "上传图片失败";
    			throw new ServiceRulesException(UserSettingActivity.MSG_UPLOAD_USER_PORTRAIT_ERROR);
    		}

        } else {  
            System.out.println("链接失败.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}

		return successString;
		// TODO Auto-generated method stub
		
*/	}
}
