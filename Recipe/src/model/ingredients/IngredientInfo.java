package model.ingredients;

import java.util.HashSet;

import model.measurements.MeasurementType;

import controllers.IngredientController;

public class IngredientInfo {
	
	private static HashSet<IngredientInfo> ingredients;
	
	private String name;
	private NutritionInfo nutrition;
	private MeasurementType typeOfMeasurement;
	
	
	public String getName()
	{
		return name;
	}
	
	public NutritionInfo getNutrition()
	{
		return nutrition;
	}
	
	public MeasurementType getMeasurementType()
	{
		return typeOfMeasurement;
	}
	
	public static IngredientInfo getIngredientByName(String name)
	{
		for (IngredientInfo i: ingredients)
		{
			if (i.getName().equals(name))
			{
				return i;
			}
		}
		IngredientInfo i = IngredientController.addIngredient();
		ingredients.add(i);
		return i;
	}
	
	private IngredientInfo(String name, NutritionInfo nutrition, MeasurementType m)
	{
		this.name = name;
		this.nutrition = nutrition;
		this.typeOfMeasurement = m;
	}
	
	public static void addIngredient(IngredientInfo i)
	{
		ingredients.add(i);
	}

}
