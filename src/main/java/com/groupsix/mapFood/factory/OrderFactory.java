package com.groupsix.mapFood.factory;

import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {

    public Order getInstance(OrderEntity orderEntity){
        Order order = new Order();
        order.setId(orderEntity.getId());

        return order;

    }
}
