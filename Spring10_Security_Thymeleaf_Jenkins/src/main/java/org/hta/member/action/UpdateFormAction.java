package org.hta.member.action;

import java.io.IOException;

import org.hta.member.controller.Action;
import org.hta.member.controller.ActionForward;
import org.hta.member.dao.MemberDao;
import org.hta.member.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		String id = request.getParameter("id");
		MemberDao md = new MemberDao();
		Member mem = md.select(id);
		
		request.setAttribute("mem", mem);
		forward.setRedirect(false);
		forward.setPath("jsp/updateForm.jsp");
		return forward;
	}

}
