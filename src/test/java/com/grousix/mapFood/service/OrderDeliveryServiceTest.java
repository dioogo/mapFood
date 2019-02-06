package com.grousix.mapFood.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.repository.CustomerRepository;
import com.groupsix.mapFood.service.OrderDeliveryService;

@RunWith(MockitoJUnitRunner.class)
public class OrderDeliveryServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private OrderDeliveryService service;
	
	@Test
	public void testGetOrderDelivery() {
		
		final CustomerEntity customer = new CustomerEntity();
		customer.setLat(new Double(1.0));
		customer.setLon(new Double(2.0));
		
		when(customerRepository.getOne(5)).thenReturn(customer);
		
		//final OrderDeliveryEntity orderDeliveryEntity = service.getOrderDelivery(5);
		
		//assertEquals(new Double(1.0), orderDeliveryEntity.getDestinationLat());
		//assertEquals(new Double(2.0), orderDeliveryEntity.getDestinationLon());
	}
}
