CREATE TABLE `mapfood`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `restaurant_id` INT NOT NULL,
  `estimated_time_to_delivery` TIMESTAMP NOT NULL,
  `total` INT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `mapfood`.`order` 
ADD INDEX `fk_order_1_idx` (`customer_id` ASC) VISIBLE,
ADD INDEX `fk_order_2_idx` (`restaurant_id` ASC) VISIBLE;
;
ALTER TABLE `mapfood`.`order` 
ADD CONSTRAINT `fk_order_1`
  FOREIGN KEY (`customer_id`)
  REFERENCES `mapfood`.`customer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_order_2`
  FOREIGN KEY (`restaurant_id`)
  REFERENCES `mapfood`.`restaurant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE `mapfood`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `total` INT NOT NULL,
  `item_price` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_item_2_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `mapfood`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `mapfood`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `mapfood`.`order_delivery` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `destination_lat` DOUBLE NOT NULL,
  `destination_lon` DOUBLE NOT NULL,
  `motoboy_id` INT NULL DEFAULT NULL,
  `estimated_time_to_restaurant` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_delivery_1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_delivery_2_idx` (`motoboy_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_delivery_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `mapfood`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_delivery_2`
    FOREIGN KEY (`motoboy_id`)
    REFERENCES `mapfood`.`motoboy` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `mapfood`.`order_delivery` 
CHANGE COLUMN `estimated_time_to_restaurant` `estimated_time_to_restaurant` TIMESTAMP NULL DEFAULT NULL ;
