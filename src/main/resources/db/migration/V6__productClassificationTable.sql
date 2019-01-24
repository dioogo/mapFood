CREATE TABLE `mapfood`.`product_classification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `mapfood`.`product_classification` 
ADD COLUMN `restaurant_id` INT NOT NULL AFTER `name`,
ADD INDEX `fk_product_classification_1_idx` (`restaurant_id` ASC) VISIBLE;
;
ALTER TABLE `mapfood`.`product_classification` 
ADD CONSTRAINT `fk_product_classification_1`
  FOREIGN KEY (`restaurant_id`)
  REFERENCES `mapfood`.`restaurant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;