package org.hta.member.controller;

public class ActionForward {
	private boolean redirect=false;
	private String path=null;

	//property redirect의 is 메소드
	public boolean isRedirect() {
		//프로퍼티 타입이  boolean인 경우 get대신 is를 앞에 붙일 수 있습니다
		return redirect;
	}

	//property redirect의 set 메소드
	public void setRedirect(boolean b) {
		this.redirect = b;
	}
	
	//property Path의 get 메소드
	public String getPath() {
		return path;
	}

	//property Path의 set 메소드
	public void setPath(String string) {
		this.path = string;
	}

}
