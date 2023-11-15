package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.repository.model.Air;

@Mapper
public interface AirRepository {
	
	public int insertAir(Air air);
	
	public List<Air> findByAirList(int userId);

}
