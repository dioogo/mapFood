package com.groupsix.mapFood.factory;

import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.pojo.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class OrderFactory {

    public Order getInstance(OrderEntity orderEntity){
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setCustomerId(orderEntity.getCustomer().getId());
        order.setRestaurantId(orderEntity.getRestaurant().getId());
        order.setTotal(orderEntity.getTotal());


        return order;

    }
}
