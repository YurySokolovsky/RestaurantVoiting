package uv.sokolovsky.testData;

import uv.sokolovsky.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uv.sokolovsky.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT_ID = START_SEQ + 3;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_ID, "Ресторан", "Одоевского, 1");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_ID + 2, "Закусочная", "Козлова, 38");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT_ID + 1, "Столовая", "Коласа, 17");

    public static List<Restaurant> restaurants =  Arrays.asList(RESTAURANT2, RESTAURANT1, RESTAURANT3);


    public static Restaurant getCreated() {
        return new Restaurant("NewRestaurant","NewAddress, 1");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant", "UpdatedAddress, 1");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
