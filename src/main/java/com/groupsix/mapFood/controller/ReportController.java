package com.groupsix.mapFood.controller;


import com.groupsix.mapFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> listInformations(@PathVariable Integer id){
        if(orderService.informationsRestaurant(id).isEmpty()){
            return ResponseEntity.ok("Você ainda não tem pedidos :(");
        }else{
            return ResponseEntity.ok(orderService.informationsRestaurant(id));
        }

    }

}
