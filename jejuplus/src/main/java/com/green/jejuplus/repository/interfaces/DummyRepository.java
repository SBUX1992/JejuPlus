package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Place;

@Mapper
public interface DummyRepository {

	public List<Place> findPlace();
	public void updateOverview(@Param("overview")String overview, @Param("placeId")Integer placeId);
	
	public void insertData(Contents contents);
}
