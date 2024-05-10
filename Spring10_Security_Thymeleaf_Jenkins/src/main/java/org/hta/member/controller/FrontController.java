package org.hta.member.controller;

import java.io.IOException;

import org.hta.member.action.LoginFormAction;
import org.hta.member.action.LoginproAction;
import org.hta.member.action.LogoutAction;
import org.hta.member.action.MainAction;
import org.hta.member.action.UpdateFormAction;
import org.hta.member.action.UpdateProAction;
import org.hta.member.action.joinFormAction;
import org.hta.member.action.joinProAction;
import org.hta.member.action.ListAction;
import org.hta.member.action.DeleteAction;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("*.net")
public class FrontController extends jakarta.servlet.http.HttpServlet  {
	private static final long serialVersionUID = 1L;
	
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

	  String RequestURI = request.getRequestURI();
	  String contextPath = request.getContextPath();
	  String command = RequestURI.substring(contextPath.length());
	  ActionForward forward = null;
	  Action action = null;
	  

	  System.out.println("RequestURI = " + RequestURI);
	  System.out.println("contextPath = " + contextPath);
	  System.out.println("command = " + command);
	  
	  switch (command) {
	  case "/main.net":
		  action = new MainAction();
		  break;
	  case "/loginForm.net":
		  action = new LoginFormAction();
		  break;	
	  case "/loginPro.net":
		  action = new LoginproAction();
		  break;	
	  case "/joinForm.net":
		  action = new joinFormAction();
		  break;	 
	  case "/joinPro.net":
		  action = new joinProAction();
		  break;	 
	  case "/logout.net":
		  action = new LogoutAction();
		  break;	 
	  case "/list.net":
		  action = new ListAction();
		  break;	 
	  case "/delete.net":
		  action = new DeleteAction();
		  break;	 
	  case "/updateForm.net":
		  action = new UpdateFormAction();
		  break;	 
	  case "/updatePro.net":
		  action = new UpdateProAction();
		  break;	 
	  }
	  
	  forward = action.execute(request, response);
	  
	  if(forward != null) {
		  if (forward.isRedirect()) {// 리다이렉트 됩니다.
			  response.sendRedirect(forward.getPath());
		  }else {// 포워딩됩니다.
			  RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
			  dispatcher.forward(request, response);
		  }
	  }//if (forward != null)	
}//doProcess
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
		
	}
			
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
		
	}
}
