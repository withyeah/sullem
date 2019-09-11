package com.ssafy.demo.repository;

import java.util.LinkedList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.dto.ScheduleDTO;
import com.ssafy.demo.mapper.ScheduleMapper;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

	@Autowired
	private SqlSessionTemplate template;

	@Override
	public int insert(ScheduleDTO s) {
		System.out.println(s.toString());
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.insert(s);
	}
	

	@Override
	public int update(ScheduleDTO s) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.update(s);
	}

	@Override
	public int delete(int scdNo) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.delete(scdNo);
	}


	@Override
	public List<ScheduleDTO> selectByViewsTop(int per) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.selectByViewsTop(per);
	}


	@Override
	public ScheduleDTO selectByScdNo(int scdNo) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.selectByScdNo(scdNo);
	}


	@Override
	public List<ScheduleDTO> selectAll() {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.selectAll();
	}


	@Override
	public List<ScheduleDTO> selectByKeyword(String keyword) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.selectByKeyword(keyword);
	}


	@Override
	public List<ScheduleDTO> pageList(String keyword, int startCol, int count) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.pageList(keyword, startCol, count);
	}


	@Override
	public int increaseLikesByScdNo(int scdNo) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.increaseLikesByScdNo(scdNo);
	}


	@Override
	public int increaseViewsByScdNo(int scdNo) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		return mapper.increaseViewsByScdNo(scdNo);
	}


	@Override
	public List<ScheduleDTO> selectBySeq(int seq) {
		ScheduleMapper mapper = template.getMapper(ScheduleMapper.class);
		try {
			return mapper.selectBySeq(seq);
		}catch(Exception e) {
			return new LinkedList();
		}
		
	}
	
	

	
	
	
	
}
