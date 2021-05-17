package com.example.recipe.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe.domain.Recipe;
import com.example.recipe.domain.RecipeAllDTO;
import com.example.recipe.domain.RecipeDTO;
import com.example.recipe.domain.RecipeIngredients;
import com.example.recipe.repository.IngredientsRepository;
import com.example.recipe.repository.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private IngredientsRepository ingredientsRepository;

	public void addRecipe(Recipe recipe) {
		List<RecipeIngredients> recipeIngredients = recipe.getRecipeIngredients();
		for (RecipeIngredients recipeIngredientsToBeSaved : recipeIngredients) {
			recipeIngredientsToBeSaved.setIngredientId(
					ingredientsRepository.findByName(recipeIngredientsToBeSaved.getIngredientName()).getIngredientId());
		}
		recipe.setPreparationTime(preparationTimeToMinutes(recipe.getPreparationTime()));
		recipeRepository.save(recipe);
	}

	public RecipeDTO getRecipeById(Long recipeId) {
		Recipe recipe = this.recipeRepository.findByRecipeId(recipeId);
		if (recipe != null) {
			RecipeDTO recipeDTO = new RecipeDTO();
			Map<String, String> ingredientsMap = new HashMap<>();
			for (RecipeIngredients recipeIngredient : recipe.getRecipeIngredients()) {
				ingredientsMap.put(
						ingredientsRepository.findByIngredientId(recipeIngredient.getIngredientId()).getName(),
						recipeIngredient.getQuantity());
			}
			recipeDTO.setRecipeId(recipe.getRecipeId());
			recipeDTO.setSource(recipe.getSource());
			recipeDTO.setName(recipe.getName());
			recipeDTO.setPreparationTime(recipe.getPreparationTime() + " minutes");
			recipeDTO.setInstructions(recipe.getInstructions());
			recipeDTO.setIngredients(ingredientsMap);
			return recipeDTO;
		} else
			return null;
	}

	public List<RecipeAllDTO> getAllRecipes() {
		return getRecipesDetails(recipeRepository.findAll());
	}

	public List<RecipeAllDTO> getAllRecipesByIngredientName(String name) {
		return getRecipesDetails(recipeRepository.findRecipeByIngredientName(name));
	}

	public List<RecipeAllDTO> getAllRecipesFilterByName(String characters) {
		return getRecipesDetails(recipeRepository.findRecipeByName(characters));
	}

	public List<RecipeAllDTO> getAllRecipesFilterByPreparationTime(Integer minutes) {
		return getRecipesDetails(recipeRepository.findRecipeByPreparationTime(minutes));
	}

	public List<RecipeAllDTO> getRecipesDetails(List<Recipe> recipes) {
		List<RecipeAllDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes) {
			RecipeAllDTO r = getRecipeDTO(recipe);
			recipeDTOs.add(r);
		}
		return recipeDTOs;
	}

	public RecipeAllDTO getRecipeDTO(Recipe recipe) {
		RecipeAllDTO recipeDTO = new RecipeAllDTO();
		recipeDTO.setRecipeId(recipe.getRecipeId());
		recipeDTO.setName(recipe.getName());
		recipeDTO.setSource(recipe.getSource());
		recipeDTO.setNumberOfIngredients(recipe.getRecipeIngredients().size());
		recipeDTO.setPreparationTime(formatPreparationTime(recipe.getPreparationTime()));
		recipeDTO.setIngredients(formatReceipeIngredients(recipe.getRecipeIngredients()));
		recipeDTO.setInstructions(formatInstruction(recipe.getInstructions()));
		return recipeDTO;

	}

	private String preparationTimeToMinutes(String preparationTime) {
		String[] tokens = preparationTime.split(" ");
		if (tokens.length == 1) {
			return preparationTime;
		} else {
			int duration = 60 * Integer.parseInt(tokens[0]) + Integer.parseInt(tokens[1]);
			return String.valueOf(duration);
		}
	}

	private String formatPreparationTime(String preparationTime) {
		Duration duration = Duration.ofMinutes(Long.parseLong(preparationTime));
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();
		if (hours == 0) {
			return String.valueOf(minutes) + " minutes";
		} else {
			return String.format("%d hours %d minutes", hours, minutes);
		}

	}

	private List<String> formatReceipeIngredients(List<RecipeIngredients> recipeIngredients) {
		List<String> recipeIngredientsName = new ArrayList<>();
		List<RecipeIngredients> firstThreeElementsList = recipeIngredients.stream().limit(3)
				.collect(Collectors.toList());
		for (RecipeIngredients recipeIngredient : firstThreeElementsList) {
			recipeIngredientsName
					.add(ingredientsRepository.findByIngredientId(recipeIngredient.getIngredientId()).getName());
		}
		if (recipeIngredients.size() > 3) {
			recipeIngredientsName.add("...");
		}
		return recipeIngredientsName;

	}

	private String formatInstruction(String instructions) {
		String instructionToBeShown = "";
		if (instructions.length() <= 50) {
			instructionToBeShown = instructions;
		} else {
			int position = 49;
			if (!Character.isWhitespace(instructions.charAt(position))) {
				while (position < instructions.length()) {
					position++;
					if (Character.isWhitespace(instructions.charAt(position))) {
						break;
					}
				}
				instructionToBeShown = instructions.substring(0, position) + "...";
			} else {
				instructionToBeShown = instructions.substring(0, 50) + "...";
			}
		}
		return instructionToBeShown;
	}
}
