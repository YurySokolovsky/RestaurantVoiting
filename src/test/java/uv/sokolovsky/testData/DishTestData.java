package uv.sokolovsky.testData;

import uv.sokolovsky.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uv.sokolovsky.model.AbstractBaseEntity.START_SEQ;
import static uv.sokolovsky.testData.RestaurantTestData.RESTAURANT1;

public class DishTestData {
    public static final int DISH_ID = START_SEQ + 6;

    public static final Dish DISH1=new Dish(DISH_ID, "Суп рассольник", new BigDecimal("3.50"), LocalDate.of(2018, 05 ,05));
    public static final Dish DISH2=new Dish(DISH_ID + 1, "Гуляш", new BigDecimal("3.90"), LocalDate.of(2018,05, 05));
    public static final Dish DISH3=new Dish(DISH_ID + 6, "Каша гречневая",new BigDecimal("2.70") , LocalDate.now());
    public static final Dish DISH4=new Dish(DISH_ID + 7, "Котлета по киевски",new BigDecimal("2.40"), LocalDate.now());

    public static List<Dish> dishes = Arrays.asList(DISH2, DISH3, DISH4, DISH1);

    public static Dish getCreated() {
        return new Dish ("NewDish", new BigDecimal("1.10"), RESTAURANT1, LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID + 6,"UpdatedDish", new BigDecimal("2.20"), RESTAURANT1, LocalDate.now());
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "menuDate");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "menuDate").isEqualTo(expected);
    }

}
