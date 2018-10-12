package uv.sokolovsky.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {
    @Autowired
    private CrudDishRepository crudRepository;

    @Override
    public Dish save(Dish dish) {
         return crudRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) !=0;
    }

    @Override
    public Dish get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll(Restaurant restaurant) {
        return crudRepository.getAll(restaurant);
    }

    @Override
    public List<Dish> getByDate(LocalDate date, Restaurant restaurant) {
        return crudRepository.getByDate(date, restaurant);
    }
}
