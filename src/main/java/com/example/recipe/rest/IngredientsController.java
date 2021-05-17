package com.example.recipe.rest;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipe.services.IngredientsService;

@RestController
@RequestMapping("/ingredients")
@Produces(MediaType.APPLICATION_JSON)

public class IngredientsController {

	@Autowired
	private IngredientsService ingredientsService;

	@GetMapping("/all")
	public List<String> getAllIngredients() {
		return ingredientsService.getAllIngredients();
	}
}
