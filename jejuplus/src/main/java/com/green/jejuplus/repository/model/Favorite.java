package com.green.jejuplus.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
	private Integer favoriteId;
	private Integer userId;
	private Integer contentsId;

}
