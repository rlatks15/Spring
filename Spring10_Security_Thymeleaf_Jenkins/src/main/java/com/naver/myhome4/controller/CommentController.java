package com.naver.myhome4.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.myhome4.domain.Comment;
import com.naver.myhome4.service.CommentService;

import oracle.jdbc.proxy.annotation.Post;
	
	@RestController
	@RequestMapping(value="/comment")
	public class CommentController {
		
		private CommentService commentservice;
		
		@Autowired
		public CommentController(CommentService commentservice) {
			this.commentservice=commentservice;
		}
		private static final Logger Logger = LoggerFactory.getLogger(CommentController.class);
		
		@PostMapping(value = "/add")
		public int CommentAdd(Comment co) {
			return commentservice.commentsInsert(co);
		}
		
		@PostMapping(value = "/list")
		public Map<String,Object> CommentList(int board_num,  int page) {
			List<Comment> list = commentservice.getCommentList(board_num, page);
			int listcount = commentservice.getListCount(board_num);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("listcount", listcount);
			Logger.info("/comment/list");
			return map;
		}
		
		@PostMapping(value = "/update")
		public int CommentUpdate(Comment co) {
				return commentservice.commentsUpdate(co);
		}
		
		@PostMapping(value = "/delete")
		public int CommentDelete(int num) {
			return commentservice.commentsDelete(num);
		}
	}

