package com.example.recipes_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.recipes_be.model.Recipe;
import com.example.recipes_be.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RecipeService {
	private final RecipeRepository recipeRepository;
	private final RestTemplate restTemplate;

	@Autowired
	public RecipeService(RecipeRepository recipeRepository, RestTemplate restTemplate) {
		this.recipeRepository = recipeRepository;
		this.restTemplate = restTemplate;
	}

	public List<Recipe> loadRecipesFromExternalApi() {
		String baseUrl = "https://dummyjson.com/recipes";
		int limit = 30;
		int skip = 0;
		int total = 0;
		List<Recipe> allRecipes = new ArrayList<>();

		do {
			String url = String.format("%s?limit=%d&skip=%d", baseUrl, limit, skip);
			RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);

			if (response == null || response.getRecipes() == null) {
				break;
			}

			allRecipes.addAll(response.getRecipes());
			total = response.getTotal();
			skip += limit;
		} while (skip < total);

		return recipeRepository.saveAll(allRecipes);
	}

	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	public List<Recipe> getRecipesByCuisine(String cuisine) {
		return recipeRepository.findByCuisine(cuisine);
	}

	public List<Recipe> getRecipesSortedByCalories(boolean ascending) {
		List<Recipe> recipes = recipeRepository.findAll();
		recipes.sort(ascending ? Comparator.comparingInt(Recipe::getCaloriesPerServing)
				: Comparator.comparingInt(Recipe::getCaloriesPerServing).reversed());
		return recipes;
	}

	public Recipe getRecipeById(Long id) {
		return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found!"));
	}
}
