package uv.sokolovsky.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.to.RestaurantTo;
import uv.sokolovsky.util.RestaurantUtil;
import uv.sokolovsky.web.View;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/restaurants")
public class RestaurantAjaxController extends AbstractRestaurantController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Validated(View.Web.class) RestaurantTo restaurantTo) {
        if (restaurantTo.isNew()) {
            super.create(RestaurantUtil.createNewFromTo(restaurantTo));
        } else {
            super.update(restaurantTo, restaurantTo.getId());
        }
    }
}