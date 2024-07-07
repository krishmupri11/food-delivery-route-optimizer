package com.optimal.route.food_delivery.service;

import com.optimal.route.food_delivery.constant.Constant;
import com.optimal.route.food_delivery.model.Customer;
import com.optimal.route.food_delivery.model.Location;
import com.optimal.route.food_delivery.model.Restaurant;
import com.optimal.route.food_delivery.rest.request.OptimalRouteRequest;
import com.optimal.route.food_delivery.rest.response.OptimalRouteResponse;
import com.optimal.route.food_delivery.strategy.DistanceCalculationsStrategy;
import com.optimal.route.food_delivery.strategy.HaversineDistanceCalStrategy;
import com.optimal.route.food_delivery.util.PermutationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryOptimizerService {

    public OptimalRouteResponse getOptimalRoute(OptimalRouteRequest request){
        DistanceCalculationsStrategy distanceCalculationsStrategy = new HaversineDistanceCalStrategy();
        return findOptimalRoute(request, distanceCalculationsStrategy);
    }

    private static double travelTime(double distance) {
        return distance / Constant.AVERAGE_SPEED_KMH;
    }

    /**
     *
     * @param deliveryPersonLocation
     * @param restaurants
     * @param customers
     * @param route
     * @return distance
     */
    private static double calculateRouteTime(Location deliveryPersonLocation,
                                             List<Location> restaurants,
                                             List<Double> prepTimes,
                                             List<Location> customers,
                                             List<Integer> route,
                                             DistanceCalculationsStrategy distanceCalculationsStrategy
    ) {
        double totalTime = 0.0;
        Location currentLocation = deliveryPersonLocation;

        for (int i = 0; i < route.size(); i++) {
            int index = route.get(i);
            Location nextLocation;
            if (index < restaurants.size()) {
                // Going to a restaurant
                nextLocation = new Location(
                        restaurants.get(index).getLatitude(),
                        restaurants.get(index).getLongitude());

                totalTime += travelTime(
                        distanceCalculationsStrategy.calculateDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
                                nextLocation.getLatitude(),
                                nextLocation.getLongitude()));

                totalTime += prepTimes.get(index);
            } else {
                // Going to a customer
                int customerIndex = index - restaurants.size();
                nextLocation = new Location(customers.get(customerIndex).getLatitude(),
                        customers.get(customerIndex).getLongitude());

                totalTime += travelTime(
                        distanceCalculationsStrategy.calculateDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
                                nextLocation.getLatitude(), nextLocation.getLongitude()));
            }

            currentLocation = nextLocation;
        }

        return totalTime;
    }

    /**
     * This function will yield optimal route
     * @param request
     * @param distanceCalculationsStrategy
     * @return
     */
    public static OptimalRouteResponse findOptimalRoute(OptimalRouteRequest request, DistanceCalculationsStrategy distanceCalculationsStrategy) {
        List<Restaurant> restaurantList = request.getRestaurantList();
        List<Customer> customerList = request.getCustomerList();
        int totalOrder = restaurantList.size();

        List<List<Integer>> validPermutations = new ArrayList<>();
        PermutationUtil.generateValidPermutations(totalOrder, new ArrayList<>(), new boolean[totalOrder], new boolean[totalOrder],
                validPermutations);

        double minTime = Double.MAX_VALUE;
        List<Integer> bestRoute = new ArrayList<>();

        for (List<Integer> perm : validPermutations) {

            double currentTime = calculateRouteTime(request.getDeliveryPersonLocation(),
                    restaurantList.stream().map(Restaurant::getLocation).collect(Collectors.toList()),
                    restaurantList.stream().map(Restaurant::getPreparationTime).collect(Collectors.toList()),
                    customerList.stream().map(Customer::getLocation).collect(Collectors.toList()),
                    perm, distanceCalculationsStrategy);

            if (currentTime < minTime) {
                minTime = currentTime;
                bestRoute = new ArrayList<>(perm);
            }
        }

        List<String> optimalPath = new ArrayList<>();
        for (int index : bestRoute) {
            if (index < totalOrder) {
                optimalPath.add("Restaurant " + (index + 1));
            } else {
                optimalPath.add("Customer " + (index - totalOrder + 1));
            }
        }

        return new OptimalRouteResponse(minTime, optimalPath);
    }


}
