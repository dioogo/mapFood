package com.groupsix.mapFood.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.pojo.Order;

@Repository
public class OrderDAOImpl implements OrderDAO {

	private static final String INSER_ORDER = "INSERT INTO `mapfood`.`order` (`customer_id`, `restaurant_id`, `estimated_time_to_delivery`, `total`) VALUES (?, ?, current_timestamp + INTERVAL 10 MINUTE, ?);";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer createOrder(Order order) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		   jdbcTemplate.update(connection -> {
		        PreparedStatement ps = connection
		          .prepareStatement(INSER_ORDER, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, order.getCustomerId());
		        ps.setInt(2, order.getRestaurantId());
		        ps.setInt(3, order.getTotal());
		          return ps;
		        }, keyHolder);
		 
		   return keyHolder.getKey().intValue();
	}

}
