SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `excord`.`ec_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `excord`.`ec_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `tag` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `excord`.`ec_testcasetagmapping`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `excord`.`ec_testcasetagmapping` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `testcase_id` BIGINT NOT NULL ,
  `tag_id` BIGINT NOT NULL ,
  CONSTRAINT `tc_tag_mapping_fk1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `excord`.`ec_tag` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `tc_tag_mapping_fk2`
    FOREIGN KEY (`testcase_id`)
    REFERENCES `excord`.`ec_testcase` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE, 
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;