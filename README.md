# Best Route Problem(Food Delivery Route optimizer)

## Overview

It is based on Java Spring Boot application 
# AIM : 

To calculate the optimal delivery route for a delivery person. 
It considers multiple restaurant and customer locations and
optimizes the route to minimize the total delivery time.


## Working flow

The Delivery Optimizer application calculates the optimal delivery route using the following steps:

1. **Computation of distance using Haversine distance method** between two geographical points.
2. **Computation of travel time to deliver to all customers** based on the distance and a  speed(20 km/hr).
3. **Generate all valid permutations** of restaurant and customer locations.
4. **Evaluation each permutation** the one with minimum delivery time to deliver to all locations.
5. **Return the optimal route and time** in a JSON response.

## API Endpoint

### `GET /api/v1/optimal-route`

This endpoint calculates the optimal delivery route based on the provided driver, restaurant, and
customer locations, as well as preparation times for each restaurant.

#### Request Payload

```json
{
    "deliveryPersonLocation": {
        "latitude": 12.9716,
        "longitude": 77.5946
    },
    "restaurantList": [
        {
            "location": {
                "latitude": 12.9352,
                "longitude": 77.6257
            },
            "preptime": 0.5
        },
        {
            "location": {
                "latitude": 13.0352,
                "longitude": 77.5970
            },
            "preptime": 0.3
        }
    ],
    "customerList": [
        {
            "location": {
                "latitude": 12.9784,
                "longitude": 77.6408
            }
        },
        {
            "location": {
                "latitude": 13.1152,
                "longitude": 77.5970
            }
        }
    ]
}
```

#### Response

```json
{
    "time": "1.36",
    "path": [
        "Restaurant 1",
        "Customer 1",
        "Restaurant 2",
        "Customer 2"
    ]
}
```

### Example Usage

You can test the API using a tool like `curl` or Postman.

#### Using `curl`
```
curl --location --request GET 'http://localhost:8080/api/v1/optimal-route' \
--header 'Content-Type: application/json' \
--data '{
    "deliveryPersonLocation": {
        "latitude": 12.9716,
        "longitude": 77.5946
    },
    "restaurantList": [
        {
            "location": {
                "latitude": 12.9352,
                "longitude": 77.6257
            },
            "preptime": 0.5
        },
        {
            "location": {
                "latitude": 13.0352,
                "longitude": 77.5970
            },
            "preptime": 0.3
        }
    ],
    "customerList": [
        {
            "location": {
                "latitude": 12.9784,
                "longitude": 77.6408
            }
        },
        {
            "location": {
                "latitude": 13.1152,
                "longitude": 77.5970
            }
        }
    ]
}'
```


## Classes and Methods

### `DeliveryOptimizerRes`

Handles the `/api/v1/optimal-route` endpoint, converting JSON input into appropriate objects and calling
the `DeliveryOptimizerService` to calculate the optimal route.

### `DeliveryOptimizerService`

Contains the `getOptimalRoute` method that uses the `DeliveryOptimizer` utility to compute the
optimal delivery route.

### `DistanceCalculationsStrategy`

Added an interface so that in future if we want another way to calculate distance we can easily extend to that.
### `HaversineDistanceCalStrategy`
concrete implmentation of interface where actual logic to calculate distance is written

### OptimalRouteRequest
Request class for api

### OptimalRouteResponse
Response class for api

### Models
- `Restaurant`
- `Customer`
- `Location`


These classes represent involved entities.

## Constants

### `Constant`

constants such as `EARTH_RADIUS_KM` and `AVERAGE_SPEED_KMH` used in the distance and time calculations are defined here.

## Building and Running the Application

1. **Build the project** using Maven:
   ```sh
   mvn clean install
   ```

2. **Run the application**:
   ```sh
   java -jar target/food-delivery-0.0.1.jar
   ```
