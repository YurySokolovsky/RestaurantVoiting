package uv.sokolovsky.util;

import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.to.RestaurantTo;
import uv.sokolovsky.to.RestaurantWithVotes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class RestaurantUtil {

    private RestaurantUtil(){
    }

    public static List<RestaurantWithVotes> getRestaurantWithVotes (List <Restaurant> restaurants, List <Vote> votes) {
        return getFiltredWithVotes (restaurants, votes, restaurant -> true);
    }

    public static List <RestaurantWithVotes> getFiltredWithVotes (List <Restaurant> restaurants, List <Vote> votes, Predicate<Restaurant> filter) {
        Map <Restaurant, Long> emptyVote = restaurants.stream()
                .collect(Collectors.toMap(x -> x, x -> Long.valueOf(0)));

        Map <Restaurant, Long> voteByDate = votes.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant,  Collectors.counting()));

        Map <Restaurant, Long> voteCount = Stream.of(emptyVote, voteByDate)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::max));

        return restaurants.stream()
                .filter(filter)
                .map(restaurant -> createRestaurantWithVotes(restaurant, voteCount.get(restaurant).intValue()))
                .collect(toList());
    }

    public static RestaurantWithVotes createRestaurantWithVotes(Restaurant restaurant, int voteCount) {
        return new RestaurantWithVotes(restaurant.getId(), restaurant.getRestaurantName(), restaurant.getAddress(), voteCount);
    }

    public static Restaurant createNewFromTo(RestaurantTo newRestaurant) {
        return new Restaurant(null, newRestaurant.getRestaurantName(), newRestaurant.getAddress());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setRestaurantName(restaurantTo.getRestaurantName());
        restaurant.setAddress(restaurantTo.getAddress());
        return restaurant;
    }
}
