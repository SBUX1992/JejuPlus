package com.green.jejuplus.dto.contents;

import lombok.Data;

@Data
public class TouristAreaDetailDto {
	private int contentsId;
	private String contentsLabel;
	private String roadAddress;
	private String thumbnailPath;
	private String latitude;
	private String longitude;
	private String phoneNo;
	private String title;
	private String tag;
	private String introduction;
	private String imgPath;
	private int recommended;

}
