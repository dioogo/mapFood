package com.groupsix.mapFood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.pojo.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private static final String SELECT_BY_ID = "SELECT * from mapFood.customer where id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Customer getById(Integer id) {
		Customer customer = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_ID, id);

        for (Map<String, Object> row : rows) 
        {
        	customer = new Customer();
        	customer.setId((int)row.get("id"));
        	customer.setLat((double)row.get("lat"));
        	customer.setLon((double)row.get("lon"));

        	break;
        }
        
        return customer;
	}
}
