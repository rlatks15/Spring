package org.hta.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.hta.member.controller.Action;
import org.hta.member.controller.ActionForward;
import org.hta.member.dao.MemberDao;
import org.hta.member.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginproAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String password=request.getParameter("password");
		
		Member mem = new Member();
		mem.setId(id);
		mem.setPassword(password);
		
		MemberDao md = new MemberDao();
		int result = md.chk(mem);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result == 1) { // 아이디와 비번이 같은 경우
			HttpSession session = request.getSession();
			session.setAttribute("id", mem.getId());
			out.println("<script>");
			out.println("alert('환영합니다~." + id + "님~');");
			out.println("location.href='main.net'");
			out.println("</script>");
			out.close();
		}else if(result == -1) {// 아이디는 같고 비번이 다른 경우
			out.println("<script>");
			out.println("alert('비번이 다릅니다.')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}else if(result == 0) {
			out.println("<script>");
			out.println("alert('ID가 존재하지 않습니다')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
