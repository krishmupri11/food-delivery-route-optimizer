package com.optimal.route.food_delivery.rest.api;

import com.optimal.route.food_delivery.rest.request.OptimalRouteRequest;
import com.optimal.route.food_delivery.rest.response.OptimalRouteResponse;
import com.optimal.route.food_delivery.service.DeliveryOptimizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DeliveryOptimizerRes {
    @Autowired
    DeliveryOptimizerService deliveryOptimizerService;

    @GetMapping("/optimal-route")
    public OptimalRouteResponse getOptimalRoute(@RequestBody OptimalRouteRequest request){
        return deliveryOptimizerService.getOptimalRoute(request);
    }

}
