package com.ssafy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.demo.dto.PostDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository repo;

	@Override
	public int insert(PostDTO post) {
		return repo.insert(post);
	}

	@Override
	public PostDTO selectByPostNo(int postNo) {
		return repo.selectByPostNo(postNo);
	}

	@Override
	public List<PostSimpleDTO> selectAll() {
		return repo.selectAll();
	}
	
	@Override
	public PostPageDTO makePagePost(String keyword, String page, int per) {
		PostPageDTO pp = new PostPageDTO();
		pp.setTitle(keyword);
		int totalBoardCount;
		int p=1;
		if (page != null && page.length() > 0) {
			p = Integer.parseInt(page);
		}
		if (keyword == "" || keyword == null || keyword.equals(" ")) {
			keyword = "%";
			totalBoardCount = repo.selectAll().size();
		} else {
			keyword = "%"+keyword+"%";
			totalBoardCount = repo.selectByKeyword(keyword).size();
		}
		
		int totalPageCount = totalBoardCount / per;
		if (totalBoardCount % per != 0)
			totalPageCount++;

		int startCol = (p - 1) * per;
		List<PostSimpleDTO> postList = repo.pageList(keyword, startCol, per);

		int startPage = (p - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		if (endPage > totalPageCount) // 시작페이지에서 9씩 더하다가 총 페이지수 넘어가면 끝페이지를 총 페이지수로
			endPage = totalPageCount;
		pp.setPostList(postList);
		pp.setPage(p);
		pp.setStartPage(startPage);
		pp.setEndPage(endPage);
		pp.setTotalPage(totalPageCount);
		return pp;
	}

	@Override
	public List<PostSimpleDTO> selectByKeyword(String keword) {
		return null;
	}

	@Override
	public int increaseLikesByPostNo(int postNo) {
		return repo.increaseLikesByPostNo(postNo);
	}

	@Override
	public int increaseViewsByPostNo(int postNo) {
		return repo.increaseViewsByPostNo(postNo);
	}

	@Override
	public List<PostSimpleDTO> selectListByViewsTop(int per) {
		return repo.selectListByViewsTop(per);
	}

	@Override
	public int delete(int postNo) {
		return repo.delete(postNo);
	}

	@Override
	public List<PostDTO> selectBySeq(int seq) {
		return repo.selectBySeq(seq);
	}

	@Override
	public int update(PostDTO post) {
		return repo.update(post);
	}
}
