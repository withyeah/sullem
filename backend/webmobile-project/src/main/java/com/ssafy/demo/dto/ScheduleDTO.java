package com.ssafy.demo.dto;

import java.util.Arrays;
import java.util.List;

public class ScheduleDTO {
	private int scdNo;
	private String title;
	private int seq;
	private String name;
	private String country;
	private String description;
	private String events;
	private String startDate;
	private String createTime;
	private String updateTime;
	private int views;
	private int likes;
	public ScheduleDTO(int scdNo, String title, int seq, String name, String country, String description, String events,
			String startDate, String createTime, String updateTime, int views, int likes) {
		super();
		this.scdNo = scdNo;
		this.title = title;
		this.seq = seq;
		this.name = name;
		this.country = country;
		this.description = description;
		this.events = events;
		this.startDate = startDate;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.views = views;
		this.likes = likes;
	}
	public ScheduleDTO() {
		super();
	}
	public int getScdNo() {
		return scdNo;
	}
	public void setScdNo(int scdNo) {
		this.scdNo = scdNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	@Override
	public String toString() {
		return "ScheduleDTO [scdNo=" + scdNo + ", title=" + title + ", seq=" + seq + ", name=" + name + ", country="
				+ country + ", description=" + description + ", events=" + events + ", startDate=" + startDate
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", views=" + views + ", likes=" + likes
				+ "]";
	}
	
	
}
