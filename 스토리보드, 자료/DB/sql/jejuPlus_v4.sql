-- MySQL Script generated by MySQL Workbench
-- Wed Nov  8 14:30:44 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema jejuplus
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema jejuplus
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jejuplus` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `jejuplus` ;

-- -----------------------------------------------------
-- Table `jejuplus`.`tb_level`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_level` (
  `level_id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`level_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `level_id` INT NULL DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(15) NOT NULL,
  `is_kakao` VARCHAR(2) NOT NULL,
  `fullname` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `level_id` (`level_id` ASC) VISIBLE,
  CONSTRAINT `tb_user_ibfk_1`
    FOREIGN KEY (`level_id`)
    REFERENCES `jejuplus`.`tb_level` (`level_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_payment` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `tid` VARCHAR(200) NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`payment_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tb_payment_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_air`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_air` (
  `air_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `payment_id` INT NOT NULL,
  `dep_pland_time` VARCHAR(50) NOT NULL,
  `arr_pland_time` VARCHAR(50) NOT NULL,
  `airline_nm` VARCHAR(30) NOT NULL,
  `arr_airport_nm` VARCHAR(30) NOT NULL,
  `dep_airport_nm` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`air_id`),
  INDEX `payment_id` (`payment_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tb_air_ibfk_1`
    FOREIGN KEY (`payment_id`)
    REFERENCES `jejuplus`.`tb_payment` (`payment_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `tb_air_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_contents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_contents` (
  `contents_id` INT NOT NULL AUTO_INCREMENT,
  `contents_temp_id` VARCHAR(50) NULL DEFAULT NULL,
  `contents_label` VARCHAR(50) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `region1` VARCHAR(255) NULL DEFAULT NULL,
  `region2` VARCHAR(255) NULL DEFAULT NULL,
  `jibeon_address` VARCHAR(255) NULL DEFAULT NULL,
  `road_address` VARCHAR(255) NULL DEFAULT NULL,
  `tag` TEXT NULL DEFAULT NULL,
  `introduction` TEXT NULL DEFAULT NULL,
  `latitude` VARCHAR(100) NULL DEFAULT NULL,
  `longitude` VARCHAR(100) NULL DEFAULT NULL,
  `phone_no` VARCHAR(100) NULL DEFAULT NULL,
  `img_path` VARCHAR(255) NULL DEFAULT NULL,
  `thumbnail_path` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`contents_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_favorite` (
  `favorite_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `contents_id` INT NOT NULL,
  PRIMARY KEY (`favorite_id`),
  INDEX `contents_id` (`contents_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tb_favorite_ibfk_1`
    FOREIGN KEY (`contents_id`)
    REFERENCES `jejuplus`.`tb_contents` (`contents_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `tb_favorite_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_schedule` (
  `schedule_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `user_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tb_schedule_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_item` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `contents_id` INT NOT NULL,
  `schedule_id` INT NOT NULL,
  `item_memo` TEXT NULL DEFAULT NULL,
  `item_day` INT NULL DEFAULT NULL,
  `item_sequence` INT NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `contents_id` (`contents_id` ASC) VISIBLE,
  INDEX `schedule_id` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `tb_item_ibfk_1`
    FOREIGN KEY (`contents_id`)
    REFERENCES `jejuplus`.`tb_contents` (`contents_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `tb_item_ibfk_2`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `jejuplus`.`tb_schedule` (`schedule_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_promotion` (
  `promotion_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL DEFAULT NULL,
  `introduce` VARCHAR(45) NULL DEFAULT NULL,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  `start_date` TIMESTAMP NOT NULL,
  `end_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`promotion_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_promotionimg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_promotionimg` (
  `promotionimg_id` INT NOT NULL AUTO_INCREMENT,
  `promotion_id` INT NOT NULL,
  `img_path` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`promotionimg_id`),
  INDEX `promotion_id_idx` (`promotion_id` ASC) VISIBLE,
  CONSTRAINT `promotion_id`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `jejuplus`.`tb_promotion` (`promotion_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_recommended`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_recommended` (
  `user_id` INT NOT NULL,
  `contents_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `contents_id`),
  INDEX `contents_id` (`contents_id` ASC) VISIBLE,
  CONSTRAINT `tb_recommended_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`),
  CONSTRAINT `tb_recommended_ibfk_2`
    FOREIGN KEY (`contents_id`)
    REFERENCES `jejuplus`.`tb_contents` (`contents_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jejuplus`.`tb_review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jejuplus`.`tb_review` (
  `review_star` INT NULL DEFAULT NULL,
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `contents_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `review_content` TEXT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `review_recommend` INT NULL DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `contents_id` (`contents_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tb_review_ibfk_1`
    FOREIGN KEY (`contents_id`)
    REFERENCES `jejuplus`.`tb_contents` (`contents_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `tb_review_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `jejuplus`.`tb_user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `jejuplus` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
