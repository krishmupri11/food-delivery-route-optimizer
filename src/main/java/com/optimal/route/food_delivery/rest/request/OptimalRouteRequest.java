package com.optimal.route.food_delivery.rest.request;

import com.optimal.route.food_delivery.model.Customer;
import com.optimal.route.food_delivery.model.Location;
import com.optimal.route.food_delivery.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@AllArgsConstructor
@Data
public class OptimalRouteRequest {
    private Location deliveryPersonLocation;
    private List<Restaurant> restaurantList;
    private List<Customer> customerList;
}
