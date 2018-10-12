package uv.sokolovsky.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.to.RestaurantTo;

import java.util.List;

import static uv.sokolovsky.util.ValidationUtil.assureIdConsistent;
import static uv.sokolovsky.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(AbstractRestaurantController.class);

    @Autowired
    private RestaurantService service;

    public Restaurant get(int id) {
        log.info("get restaurant", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete restaurant", id);
        service.delete(id);
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant", restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant", restaurant);
        service.update(restaurant);
    }

    public void update(RestaurantTo restaurantTo, int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        service.update(restaurantTo);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }
}
