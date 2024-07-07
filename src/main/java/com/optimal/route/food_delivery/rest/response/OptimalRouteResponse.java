package com.optimal.route.food_delivery.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OptimalRouteResponse {
    private String time;
    private List<String> path;
}
