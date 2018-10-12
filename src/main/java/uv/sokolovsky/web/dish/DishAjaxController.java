package uv.sokolovsky.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.to.DishTo;
import uv.sokolovsky.util.DateTimeUtil;
import uv.sokolovsky.util.DishUtil;
import uv.sokolovsky.web.View;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ajax/dishes")
public class DishAjaxController extends AbstractDishController{
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@RequestParam (value="restaurantId", required = false) Integer restaurantId) {
        if (restaurantId != null) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            return super.getByDate(DateTimeUtil.DEFAULT_DATE, restaurant);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Validated(View.Web.class) DishTo dishTo) {
        Restaurant restaurant = restaurantService.get(dishTo.getRestaurantId());
        if (dishTo.isNew()) {
            super.create(DishUtil.createNewFromTo(dishTo, restaurant));
        } else {
            super.update(dishTo, dishTo.getId());
        }
    }

    @PostMapping(value = "/getDishesByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByDate(@RequestParam (value="date", required = false) LocalDate date,
                                @RequestParam (value="restaurantId", required = false) Integer restaurantId) {
        if (date == null) {
            return super.getByDate(DateTimeUtil.DEFAULT_DATE, restaurantService.get(restaurantId));
        }
        return super.getByDate(date, restaurantService.get(restaurantId));
    }
}
