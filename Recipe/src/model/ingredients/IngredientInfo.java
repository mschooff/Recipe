package model.ingredients;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

import model.measurements.MeasurementType;
import model.recipe.Recipe;

import controllers.IngredientController;

public class IngredientInfo implements Serializable{
	
	private static final long serialVersionUID = -9078357085939405997L;


	private static TreeMap<String, IngredientInfo> ingredients = new TreeMap<String, IngredientInfo>();
	
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
		return ingredients.get(name);
	}
	
	public IngredientInfo(String name, NutritionInfo nutrition, MeasurementType m)
	{
		this.name = name;
		this.nutrition = nutrition;
		this.typeOfMeasurement = m;
	}
	
	public static IngredientInfo[] getIngredients()
	{
		return ingredients.values().toArray(new IngredientInfo[ingredients.size()]);
	}
	
	public static void addIngredient(IngredientInfo i)
	{
		ingredients.put(i.getName(), i);
	}
	
	public static void loadIngredients(String datFile)
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
				IngredientInfo.addIngredient((IngredientInfo) in.readObject());
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

}
