package com.ssafy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.demo.dto.ScheduleDTO;

public interface ScheduleRepository {
	public int insert(ScheduleDTO s);
	public int update(ScheduleDTO s);
	public int delete(int scdNo);
	public List<ScheduleDTO> selectAll();
	public ScheduleDTO selectByScdNo(int scdNo);
	public List<ScheduleDTO> selectBySeq(int seq);
	public List<ScheduleDTO> selectByViewsTop(int per);
	public List<ScheduleDTO> selectByKeyword(String keyword);
	public List<ScheduleDTO> pageList(@Param("keyword")String keyword, @Param("startCol")int startCol, @Param("count")int count);
	public int increaseLikesByScdNo(int scdNo);
	public int increaseViewsByScdNo(int scdNo);
	
}
