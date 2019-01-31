package com.groupsix.mapFood.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.pojo.OrderDelivery;

@Repository
public class OrderDeliveryDAOImpl implements OrderDeliveryDAO {

	private static final String INSERT_ORDER_DELIVERY = "INSERT INTO `mapfood`.`order_delivery` (`order_id`, `destination_lat`, `destination_lon`) VALUES (?, ?, ?);";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer createOrderDelivery(OrderDelivery orderDelivery) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		   jdbcTemplate.update(connection -> {
		        PreparedStatement ps = connection
		          .prepareStatement(INSERT_ORDER_DELIVERY, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, orderDelivery.getOrderId());
		        ps.setDouble(2, orderDelivery.getDestinationLat());
		        ps.setDouble(3, orderDelivery.getDestinationLon());
		          return ps;
		        }, keyHolder);
		 
		   return keyHolder.getKey().intValue();
	}

}
