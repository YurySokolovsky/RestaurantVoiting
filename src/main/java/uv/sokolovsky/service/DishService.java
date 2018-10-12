package uv.sokolovsky.service;

import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.to.DishTo;
import uv.sokolovsky.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface DishService {

    Dish get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Dish> getAll(Restaurant restaurant);

    List<Dish> getByDate(LocalDate date, Restaurant restaurant);

    void update(Dish dish);

    void update(DishTo DishTo);

    Dish create(Dish dish);
}
