package com.green.jejuplus.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.repository.model.Favorite;

@Mapper
public interface FavoriteRepository {
	
	public int insertFavorite(Favorite favorite);
	public Favorite selectFavorite(Favorite favorite);
	public int deleteFavorite(Favorite favorite);

}
