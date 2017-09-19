package com.javmin.main;

import java.util.ArrayList;
import java.util.List;

public class TagNode {
	
	private String tagName;
	private String tagValue;
	private List<TagNode> children=new ArrayList<>();
	private List<Attribute> attributes = new ArrayList<>();
	private static class Attribute{
		String name;
		String value;
	}
	
	public TagNode(String name){
		this.tagName=name;
	}
	
	class TagBuilder{
		
		
	}
	

	public String toXml(){
		return getStrVlaue(this,"");
	}
	
	private String toXmlPrefix(String tagName,String value,List<Attribute> attributes){
		String attributeStr="";
		for (Attribute attribute : attributes) {
			attributeStr+=" "+attribute.name+"=\""+attribute.value+"\"";
		}
		return "<"+tagName+attributeStr+">"+value+"</"+tagName+">";
	}
	
	private String getStrVlaue(TagNode node,String result){
		List<TagNode> nodes = node.children;
		if(nodes==null||nodes.size()==0){
		  return toXmlPrefix(node.tagName,node.tagValue,node.attributes);
		}
		for (TagNode tagNode : nodes) {
			result+= getStrVlaue(tagNode,result);
		}
		return toXmlPrefix(node.tagName,result,node.attributes);
	}
	
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public List<TagNode> getChildren() {
		return children;
	}

	public void setChildren(List<TagNode> children) {
		this.children = children;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(String name,String value) {
		Attribute attribute = new Attribute();
		attribute.name=name;
		attribute.value=value;
		attributes.add(attribute);
	}

	public static void main(String[] args) {
		TagNode tagNode = new TagNode("order");
		TagNode lineItemsTag = new TagNode("line-items");
		TagNode tag1 = new TagNode("line-item");
		tag1.setAttributes("pid","p3677");
		tag1.setAttributes("qty", "3");
		lineItemsTag.getChildren().add(tag1);
		TagNode tag2 = new TagNode("line-item");
		tag2.setAttributes("pid","p9877");
		tag2.setAttributes("qty", "10");
		lineItemsTag.getChildren().add(tag2);
		tagNode.getChildren().add(lineItemsTag);
		System.out.println(tagNode.toXml());
	}
	
}
