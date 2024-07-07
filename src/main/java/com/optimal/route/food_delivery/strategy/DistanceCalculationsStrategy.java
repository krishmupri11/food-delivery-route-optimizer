package com.optimal.route.food_delivery.strategy;

public interface DistanceCalculationsStrategy {
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
