package uv.sokolovsky.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.restaurant=:restaurant ORDER BY d.description")
    List<Dish> getAll(@Param("restaurant") Restaurant restaurant);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT d FROM Dish d WHERE d.restaurant=:restaurant AND d.menuDate=:menuDate ORDER BY d.description")
    List<Dish> getByDate(@Param("menuDate") LocalDate menuDate, @Param("restaurant") Restaurant restaurant);
}
