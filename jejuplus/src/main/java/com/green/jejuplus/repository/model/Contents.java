package com.green.jejuplus.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contents {
	private Integer contentsId;
	private String contentsTempId;
	private String contentsLabel;
	private String title;
	private String region1;
	private String region2;
	private String jibeonAddress;
	private String roadAddress;
	private String tag;
	private String introduction;
	private String latitude;
	private String longitude;
	private String phoneNo;
	private String imgPath;
	private String thumbnailPath;
	private Integer recommended;
	
}
