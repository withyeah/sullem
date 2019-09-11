package com.ssafy.demo.repository;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.demo.dto.PostDTO;
import com.ssafy.demo.dto.PostSimpleDTO;
import com.ssafy.demo.mapper.PostMapper;

@Repository
public class PostRepositoryImpl implements PostRepository{

	@Autowired
	private SqlSessionTemplate template;

	@Override
	public int insert(PostDTO post) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.insert(post);
	}

	@Override
	public PostDTO selectByPostNo(int postNo) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.selectByPostNo(postNo);
	}

	@Override
	public List<PostSimpleDTO> selectAll() {
		System.out.println("selectAll");
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.selectAll();
	}

	@Override
	public List<PostSimpleDTO> selectByKeyword(String keyword) {
		System.out.println("selectByKeyword");
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.selectByKeyword(keyword);
	}

	@Override
	public List<PostSimpleDTO> pageList(String keyword, int startCol, int count) {
		System.out.println("pageList");
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.pageList(keyword, startCol, count);
	}

	@Override
	public int increaseLikesByPostNo(int postNo) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.increaseLikesByPostNo(postNo);
	}

	@Override
	public int increaseViewsByPostNo(int postNo) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.increaseViewsByPostNo(postNo);
	}

	@Override
	public List<PostSimpleDTO> selectListByViewsTop(int per) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.selectListByViewsTop(per);
	}

	@Override
	public int delete(int postNo) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.delete(postNo);
	}

	@Override
	public List<PostDTO> selectBySeq(int seq) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		try {
			return mapper.selectBySeq(seq);
		} catch(Exception e) {
			return new LinkedList<>();
		}
	}

	@Override
	public int update(PostDTO post) {
		PostMapper mapper = template.getMapper(PostMapper.class);
		return mapper.update(post);
	}

	
}
