package model.entity;

import java.sql.Date;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class Invoice implements Entity {
    private int id;
    private int userId;
    private Date orderedAt;
    private int price;
    private List<Cupcake> cupcakes;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    public void setCupcakes(List<Cupcake> cupcakes) {
        this.cupcakes = cupcakes;
    }
}
