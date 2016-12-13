package dz.btesto.upmc.jiaanapp.entity;

/**
 * Created by besto on 11/12/16.
 */

public class Ingredients {

    private  int ingredientsId;
    private String imageUrl;
    private String name;

    public Ingredients(int ingredientsId, String imageUrl, String name) {
        this.ingredientsId = ingredientsId;
        this.imageUrl = imageUrl;
        this.name = name;
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
}
