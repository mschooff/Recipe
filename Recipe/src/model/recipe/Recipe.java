package model.recipe;

import java.util.ArrayList;
import java.util.List;

import model.ingredients.NutritionInfo;
import model.measurements.Measurement;

public class Recipe {
	
	private String name;
	private ArrayList<Ingredient> ingredients;
	private Measurement yields;
	private int servings;
	String directions;
	
	public Recipe(String name, Measurement yields, int servings, String directions)
	{
		this.name = name;
		ingredients = new ArrayList<Ingredient>();
		this.yields = yields;
		this.servings = servings;
		this.directions = directions;
	}
	
	public Recipe(String name, List<Ingredient> ingredients, Measurement yields, int servings, String directions)
	{
		this.name = name;
		this.ingredients = new ArrayList<Ingredient>();
		for(Ingredient i: ingredients)
		{
			this.ingredients.add(i);
		}
		this.yields = yields;
		this.servings = servings;
		this.directions = directions;
	}
	
	public NutritionInfo getNutrition()
	{
		Measurement totalYield = new Measurement(yields);
		double calories = 0;
		double totalFat = 0;
		double totalCarbs = 0;
		double fiber = 0;
		double sugar = 0;
		double protein = 0;
		for (Ingredient i: ingredients)
		{
			NutritionInfo ingredientNutrition = i.getTotalNutrition();
			calories += ingredientNutrition.getCalories();
			totalFat += ingredientNutrition.getTotalFat();
			totalCarbs += ingredientNutrition.getTotalCarbs();
			fiber += ingredientNutrition.getFiber();
			sugar += ingredientNutrition.getSugar();
			protein += ingredientNutrition.getProtein();
		}
		return new NutritionInfo(totalYield, calories, totalFat, totalCarbs, fiber, sugar, protein);
	}
	
	public void addIngredient(Ingredient i)
	{
		ingredients.add(i);
	}
	
	public int size()
	{
		return ingredients.size();
	}
	
	public Ingredient getIngredient(int i)
	{
		return ingredients.get(i);
	}
	
	public int getNumberOfServings()
	{
		return servings;
	}
	
	public Measurement getYield()
	{
		return yields;
	}
	
	public String getName()
	{
		return name;
	}

}
