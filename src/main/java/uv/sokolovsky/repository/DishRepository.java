package uv.sokolovsky.repository;

import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    // null if updated dish do not belong to restaurantId
    Dish save(Dish dish);

    // false if dish do not
    boolean delete(int id);

    // null if dish do not belong to restaurantId
    Dish get(int id);

    // ORDERED description
    List<Dish> getAll(Restaurant restaurant);

    // ORDERED description
    List<Dish> getByDate (LocalDate date, Restaurant restaurant);
}
