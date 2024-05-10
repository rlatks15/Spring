package org.hta.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.hta.member.controller.Action;
import org.hta.member.controller.ActionForward;
import org.hta.member.dao.MemberDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		MemberDao md = new MemberDao();
		int result = md.delete(id);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (result == 1) {
			out.println("<script>");
			out.println("alert('삭세 성공입니다.');");
			out.println("location.href='list.net'");
			out.println("</script>");
			out.close();
		} else {
			out.println("<script>");
			out.println("alert('삭제 성공입니다.');");
			out.println("location.href='list.net'");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
