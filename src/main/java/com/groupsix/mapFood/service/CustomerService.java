package com.groupsix.mapFood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Optional<CustomerEntity> findById(Integer id) {
		return customerRepository.findById(id);
	}
}
