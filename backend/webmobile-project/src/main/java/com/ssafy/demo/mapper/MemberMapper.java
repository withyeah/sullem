package com.ssafy.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.demo.dto.MemberDTO;

public interface MemberMapper {
	public int insert(MemberDTO member);
	public int update(MemberDTO member);
	public int updateWithPassword(MemberDTO member);
	public int deleteBySeq(int seq);
	public int deleteByEmail(String email);
	public MemberDTO selectByEmail(String email);
	public List<MemberDTO> selectAll();
	public MemberDTO selectByEmailAndPassword(String email, String password);
	public List<MemberDTO> searchByName(String name);
	public List<MemberDTO> searchByEmail(String email);
	public List<MemberDTO> pageListByEmail(@Param("email")String email, @Param("startCol")int startCol, @Param("count")int count);
	
}
