package com.lanxi.lanxiproject.lib;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http通信
 * @author Zhubinbin
 *
 */
public class HttpService {
	
	/**
	 * POST
	 * @param xml
	 * @param url
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws LanxiProjectException
	 */
	public static String post(String xml, String url, String charset, 
			int timeout) throws LanxiProjectException{
		
		System.gc();//回收没有正常关闭的链接
		
		String result = "";
		
		HttpURLConnection request = null;
		InputStream response = null;
		OutputStream reqStrem = null;
		
		try {
			//设置HttpWebRequest的相关属性
			if (url.toLowerCase().startsWith("https")) {
				HttpsURLConnection httpsRequest = (HttpsURLConnection) new URL(
						url).openConnection();
				
				request = httpsRequest;
					
			} else {
				request = (HttpURLConnection) new URL(url).openConnection();
			}
			
			request.setRequestMethod("POST");
			request.setDoOutput(true);
			request.setConnectTimeout(timeout*1000);
			
			//设置POST的数据类型和长度
			request.addRequestProperty("Content-Type", "text/xml");
			byte[] data = xml.getBytes(charset);
			
			//往服务器写数据
			reqStrem = request.getOutputStream();
			reqStrem.write(data, 0, data.length);
			reqStrem.flush();
			
			//获取服务器返回
			response = request.getInputStream();
			
			StringBuffer sb = new StringBuffer();
			byte[] d = new byte[4096];
			int chunk = 0;
			
			while((chunk = response.read(d)) != -1) {
				
				sb.append(new String(d, 0, chunk, charset));
			}
			
			result.toString();
		} catch (Exception e) {
			
			throw new LanxiProjectException(e.toString());
		} finally {
			//关闭连接和流
			if (reqStrem != null) {
				try {
					reqStrem.close();
				} catch (IOException e) {
					
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return result;
	}
	
	/**
	 * GET
	 * @param url
	 * @param charset
	 * @return
	 * @throws LanxiProjectException
	 */
	public static String get(String url, String charset) throws LanxiProjectException {
		
		System.gc();
		String result = "";
		
		HttpURLConnection request = null;
		InputStream response = null;
		
		try {
			
			request = (HttpURLConnection) new URL(url).openConnection();
			request.setRequestMethod("GET");
			
			//服务端返回
			response = request.getInputStream();
			
			StringBuffer sb = new StringBuffer();
			byte[] d = new byte[1024];
			int chunk = 0;
			
			while ((chunk = response.read(d)) != -1) {
				
				sb.append(new String(d, 0, chunk, charset));
			}
			
			result.toString();
		} catch (Exception e) {
			throw new LanxiProjectException(e.toString());
		} finally {
			//关闭连接和流
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return result;
	}

}
