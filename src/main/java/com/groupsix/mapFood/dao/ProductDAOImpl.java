package com.groupsix.mapFood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.pojo.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	private static final String SELECT_BY_ID = "SELECT * from mapFood.product where id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Product getById(Integer id) {

		Product product = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_ID, id);

        for (Map<String, Object> row : rows) 
        {
             product = new Product();
             product.setId((int)row.get("id"));
             product.setDescription((String)row.get("description"));
             product.setPrice((int)row.get("price"));

             break;
        }
        
        return product;
	}
}
