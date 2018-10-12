package uv.sokolovsky.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.util.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static uv.sokolovsky.testData.DishTestData.*;
import static uv.sokolovsky.testData.RestaurantTestData.RESTAURANT1;

public class DishServiceTest extends AbstractServiceTest {
    @Autowired
    protected DishService service;

    @Test
    public void create() throws Exception {
        Dish created = getCreated();
        service.create(created);
        assertMatch(service.getByDate(LocalDate.now(),RESTAURANT1), created, DISH3, DISH4);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicate() throws Exception {
        service.create(new Dish("Каша гречневая", new BigDecimal("2.70"), RESTAURANT1, LocalDate.now()));
    }

    @Test
    public void get() throws Exception {
        Dish dish = service.get(DISH_ID);
        assertMatch(dish, DISH1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_ID + 6);
        assertMatch(service.getByDate(LocalDate.now(),RESTAURANT1), DISH4);

    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(RESTAURANT1), dishes);
    }

    @Test
    public void getByDate() throws Exception {
        assertMatch(service.getByDate(LocalDate.of(2018, 05, 05),RESTAURANT1), DISH2, DISH1);
    }

    @Test
    public void update() throws Exception {
        Dish updated = getUpdated();
        service.update(updated);
        assertMatch(service.getByDate(LocalDate.now(),RESTAURANT1), updated, DISH4);
    }

    @Test(expected = DataAccessException.class)
    public void updateDuplicate() throws Exception {
        service.update(new Dish("Каша гречневая", new BigDecimal("1.00"), RESTAURANT1, LocalDate.now()));
    }
}