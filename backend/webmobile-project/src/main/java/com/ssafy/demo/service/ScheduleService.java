package com.ssafy.demo.service;

import java.util.List;

import com.ssafy.demo.dto.ScheduleDTO;
import com.ssafy.demo.dto.SchedulePageDTO;

public interface ScheduleService {
	public int insert(String s);
	public int update(String s);
	public int delete(int scdNo);
	public List<ScheduleDTO> selectBySeq(int seq);
	public List<ScheduleDTO> selectAll();
	public ScheduleDTO selectByScdNo(int scdNo);
	public List<ScheduleDTO> selectByViewsTop(int per);
	public SchedulePageDTO makePageSchedule(String keyword, String page, int per);
	public int increaseLikesByScdNo(int scdNo);
	public int increaseViewsByScdNo(int scdNo);
}
