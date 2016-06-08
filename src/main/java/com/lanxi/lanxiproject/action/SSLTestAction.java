package com.lanxi.lanxiproject.action;

import com.lanxi.httpsclient.core.ESignature;
import com.lanxi.httpsclient.core.HttpsClient;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


@RestController
@RequestMapping("/ssl")
public class SSLTestAction {

	Logger logger = Logger.getLogger(TestAction.class);
	
	
	@RequestMapping("/qry")
	public boolean qry(HttpServletRequest request) throws Exception{
		
		//请求地址
		String url = "https://123.59.152.23:8441/httpstest/https/test.do";
		//待签名数据
		String content = "<?xml version='1.0' encoding='UTF-8' ?><HTTPS><TEST>test</TEST>";
		
		
		logger.info("content:"+content);
		
		//经BASE64和URL转码后的签名
		String sig = ESignature.sign(content, "UTF-8");
		
		logger.info("sig:"+sig);;
		
		
		//请求报文
		String msg = content + "<SIGN>" + sig + "</SIGN></HTTPS>";
		
		logger.info("msg:"+msg);
		
		String result = "";
		//发送请求
		try {
		result = HttpsClient.sendData(msg, url, "UTF-8", 60000);
		logger.info("result:"+result);
		}catch (MalformedURLException e) {
			throw new RuntimeException("发送post请求出错,url:" + url, e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的字符charSet:" + e);
		} catch (IOException e) {
			throw new RuntimeException("发送post请求IO出错,url:" + url, e);
		//解析返回报文得到待验签名
		}
		
		String sign = XmlParser.getNodeString(XmlParser.getDocument(result), "/HTTPS/SIGN");
		logger.info("sign:"+sign);
		//验签
		boolean b = ESignature.verify("hzlxxxjsyxgs2016", sign, "UTF-8");
		
		


				
		return b;		
	}
	
}
