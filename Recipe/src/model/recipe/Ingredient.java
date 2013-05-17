package model.recipe;

import model.ingredients.*;
import model.measurements.Measurement;

public class Ingredient {
	
	private IngredientInfo info;
	private Measurement amount;
	private String instructions;
	
	public Ingredient(IngredientInfo ingredient, Measurement amount, String instructions)
	{
		info = ingredient;
		this.amount = amount;
		this.instructions = instructions;
	}
	
	public Ingredient(IngredientInfo ingredient, Measurement amount)
	{
		info = ingredient;
		this.amount = amount;
		instructions = "";
	}

	public IngredientInfo getInfo() {
		return info;
	}

	public Measurement getAmount() {
		return amount;
	}

	public String getInstructions() {
		return instructions;
	}
	
	public NutritionInfo getNutrition()
	{
		return info.getNutrition();
	}
	
	public double getServingtoAmountConversion()
	{
		return amount.getAmount() / getNutrition().getServingSize().getAmount();
	}
	
	public NutritionInfo getTotalNutrition()
	{
		double calories = getNutrition().getCalories() * getServingtoAmountConversion();
		double totalFat = getNutrition().getTotalFat() * getServingtoAmountConversion();
		double totalCarbs = getNutrition().getTotalCarbs() * getServingtoAmountConversion();
		double fiber = getNutrition().getFiber() * getServingtoAmountConversion();
		double sugar = getNutrition().getSugar() * getServingtoAmountConversion();
		double protein = getNutrition().getProtein() * getServingtoAmountConversion();
		return new NutritionInfo(amount, calories, totalFat, totalCarbs, fiber, sugar, protein);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(amount.toString() + " ");
		sb.append(info.getName());
		if (instructions.length() > 0)
		{
			sb.append(" ," + instructions);
		}
		return sb.toString();
	}

}
