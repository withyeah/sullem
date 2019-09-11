package com.ssafy.demo.dto;

import java.util.List;


public class MemberPageDTO {
	private List<MemberDTO> memberList;
	private int page;
	private int startPage;
	private int endPage;
	private int totalPage;
	private String name;
	private String email;
	public MemberPageDTO() {
	}
	public MemberPageDTO(List<MemberDTO> memberList, int page, int startPage, int endPage, int totalPage, String name,
			String email) {
		this.memberList = memberList;
		this.page = page;
		this.startPage = startPage;
		this.endPage = endPage;
		this.totalPage = totalPage;
		this.name = name;
		this.email = email;
	}
	public List<MemberDTO> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MemberDTO> memberList) {
		this.memberList = memberList;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "MemberPageDTO [memberList=" + memberList + ", page=" + page + ", startPage=" + startPage + ", endPage="
				+ endPage + ", totalPage=" + totalPage + ", name=" + name + ", email=" + email + "]";
	}
}
