package com.ssafy.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.demo.dto.PostDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.service.PostService;


@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService service;
	
	
	@DeleteMapping("delete/{postNo}")
	public int delete(@PathVariable int postNo) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" : ");
		System.out.println("delete - postNo:"+postNo);
		return service.delete(postNo);
	}
	
	@PostMapping("uploadPost")
	public int uploadPost(@RequestBody PostDTO post) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		post.setCreateDate(df.format(date));
		System.out.print(df.format(date)+" : Upload Post ");
		System.out.println(post.toString());
		return service.insert(post);
	}
	
	@PostMapping("update")
	public int update(@RequestBody PostDTO post) {
		System.out.println("update");
		return service.update(post);
	}
	@GetMapping("selectPage/{title}/{p}")
	public PostPageDTO selectPage(@PathVariable String p,@PathVariable String title){
		PostPageDTO pp = service.makePagePost(title,p,9);
		System.out.println(pp);
		return pp;
	}
	@GetMapping("selectAll")
	public List<PostSimpleDTO> selectAll(){
		return service.selectAll();
	}
	@GetMapping("selectTopPost")
	public List<PostSimpleDTO> selectTopPost(){
		return service.selectListByViewsTop(6);
	}
	
	@GetMapping("selectBySeq/{seq}")
	public List<PostDTO> selectBySeq(@PathVariable int seq) {
		return service.selectBySeq(seq);
	}
	
	@GetMapping("detailPost/{postNo}")
	public PostDTO detailPost(@PathVariable int postNo) {
		service.increaseViewsByPostNo(postNo);
		PostDTO post = service.selectByPostNo(postNo);
		
//		String content = post.getContent();
//		content.replaceAll("img src", "v-img src");
//		post.setContent(content);
//		System.out.println(content);
		return post;
	}
	
	
	
	@RequestMapping(path = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestPart MultipartFile image) throws IOException {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = "i"+df.format(date) + image.getOriginalFilename();
		System.out.println(filename);
		
		
		byte[] pData = image.getBytes();
		File lOutFile = null;
		FileOutputStream lFileOutputStream = null;
		String url ="";
		try {
			lOutFile = new File("C:\\assets/"+filename);
			lFileOutputStream = new FileOutputStream(lOutFile);
			lFileOutputStream.write(pData);
			
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		} finally {
			lFileOutputStream.close();
			url = "http://192.168.31.114:8399/assets/"+filename;
		}
		return url;
	}
	@GetMapping("test")
	public String test() {
		String test = "'<p class=\"ql-align-center\">아이폰8을 분실하고 3일째.. </p><p class=\"ql-align-center\">오랜만에 휴대폰없이 생활하려니 많이 허전하면서도 나쁘지는 않았습니다.. 중요한 연락이 있기에.. 임대폰을 신청하러 갑니다!.</p><p><a href=\"https://blog.naver.com/PostView.nhn?blogId=qkrtlswhd&amp;logNo=221538398012&amp;categoryNo=0&amp;parentCategoryNo=0&amp;viewDate=&amp;currentPage=1&amp;postListTopCurrentPage=&amp;from=section&amp;userTopListOpen=true&amp;userTopListCount=10&amp;userTopListManageOpen=false&amp;userTopListCurrentPage=1#\" target=\"_blank\"><img src=\"https://storep-phinf.pstatic.net/bcosall_01/original_2.gif?type=pa50_50\"></a></p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">일단 저는 아이폰8 을 분실했었고, 통신사는 KT입니다.</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">KT 통신사는 대리점 이 아닌<strong style=\"background-color: inherit;\"> 직영점</strong>이나<strong style=\"background-color: inherit;\"> 플라자</strong>에서 분실 신고 및 임대폰신청이 가능합니다. </p><p class=\"ql-align-center\">온라인에서도 신청이 가능하며 택배수령 할 수도 있습니다.</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">방문시 꼭 <strong style=\"background-color: inherit;\">신분증</strong>을 챙기시고!</p><p class=\"ql-align-center\">간단한 서류 작성 후 조금 기다리면 ...</p><p class=\"ql-align-center\">...</p><p class=\"ql-align-center\">...</p><p class=\"ql-align-center\">분실폰에 준하는 모델을 받게됩니다!</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">분실했던 휴대폰은 아이폰8이었기에, 저는 재고가 남아있는 아이폰7을 임대받았습니다.</p><p class=\"ql-align-center\">(이때 방문전 원하는 모델의 <strong style=\"background-color: inherit;\">재고</strong>가 남아있는지 꼭 확인 하셔야 합니다!. 허탕 칠수가...</p><p class=\"ql-align-center\">그리고 <strong style=\"background-color: inherit;\">요금연체</strong>시 임대가 불가능 하답니다!)</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">일반 분실 및 A/S 임대폰 : 30일 무료 + ( 사용일수 x 100원 )</p><p class=\"ql-align-center\">프리미엄 임대폰 : 사용일수 x 600원</p><p class=\"ql-align-center\">VIP 고객은 일반폰 180일, 프리미엄폰 7일 무료로 이용가능 합니다!</p><p class=\"ql-align-center\">아이폰은 프리미엄폰에 속합니다.</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">따라서 저는 일주일 무료이용! ( VIP자랑 ㅎ )</p><p class=\"ql-align-center\">이후 사용일수마다 600원 추가요금이 붙게됩니다.</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">아, 그리고 유심칩 비용 별도..(얼마였더라.. 만원안쪽이었던것같은..)</p><p><a href=\"https://blog.naver.com/PostView.nhn?blogId=qkrtlswhd&amp;logNo=221538398012&amp;categoryNo=0&amp;parentCategoryNo=0&amp;viewDate=&amp;currentPage=1&amp;postListTopCurrentPage=&amp;from=section&amp;userTopListOpen=true&amp;userTopListCount=10&amp;userTopListManageOpen=false&amp;userTopListCurrentPage=1#\" target=\"_blank\"><img src=\"https://postfiles.pstatic.net/MjAxOTA1MTVfMTg5/MDAxNTU3OTE0NzE3OTQ5.sYlipAlkISuSf-W_3kx3slb_APGWkmy4ay7m-brXK40g.bBkFvQL8-8l4dOGa5y39AHuuDUVvIoX-LPVvHvRCPOwg.PNG.qkrtlswhd/SE-45b6b0e2-cfe7-485b-b18d-bfb14e714a03.png?type=w966\"></a></p><p class=\"ql-align-center\">임대폰 수령시 외부 기스나 작동하는데 문제없는지 꼼꼼히 확인해주세요.</p><p class=\"ql-align-center\">임대폰 분실/파손/고장시</p><p class=\"ql-align-center\">본체 </p><p class=\"ql-align-center\">170,000원</p><p class=\"ql-align-center\">배터리</p><p class=\"ql-align-center\">3,000원</p><p class=\"ql-align-center\">케이블</p><p class=\"ql-align-center\">3,000원</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">그리하여 아이폰7 수령! </p><p><a href=\"https://blog.naver.com/PostView.nhn?blogId=qkrtlswhd&amp;logNo=221538398012&amp;categoryNo=0&amp;parentCategoryNo=0&amp;viewDate=&amp;currentPage=1&amp;postListTopCurrentPage=&amp;from=section&amp;userTopListOpen=true&amp;userTopListCount=10&amp;userTopListManageOpen=false&amp;userTopListCurrentPage=1#\" target=\"_blank\"><img src=\"https://postfiles.pstatic.net/MjAxOTA1MTVfNzkg/MDAxNTU3OTE1MTA5NzY4.vr5txGbAmelAp4hJxlKRMG31uOXOJGWDl0XRPbokplUg.sIanJei-HwxDJoccQv7rrQMXvA-Bw7ZnlCr2UzphcnEg.PNG.qkrtlswhd/SE-23f824e3-d5e5-49e0-a720-5bbb9769a9eb.png?type=w966\"></a></p><p class=\"ql-align-center\"><span style=\"color: rgb(85, 85, 85);\">ps 어벤져스 재밌어요!</span></p><p class=\"ql-align-center\">​</p><p class=\"ql-align-center\">지금은 분실되었던 휴대폰을 찾았으나 회수하러 갈수없는 상황이다보니..</p><p class=\"ql-align-center\">그때까지 열심히 사용하고 반납할테다!</p><p><a href=\"https://blog.naver.com/PostView.nhn?blogId=qkrtlswhd&amp;logNo=221538398012&amp;categoryNo=0&amp;parentCategoryNo=0&amp;viewDate=&amp;currentPage=1&amp;postListTopCurrentPage=&amp;from=section&amp;userTopListOpen=true&amp;userTopListCount=10&amp;userTopListManageOpen=false&amp;userTopListCurrentPage=1#\" target=\"_blank\"><img src=\"https://storep-phinf.pstatic.net/bcosall_01/original_6.gif?type=pa50_50\"></a></p><p class=\"ql-align-center\">​</p><p><br></p>'";
		test.replaceAll("img src", "v-img src");
		return test;
	}
}
