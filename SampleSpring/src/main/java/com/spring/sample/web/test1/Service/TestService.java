package com.spring.sample.web.test1.Service;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class TestService implements iTestService {

	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "memberMapper";
	
	@Override
	public int test() {
		return sqlSession.selectOne(namespace + ".selectTest");
	}
}
