package model.category;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

import model.recipe.Recipe;

public class Category extends TreeSet<Recipe> implements Serializable{
	
	private static final long serialVersionUID = -6792614284322366569L;
	private String name;
	
	public Category(String name)
	{
		super();
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addRecipe(Recipe r)
	{
		this.add(r);
	}
	
	public Recipe getRecipe(int i)
	{
		Iterator<Recipe> it = this.iterator();
		for (int j = 0; j < i ; j++)
		{
			it.next();
		}
		return it.next();
	}
	
	
	

}
