package org.hta.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.hta.member.controller.Action;
import org.hta.member.controller.ActionForward;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다.');");
		out.println("location.href='loginForm.net'");
		out.println("</script>");
		out.close();
		
		return null;
	}

}
