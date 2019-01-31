package com.groupsix.mapFood.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.pojo.OrderItem;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

	private static final String INSERT_ORDER_ITEM = "INSERT INTO `mapfood`.`order_item` (`order_id`, `product_id`, `quantity`, `name`, `total`, `item_price`) VALUES (?, ?, ?, ?, ?, ?);";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer createOrderItem(OrderItem orderItem) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		   jdbcTemplate.update(connection -> {
		        PreparedStatement ps = connection
		          .prepareStatement(INSERT_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, orderItem.getOrderId());
		        ps.setInt(2, orderItem.getProductId());
		        ps.setInt(3, orderItem.getQuantity());
		        ps.setString(4, orderItem.getName());
		        ps.setInt(5, orderItem.getTotal());
		        ps.setInt(6, orderItem.getItemPrice());
		          return ps;
		        }, keyHolder);
		 
		   return keyHolder.getKey().intValue();
	}

	
}
