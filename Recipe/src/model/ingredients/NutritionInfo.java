package model.ingredients;

import java.io.Serializable;

import model.measurements.Measurement;

public class NutritionInfo implements Serializable{
	
	private static final long serialVersionUID = 7667522632826437957L;

	private Measurement servingSize;
	
	private double calories;
	private double totalFat;
	private double totalCarbs;
	private double fiber;
	private double sugar;
	private double protein;
	
	public NutritionInfo(Measurement m, double calorie, double fat, double carbs, 
			double fiber, double sugar, double protein)
	{
		servingSize = m;
		calories = calorie;
		totalFat = fat;
		totalCarbs = carbs;
		this.fiber = fiber;
		this.sugar = sugar;
		this.protein = protein;
	}

	public Measurement getServingSize() {
		return servingSize;
	}

	public double getCalories() {
		return calories;
	}

	public double getTotalFat() {
		return totalFat;
	}

	public double getTotalCarbs() {
		return totalCarbs;
	}

	public double getFiber() {
		return fiber;
	}

	public double getSugar() {
		return sugar;
	}

	public double getProtein() {
		return protein;
	}

}
