package uv.sokolovsky.service;

import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.to.RestaurantTo;
import uv.sokolovsky.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    void update(Restaurant restaurant);

    void update(RestaurantTo restaurantTo);

    List<Restaurant> getAll();
}
