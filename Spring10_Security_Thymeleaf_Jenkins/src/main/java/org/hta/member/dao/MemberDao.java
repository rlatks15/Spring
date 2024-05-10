package org.hta.member.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hta.member.domain.Member;

public class MemberDao {
	
	private SqlSession getSession() {
		SqlSession session = null;
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("org/hta/mybatis/config/sqlMapConfig.xml");
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(reader);
			session = sf.openSession(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}

	public int chk(Member member) {
		int result = 0; //아이디 없는 경우
		
		try(SqlSession session = getSession()){
			// 조회결과가 없는 경우 dbmember는 null 입니다.
			Member dbmember = (Member) session.selectOne("org.hta.mybatis.member.select",
														 member.getId());
			if (dbmember != null) {
				if (dbmember.getId().equals(member.getId())) {
					result = -1; // 아이디는 같고 비번이 다른 경우
					if (dbmember.getPassword().equals(member.getPassword())) {
						result = 1; // 아이디와 비번이 같은 경우
					}
				}
			} else {
				System.out.println("chk()결과 = null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(Member member) {
		int result = 0;
		try(SqlSession session = getSession()){
			result = session.insert("org.hta.mybatis.member.insert", member);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Member> list() {
		List<Member> list = null;
		try(SqlSession session = getSession()){
			list = session.selectList("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Member select(String id) {
		Member dbmember = null;
		try(SqlSession session = getSession()){
			dbmember = (Member) session.selectOne("select", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbmember;
	}

	public int delete(String id) {
		int result = 0;
		try(SqlSession session = getSession()){
			result = session.delete("delete",id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int update(Member mem) {
		int result = 0;
		try(SqlSession session = getSession()){
			result = session.update("update",mem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
