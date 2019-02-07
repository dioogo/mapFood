package com.groupsix.mapFood.validation;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.service.CustomerService;
import com.groupsix.mapFood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderValidation {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    private Order order;

    public OrderValidation(Order order) {
        this.order = order;
    }

    public void verifyTotalOrder()
            throws TotalPriceException {

        boolean totalIsInvalid = !order.getTotal()
                    .equals(order.getOrderItems().stream()
                            .mapToInt(i -> i.getTotal())
                            .sum());

        if(totalIsInvalid) {
            throw new TotalPriceException();
        }
    }

    public void verifyCustomerAndRestaurantDistance()
            throws CustomerTooFarException {

        CustomerEntity customer = customerService.getCustomer(order.getCustomerId());
        RestaurantEntity restaurant = restaurantService.getRestaurant(order.getRestaurantId());

        if(customer.isNotInTheSameCity(restaurant.getLat(), restaurant.getLon())) {
            throw new CustomerTooFarException();
        }
    }

    public void verifyPricesFromItems(List<OrderItemEntity> orderItemsEntities)
            throws ItemsPriceException {

        boolean itemWithWrongPrice = orderItemsEntities.stream()
                .anyMatch(i -> !i.getTotal().equals(i.getProduct().getPrice() * i.getQuantity()));

        if(itemWithWrongPrice) {
            throw new ItemsPriceException();
        }
    }

    public void verifyItemsFromSameRestaurant(List<OrderItemEntity> orderItemsEntities)
            throws DiferentRestaurantException {

        boolean itemFromAnotherRestaurant = orderItemsEntities.stream()
                .anyMatch(i -> !i.getProduct()
                        .getRestaurant()
                        .getId()
                        .equals(order.getRestaurantId()));

        if(itemFromAnotherRestaurant) {
            throw new DiferentRestaurantException();
        }
    }
}
