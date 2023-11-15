package com.green.jejuplus.dto.contents;

import lombok.Data;

@Data
public class FavoriteRequestDto {
	
	private Integer favoriteId;
	private Integer userId;
	private Integer contentsId;

}
