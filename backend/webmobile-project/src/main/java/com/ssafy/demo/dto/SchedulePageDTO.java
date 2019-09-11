package com.ssafy.demo.dto;

import java.util.List;


public class SchedulePageDTO {
	private List<ScheduleDTO> List;
	private int page;
	private int startPage;
	private int endPage;
	private int totalPage;
	private String title;
	public SchedulePageDTO() {}
	public SchedulePageDTO(java.util.List<ScheduleDTO> list, int page, int startPage, int endPage, int totalPage,
			String title) {
		List = list;
		this.page = page;
		this.startPage = startPage;
		this.endPage = endPage;
		this.totalPage = totalPage;
		this.title = title;
	}
	public List<ScheduleDTO> getList() {
		return List;
	}
	public void setList(List<ScheduleDTO> list) {
		List = list;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "SchedulePageDTO [List=" + List + ", page=" + page + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", totalPage=" + totalPage + ", title=" + title + "]";
	}
	
	
}
