package com.ssafy.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.demo.dto.DaysDTO;
import com.ssafy.demo.dto.MemberDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.dto.RouteDTO;
import com.ssafy.demo.dto.ScheduleDTO;
import com.ssafy.demo.dto.SchedulePageDTO;
import com.ssafy.demo.repository.ScheduleRepository;
import com.ssafy.demo.repository.ScheduleRepositoryImpl;
import com.ssafy.util.AES256Util;

@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleRepositoryImpl repo;
	
	@Override
	public int insert(String s) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		ScheduleDTO schedule = new ScheduleDTO();
		
		JSONObject json = new JSONObject(s);
		schedule.setTitle(json.get("title").toString());
		schedule.setCountry(json.get("country").toString());
		schedule.setStartDate(json.get("startDate").toString());
		schedule.setDescription(json.get("description").toString());
		schedule.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		schedule.setEvents(json.get("events").toString());
		
		schedule.setSeq((int)json.get("seq"));
		schedule.setName(json.get("name").toString());
		return repo.insert(schedule);
	}

	@Override
	public int update(String s) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		ScheduleDTO schedule = new ScheduleDTO();
		
		JSONObject json = new JSONObject(s);
		schedule.setScdNo((int)json.get("scdNo"));
		schedule.setCountry(json.get("country").toString());
		schedule.setStartDate(json.get("startDate").toString());
		schedule.setDescription(json.get("description").toString());
		schedule.setTitle(json.get("title").toString());
		schedule.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		schedule.setEvents(json.get("events").toString());
		
		return repo.update(schedule);
	}

	@Override
	public int delete(int scdNo) {
		return repo.delete(scdNo);
	}

	@Override
	public List<ScheduleDTO> selectByViewsTop(int per) {
		return repo.selectByViewsTop(per);
	}

	@Override
	public ScheduleDTO selectByScdNo(int scdNo) {
		return repo.selectByScdNo(scdNo);
	}

	@Override
	public List<ScheduleDTO> selectAll() {
		return repo.selectAll();
	}

	@Override
	public SchedulePageDTO makePageSchedule(String keyword, String page, int per) {
		SchedulePageDTO sp = new SchedulePageDTO();
		sp.setTitle(keyword);
		int totalBoardCount;
		int p=1;
		if (page != null && page.length() > 0) {
			p = Integer.parseInt(page);
		}
		if (keyword == "" || keyword == null || keyword.equals(" ")) {
			keyword = "%";
			totalBoardCount = repo.selectAll().size();
		} else {
			keyword = "%"+keyword+"%";
			totalBoardCount = repo.selectByKeyword(keyword).size();
		}
		
		int totalPageCount = totalBoardCount / per;
		if (totalBoardCount % per != 0)
			totalPageCount++;

		int startCol = (p - 1) * per;
		List<ScheduleDTO> list = repo.pageList(keyword, startCol, per);

		int startPage = (p - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		if (endPage > totalPageCount) // 시작페이지에서 9씩 더하다가 총 페이지수 넘어가면 끝페이지를 총 페이지수로
			endPage = totalPageCount;
		sp.setList(list);
		sp.setPage(p);
		sp.setStartPage(startPage);
		sp.setEndPage(endPage);
		sp.setTotalPage(totalPageCount);
		return sp;
	}

	@Override
	public int increaseLikesByScdNo(int scdNo) {
		return repo.increaseLikesByScdNo(scdNo);
	}

	@Override
	public int increaseViewsByScdNo(int scdNo) {
		return repo.increaseViewsByScdNo(scdNo);
	}

	@Override
	public List<ScheduleDTO> selectBySeq(int seq) {
		return repo.selectBySeq(seq);
	}

}
