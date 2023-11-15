package com.green.jejuplus.util;

import lombok.Data;

@Data
public class PagingDto {
    private int page;             // 현재 페이지 번호
    private int recordSize;       // 페이지당 출력할 데이터 개수
    private String keyword;       // 검색 키워드
    private String searchType;    // 검색 유형
    private int userId;
    private String classification;
    
    public int getOffset() {
        return (page - 1) * recordSize;
    }
    
    public PagingDto() {
		this.page = 1;
		this.recordSize = 10;
	}
}
