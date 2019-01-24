CREATE TABLE `mapfood`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hash` VARCHAR(200) NOT NULL,
  `city_id` INT NOT NULL,
  `lat` DOUBLE NOT NULL,
  `lon` DOUBLE NOT NULL,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_restaurant_1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_restaurant_1`
    FOREIGN KEY (`city_id`)
    REFERENCES `mapfood`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `mapfood`.`restaurant` 
CHANGE COLUMN `description` `restaurant_type_id` INT NOT NULL ,
ADD INDEX `fk_restaurant_2_idx` (`restaurant_type_id` ASC) VISIBLE;
;
ALTER TABLE `mapfood`.`restaurant` 
ADD CONSTRAINT `fk_restaurant_2`
  FOREIGN KEY (`restaurant_type_id`)
  REFERENCES `mapfood`.`restaurant_type` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
