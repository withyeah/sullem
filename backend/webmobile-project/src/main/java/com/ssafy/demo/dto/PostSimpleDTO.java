package com.ssafy.demo.dto;

public class PostSimpleDTO {
	private int postNo;
	private String title;
	private int seq;
	private String description;
	private String createDate;
	private String name;
	private int views;
	private int likes;
	private String thumbnail;
	public PostSimpleDTO(int postNo, String title, int seq, String description, String createDate, String name,
			int views, int likes, String thumbnail) {
		this.postNo = postNo;
		this.title = title;
		this.seq = seq;
		this.description = description;
		this.createDate = createDate;
		this.name = name;
		this.views = views;
		this.likes = likes;
		this.thumbnail = thumbnail;
	}
	public PostSimpleDTO() {
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@Override
	public String toString() {
		return "PostSimpleDTO [postNo=" + postNo + ", title=" + title + ", seq=" + seq + ", description=" + description
				+ ", createDate=" + createDate + ", name=" + name + ", views=" + views + ", likes=" + likes
				+ ", thumbnail=" + thumbnail + "]";
	}
	
	
}
