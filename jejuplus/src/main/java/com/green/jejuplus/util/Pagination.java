package com.green.jejuplus.util;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Pagination {
	private PagingDto paging;
	private int articleTotalCount;
	private int endPage;
	private int beginPage;
	private boolean prev;
	private boolean next;
	
	private final int buttonNum = 10;
	
	
	private void calcDataOfPage() {
		
		endPage = (int) (Math.ceil(paging.getPage() / (double) buttonNum) * buttonNum);
		
		beginPage = (endPage - buttonNum) + 1;
			
		prev = (beginPage == 1) ? false : true;
		
		next = articleTotalCount <= (endPage * paging.getRecordSize()) ? false : true;
		
		if(!next) {
			endPage = (int) Math.ceil(articleTotalCount / (double) paging.getRecordSize()); 
		}
		
	}
	
	public void setArticleTotalCount(int articleTotalCount) {
		this.articleTotalCount = articleTotalCount;
		calcDataOfPage();
	}

}
