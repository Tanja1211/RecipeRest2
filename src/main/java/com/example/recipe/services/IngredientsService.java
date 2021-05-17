package com.example.recipe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe.repository.IngredientsRepository;

@Service
public class IngredientsService {
	
	@Autowired
	private IngredientsRepository ingredientsRepository;
		
	public List<String> getAllIngredients() {
		return ingredientsRepository.findAllIngredients();
	}
	
}
