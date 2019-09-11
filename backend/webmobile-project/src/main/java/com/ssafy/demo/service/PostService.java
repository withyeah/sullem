package com.ssafy.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.demo.dto.PostDTO;
import com.ssafy.demo.dto.PostPageDTO;
import com.ssafy.demo.dto.PostSimpleDTO;

public interface PostService {
	public int insert(PostDTO post);
	public int delete(int postNo);
	public int update(PostDTO post);
	public PostDTO selectByPostNo(int postNo);
	public List<PostSimpleDTO> selectByKeyword(String keword);
	public List<PostSimpleDTO> selectAll();
	public List<PostDTO> selectBySeq(int seq);
	public PostPageDTO makePagePost(String keyword, String page, int per);
	public List<PostSimpleDTO> selectListByViewsTop(@Param("per")int per);
	public int increaseLikesByPostNo(int postNo);
	public int increaseViewsByPostNo(int postNo);
}
