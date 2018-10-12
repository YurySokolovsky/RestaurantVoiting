package uv.sokolovsky.to;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 250, message = "length must between 5 and 256 characters")
    @SafeHtml
    private String description;

    @NotNull
    @Range(min=0, message = "value can't be less than 0")
    @Digits(integer = 10, fraction = 2, message = "the value mustn't have more than 10 characters to the point and 2 after the point")
    private BigDecimal cost;

    @NotNull
    private LocalDate menuDate;

    protected Integer restaurantId;

    public DishTo () {}

    public DishTo (Integer id, String description, BigDecimal cost, LocalDate menuDate, Integer restaurantId) {
        super(id);
        this.description = description;
        this.cost = cost;
        this.menuDate = menuDate;
        this.restaurantId=restaurantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String toString(){
        return 	"Dish{" +
                "id=" + id +
                "description=" + description + '\'' +
                "cost=" +  cost + '\'' +
                "menuDate=" + menuDate +
                "restaurantId=" + restaurantId +
                "}";
    }
}
