package com.example.recipes_be.controller;

import org.springframework.web.bind.annotation.*;

import com.example.recipes_be.model.Recipe;
import com.example.recipes_be.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@PostMapping("/load")
	public List<Recipe> loadRecipes() {
		return recipeService.loadRecipesFromExternalApi();
	}

	@GetMapping
	public List<Recipe> getAllRecipes() {
		return recipeService.getAllRecipes();
	}

	@GetMapping("/cuisine/{cuisine}")
	public List<Recipe> getRecipesByCuisine(@PathVariable String cuisine) {
		return recipeService.getRecipesByCuisine(cuisine);
	}

	@GetMapping("/sort")
	public List<Recipe> getRecipesSorted(@RequestParam boolean ascending) {
		return recipeService.getRecipesSortedByCalories(ascending);
	}

	@GetMapping("/{id}")
	public Recipe getRecipeById(@PathVariable Long id) {
		return recipeService.getRecipeById(id);
	}
}
