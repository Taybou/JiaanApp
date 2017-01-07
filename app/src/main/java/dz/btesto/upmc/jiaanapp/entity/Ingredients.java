package dz.btesto.upmc.jiaanapp.entity;

import java.io.Serializable;

/**
 * Created by besto on 11/12/16.
 */

public class Ingredients implements Serializable {

    private  int ingredientsId;
    private String imageUrl;
    private String name;
    private boolean state ;


    public Ingredients(int ingredientsId, String imageUrl, String name,boolean state) {
        this.ingredientsId = ingredientsId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.state = state ;

    }

    public int getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(int ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
