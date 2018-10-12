package uv.sokolovsky.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.util.DateTimeUtil;
import uv.sokolovsky.web.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController extends AbstractDishController {
    static final String REST_URL = "/rest/dishes";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@RequestParam (value="restaurantId", required = false) Integer restaurantId) {
        return super.getByDate(DateTimeUtil.DEFAULT_DATE, restaurantService.get(restaurantId));
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated(View.Web.class) @RequestBody Dish dish) {
        Dish created = super.create(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish,@PathVariable("id") int id) {
        super.update(dish, id);
    }

    @GetMapping(value = "/getDishesByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByDate(@RequestParam ("date") LocalDate date,
                                @RequestParam ("restaurantId") Integer restaurantId) {
        return super.getByDate(date, restaurantService.get(restaurantId));
    }
}
