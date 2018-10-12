package uv.sokolovsky.model;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import uv.sokolovsky.web.View;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "restaurant_id"}, name = "dishes_unique_description_restaurant_menu_date_idx")})
public class Dish extends AbstractBaseEntity {
    @Column(name = "description", nullable = false)
    @Size(min = 2, max = 250)
    @NotEmpty
    @SafeHtml(groups = {View.Web.class})
    private String description;

    @Column(name = "cost", nullable = false)
    @Range(min = 0)
    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "menu_date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate menuDate;

    public Dish() {
    }

    public Dish(Integer id, String description, BigDecimal cost, LocalDate menuDate) {
        super(id);
        this.description=description;
        this.cost=cost;
        this.menuDate=menuDate;
    }

    public Dish(String description, BigDecimal cost, Restaurant restaurant, LocalDate menuDate) {
        this(null, description, cost, restaurant, menuDate);
    }

    public Dish(Integer id, String description, BigDecimal cost, Restaurant restaurant, LocalDate menuDate) {
        super(id);
        this.description = description;
        this.cost = cost;
        this.restaurant = restaurant;
        this.menuDate = menuDate;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString(){
        return 	"Dish ["+
                "id=" + id +
                "description=" + description +
                "cost=" + cost +
                "menuDate=" + menuDate +
                "]";
    }
}
