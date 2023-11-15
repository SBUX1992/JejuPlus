package com.green.jejuplus.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.repository.model.Recommended;

@Mapper
public interface RecommendedRepository {

	public Recommended selectRecommended(Recommended Recommended);
	public int insertRecommended(Recommended Recommended);
	public int deleteRecommended(Recommended Recommended);
	
}
