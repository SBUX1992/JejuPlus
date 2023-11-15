package com.green.jejuplus.service.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.green.jejuplus.repository.interfaces.PromotionRepository;
import com.green.jejuplus.repository.model.Promotion;

@Service
public class DataCleanupService {
	
	 @Autowired
	 private PromotionRepository promotionRepository;
	
	
    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void cleanupOldData() {
    	LocalDate currentDate = LocalDate.now();
        
    	// 오늘 날짜보다 end_date가 작은 데이터를 찾아 삭제
        List<Promotion> outdatedPromotions = promotionRepository.findByEndDateBefore(currentDate);

        for (Promotion promotion : outdatedPromotions) {
            // 프로모션과 연관된 이미지 삭제
        	int promotiondId = promotion.getPromotionId();
        	
        	promotionRepository.deleteImgByPromotionId(promotiondId);
        }

        // 프로모션 삭제
        for(Promotion promotion : outdatedPromotions) {
        	int promotiondId = promotion.getPromotionId();
        	promotionRepository.deleteAll(promotiondId);
        }
    }
}
