package com.naver.myhome4.controller;



import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.myhome4.domain.MailVO;
import com.naver.myhome4.domain.Member;
import com.naver.myhome4.service.MemberService;
import com.naver.myhome4.task.SendMail;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
	
	@Controller
	@RequestMapping(value="/member")//http://localhost:8088/myhome4/member/로 시작하는 주소 매핑
	public class MemberController {
		
		//import org.slf4j.Logger;
		//import org.slf4j.LoggerFactory;
		private static final Logger Logger = LoggerFactory.getLogger(MemberController.class);
		
		private MemberService memberservice;
		private PasswordEncoder passwordEncoder;
		private SendMail sendMail;
		
		@Autowired
		public MemberController(MemberService memberservice,
								PasswordEncoder passwordEncoder,
								SendMail sendMail) {
			this.memberservice=memberservice;
			this.passwordEncoder=passwordEncoder;
			this.sendMail=sendMail;
		}
		
		/* @CookieValue(value="saveid", required=false) Cookie redCookie
		   이름이 saveid인 쿠키를 Cookie 타입으로 전달받습니다.
		   지정한 이름의 쿠키가 존재하지 않을 수도 있기 때문에 required=false로 설정해야 합니다.
		   즉, id 기억하기를 선택하지 않을 수도 있기 때문에 required=false로 설정해야 합니다.
		   required=true 상태에서 지정한 이름을 가진 쿠키가 존재하지 않으면 스프링 MVC는 익셉션을 발생시킵니다.
		 */
		//http://localhost:9500/myhome4/member/login
		//로그인 폼이동
		@RequestMapping(value = "/login", method = RequestMethod.GET)
		public ModelAndView login(ModelAndView mv,
								  @CookieValue(value="remember-me", required=false) Cookie readCookie,
								  HttpSession session,
								  Principal userPrincipal
								  ) {
			if(readCookie != null) {
				// principal.getName() : 로그인한 아이디 값을 알 수 있어요
				Logger.info("저장된 아이디 :" + userPrincipal.getName());
				mv.setViewName("redirect:/board/list");
				
			} else {
				mv.setViewName("member/member_loginForm");
				
				// 세션에 저장된 값을 한 번만 실행될 수 있도록 model에 저장
				mv.addObject("loginfail", session.getAttribute("loginfail"));
				
				session.removeAttribute("loginfail"); // 세션의 값은 제거합니다.
			}
			 return mv;
		}
		
		//http://localhost:9400/myhome4/member/join
		//회원가입 폼 이동
		@RequestMapping(value = "/join", method = RequestMethod.GET)
		public String join() {
			return "member/member_joinForm";//WEB-INF/views/member/member_joinForm.jsp
		}
		
		//회원가입폼에서 아이디 검사
		@ResponseBody
		@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
		public int idcheck(@RequestParam("id") String id) {
			return memberservice.isId(id);
		}
		
		
		//회원가입처리
		@RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
		public String joinProcess( Member member,
								   RedirectAttributes rattr,
								   Model model,
								   jakarta.servlet.http.HttpServletRequest request) {
			//비밀번호 암호화 추가
			String encPassword = passwordEncoder.encode(member.getPassword());
			Logger.info(encPassword);
			member.setPassword(encPassword);
			
			int result = memberservice.insert(member);
			//result=0;
			/*
			 	스프링에서 제공하는 RedirectAttributes는 기존의 Servlet에서 사용되던
			 	response.sendRedirect()를 사용할 때와 동일한 용도로 사용합니다.
			 	리다이렉트로 전송하면 파라미터를 전달하고자 할 때 addAttribute()나 addFlashAttribute()를 사용합니다.
			 	예) response.sendRedirect("/test?result=1");
			 		=> rattr.addAttribute("result",1)"
			 */
			// 삽입이 된 경우
			if (result == 1) {
				MailVO vo = new MailVO();
				vo.setTo(member.getEmail());
				vo.setContent(member.getId() + "왜 했어?");
				sendMail.sendMail(vo);
				
				rattr.addFlashAttribute("result", "joinSuccess");
				return "redirect:login";
			}else {
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("message", "회원 가입 실패");
				return "error/error";
			}
		}
				
		
		//회원 정보 수정폼
		@RequestMapping(value = "/update", method = RequestMethod.GET)
		public ModelAndView member_update(HttpSession session,
										  ModelAndView mv, Principal userPrincipal) {
			String id = userPrincipal.getName();
			
			if(id==null) {
				mv.setViewName("redirect:login");
				Logger.info("id is null");
			}else {
				Member m = memberservice.member_info(id);
				mv.setViewName("member/member_updateForm");
				mv.addObject("memberinfo", m);
			}
			return mv;
		}
		
		//수정처리
		@RequestMapping(value = "/updateProcess", method = RequestMethod.POST)
		public String updateProcess(Member member, Model model,
									jakarta.servlet.http.HttpServletRequest request,
									RedirectAttributes rattr) {
			
			int result = memberservice.update(member);
			if (result == 1) {
				rattr.addFlashAttribute("result","updateSuccess");
				return "redirect:/board/list";
			} else {
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("message", "정보 수정 실패");
				return "error/error";
			}
		}
		
		/*
		 	1. header.jsp에서 이동하는 경우
		 	   href="${pageContext.request.contextPath}/member/list"
		 	2. member_list.jsp에서 이동하는 경우
		 	   <a href="list?page=2&search_field=-1&search_word=" class="page-link">2</a>
		 */
		@RequestMapping(value = "/list")
		public ModelAndView memberList(
				@RequestParam(value="page", defaultValue = "1") int page,
				@RequestParam(value="limit", defaultValue = "3") int limit,
				ModelAndView mv,
				@RequestParam(value="search_field", defaultValue = "-1") int index,
				@RequestParam(value="search_word", defaultValue = "") String search_word
				)
		{
			
			
			int listcount = memberservice.getSearchListCount(index, search_word); // 총 리스트 수를 받아옵니다.
			
			List<Member> list = memberservice.getSearchList(index, search_word, page, limit);
			
			// 총 페이지 수	
			int maxpage = (listcount + limit - 1) / limit;
			
			// 현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
			int startpage = ((page - 1) / 10) * 10 + 1;
			
			// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30 등...)
			int endpage = startpage + 10 - 1;
			
			if (endpage > maxpage)
				endpage = maxpage;
			
			mv.setViewName("member/member_list");
			mv.addObject("page",page);
			mv.addObject("maxpage",maxpage);
			mv.addObject("startpage",startpage);
			mv.addObject("endpage",endpage);
			mv.addObject("listcount",listcount);
			mv.addObject("memberlist",list);
			mv.addObject("search_field",index);
			mv.addObject("search_word",search_word);
			return mv;
		}
		
		@RequestMapping(value = "/info", method = RequestMethod.GET)
		public ModelAndView member_info(@RequestParam("id") String id,
										  ModelAndView mv,
										  HttpServletRequest request) {
			Member m = memberservice.member_info(id);
			//m=null; //오류 확인하는 값
			if(m!=null) {
				mv.setViewName("member/member_info");
				mv.addObject("memberinfo", m);
			}else {
				mv.addObject("url", request.getRequestURL());
				mv.addObject("message", "해당 정보가 없습니다.");
				mv.setViewName("error/error");
			}
			return mv;
		}
		
		//삭제
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public String member_delete(String id) {
			memberservice.delete(id);
			return "redirect:list";
		}
	}

