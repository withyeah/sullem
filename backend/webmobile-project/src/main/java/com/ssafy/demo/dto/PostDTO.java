package com.ssafy.demo.dto;

public class PostDTO {

	private int postNo;
	private int seq;
	private String title;
	private String description;
	private String updateDate;
	private String createDate;
	private String name;
	private String content;
	private String thumbnail;
	private int likes;
	private int views;
	public PostDTO(int postNo, int seq, String title, String description, String updateDate, String createDate,
			String name, String content, String thumbnail, int likes, int views) {
		this.postNo = postNo;
		this.seq = seq;
		this.title = title;
		this.description = description;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.name = name;
		this.content = content;
		this.thumbnail = thumbnail;
		this.likes = likes;
		this.views = views;
	}
	public PostDTO() {
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	@Override
	public String toString() {
		return "PostDTO [postNo=" + postNo + ", seq=" + seq + ", title=" + title + ", description=" + description
				+ ", updateDate=" + updateDate + ", createDate=" + createDate + ", name=" + name + ", content="
				+ content + ", thumbnail=" + thumbnail + ", likes=" + likes + ", views=" + views + "]";
	}
	
	
	
}
