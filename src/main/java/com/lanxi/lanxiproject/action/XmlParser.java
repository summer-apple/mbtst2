package com.lanxi.lanxiproject.action;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class XmlParser {
	public static Document getDocumentFromFile(String xmlFileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(xmlFileName));
		return doc;
	}

	public static Document getDocument(String strXmlContent) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXmlContent);
		return doc;
	}

	public static Document getDocument(String strXmlContent, String strEncoding) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXmlContent);
		doc.setXMLEncoding(strEncoding);
		return doc;
	}

	public static Document getDocument(InputStream inStream) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inStream);
		return doc;
	}

	public static Document getDocument(InputStream inStream, String strEncoding) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inStream);
		doc.setXMLEncoding(strEncoding);
		return doc;
	}

	public static String getDocString(Document doc) {
		String xmlContent = "";
		xmlContent = doc.asXML();
		return xmlContent;
	}

	public static String getNodeString(Document doc, String strXmlPath) {
		Node node = doc.selectSingleNode(strXmlPath);
		if (node == null){
			return "";
		}
		return node.getText();
	}
	public static String getNodeXmlString(Document doc, String strXmlPath) {
		Node node = doc.selectSingleNode(strXmlPath);
		if (node == null){
			return "";
		}
		return node.asXML();
	}

	public static String getNodeString(Document doc, String strXmlPath, String strAttrName, String strAttrValue) {
		String strAttr = "";
		List<?> list = doc.selectNodes(strXmlPath);
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			Element elemParam = (Element) iter.next();
			Attribute attribute = elemParam.attribute(strAttrName);
			if (attribute == null){
				continue;
			}
			strAttr = attribute.getValue();
			if (strAttr.compareTo(strAttrValue) == 0){
				return elemParam.getText();
			}
		}
		return "";
	}
	public static String getNodeStringValue(Document doc, String strXmlPath, String strAttrName, String strAttrValue) {
		String strAttr = "";
		String valueString="";
		List<?> list = doc.selectNodes(strXmlPath);
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			Element elemParam = (Element) iter.next();
			Attribute attribute = elemParam.attribute(strAttrName);
			if (attribute == null){
				continue;
			}
			strAttr = attribute.getValue();
			if (strAttr.compareTo(strAttrValue) == 0){
				valueString=elemParam.attributeValue("value");
				return valueString;
			}
		}
		return "";
	}

	public static String getAttrString(Document doc, String strXmlPath) {
		String strAttr = "";
		Attribute attr = (Attribute) doc.selectSingleNode(strXmlPath);
		if (attr != null){
			strAttr = attr.getValue();
		}
		return strAttr;
	}

	public static boolean addAttribute(Document doc, String strXmlPath, String strElemName, String strElemValue, String strAttrName, String strAttrValue) {
		Node node = doc.selectSingleNode(strXmlPath);
		if (node == null){
			return false;
		}
		if (strElemName.trim().length() == 0){
			return false;
		}
		Element elem = (Element) node;
		Element elemAdd = elem.addElement(strElemName);
		elemAdd.setText(strElemValue);
		if (strAttrName.trim().length() > 0){
			elemAdd.addAttribute(strAttrName, strAttrValue);
		}
		return true;
	}

	public static boolean setNodeText(Document doc, String strXmlPath, String strNodeText) {
		Node node = doc.selectSingleNode(strXmlPath);
		if (node == null){
			return false;
		}
		node.setText(strNodeText);
		return true;
	}

	public static String formatXmlString(Document doc, String strEncoding) {
		XMLWriter writer = null;
		StringWriter sw = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(strEncoding);
		try {
			writer = new XMLWriter(format);
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
		writer.setWriter(sw);
		try {
			writer.write(doc);
		} catch (IOException ex1) {
			return null;
		}
		try {
			writer.close();
		} catch (IOException ex2) {
			return null;
		}
		return sw.toString();
	}
}