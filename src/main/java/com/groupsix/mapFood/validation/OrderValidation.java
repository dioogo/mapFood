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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidation {

    // Usado no cálculo de distância entre coordenadas,
    // uma coordenada equivale a 111Km, então 0.5 ~= 56Km
    private static Double MAX_DISTANCE = 0.5;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    public OrderValidation() {}

    public void verifyTotalOrder(Order order)
            throws TotalPriceException {

        boolean totalIsInvalid = !order.getTotal()
                    .equals(order.getOrderItems().stream()
                            .mapToInt(i -> i.getTotal())
                            .sum());

        if(totalIsInvalid) {
            throw new TotalPriceException();
        }
    }

    public void verifyCustomerAndRestaurantDistance(Order order)
            throws CustomerTooFarException {

        CustomerEntity customer = customerService.findById(order.getCustomerId()).get();
        RestaurantEntity restaurant = restaurantService.findById(order.getRestaurantId()).get();

        Double distanceFromCustomerToRestaurant = Math.sqrt(
                Math.pow(customer.getLon() - restaurant.getLon(), 2) +
                Math.pow(customer.getLat() - restaurant.getLat(), 2));

        if(distanceFromCustomerToRestaurant > MAX_DISTANCE) {
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

    public void verifyItemsFromSameRestaurant(List<OrderItemEntity> orderItemsEntities, Order order)
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
