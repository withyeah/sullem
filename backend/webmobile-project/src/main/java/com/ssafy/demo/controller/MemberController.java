package com.ssafy.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.demo.dto.MemberDTO;
import com.ssafy.demo.dto.MemberPageDTO;
import com.ssafy.demo.service.MemberService;
import com.ssafy.util.AES256Util;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	
	@GetMapping("selectAll")
	public List<MemberDTO> selectAll(){
		return service.selectAll();
	}
	
	@PostMapping("login/{email}/{password}")
	public MemberDTO login(@PathVariable String email, @PathVariable String password) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" : ");
		System.out.println("login - "+email+"/"+password);
		return service.selectByEmailAndPassword(email, password);
	}
	
	@PostMapping("insert")
	public int insert(@RequestBody MemberDTO member) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" : ");
		System.out.println("Member insert - "+member.toString());
		return service.insert(member);
	}
	
	@DeleteMapping("delete/{seq}")
	public int deleteBySeq(@PathVariable int seq) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" : ");
		System.out.println("Member delete - "+seq);
		return service.deleteBySeq(seq);
	}

	@GetMapping("select_email/{email}")
	public MemberDTO selectByEmail(@PathVariable String email) {
		try {
			return service.selectByEmail(email);
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	@PostMapping("update")
	public MemberDTO update(@RequestBody MemberDTO member) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" : ");
		System.out.println("update -"+member.toString());
		service.update(member);
		return service.selectByEmail(member.getEmail());
	}
	
	
	@GetMapping("search_name/{name}")
	public List<MemberDTO> searchByName(@PathVariable String name){
		try {
			return service.searchByName(name);
		} catch(Exception e) {
			return null;

		}
	}
	
	@GetMapping("search_email/{email}")
	public List<MemberDTO> searchByEmail(@PathVariable String email){
		return service.searchByEmail(email);
	}
	
	@GetMapping("searchPage_email/{email}/{page}")
	public MemberPageDTO searchPageByEmail(@PathVariable String email, @PathVariable String page) {
		return service.makePageMemberByEmail(email, page, 10);
	}
	
	@GetMapping("findPassword/{email}")
	public String mailSender(@PathVariable String email) throws AddressException, NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException, GeneralSecurityException {
		return service.mailSender(email);
	}
}
