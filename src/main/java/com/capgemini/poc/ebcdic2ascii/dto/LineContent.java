package com.capgemini.poc.ebcdic2ascii.dto;

public class LineContent {

	private String content;

	public LineContent() {
	}

	public LineContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}

}
