package com.barath.app;

public class HelloMessage {
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HelloMessage(String text) {
		super();
		this.text = text;
	}

	public HelloMessage() {
		super();
		
	}

	@Override
	public String toString() {
		return "HelloMessage [text=" + text + "]";
	}
	
	
	

}
