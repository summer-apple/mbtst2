package com.lanxi.lanxiproject.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanxi.lanxiproject.lib.LanxiProjectException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 所有的API接口通信都依赖这个数据结构
 * @author zhubinbin
 *
 */
public class LanxiProjectData {
	
	public LanxiProjectData() {
		
	}

	//方便排序
	private TreeMap<String, Object> m_values = new TreeMap<String, Object>();
	
	/**
	 * 设置字段值
	 * @param key
	 * 			字段名
	 * @param value
	 *          字段值
	 */
	public void setValue(String key, Object value) {
		m_values.put(key, value);
	}
	
	/**
	 * 取字段值
	 * @param key
	 * @return
	 */
	public Object getValue(String key) {
		return m_values.get(key);
	}
	
	/**
	 * 判断是否设置
	 * @param key
	 * @return
	 */
	public boolean isSet(String key) {
		return m_values.containsKey(key);
	}
	
	/**
	 * 转成xml
	 * @return
	 * @throws LanxiProjectException
	 */
	public String toXml() throws LanxiProjectException {
		
		//数据为空时不转换
		if (0 == m_values.size()) {
			throw new LanxiProjectException("LanxiProjectData数据为空");
		}
		
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		
		for (Entry<String, Object> pair : m_values.entrySet()) {
			//去掉字段值为空的数据
			if (pair.getValue() == null) {
				throw new LanxiProjectException("LanxiProjectData去掉字段值为空的数据");
			}
			
			String key = pair.getKey();
			Object value = pair.getValue();
			
			if (value instanceof Integer || value instanceof String) {
				xml.append("<").append(key).append(">").append(value).append("</")
				.append(key).append(">");
			} else {
				throw new LanxiProjectException("LanxiProjectData数据类型错误");
			}
		}
		
		xml.append("</xml>");
		
		return xml.toString();
	}
	
	/**
	 * 将xml转换成内部数据结构
	 * @param xml
	 * @return
	 * @throws LanxiProjectException
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 */
	public TreeMap<String, Object> fromXml(String xml) throws LanxiProjectException, 
	        ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException {

		//xml为空时不转换
		if (null == xml || xml.isEmpty()) {
			throw new LanxiProjectException("xml为空");
		}
		
		DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder doccumentBuilder = documentBuildFactory
				.newDocumentBuilder();
		Document document = doccumentBuilder.parse(new ByteArrayInputStream(xml
				.getBytes("UTF-8")));//编码格式可以修改
		
		Node xmlNode = document.getFirstChild();//获取到根节点<xml>
		NodeList nodes = xmlNode.getChildNodes();
		
		for (int i = 0, length = nodes.getLength(); i < length; i ++) {
			Node xn = nodes.item(i);
			
			if (xn instanceof Element) {
				Element xe = (Element) xn;
				m_values.put(xe.getNodeName(), xe.getTextContent());
			}
		}
		
		//如果有签名，取出签名字段并验签，否则直接返回
		//TODO
		//checkSign
		
		return m_values;
	}
	
	/**
	 * 转成json
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String toJson() throws JsonProcessingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonStr = mapper.writeValueAsString(m_values);
		
		return jsonStr;
	}
	
	/**
	 * 将json转成内部数据结构
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public TreeMap<String, Object> fromJson(String json) throws JsonParseException, 
	        JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		m_values = mapper.readValue(json, TreeMap.class);
		
		return m_values;
	}
	
	//生成签名--makeSign
	
	//验签--checkSign
	
	/**
	 * 获取
	 * @return
	 */
	public TreeMap<String, Object> getValues() {
		
		return m_values;
				
	}
}
