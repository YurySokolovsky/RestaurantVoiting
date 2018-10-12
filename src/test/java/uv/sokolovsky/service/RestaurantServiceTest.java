package uv.sokolovsky.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.util.exception.NotFoundException;

import java.util.List;

import static uv.sokolovsky.testData.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void create() throws Exception {
        Restaurant created = getCreated();
        service.create(created);
        assertMatch(service.getAll(), created, RESTAURANT2, RESTAURANT1, RESTAURANT3);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameAddressCreate() throws Exception {
        service.create(new Restaurant("Ресторан", "Одоевского, 1"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(RESTAURANT_ID);
        assertMatch(service.getAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        assertMatch(restaurant, RESTAURANT1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(RESTAURANT_ID), updated);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameAddressUpdate() throws Exception {
        service.update(new Restaurant(RESTAURANT_ID,"Столовая", "Коласа, 17"));
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all, restaurants);
    }
}