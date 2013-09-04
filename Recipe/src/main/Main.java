package main;

import java.awt.EventQueue;
import java.io.File;

import model.category.Categories;
import model.ingredients.IngredientInfo;
import model.ingredients.NutritionInfo;
import model.measurements.Measurement;
import model.measurements.MeasurementType;
import model.measurements.VolumeMeasurement;
import model.measurements.VolumeUnits;
import model.recipe.Ingredient;
import model.recipe.Recipe;

import view.MainWindow;

public class Main {
	
	public static String ingredientsFile = "ingredients";
	public static String recipesFile = "recipes";
	public static String categoriesFile = "categories";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//init();
		test();
		
			EventQueue.invokeLater(new Runnable() {		
				public void run() {
					
			MainWindow window = new MainWindow();
			window.setVisible(true);
			}
		});
	}

	
	private static void init()
	{
		IngredientInfo.loadIngredients(ingredientsFile);
		Recipe.loadRecipes(recipesFile);
		Categories.getSingleton().loadCategories(categoriesFile);
	}
	
	private static void test()
	{
		
		IngredientInfo.addIngredient(new IngredientInfo("Test 1", new NutritionInfo(new VolumeMeasurement(3, VolumeUnits.LITER), 1, 2, 3, 4, 5, 6), MeasurementType.VOLUME));
		IngredientInfo.addIngredient(new IngredientInfo("Test 2", new NutritionInfo(new VolumeMeasurement(12, VolumeUnits.CUP), 10, 12, 13, 41, 15, 16), MeasurementType.VOLUME));
		
		Recipe recipe1 = new Recipe("Recipe 1", new VolumeMeasurement(10, VolumeUnits.MILLILITER), 5, "1. Step One\n2. Step Two\n 3. Step Three");
		recipe1.addIngredient(new Ingredient(IngredientInfo.getIngredientByName("Test 1"), new VolumeMeasurement(2, VolumeUnits.PINCH)));
		Recipe.addRecipe(recipe1);
		Recipe recipe2 = new Recipe("Recipe 2", new VolumeMeasurement(4, VolumeUnits.MILLILITER), 2, "1. Step One\n2. Step Two\n 3. Step Three");
		recipe2.addIngredient(new Ingredient(IngredientInfo.getIngredientByName("Test 2"), new VolumeMeasurement(.25, VolumeUnits.CUP)));
		Recipe.addRecipe(recipe2);
		Recipe.addRecipe(new Recipe("Recipe 3", new VolumeMeasurement(2, VolumeUnits.GALLON), 1, "1. Step One\n2. Step Two\n 3. Step Three"));
		
		System.out.println(Recipe.size());
	}

}