package com.ssafy.demo.dto;

public class MemberDTO {
	private int seq;
	private String email;
	private String password;
	private String name;
	private int age;
	private String gender;
	private String phone;
	private String create_at;
	private String type;
	public MemberDTO(int seq, String email, String password, String name, int age, String gender, String phone,
			String create_at, String type) {
		this.seq = seq;
		this.email = email;
		this.password = password;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.create_at = create_at;
		this.type = type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreate_at() {
		return create_at;
	}
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MemberDTO [seq=" + seq + ", email=" + email + ", password=" + password + ", name=" + name + ", age="
				+ age + ", gender=" + gender + ", phone=" + phone + ", create_at=" + create_at + ", type=" + type + "]";
	}
	
}
