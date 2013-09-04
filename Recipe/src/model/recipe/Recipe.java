package model.recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.filechooser.FileNameExtensionFilter;

import model.ingredients.IngredientInfo;
import model.ingredients.NutritionInfo;
import model.measurements.Measurement;

public class Recipe implements Serializable, Comparable<Recipe>{
	
	private static TreeMap<String, Recipe> recipes = new TreeMap<String, Recipe>();
	
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
	
	public int numberOfIngredients()
	{
		return ingredients.size();
	}
	
	public Ingredient getIngredient(int i)
	{
		return ingredients.get(i);
	}
	
	public List<Ingredient> getIngredients()
	{
		return ingredients;
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
	
	public String getDirections()
	{
		return directions;
	}
	
	public String toString()
	{
		return name;
	}
	
	public int compareTo(Recipe other)
	{
		if (this == other)
		{
			return 0;
		}
		if (this.getName().equals(other.getName()))
		{
			return this.hashCode() - other.hashCode();
		}
		return this.getName().compareTo(other.getName());
	}
	
	public static void addRecipe(Recipe r)
	{
		recipes.put(r.getName(), r);
	}
	
	public static void loadRecipes(String datFile)
	{
		File folder = new File(datFile);
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".dat");
			}
		});
		ObjectInputStream in = null;
		for(File f : files)
		{
			try {
				in = new ObjectInputStream(new FileInputStream(f));
				Recipe.addRecipe((Recipe) in.readObject());
			} catch (FileNotFoundException e) {
				System.err.println("Ingredients file not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Error reading ingredients file");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		if (in != null)
		{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static int size()
	{
		return recipes.size();
	}
	
	public static Recipe getRecipe(String name)
	{
		return recipes.get(name);
	}
	
	public static Recipe getRecipe(int i)
	{
		Iterator<String> it = recipes.keySet().iterator();
		for (int j = 0; j < i; j++)
		{
			it.next();
		}
		return recipes.get(it.next());
	}
	

}
