package com.ssafy.demo.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.demo.dto.MemberDTO;
import com.ssafy.demo.mapper.MemberMapper;

@Repository
public class MemberRepositoryImpl implements MemberRepository{
	
	@Autowired
	private SqlSessionTemplate template;

	public int insert(MemberDTO member) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.insert(member);
		
	}

	@Override
	public int update(MemberDTO member) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.update(member);
	}

	@Override
	public int updateWithPassword(MemberDTO member) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.updateWithPassword(member);
	}

	@Override
	public int deleteBySeq(int seq) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.deleteBySeq(seq);
	}

	@Override
	public int deleteByEmail(String email) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.deleteByEmail(email);
	}
	
	public MemberDTO selectByEmail(String email) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.selectByEmail(email);
	}

	public List<MemberDTO> selectAll() {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.selectAll();
	}

	@Override
	public MemberDTO selectByEmailAndPassword(String email, String password) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.selectByEmailAndPassword(email, password);
	}

	@Override
	public List<MemberDTO> searchByName(String name) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.searchByName(name);
	}

	@Override
	public List<MemberDTO> searchByEmail(String email) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.searchByEmail(email);
	}

	@Override
	public List<MemberDTO> pageListByEmail(String email, int startCol, int count) {
		MemberMapper mapper = template.getMapper(MemberMapper.class);
		return mapper.pageListByEmail(email, startCol, count);
	}

	
}
