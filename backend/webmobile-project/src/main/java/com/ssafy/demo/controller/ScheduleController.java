package com.ssafy.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.demo.dto.DaysDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.RouteDTO;
import com.ssafy.demo.dto.ScheduleDTO;
import com.ssafy.demo.dto.SchedulePageDTO;
import com.ssafy.demo.service.ScheduleServiceImpl;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleServiceImpl service;
	
	@PostMapping("insert")
	public int insert(@RequestBody String body) {
		return service.insert(body);
	}
	
	@PostMapping("update")
	public int update(@RequestBody String body) {
		System.out.println("update");
		return service.update(body);
	}
	
	@DeleteMapping("delete/{scdNo}")
	public int delete(@PathVariable int scdNo) {
		return service.delete(scdNo);
	}
	
	@GetMapping("selectAll")
	public List<ScheduleDTO> selectAll(){
		return service.selectAll();
	}
	@GetMapping("selectByViewsTop")
	public List<ScheduleDTO> selectByViewsTop(){
		return service.selectByViewsTop(6);
	}
	
	@GetMapping("selectByNo/{scdNo}")
	public ScheduleDTO selectByScdNo(@PathVariable int scdNo) {
		service.increaseViewsByScdNo(scdNo);
		return service.selectByScdNo(scdNo);
	}
	
	@GetMapping("selectBySeq/{seq}")
	public List<ScheduleDTO> selectBySeq(@PathVariable int seq){
		System.out.println(seq);
		List<ScheduleDTO> selectBySeqList = service.selectBySeq(seq);
		System.out.println(selectBySeqList.toString());
		return selectBySeqList;
	}
	
	@GetMapping("selectPage/{keyword}/{p}")
	public SchedulePageDTO selectPage(@PathVariable String p,@PathVariable String keyword){
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":: title:"+keyword+"/p:"+p);
		SchedulePageDTO pp = service.makePageSchedule(keyword,p,9);
		System.out.println(pp);
		return pp;
	}
}
