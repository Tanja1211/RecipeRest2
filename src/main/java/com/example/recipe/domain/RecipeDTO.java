package com.example.recipe.domain;

import java.util.Map;

public class RecipeDTO {
	private Long recipeId;
	private String name;
	private String source;
	private Map<String, String> ingredients;
	private String preparationTime;
	private String instructions;
	public Long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Map<String, String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Map<String, String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
}
