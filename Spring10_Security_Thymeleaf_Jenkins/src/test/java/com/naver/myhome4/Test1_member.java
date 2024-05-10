package com.naver.myhome4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.naver.myhome4.domain.Member;
import com.naver.myhome4.mybatis.mapper.MemberMapper;
/*
 1. Junit이란 Java에서 독립된 단위테스트를 지원해주는 프레임워크입니다.
 2. 단위 테스트
 	단위테스트는 하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트입니다.
 	하나의 모듈이란 각 계층에서의 하나의 기능 또는 메소드로 이해할 수 있습니다.
 	하나의 기능이 올바르게 동작하는지를 독립적으로 테스트하는 것입니다.
 */
@SpringBootTest
class Test1_member {
	
	private static final Logger Logger =
					LoggerFactory.getLogger(Test1_member.class);
	
	@Autowired
	private MemberMapper dao;

	//@Test
	public void insertMember() {
		Member member = new Member();
		member.setEmail("H1234@hta.com");
		member.setName("고길동");
		member.setId("H1234");
		member.setGender("남");
		member.setAge(30);
		member.setPassword("1234");
		
		dao.insert(member);
		
		Logger.info("=== 삽입 완료 ===");
	}
	
	//@Test
	public void getSearchListCount() {
		Map<String, String> map = new HashMap<String, String>();
		
		int count = dao.getSearchListCount(map);
		Logger.info("=== " + count + " 개 있어요 ===");
	}
	
	//@Test
	public void getSearchListCount2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("search_field", "id");
		map.put("search_word", "%a%");
		int count = dao.getSearchListCount(map);
		Logger.info("=== " + count + " 개 있어요 ===");
	}
	
	//@Test
	 public void getSearchList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 1);
		map.put("end", 10);
		
		List<Member> list = dao.getSearchList(map);
		for(Member member : list) {
			Logger.info("======================");
			Logger.info(member.getId());
			Logger.info(member.getEmail());
			Logger.info(member.getGender());
			Logger.info(member.getName());
			Logger.info(member.getAge() + "");
			Logger.info(member.getPassword());
		}
	}
	 @Test
	 public void getSearchList2() {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("search_field", "id");
		 map.put("search_word", "%a%");
		 map.put("start", 1);
		 map.put("end", 10);
		 
		 List<Member> list = dao.getSearchList(map);
		 for(Member member : list) {
			 Logger.info("======================");
			 Logger.info(member.getId());
			 Logger.info(member.getEmail());
			 Logger.info(member.getGender());
			 Logger.info(member.getName());
			 Logger.info(member.getAge() + "");
			 Logger.info(member.getPassword());
		 }
	 }
}
