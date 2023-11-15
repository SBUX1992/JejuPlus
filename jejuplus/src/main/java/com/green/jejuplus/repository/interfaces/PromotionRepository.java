package com.green.jejuplus.repository.interfaces;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.jejuplus.dto.admin.AdminPromotionDto;
import com.green.jejuplus.repository.model.Promotion;
import com.green.jejuplus.repository.model.PromotionImg;

@Mapper
public interface PromotionRepository {

	void savePromotion(Promotion promotion);

	void savePromotionImg(PromotionImg image);

	List<AdminPromotionDto> getPromotions();

	String getImageUrlsByPromotionId(int promotionId);

	List<AdminPromotionDto> searchPromotions(@Param("search") String search,@Param("pageSize") int pageSize,@Param("offset") int offset);

	int countPromotionsWithSearch(String search);


	List<AdminPromotionDto> findPromotions(@Param("promotionsPerPage")int promotionsPerPage,@Param("offset") int offset);

	int count();

	void adminPromotionDelete(int promotionId);

	void adminPromotionImgDelete(int promotionId);

	Promotion findByPromotionDetail(int promotionId);

	List<PromotionImg> findByPromotionImg(int promotionId);

	List<Promotion> findByEndDateBefore(LocalDate currentDate);

	void deleteImgByPromotionId(int promotionId);

	void deleteAll(int promotiondId);

	AdminPromotionDto findByAdminPromotionDetail(int promotionId);

	int updatePromotion(@Param("promotionId")int promotionId,@Param("title") String title,@Param("introduce") String introduce,@Param("content") String content);


	void updatePromotionEndDate(@Param("promotionId") int promotionId,@Param("endDate") String endDate);

	int checkImageExists(@Param("promotionId")int promotionId,@Param("imagePath") String imagePath);

	List<PromotionImg> findByPromotionImgPath(int promotionId);


	void updatePromotionImg(@Param("imageFilename") String imageFilename, @Param("promotionId") int promotionId, @Param("promotionImgId") int promotionImgId);


}
