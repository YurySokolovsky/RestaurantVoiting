package uv.sokolovsky.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.service.DishService;
import uv.sokolovsky.to.DishTo;

import java.time.LocalDate;
import java.util.List;

import static uv.sokolovsky.util.ValidationUtil.assureIdConsistent;
import static uv.sokolovsky.util.ValidationUtil.checkNew;

public abstract class AbstractDishController {
    private static final Logger log = LoggerFactory.getLogger(AbstractDishController.class);

    @Autowired
    private DishService service;

    public Dish get(int id) {
        log.info("get dish {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete dish {}", id);
        service.delete(id);
    }

    public Dish create(Dish dish) {
        checkNew(dish);
        log.info("create {} for restaurant {}", dish, dish.getRestaurant().getId());
        return service.create(dish);
    }

    public void update(Dish dish, int id) {
        assureIdConsistent(dish, id);
        log.info("update {} for restaurant {}", dish, dish.getRestaurant().getRestaurantName());
        service.update(dish);
    }

    public void update(DishTo dishTo, int id) {
        log.info("update {} with id={} for restaurant {}" , dishTo, id, dishTo.getRestaurantId());
        assureIdConsistent(dishTo, id);
        service.update(dishTo);
    }

    public List<Dish> getAll(Restaurant restaurant) {
        log.info("getAll");
        return service.getAll(restaurant);
    }

    public List<Dish> getByDate(LocalDate date, Restaurant restaurant) {
        log.info("getByDate {} for restaurant {}", date, restaurant.getId());
        List<Dish> dishDateFiltered = service.getByDate(date, restaurant);
        return dishDateFiltered;
    }
}
