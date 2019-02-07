package com.groupsix.mapFood.service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerEntity getCustomer(final Integer customerId) {
		return customerRepository.getOne(customerId);
	}
}
