package uv.sokolovsky.util;

import uv.sokolovsky.model.Dish;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.to.DishTo;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {

    public static Dish createNewFromTo(DishTo newDish, Restaurant restaurant) {
        return new Dish(null, newDish.getDescription(), newDish.getCost(), restaurant, newDish.getMenuDate());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setDescription(dishTo.getDescription());
        dish.setCost(dishTo.getCost());
        dish.setMenuDate(dishTo.getMenuDate());
        return dish;
    }

    public static List <String> getDishesDescriptions (List <Dish> dishes) {
        return dishes.stream().map(Dish::getDescription).distinct().collect(Collectors.toList());
    }
}
