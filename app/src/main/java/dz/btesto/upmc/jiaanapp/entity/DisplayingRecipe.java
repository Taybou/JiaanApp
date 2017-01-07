package dz.btesto.upmc.jiaanapp.entity;

/**
 * Created by besto on 16/12/16.
 */

public class DisplayingRecipe {

    private int recipeId;
    private String title;
    private String imageUrl;
    private String likes ;

    public DisplayingRecipe(int recipeId, String title, String imageUrl, String likes) {
        this.recipeId = recipeId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.likes = likes;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
