package org.hta.member.action;

import java.io.IOException;

import org.hta.member.controller.Action;
import org.hta.member.controller.ActionForward;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
	 	forward.setRedirect(false); //주소 변경없이 jsp페이지의 내용을 보여줍니다.
		forward.setPath("jsp/main.jsp");
		return forward;
}

}
