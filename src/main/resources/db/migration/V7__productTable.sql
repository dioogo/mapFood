CREATE TABLE `mapfood`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hash` VARCHAR(200) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `restaurant_id` INT NOT NULL,
  `classification_id` INT NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_1_idx` (`restaurant_id` ASC),
  INDEX `fk_product_2_idx` (`classification_id` ASC),
  CONSTRAINT `fk_product_1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `mapfood`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
   CONSTRAINT `fk_product_2`
    FOREIGN KEY (`classification_id`)
    REFERENCES `mapfood`.`product_classification` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
