package dz.btesto.upmc.jiaanapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by besto on 11/12/16.
 */

public class Recipe implements Serializable {

    private int recipeId;
    private String title;
    private String instructions;
    private int preparationMinutes;
    private int cookingMinutes;
    private String imageUrl;
    private List<Ingredients> ingredientsList;

    public Recipe() {

    }

    public Recipe(int recipeId, String title, String instructions, int preparationMinutes, int cookingMinutes, String imageUrl) {
        this.recipeId = recipeId;
        this.title = title;
        this.instructions = instructions;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.imageUrl = imageUrl;
    }

    public Recipe(int recipeId, String title, String instructions, int preparationMinutes, int cookingMinutes, String imageUrl, List<Ingredients> ingredientsList) {
        this.recipeId = recipeId;
        this.title = title;
        this.instructions = instructions;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.imageUrl = imageUrl;
        this.ingredientsList = ingredientsList;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
