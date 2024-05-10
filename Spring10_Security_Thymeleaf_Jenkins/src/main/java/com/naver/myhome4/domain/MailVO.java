package com.naver.myhome4.domain;

public class MailVO {
	private String from="rlatks15@naver.com";
	private String to;
	private String subject="회원 가입? - 제목";
	private String content="왜 했어? - 내용";
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
