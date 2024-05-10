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

public class joinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		Member mem = new Member();
		mem.setId(id);
		mem.setPassword(password);
		
		int result=0;
		MemberDao md = new MemberDao();
		int chkresult = md.chk(mem);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		if(chkresult==0) { //아이디 없는 경우
			result = md.insert(mem);
			if(result>0) {
				out.println("<script>");
				out.println("alert('가입 성공입니다.');");
				out.println("location.href='loginForm.net'");
				out.println("</script>");
				out.close();
			}else {
				out.println("<script>");
				out.println("alert('가입 실패입니다.');");
				out.println("history.go(-1)");
				out.println("</script>");
				out.close();
			}
		}else {
			out.println("<script>");
			out.println("alert('이미 아이디가 존재합니다.');");
			out.println("history.go(-1)");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}

}
