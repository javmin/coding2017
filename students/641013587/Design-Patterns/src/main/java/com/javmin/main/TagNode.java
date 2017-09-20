package com.javmin.main;

import java.util.ArrayList;
import java.util.List;

import com.javmin.main.TagNode.TagBuilder;

public class TagNode {
	
	private String tagName;
	private String tagValue;
	private List<TagNode> children=new ArrayList<>();
	private List<Attribute> attributes = new ArrayList<>();
	private static class Attribute{
		String name;
		String value;
	}
	
	private TagNode(){
	}
	
	public static class TagBuilder{
		String tagName;
		String tagValue;
		List<TagNode> children=new ArrayList<>();
		List<Attribute> attributes = new ArrayList<>();
		private TagBuilder parent;
		
		private TagBuilder(){
		}
		
		public static TagBuilder create(String name){
			 TagBuilder tagBuilder = new TagBuilder();
			 tagBuilder.tagName=name;
			 return tagBuilder;
		}
		
		public TagBuilder tagValue(String tagValue){
			this.tagValue=tagValue;
			return this;
		}
		
		public TagBuilder children(String name){
			TagBuilder create = TagBuilder.create(name);
			TagNode node=create.toTagNode();
			this.children.add(node);
			create.parent=this;
			return create;
		}

		private TagNode toTagNode() {
			TagNode tagNode = new TagNode();
			TagBuilder top=getTop(this);
			tagNode.tagName=top.tagName;
			tagNode.attributes=top.attributes;
			tagNode.children=top.children;
			tagNode.tagValue=top.tagValue;
			return tagNode;
		}

		private TagBuilder getTop(TagBuilder tagBuilder) {
			if(tagBuilder.parent!=null){
				return getTop(tagBuilder.parent);
			}
			return tagBuilder;
		}

		public TagBuilder attributes(String key, String value) {
			Attribute attribute = new Attribute();
			attribute.name=key;
			attribute.value=value;
			this.attributes.add(attribute);
			return this;
		}

		public TagBuilder brother(String name) {
			TagBuilder parent2 = this.parent;
			/*if(parent2==null){
				parent2=new TagBuilder();
				parent2.children.add(this.toTagNode());
				this.parent=parent2;
			}*/
			return parent2.children(name);
		}
		
		
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
		TagBuilder builder = 
			TagBuilder.create("order").children("line-items")
			.children("line-item").attributes("pid","p3677").attributes("qty", "3").brother("line-item").attributes("pid","p9877").attributes("qty", "10");
		TagNode tagNode = builder.toTagNode();
		System.out.println(tagNode.toXml());
		/*TagNode tagNode = new TagNode("order");
		TagNode lineItemsTag = new TagNode("line-items");
		TagNode tag1 = new TagNode("");
		tag1.setAttributes("pid","p3677");
		tag1.setAttributes("qty", "3");
		lineItemsTag.getChildren().add(tag1);
		TagNode tag2 = new TagNode("line-item");
		tag2.setAttributes("pid","p9877");
		tag2.setAttributes("qty", "10");
		lineItemsTag.getChildren().add(tag2);
		tagNode.getChildren().add(lineItemsTag);
		System.out.println(tagNode.toXml());*/
	}
	
}
