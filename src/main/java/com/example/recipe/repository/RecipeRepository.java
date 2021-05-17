package com.example.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.recipe.domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	Recipe findByRecipeId(Long recipeId);

	@Query(value = "SELECT * FROM recipes r inner join recipe_ingredients ri on r.recipe_id = ri.recipe_id inner join ingredients i on ri.ingredient_id = i.ingredient_id where i.name = :name", nativeQuery = true)
	List<Recipe> findRecipeByIngredientName(@Param("name") String name);

	@Query(value = "SELECT * FROM recipes where name LIKE %:characters%", nativeQuery = true)
	List<Recipe> findRecipeByName(@Param("characters") String characters);

	@Query(value = "SELECT * FROM recipes where preparation_time < :minutes", nativeQuery = true)
	List<Recipe> findRecipeByPreparationTime(@Param("minutes") Integer minutes);

}
