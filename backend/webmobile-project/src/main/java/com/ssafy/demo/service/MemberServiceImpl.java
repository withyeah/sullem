package com.ssafy.demo.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.demo.dto.MemberDTO;
import com.ssafy.demo.dto.MemberPageDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.repository.MemberRepository;
import com.ssafy.util.AES256Util;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository repo;
	@Override
	public int insert(MemberDTO member) {
		member.setType("user");
		try {
			AES256Util aes = new AES256Util();
			member.setPassword(aes.encrypt(member.getPassword()));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return repo.insert(member);
	}

	@Override
	public int update(MemberDTO member) {
		if(member.getPassword()!=null) {
			try {
				AES256Util aes = new AES256Util();
				member.setPassword(aes.encrypt(member.getPassword()));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}
			return repo.updateWithPassword(member);
		}else{
			return repo.update(member);
		}
	}

	@Override
	public int deleteBySeq(int seq) {
		return repo.deleteBySeq(seq);
	}

	@Override
	public int deleteByEmail(String email) {
		return repo.deleteByEmail(email);
	}

	
	@Override
	public MemberDTO selectByEmail(String email) {
		return repo.selectByEmail(email);
	}

	@Override
	public List<MemberDTO> selectAll() {
		return repo.selectAll();
	}

	@Override
	public MemberDTO selectByEmailAndPassword(String email, String password) {
		AES256Util aes;
		try {
			aes = new AES256Util();
			password = aes.encrypt(password);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return repo.selectByEmailAndPassword(email, password);
	}

	@Override
	public List<MemberDTO> searchByName(String name) {
		return repo.searchByName(name);
	}
	
	@Override
	public MemberPageDTO makePageMemberByEmail(String email, String page, int per) {
		MemberPageDTO mp = new MemberPageDTO();
		mp.setEmail(email);
		int totalBoardCount;
		int p=1;
		if (page != null && page.length() > 0) {
			p = Integer.parseInt(page);
		}
		System.out.println("//"+email+"//");
		if (email == "" || email == null || email.equals(" ")) {
			email = "%";
			System.out.println("email null: " + email);
			totalBoardCount = repo.selectAll().size();
		} else {
			email = "%"+email+"%";
			System.out.println("email not null: " + email);
			totalBoardCount = repo.searchByEmail(email).size();
		}
		
		int totalPageCount = totalBoardCount / per;
		if (totalBoardCount % per != 0)
			totalPageCount++;

		int startCol = (p - 1) * per;
		System.out.println(startCol +" / "+ per);
		List<MemberDTO> memberList = repo.pageListByEmail(email, startCol, per);

		int startPage = (p - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		if (endPage > totalPageCount) // 시작페이지에서 9씩 더하다가 총 페이지수 넘어가면 끝페이지를 총 페이지수로
			endPage = totalPageCount;
		mp.setMemberList(memberList);
		mp.setPage(p);
		mp.setStartPage(startPage);
		mp.setEndPage(endPage);
		mp.setTotalPage(totalPageCount);
		return mp;
	}
	
	
	@Override
	public List<MemberDTO> searchByEmail(String email) {
		return repo.searchByEmail(email);
	}

	@Override
	public String mailSender(String email)  throws AddressException, MessagingException, NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException{
		email = email.trim();
		System.out.println("회원정보 "+email);
		MemberDTO member = repo.selectByEmail(email);
		if(member !=null && member.getEmail().equals(email)) {
			// 네이버일 경우 smtp.naver.com 을 입력합니다.
			// Google일 경우 smtp.gmail.com 을 입력합니다.
			String host = "smtp.gmail.com";
			final String username = "tlswhd1020"; // 네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
			final String password = "dkffkels32"; // 네이버 이메일 비밀번호를 입력해주세요..
			int port = 465; // 포트번호
			// 메일 내용
			String recipient = member.getEmail(); // 받는 사람의 메일주소를 입력해주세요. starlight747@naver.com
			String subject = member.getEmail()+" 회원 비밀번호"; // 메일 제목 입력해주세요. 
			String body = member.getEmail()+"님의 비밀번호는 :"+(new AES256Util()).decrypt(member.getPassword()) +" 입니다.";
			// 메일 내용 입력해주세요.
			Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성
			// SMTP 서버 정보 설정
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host); // Session 생성
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un = username;
				String pw = password;

				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(un, pw);
				}
			});
			session.setDebug(true); // for debug
			Message mimeMessage = new MimeMessage(session); // MimeMessage 생성
			mimeMessage.setFrom(new InternetAddress("tlswhd1020@gmail.com")); // 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀
																			// 주소를 다 작성해주세요.
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); // 수신자셋팅 //.TO 외에 .CC(참조)
																								// .BCC(숨은참조) 도 있음
			mimeMessage.setSubject(subject); // 제목셋팅 
			mimeMessage.setText(body); //내용셋팅
			Transport.send(mimeMessage); // javax.mail.Transport.send() 이용
			return "이메일 전송에 성공하였습니다.";
		}else {
			return "회원정보가 일치하지 않습니다";
		}
	}
	
}
