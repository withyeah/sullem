package com.ssafy.demo.dto;

import java.util.List;


public class PostPageDTO {
	private List<PostSimpleDTO> PostList;
	private int page;
	private int startPage;
	private int endPage;
	private int totalPage;
	private String title;
	public PostPageDTO() {}
	public PostPageDTO(List<PostSimpleDTO> postList, int page, int startPage, int endPage, int totalPage, String title) {
		PostList = postList;
		this.page = page;
		this.startPage = startPage;
		this.endPage = endPage;
		this.totalPage = totalPage;
		this.title = title;
	}
	public List<PostSimpleDTO> getPostList() {
		return PostList;
	}
	public void setPostList(List<PostSimpleDTO> postList) {
		PostList = postList;
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
		return "PostPageDTO [PostList=" + PostList + ", page=" + page + ", startPage=" + startPage + ", endPage="
				+ endPage + ", totalPage=" + totalPage + ", title=" + title + "]";
	}
}
