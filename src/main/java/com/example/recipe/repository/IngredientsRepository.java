package com.example.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.recipe.domain.Ingredients;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long>  {
	
	@Query(value = "select name from ingredients", nativeQuery = true)
	List<String> findAllIngredients();
	
	Ingredients findByName(String name);
	
	Ingredients findByIngredientId(Long id);

}
