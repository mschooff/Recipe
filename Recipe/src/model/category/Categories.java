package model.category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.ingredients.IngredientInfo;
import model.recipe.Recipe;

public class Categories implements TreeModel {
	
	private TreeMap<String, Category> categories; 
	private static Categories singleton = null;
	private ArrayList<TreeModelListener> listeners;
	
	private static String allRecipes = "All Recipes";

	public static Categories getSingleton()
	{
		if (singleton == null)
		{
			singleton = new Categories();
		}
		return singleton;
	}
	
	private Categories() {
		categories = new TreeMap<String, Category>();
		listeners = new ArrayList<TreeModelListener>();
	}
	
	public void loadCategories(String datFile)
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
				Categories.getSingleton().addCategory((Category) in.readObject());
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

	
	public void addCategory(Category c)
	{
		categories.put(c.getName(), c);
	}
	
	public Category getCategory(String name)
	{
		return categories.get(name);
	}
	
	public int size()
	{
		return categories.size();
	}
	
	public Category getCategory(int i)
	{
		Iterator<String> it = categories.keySet().iterator();
		for (int j = 0; j < i; j++)
		{
			it.next();
		}
		return categories.get(it.next());
	}

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		listeners.add(arg0);

	}

	@Override
	public Object getChild(Object parent, int i) {
		if (this.isLeaf(parent))
		{
			return null;
		}
		else if (parent == getRoot())
		{
			if (i == 0)
			{
				return allRecipes;
			}
			//iterate i - 1 times over categories
			Iterator<String> it = categories.keySet().iterator();
			for (int j = 0; j < i; j++)
			{
				it.next();
			}
			return categories.get(it.next());
		}
		else if (parent == allRecipes)
		{
			return Recipe.getRecipe(i);
		}
		else
		{
			return ((Category)parent).getRecipe(i);
		}
	}

	@Override
	public int getChildCount(Object parent) {
		if (this.isLeaf(parent))
		{
			return -1;
		}
		else if (parent == getRoot())
		{
			return categories.size() + 1;
		}
		else if (parent == allRecipes)
		{
			return Recipe.size();
		}
		else
		{
			return ((Category)parent).size();
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (this.isLeaf(parent))
		{
			return -1;
		}
		else if (parent == getRoot())
		{
			if (child == allRecipes)
			{
				return 0;
			}
			Iterator<String> it = categories.keySet().iterator();
			Category c = null;
			int i = 0;
			try {
				do
				{
					c = categories.get(it.next());
					i++;
				} while (c != child);
				return i;
			} catch (NoSuchElementException e)
			{
				return -1;
			}
		}
		else if (parent == allRecipes)
		{
			for (int i = 0; i < Recipe.size(); i++)
			{
				if (Recipe.getRecipe(i) == child)
				{
					return i;
				}
			}
			return -1;
		}
		else
		{
			Category c = (Category)parent;
			for (int i = 0; i < c.size(); i++)
			{
				if (c.getRecipe(i) == child)
				{
					return i;
				}
			}
			return -1;
		}
	}

	@Override
	public Object getRoot() {
		return "Recipes";
	}

	@Override
	public boolean isLeaf(Object node) {
		return (!(node == getRoot() || node == allRecipes || categories.containsValue(node)));
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		listeners.remove(arg0);

	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object newValue) {
		System.err.println("Changing value of " + arg0 + " to " + newValue);

	}

}
