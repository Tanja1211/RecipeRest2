package com.example.recipe.rest;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipe.domain.Recipe;
import com.example.recipe.domain.RecipeAllDTO;
import com.example.recipe.domain.RecipeDTO;
import com.example.recipe.services.RecipeService;

@RestController
@RequestMapping("/recipe")
@Produces(MediaType.APPLICATION_JSON)

public class RecipeController {

	@Autowired
	private RecipeService recipeService;
		
	@PostMapping("/add")
	public void addRecipe(@RequestBody Recipe recipe) {
		recipeService.addRecipe(recipe);	
	}
	
	@GetMapping("/{recipe_id}")
	public RecipeDTO getRecipeByID(@PathVariable("recipe_id") Long recipeId) {
		return recipeService.getRecipeById(recipeId);
	}
	
	@GetMapping("/all")
	public List<RecipeAllDTO> getAllRecipes() {
		return recipeService.getAllRecipes();
	}
	
	@GetMapping("/all-by-ingredient-name/{ingredient-name}")
	public List<RecipeAllDTO> getAllRecipesByIngredientName(@PathVariable("ingredient-name") String ingredientName) {
		return recipeService.getAllRecipesByIngredientName(ingredientName);
	}
	
	@GetMapping("/all-by-characters/{characters}")
	public List<RecipeAllDTO> getAllRecipesFilterByName(@PathVariable("characters") String characters) {
		return recipeService.getAllRecipesFilterByName(characters);
	}
	
	@GetMapping("/all-by-preparation-time/{minutes}")
	public List<RecipeAllDTO> getAllRecipesFilterByPreparationTime(@PathVariable("minutes") Integer minutes) {
		return recipeService.getAllRecipesFilterByPreparationTime(minutes);
	}
	
}
