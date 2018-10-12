package uv.sokolovsky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.repository.DishRepository;
import uv.sokolovsky.to.DishTo;
import uv.sokolovsky.util.DishUtil;

import java.time.LocalDate;
import java.util.List;

import static uv.sokolovsky.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {
    private DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) { this.repository = repository;}

    @Override
    public Dish get(int id)  {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public void update(Dish dish) {
        Assert.notNull(dish, "restaurant must not be null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    @Transactional
    @Override
    public void update(DishTo dishTo) {
        Dish dish = get(dishTo.getId());
        repository.save(DishUtil.updateFromTo(dish, dishTo));
    }

    @Override
    public List<Dish> getAll(Restaurant restaurant) {
        return repository.getAll(restaurant);
    }

    @Override
    public List<Dish> getByDate(LocalDate date, Restaurant restaurant) {
        Assert.notNull(date, "date must not be null");
        return repository.getByDate(date, restaurant);
    }
}
