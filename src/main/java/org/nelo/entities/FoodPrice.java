package org.nelo.entities;

import javax.persistence.*;

@Entity
@Table(name = "FOOD_PRICES")
public class FoodPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int foodPriceId;

    private Integer breakfast;
    private Integer lunch;
    private Integer dinner;

    public int getFoodPriceId() {
        return foodPriceId;
    }

    public void setFoodPriceId(int foodPriceId) {
        this.foodPriceId = foodPriceId;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    public Integer getDinner() {
        return dinner;
    }

    public void setDinner(Integer dinner) {
        this.dinner = dinner;
    }
}
