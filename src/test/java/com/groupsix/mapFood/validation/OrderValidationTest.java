package com.groupsix.mapFood.validation;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.CustomerRepository;
import com.groupsix.mapFood.repository.RestaurantRepository;
import com.groupsix.mapFood.service.CustomerService;
import com.groupsix.mapFood.service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderValidationTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private OrderValidation orderValidation;

    @Test
    public void testVerifyTotalOrder() {

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        item1.setTotal(200);
        item2.setTotal(600);

        orderItems.add(item1);
        orderItems.add(item2);

        order.setTotal(800);
        order.setOrderItems(orderItems);

        try {
            orderValidation.verifyTotalOrder(order);
        } catch (TotalPriceException e) {
            fail();
        }
    }

    @Test
    public void testVerifyTotalOrderWithWrongTotal() {

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        item1.setTotal(200);
        item2.setTotal(600);

        orderItems.add(item1);
        orderItems.add(item2);

        order.setTotal(700);
        order.setOrderItems(orderItems);

        try {
            orderValidation.verifyTotalOrder(order);
        } catch (TotalPriceException e) {
            return;
        }
        fail();
    }

    @Test
    public void testVerifyCustomerAndRestaurantDistance() {

        Order order = new Order();
        CustomerEntity customer = new CustomerEntity();
        RestaurantEntity restaurant = new RestaurantEntity();

        customer.setId(9);
        customer.setLon(-0.44);
        customer.setLat(0.45);

        restaurant.setId(5);
        restaurant.setLon(-0.48);
        restaurant.setLat(0.42);

        when(customerService.findById(9)).thenReturn(Optional.of(customer));
        when(restaurantService.findById(5)).thenReturn(Optional.of(restaurant));

        order.setCustomerId(9);
        order.setRestaurantId(5);

        try {
            orderValidation.verifyCustomerAndRestaurantDistance(order);
        } catch (CustomerTooFarException e) {
            fail();
        }
    }

    @Test
    public void testVerifyCustomerTooFarFromTheRestaurant() {

        Order order = new Order();
        CustomerEntity customer = new CustomerEntity();
        RestaurantEntity restaurant = new RestaurantEntity();

        customer.setId(9);
        customer.setLon(-2.44);
        customer.setLat(2.45);

        restaurant.setId(5);
        restaurant.setLon(-31.48);
        restaurant.setLat(31.42);

        when(customerService.findById(9)).thenReturn(Optional.of(customer));
        when(restaurantService.findById(5)).thenReturn(Optional.of(restaurant));

        order.setCustomerId(9);
        order.setRestaurantId(5);

        try {
            orderValidation.verifyCustomerAndRestaurantDistance(order);
        } catch (CustomerTooFarException e) {
            return;
        }
        fail();
    }

    @Test
    public void testVerifyPricesFromItems() {

        List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
        ProductEntity product1 = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        OrderItemEntity item1 = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();

        product1.setPrice(50);
        product2.setPrice(100);

        item1.setProduct(product1);
        item1.setQuantity(3);
        item1.setTotal(150);
        item2.setProduct(product2);
        item2.setQuantity(4);
        item2.setTotal(400);

        orderItemsEntities.add(item1);
        orderItemsEntities.add(item2);

        try {
            orderValidation.verifyPricesFromItems(orderItemsEntities);
        } catch (ItemsPriceException e) {
            fail();
        }
    }

    @Test
    public void testVerifyPricesFromItemsWithWrongTotal() {

        List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
        ProductEntity product1 = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        OrderItemEntity item1 = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();

        product1.setPrice(50);
        product2.setPrice(100);

        item1.setProduct(product1);
        item1.setQuantity(3);
        item1.setTotal(200);
        item2.setProduct(product2);
        item2.setQuantity(4);
        item2.setTotal(300);

        orderItemsEntities.add(item1);
        orderItemsEntities.add(item2);

        try {
            orderValidation.verifyPricesFromItems(orderItemsEntities);
        } catch (ItemsPriceException e) {
            return;
        }
        fail();
    }

    @Test
    public void testVerifyItemsFromSameRestaurant() {

        Order order = new Order();
        List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
        ProductEntity product1 = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        RestaurantEntity restaurant1 = new RestaurantEntity();
        RestaurantEntity restaurant2 = new RestaurantEntity();
        OrderItemEntity item1 = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();

        order.setRestaurantId(10);
        restaurant1.setId(10);
        restaurant2.setId(10);

        product1.setRestaurant(restaurant1);
        product2.setRestaurant(restaurant2);

        item1.setProduct(product1);
        item2.setProduct(product2);

        orderItemsEntities.add(item1);
        orderItemsEntities.add(item2);

        try {
            orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);
        } catch (DiferentRestaurantException e) {
            fail();
        }
    }

    @Test
    public void testVerifyItemsFromSameRestaurantWithDiferentRestaurants() {

        Order order = new Order();
        List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
        ProductEntity product1 = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        RestaurantEntity restaurant1 = new RestaurantEntity();
        RestaurantEntity restaurant2 = new RestaurantEntity();
        OrderItemEntity item1 = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();

        order.setRestaurantId(10);
        restaurant1.setId(11);
        restaurant2.setId(10);

        product1.setRestaurant(restaurant1);
        product2.setRestaurant(restaurant2);

        item1.setProduct(product1);
        item2.setProduct(product2);

        orderItemsEntities.add(item1);
        orderItemsEntities.add(item2);

        try {
            orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);
        } catch (DiferentRestaurantException e) {
            return;
        }
        fail();
    }
}
