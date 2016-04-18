package com.laomaizi.bandwagondemo.lightpainting;

public class FavItem {
	private String text;
	private String color;
	private String fontsize;
	private String time;
	private String delay;
	private String id;

	public FavItem(String text,String color,String fontSize,String time,String delay, String id){
		
		this.text = text;
		this.color = color;
		this.fontsize = fontSize;
		this.time = time;
		this.delay = delay;
		this.id = null;
	
	}
	
	
	public FavItem() {
		// TODO 自动生成的构造函数存根
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text =text;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFontsize() {
		return fontsize;
	}

	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	


}
