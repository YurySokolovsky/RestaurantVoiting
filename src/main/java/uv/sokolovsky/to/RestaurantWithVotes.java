package uv.sokolovsky.to;

public class RestaurantWithVotes extends BaseTo {

    private String restaurantName;

    private String address;

    private Integer voteCount;

    public RestaurantWithVotes(Integer id, String restaurantName, String address, int voteCount) {
        super(id);
        this.restaurantName = restaurantName;
        this.address = address;
        this.voteCount = voteCount;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", restaurantName=" + restaurantName +
                ", address=" + address +
                ", voteCount" + voteCount +
                '}';
    }
}

